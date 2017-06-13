package gui;

import graphbuilder.math.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.Color.*;

public class PanelG extends JFrame {
	Expression z;

	/**
	 * @return the z
	 */
	public Expression getZ() {
		return z;
	}

	/**
	 * @param z
	 *            the z to set
	 */
	public void setZ(Expression z) {
		this.z = z;
	}

	double[] xy;
	double[] yx;

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
	final JSlider slider = new JSlider();
	final JSlider slider_1 = new JSlider();
	final JPanel panel_1 = new JPanel();
	public PanelG() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Graphics g = panel_1.getGraphics();
				if (getMaximum() !=0 && getMaximum()>0.001)Graf_paint((int)(getMaximum()+0), slider_1.getValue(), g, getXY(),getYX());
				else Graf_paint(slider.getValue(), slider_1.getValue(), g, getXY(),
						getYX());
			}
		});

		setTitle("\u0413\u0440\u0430\u0444\u0438\u043A");
		setBounds(100, 100, 572, 582);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(panel_1);

		
		slider_1.setMaximum(1000);
		slider_1.setValue(5);

		
		slider.setMaximum(1000);
		slider.setMinimum(1);
		slider.setValue(5);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Graphics g = panel_1.getGraphics();
				Graf_paint(slider.getValue(), slider_1.getValue(), g, getXY(),
						getYX());
			}
		});
		panel.add(slider, BorderLayout.NORTH);

		slider_1.setMinimum(1);
		slider_1.setOrientation(SwingConstants.VERTICAL);
		slider_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Graphics g = panel_1.getGraphics();
				Graf_paint(slider.getValue(), slider_1.getValue(), g, getXY(),
						getYX());
			}
		});
		panel.add(slider_1, BorderLayout.EAST);

		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent arg0) {
				Graphics g = panel_1.getGraphics();
				Graf_paint(slider.getValue(), slider_1.getValue(), g, getXY(),
						getYX());
			}
		});
	}
	Double Maximum =0.0;
	public Double getMaximum() {
		return Maximum;
	}

	public void setMaximum(double Maximum){
		this.Maximum=Maximum+Maximum/10;
	}


	protected void Graf_paint(int xstp, int ystp, Graphics g, double[] xy,
			double[] yx) {
		//System.out.println(xstp + " " + ystp + " " + xy[0] + " " + yx[0]);
		// int scale=1;
		// int xStep=stp;
		// Lagrange l = new Lagrange(xy,yx);
		double xStep = 0.0001; // ���� ���� �� X. ������� ����, ���� �� �������:
		double yStep = xStep; // shag X.
		double xMax = xstp+xstp/10; // max znach x
		if (getMaximum() !=0 && getMaximum()>0.001){xMax=getMaximum();setMaximum(0.0);}
		// max znach y
		double xMin = -1 * xMax; // min znach x
		// min znach y
		double xScale = getWidth() / (xMax - xMin);

		double x0Graph = getWidth()/2 ;//250;
		double y0Graph = getHeight()/2;//250;
		// double h = (mxx*2)/100;

		double yMax = ystp+ystp/10;
		double yMin = -1 * yMax;
		double yScale = getHeight() / (yMax * 2);
		showEmptyGraph(g);
		// ��������� ����
		while (xStep * xScale < 15)
			xStep *= 10;
		double dx1;
		for (double dx = 1 * xStep; dx < xMax; dx += 1 * xStep) {
			int x = (int) (x0Graph + (dx * xScale));
			g.setColor(lightGray);
			g.drawLine(x, 0, x, getHeight());
			g.setColor(black);
			dx1 = Math.round(dx * 10);
			g.drawString(dx1 / 10 + "", x + 2, getHeight()/2-10);
		}
		for (double dx = -1 * xStep; dx > xMin; dx -= 1 * xStep) {
			int x = (int) (x0Graph + (dx * xScale));
			g.setColor(lightGray);
			g.drawLine(x, 0, x, getHeight());
			g.setColor(black);
			dx1 = Math.round(dx * 10);
			g.drawString(dx1 / 10 + "", x + 2, getHeight()/2-10);
		}

		// double yStep = 1; // ���� ���� �� Y
		while (yStep * yScale < 12)
			yStep *= 10;
		double dy1;
		for (double dy = 1 * yStep; dy < yMax; dy += 1 * yStep) {
			int y = (int) (y0Graph - (dy * yScale));
			g.setColor(lightGray);
			g.drawLine(0, y, getWidth(), y);
			g.setColor(black);
			dy1 = Math.round(dy * 10);
			g.drawString(dy1 / 10 + "", getWidth()/2+5, y - 2);
		}
		for (double dy = -1 * yStep; dy > yMin; dy -= 1 * yStep) {
			int y = (int) (y0Graph - (dy * yScale));
			g.setColor(lightGray);
			g.drawLine(0, y, getWidth(), y);
			g.setColor(black);
			dy1 = Math.round(dy * 10);
			g.drawString(dy1 / 10 + "", getWidth()/2+5, y - 2);
		}

		// ³�:
		g.setColor(black);
		g.drawLine((int) x0Graph, 0, (int) x0Graph, getHeight());
		g.drawLine(0, (int) y0Graph, getWidth(), (int) y0Graph);
		g.drawString("0.0", getWidth()+5, (int) y0Graph - 2);
		g.drawString("X", getHeight()-10, getWidth()+15);
		g.drawString("Y", getWidth()/2-10, 10);
		// ������ f(x):
		g.setColor(red);
		System.out.println(xy.length-1);
		for (int i = 0; i < xy.length-1; i++) {

			g.drawLine((int) (x0Graph+xy[i] * xScale),     (int) (y0Graph - yx[i] * yScale),
					   (int) (x0Graph+xy[i + 1] * xScale), (int) (y0Graph - yx[i +1]* yScale));
			System.out.println((xy[i] * xScale)+" "+(y0Graph - yx[i] * yScale));
			System.out.println((xy[i+1] * xScale)+" "+(y0Graph - yx[i+1] * yScale));
			// x = (xGraph-x0Graph)/xScale; // ����������� �� xGraph � x
		}
	}

	private void showEmptyGraph(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// CopyOf
					Graf frame = new Graf();// CopyOfGraf();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
