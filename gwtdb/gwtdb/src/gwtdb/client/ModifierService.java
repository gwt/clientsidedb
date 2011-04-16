package gwtdb.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("modify")
public interface ModifierService extends RemoteService {
	void put(ClientEntity entity);
}
