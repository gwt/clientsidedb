package gwtdb.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DemoDataServiceAsync {
	void create(int count, AsyncCallback<Void> callback);
}
