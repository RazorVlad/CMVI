package math.gauss.lineExchange;

import math.HelpMethods;

/**
 * Created by razor on 16.06.2017.
 */
public class LineExchange {
    public static int lineExchange(double[][] a) {
        int n = a.length;
        int count = 0;
        double[][] ac = new double[n][n];
        for (int i = 0; i < n; i++)
            System.arraycopy(a[i], 0, ac[i], 0, n);
        double max;
        int maxInd;
        for (int j = 0; j < n; j++) {
            max = 0;
            maxInd = j;
            for (int i = 0; i < n; i++) {
                if (ac[i] == null)
                    continue;
                if (Math.abs(ac[i][j]) > max) {
                    max = Math.abs(ac[i][j]);
                    maxInd = i;
                    count++;
                }
            }
            System.arraycopy(ac[maxInd], 0, a[j], 0, n);
            ac[maxInd] = null;
        }
        return count;
    }

    public static int lineExchange(double[][] a, double[] b) {

        int n = a.length;
        System.out.println("before line exhange");
        for (int ii = 0; ii < n; ii++) {
            for (int j = 0; j < n; j++)
                System.out.println("a" + ii + j + " : " + a[ii][j]);
        }
        for (int j = 0; j < n; j++)
            System.out.println("b" + j + " : " + b[j]);

        if (!HelpMethods.thereAreZeros(a)) {
            // this.trueIndexes = new int[n];
            int count = 0;
            double[][] ac = new double[n][n];
            for (int i = 0; i < n; i++)
                System.arraycopy(a[i], 0, ac[i], 0, n);
            double[] bc = new double[n];
            System.arraycopy(b, 0, bc, 0, n);
            double max;
            int maxInd;
            for (int j = 0; j < n; j++) {
                max = 0;
                maxInd = j;
                for (int i = 0; i < n; i++) {
                    if (ac[i] == null)
                        continue;
                    if (Math.abs(ac[i][j]) > max) {
                        max = Math.abs(ac[i][j]);
                        maxInd = i;
                        count++;
                    }
                }
                System.arraycopy(ac[maxInd], 0, a[j], 0, n);
                b[j] = bc[maxInd];
                ac[maxInd] = null;
                // trueIndexes[maxInd] = j;
            }
            System.out.println("after line exhange");
            for (int ii = 0; ii < n; ii++) {
                for (int k = 0; k < n; k++)
                    System.out.println("a" + ii + k + " : " + a[ii][k]);
            }
            for (int k = 0; k < n; k++)
                System.out.println("b" + k + " : " + b[k]);
            return count;
        } else
            return CarefulLineExchange.carefulLineExchange(a, b);

    }
}
