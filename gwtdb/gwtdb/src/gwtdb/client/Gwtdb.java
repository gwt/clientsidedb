package gwtdb.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwtdb implements EntryPoint {
	public void onModuleLoad() {
		final ChannelLogView view = new ChannelLogView();
		RootLayoutPanel.get().add(view);
		new CachingLocalDataStore(view);
	}
}
