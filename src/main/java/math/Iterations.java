package math;

import graphbuilder.math.Expression;
import graphbuilder.math.FuncMap;
import graphbuilder.math.VarMap;
import java.util.ArrayList;

public class Iterations {

	private Expression z;
	private VarMap vm = new VarMap();
	private FuncMap fm = new FuncMap();
	String Iterations = "";
int iter[];
	public String getIterations() {
		return Iterations;
	}

	private double h = 0.001, c,e=0.001;
	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

	private ArrayList<Double> a = new ArrayList<Double>();
	private ArrayList<Double> b = new ArrayList<Double>();
	
	public void findAB() {
		this.a.clear();
		this.b.clear();
		int k=1;
		for (double i = -100; i < 100; i += 0.5) {
			if (y(i) * y(i + 0.5) < 0) {				
				this.a.add(i);
				this.b.add(i + 0.5);
				Iterations+="<br>Интервал "+k+" = ["+i+";"+(i+0.5)+"]";k++;
			}
			if (y(i) * y(i + 0.5) == 0) {
				this.a.add(i - 0.25);
				this.b.add(i + 0.75);
				Iterations+="<br>Интервал "+k+" = ["+(i - 0.25)+";"+(i + 0.75)+"]";k++;
				i+= 0.5;
			}
		}
	}

	public Iterations(Expression z) {
		this.z = z;
		Iterations = "<h2><center>Pешение нелинейного уравнения с помощью метода простых итераций</center></h2>";
		Iterations+="Задано уравнение"+z.toString();
		Iterations+="<br>Находим интервалы в которых находятся корни:";
		findAB();
	}
	
	public Iterations(Expression z, double[] a, double[] b) {
		this.z = z;
		Iterations = "<h2><center>Pешение нелинейного уравнения с помощью метода простых итераций</center></h2>";
		Iterations+="<br><h3>Задано уравнение f(x) = "+z.toString()+"</h3>";
		Iterations+="<br><h3>Заданы интервалы,в которых требуется искать корни:</h3>";
		for (int i = 0; i < a.length; i++) {
			Iterations+="<br>Интервал "+(i+1)+" = ["+a[i]+";"+b[i]+"]";
			this.a.add(a[i]);
			this.b.add(b[i]);
		}
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}
	public double getEps() {
		return e;
	}

	public void setEps(double e) {
		this.e = e;
	}

	public ArrayList<Double> getA() {
		return a;
	}

	public double getAi(int i) {
		return this.a.get(i);
	}

	public void setA(double[] a) {
		this.a.clear();
		for (double anA : a) this.a.add(anA);
	}

	public ArrayList<Double> getB() {
		return b;
	}

	public double getBi(int i) {
		return this.b.get(i);
	}

	public void setB(double[] b) {
		this.b.clear();
		for (double aB : b) this.b.add(aB);
	}

	public Expression getZ() {
		return z;
	}

	public void setZ(Expression z) {
		this.z = z;
	}

	public VarMap getVm() {
		vm.setValue("pi", Math.PI);
		vm.setValue("e", Math.E);
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
		fm.loadDefaultFunctions();
		return getZ().eval(getVm(), fm);
	}
	
	public double fi(double x){
		setX(x);		
		return x + getC() * y(x);
	}
	
	public double fiDeriv(double x){
		System.out.println("fideriv" + (fi(x + h) - fi(x - h)) / (2 * h));
		return (fi(x + h) - fi(x - h)) / (2 * h);
	}

	// находит производную функции в точке
	public double deriv(double x) {
		System.out.println("deriv" + x + (y(x + h) - y(x - h)) / (2 * h));
		return (y(x + h) - y(x - h)) / (2 * h);
	}
	private boolean checkDeriv(double a, double b) {		
		if (Math.abs(fiDeriv(a)) < 1 && Math.abs(fiDeriv((a + b) / 2)) < 1
				&& Math.abs(fiDeriv(b)) < 1)
			return true;
		else
			return false;
	}
	private double findC(double a, double b) {
		if(Math.abs(deriv(a)) > Math.abs(deriv(b)))
			return (1 / Math.abs(deriv(a)));
		else
			return 1 / Math.abs(deriv(b));
	}
	public double findRoot(double x0) {
		double x1 = x0 + c * y(x0);
		if (Math.abs(Math.abs(x1) - Math.abs(x0)) >= getEps())
			findRoot(x1);
		
		return x1;
	}
	public double[] solve() {
		iter = new int[getA().size()];
		double[] x = new double[getA().size()];
		for (int i = 0; i < getA().size(); i++) {
			setC(findC(getAi(i), getBi(i)));
		//	Iterations+="<br>Проверяем сходимость функции на интервале [ "+getAi(i)+" ; "+getBi(i)+" ]";
			if (!checkDeriv(getAi(i), getBi(i))) {
			//	Iterations+="<br>Не выполняются условия сходимости";
				System.out.println("Не выполняются условия сходимости");
				//return x;
			}	
			
			Iterations+="<br><br><h3>Ищем корень на интервале [ "+getAi(i)+" ; "+getBi(i)+" ]</h3>";
			Iterations+="<br>Выберем приближенное значение корня X0 = "+((getAi(i) + getBi(i)) / 2);
			x[i] = findRoot((getAi(i) + getBi(i)) / 2);
			Iterations+="<br>Найденный корень = "+x[i];
		}
		Iterations+="<br><h3>Решение:</h3>";
		for (int i = 0; i < x.length; i++){
			Iterations+="<br>X"+(i+1)+" = "+x[i];
			}
		return x;
		}
	public static void main(String[] args) {
	}
}
