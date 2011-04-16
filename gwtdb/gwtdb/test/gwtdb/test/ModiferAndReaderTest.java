package gwtdb.test;

import java.util.HashMap;

import gwtdb.client.ClientEntity;
import gwtdb.server.ModificationServiceImpl;
import gwtdb.server.ReadServiceImpl;

public class ModiferAndReaderTest extends AbstractDatastoreTest {
	private ModificationServiceImpl modifier;
	private ReadServiceImpl reader;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		modifier = new ModificationServiceImpl();
		reader = new ReadServiceImpl();
	}
	
	public void testPut() {
		modifier.put(new ClientEntity("contact"));
	}
	
	public void testPutAndGet() {
		modifier.put(new ClientEntity("a"));
	
		final HashMap<String, ClientEntity[]> all = reader.getAll(new String[]{"a"});
		assertEquals(1, all.get("a").length);
		assertEquals("a", all.get("a")[0].getKind());
	}
	
}
