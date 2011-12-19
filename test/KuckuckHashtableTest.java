/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.Vector;

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
	public void insertTest() {
		tHashMap.insert(42);
		tHashMap.insert(23);
		
		for (int i = 0; i < 10; i++) {
			tHashMap.insert(new Random().nextInt(4223));
			//tHashMap.printOut();
		}
		//System.out.printf("Kuckuck: fineshed insertTest with a size %d table at a fill rate of %f / %f\n",tHashMap.getSize(),tHashMap.getRate()[0],tHashMap.getRate()[1]);
	}
	
	@Test
	public void deleteTest() {

		for (int i = 0; i < 15; i++) {
			tHashMap.insert(i);
			//tHashMap.printOut();
		}
		assertTrue(tHashMap.search(12));
		for (int i = 14; i > 2; i--) {
			tHashMap.delete(i);
			//tHashMap.printOut();
		}
		assertFalse(tHashMap.search(12));
		assertTrue(tHashMap.search(0));
		assertTrue(tHashMap.search(1));
		assertTrue(tHashMap.search(2));
		//System.out.printf("Kuckuck: fineshed deleteTest with a size %d table at a fill rate of %f / %f\n",tHashMap.getSize(),tHashMap.getRate()[0],tHashMap.getRate()[1]);
	}
	
	@Test
	public void reallyBigInsertTest() {
		
		Vector<Integer> rands = new Vector<Integer>();
		
		for (int i = 0; i < 10000; i++) {
			// generate a lot of random Integers
			int randInt = new Random().nextInt(10000);
			rands.add(randInt);
			// insert them to into the hash
			tHashMap.insert(randInt);
			//tHashMap.printOut();
		}
		for (Integer integer : rands) {
			// check if all of them are in there
			assertTrue(tHashMap.search(integer));
		}
		System.out.printf("Kuckuck: fineshed reallyBigInsertTest with a size %d table at a fill rate of %f / %f\n",tHashMap.getSize(),tHashMap.getRate()[0],tHashMap.getRate()[1]);
	}

}
