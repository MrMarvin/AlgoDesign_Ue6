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
	}
	
	@Test
	public void deleteAndSearchTest() {
		tHashMap.insert(42);
		assertTrue(tHashMap.search(42));
		tHashMap.delete(42);
		assertFalse(tHashMap.search(42));
	}

}
