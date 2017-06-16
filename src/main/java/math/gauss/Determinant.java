package math.gauss;

import math.PrintInHTML;
import math.gauss.lineExchange.LineExchange;

/**
 * Created by razor on 16.06.2017.
 */
public class Determinant {
    public String solveText;

    public String getSolveText() {
        return solveText;
    }

    public double det(double[][] a) {
        double det = 1, d = 0;
        int n = a.length, count = 0;
        String DETit = "";
        solveText = "<h2><center>Нахождение определителя методом Гаусса</center></h2> <p><h3>Дана матрица A:</h3></p>";
        solveText += PrintInHTML.printMatrix(a, 1);
        //проверим, надо ли менять местами строки
        int q = 0;
        for (int i = 0; i < n; i++) {
            if (a[i][i] == 0) {// значит надо и это надо добавить в отчет
                count = LineExchange.lineExchange(a);
                q++;// это теперь наша исходная матрица
            }
        }
        if (q > 0) {
            solveText += "<br /><h3>Меняем строки таблицы:</h3>";
            solveText += PrintInHTML.printMatrix(a, 1);
        }
        for (int i = 0; i < n; i++) {
            solveText += "<h3><i>Итерация № " + (i + 1) + "</i></h3>";

            for (int j = i + 1; j < n; j++) {
                a[i][j] /= a[i][i];
            }

            d = Math.round(a[i][i] * 100);
            double z = Math.round(a[i][i] * 100);
            double x = Math.round(det * 100);
            if (i == 0)
                DETit += (z /= 100);
            else
                DETit += " * " + (z /= 100);
            det *= a[i][i];
            System.out.println("Определитель = " + det);
            solveText += "<br />Делим строку " + (i + 1) + " на элемент A[" + (i + 1)
                    + "][" + (i + 1) + "]<br />";
            if (i + 1 < n) {
                solveText += "Домножаем строку " + (i + 1) + " на элемент А["
                        + (i + 2) + "][" + (i + 1) + "]<br />";
                solveText += "Отнимаем от строки " + (i + 2) + " строку " + (i + 1)
                        + "<br />";
            }
            a[i][i] = 1;
            for (int k = i + 1; k < n; k++) {
                for (int j = i + 1; j < n; j++) {
                    a[k][j] -= a[i][j] * a[k][i];
                }
                a[k][i] = 0;

            }

            solveText += PrintInHTML.printMatrix(a, 100);
        }
        solveText += "<br /><h3>Определитель = " + DETit + " = " + det + "</h3>";
        solveText += "<p></p>";
        if (count % 2 != 0)
            return 0 - det;
        else
            return det;
    }
}
