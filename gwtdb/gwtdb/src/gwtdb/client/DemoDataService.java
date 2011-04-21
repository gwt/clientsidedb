package gwtdb.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("demo")
public interface DemoDataService extends RemoteService {
	public void create(int count);
}
