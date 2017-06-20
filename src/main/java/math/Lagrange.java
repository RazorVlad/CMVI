package math;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class Lagrange {

    private ArrayList<Double> x = new ArrayList<Double>();
    private ArrayList<Double> y = new ArrayList<Double>();
    private String lagr;
    //="<h2><center>"
    //+"Интерполирование функции методом Лагранжа"
    //+"</h2></center>";
    private ResourceBundle bundle;

    public String getLagr() {
        return lagr;
    }


    public Lagrange(double[] x, double[] y, ResourceBundle bundle) {
        this.bundle = bundle;
        setLagrange(x, y);
    }

    public Lagrange(double[] x, double[] y) {
        this.bundle = ResourceBundle.getBundle("MethodsBundle");
        setLagrange(x, y);
    }

    public void setLagrange(double[] x, double[] y) {
        this.x = new ArrayList<Double>();
        lagr = "<h2><center>"
                //+"Интерполирование функции методом Лагранжа"
                + bundle.getString("report.solveLagrange")
                + "</h2></center";
        for (int i = 0; i < x.length; i++)
            this.x.add(x[i]);
        this.y = new ArrayList<Double>();
        for (int i = 0; i < y.length; i++)
            this.y.add(y[i]);
    }


    public void setX(ArrayList<Double> x) {
        this.x = x;
    }

    public void setX(double[] x) {
        this.x = new ArrayList<Double>();
        for (int i = 0; i < x.length; i++)
            this.x.add(x[i]);
    }

    public void setY(double[] y) {
        this.y = new ArrayList<Double>();
        for (int i = 0; i < y.length; i++)
            this.y.add(y[i]);
    }

    public void setY(ArrayList<Double> y) {
        this.y = y;
    }

    public ArrayList<Double> getX() {
        return x;
    }

    public ArrayList<Double> getY() {
        return y;
    }

    public void addLastXY(double x, double y) {
        this.x.add(x);
        this.y.add(y);
    }

    public void addXYtoPosition(double x, double y, int position) {
        this.x.add(position, x);
        this.y.add(position, y);
    }

    public double getXi(int i) {
        return this.x.get(i);
    }

    public double getYi(int i) {
        return this.y.get(i);
    }

    public int xyCount() {
        return this.x.size();
    }

    //возвращает значение полинома Лагранжа в любой точке внутри области
    public double getLagrY(double x) {
        double l, g = 0;
        for (int i = 0; i < xyCount(); i++) {
            l = 1;
            for (int j = 0; j < xyCount(); j++) {
                if (j != i) {
                    l *= (x - getXi(j)) / (getXi(i) - getXi(j));
                }
            }
            g += l * getYi(i);
        }
        return g;
    }

    //код Шовкуна
    //хрен его что это, но надо
    public ArrayList<Double> PolPly(ArrayList<Double> x, ArrayList<Double> y) {
        ArrayList<Double> result = new ArrayList<Double>();
        for (int i = 0; i < x.size(); i++) {
            for (int n = 0; n < y.size(); n++) {
                if (result.size() > i + n) {
                    result.set(i + n, result.get(i + n) + x.get(i) * y.get(n));
                } else {
                    result.add(i + n, x.get(i) * y.get(n));
                }
            }
        }
        return result;
    }

    //все еще код Шовкуна
    public ArrayList<Double> PolPlyChisl(ArrayList<Double> x, double y) {
        double rez = 0;
        ArrayList<Double> result = new ArrayList<Double>();
        for (int i = 0; i < x.size(); i++) {
            rez = x.get(i) * y;
            result.add(rez);
        }
        return result;
    }

    //все еще Шовкун
    public ArrayList<Double> PolSum(ArrayList<Double> x, ArrayList<Double> y) {
        int small_size = 0;
        ArrayList<Double> result = new ArrayList<Double>();
        if (x.size() > y.size()) {
            small_size = y.size();
            for (int i1 = 0; i1 < x.size(); i1++)
                result.add(x.get(i1));
        } else {
            small_size = x.size();
            for (int i1 = 0; i1 < y.size(); i1++)
                result.add(y.get(i1));
        }
        for (int i = 0; i < small_size; i++) {
            result.set(i, result.get(i) + y.get(i));
        }
        return result;
    }

    double formatDouble(double d, int dz) {
        double dd = Math.pow(10, dz);
        return Math.round(d * dd) / dd;
    }

    public String PolToString(ArrayList<Double> x) {
        String out = new String();
        out = "";
        boolean minus = false;
        for (int i = 0; i < x.size(); i++) {
            if (x.get(i) < 0) {
                minus = true;
            } else {
                minus = false;
            }
            if (x.get(i) != 0) {
                if (i == 0) {
                    out = "" + Math.abs(x.get(i));
                } else if (i == 1) {
                    out = formatDouble(Math.abs(x.get(i)), 3) + "x" + out;
                } else {
                    out = formatDouble(Math.abs(x.get(i)), 3) + "x<sup>" + i + "</sup>" + out;
                }
                if (minus == true) {
                    out = " - " + out;
                } else if (i == x.size() - 1) {

                } else {
                    out = " + " + out;
                }
            }
        }
        return out;
    }

    //это должно упрощать полином
    public /*void*/ ArrayList<Double> eqInfo(/*PrintWriter out*/) {
//	    out.println("<span style='color:red'>");
//	    out.println("g(x) ~ ");

        ArrayList<Double> sum = new ArrayList<Double>();
        ArrayList<Double> up = new ArrayList<Double>();
        ArrayList<Double> previous = new ArrayList<Double>();
        ArrayList<Double> pol = new ArrayList<Double>();
        ArrayList<Double> tmp = new ArrayList<Double>();
        ArrayList<Double> tmp2 = new ArrayList<Double>();

        for (int i = 0; i < xyCount(); i++) {//j<3
            double chisl = 1, znam = 1;
            boolean ply = true;
            for (int j = 0; j < xyCount(); j++) {//j<3
                //   out.println("I: " + i + "J: " + j + "<br />");
                ply = true;
                if (i != j) {
                    if (j == 0) {
                        ply = false;
                    }
                    if (i == 0 && j == 1) {
                        ply = false;
                    }

                    if (ply == true) {
                        previous.clear();
                        for (int i1 = 0; i1 < up.size(); i1++)
                            previous.add(up.get(i1));
                    }

                    up.clear();
                    up.add(-getXi(j));
                    up.add(1.0);
                    //     out.println("BASE: " + PolToString(up) + "<br />");

                    if (ply == true) {
                        up = PolPly(up, previous);
                        //       out.println("PLY: " + PolToString(up) + "<br />");
                    }

                    //     chisl *= (x - getX(j));
                    znam *= (getXi(i) - getXi(j));
                    if (znam == 0) {
//	                out.println("ERROR");
                        System.out.println("делим на ноль! ошибка!");
                    }
                }
            }

            pol = PolPlyChisl(up, 1 / znam);
            //  out.println("ZNAM: " + 1/znam);
            //  out.println("DEL: " + PolToString(pol));
            //
            pol = PolPlyChisl(pol, getYi(i));
            // out.println("COEF: " + getY(i));
            //  out.println("DEL: " + PolToString(pol));

            tmp2.clear();
            for (int i1 = 0; i1 < pol.size(); i1++)
                tmp2.add(pol.get(i1));

            if (i == 0) {
                for (int i1 = 0; i1 < tmp2.size(); i1++)
                    sum.add(tmp2.get(i1));
            } else {
                tmp.clear();
                //  out.println("<br/>ODIN: " + tmp2);
                //  out.println("<br/>DVA: " + sum);

                for (int i1 = 0; i1 < tmp2.size(); i1++)
                    tmp.add(tmp2.get(i1) + sum.get(i1));


                sum.clear();
                for (int i1 = 0; i1 < tmp.size(); i1++)
                    sum.add(tmp.get(i1));
            }
            //   out.println("<br/>SUM: " + PolToString(sum));
            // out.println("<br /><br />");
            //  sum += getY(i) * (chisl / znam);
        }
//	    out.println(PolToString(sum));
//	    out.println("</span>");
        return sum;
    }

    //возвращает строку с полиномом Лагранжа (не упрощенную)
    public String getLagrString() {
        lagr += "<h3>"
                //+"Заданы точки"
                + bundle.getString("report.initDots")
                + ":</h3>";
        for (int i = 0; i < xyCount(); i++)
            lagr += "<br>x" + i
                    + " = " + getXi(i) + "; y" + i
                    + " = " + getYi(i);
        String s = "";//"L = ";
        for (int i = 0; i < xyCount(); i++) {
            if (i == 0)
                s += getYi(i) + "*(";//[
            else
                s += " + " + getYi(i) + "*(";//[
            for (int j = 0; j < xyCount(); j++) {
                if (j != i) {
                    if ((j == 0) || (i == 0 && j == 1)) s += "(x - ";// + getXi(j) + ")";
                        //if(i==0 && j==1)s += "(x - " + getXi(j) + ")";
                    else s += "*(x - ";// + getXi(j) + ")";
                    if (getXi(j) < 0) s += "(" + getXi(j) + ")" + ")";
                    else s += getXi(j) + ")";
                }
            }
            s += " / ";
            for (int j = 0; j < xyCount(); j++) {
                if (j != i) {
                    if ((j == 0) || (i == 0 && j == 1)) s += "(" + getXi(i) + " - ";//+ getXi(j) + ")";
                        //if(i==0 && j==1)s += "(" + getXi(i) + " - " + getXi(j) + ")";
                    else s += "*(" + getXi(i) + " - ";//+ getXi(j) + ")";
                    if (getXi(j) < 0) s += "(" + getXi(j) + ")" + ")";
                    else s += getXi(j) + ")";
                }
            }
            s += ")";//]
        }
        lagr += "<h3>"
                //+"Получаем полином"
                + bundle.getString("report.getPolynom")
                + "</h3>";
        lagr += s;
        lagr += "<h3>"
                //+"Упрощенный вид"
                + bundle.getString("report.shortType")
                + ":</h3>L = " + this.PolToString(this.eqInfo());
        //System.out.println("Не упрощенный вид:\nL = "+this.getLagrString()
        //+ "\nУпрощенный вид:\nL = " +this.PolToString(this.eqInfo()));
        return s;
    }

    public static void main(String[] args) {
        double ax[] = {1, 3, 5};
        double ay[] = {4, 3, 6};
        Lagrange l = new Lagrange(ax, ay);
        //System.out.println(l.getLagrString());
        //Double d = 25.92837;
        //System.out.println(d.intValue());
        System.out.println("Не упрощенный вид:\nL = " + l.getLagrString()
                + "\nУпрощенный вид:\nL = " + l.PolToString(l.eqInfo()));
    }

}
