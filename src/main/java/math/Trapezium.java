package math;

import graphbuilder.math.Expression;
import graphbuilder.math.FuncMap;
import graphbuilder.math.VarMap;

//численное интегрирование методом трапеций
public class Trapezium {

	private double eps = 0.00001, a, b;
	private Expression z;
	private VarMap vm=new VarMap();
	private FuncMap fm = new FuncMap();
	String Trapezium="";
	
	public String getTrapezium() {
		return Trapezium;
	}

	public Trapezium(Expression z, double a, double b){
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
	//переопределяет икс в VarMap
	public void setX(double x){
		getVm().setValue("x", x);
	}
	
	public double y(double x) {
		setX(x);
		fm.loadDefaultFunctions();
		return getZ().eval(getVm(), fm);
	}
	
	//собственно вычисляет интеграл
	public double integral(){
		Trapezium = "<h2><center>Нахождение интеграла методом трапеций</center></h2> <p><br>Задан интервал:";
		Trapezium+= "<br>A = "+a+" ; B = "+b;
		Trapezium+= "<br>и подинтегральная функция f(x):";
		Trapezium+= z.toString();
		Double conv = (b - a)/getEps();
		int n = Math.abs(conv.intValue()) + 1;
		//System.out.println("n: " + n);
		double h = (getB() - getA()) / n;
		Trapezium+= "<br><h3>Разобьем интервал интегрирования на "+n+" равных частей с шагом h = "+h+"</h3>";
		//System.out.println("h: " + h);
		Trapezium+="<br><h3>Площадь криволинейной трапеции равна интегралу от Xi до Xi+1 функции f(x)</h3>";
		Trapezium+="<br><h3>Апроксимируем функцию f(x) полиномом 1-й степени,</h3>";
		Trapezium+="<br><h3>тогда эту площадь можно приравнять к площади прямоугольной трапеции.</h3>";
		double res = 0;
		Trapezium+="<br><h3>Площадь трапеции будет состоять из суммы площадей треугольников = </h3>"+((h / 2) * (y(a) + y(b)));
		res += (h / 2) * (y(a) + y(b));
		//System.out.println("res:  " + res);
		double S=0;
		for(int i = 1 ; i < n - 1; i++){		
			S+= h * y(a + i * h);
			res += h * y(a + i * h);}
		Trapezium+="<br><h3>И суммы площадей прямоугольников = </h3>"+S;
		//System.out.println("res:  " + res);
		Trapezium+="<h3>Значение интеграла = "+res+"</h3>";
		return res;
	}

	public static void main(String[] args) {
		

	}

}
