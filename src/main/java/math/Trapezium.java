package math;

import graphbuilder.math.Expression;
import graphbuilder.math.FuncMap;
import graphbuilder.math.VarMap;

import java.util.ResourceBundle;

//численное интегрирование методом трапеций
public class Trapezium {

    private double eps = 0.00001, a, b;
    private Expression z;
    private VarMap vm = new VarMap();
    private FuncMap fm = new FuncMap();
    String Trapezium = "";
    private ResourceBundle bundle;

    public String getTrapezium() {
        return Trapezium;
    }

    public Trapezium(Expression z, double a, double b, ResourceBundle bundle) {
        this.z = z;
        this.a = a;
        this.b = b;
        this.bundle = bundle;
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
    public void setX(double x) {
        getVm().setValue("x", x);
    }

    public double y(double x) {
        setX(x);
        fm.loadDefaultFunctions();
        return getZ().eval(getVm(), fm);
    }

    //собственно вычисляет интеграл
    public double integral() {
        Trapezium = "<h2><center>" + bundle.getString("report.integralSearchTrapezium") + "</center></h2> ";
        Trapezium += "<br>" + bundle.getString("report.definedInterval") + ":";
        Trapezium += "<br>A = " + a + " ; B = " + b;
        Trapezium += "<br>" + bundle.getString("report.andIntegralFunction") + " f(x)" + ":";
        Trapezium += z.toString();
        Double conv = (b - a) / getEps();
        int n = Math.abs(conv.intValue()) + 1;
        //System.out.println("n: " + n);
        double h = (getB() - getA()) / n;
        Trapezium += "<br><h3>" + bundle.getString("report.splitInterval") + " " + n + " ";
        Trapezium += bundle.getString("report.equalParts") + " = " + h + "</h3>";
        //System.out.println("h: " + h);
        Trapezium += "<br><h3>" + bundle.getString("report.trapeziumAreaEqualsIntegral") + "</h3>";
        Trapezium += "<br><h3>" + bundle.getString("report.aproximizeFunction") + "</h3>";
        Trapezium += "<br><h3>" + bundle.getString("report.thenThisAreaCanEqualToTriangle") + "</h3>";
        double res = 0;
        Trapezium += "<br><h3>" + bundle.getString("report.trapeziumAreaEqualSumTriangleArea") + " = </h3>" + ((h / 2) * (y(a) + y(b)));
        res += (h / 2) * (y(a) + y(b));
        //System.out.println("res:  " + res);
        double S = 0;
        for (int i = 1; i < n - 1; i++) {
            S += h * y(a + i * h);
            res += h * y(a + i * h);
        }
        Trapezium += "<br><h3>" + bundle.getString("report.andRecktangleArea") + " = </h3>" + S;
        //System.out.println("res:  " + res);
        Trapezium += "<h3>" + bundle.getString("report.integralValue") + " = " + res + "</h3>";
        return res;
    }

    public static void main(String[] args) {


    }

}
