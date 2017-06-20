package math.gauss;

import math.gauss.lineExchange.CarefulLineExchange;
import math.gauss.lineExchange.LineExchange;

public class Gauss {
    public double[][] a;
    public double[] b, roots;
    int[] trueIndexes;

    public int[] getTrueIndexes() {
        return trueIndexes;
    }

    public String solveText;

    public double[][] getA() {
        return a;
    }

    public void setA(double[][] a) {
        this.a = a;
    }

    public double[] getB() {
        return b;
    }

    public void setB(double[] b) {
        this.b = b;
    }

    /**
     * @return the gauss
     */
    public String getSolveText() {
        return solveText;
    }

    // решение СЛУ методом Гаусса
    public double[] solve(double[][] a, double[] b, boolean useAsSubMethod) {
        int n = a.length;
        double[][] ishA = new double[n][n];
        double[] ishB = new double[n];
        // прямой ход
        String task = "";
        task += "<h2><center>Решение СЛАУ методом Гаусса</center></h2> <h3>Даны матрицы A и В:</h3>";
        task += "<table border=1>";
        for (int i = 0; i < n; i++) {
            task += "<tr>";
            for (int j = 0; j < n; j++) {
                task += "<td>";
                ishA[i][j] = a[i][j];
                task += (a[i][j] + "*X" + (j + 1) + " ");
                task += "</td>";
            }
            task += "<td>";
            ishB[i] = b[i];
            task += "= " + b[i];
            task += "</td>";
            task += "</tr>";
        }
        task += "</table>";
        if (!useAsSubMethod) solveText = task;
        //в конце отчета вывести в виде проверки то же самое
        //но подставляя вместо Х цифры из массива ответов
        //вывести дважды - в виде строки и не в виде строки(чтобы произведения посчитались и
        //получилось цифра=цифра)

        LineExchange.lineExchange(a, b); // тут поменялись местами строчки, реализован выбор
        // ведущих элементов
        solveText += "<br /><h3>Реализуем выбор ведущих элементов</h3>\n";
        solveText += "<table border=1>";
        for (int i = 0; i < n; i++) {
            solveText += "<tr>";
            for (int j = 0; j < n; j++) {
                solveText += "<td>";
                solveText += (a[i][j] + "*X" + (j + 1) + " ");
                solveText += "</td>";
            }
            if (!useAsSubMethod) {
                solveText += "<td>";
                solveText += "= " + b[i];
                solveText += "</td>";
            }
            solveText += "</tr>";
        }
        solveText += "</table>";
        solveText += "<h3>Прямой ход</h3>";
        for (int i = 0; i < n; i++) {

            solveText += "<i><b>Итерация № " + (i + 1) + "</b></i><br />";
            // solveText+=a[i][i]+"*X1"+" ";
            for (int j = i + 1; j < n; j++) {
                a[i][j] /= a[i][i];

            }

            b[i] /= a[i][i];

            a[i][i] = 1;

            solveText += "<br />Делим строку " + (i + 1) + " на элемент A[" + (i + 1)
                    + "][" + (i + 1) + "]<br />";
            if (i + 1 < n) {
                solveText += "Домножаем строку " + (i + 1) + " на элемент А["
                        + (i + 2) + "][" + (i + 1) + "]<br />";
                solveText += "Отнимаем от строки " + (i + 2) + " строку " + (i + 1);
            }
            for (int k = i + 1; k < n; k++) {
                b[k] -= b[i] * a[k][i];
                for (int j = i + 1; j < n; j++) {
                    a[k][j] -= a[i][j] * a[k][i];
                }
                a[k][i] = 0;
            }
            solveText += "<table border=1>";
            for (int t = 0; t < n; t++) {
                solveText += "<tr>";
                for (int j = 0; j < n; j++) {
                    solveText += "<td>";
                    double k1 = Math.round(a[t][j] * 100);
                    solveText += (k1 / 100 + "*X" + (j + 1) + " ");
                    solveText += "</td>";
                }
                if (!useAsSubMethod) solveText += addBvalueInReport(b[t]);

                solveText += "</tr>";
            }
            solveText += "</table>";
            solveText += "<br />";
        }
        // обратный ход

        double[] x = new double[n];
        solveText += "<h3>Обратный ход</h3>";
        for (int i = n - 1; i != -1; i--) {
            for (int j = i - 1; j != -1; j--) {
                b[j] -= b[i] * a[j][i];
                a[j][i] -= a[i][i] * a[j][i];
            }
        }

        solveText += "<table border=1>";
        for (int t = 0; t < n; t++) {
            solveText += "<tr>";
            for (int j = 0; j < n; j++) {
                solveText += "<td>";
                double k1 = Math.round(a[t][j] * 100);
                solveText += ((k1 / 100) + "*X" + (j + 1) + " ");
                solveText += "</td>";
            }

            if (!useAsSubMethod) solveText += addBvalueInReport(b[t]);

            solveText += "</tr>";
        }
        solveText += "</table>";
        String decisionMatrix = "";
        decisionMatrix += "<br /><h3>Матрица решений:</h3><br />";
        decisionMatrix += "<table border=1>";
        System.arraycopy(b, 0, x, 0, n);
        decisionMatrix += "<tr>";
        for (int i = 0; i < n; i++) {
            decisionMatrix += "<td>";
            decisionMatrix += "X" + i + " = " + x[i];
            decisionMatrix += "</td>";
            System.out.println("x" + i + " : " + x[i]);
        }
        decisionMatrix += "</tr>";
        decisionMatrix += "</table>";
        decisionMatrix += "<br /><br />";
        this.roots = x;
        decisionMatrix += "<br /><h3>Проверка:</h3>";
        decisionMatrix += "<table border=0>";
        for (int i = 0; i < n; i++) {
            decisionMatrix += "<tr>";
            for (int j = 0; j < n; j++) {
                decisionMatrix += "<td>";
                double k1 = Math.round(x[j] * 100);
                if (j == (n - 1)) decisionMatrix += ("(" + ishA[i][j] + ") * (" + (k1 / 100) + ") ");
                else decisionMatrix += ("(" + ishA[i][j] + ") * (" + (k1 / 100) + ") + ");
                decisionMatrix += "</td>";

            }
            decisionMatrix += "<td>";
            decisionMatrix += " = ";
            decisionMatrix += "</td>";
            for (int j = 0; j < n; j++) {
                decisionMatrix += "<td>";
                double k1 = Math.round(x[j] * 100);
                double k11 = Math.round((ishA[i][j] * (k1 / 100)) * 1000);

                if (k11 < 0) {
                    if (j == (n - 1)) decisionMatrix += "(" + (k11 / 1000) + ") ";
                    else decisionMatrix += "(" + ((k11 / 1000) + ") + ");
                } else {
                    if (j == (n - 1)) decisionMatrix += ((k11 / 1000) + " ");
                    else decisionMatrix += ((k11 / 1000) + " + ");
                }
                decisionMatrix += "</td>";
            }
            decisionMatrix += "<td>";
            decisionMatrix += "= " + ishB[i];
            decisionMatrix += "</td>";
            decisionMatrix += "</tr>";
        }
        decisionMatrix += "</table>";
        if (!useAsSubMethod) solveText += decisionMatrix;
        // System.out.println(solveText);

        return x;
    }

    // возвращает корни системы в последовательности, в которой они бы
    // находились,
    // если бы мы не переставляли местами строки (нужно для Крылова)
    // public double[] nativeRootSequence() {
    // double[] nrs = new double[this.roots.length];
    //
    // return nrs;
    // }

    public static String addBvalueInReport(double b) {
        String solveText = "<td>";
        double f = Math.round(b * 100);
        solveText += "= " + f / 100;
        solveText += "</td>";
        return solveText;
    }

    public static void main(String[] args) {
//		solveText g = new solveText();
        double a[][] = {{0, 0, 3, 2}, {0, 5, 0, 1}, {0, 1, 4, 2},
                {2, 3, 0, 0}};
        double[] b = {1, 2, 3, 4};
        int n = a.length;
        int bla = CarefulLineExchange.carefulLineExchange(a, b);
        for (int ii = 0; ii < n; ii++) {
            for (int j = 0; j < n; j++)
                System.out.println("a" + ii + j + " : " + a[ii][j]);
        }
        for (int j = 0; j < n; j++)
            System.out.println("b" + j + " : " + b[j]);
    }
}
