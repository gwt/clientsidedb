package gwtdb.server;

import gwtdb.client.DemoDataService;

import java.util.ArrayList;
import java.util.Random;

import com.google.appengine.api.datastore.AsyncDatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DemoServiceImpl extends RemoteServiceServlet implements DemoDataService {
	private static final long serialVersionUID = 3680013580179978613L;
	private static final AsyncDatastoreService db = DatastoreServiceFactory.getAsyncDatastoreService();
	private static final Random r = new Random();
	
	@Override
	public void create(int count) {
		final ArrayList<Entity> list = new ArrayList<Entity>();
		for (int i=0; i<count; i++) {
			final Entity e = new Entity("Contact");
			e.setProperty("Name", "Contact Name" + r.nextInt());
			e.setProperty("Mail", "Contact Mail " + r.nextInt());
			list.add(e);
		}
		
		db.put(list);
	}
}
