package gui.plotDraw;

import graphbuilder.math.*;
import org.jdesktop.swingx.JXGraph;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D.Double;

public class NewtonAndIntegralGraphFrame extends JFrame {
	Expression z;
	double Xmin=-5;
	double Xmax=10;
	double Ymin=-5;
	double Ymax=10;
	public void setXmin(double xmin) {
		Xmin = xmin;
	}
	public void setXmax(double xmax) {
		Xmax = xmax;
	}
	public void setYmin(double ymin) {
		Ymin = ymin;
	}
	public void setYmax(double ymax) {
		Ymax = ymax;
	}
	public Expression getZ() {
		return z;
	}
	public void setZ(Expression z) {
		this.z = z;
	}	
	public NewtonAndIntegralGraphFrame() {
		
		setTitle("\u0413\u0440\u0430\u0444\u0438\u043A");
		setBounds(100, 100, 572, 582);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		final JXGraph graph = new JXGraph();
		graph.setMajorX(Xmax/10);
		graph.setMajorY(Ymax/10);
		graph.setMinorCountX(5);
		graph.setMinorCountY(5);
		graph.setView(new Double(Xmin, Ymin, Xmax, Ymax));
		final FuncMap fm = new FuncMap();
		fm.loadDefaultFunctions();
		final VarMap vm = new VarMap(false /* case sensitive */);
				vm.setValue("e", Math.E);
				vm.setValue("PI", Math.PI);
		graph.addPlots(Color.RED, new JXGraph.Plot() {
			public double compute(double arg0) {
				// TODO Auto-generated method stub
				vm.setValue("x", arg0);
				return z.eval(vm, fm);
			}
		});
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(graph);
		
		final JSlider slider = new JSlider();
		slider.setPreferredSize(new Dimension(200, 30));
		panel.add(slider, BorderLayout.NORTH);
		slider.setMaximum(50);
		slider.setMinimum(1);
		slider.setValue(10);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				setXmin((slider.getValue())*(slider.getValue()));
				setXmax(Xmin*2);
				setYmin((slider.getValue())*(slider.getValue()));
				setYmax(Ymin*2);
				graph.setMajorX(Xmin/5);
				graph.setMajorY(Ymin/5);
				graph.setView(new Double(-Xmin, -Ymin, Xmax, Ymax));
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent arg0) {
			}
		});
		
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewtonAndIntegralGraphFrame frame = new NewtonAndIntegralGraphFrame();
					frame.setVisible(true);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
