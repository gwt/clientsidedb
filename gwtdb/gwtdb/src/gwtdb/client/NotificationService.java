package gwtdb.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("notify")
public interface NotificationService extends RemoteService {
	String registerClient(String clientId);
}
