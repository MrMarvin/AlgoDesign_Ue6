package src;

import org.omg.CosNaming.NamingContextPackage.NotFound;

public class LinSortHashtable extends ALinSortHashtable {

	private static final int MARK = -1;
	private static final float ALPHAMIN = ((float) 1 / (float) 8);
	private static final float ALPHAIDEAl = ((float) 1 / (float) 4);
	private static final float ALPHAMAX = ((float) 1 / (float) 2);
	int size;
	Hashfunction hashFunction;
	Integer[] data;
	int elementsInTable = 0;

	public LinSortHashtable(int size, Hashfunction function) {
		this.hashFunction = function;
		this.size = size;
		data = new Integer[size];
	}

	/**
	 * Computes the fill rate
	 * 
	 * @return
	 */
	private float rate() {
		try {
			return ((float) elementsInTable / (float) size);
		} catch (ArithmeticException e) {
			return 0;
		}

	}

	/**
	 * Inserts a given key into the data. Takes care for rehashing.
	 * 
	 * @param key
	 *            is the key that is to be added
	 */
	@Override
	public void insert(int key) {
		insertWithoutRehashing(key);
		checkAndDoRehash();
	}

	/**
	 * Deletes a given key. Cares for rehashing.
	 * 
	 * @param key
	 *            is the key that is to be deleted
	 */
	@Override
	public void delete(int key) {
		try {
			data[find(key)] = new Integer(MARK);
			elementsInTable--;
			checkAndDoRehash();
		} catch (NotFound e) {
			// depending on the definition of delete(): do something.
			// I assume that its okay this way. The Object is not there.
		}

	}

	/**
	 * Searches for the key in this HashTable.
	 * 
	 * @return true if the key is in this HashTable, false otherwise. Note:
	 *         False can be either the corresponding slot is null OR the slot is
	 *         marked for deletion
	 */
	@Override
	public boolean search(int key) {
		try {
			find(key);
			return true;
		} catch (NotFound e) {
			return false;
		}
	}

	/**
	 * Rehashes the whole table and changes its size.
	 * 
	 * @param biggerOrSmaller
	 *            if <= -1 it makes the table half the size. If >= 1 it doubles
	 *            its size.
	 */
	private void rehash(int biggerOrSmaller) {
		// TODO: change the hashing function (in case its not the simple one!)
		Integer[] oldData = data;
		if (biggerOrSmaller <= -1) {
			size = (int) size / 2;
		} else if (biggerOrSmaller >= 1) {
			size = size * 2;
		}
		this.data = new Integer[size];
		elementsInTable = 0;
		for (Integer key : oldData) {
			if (key != null && key != MARK) {
				insertWithoutRehashing(key);
			}
		}
	}

	/**
	 * Checks if the data array is out of range and if it is, start a rehash
	 * 
	 */
	private void checkAndDoRehash() {
		float r = rate();
		if (r > ALPHAMAX) {
			// System.out.println("rehashing! (r > ALPHAMAX)");
			rehash(1);
		} else if (r < ALPHAMIN) {
			// System.out.println("rehashing! (r < ALPHAMIN)");
			rehash(-1);
		}

	}

	/**
	 * Inserts the given key into the data. Does not automatically rehash!
	 * 
	 * @param key
	 *            is the key that is to be added
	 */
	private void insertWithoutRehashing(int key) {
		int place = hashFunction.hash(key);
		data[nextFreeSlot(place)] = key;
		elementsInTable++;
	}

	/**
	 * Find the next free slot for inserting into this table.
	 * 
	 * @param place
	 *            from where on the slot is looked for
	 * @return the index at which the element can be added
	 */
	private int nextFreeSlot(int place) {
		for (int i = place; i < data.length; i++) {
			if (data[i] == null || data[i] == MARK) {
				return i;
			}
		}
		// there was no slot found after, so wrap around a look a the top of the
		// table
		for (int i = 0; i < place; i++) {
			if (data[i] == null || data[i] == MARK) {
				return i;
			}
		}
		throw new ArrayIndexOutOfBoundsException(
				"this hashtable is completely full!");

	}

	/**
	 * 
	 * @param key
	 * @return
	 * @throws NotFound
	 */
	private int find(int key) throws NotFound {
		for (int i = hashFunction.hash(key); i < data.length; i++) {
			if (data[i] == null) {
				// from here on, there definitely is not this key
				break;
			} else if (data[i] == key) {
				// found the key
				return i;
			}

		}
		// not at the bottom, but maybe at the top part of the array
		for (int i = 0; i < hashFunction.hash(key); i++) {
			if (data[i] == null) {
				// from here on, there definitely is not this key
				break;
			} else if (data[i] == key) {
				// found the key
				return i;
			}

		}

		throw new NotFound();
	}

	@Override
	public int getSize() {
		return size;
	}

	/**
	 * Prints out this table in a single horizontal line.
	 */
	public void printOut() {
		System.out.print(this.getClass().getName() + ": ");
		for (Integer e : data) {
			if (e == null) {
				System.out.print("null | ");
			} else if (e == -1) {
				System.out.print("mark | ");
			} else {
				System.out.print(e.toString() + " | ");
			}
		}
		System.out.println();
	}

}
