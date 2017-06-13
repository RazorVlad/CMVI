package math;

import static java.lang.Math.*;

import Jama.Matrix;

public class GaussSeidel {
    private String GZ = "";

    public String getGZ() {
        return GZ;
    }

    String Jacoby = "";

    // проверка диагонального преобладания
    private static boolean check(double[][] a) {
        int n;
        if (a.length == a[0].length)
            n = a.length;
        else
            n = a.length - 1;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                if (i != j)
                    sum += abs(a[i][j]);
            if (abs(a[i][i]) < sum)
                return false;
            sum = 0;
        }
        return true;
    }

    // ищем В
    private static double[][] findB(double[][] a) {
        int n = a.length;
        double b[][] = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j)
                    b[i][j] = -a[i][j] / a[i][i];
                // System.out.println(b[i][j]);
            }
            b[i][i] = 0;
            // System.out.println(b[i][i]);
        }
        return b;
    }

    static Double Norm = 0.0;

    // проверяем норму В
    private static boolean bNormCheck(double b[][]) {
        int n = b.length;
        double[] sum = new double[n];
        double norm = 0;

        for (int i = 0; i < n; i++) {
            sum[i] = 0;
            for (int j = 0; j < n; j++) {
                if (i != j)
                    sum[i] += abs(b[i][j]);
            }
            // System.out.println(sum[i]);
            if (sum[i] > norm)
                norm = sum[i];
            // System.out.println(norm);
        }
        Norm = norm;
        if (norm >= 1)
            return false;
        return true;
    }

    // умножение матрицы на матрицу(пока только для квадратных)
    public static double[][] matrixMultiplication(double[][] a, double[][] b) {
        int n = a.length;
        double[][] res = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                res[i][j] = 0;
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++)
                for (int j = 0; j < n; j++) {
                    res[i][k] += a[i][j] * b[j][k];
                    // System.out.println(res[i][k]);
                }
        }
        return res;
    }

    // умножение матрицы на вектор
    public static double[] vectMatrMultiplication(double[][] a, double[] b) {
        int n = a.length;
        double[] res = new double[n];
        for (int k = 0; k < n; k++)
            res[k] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                res[i] += a[i][j] * b[j];
            // System.out.println(res[i]);
        }
        return res;
    }

    // транспонирование матрицы
    public static double[][] transMatrix(double[][] a) {
        int m = a.length;
        int n = a[0].length;
        double[][] res = new double[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                res[j][i] = a[i][j];
        // for (int i = 0; i < m; i++)
        // for (int j = 0; j < n; j++)
        // System.out.println(res[i][j]);
        return res;
    }

    // приведение матрицы к виду, удобному для итераций
    private double[][] modifyA(double[][] a) {
        int m = a.length;
        int n = a[0].length;
        double[][] res = new double[m][n];
        double[][] resLeftPart = new double[m][m];
        double[] resRightPart = new double[m];
        double[][] leftPart = new double[m][m];
        double[] rightPart = new double[m];
        double[][] transLeftPart = new double[m][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n - 1; j++)
                leftPart[i][j] = a[i][j];
        for (int i = 0; i < m; i++) {
            rightPart[i] = a[i][n - 1];
            // System.out.println(rightPart[i]);
        }
        transLeftPart = transMatrix(leftPart);
        resLeftPart = matrixMultiplication(transLeftPart, leftPart);
        resRightPart = vectMatrMultiplication(transLeftPart, rightPart);
        Matrix matrA = new Matrix(resLeftPart);
        Matrix eiVal = new Matrix(matrA.eig().getD().getArray());
        double[] vect = new double[eiVal.getArray().length];

        for (int i = 0; i < eiVal.getArray().length; i++)
            vect[i] = eiVal.get(i, i);

        Krylov kr = new Krylov(resLeftPart);
        //kr.findNumbers();
        kr.setNumbers(vect);

        //		if (kr.getNumbers().length == 0) {
        //			System.out.println("У матрицы условий нет действительных "
        //					+ "собственных значений и приведение к виду, "
        //					+ "удобному для итераций невозможно.");
        //			return null;
        //		}

        double tau = 2 / (kr.maxNumber() + kr.minNumber());

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (j == n - 1)
                    res[i][j] = tau * resRightPart[i];
                else
                    res[i][j] = tau * resLeftPart[i][j];
        return res;
    }

    // решаем
    public double[] solveSeidel(double[][] aa, double eps) {
        int n = aa.length;
        GZ = "<h2><center>Решение СЛАУ методом Гаусса-Зейделя</center></h2> <h3>Даны матрицы A и В:</h3>\n";
        GZ += "<table border=1>";
        for (int i = 0; i < n; i++) {
            GZ += "<tr>";
            for (int j = 0; j < n; j++) {
                GZ += "<td>";
                GZ += (aa[i][j] + "*X" + (j + 1) + " ");
            }
            GZ += "<td>";
            GZ += "= " + aa[i][n] + "<br>\n";
        }
        GZ += "</table>";
        double[][] a = new double[n][];
        if (!check(aa)) {
            GZ += "<br>Приводим матрицу к необходимому виду";
            a = modifyA(aa);
            GZ += "<table border=1>";
            for (int i = 0; i < n; i++) {
                GZ += "<tr>";
                for (int j = 0; j < n; j++) {
                    GZ += "<td>";
                    GZ += (a[i][j]);
                }
                GZ += "<td>";
                GZ += "= " + a[i][n] + "\n";
            }
            GZ += "</table>";
            if (!check(a))
                System.out.println("приведение не сработало,"
                        + " диагонального преобладания все равно нет");
        } else
            a = aa;

        double[][] b = findB(a);
        GZ += "<br>Вычисляем элементы матрицы b:";
        GZ += "<table border=1>";
        for (int i = 0; i < n; i++) {
            GZ += "<tr>";
            for (int j = 0; j < n; j++) {
                GZ += "<td>";
                double bb = Math.round(b[i][j] * 1000);
                GZ += (bb / 1000);
            }
        }
        GZ += "</table>";
        if (!bNormCheck(b))
            System.out.println("error in b norm");//TODO Вызывает зависание


        GZ += "<br>Находим норму матрицы b";
        GZ += "<br><br>||b|| = " + Norm;
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
        GZ += "<br><h2>Уточняем корни:</h2>";
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
                    GZ += "<br>Итерация" + (count + 1);
                    GZ += "<table border=1>";
                    for (int k = 0; k < n; k++) {
                        GZ += "<td>";
                        double xx = Math.round(x[k] * 100000000);
                        GZ += "X" + k + " = " + xx / 100000000;

                        System.out.println("x" + k + " : " + x[k]);
                    }
                    GZ += "</table>";
                    count++;
                }
            }

        }
        GZ += "<br><h2>Матрица решений:</h2><br>";
        GZ += "<table border=1>";
        for (int i = 0; i < n; i++) {
            GZ += "<td>";
            GZ += "X" + i + " = " + x[i];
            double xx = Math.round(x[i] * 100000000);
            System.out.println("x" + i + " : " + xx / 100000000);
        }
        GZ += "</table>";
        GZ += "<br><br>";
        return x;
    }

    public String getJacoby() {
        return Jacoby;
    }

    public double[] solveJacoby(double[][] aa, double eps) {
        int n = aa.length;
        Jacoby = "<h2><center>Решение СЛАУ методом Якоби</center></h2> <h3>Даны матрицы A и В:</h3>\n";
        Jacoby += "<table border=1>";
        for (int i = 0; i < n; i++) {
            Jacoby += "<tr>";
            for (int j = 0; j < n; j++) {
                Jacoby += "<td>";
                Jacoby += (aa[i][j] + "*X" + (j + 1) + " ");
            }
            Jacoby += "<td>";
            Jacoby += "= " + aa[i][n] + "<br>\n";
        }
        Jacoby += "</table>";
        double[][] a = new double[n][];
        if (!check(aa)) {
            Jacoby += "<br>Приводим матрицу к необходимому виду";
            a = modifyA(aa);
            Jacoby += "<table border=1>";
            for (int i = 0; i < n; i++) {
                Jacoby += "<tr>";
                for (int j = 0; j < n; j++) {
                    Jacoby += "<td>";
                    Jacoby += (a[i][j]);
                }
            }
            Jacoby += "</table>";
            if (!check(a))
                System.out
                        .println("приведение не сработало, диагонального преобладания все равно нет");
        } else
            a = aa;

        double[][] b = findB(a);
        Jacoby += "<br>Вычисляем элементы матрицы b:";
        Jacoby += "<table border=1>";
        for (int i = 0; i < n; i++) {
            Jacoby += "<tr>";
            for (int j = 0; j < n; j++) {
                Jacoby += "<td>";
                double bb = Math.round(b[i][j] * 1000);
                Jacoby += (bb / 1000);
            }
        }
        Jacoby += "</table>";
        if (!bNormCheck(b))
            System.out.println("error in b norm");
        Jacoby += "<br>Находим норму матрицы b";
        Jacoby += "<br><br>||b|| = " + Norm;
        double[] prev = new double[n];
        for (int i = 0; i < n; i++) {
            prev[i] = a[i][n] / a[i][i];
            // System.out.println(d[i]);
        }
        double[] x = new double[n];
        Jacoby += "<br><br>Находим начальное приближение x0=d:<br>";
        for (int i = 0; i < n; i++) {
            x[i] = a[i][n] / a[i][i];
            // System.out.println(x[i]);
        }
        Jacoby += "<table border=1>";
        for (int i = 0; i < n; i++) {
            Jacoby += "<td>";
            Jacoby += "X" + i + " = " + x[i];
            //System.out.println("x" + i + " : " + x[i]);
        }
        Jacoby += "</table>";
        double[] d = new double[n];
        for (int i = 0; i < n; i++) {
            d[i] = a[i][n] / a[i][i];
            // System.out.println(d[i]);
        }
        int count = 0;
        Jacoby += "<br><h3>Уточняем корни:</h3>";
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
                    Jacoby += "<br>Итерация " + (count + 1) + ":";
                    Jacoby += "<table border=1>";
                    for (int k = 0; k < n; k++) {
                        Jacoby += "<td>";
                        double xx = Math.round(x[k] * 100000000);
                        Jacoby += "X" + k + " = " + (xx / 100000000);
                        //System.out.println("x" + k + " : " + x[k]);
                    }
                    Jacoby += "</table>";
                    count++;
                }

            }

        }
        Jacoby += "<br><H3>Матрица решений:</H3><br>";
        Jacoby += "<table border=1>";
        for (int i = 0; i < n; i++) {
            Jacoby += "<td>";
            Jacoby += "X" + i + " = " + x[i];
            System.out.println("x" + i + " : " + x[i]);
        }
        Jacoby += "</table>";
        Jacoby += "<br><br>";
        return x;
    }

    public static void main(String[] args) {

    }

}
