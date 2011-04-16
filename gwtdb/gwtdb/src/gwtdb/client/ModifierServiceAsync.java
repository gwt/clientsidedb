package gwtdb.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ModifierServiceAsync {
	void put(ClientEntity entity, AsyncCallback<Void> callback);
}
