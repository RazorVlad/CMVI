package math;

import Jama.Matrix;

import static java.lang.Math.abs;

/**
 * Created by razor on 16.06.2017.
 */
public class MatrixActions {
    // проверка диагонального преобладания
    public static boolean check(double[][] a) {
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



    public static double normSearch(double[][] b) {
        int n = b.length;
        double[] sum = new double[n];
        double norm = 0;

        for (int i = 0; i < n; i++) {
            sum[i] = 0;
            for (int j = 0; j < n; j++) {
                if (i != j)
                    sum[i] += abs(b[i][j]);
            }
            if (sum[i] > norm)
                norm = sum[i];
        }
        return norm;
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

    // ищем В
    public static double[][] findB(double[][] a) {
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

    // приведение матрицы к виду, удобному для итераций
    public static double[][] modifyA(double[][] a) {
        int m = a.length;
        int n = a[0].length;
        double[][] res = new double[m][n];
        double[][] resLeftPart = new double[m][m];
        double[] resRightPart = new double[m];
        double[][] leftPart = new double[m][m];
        double[] rightPart = new double[m];
        double[][] transLeftPart = new double[m][m];
        for (int i = 0; i < m; i++)
            System.arraycopy(a[i], 0, leftPart[i], 0, n - 1);
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
}
