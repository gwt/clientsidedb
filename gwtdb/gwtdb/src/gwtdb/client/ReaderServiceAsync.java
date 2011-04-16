package gwtdb.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReaderServiceAsync {
	void getAll(String[] kinds, AsyncCallback<HashMap<String, ClientEntity[]>> callback);
	void getById(String kind, long id, AsyncCallback<ClientEntity> callback);
}
