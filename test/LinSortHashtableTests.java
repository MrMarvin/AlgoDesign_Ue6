package test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.HashMap;
import src.LinSortHashtable;
import src.SimpleHash;

/**
 * @author Marvin Frick
 * 
 */
public class LinSortHashtableTests {

	HashMap tHashMap;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		tHashMap = new LinSortHashtable(10, new SimpleHash(10));
	}

	@Test
	public void insertAndSearchTest() {
		tHashMap.insert(42);
		assertTrue(tHashMap.search(42));
		tHashMap.insert(23);
		assertTrue(tHashMap.search(23));
		tHashMap.insert(4232);
		assertTrue(tHashMap.search(4232));
		assertTrue(tHashMap.search(42));
	}
	
	@Test
	public void deleteAndSearchTest() {
		tHashMap.insert(42);
		assertTrue(tHashMap.search(42));
		tHashMap.delete(42);
		assertFalse(tHashMap.search(42));
		tHashMap.insert(23);
		tHashMap.insert(223);
		tHashMap.insert(2223);
		tHashMap.delete(223);
		assertFalse(tHashMap.search(223));
		tHashMap.delete(23);
		// if 23 and 223 got deleted, there still is 2223...
		assertTrue(tHashMap.search(2223));
		tHashMap.delete(2223);
		assertFalse(tHashMap.search(2223));
		
		
		
	}

	@Test
	public void insertAndSearchWithWrapAroundTest() {
		tHashMap.insert(9);
		assertTrue(tHashMap.search(9));
		tHashMap.insert(99);
		tHashMap.printOut();
		assertTrue(tHashMap.search(9));
		assertTrue(tHashMap.search(99));
		tHashMap.insert(0);
		tHashMap.insert(10);
		tHashMap.insert(1);
		tHashMap.insert(429);
		tHashMap.printOut();
		assertTrue(tHashMap.search(1));
		assertTrue(tHashMap.search(429));
	}
	
	@Test
	public void deleteAndSearchWithWrapAroundTest() {

	}
}
