package gwtdb.server;

import gwtdb.client.ClientEntity;
import gwtdb.client.ReaderService;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class ReadServiceImpl extends AbstractDatastoreService implements ReaderService {
	private static final long serialVersionUID = -4972917137189915361L;

	@Override
	public HashMap<String, ClientEntity[]> getAll(final String[] kinds) {
		final HashMap<String, ClientEntity[]> res = new HashMap<String, ClientEntity[]>();
		
		for (final String kind: kinds) {
			final ArrayList<ClientEntity> client = new ArrayList<ClientEntity>();
			final PreparedQuery pq = db.prepare(new Query(kind));
			
			for (final Entity entity: pq.asList(FetchOptions.Builder.withLimit(999)).toArray(new Entity[0])) {
				client.add(copy(entity));
			}
			
			res.put(kind, client.toArray(new ClientEntity[0]));
		}
		
		return new HashMap<String, ClientEntity[]>();
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
