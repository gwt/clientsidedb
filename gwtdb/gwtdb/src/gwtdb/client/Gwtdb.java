package gwtdb.client;

import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class Gwtdb implements EntryPoint {
	public void onModuleLoad() {
		final SimpleEventBus bus = new SimpleEventBus();
		final SampleView view = new SampleView();
		RootLayoutPanel.get().add(view);

		final ClientSideDB db = ClientSideDB.instance(view, bus);
		new SamplePresenter(view, db);
		
		db.getAll(new String[]{"Contact"}, new AsyncCallback<HashMap<String,ClientEntity[]>>() {
			@Override
			public void onSuccess(HashMap<String, ClientEntity[]> result) {
				for (final ClientEntity e: result.get("Contact")) {
					db.addEntity(e);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
}
