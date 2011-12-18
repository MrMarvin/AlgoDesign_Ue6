import java.util.Random;

public class UniversalHash implements Hashfunction {

	int r;
	// the 10.000th prime. Taken from http://oeis.org/A000040
	int p = 1299709;
	int a;
	int b;
	
	public UniversalHash(int r) {
		this.r = r;
		// randomly choose a and b
		a = new Random().nextInt(p-1)+1;
		b = new Random().nextInt(p-1)+1;
	}

	@Override
	public int hash(int key) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int hashAB(int a,int b,int key){
		return ((a*key)+b % p) % r;
	}
}
