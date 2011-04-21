package gwtdb.server;

import gwtdb.client.ClientEntity;
import gwtdb.client.ReaderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class ReadServiceImpl extends DatastoreLayer implements ReaderService {
	private static final long serialVersionUID = -4972917137189915361L;

	@Override
	public HashMap<String, ClientEntity[]> getAll(final String[] kinds) {
		final HashMap<String, ClientEntity[]> allKinds = new HashMap<String, ClientEntity[]>();

		for (final String kind : kinds) {
			final List<ClientEntity> list = getFromMemcache(getGetAllKey(kind), new ArrayList<ClientEntity>());
			final boolean cacheIsValid = getFromMemcache(getGetAllIsValidKey(kind), false);

			if (!cacheIsValid) {
				list.clear();
				final PreparedQuery pq = db.prepare(new Query(kind));

				for (final Entity entity : pq.asList(FetchOptions.Builder.withLimit(999)).toArray(new Entity[0])) {
					list.add(copy(entity));
				}

				memcacheService.put(getGetAllKey(kind), list);
				memcacheService.put(getGetAllIsValidKey(kind), true);
			}
			
			allKinds.put(kind, list.toArray(new ClientEntity[0]));
		}

		return allKinds;
	}

	@Override
	public ClientEntity getById(final String kind, final long id) {
		try {
			return copy(db.get(KeyFactory.createKey(kind, id)));
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
}
