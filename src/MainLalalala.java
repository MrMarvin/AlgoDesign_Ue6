package src;

public class MainLalalala {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		HashMap table = new LinSortHashtable(10, new SimpleHash(10));
		table.printOut();
		table.insert(42);
		table.printOut();
		table.insert(23);
		table.printOut();
		table.insert(4232);
		table.printOut();
		
		table.delete(42);
		table.printOut();
	}

}
