package gwtdb.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(ModiferAndReaderTest.class);
		suite.addTestSuite(ClientEntitySerialisationTest.class);
		suite.addTestSuite(ReaderServiceTest.class);
		suite.addTestSuite(IdCreatorTest.class);
		//$JUnit-END$
		return suite;
	}
}
