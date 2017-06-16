package math.gauss;

import math.MatrixActions;
import math.PrintInHTML;

/**
 * Created by razor on 16.06.2017.
 */
public class ReverseMatrix {
    private String solveText;

    public String getSolveText() {
        return solveText;
    }

    public double[][] reverseMatrix(double[][] a) {
        int n = a.length;
        solveText = "<h2><center>Нахождение обратной матрицы методом Гаусса</center></h2> ";
        solveText += "<p><h3>Дана матрица A:</h3></p>";

        solveText += PrintInHTML.printMatrix(a, 1);

        double[] b = new double[n];
        double[][] revA = new double[n][n];
        for (int i = 0; i < n; i++) {
            double[][] copy = new double[n][n];
            for (int t = 0; t < n; t++)
                System.arraycopy(a[t], 0, copy[t], 0, n);
            double[] xij = new double[n];
            b[i] = 1;
            for (int j = 0; j < n; j++)
                if (j != i)
                    b[j] = 0;

            Gauss gauss = new Gauss();
            solveText += gauss.getSolveText();

            xij = gauss.solve(copy, b, true);
            for (int k = 0; k < n; k++) {
                revA[k][i] = xij[k];
            }
            copy = null;
        }
        solveText += "<br /><h3>Обратная матрица:</h3><br />";

        solveText += PrintInHTML.printMatrix(revA, 10000);

        solveText += "<br /><h3>Проверка:</h3>";
        solveText += "<br />Умножим исходную матрицу<br />";

        solveText += PrintInHTML.printMatrix(a, 1);

        solveText += "<br />На обратную матрицу<br />";

        solveText += PrintInHTML.printMatrix(revA, 10000);

        double[][] check = MatrixActions.matrixMultiplication(a, revA);
        solveText += "<br />И получим:<br />";

        solveText += PrintInHTML.printMatrix(check, 10000);

        //для проверки(для отчета) вывести исходную а, рядом с ней revA - типа
        //мы их перемножаем и рядом следующую:


        return revA;
    }


}
