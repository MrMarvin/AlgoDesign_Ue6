/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Test;

import src.Hashfunction;
import src.SimpleHash;
import src.UniversalHash;

/**
 * @author Marvin Frick
 *
 */
public class HashfunctionsTest {

	@Test
	public void simpleHashfunctionTest() {
		Hashfunction f = new SimpleHash(10);
		assertTrue(f.hash(42) == 2);
		assertTrue(f.hash(0) == 0);
		assertTrue(f.hash(9999999) == 9);
	}

	@Test
	public void universalHashfunctionTestFAILSSOMETIMESDUETORANDOMNESS() {		
		for (int i = 0; i < 10; i++) {
			Hashfunction f = new UniversalHash(100);
			Hashfunction g = new UniversalHash(100);
			
			assertTrue(f.hash(42) != f.hash(23));
			
			// yes, to a chance of 1/100 this test fails...
			assertTrue(f.hash(42) != g.hash(42));
		}
		
		
		
	}
}
