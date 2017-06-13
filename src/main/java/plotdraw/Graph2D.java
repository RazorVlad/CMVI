package plotdraw;

import keypoint.PngEncoder;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;

public class Graph2D extends JPanel {
	private static final long serialVersionUID = -5622357644343886888L;

	Axis xAxis = new Axis(-8, 8), yAxis = new Axis(-8, 8);

	double xcenter, ycenter, xscale, yscale, xmin, xmax;

	int width, height, semiHeight, semiWidth;

	Graphics2D graph;

	Image buffer;

	public static class Axis {
		public double min = 0, max = 0;

		double step = -1;

		public Axis() {
		}

		public Axis(double min, double max) {
			this.min = min;
			this.max = max;
		}

		public Axis(double min, double max, double step) {
			this.min = min;
			this.max = max;
			this.step = step;

		}

		public double getWidth() {
			return max - min;
		}

		public void setStep(double step) {
			this.step = step;
		}

		public double getStep(double scale) {
			if (step < 0) {
				step = Math.exp(Math.round((Math.log(getWidth() / 20) / Math
						.log(10)))
						* Math.log(10));
				while (step * scale < 24)
					step *= 2;
			}
			return step;
		}
	}

	public void resizeBuffer() {		
		buffer = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);
		graph = (Graphics2D) buffer.getGraphics();		
	}

	public void prepareScale() {
		width = getWidth();
		height = getHeight();
		semiHeight = height / 2;
		semiWidth = width / 2;
		xscale = width / xAxis.getWidth();
		yscale = height / yAxis.getWidth();
		xcenter = (xAxis.max + xAxis.min) / 2.0;
		ycenter = (yAxis.max + yAxis.min) / 2.0;
		xmin = screen2x(0);
		xmax = screen2x(width);
	}

	public void clear() {
		resizeBuffer();
		prepareScale();

		graph.setColor(Color.white);
		graph.fillRect(0, 0, width, height);
		// draw grid
		graph.setColor(Color.lightGray);
		double xStep = xAxis.getStep(xscale);
		for (double dx = (Math.ceil(xAxis.min / xStep) - 1) * xStep; dx <= xAxis.max; dx += xStep) {
			int x = x2screen(dx);
			graph.drawLine(x, 0, x, height);
		}
		double yStep = yAxis.getStep(yscale);
		for (double dy = (Math.ceil(yAxis.min / yStep) - 1) * yStep; dy <= yAxis.max; dy += yStep) {
			int y = y2screen(dy);
			graph.drawLine(0, y, width, y);
		}
		// draw axes
		graph.setColor(Color.black);
		Stroke startStroke = graph.getStroke();
		graph.setStroke(new BasicStroke(2.0f));
		graph.drawLine(0, y2screen(0), width, y2screen(0));
		graph.drawLine(x2screen(0), 0, x2screen(0), height);
		graph.setStroke(startStroke);
	}

	public void drawTips() {
		graph.setColor(Color.black);
		// draw grid
		double xStep = xAxis.getStep(xscale);
		for (double dx = (Math.ceil(xAxis.min / xStep) - 1) * xStep; dx <= xAxis.max; dx += xStep) {
			int x = x2screen(dx);
			graph
					.drawString(NumberFormat.getNumberInstance().format(dx),
							x + 2, 20);
		}
		double yStep = yAxis.getStep(yscale);
		for (double dy = (Math.ceil(yAxis.min / yStep) - 1) * yStep; dy <= yAxis.max; dy += yStep) {
			int y = y2screen(dy);
			graph.drawString(NumberFormat.getNumberInstance().format(dy), 2, y - 2);
		}
		graph.drawString("X", width - 10, y2screen(0));
		graph.drawString("Y", x2screen(0) + 2, 10);
	}

	

	/*public Polygon toCoords(FloatPoint[] points) {
		int nPoint = points.length;
		int[] xs = new int[nPoint], ys = new int[nPoint];
		for (int i = 0; i < nPoint; ++i) {
			ys[i] = y2screen(points[i].y);
			xs[i] = x2screen(points[i].x);			
		}
		return new Polygon(xs, ys, nPoint);
	}

	public void drawPoints(FloatPoint[] points, int w, int h) {		
		if (points == null || points.length == 0)
			return;
		int l = w / 2, r = w - l;
		Polygon poly = toCoords(points);
		for (int i = 0; i < poly.npoints; ++i) 
			graph.fillArc(poly.xpoints[i] - l, poly.ypoints[i] - r, w, h, 0, 360);		
	}

	public void drawPolygon(FloatPoint[] points) {
		if (points == null || points.length == 0)
			return;
		Polygon poly = toCoords(points);
		graph.drawPolygon(poly);
	}

	public void fillPolygon(FloatPoint[] points) {
		if (points == null || points.length == 0)
			return;
		Polygon poly = toCoords(points);
		graph.fillPolygon(poly);
	}
	
	public void drawPolyline(FloatPoint[] points) {
		if (points == null || points.length == 0)
			return;
		Polygon poly = toCoords(points);
		graph.drawPolyline(poly.xpoints, poly.ypoints, poly.npoints);
	}*/

	public int x2screen(double x) {
		return (int) Math.round(semiWidth + (x - xcenter) * xscale);
	}

	public int y2screen(double y) {
		return (int) Math.round(semiHeight - (y - ycenter) * yscale);
	}

	public double screen2x(double x) {
		return ((x - 0 - semiWidth) / xscale + xcenter);
	}

	public double screen2y(double y) {
		return ((semiHeight - (y - 0)) / yscale + ycenter);
	}

	public void drawLine(double a, double b, double c) {
		/*
		 * if (i >= task.getLimN()) c->Pen->Style = psDash;
		 */
		if (b == 0) { // vertical line
			if (a == 0)
				return;
			graph.drawLine(x2screen(c / a), 0, x2screen(c / a), height);
		} else {
			int yhigh = y2screen((c - a * xmax) / b), ylow = y2screen((c - a
					* xmin)
					/ b);
			if ((yhigh < 0 && ylow < 0) || (yhigh > height && ylow > height))
				return;
			graph.drawLine(0, ylow, width, yhigh);
		}
	}

/*	public void drawLine(LineEquation eqn) {
		drawLine(eqn.a, eqn.b, eqn.c);
	}*/

	public Axis getXAxis() {
		return xAxis;
	}

	public void setXAxis(Axis axis) {
		if (axis != null)
			xAxis = axis;
	}

	public Axis getYAxis() {
		return yAxis;
	}

	public void setYAxis(Axis axis) {
		if (axis != null)
			yAxis = axis;
	}
	public void saveToFile(String fileName) throws IOException {	
		PngEncoder enc = new PngEncoder(buffer);
		enc.setCompressionLevel(9);
		FileOutputStream fw = new FileOutputStream(fileName);
		fw.write(enc.pngEncode());
		fw.close();		
	}

	public Graphics2D getGraph() {
		return graph;
	}

	public Image getBuffer() {
		return buffer;
	}
}
