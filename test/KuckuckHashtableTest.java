/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import src.LinSortHashTable;
import src.KuckuckHashTable;
import src.SimpleHash;

/**
 * @author Marvin Frick
 *
 */
public class KuckuckHashtableTest {

	private KuckuckHashTable tHashMap;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		tHashMap = new KuckuckHashTable(10);
	}
	
	@Test
	public void KuckuckInsertTest() {
		tHashMap.insert(42);
		tHashMap.insert(23);
		
		for (int i = 0; i < 10; i++) {
			tHashMap.insert(new Random().nextInt(4223));
			//tHashMap.printOut();
		}
		
	}
	
	@Test
	public void KuckuckDeleteTest() {

		for (int i = 0; i < 15; i++) {
			tHashMap.insert(i);
			//tHashMap.printOut();
		}
		tHashMap.printOut();
		assertTrue(tHashMap.search(12));
		for (int i = 14; i > 2; i--) {
			tHashMap.delete(i);
			//tHashMap.printOut();
		}
		tHashMap.printOut();
		assertFalse(tHashMap.search(12));
		assertTrue(tHashMap.search(0));
		assertTrue(tHashMap.search(1));
		assertTrue(tHashMap.search(2));
	}

}
