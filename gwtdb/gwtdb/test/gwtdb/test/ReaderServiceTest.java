package gwtdb.test;

import gwtdb.server.ReadServiceImpl;

public class ReaderServiceTest extends AbstractDatastoreTest {
	private ReadServiceImpl service;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		service = new ReadServiceImpl();
	}

	public void testGetAllEmpty() {
		assertNotNull(service.getAll(new String[0]));
		assertEquals(0, service.getAll(new String[0]).size());
	}
	
	public void testGetAllWithOne() {
		assertNull(service.getById("Contact", 23));
	}
}
