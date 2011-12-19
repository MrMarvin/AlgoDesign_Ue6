/**
 * 
 */
package src;

import java.util.Vector;

/**
 * @author Marvin Frick
 * 
 */
public class KuckuckHashTable implements HashMap {

	private static final int EPSILON = 1;

	private int detectInfinitLoopsCounter = 0;
	private KuckucksTable table1;
	private KuckucksTable table2;

	/**
	 * Constructor for a new KuckuckHashtable
	 * 
	 * @param size
	 *            the initial size of the tables
	 */
	public KuckuckHashTable(int size) {
		table1 = new KuckucksTable(size + EPSILON, new UniversalHash(size
				+ EPSILON));
		table2 = new KuckucksTable(size + EPSILON, new UniversalHash(size
				+ EPSILON));
	}

	/**
	 * @see src.HashMap#insert(int)
	 */
	@Override
	public void insert(int key) {
		// first try to insert into the first or the second table
		if (!(table1.insert(key) || table2.insert(key))) {
			// if both doesn't work: force it into!
			detectInfinitLoopsCounter++;
			if (!detectInfinitLoop()) {
				insert(table2.forceInsert(key));
			} else {
				rehash(table2.forceInsert(key));
			}
		}

	}

	/**
	 * detects if insert() is running in an infinite loop
	 * 
	 * @return true if it (most likely) does. False otherwise
	 */
	private boolean detectInfinitLoop() {
		// according to script 6-34:
		return detectInfinitLoopsCounter > 3 * Math.log(table1.getSize());
	}

	/**
	 * rehashes both tables
	 */
	private void rehash(int tmpElement) {
		//System.out.println("rehashing!");
		detectInfinitLoopsCounter = 0;
		Vector<Integer> allKeys = new Vector<Integer>();
		allKeys.add(tmpElement);
		allKeys.addAll(table1.getAllKeys());
		allKeys.addAll(table2.getAllKeys());
		table1.rehash(0);
		table2.rehash(0);
		for (Integer integer : allKeys) {
			if (integer != null) {
				insert(integer.intValue());
			}
		}
	}

	/**
	 * @see src.HashMap#delete(int)
	 */
	@Override
	public void delete(int key) {
		table1.delete(key);
		table2.delete(key);
	}

	/**
	 * @see src.HashMap#search(int)
	 */
	@Override
	public boolean search(int key) {
		return table1.search(key) || table2.search(key);
	}

	/**
	 * @see src.HashMap#getSize()
	 */
	@Override
	public int getSize() {
		return table1.getSize();
	}

	/**
	 * @see src.HashMap#printOut()
	 */
	@Override
	public void printOut() {
		System.out.print("table1: ");
		table1.printOut();
		System.out.print("table2: ");
		table2.printOut();
		System.out.println();
	}

}
