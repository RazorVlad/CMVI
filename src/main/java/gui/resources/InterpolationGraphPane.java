package gui.resources;

import static java.awt.Color.*;

import java.awt.Graphics;
import java.awt.Image;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JSlider;

import keypoint.PngEncoder;

import math.Lagrange;



public class InterpolationGraphPane extends JPanel {
	//Expression z;
	double[] xy;
	double[] yx;
	Image buffer;
	public double[] getXY() {
		return xy;
	}
	public void setXY(double[] xy) {
		this.xy = xy;
	}
	public double[] getYX() {
		return yx;
	}
	public void setYX(double[] yx) {
		this.yx = yx;
	}

	public InterpolationGraphPane() {
		setBounds(100, 100, 521, 582);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		final JPanel panel_1 = new JPanel();
		panel_1.setBounds(2, 42, 500, 500);
		panel.add(panel_1);
		final JSlider slider = new JSlider();
		slider.setMinimum(1);
		slider.setMaximum(20);
		slider.setValue(5);
		slider.setBounds(2, 11, 500, 23);
		panel.add(slider);
	}

	public void Graf_paint(Graphics g){
		Lagrange l = new Lagrange(xy,yx);

		int xStep = 1; // Крок сітки по X. Змінюємо крок, якщо це потрібно:
		int yStep = xStep; // shag X.
		double mxx=xy[0];
		double xMax=Math.abs(xy[0]);
		double mnx=xy[0];
		for(int i=0;i<xy.length;i++){
			if(Math.abs(xy[i])>xMax)xMax=Math.abs(xy[i]);
		}
		for(int i=0;i<xy.length;i++){
			if(xy[i]>mxx)mxx=(xy[i]);
		}
		for(int i=0;i<xy.length;i++){
			if(xy[i]<mnx)mnx=xy[i];
		}	//max znach x
		//max znach y
		xMax+=1;
		double xMin=-1*xMax;	//min znach x
		//min znach y
		double xScale = getWidth()/(xMax - xMin);

		double x0Graph = 170;
		double y0Graph = 170;
		double h = (mxx*2)/100;

		double yMax=Math.abs(yx[0]);
		for(int i=0;i<yx.length;i++){
			if(Math.abs(yx[i])>yMax)yMax=Math.abs(yx[i]);
		}
		for(double d = mnx;d<mxx;d+=h){
			if(Math.abs(l.getLagrY(d))>yMax)yMax=Math.abs(l.getLagrY(d));
		}
		yMax+=1;
		double yMin=-1*yMax;
		double yScale = getHeight()/(yMax*2);
		showEmptyGraph(g);
		// Малювання сітки
		while (xStep * xScale < 12)
			xStep *= 2;
		for (double dx = 1*xStep; dx < xMax; dx += 1*xStep) {
			int x = (int) (x0Graph + (dx * xScale));
			g.setColor(lightGray);
			g.drawLine(x, 0, x, getHeight());
			g.setColor(black);
			g.drawString((int)dx + "", x + 2, 165);
		}
		for (double dx = -1*xStep; dx > xMin; dx -= 1*xStep) {
			int x = (int) (x0Graph + (dx * xScale));
			g.setColor(lightGray);
			g.drawLine(x, 0, x, getHeight());
			g.setColor(black);
			g.drawString((int)dx + "", x + 2, 165);
		}

		// double yStep = 1;  // Крок сітки по Y
		while (yStep * yScale < 12)yStep *=2;

		for (double dy = 1*yStep; dy < yMax; dy +=1*yStep) {
			int y = (int) (y0Graph - (dy* yScale));
			g.setColor(lightGray);
			g.drawLine(0, y, getWidth(), y);
			g.setColor(black);
			g.drawString(dy + "", 172, y - 2);
		}
		for (double dy = -1*yStep; dy > yMin; dy -= 1*yStep) {
			int y = (int) (y0Graph - (dy* yScale));
			g.setColor(lightGray);
			g.drawLine(0, y, getWidth(), y);
			g.setColor(black);
			g.drawString(dy + "", 172, y - 2);
		}

		// Вісі:
		g.setColor(black);
		g.drawLine((int)x0Graph, 0, (int)x0Graph, getHeight());
		g.drawLine(0, (int)y0Graph, getWidth(), (int)y0Graph);
		g.drawString("0.0", 172, (int)y0Graph - 2);
		g.drawString("X", getWidth() - 10, (int)y0Graph - 2);
		g.drawString("Y", (int)x0Graph + 2, 10);
		// график f(x):
		g.setColor(red);
		int xOldGraph = (int)Math.ceil(x0Graph+xScale * (mnx)); // округление всегда вправо от func.getMinX()
		int yOldGraph = (int) (y0Graph - ((l.getLagrY(mnx) * yScale)));
		int xGraph = xOldGraph; // будет возрастать в цикле на 1 , пока x <= func.getMaxX()
		double x = mnx;
		while (x <= mxx)
		{
			int yGraph = (int) (y0Graph - (l.getLagrY(x) * yScale));
			g.drawLine(xOldGraph, yOldGraph, xGraph, yGraph);
			xOldGraph = xGraph;
			yOldGraph = yGraph;
			xGraph++;
			x = (xGraph-x0Graph)/xScale; // пересчитать из xGraph в x
		}
		//точки
		for(int i=0;i<xy.length;i++){
			g.setColor(BLUE);
			g.fillOval((int)(xy[i] * xScale+169-3), (int)(y0Graph - l.getLagrY(xy[i])* yScale-3), 7, 7);
			g.setColor(white);
			g.fillOval((int)(xy[i] * xScale+169-2), (int)(y0Graph - l.getLagrY(xy[i])* yScale-2), 5, 5);
		}
	}
	public void saveToFile(String fileName) throws IOException {
		PngEncoder enc = new PngEncoder(buffer);
		enc.setCompressionLevel(9);
		FileOutputStream fw = new FileOutputStream(fileName);
		fw.write(enc.pngEncode());
		fw.close();
	}
	private void showEmptyGraph(Graphics g) {
		g.setColor(java.awt.Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	public static void main(String[] args) {
	}
}
