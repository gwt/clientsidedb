package gwtdb.server;

import gwtdb.client.ClientEntity;
import gwtdb.client.ModifierService;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class ModificationServiceImpl extends AbstractDatastoreService implements ModifierService {
	private static final long serialVersionUID = -8882436882547916759L;
	private static final DatastoreService db = DatastoreServiceFactory.getDatastoreService();
	
	
	@Override
	public void put(final ClientEntity entity) {
		final Entity e = new Entity(entity.getKind());
		for (final String key : entity.keys()) {
			e.setProperty(key, entity.get(key));
		}
		
		final Key k = db.put(e);
		
		// TODO run this asynchronously
		NotificationServiceImpl.notifyClients("new entity added/updated", k);
	}
}