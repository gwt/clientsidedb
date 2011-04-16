package gwtdb.test;

import gwtdb.client.IdCreator;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class IdCreatorTest extends TestCase {
	public void testGet() {
		assertNotNull(IdCreator.get());
		assertTrue(IdCreator.get().length() > 0);
	}
	
	public void testDistinct() {
		final Set<String> ids = new HashSet<String>();
		final int passes = 1000;
		for (int i=0; i<passes; i++) {
			ids.add(IdCreator.get());
		}
		assertEquals(passes, ids.size());
	}
}
