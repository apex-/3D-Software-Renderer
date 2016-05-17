package renderer.program;

import javax.swing.plaf.synth.SynthSeparatorUI;

import renderer.display.Display;
import renderer.display.RenderContext;
import renderer.geometry.Vertex;
import renderer.rasterize.TriangleRasterizer;
import renderer.test.DirectDrawPanel;

public class Main {

	public static void main(String[] args) {
		
		Display display = new Display(1280, 720, "3D Rendering Demo");
		int nRenderedImages = 0;
		int totalRenderTime = 0;
		
		float shift = 1.1f;
		float xpos = 300.0f;
		
		while(true) {
			long t1 = System.currentTimeMillis();
			display.clear();
			RenderContext renderContext = display.getRenderContext();
			
			// here come the draw calls
//			for (int y = 100; y<200; y++) {
//				for (int x= 100; x<200; x++) {
//					renderContext.setPixel(x, y, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff);
//				}
//			}
			
			xpos += Math.sin(shift)*10;
			shift+=0.1;
			Vertex v1 = new Vertex(1200, 100, 0, 1);
			Vertex v2 = new Vertex(xpos, 300, 0, 1);
			Vertex v3 = new Vertex(300, 500, 0, 1);
			
			TriangleRasterizer s = new TriangleRasterizer(display);

			s.drawTriangle(v1, v2, v3);
			
			display.updateAndRender();
			
			nRenderedImages ++;
			long t2 = System.currentTimeMillis();
			totalRenderTime += (t2-t1);
			if (nRenderedImages % 100 == 0) {
				float averageRenderTime = (float) (totalRenderTime/100.0);
				float fps = 1000/averageRenderTime;
				System.out.println("Average render time: " + averageRenderTime + " ms" + ", " + fps + " average FPS");
				totalRenderTime = 0;
			}
		}
		
	}

}
