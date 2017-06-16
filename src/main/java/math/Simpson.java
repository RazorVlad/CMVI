package math;

import graphbuilder.math.Expression;
import graphbuilder.math.FuncMap;
import graphbuilder.math.VarMap;

import java.util.ResourceBundle;

//интегрирование методом —импсона (парабол)
public class Simpson {

    private double eps = 0.001, a, b;
    private Expression z;
    private VarMap vm = new VarMap();
    private FuncMap fm = new FuncMap();
    private ResourceBundle bundle;
    String Simpson = "";

    public String getSimpson() {
        return Simpson;
    }

    public Simpson(Expression z, double a, double b, ResourceBundle bundle) {
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

    // переопредел€ет икс в VarMap
    public void setX(double x) {
        getVm().setValue("x", x);
    }

    public double y(double x) {
        setX(x);
        fm.loadDefaultFunctions();
        return getZ().eval(getVm(), fm);
    }

    // собственно вычисл€ет интеграл
    public double integral() {
        Simpson = "<h2><center>" + bundle.getString("report.integralSearchParabol") + "</center></h2> ";
        Simpson += "<p><br>" + bundle.getString("report.definedInterval") + ":";
        Simpson += "<br>A = " + a + " ; B = " + b;
        Simpson += "<br>" + bundle.getString("report.andIntegralFunction") + " f(x):<br>";
        Simpson += z.toString();
        Double conv = (b - a) / getEps();
        int n = Math.abs(conv.intValue()) + 1;

        //System.out.println("n: " + n);
        double h = (getB() - getA()) / n;
        Simpson += "<br /><h3>"+bundle.getString("report.splitInterval")+" ";
        Simpson += n + " "+bundle.getString("report.equalParts")+" = " + h + "</h3>";
        Simpson += "<br /><h3>"
                +bundle.getString("report.onEachPart")
                +" <br />"+bundle.getString("report.replaceFx")
                +",<br> "+bundle.getString("report.thatIntegratedFx")
                +" Xi,Xi+1,Xi+2</h3>";
        //System.out.println("h: " + h);
        Simpson += "<br><h3>"+bundle.getString("report.calculatingThisFigureArea")+"</h3>";
        double res = 0;
        res += (h / 3) * (y(a) + y(b));
        //System.out.println("res:  " + res);
        for (int i = 1; i < n - 1; i += 2)
            res += h * 4 * y(getA() + i * h) / 3;// ак это описать € не знаю, желающих прошу к клавиатуре

        for (int i = 2; i < n - 1; i += 2) {
            res += h * 2 * y(getA() + i * h) / 3;
        }
        //System.out.println("res:  " + res);
        Simpson += "<br><h3>"+bundle.getString("report.integralValue")+" = " + res + "</h3>";
        return res;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
