package renderer.rasterize;

import java.lang.reflect.GenericArrayType;

import renderer.display.Display;
import renderer.display.RenderContext;
import renderer.geometry.Vertex;

/**
 * 
 * @author tbruese
 *
 */
public class TriangleRasterizer {
	
	
	RenderContext renderContext;
	
	public TriangleRasterizer(Display display) {
		
		this.renderContext = display.getRenderContext();
		
	}
	
	public void drawTriangle(Vertex v1, Vertex v2, Vertex v3) {
		
		// sort the vertices according to their y coordinate (in ascending order)
		if (v1.getPos().GetY() > v2.getPos().GetY()) {
			Vertex temp = v1;
			v1 = v2;
			v2 = temp;
		}
		
		if (v2.getPos().GetY() > v3.getPos().GetY()) {
			Vertex temp = v2;
			v2 = v3;
			v3 = temp;
		}
		
		if (v1.getPos().GetY() > v2.getPos().GetY()) {
			Vertex temp = v1;
			v1 = v2;
			v2 = temp;
		}
		
		Edge topToBottom = new Edge(v1, v3);
		Edge topToMiddle = new Edge(v1, v2);
		Edge middleToBottom = new Edge(v2, v3);
		
		scanEdges(topToBottom, topToMiddle, TriangleRasterizer.isLeftHanded(v1, v3, v2));
		scanEdges(topToBottom, middleToBottom, TriangleRasterizer.isLeftHanded(v1, v3, v2));
	}
	
	private void scanEdges(Edge a, Edge b, boolean handedness)
	{
		Edge left = a;
		Edge right = b;
		if(handedness)
		{
			Edge temp = left;
			left = right;
			right = temp;
		}

		int yStart = b.getYStart(); 
		int yEnd   = b.getYEnd();
		for(int yCoord = yStart; yCoord < yEnd; yCoord++)
		{
			drawScanLine(left, right, yCoord);
			left.step();
			right.step();
		}
	}
	 
	private void drawScanLine(Edge left, Edge right, int yCoord)
	{
		int xMin = (int)Math.ceil(left.getCurrentX());
		int xMax = (int)Math.ceil(right.getCurrentX());
		float xPrestep = xMin - left.getCurrentX();

//		float xDist = right.GetX() - left.GetX();
//		float texCoordXXStep = (right.GetTexCoordX() - left.GetTexCoordX())/xDist;
//		float texCoordYXStep = (right.GetTexCoordY() - left.GetTexCoordY())/xDist;
//		float oneOverZXStep = (right.GetOneOverZ() - left.GetOneOverZ())/xDist;
//		float depthXStep = (right.GetDepth() - left.GetDepth())/xDist;

		// Apparently, now that stepping is actually on pixel centers, gradients are
		// precise enough again.
//		float texCoordXXStep = gradients.GetTexCoordXXStep();
//		float texCoordYXStep = gradients.GetTexCoordYXStep();
//		float oneOverZXStep = gradients.GetOneOverZXStep();
//		float depthXStep = gradients.GetDepthXStep();
//		float lightAmtXStep = gradients.GetLightAmtXStep();
//
//		float texCoordX = left.GetTexCoordX() + texCoordXXStep * xPrestep;
//		float texCoordY = left.GetTexCoordY() + texCoordYXStep * xPrestep;
//		float oneOverZ = left.GetOneOverZ() + oneOverZXStep * xPrestep;
//		float depth = left.GetDepth() + depthXStep * xPrestep;
//		float lightAmt = left.GetLightAmt() + lightAmtXStep * xPrestep;

		for(int xCoord = xMin; xCoord < xMax; xCoord++)
		{
			//int index = i + j * GetWidth();
			//if(depth < m_zBuffer[index])
			//{
//				m_zBuffer[index] = depth;
//				float z = 1.0f/oneOverZ;
//				int srcX = (int)((texCoordX * z) * (float)(texture.GetWidth() - 1) + 0.5f);
//				int srcY = (int)((texCoordY * z) * (float)(texture.GetHeight() - 1) + 0.5f);

				renderContext.setPixel(xCoord, yCoord, (byte)0xff, (byte)0xff, (byte)0x00, (byte)0x00);
				//CopyPixel(i, j, srcX, srcY, texture, lightAmt);
			//}

//			oneOverZ += oneOverZXStep;
//			texCoordX += texCoordXXStep;
//			texCoordY += texCoordYXStep;
//			depth += depthXStep;
//			lightAmt += lightAmtXStep;
		}
	}
	
	
	
	/**
	 * Returns true if the triangle is left-handed or false if
	 * it is right-handed.
	 * 
	 * @param a the vertex with the lowest y position
	 * @param b the vertex with the largest y position
	 * @param c the vertex that lies between a and b regarding his y position
	 * @return true if the triangle is left-handed or false if
	 * it is right-handed.
	 */
	private static boolean isLeftHanded(Vertex a, Vertex b, Vertex c) {
			
		// calculate the triangle area*2	
		float x1 = b.getX() - a.getX();
		float y1 = b.getY() - a.getY();

		float x2 = c.getX() - a.getX();
		float y2 = c.getY() - a.getY();

		return (x1 * y2 - x2 * y1) >= 0;
	}

}
