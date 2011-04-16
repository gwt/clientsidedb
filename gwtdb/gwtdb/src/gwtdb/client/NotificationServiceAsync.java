package gwtdb.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface NotificationServiceAsync {
	void registerClient(String clientId, AsyncCallback<String> callback);
}
