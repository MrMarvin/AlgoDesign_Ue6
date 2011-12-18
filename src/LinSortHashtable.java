package src;

import org.omg.CosNaming.NamingContextPackage.NotFound;

public class LinSortHashtable extends ALinSortHashtable {

	private static final int MARK = -1;
	int size;
	Hashfunction hashFunction;
	Integer[] data;

	public LinSortHashtable(int size, Hashfunction function) {
		this.hashFunction = function;
		this.size = size;
		data = new Integer[size];
	}

	@Override
	public void insert(int key) {
		int place = hashFunction.hash(key);
		data[nextFreeSlot(place)] = key;
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

	@Override
	public void delete(int key) {
		try {
			data[find(key)] = new Integer(MARK);
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
