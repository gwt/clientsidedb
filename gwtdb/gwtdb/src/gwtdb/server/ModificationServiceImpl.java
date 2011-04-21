package gwtdb.server;

import gwtdb.client.ClientEntity;
import gwtdb.client.ModifierService;

import java.util.logging.Logger;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ModificationServiceImpl extends DatastoreLayer implements ModifierService {
	private static final long serialVersionUID = -8882436882547916759L;

	@Override
	public void put(final ClientEntity entity) {
		try {
			final Entity e = (entity.getId() > 0) ? db.get(KeyFactory.createKey(entity.getKind(), entity.getId())) : new Entity(entity.getKind());

			for (final String key : entity.keys()) {
				e.setProperty(key, entity.get(key));
			}

			final Key k = db.put(e);

			invalidateReadCache(entity.getKind());
			
			// TODO run this asynchronously
			// TODO delete dead clients from set
			NotificationServiceImpl.notifyClients("new entity added/updated", k);
		} catch (EntityNotFoundException e1) {
			final Logger l = Logger.getLogger(ModificationServiceImpl.class.getSimpleName());
			l.warning("Cannot update existing entity " + entity.getKind() + "/" + entity.getId());
		}
	}
	
	private void invalidateReadCache(final String kind) {
		memcacheService.put(getGetAllIsValidKey(kind), false);
	}
}