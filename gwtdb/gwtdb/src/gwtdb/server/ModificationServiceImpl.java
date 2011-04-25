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
	public void put(final ClientEntity clientEntity) {
		try {
			final Entity entity = (clientEntity.getId() > 0) ? db.get(KeyFactory.createKey(clientEntity.getKind(), clientEntity.getId())) : new Entity(clientEntity.getKind());

			for (final String key : clientEntity.keys()) {
				entity.setProperty(key, clientEntity.get(key));
			}

			final Key k = db.put(entity);
			// insert current id to make sure it is visible in the update description that is sent to the clients
			clientEntity.setId(k.getId());

			invalidateReadCache(clientEntity.getKind());

			// TODO run this asynchronously
			// TODO delete dead clients from set
			NotificationServiceImpl.notifyClients("update?" + clientEntity.toString());
		} catch (EntityNotFoundException e1) {
			final Logger l = Logger.getLogger(ModificationServiceImpl.class.getSimpleName());
			l.warning("Cannot update existing entity " + clientEntity.getKind() + "/" + clientEntity.getId());
		}
	}

	private void invalidateReadCache(final String kind) {
		memcacheService.put(getGetAllIsValidKey(kind), false);
	}
}