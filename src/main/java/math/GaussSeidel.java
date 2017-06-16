package math;

import static java.lang.Math.*;

public class GaussSeidel {
    private String solveText = "";

    public String getSolveText() {
        return solveText;
    }

    // ������
    public double[] solveSeidel(double[][] aa, double eps) {
        int n = aa.length;
        solveText = "<h2><center>������� ���� ������� ������-�������</center></h2> <h3>���� ������� A � �:</h3>\n";

        solveText += PrintInHTML.printAandBMatrix(aa);

        double[][] a = new double[n][];
        if (!MatrixActions.check(aa)) {
            solveText += "<br>�������� ������� � ������������ ����";
            a = MatrixActions.modifyA(aa);
            solveText += "<table border=1>";
            for (int i = 0; i < n; i++) {
                solveText += "<tr>";
                for (int j = 0; j < n; j++) {
                    solveText += "<td>";
                    solveText += (a[i][j]);
                }
                solveText += "<td>";
                solveText += "= " + a[i][n] + "\n";
            }
            solveText += "</table>";
            if (!MatrixActions.check(a))
                System.out.println("���������� �� ���������,"
                        + " ������������� ������������ ��� ����� ���");
        } else
            a = aa;

        double[][] b = MatrixActions.findB(a);
        solveText += "<br>��������� �������� ������� b:";

        solveText += PrintInHTML.printMatrix(b, 1000);

        double norm = MatrixActions.normSearch(b);

        if (norm >= 1)
            System.out.println("error in b norm");//TODO �������� ���������

        solveText += "<br>������� ����� ������� b";
        solveText += "<br><br>||b|| = " + norm;
        double[] d = new double[n];
        for (int i = 0; i < n; i++) {
            d[i] = a[i][n] / a[i][i];
            // System.out.println(d[i]);
        }

        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = a[i][n] / a[i][i];
            // System.out.println(x[i]);
        }

        double[] prev = new double[n];

        int count = 0;
        solveText += "<br><h2>�������� �����:</h2>";
        while (count < n) {

            for (int i = 0; i < n; i++) {
                prev[i] = x[i];
                // System.out.println(x[i]);
                x[i] = 0;
                for (int j = 0; j < n; j++) {
                    if (i != j)
                        x[i] += b[i][j] * x[j];
                    // System.out.println(x[i]);
                }
                x[i] += d[i];
                // System.out.println(x[i]);
                if (abs(x[i] - prev[i]) <= eps) {
                    solveText += "<br>��������" + (count + 1);
                    solveText += "<table border=1>";
                    for (int k = 0; k < n; k++) {
                        solveText += "<td>";
                        double xx = Math.round(x[k] * 100000000);
                        solveText += "X" + k + " = " + xx / 100000000;

                        System.out.println("x" + k + " : " + x[k]);
                    }
                    solveText += "</table>";
                    count++;
                }
            }

        }
        solveText += "<br><h2>������� �������:</h2><br>";
        solveText += "<table border=1>";
        for (int i = 0; i < n; i++) {
            solveText += "<td>";
            solveText += "X" + i + " = " + x[i];
            double xx = Math.round(x[i] * 100000000);
            System.out.println("x" + i + " : " + xx / 100000000);
        }
        solveText += "</table>";
        solveText += "<br><br>";
        return x;
    }

    public static void main(String[] args) {

    }

}
