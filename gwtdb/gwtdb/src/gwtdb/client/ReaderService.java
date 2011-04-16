package gwtdb.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("read")
public interface ReaderService extends RemoteService {
	HashMap<String, ClientEntity[]> getAll(String[] kinds);
	ClientEntity getById(String kind, long id);
}
