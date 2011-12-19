/**
 * 
 */
package src;

import java.util.Vector;

/**
 * @author Marvin Frick
 * 
 */
public class KuckucksTable {

	private static final float ALPHAMIN = ((float) 1 / (float) 16);
	private static final float ALPHAIDEAL = ((float) 1 / (float) 8);
	private static final float ALPHAMAX = ((float) 1 / (float) 4);
	private Integer[] data;
	private int size;
	private int elementsInTable;
	private Hashfunction hashFunction;

	/**
	 * @param size
	 * @param function
	 */
	public KuckucksTable(int size, Hashfunction function) {
		this.hashFunction = function;
		this.size = size;
		data = new Integer[size];
	}

	/**
	 * Returns all keys in this table.
	 * 
	 * @return all keys in this table
	 */
	public Vector<Integer> getAllKeys() {
		Vector<Integer> res = new Vector<Integer>();
		for (int i = 0; i < data.length; i++) {
			res.add(data[i]);
		}
		return res;
	}

	/**
	 * Rehashes the whole table and changes its size.
	 * 
	 * @param biggerOrSmaller
	 *            if <= -1 it makes the table half the size. If >= 1 it doubles
	 *            its size.
	 * @throws Exception
	 *             if the table needs to be resized
	 */
	public void rehash(int biggerOrSmaller) {
		// calculate the new size
		if (biggerOrSmaller <= -1) {
			size = (int) size / 2;
		} else if (biggerOrSmaller >= 1) {
			size = size * 2;
		}

		// change the hashing function
		hashFunction = new UniversalHash(size);

		// and create a new data array
		this.data = new Integer[size];
		elementsInTable = 0;
	}

	/**
	 * Inserts the Key if possible.
	 * 
	 * @param key
	 *            is inserted
	 * @return true if successfully inserted. False otherwise.
	 */
	public boolean insert(int key) {
		if (data[hashFunction.hash(key)] == null) {
			// if the slot is empty: insert key
			data[hashFunction.hash(key)] = key;
			elementsInTable++;
			return true;
		} else if (data[hashFunction.hash(key)].intValue() == key) {
			// if not empty but there already is this exact key
			return true;
		} else {
			// any other key is blocking this slot!
			return false;
		}
	}

	/**
	 * Forcefully inserts the given key. Thereby returns the old inhabitant of
	 * its slot.
	 * 
	 * @param key
	 *            is inserted
	 * @return the key that was previously on that slot.
	 */
	public int forceInsert(int key) {
		int oldInhabitant = data[hashFunction.hash(key)];
		// System.out.printf("Forcefully inserting %d and therefore re-inserting %d\n",key,oldInhabitant);
		data[hashFunction.hash(key)] = key;
		return oldInhabitant;
	}

	public void delete(int key) {
		if (search(key)) {
			data[hashFunction.hash(key)] = null;
		}
	}

	/**
	 * Searches if the given key is in this table.
	 * 
	 * @param key
	 *            that is searched for.
	 * @return true if the key is in the table. False otherwise.
	 */
	public boolean search(int key) {
		return (data[hashFunction.hash(key)] != null && data[hashFunction
				.hash(key)].intValue() == key);
	}

	public int getSize() {
		return size;
	}

	/**
	 * Prints out this table in a single horizontal line.
	 */
	public void printOut() {
		for (Integer e : data) {
			if (e == null) {
				System.out.print("null | ");
			} else {
				System.out.print(e.toString() + " | ");
			}
		}
		System.out.println();
	}
}
