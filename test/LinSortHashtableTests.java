package test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.HashMap;
import src.LinSortHashTable;
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
		tHashMap = new LinSortHashTable(10, new SimpleHash(10));
	}

	@Test
	public void insertAndSearchTest() {
		tHashMap.insert(42, true);
		assertTrue(tHashMap.search(42));
		tHashMap.insert(23, true);
		assertTrue(tHashMap.search(23));
		tHashMap.insert(4232, true);
		assertTrue(tHashMap.search(4232));
		assertTrue(tHashMap.search(42));
	}
	
	@Test
	public void deleteAndSearchTest() {
		tHashMap.insert(42, true);
		assertTrue(tHashMap.search(42));
		tHashMap.delete(42);
		assertFalse(tHashMap.search(42));
		tHashMap.insert(23, true);
		tHashMap.insert(223, true);
		tHashMap.insert(2223, true);
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
		tHashMap.insert(9, true);
		assertTrue(tHashMap.search(9));
		tHashMap.insert(99, true);
		assertTrue(tHashMap.search(9));
		assertTrue(tHashMap.search(99));
		tHashMap.insert(0, true);
		tHashMap.insert(10, true);
		tHashMap.insert(1, true);
		tHashMap.insert(429, true);
		assertTrue(tHashMap.search(1));
		assertTrue(tHashMap.search(429));
	}
	
	@Test
	public void deleteAndSearchWithWrapAroundTest() {
		tHashMap.insert(42, true);
		assertTrue(tHashMap.search(42));
		tHashMap.delete(42);
		assertFalse(tHashMap.search(9));
		tHashMap.insert(8, true);
		tHashMap.insert(9, true);
		tHashMap.insert(99, true);
		tHashMap.delete(9);
		assertTrue(tHashMap.search(99));
		tHashMap.delete(99);
		assertFalse(tHashMap.search(99));
	}
	
	@Test
	public void forceToRehashTest() {
		for (int i = 0; i < 23; i++) {
			tHashMap.insert(i, true);
		}
		assertTrue(tHashMap.getSize() >= 40);
		
		for (int i = 0; i < 22; i++) {
			tHashMap.delete(i+1);
		}
		assertTrue(tHashMap.getSize() <= 10);
	}
	
}
