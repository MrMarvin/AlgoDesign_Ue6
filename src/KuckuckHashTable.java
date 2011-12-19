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
		insert(key, true);
	}

	/**
	 * 
	 */
	public void insert(int key, boolean checkForResize) {
		try {
			// first try to insert into the first or the second table
			if (!(table1.insert(key, true) || table2.insert(key, true))) {
				// if both doesn't work: force it into!
				detectInfinitLoopsCounter++;
				if (!detectInfinitLoop()) {
					insert(table2.forceInsert(key), checkForResize);
				} else {
					//System.out.println("detected infinite loop. Need to rehash!");
					rehash(table2.forceInsert(key), 0);
				}
			}
		} catch (Exception e) {
			if (e.getMessage() == "too small!") {
				rehash(null, 1);
			} else if (e.getMessage() == "too big!") {
				// System.out.println("but i dont care. Seems that the other table complains while inserting");
			}
			// else: do nothing, this is not our exception!
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
	 * 
	 * @param biggerOrSmaller
	 *            TODO
	 */
	private void rehash(Integer tmpElement, int biggerOrSmaller) {
		//System.out.printf("before reahash: %d. New tables are of size %d. With %d / %d (%d) elements this is a rate of %f / %f\n", biggerOrSmaller, getSize(),table1.getElementsInTable(), table2.getElementsInTable(), table1.getElementsInTable()+table2.getElementsInTable(), getRate()[0],getRate()[1]);
		detectInfinitLoopsCounter = 0;
		Vector<Integer> allKeys = new Vector<Integer>();
		allKeys.add(tmpElement);
		allKeys.addAll(table1.getAllKeys());
		allKeys.addAll(table2.getAllKeys());
		table1.rehash(biggerOrSmaller);
		table2.rehash(biggerOrSmaller);
		for (Integer integer : allKeys) {
			if (integer != null) {
				insert(integer.intValue(),false);
			}
		}
		//System.out.printf("reahashed. New tables are of size %d. With %d / %d (%d) elements this is a rate of %f / %f\n\n", getSize(),table1.getElementsInTable(), table2.getElementsInTable(), table1.getElementsInTable()+table2.getElementsInTable(), getRate()[0],getRate()[1]);
	}

	/**
	 * @see src.HashMap#delete(int)
	 */
	@Override
	public void delete(int key) {
		try {
			table1.delete(key);
			table2.delete(key);
		} catch (Exception e) {
			if (e.getMessage() == "too big!") {
				rehash(null, -1);
			}
			// else: do nothing, this is not our exception!
		}
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

	public float[] getRate() {
		float[] res = new float[2];
		res[0] = table1.getRate();
		res[1] = table2.getRate();
		return res;
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
