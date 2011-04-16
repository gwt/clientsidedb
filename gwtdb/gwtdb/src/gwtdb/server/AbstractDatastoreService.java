package gwtdb.server;

import gwtdb.client.ClientEntity;

import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

abstract public class AbstractDatastoreService extends RemoteServiceServlet {
	private static final long serialVersionUID = 163908598771316840L;
	protected static final DatastoreService db = DatastoreServiceFactory.getDatastoreService();

	protected ClientEntity copy(final Entity entity) {
		final ClientEntity ce = new ClientEntity(entity.getKind(), entity.getKey().getId());
		
		for (final Map.Entry<String, Object> entry : entity.getProperties().entrySet()) {
			ce.setProperty(entry.getKey(), entry.getValue());
		}
		
		return ce;
	}
}
