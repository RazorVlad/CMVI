package math;

/**
 * Created by razor on 16.06.2017.
 */
public class PrintInHTML {
    public static String printMatrix(double[][] a, int roundValuesBy) {
        String matrix = "";
        matrix += "<table border=1>";
        for (int i = 0; i < a.length; i++) {
            matrix += "<tr>";
            for (int j = 0; j < a.length; j++) {
                matrix += "<td>";

                double y1;
                y1 = Math.round(a[i][j] * roundValuesBy);

                matrix += ((y1 / roundValuesBy) + "   ");
                matrix += "</td>";
            }
            matrix += "</tr>";
        }
        matrix += "</table>";
        return matrix;
    }

    public static String printAandBMatrix(double[][] a) {
        String solveText = "";
        solveText += "<table border=1>";
        for (int i = 0; i < a.length; i++) {
            solveText += "<tr>";
            for (int j = 0; j < a.length; j++) {
                solveText += "<td>";
                solveText += (a[i][j] + "*X" + (j + 1) + " ");
            }
            solveText += "<td>";
            solveText += "= " + a[i][a.length] + "<br>\n";
        }
        solveText += "</table>";
        return solveText;
    }
}
