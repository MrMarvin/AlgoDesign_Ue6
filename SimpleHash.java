public class SimpleHash implements Hashfunction {

	int r;

	public SimpleHash(int r) {
		this.r = r;
	}

	@Override
	public int hash(int key) {
		return (key % r);
	}

}
