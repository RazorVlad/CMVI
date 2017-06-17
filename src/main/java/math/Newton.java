package math;

import graphbuilder.math.*;

import java.util.ArrayList;

public class Newton {//Метод Ньютона(касательных)

	private Expression z;
	private VarMap vm = new VarMap();
	private FuncMap fm = new FuncMap();
	private double h = 0.001;
	private String newton="";
	private ResourceBundle bundle;
	public String getNewton() {
		return newton;
	}

	public String Newton;
	private ArrayList<Double> a = new ArrayList<Double>();
	private ArrayList<Double> b = new ArrayList<Double>();

	public Newton(Expression z, double[] a, double[] b,ResourceBundle bundle) {
		this.z = z;
		this.bundle=bundle;
		newton = "<h2><center>"
	//	+"Pешение нелинейного уравнения с помощью метода Ньютона"
		+bundle.getString("report.solveNewton")
		+"</center></h2>";
		newton+="<h2>"
		//+"Задано уравнение"
		+bundle.getString("report.definedExpression")
		+z.toString()+"</h2>";
		newton+="<br /><h2>"
		//+"Заданы интервалы,в которых требуется искать корни"
		+bundle.getString("report.definedRootIntervals")
		+":</h2>";
		for (int i = 0; i < a.length; i++) {
			newton+="<br />"
			//+"Интервал"
			+bundle.getString("report.interval")
			+" "+(i+1)+" = ["+a[i]+";"+b[i]+"]";
			this.a.add(a[i]);
			this.b.add(b[i]);
		}

	}

	public Newton(Expression z) {
		this.z = z;
		newton = "<h2><center>"
		//+"Pешение нелинейного уравнения с помощью метода Ньютона"
		+bundle.getString("report.solveNewton")
		+"</center></h2>";
		newton+=bundle.getString("report.definedExpression")
	//	"Задано уравнение"
		+z.toString();
		newton+="<br />"
		//+"Находим интервалы в которых находятся корни:";
		+bundle.getString("report.searchRootIntervals");
		findAB();
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public ArrayList<Double> getA() {
		return a;
	}

	public double getAi(int i) {
		return this.a.get(i);
	}

	public void setA(double[] a) {
		this.a.clear();
		for (int i = 0; i < a.length; i++) 
			this.a.add(a[i]);
	}

	public ArrayList<Double> getB() {
		return b;
	}

	public double getBi(int i) {
		return this.b.get(i);
	}

	public void setB(double[] b) {
		this.b.clear();
		for (int i = 0; i < b.length; i++) 
			this.b.add(b[i]);
	}

	public Expression getZ() {
		return z;
	}

	public void setZ(Expression z) {
		this.z = z;
	}

	public VarMap getVm() {
		return vm;
	}

	public void setVm(VarMap vm) {
		this.vm = vm;
	}

	public FuncMap getFm() {
		fm.loadDefaultFunctions();
		return fm;
	}

	public void setFm(FuncMap fm) {
		this.fm = fm;
	}

	// переопределяет икс в VarMap
	public void setX(double x) {
		getVm().setValue("x", x);
	}

	// возвращает значение функции
	public double y(double x) {
		setX(x);
		// System.out.println("y(x): " + getZ().eval(getVm(), getFm()));
		return getZ().eval(getVm(), getFm());
	}

	// находит производную функции в точке
	public double deriv(double x) {
		// System.out.println("deriv x0: " + (y(x + h) - y(x - h)) / (2 * h));
		return (y(x + h) - y(x - h)) / (2 * h);
	}

	public void findAB() {
		this.a.clear();
		this.b.clear();
		double k=1;
		for (double i = -100; i < 100; i += 0.5) {
			if (y(i) * y(i + 0.5) < 0) {				
				this.a.add(i);
				this.b.add(i + 0.5);
				newton+="<br />"
				//+"Интервал"
				+bundle.getString("report.interval")
				+" "+k+" = ["+i+";"+(i+0.5)+"]";k++;
			}
			if (y(i) * y(i + 0.5) == 0) {
				this.a.add(i - 0.25);
				this.b.add(i + 0.75);
				newton+="<br>"
				//+"Интервал"
				+bundle.getString("report.interval")
				+" "+k+" = ["+(i - 0.25)+";"+(i + 0.75)+"]";k++;
			}
		}
	}

	public double findRoot(double x0) {
		// System.out.println("начали уточнять корень");
		// System.out.println("начальный x0: " + x0);
		newton+="<br />"
		//+"Выберем приближенное значение корня"
		+bundle.getString("report.chooseStartRoot")
		+" X0 = " + x0;
		double x1 = x0 - (y(x0) / deriv(x0));
		// System.out.println("начальный x1: " + x1);
		
		do {
			x0 = x1;
			// System.out.println("x0: " + x0);
			x1 = x0 - (y(x0) / deriv(x0));
			// System.out.println(x1);
		} while (Math.abs(Math.abs(x1) - Math.abs(x0)) >= getH());
		newton+="<br />"
		//+"Уточненный корень"
		+bundle.getString("report.accurRoot")
		+" X = " + x1;
		return x1;
	}

	public double[] solve() {
		//newton = "<h2><center>Pешение нелинейного уравнения с помощью метода Ньютона</center></h2>";
		//newton+="Задано уравнение"+z.toString();
		vm.setValue("pi", Math.PI);
		vm.setValue("e", Math.E);
		fm.loadDefaultFunctions();
		double[] x = new double[getA().size()];
		for (int i = 0; i < getA().size(); i++){
			newton+="<br /><h3>"
			//+"Ищем корень на интервале"
			+bundle.getString("report.searchRootInTheInterval")
			+" [ "+getAi(i)+" ; "+getBi(i)+" ]</h3>";
			x[i] = findRoot((getAi(i) + getBi(i)) / 2);}
		// x[i] = findRoot(-0.1);
		newton+="<br /><h3>"
	//	+"Решение"
		+bundle.getString("report.solution")
		+":</h3>";
		for (int i = 0; i < x.length; i++){
			newton+="<br />X"+(i+1)+" = "+x[i];
			}
		return x;
	}

	public static void main(String[] args) {

	}

}
