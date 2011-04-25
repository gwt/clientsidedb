package gwtdb.test;

import gwtdb.client.ClientEntity;
import junit.framework.TestCase;

public class ClientEntitySerialisationTest extends TestCase {
	public void testToString() {
		final ClientEntity f = new ClientEntity("Contact", 23);
		f.setProperty("Name", "Erwin");
		f.setProperty("LastName", "Belzig");
		
		assertEquals("id=23&kind=Contact&Name=Erwin&LastName=Belzig&", f.toString());
	}
	
	public void testFromString() {
		final ClientEntity c  = ClientEntity.fromUpdateDescription("update?id=23&kind=Contact&Name=Erwin&LastName=Belzig&");

		assertEquals("Contact", c.getKind());
		assertEquals(23, c.getId());
		assertEquals("Erwin", c.get("Name"));
		assertEquals("Belzig", c.get("LastName"));
	}
}
