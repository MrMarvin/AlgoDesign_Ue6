package src;

public interface HashMap {
	void insert(int key);
	void insert(int key, boolean checkForResize);
	void delete(int key);
	boolean search(int key);
	int getSize();
	void printOut();
	String describe();
}
