package gwtdb.client;

import gwtdb.client.SampleView.View;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SamplePresenter {
	public interface Display {
		void append(String message);
		HasClickHandlers getNewButton();
		HasClickHandlers getDeleteBtn();
		HasClickHandlers getSaveBtn();
		HasClickHandlers getCancelBtn();
		void insert(ClientEntity entity);
		void insert(ClientEntity[] clientEntities);
		void switchView(View create);
		ClientEntity getEntity();
		HasKeyUpHandlers getNameBox();
		HasKeyUpHandlers getEmailBox();
		HasClickHandlers getCreateDemoDataBtn();
	}

	private Display view;
	private ClientSideDB db;
	private DemoDataServiceAsync demo = GWT.create(DemoDataService.class);

	public SamplePresenter(final Display view, final ClientSideDB db) {
		this.view = view;
		this.db = db;

		db.getBus().addHandler(UpdateEvent.TYPE, new UpdateEventHandler() {
			@Override
			public void handle(UpdateEvent event) {
				view.append("received update");
				
				db.getAll(new String[] { "Contact" }, new AsyncCallback<HashMap<String, ClientEntity[]>>() {
					@Override
					public void onSuccess(HashMap<String, ClientEntity[]> result) {
						view.insert(result.get("Contact"));
					}

					@Override
					public void onFailure(Throwable caught) {
					}
				});
			}
		});

		view.getNewButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				view.switchView(View.CREATE);
			}
		});
		view.getCancelBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				view.switchView(View.EMPTY);
			}
		});
		view.getDeleteBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				view.switchView(View.EMPTY);
				// TODO delete
			}
		});
		view.getSaveBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				save();
			}
		});
		view.getCreateDemoDataBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				demo.create(1000, new AsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
					}
					
					@Override
					public void onFailure(Throwable caught) {
					}
				});
			}
		});
		final KeyUpHandler saveOnEnter = new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (KeyCodes.KEY_ENTER == event.getNativeKeyCode()) {
					save();
				}
			}
		};
		view.getEmailBox().addKeyUpHandler(saveOnEnter);
		view.getNameBox().addKeyUpHandler(saveOnEnter);
	}

	private void save() {
		final ClientEntity e = view.getEntity();

		db.put(e, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				view.switchView(View.EMPTY);
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
}
