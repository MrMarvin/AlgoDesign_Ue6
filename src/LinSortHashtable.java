package src;

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
		if (!this.search(key)) {
			// if the table has an empty slot for key, simply insert it there!
			data[place] = new Integer(key);
		} else {
			// else find a following slot which fits the key
			for (int i = place; i < data.length; i++) {
				if (data[i] == null || data[i] == MARK) {
					data[i] = new Integer(key);
					break;
				}
			}
		}

	}

	@Override
	public void delete(int key) {
		data[hashFunction.hash(key)] = new Integer(MARK);

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
		return !((data[hashFunction.hash(key)] == null) || data[hashFunction
				.hash(key)] == MARK);
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
