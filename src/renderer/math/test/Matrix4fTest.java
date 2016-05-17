package renderer.math.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import renderer.math.Matrix4f;

public class Matrix4fTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInvert() {
		
		float[] elements = new float[] {
				1.2f, 0.3f, 0.7f, 1.7f,
				0.1f, 1.6f, 0.6f, 0.8f,
				0.4f, 0.4f, 2.1f, 1.4f,
				0.2f, 1.1f, 2.2f, 1.0f
		};
		Matrix4f a = new Matrix4f(elements);
		Matrix4f i = a.invert();
		Matrix4f expected = new Matrix4f(new float[] {1.471164309031555f,-1.397896264055131f,-2.908959013420383f,2.689880304678997f,
				0.1436343852013057f,	0.3467537178092126f,-0.8560029017047513f,0.6768226332970619f,
				0.01305767138193693f,-0.5745375408052229f,-0.3808487486398256f,0.9706202393906409f,
				-0.4809575625680085f,1.162132752992382f,2.361262241566919f,-2.417845484221979f
		});
		assertTrue(i.equals(expected));
	}
	
	
	@Test public void testInvert3x3() {
		
		float[] elements = new float[] {
				1.2f, 0.3f, 0.7f, 0f,
				0.1f, 1.6f, 0.6f, 0f,
				0.4f, 0.4f, 2.1f, 0f,
				0f, 0f, 0f, 1.0f};
		
		
		long t1 = System.currentTimeMillis();
		Matrix4f a = new Matrix4f(elements);
		for (int i=0; i< 10000000; i++) {
			Matrix4f invert = a.invert();
		}
		long t2 = System.currentTimeMillis();	
		System.out.println("inversion took " + (t2-t1) + " milliseconds.");
		
		Matrix4f m1 = new Matrix4f(elements);
		Matrix4f m2 = new Matrix4f(elements);
		t1 = System.currentTimeMillis();
		for (int i=0; i< 10000000; i++) {
			Matrix4f m3 = m1.mul(m2);
		}
		t2 = System.currentTimeMillis();	
		System.out.println("multiplication took " + (t2-t1) + " milliseconds.");
		
		t1 = System.currentTimeMillis();
		for (int i=0; i< 10000000; i++) {
			Math.sin(2.23403);
		}
		t2 = System.currentTimeMillis();	
		System.out.println("sin took " + (t2-t1) + " milliseconds.");
		
		
		
	}
	

	@Test
	public void testMultiply() {

		float[] elements1 = new float[] {
				1.2f, 0.3f, 0.7f, 1.7f,
				0.1f, 1.6f, 0.6f, 0.8f,
				0.4f, 0.4f, 2.1f, 1.4f,
				0.2f, 1.1f, 2.2f, 1.0f};
				
		float[] elements2 = new float[] {
				1.2f, 0.3f, 0.7f, 1.7f,
				0.1f, 1.6f, 0.6f, 0.8f,
				0.4f, 0.4f, 2.1f, 1.4f,
				0.2f, 1.1f, 2.2f, 1.0f};
		
	}

}
