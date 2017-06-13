package gui;

import graphbuilder.math.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.Color.*;

public class CopyOfGraf extends JFrame {
	Expression z;
	/**
	 * @return the z
	 */
	public Expression getZ() {
		return z;
	}
	/**
	 * @param z the z to set
	 */
	public void setZ(Expression z) {
		this.z = z;
	}
	public CopyOfGraf() {
		
		setTitle("\u0413\u0440\u0430\u0444\u0438\u043A");
		setBounds(100, 100, 521, 582);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		final JPanel panel_1 = new JPanel();
		panel_1.setBounds(2, 42, 500, 500);
		panel.add(panel_1);
		
		
		final JSlider slider = new JSlider();
		slider.setMinimum(1);
		slider.setMaximum(20);
		slider.setValue(1);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Graphics g = panel_1.getGraphics();
				Graf_paint(slider.getValue(),g,getZ());
			}
		});
		slider.setBounds(2, 11, 500, 23);
		panel.add(slider);
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent arg0) {
				Graphics g = panel_1.getGraphics();
				Graf_paint(slider.getValue(),g,getZ());
			}
		});
	}
	protected void Graf_paint(int stp, Graphics g, Expression z){
			int scale=1;
			int xStep=stp;
			int yStep = xStep; // shag X.
			int xMax=250/xStep;	//max znach x
			int yMax=250/yStep;	//max znach y
			int xMin=-250/xStep;	//min znach x
			int yMin=-250/yStep;	//min znach y
			int xScale = getWidth()/(xMax - xMin);
			int yScale = getHeight()/(yMax - yMin);
			int x0Graph = 250;
			int y0Graph = 250;
			
			showEmptyGraph(g);
			
			// risovanie setki
			for (double dx =  25, i=1; dx <= 250; dx += 25, i++) {
		    	int x = 250 + (int)(dx * scale);
		    	g.setColor(lightGray);
		    	g.drawLine(x, 0, x, getHeight());
		    	g.setColor(black);
		    	g.drawString((int)i*stp + "", x-20, 240);
		    }
		    for (double dx =  25, i=1; dx <= 250; dx += 25, i++) {
		    	int x = 250 - (int)(dx * scale);
		    	g.setColor(lightGray);
		    	g.drawLine(x, 0, x, getHeight());
		    	g.setColor(black);
		    	g.drawString((int)-stp*i + "", x+5 , 273);
		    	
		    }
		    for (double dy = 25, i=1; dy <= 250; dy += 25, i++) {
		    	int y = 250 + (int)(dy * scale);
		    	g.setColor(lightGray);
		    	g.drawLine(0, y, getWidth(), y);
		    	g.setColor(black);
		    	g.drawString((int)-i*stp + "", 230, y - 2);
		    }
		    for (double dy = 25, i=1; dy <= 250; dy += 25, i++) {
		    	int y = 250 - (int)(dy * scale);
		    	g.setColor(lightGray);
		    	g.drawLine(0, y, getWidth(), y);
		    	g.setColor(black);
		    	g.drawString((int)i*stp + "", 255, y + 16);
		    }
			// osi
		    g.setColor(black);
		    g.drawLine(250, 0, 250, 500);
		    g.drawLine(0, 250, 500, 250);
		    g.drawString("0.0", 230, 243);
		    g.drawString("X", 500 - 15, 265);
		    g.drawString("Y", 235, 15);
		   // ������
			g.setColor(blue);
			VarMap vm = new VarMap(false /* case sensitive */);
			vm.setValue("pi", Math.PI);
			vm.setValue("e", Math.E);
			vm.setValue("x", -stp*10);
			
			FuncMap fm = new FuncMap();
			fm.loadDefaultFunctions();
			System.out.println(z); 
			double xOldGraph2 = -stp*1000; // ���������� ������ ������ �� func.getMinX() 
			double yOldGraph2 = (int) (y0Graph - z.eval(vm, fm)*25*stp);
			double xGraph2 = xOldGraph2+1; // ����� ���������� � ����� �� 1 , ���� x <= func.getMaxX()  
			vm.setValue("x", xGraph2);
			int yGraph2;
			while (xGraph2<=stp*1000)
			{
				yGraph2 = (int) (y0Graph - z.eval(vm, fm)*25*stp);
				if(yOldGraph2!= 0){
				g.drawLine((int)xOldGraph2*25+(int)x0Graph, (int)yOldGraph2, (int)xGraph2*25+x0Graph, (int)yGraph2);}
				xOldGraph2 = xGraph2;
				yOldGraph2 = yGraph2;
				vm.setValue("x", xGraph2+1);
				xGraph2+=1;
				
			}
			// 
			/*int xOldGraph2 = xMin; // ���������� ������ ������ �� func.getMinX() 
			int yOldGraph2 = (int) (y0Graph - z.eval(vm, fm)*yScale);
			int xGraph2 = xOldGraph2+1; // ����� ���������� � ����� �� 1 , ���� x <= func.getMaxX()  
			vm.setValue("x", xGraph2);
			int yGraph2;
			while (xGraph2<=xMax)
			{
				yGraph2 = (int) (y0Graph - z.eval(vm, fm)*yScale);
				if(yOldGraph2!= 0){
				g.drawLine(xOldGraph2*xScale+x0Graph, yOldGraph2, xGraph2*xScale+x0Graph, yGraph2);}
				xOldGraph2 = xGraph2;
				yOldGraph2 = yGraph2;
				vm.setValue("x", xGraph2+1);
				xGraph2++;
				
			}*/
	 }
	
	private void showEmptyGraph(Graphics g) {
		g.setColor(Color.white);
	    g.fillRect(0, 0, getWidth(), getHeight());
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Graf frame = new Graf();
					frame.setVisible(true);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
