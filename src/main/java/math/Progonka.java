package math;

import graphbuilder.math.Expression;
import graphbuilder.math.FuncMap;
import graphbuilder.math.VarMap;

public class Progonka {

	private Expression p, q, f;
	private VarMap vm;
	private FuncMap fm;
	private double a, b, A, B, h;
	int n = 10;
	int eps = 10;
	private double[] alfa, beta;
	String Progonka = "";

	public String getProgonka() {
		return Progonka;
	}

	public Progonka() {
		vm = new VarMap();
		fm = new FuncMap();
		vm.setValue("pi", Math.PI);
		vm.setValue("e", Math.E);
		fm.loadDefaultFunctions();
	}

	public double getAlfai(int i) {
		return this.alfa[i];
	}

	public double getBetai(int i) {
		return this.beta[i];
	}

	public double[] getAlfa() {
		return alfa;
	}

	public void setAlfa(double[] alfa) {
		this.alfa = alfa;
	}

	public double[] getBeta() {
		return beta;
	}

	public void setBeta(double[] beta) {
		this.beta = beta;
	}

	public Expression getP() {
		return p;
	}

	public void setP(Expression p) {
		this.p = p;
	}

	public Expression getQ() {
		return q;
	}

	public void setQ(Expression q) {
		this.q = q;
	}

	public Expression getF() {
		return f;
	}

	public void setF(Expression f) {
		this.f = f;
	}

	public VarMap getVm() {
		return vm;
	}

	public void setVm(VarMap vm) {
		this.vm = vm;
	}

	public FuncMap getFm() {
		return fm;
	}

	public void setFm(FuncMap fm) {
		this.fm = fm;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n +1;
	}

	public int getEps() {
		return eps;
	}

	public void setEps(int eps) {
		this.eps = eps;
	}

	public double geta() {
		return a;
	}

	public void seta(double a) {
		this.a = a;
	}

	public double getb() {
		return b;
	}

	public void setb(double b) {
		this.b = b;
	}

	public double getA() {
		return A;
	}

	public void setA(double a) {
		A = a;
	}

	public double getB() {
		return B;
	}

	public void setB(double b) {
		B = b;
	}

	public void setX(double x) {
		getVm().setValue("x", x);
	}

	// возвращает значение функции
	public double p(double x) {
		setX(x);
		return getP().eval(getVm(), getFm());
	}

	// возвращает значение функции
	public double q(double x) {
		setX(x);
		return getQ().eval(getVm(), getFm());
	}

	// возвращает значение функции
	public double f(double x) {
		setX(x);
		return getF().eval(getVm(), getFm());
	}

	// возвращает значение функции
	public double p(int i) {
		setX(geta() + i * getH());
		return getP().eval(getVm(), getFm());
	}

	// возвращает значение функции
	public double q(int i) {
		setX(geta() + i * getH());
		return getQ().eval(getVm(), getFm());
	}

	// возвращает значение функции
	public double f(int i) {
		setX(geta() + i * getH());
		return getF().eval(getVm(), getFm());
	}

	// возвращает массив решений
	public double[] solve() {
		Progonka = "<h2><center>"
		+"Решение методом Прогонки"
		+"</center></h2>";
		Progonka += "<br><h3>"
		+"Исходные данные"
		+":</h3>";
		Progonka += "<br>"
		+"Интервал"
		+" a=" + geta() 
		+ ",b=" + getb();
		Progonka += "<br>Alfa(1)=" 
		+ getAlfai(0) 
		+ "; Alfa(2)=" 
		+ getAlfai(1)
		+ "; A=" 
		+ getA();
		Progonka += "<br>Beta(1)=" 
		+ getBetai(0) 
		+ "; Beta(2)=" 
		+ getBetai(1)
		+ "; B=" 
		+ getB();
		Progonka += "<br>p(x) = " + getP();
		Progonka += "<br>q(x) = " + getQ();
		Progonka += "<br>f(x) = " + getF();
		Progonka += "<br><br><h3>"
		+"Находим шаг h"
		+":</h3>";
		Progonka += "<br>"
		+"Делим интервал"
		+" (" + getb() + " - " + geta()
				+ ") "
				+"на количество разбиений"
				+" n = "+n;
		// int n = (int)getH();//(int) ((getb() - geta()) / h);
		setH((getb() - geta()) / (n-1));
		Progonka += "<br>h = " + h;
		System.out.println("n " + n);
		double[] a = new double[n];
		double[] b = new double[n];
		double[] c = new double[n];
		double[] d = new double[n];
		Progonka += "<br><br><h3>"
		+"Вычисляем коэфициенты"
		+" a[0],b[0],c[0],d[0],V[0],U[0]</h3>";
		a[0] = 0;
		Progonka += "<br>"
		+"Коэфициент"
		+" a[0]=0";
		b[0] = getH() * getAlfai(0) - getAlfai(1);
		Progonka += "<br>"
		+"Коэфициент"
		+" b[0]=h*Alfa0-alfa1 = " + h + "*"
				+ getAlfai(0) + "-" + getAlfai(1) + " = " + b[0];
		// System.out.println("b0 " + b[0]);
		c[0] = getAlfai(1);
		Progonka += "<br>"
		+"Коэфициент"
		+" c[0]=Alfa1=" + c[0];
		// System.out.println("c0 " + c[0]);
		d[0] = getH() * getA();
		Progonka += "<br>"
		+"Коэфициент"
		+" d[0]=h*A=" + h + "*" + getA() + " = "
				+ d[0];
		// System.out.println("d0 " + d[0]);
		Progonka += "<br>"
		+"Вычисляем"
		+" V[0] и U[0]:";
		double[] v = new double[n];
		v[0] = -c[0] / b[0];
		double vn = Math.round(v[0] * 1000);
		Progonka += "<br>V[0] = -c[0] / b[0] = -" + c[0] + "/" + b[0] + " = "
				+ (vn / 1000);
		// System.out.println("v0 " + v[0]);
		double[] u = new double[n];
		u[0] = d[0] / b[0];
		double un = Math.round(u[0] * 1000);
		Progonka += "<br>U[0] = d[0] / b[0] = " + d[0] + "/" + b[0] + " = "
				+ (un / 1000);
		Progonka += "<br><br><h3>"
		+"Вычисляем коэфициенты"
		+" a[i],b[i],c[i],d[i],V[i],U[i], i "
		+"изменяется от 1 до n-1"+":</h3>";
		// System.out.println("u0 " + u[0]);
		Progonka += "a[i] = 1 - h * p(i) / 2";
		Progonka += "<br>b[i] = h * h * q(i) - 2";
		Progonka += "<br>c[i] = 1 + h * p(i) / 2";
		Progonka += "<br>d[i] = h * h * f(i)";
		Progonka += "<br>v[i] = - c[i]  / (a[i] * v[i - 1] + b[i])";
		Progonka += "<br>u[i] = (d[i] - a[i] * u[i - 1]) / (a[i] * v[i - 1] + b[i])<br>";
		Progonka += "<table>";
		for (int i = 1; i < n - 1; i++) {
			Progonka += "<tr>";
			a[i] = 1 - getH() * p(i) / 2;
			b[i] = getH() * getH() * q(i) - 2;
			c[i] = 1 + getH() * p(i) / 2;
			d[i] = getH() * getH() * f(i);
			v[i] = -c[i] / (a[i] * v[i - 1] + b[i]);
			u[i] = (d[i] - a[i] * u[i - 1]) / (a[i] * v[i - 1] + b[i]);
			double az = Math.round(a[i] * 1000);
			double bz = Math.round(b[i] * 1000);
			double cz = Math.round(c[i] * 1000);
			double dz = Math.round(d[i] * 1000);
			double vz = Math.round(v[i] * 1000);
			double uz = Math.round(u[i] * 1000);
			Progonka += "<td>a[" + i + "]=" + (az / 1000) + ";  <td>b[" + i
					+ "]=" + (bz / 1000) + ";  <td>c[" + i + "]=" + (cz / 1000)
					+ ";  <td>d[" + i + "]=" + (dz / 1000) + ";  <td>v[" + i
					+ "]=" + (vz / 1000) + ";  <td>u[" + i + "]=" + (uz / 1000);
		}
		Progonka += "</table>";
		// for(int i = 1; i < n - 1; i++){

		// System.out.println("a" + i + " " + a[i]);
		// System.out.println("b" + i + " " + b[i]);
		// System.out.println("c" + i + " " + c[i]);
		// System.out.println("d" + i + " " + d[i]);
		// System.out.println("v" + i + " " + v[i]);
		// System.out.println("u" + i + " " + u[i]);
		// }
		Progonka += "<br><h3>"
		+"Вычисляем коэфициенты"
		+" a[n],b[n],d[n]:</h3>";
		a[n - 1] = -getBetai(1);
		Progonka += "<br>a[n]= - Beta2 = " + a[n - 1];
		b[n - 1] = getH() * getBetai(0) + getBetai(1);
		Progonka += "<br>b[n]= h * Beta1 + Beta2 = " + getH() + "*"
				+ getBetai(0) + "+" + getBetai(1) + " = " + b[n - 1];
		d[n - 1] = getH() * getB();
		double dn = Math.round(d[n - 1] * 1000);
		Progonka += "<br>d[n]= h*B = " + getH() + "*" + getB() + " = "
				+ (dn / 1000);
		for (int i = n - 1; i < n; i++) {
			System.out.println("a" + i + " " + a[i]);
			System.out.println("b" + i + " " + b[i]);
			// System.out.println("c" + i + " " + c[i]);
			System.out.println("d" + i + " " + d[i]);
			// System.out.println("v" + i + " " + v[i]);
			// System.out.println("u" + i + " " + u[i]);
		}
		Progonka += "<br><br><h3>"
		+"Вычисляем"
		+" y[n]=y[" + (n - 1)
				+ "] "
				+"по формуле"
				+":</h3>";
		double[] y = new double[n ];
		y[n - 1] = (d[n - 1] - a[n - 1] * u[n - 2])
				/ (a[n - 1] * v[n - 2] + b[n - 1]);
		double dc = Math.round(d[n - 1] * 1000);
		double ac = Math.round(a[n - 1] * 1000);
		double uc = Math.round(u[n - 2] * 1000);
		double vc = Math.round(v[n - 2] * 1000);
		double bc = Math.round(b[n - 1] * 1000);
		double yc = Math.round(y[n - 1] * 1000);
		Progonka += "y[n] = (d[n] - a[n] * u[n - 1]) / (a[n] * v[n - 1] + b[n])";
		Progonka += "<br>y[" + (n - 1) + "] = (" + (dc / 1000) + "-"
				+ (ac / 1000) + "*" + (uc / 1000) + ")/(" + (ac / 1000) + "*"
				+ (vc / 1000) + "+" + (bc / 1000) + ") = " + (yc / 1000);
		System.out.println("y n-1" + (n - 1) + " " + y[n - 1]);
		Progonka += "<br><br><h3>"
		+"Вычисляем оставшиеся"
		+" y[i] "
		+"по формуле"
		+":</h3>";
		Progonka += "<h3>y[i] = u[i] + v[i] * y[i + 1] , i "
		+"изменяется от n-1 до 0"
		+"</h3>";
		for (int i = n - 2; i >= 0; i--) {
			System.out.println("u" + i + " " + u[i]);
			System.out.println("v" + i + " " + v[i]);
			System.out.println("y" + (i + 1) + " " + y[i + 1]);
			y[i] = u[i] + v[i] * y[i + 1];
			double uz = Math.round(u[i] * 1000);
			double vz = Math.round(v[i] * 1000);
			double y1z = Math.round(y[i + 1] * 1000);
			double yz = Math.round(y[i] * 1000);
			Progonka += "<br>y[" + i + "] = " + (uz / 1000) + " + "
					+ (vz / 1000) + " * " + (y1z / 1000) + " = " + (yz / 1000);
			System.out.println("y" + i + " " + y[i]);
		}

		return y;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
