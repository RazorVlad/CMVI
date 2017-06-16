package math;

import java.util.ResourceBundle;

import static java.lang.Math.abs;
import static math.MatrixActions.modifyA;

/**
 * Created by razor on 16.06.2017.
 */
public class Yacoby {
    String solveText = "";

    public String getSolveText() {
        return solveText;
    }

    public double[] solveJacoby(double[][] aa, double eps, ResourceBundle bundle) {
        int n = aa.length;
        solveText = "<h2><center>" + bundle.getString("report.solveYacoby") + "</center></h2> ";
        solveText += "<h3>" + bundle.getString("report.haveMatrixAandB") + ":</h3>\n";

        solveText += PrintInHTML.printAandBMatrix(aa);

        double[][] a = new double[n][];
        if (!MatrixActions.check(aa)) {
            solveText += "<br />" + bundle.getString("report.modifyMatrixToReqForm");
            a = modifyA(aa);

            solveText += PrintInHTML.printMatrix(a, 1);

            if (!MatrixActions.check(a))
                System.out
                        .println("приведение не сработало, диагонального преобладания все равно нет");
        } else
            a = aa;

        double[][] b = MatrixActions.findB(a);
        solveText += "<br />" + bundle.getString("report.calculateMatrixElements") + " b:";

        solveText += PrintInHTML.printMatrix(b, 1000);

        double norm = MatrixActions.normSearch(b);
        if (norm >= 1)
            System.out.println("error in b norm");
        solveText += "<br />" + bundle.getString("report.calculateMatrixNorm") + " b";
        solveText += "<br /><br />||b|| = " + norm;
        double[] prev = new double[n];
        for (int i = 0; i < n; i++) {
            prev[i] = a[i][n] / a[i][i];
            // System.out.println(d[i]);
        }
        double[] x = new double[n];
        solveText += "<br /><br />" + bundle.getString("report.calculateStart") + " x0=d:<br />";
        for (int i = 0; i < n; i++) {
            x[i] = a[i][n] / a[i][i];
            // System.out.println(x[i]);
        }
        solveText += "<table border=1>";
        for (int i = 0; i < n; i++) {
            solveText += "<td>";
            solveText += "X" + i + " = " + x[i];
            //System.out.println("x" + i + " : " + x[i]);
            solveText += "</td>";
        }
        solveText += "</table>";
        double[] d = new double[n];
        for (int i = 0; i < n; i++) {
            d[i] = a[i][n] / a[i][i];
            // System.out.println(d[i]);
        }
        int count = 0;
        solveText += "<br><h3>" + bundle.getString("report.calculateRoots") + ":</h3>";
        while (count < n) {
            for (int i = 0; i < n; i++) {
                prev[i] = x[i];
                // System.out.println(x[i]);
                x[i] = 0;
                for (int j = 0; j < n; j++) {
                    if (i != j)
                        x[i] += b[i][j] * prev[j];
                    // System.out.println(x[i]);
                }
                x[i] += d[i];
                // System.out.println(x[i]);
                if (abs(x[i] - prev[i]) <= eps) {
                    solveText += "<br />" + bundle.getString("report.iteration") + " " + (count + 1) + ":";
                    solveText += "<table border=1>";
                    for (int k = 0; k < n; k++) {
                        solveText += "<td>";
                        double xx = Math.round(x[k] * 100000000);
                        solveText += "X" + k + " = " + (xx / 100000000);
                        solveText += "</td>";
                        //System.out.println("x" + k + " : " + x[k]);
                    }
                    solveText += "</table>";
                    count++;
                }

            }
        }
        solveText += "<br><H3>" + bundle.getString("report.solutionMatrix") + ":</H3><br>";
        solveText += "<table border=1>";
        for (int i = 0; i < n; i++) {
            solveText += "<td>";
            solveText += "X" + i + " = " + x[i];
            System.out.println("x" + i + " : " + x[i]);
            solveText += "</td>";
        }
        solveText += "</table>";
        solveText += "<br /><br />";
        return x;
    }
}
