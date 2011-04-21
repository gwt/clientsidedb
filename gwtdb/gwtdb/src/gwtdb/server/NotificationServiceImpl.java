package gwtdb.server;

import gwtdb.client.NotificationService;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.Key;

public class NotificationServiceImpl extends MemcacheLayer implements NotificationService {
	private static final int MAX_CLIENT_AGE = 1000*60*30; // 30 minutes
	private static final String CLIENT_IDS = "clientIds";
	private static final String CLIENT_BIRTH = "clientAge";
	private static final long serialVersionUID = 5020496587263270271L;
	private static final ChannelService channelService = ChannelServiceFactory.getChannelService();

	@Override
	public String registerClient(final String clientId) {
		maintainClientIds(clientId);
		
		final String token = channelService.createChannel(clientId);
		// disabled to speed up notification service
		// channelService.sendMessage(new ChannelMessage(clientId, "[server] added client as " + clientId));
		
		return token;
	}

	private void maintainClientIds(final String clientId) {
		// remove out-dated clients
		final List<Long> ages = getFromMemcache(CLIENT_BIRTH, new ArrayList<Long>());
		final List<String> ids = getFromMemcache(CLIENT_IDS, new ArrayList<String>());
		final List<Long> validAges = new ArrayList<Long>();
		final List<String> validIds = new ArrayList<String>();
		
		final long now = System.currentTimeMillis();
		for (int i=0; i<ages.size(); i++) {
			if (now - ages.get(i) < MAX_CLIENT_AGE) {
				validIds.add(ids.get(i));
				validAges.add(ages.get(i));
			}
		}

		// add new client id as well as age of client
		validIds.add(clientId);
		memcacheService.put(CLIENT_IDS, validIds);
		validAges.add(now);
		memcacheService.put(CLIENT_BIRTH, validAges);
	}

	public static void notifyClients(final String messageToClient, final Key key) {
		final List<String> clients = getFromMemcache(CLIENT_IDS, new ArrayList<String>());
		
		for (final String clientId : clients) {
			channelService.sendMessage(new ChannelMessage(clientId, messageToClient));
		}
	}
}
