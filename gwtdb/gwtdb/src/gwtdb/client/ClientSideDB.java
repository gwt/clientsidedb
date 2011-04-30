package gwtdb.client;

import gwtdb.client.SamplePresenter.Display;

import java.util.Arrays;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClientSideDB implements ReaderServiceAsync {
	private static ClientSideDB instance;
	private static Display view;
	private static EventBus bus;
	private static final ReaderServiceAsync reader = GWT.create(ReaderService.class);
	private static final ModifierServiceAsync modifier = GWT.create(ModifierService.class);
	private static final NotificationServiceAsync notifier = GWT.create(NotificationService.class);

	private HashMap<String, ClientEntity[]> cache = new HashMap<String, ClientEntity[]>();
	private static boolean dirty = false;

	public static ClientSideDB instance(final Display view, final EventBus bus) {
		if (null == instance) {
			ClientSideDB.view = view;
			ClientSideDB.bus = bus;
			instance = new ClientSideDB();
		}
		return instance;
	}

	private ClientSideDB() {
		notifier.registerClient(IdCreator.get(), new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				view.append("notified");
				setupChannel(result);
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	// Updates are only received (i.e. this method is only called) when it has
	// one parameter (?)
	private static void onUpdate(final String data) {
		dirty = true;
		bus.fireEvent(new UpdateEvent(ClientEntity.fromUpdateDescription(data)));
		view.append("update: " + data);
	}

	private native void setupChannel(final String token) /*-{
		var channel = new $wnd.goog.appengine.Channel(token);
		var socket = channel.open();
		socket.onmessage = function(evt) {
			@gwtdb.client.ClientSideDB::onUpdate(Ljava/lang/String;)(evt.data);
		};
	}-*/;

	@Override
	public void getById(String kind, long id, AsyncCallback<ClientEntity> callback) {
		for (final ClientEntity e : cache.get(kind)) {
			if (e.getId() == id) {
				callback.onSuccess(e);
			}
		}
		// Element has not been found
		// TODO
		callback.onFailure(null); // call onFailure to indicate that element has
									// not been found.
	}

	@Override
	public void getAll(final String[] kinds, final AsyncCallback<HashMap<String, ClientEntity[]>> callback) {
		if (dirty || cache.isEmpty()) {
			reader.getAll(kinds, new AsyncCallback<HashMap<String, ClientEntity[]>>() {
				@Override
				public void onSuccess(HashMap<String, ClientEntity[]> result) {
					callback.onSuccess(cache = result);
					dirty = false;
				}

				@Override
				public void onFailure(Throwable caught) {
				}
			});
		} else {
			callback.onSuccess(cache);
		}
	}

	public void addEntity(final ClientEntity e) {
		view.insert(e);
	}

	public void put(ClientEntity entity, final AsyncCallback<Void> callback) {
		dirty = true;

		modifier.put(entity, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				callback.onSuccess(result);
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	public static EventBus getBus() {
		return bus;
	}

	// add/update given entity to/in cache
	public void update(final ClientEntity updatedEntity) {
		final String kind = updatedEntity.getKind();
		final ClientEntity[] old = cache.get(kind);
		boolean updated = false;

		// update existing entity in cache
		for (final ClientEntity e : old) {
			if (e.getId() == updatedEntity.getId()) {
				for (final String key : updatedEntity.keys()) {
					e.setProperty(key, updatedEntity.get(key));
				}
				updated = true;
				break;
			}
		}

		// add entity to cache
		if (!updated) {
			final ClientEntity[] newArray = new ClientEntity[old.length + 1];
			for (int i=0; i<old.length; i++) {
				newArray[i] = old[i];
			}
			newArray[old.length] = updatedEntity;
			cache.put(kind, newArray);
		}
		
		dirty = false;
	}
}
