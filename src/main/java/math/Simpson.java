package math;

import graphbuilder.math.Expression;
import graphbuilder.math.FuncMap;
import graphbuilder.math.VarMap;

//интегрирование методом Симпсона (парабол)
public class Simpson {

	private double eps = 0.001, a, b;
	private Expression z;
	private VarMap vm = new VarMap();
	private FuncMap fm = new FuncMap();
	String Simpson="";

	public String getSimpson() {
		return Simpson;
	}

	public Simpson(Expression z, double a, double b) {
		this.z = z;
		this.a = a;
		this.b = b;
	}

	public double getEps() {
		return eps;
	}

	public void setEps(double eps) {
		this.eps = eps;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
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

	public double y(double x) {
		setX(x);
		fm.loadDefaultFunctions();
		return getZ().eval(getVm(), fm);
	}

	// собственно вычисляет интеграл
	public double integral() {
		Simpson = "<h2><center>Нахождение интеграла методом парабол</center></h2> <p><br>Задан интервал интегрирования:";
		Simpson+= "<br>A = "+a+" ; B = "+b;
		Simpson+= "<br>и подинтегральная функция f(x):<br>";
		Simpson+= z.toString();
		Double conv = (b - a) / getEps();
		int n = Math.abs(conv.intValue()) + 1;
		
		//System.out.println("n: " + n);
		double h = (getB() - getA()) / n;
		Simpson+= "<br><h3>Разобьем интервал интегрирования на "+n+" равных частей с шагом h = "+h+"</h3>";
		Simpson+= "<br><h3>На каждом частичном отрезке [Xi;Xi+1] длины h <br>заменим функцию f(x) квадратичной параболой,<br> интерполирующей  функцию f(x) в узлах Xi,Xi+1,Xi+2</h3>";
		//System.out.println("h: " + h);
		Simpson+="<br><h3>Вычисляя площадь этой фигуры получим искомое значение интеграла</h3>";
		double res = 0;
		res += (h / 3) * (y(a) + y(b));
		//System.out.println("res:  " + res);
		for (int i = 1; i < n - 1; i += 2)
			res += h * 4 * y(getA() + i * h) / 3;//Как это описать я не знаю, желающих прошу к клавиатуре

		for (int i = 2; i < n - 1; i += 2) {
			res += h * 2 * y(getA() + i * h) / 3;
		}
		//System.out.println("res:  " + res);
		Simpson+="<br><h3>Значение интеграла = "+res+"</h3>";
		return res;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
