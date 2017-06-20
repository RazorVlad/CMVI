package math;

/**
 * Created by razor on 16.06.2017.
 */
public class HelpMethods {
    // максимальный по модулю элемент массива
    public static int selectMax(double[] ai) {
        double max = 0;
        int maxIndex = 0;
        for (int i = 0; i < ai.length; i++) {
            if (Math.abs(ai[i]) > max) {
                max = Math.abs(ai[i]);
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static boolean notIn(int j, int[] zs, int until) {
        for (int i = 0; i < until; i++) {
            if (j == zs[i])
                return false;
        }
        return true;
    }

    // правда ли что в столбике все числа нули
    public static boolean allZeros(double[][] a, int j, int except) {
        for (int i = 0; i < a.length; i++) {
            if (j < a[i].length)
                if (i != except && a[i][j] != 0.0)
                    return false;
        }
        return true;
    }

    public static boolean thereAreZeros(double[][] a) {
        int n = a.length;
        for (double[] anA : a) {
            for (int k = 0; k < n; k++)
                if (anA[k] == 0)
                    return true;
        }
        return false;
    }
}
