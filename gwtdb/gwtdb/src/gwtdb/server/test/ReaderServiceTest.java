package gwtdb.server.test;

import gwtdb.server.ReadServiceImpl;
import junit.framework.TestCase;

public class ReaderServiceTest extends TestCase {
	private ReadServiceImpl service;

	@Override
	protected void setUp() throws Exception {
		service = new ReadServiceImpl();
	}

	public void testGetAllEmpty() {
		assertNotNull(service.getAll(new String[0]));
		assertEquals(0, service.getAll(new String[0]).size());
	}
	
	public void testGetAllWithOne() {
		
	}
}
