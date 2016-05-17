package renderer.math;


/**
 * A 4x4 matrix class using single-precision floats
 * 
 * @author "Thomas Brüsemeister &lt;tbruese@ari.uni-heidelberg.de&gt;"
 */
public class Matrix4f {

	/** Column-major order matrix elements */
	protected float m11, m12, m13, m14, 
	                m21, m22, m23, m24,
	                m31, m32, m33, m34,
	                m41, m42, m43, m44;
	
	/**
	 * Creates a zero-initialised 4x4 matrix
	 */
	public Matrix4f() {
		
	}
	
	/**
	 * Creates a new 4x4 matrix initialised with the given elements.
	 * The given elements in the one-dimensional array must be in column-major order,
	 * i.e. m11 is array index 0, m21 is array index 1, ... m12 is array index 4 and so on.
	 * There are no lengths checks so the given array must be of length 16 otherwise your
	 * program will crash.
	 * 
	 * @param e the elements to which the new matrix should be initialised.
	 */
	public Matrix4f(float[] e) {
		
		assert e.length == 16;
		
		m11 = e[0];
		m21 = e[1];
		m31 = e[2];
		m41 = e[3];
		m12 = e[4];
		m22 = e[5];
		m32 = e[6];
		m42 = e[7];
		m13 = e[8];
		m23 = e[9];
		m33 = e[10];
		m43 = e[11];
		m14 = e[12];
		m24 = e[13];
		m34 = e[14];
		m44 = e[15];
	}
	
	/**
	 * Returns a copy of a 4x4 matrix
	 * @param n the matrix to be copied
	 */
	public Matrix4f(Matrix4f n) {
		
		m11 = n.m11;
		m21 = n.m21;
		m31 = n.m31;
		m41 = n.m41;
		m12 = n.m12;
		m22 = n.m22;
		m32 = n.m32;
		m42 = n.m42;
		m13 = n.m13;
		m23 = n.m23;
		m33 = n.m33;
		m43 = n.m43;
		m14 = n.m14;
		m24 = n.m24;
		m34 = n.m34;
		m44 = n.m44;
	}

	/** 
	 * Initialises the matrix to an identity matrix
	 */
	public void initIdentity() {
		
		m11 = 1.0f;
		m22 = 1.0f;
		m33 = 1.0f;
		m44 = 1.0f;
	}
	
	/**
	 * Returns a rotation matrix for a rotation around the x, y and z axis.
	 * The rotation order is x, y ,z
	 * 
	 * @param x x rotation angle [rad]
	 * @param y y rotation angle [rad]
	 * @param z z rotation angle [rad]
	 * @return a rotation matrix for a rotation around the x, y and z axis.
	 */
	public Matrix4f rotate(float x, float y, float z) {
		
			Matrix4f rx = new Matrix4f();
			Matrix4f ry = new Matrix4f();
			Matrix4f rz = new Matrix4f();
			
			float sinz = (float)Math.sin(z);
			float cosz = (float)Math.cos(z);
			float sinx = (float)Math.sin(x);
			float cosx = (float)Math.cos(x);
			float siny = (float)Math.sin(y);
			float cosy = (float)Math.cos(y);

			rz.m11 = cosz; rz.m12 = -sinz; rz.m13 = 0; rz.m14 = 0;
			rz.m21 = sinz; rz.m22 = cosz;  rz.m23 = 0; rz.m24 = 0;
			rz.m31 = 0;	   rz.m32 = 0;	   rz.m33 = 1; rz.m34 = 0;
			rz.m41 = 0;	   rz.m42 = 0;	   rz.m34 = 0; rz.m44 = 1;

			rx.m11 = 1;	rx.m12 = 0;	   rx.m13 = 0;     rx.m14 = 0;
			rx.m21 = 0;	rx.m22 = cosx; rx.m23 = -sinx; rx.m24 = 0;
			rx.m31 = 0; rx.m32 = sinx; rx.m33 = cosx ; rx.m34 = 0;
			rx.m41 = 0;	rx.m42 = 0;	   rx.m43 = 0;     rx.m44 = 1;

			ry.m11 = cosy; ry.m12 = 0; ry.m13 = -siny; ry.m14 = 0;
			ry.m21 = 0;	   ry.m22 = 1; ry.m23 = 0;	   ry.m24 = 0;
			ry.m31 = siny; ry.m32 = 0; ry.m33 = cosy;  ry.m34 = 0;
			ry.m41 = 0;	   ry.m42 = 0; ry.m34 = 0;     ry.m44 = 1;

			return rz.mul(ry.mul(rx));
	}
	
	
	
	/**
	 * Initialises a rotation matrix with the given vector for each axis
	 * 
	 * @param forward the "forward" vector
	 * @param up the "upward" vector
	 * @param right the "right" vector
	 * @return a rotation matrix with the given vector for each axis
	 */
	public Matrix4f initRotation(Vector4f forward, Vector4f up, Vector4f right) {
		
		Vector4f f = forward;
		Vector4f r = right;
		Vector4f u = up;

		m11 = r.GetX();	m12 = r.GetY();	m13 = r.GetZ();	m14 = 0;
		m21 = u.GetX();	m22 = u.GetY();	m23 = u.GetZ();	m24 = 0;
		m31 = f.GetX();	m23 = f.GetY();	m33 = f.GetZ();	m34 = 0;
		m41 = 0;		m24 = 0;		m34 = 0;		m44 = 1;

		return this;
	}
	
	
	public Matrix4f initTranslation(float x, float y, float z) {
		this.initIdentity();
		m14 = x;
		m24 = y;
		m34 = z;
		return this;
	}
	
	
	public Matrix4f initScale(float x, float y, float z) {
		this.m11 = x;
		this.m22 = y;
		this.m33 = z;
		this.m44 = 1.0f;
		return this;
	}
	
	
	/**
	 * Compares the matrix with a given 4x4 matrix
	 * 
	 * @param a the matrix to compare to
	 * @return true if the given matrix matches the given one, otherwise false
	 */
	public boolean equals(Matrix4f a) {
			
		float epsilon = 0.00001f;
		if (Math.abs(Math.abs(m11/a.m11) - 1.0) < epsilon &&
			Math.abs(Math.abs(m21/a.m21) - 1.0) < epsilon &&
			Math.abs(Math.abs(m31/a.m31) - 1.0) < epsilon &&
			Math.abs(Math.abs(m41/a.m41) - 1.0) < epsilon &&
			Math.abs(Math.abs(m12/a.m12) - 1.0) < epsilon &&
			Math.abs(Math.abs(m22/a.m22) - 1.0) < epsilon &&
			Math.abs(Math.abs(m32/a.m32) - 1.0) < epsilon &&
			Math.abs(Math.abs(m42/a.m42) - 1.0) < epsilon &&
			Math.abs(Math.abs(m13/a.m13) - 1.0) < epsilon &&
			Math.abs(Math.abs(m23/a.m23) - 1.0) < epsilon &&
			Math.abs(Math.abs(m33/a.m33) - 1.0) < epsilon &&
			Math.abs(Math.abs(m43/a.m43) - 1.0) < epsilon &&
			Math.abs(Math.abs(m14/a.m14) - 1.0) < epsilon &&
			Math.abs(Math.abs(m24/a.m24) - 1.0) < epsilon &&
			Math.abs(Math.abs(m34/a.m34) - 1.0) < epsilon &&
			Math.abs(Math.abs(m44/a.m44) - 1.0) < epsilon
		) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns a new transformation matrix which performs a translation on each axis
	 * 
	 * @param x the translation on the x-axis
	 * @param y the translation on the y-axis
	 * @param z the translation on the z-axis
	 * @return a new transformation matrix which performs a translation on each axis
	 */
	public Matrix4f translate(float x, float y, float z) {
		
		Matrix4f translationMatrix = new Matrix4f();
		translationMatrix.initIdentity();
		translationMatrix.m14 = x;
		translationMatrix.m24 = y;
		translationMatrix.m34 = z;
		
		return this.mul(translationMatrix);
	}
	
	/**
	 * Multiplies the given {@link Vector4f} by the matrix which yields a transformation
	 * @param r the {@link Vector4f} to be multiplied
	 * @return the transformed {@link Vector4f} 
	 */
	public Vector4f mul(Vector4f r)
	{
		return new Vector4f(m11 * r.GetX() + m12 * r.GetY() + m13 * r.GetZ() + m14 * r.GetW(),
		                    m21 * r.GetX() + m22 * r.GetY() + m23 * r.GetZ() + m24 * r.GetW(),
		                    m31 * r.GetX() + m32 * r.GetY() + m33 * r.GetZ() + m34 * r.GetW(),
							m41 * r.GetX() + m42 * r.GetY() + m34 * r.GetZ() + m44 * r.GetW());
	}
	
	/**
	 * Multiplies the matrix A with a given matrix B and returns the multiplied matrix C
	 * such that C = A*B
	 * 
	 * @param b the matrix to be multiplied
	 * @return the result of the multiplied matrices
	 */
	public Matrix4f mul(Matrix4f b) {
		
		Matrix4f c = new Matrix4f();
		c.m11 = this.m11 * b.m11 + this.m12 * b.m21 + this.m13 * b.m31 + this.m14 * b.m41;
		c.m21 = this.m21 * b.m11 + this.m22 * b.m21 + this.m23 * b.m31 + this.m24 * b.m41;
		c.m31 = this.m31 * b.m11 + this.m32 * b.m21 + this.m33 * b.m31 + this.m34 * b.m41;
		c.m41 = this.m41 * b.m11 + this.m42 * b.m21 + this.m43 * b.m31 + this.m44 * b.m41;
		
		c.m12 = this.m11 * b.m12 + this.m12 * b.m22 + this.m13 * b.m32 + this.m14 * b.m42;
		c.m22 = this.m21 * b.m12 + this.m22 * b.m22 + this.m23 * b.m32 + this.m24 * b.m42;
		c.m32 = this.m31 * b.m12 + this.m32 * b.m22 + this.m33 * b.m32 + this.m34 * b.m42;
		c.m42 = this.m41 * b.m12 + this.m42 * b.m22 + this.m43 * b.m32 + this.m44 * b.m42;
		
		c.m13 = this.m11 * b.m13 + this.m12 * b.m23 + this.m13 * b.m33 + this.m14 * b.m43;
		c.m23 = this.m21 * b.m13 + this.m22 * b.m23 + this.m23 * b.m33 + this.m24 * b.m43;
		c.m33 = this.m31 * b.m13 + this.m32 * b.m23 + this.m33 * b.m33 + this.m34 * b.m43;
		c.m43 = this.m41 * b.m13 + this.m42 * b.m23 + this.m43 * b.m33 + this.m44 * b.m43;
		
		c.m14 = this.m11 * b.m14 + this.m12 * b.m24 + this.m13 * b.m34 + this.m14 * b.m44;
		c.m24 = this.m21 * b.m14 + this.m22 * b.m24 + this.m23 * b.m34 + this.m24 * b.m44;
		c.m34 = this.m31 * b.m14 + this.m32 * b.m24 + this.m33 * b.m34 + this.m34 * b.m44;
		c.m44 = this.m41 * b.m14 + this.m42 * b.m24 + this.m43 * b.m34 + this.m44 * b.m44;
		
		return c;
	}
	
	
	/**
	 * Returns a transformation matrix with non-uniform scaling on each axis
	 * 
	 * @param x scaling factor for the x-axis
	 * @param y scaling factor for the y-axis
	 * @param z scaling factor for the z-axis
	 * @return a new transformation matrix containing the scale operation
	 */
	public Matrix4f scale(float x, float y, float z) {
		
		Matrix4f scalingMatrix = new Matrix4f();
		scalingMatrix.m11 = x;
		scalingMatrix.m22 = y;
		scalingMatrix.m33 = z;
		scalingMatrix.m44 = 1.0f;
		
		return this.mul(scalingMatrix);
	}
	
	/**
	 * Returns a transform matrix with a uniform scaling on each axis
	 * 
	 * @param a the scaling factor for all axes
	 * @return a new transformation matrix containing the scale operation
	 */
	public Matrix4f scale(float a) {
		
		return this.scale(a, a, a);
	}
	
	
	/**
	 * Returns the determinant of the 4x4 matrix.
	 * 
	 * @return the determinant of the 4x4 matrix.
	 */
	public float getDeterminant() {
		
		return m11*m22*m33*m44 + m11*m23*m34*m42 + m11*m24*m32*m43 +
			   m12*m21*m34*m43 + m12*m23*m31*m44 + m12*m24*m33*m41 +
			   m13*m21*m32*m44 + m13*m22*m34*m41 + m13*m24*m31*m42 +
			   m14*m21*m33*m42 + m14*m22*m31*m43 + m14*m23*m32*m41 -
			   m11*m22*m34*m43 - m11*m23*m32*m44 - m11*m24*m33*m42 -
			   m12*m21*m33*m44 - m12*m23*m34*m41 - m12*m24*m31*m43 -
			   m13*m21*m34*m42 - m13*m22*m31*m44 - m13*m24*m32*m41 -
			   m14*m21*m32*m43 - m14*m22*m33*m41 - m14*m23*m31*m42;
	}
	
	/**
	 * Returns the inverse of the matrix or a matrix filled with NaNs in case no inverse 
	 * exists (i.e. the determinant is zero)
	 * 
	 * @return the inverse of the matrix
	 */
	public Matrix4f invert() {
		
		float det = this.getDeterminant();

		if (det != 0) {
			Matrix4f i = new Matrix4f();
			i.m11 = (m22*m33*m44 + m23*m34*m42 + m24*m32*m43 - m22*m34*m43 - m23*m32*m44 - m24*m33*m42)/det;
			i.m12 = (m12*m34*m43 + m13*m32*m44 + m14*m33*m42 - m12*m33*m44 - m13*m34*m42 - m14*m32*m43)/det;
			i.m13 = (m12*m23*m44 + m13*m24*m42 + m14*m22*m43 - m12*m24*m43 - m13*m22*m44 - m14*m23*m42)/det;
			i.m14 = (m12*m24*m33 + m13*m22*m34 + m14*m23*m32 - m12*m23*m34 - m13*m24*m32 - m14*m22*m33)/det;
			i.m21 = (m21*m34*m43 + m23*m31*m44 + m24*m33*m41 - m21*m33*m44 - m23*m34*m41 - m24*m31*m43)/det;
			i.m22 = (m11*m33*m44 + m13*m34*m41 + m14*m31*m43 - m11*m34*m43 - m13*m31*m44 - m14*m33*m41)/det;
			i.m23 = (m11*m24*m43 + m13*m21*m44 + m14*m23*m41 - m11*m23*m44 - m13*m24*m41 - m14*m21*m43)/det;
			i.m24 = (m11*m23*m34 + m13*m24*m31 + m14*m21*m33 - m11*m24*m33 - m13*m21*m34 - m14*m23*m31)/det;
			i.m31 = (m21*m32*m44 + m22*m34*m41 + m24*m31*m42 - m21*m34*m42 - m22*m31*m44 - m24*m32*m41)/det;
			i.m32 = (m11*m34*m42 + m12*m31*m44 + m14*m32*m41 - m11*m32*m44 - m12*m34*m41 - m14*m31*m42)/det;
			i.m33 = (m11*m22*m44 + m12*m24*m41 + m14*m21*m42 - m11*m24*m42 - m12*m21*m44 - m14*m22*m41)/det;
			i.m34 = (m11*m24*m32 + m12*m21*m34 + m14*m22*m31 - m11*m22*m34 - m12*m24*m31 - m14*m21*m32)/det;
			i.m41 = (m21*m33*m42 + m22*m31*m43 + m23*m32*m41 - m21*m32*m43 - m22*m33*m41 - m23*m31*m42)/det;
			i.m42 = (m11*m32*m43 + m12*m33*m41 + m13*m31*m42 - m11*m33*m42 - m12*m31*m43 - m13*m32*m41)/det;
			i.m43 = (m11*m23*m42 + m12*m21*m43 + m13*m22*m41 - m11*m22*m43 - m12*m23*m41 - m13*m21*m42)/det;
			i.m44 = (m11*m22*m33 + m12*m23*m31 + m13*m21*m32 - m11*m23*m32 - m12*m21*m33 - m13*m22*m31)/det;
			
			return i;
		} else {
			// no inverse exists -> return NaN values
			return new Matrix4f(new float[] {
					Float.NaN, Float.NaN, Float.NaN, Float.NaN,
					Float.NaN, Float.NaN, Float.NaN, Float.NaN,
					Float.NaN, Float.NaN, Float.NaN, Float.NaN,
					Float.NaN, Float.NaN, Float.NaN, Float.NaN
			});
		}
	}
	
	/**
	 * Returns a string representation of the 4x4 matrix
	 * 
	 * @return a string representation of the 4x4 matrix
	 */
	public String toString() {
		
		String n = System.getProperty("line.separator");
		return  m11 + " " + m12 + " " + m13 + " " + m14 + n +
				m21 + " " + m22 + " " + m23 + " " + m24 + n +
				m31 + " " + m32 + " " + m33 + " " + m34 + n +
				m41 + " " + m42 + " " + m43 + " " + m44;
	}

}
