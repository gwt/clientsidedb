package gwtdb.server;

import gwtdb.client.NotificationService;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class NotificationServiceImpl extends RemoteServiceServlet implements NotificationService {
	private static final long serialVersionUID = 5020496587263270271L;
	private static final Logger log = Logger.getLogger(NotificationServiceImpl.class.getSimpleName());
	private static final ChannelService service = ChannelServiceFactory.getChannelService();
	// TODO store in memcache
	private static final Set<String> clients = new HashSet<String>();

	@Override
	public String registerClient(final String clientId) {
		log.warning("added client " + clientId);
		clients.add(clientId);
		service.sendMessage(new ChannelMessage(clientId, "[server] added client as " + clientId));

		return service.createChannel(clientId);
	}

	public static void notifyClients(String string) {
		log.warning("notifying " + clients.size() + " clients");

		for (final String clientId : clients) {
			service.sendMessage(new ChannelMessage(clientId, "[server] new entity added or updated"));
			log.warning("notified client " + clientId);
		}

		log.warning("done notifying clients");
	}
}
