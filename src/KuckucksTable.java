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
	 * The fill rate of this table.
	 * 
	 * @return alpha, the fill rate
	 */
	private float alpha() {
		try {
			return ((float) elementsInTable / (float) size);
		} catch (ArithmeticException e) {
			// dont devide by zero!
			return 0;
		}
		
	}

	/**
	 * Checks if this table is in range of ALPHAIDEAL
	 * 
	 * @throws Exception
	 *             if a resize is needed
	 */
	private void checkIfResizeNeeded() throws Exception {
		float a = alpha();
		if (a < ALPHAMIN) {
			//System.out.printf("need resize: a: %f < %f\n", a,ALPHAMIN);
			throw new Exception("too big!");
		} else if (a > ALPHAMAX) {
			//System.out.printf("need resize: a: %f > %f\n", a,ALPHAMAX);
			throw new Exception("too small!");
		}
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
			//System.out.printf("resizing to: %d\n",size);
		} else if (biggerOrSmaller >= 1) {
			size = size * 2;
			//System.out.printf("resizing to: %d\n",size);
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
	 * @throws Exception
	 *             if this table is, after a successful insert, too full
	 */
	public boolean insert(int key) throws Exception {
		return insert(key, true);
	}

	/**
	 * Inserts the Key if possible.
	 * 
	 * @param key
	 *            is inserted
	 * @param checkForResize TODO
	 * @return true if successfully inserted. False otherwise.
	 * @throws Exception
	 *             if this table is, after a successful insert, too full
	 */
	public boolean insert(int key, boolean checkForResize) throws Exception {
		if (data[hashFunction.hash(key)] == null) {
			// if the slot is empty: insert key
			data[hashFunction.hash(key)] = key;
			elementsInTable++;
			//System.out.printf("inserting: %d\n",key);
			if(checkForResize){
				checkIfResizeNeeded();
			}
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

	/**
	 * Deletes an element in this table
	 * 
	 * @param key
	 *            will be deleted (if in this table)
	 * @throws Exception
	 *             if the table, after successfully deleting, is too big
	 */
	public void delete(int key) throws Exception {
		if (search(key)) {
			data[hashFunction.hash(key)] = null;
			//System.out.printf("deleting: %d\n",key);
			elementsInTable--;
			checkIfResizeNeeded();
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
	
	public float getRate() {
		return alpha();
	}
	
	public int getElementsInTable() {
		return elementsInTable;
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
