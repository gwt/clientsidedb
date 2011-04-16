package gwtdb.server;

import gwtdb.client.NotificationService;

import java.util.HashSet;
import java.util.Set;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class NotificationServiceImpl extends RemoteServiceServlet implements NotificationService {
	private static final long serialVersionUID = 5020496587263270271L;
	private static final ChannelService service = ChannelServiceFactory.getChannelService();
	private static final Set<String> clients = new HashSet<String>();

	@Override
	public String registerClient(String clientId) {
		clients.add(clientId);
		
		return service.createChannel(clientId);
		//service.sendMessage(new ChannelMessage(clientId, "connected"));
	}
	
	public static void notifyClients(String string) {
		for (final String clientId : clients) {
			service.sendMessage(new ChannelMessage(clientId, "new entity added or updated"));
		}		
	}
}
