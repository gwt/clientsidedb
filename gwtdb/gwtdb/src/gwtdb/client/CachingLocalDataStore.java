package gwtdb.client;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CachingLocalDataStore implements ReaderServiceAsync {
	public interface Display {
		void append(String message);
		HasClickHandlers getModifyButton();
	}
	
	private static final ReaderServiceAsync reader = GWT.create(ReaderService.class);
	private static final ModifierServiceAsync modifier = GWT.create(ModifierService.class);
	private static final NotificationServiceAsync notifier = GWT.create(NotificationService.class);

	private HashMap<String, ClientEntity[]> cache = new HashMap<String, ClientEntity[]>();
	// private EventBus bus;
	// private String clientId, token;
	private Display view;

	public void newChannelMessage(final String message) {
		view.append(message);
	}
	
	private native void setupChannel(final String token) /*-{
		var channel = new $wnd.goog.appengine.Channel(token);
		var socket = channel.open();
		socket.onmessage = function(evt) {
			this.@gwtdb.client.CachingLocalDataStore::newChannelMessage(Ljava/lang/String;)(evt.data);
		};
		this.@gwtdb.client.CachingLocalDataStore::newChannelMessage(Ljava/lang/String;)("setup channel");
	}-*/;

	public CachingLocalDataStore(final Display view) {
		this.view = view;
		
		newChannelMessage("registering client...");
		
		view.getModifyButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				modifier.put(new ClientEntity("Contact", 1), new AsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						newChannelMessage("added contact");
					}
					
					@Override
					public void onFailure(Throwable caught) {
					}
				});
			}
		});
		
		notifier.registerClient(IdCreator.get(), new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				newChannelMessage("done.");
				setupChannel(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				newChannelMessage("error registering client " + caught.getLocalizedMessage());
			}
		});

		// register handler for dirty-ing of elements -> defer cache updating to latest possible point.
		// bus.addHandler(type, handler)
	}

	@Override
	public void getById(String kind, long id, AsyncCallback<ClientEntity> callback) {
		for (final ClientEntity e : cache.get(kind)) {
			if (e.getId() == id) {
				callback.onSuccess(e);
			}
		}
		// Element has not been found
		// TODO
		callback.onFailure(null); // call onFailure to indicate that element has not been found.
	}

	@Override
	public void getAll(String[] kinds, AsyncCallback<HashMap<String, ClientEntity[]>> callback) {
		// TODO Auto-generated method stub

	}
}
