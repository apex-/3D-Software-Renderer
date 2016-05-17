package renderer.geometry;

import renderer.math.Matrix4f;
import renderer.math.Vector4f;

public class Camera {
	
	private Transform transformation;
	private Matrix4f projection;
	
	
	public Camera(Matrix4f projection) {
		this.projection = projection;
	}
	
	
	/**
	 * Returns the view projection matrix
	 * @return the view projection matrix
	 */
	public Matrix4f getViewProjection() {
		
		Matrix4f cameraRotation = this.transformation.getRot().Conjugate().ToRotationMatrix();
		Vector4f cameraPos = this.transformation.getPos().Mul(-1);

		Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.GetX(), cameraPos.GetY(), cameraPos.GetZ());

		return projection.mul(cameraRotation.mul(cameraTranslation));
	}
	

}
