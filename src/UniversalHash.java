package src;
import java.util.Random;

public class UniversalHash implements Hashfunction {

	int r;
	// the 1000th prime. Taken from http://primes.utm.edu/lists/small/1000.txt
	int p = 7919;
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
		return hashAB(a, b, key);
	}

	private int hashAB(int a,int b,int key){
		return (((a*key)+b) % p) % r;
	}
}
