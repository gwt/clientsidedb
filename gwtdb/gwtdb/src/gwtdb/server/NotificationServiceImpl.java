package gwtdb.server;

import gwtdb.client.NotificationService;

import java.util.HashSet;
import java.util.Set;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class NotificationServiceImpl extends RemoteServiceServlet implements NotificationService {
	private static final long serialVersionUID = 5020496587263270271L;
	private static final ChannelService channelService = ChannelServiceFactory.getChannelService();
	private static final MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService();

	@Override
	public String registerClient(final String clientId) {
		final Set<String> clients = getClients();
		clients.add(clientId);
		memcacheService.put("clientIds", clients);
		
		final String token = channelService.createChannel(clientId);
		// disabled to speed up notification service
		// channelService.sendMessage(new ChannelMessage(clientId, "[server] added client as " + clientId));
		
		return token;
	}

	public static void notifyClients(String string, final Key key) {
		final Set<String> clients = getClients();
		
		for (final String clientId : clients) {
			channelService.sendMessage(new ChannelMessage(clientId, "[server] new entity added or updated"));
		}
	}
	
	private static Set<String> getClients() {
		if (!memcacheService.contains("clientIds")) {
			memcacheService.put("clientIds", new HashSet<String>());
		}

		return (Set<String>) memcacheService.get("clientIds");
	}
}
