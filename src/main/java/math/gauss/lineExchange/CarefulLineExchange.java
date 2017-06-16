package math.gauss.lineExchange;

import math.HelpMethods;

/**
 * Created by razor on 16.06.2017.
 */
public class CarefulLineExchange {
    // меняет местами строки, реализует выбор ведущго элемента
    public static int carefulLineExchange(double[][] a, double[] b) {
        int n = a.length;
        int count = 0;
        double[][] ac = new double[n][n];
        for (int i = 0; i < n; i++)
            System.arraycopy(a[i], 0, ac[i], 0, n);
        double[] bc = new double[n];
        System.arraycopy(b, 0, bc, 0, n);

        int[][] zInd = new int[n][n]; // массив индексов нулевых элементов
        int zIndCount[] = new int[n]; // количествo нулей в строках
        int[] zcDown = new int[n]; // очередь индексов по убыванию количества
        // нулей
        // ///////////////////////////////
        // заполняем zInd и zIndCount
        for (int i = 0; i < n; i++) {
            zIndCount[i] = 0;
            for (int j = 0; j < n; j++) {
                if (a[i][j] == 0) {
                    zInd[i][zIndCount[i]] = j;
                    zIndCount[i]++;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.println("zInd " + i + j + " " + zInd[i][j]);
            System.out.println("zIndCount " + i + " " + zIndCount[i]);
        }
        // ///////////////////////////////
        // заполняем zcDown
        int[] copy = new int[n];
        System.arraycopy(zIndCount, 0, copy, 0, n);
        for (int t = 0; t < n; t++)
            System.out.println("copy " + t + " " + copy[t]);
        for (int i = 0; i < n; i++) {
            int mv = -1, ind = 0;
            for (int j = 0; j < n; j++) {
                if (copy[j] > mv) {
                    mv = copy[j];
                    ind = j;
                }
            }
            copy[ind] = -1;
            zcDown[i] = ind;
            for (int t = 0; t < n; t++)
                System.out.println("copy " + t + " " + copy[t]);
        }
        for (int i = 0; i < n; i++) {
            System.out.println("zcDown " + i + " " + zcDown[i]);
            for (int j = 0; j < n; j++)
                System.out.println("a" + zcDown[i] + j + " : " + a[zcDown[i]][j]);
        }
        // ////////////////////////////////
        int[] taken = new int[n];
        int ct = 0;
        for (int iii = 0; iii < n; iii++) {
            for (int j = 0; j < n; j++)
                a[iii][j] = 0;
            b[iii] = 0;
        }
        for (int ii = 0; ii < n; ii++) {
            int i = zcDown[ii];
            if (zIndCount[i] != 0) { // если в строке есть нули
                for (int j = 0; j < n; j++) { // пошли по ее элементам
                    if (HelpMethods.notIn(j, zInd[i], zIndCount[i]) && HelpMethods.notIn(j, taken, ct)) // если не ноль
                        if (HelpMethods.allZeros(a, j, i)) { // и все кроме него в столбце
                            // нули
                            System.arraycopy(ac[i], 0, a[j], 0, n);
                            b[j] = bc[i];
                            taken[ct] = j;
                            ct++;
                            ac[i] = null;
                            if (i != j)
                                count++;
                            System.out.println("one");
//							if (ct == n)
//								return count;
                        }
                }
                if (ac[i] != null) {
                    int j = HelpMethods.selectMax(ac[i]);
                    if (HelpMethods.notIn(j, taken, ct)) {
                        System.arraycopy(ac[i], 0, a[j], 0, n);
                        b[j] = bc[i];
                        taken[ct] = j;
                        ct++;
                        ac[i] = null;
                        if (i != j)
                            count++;
                        System.out.println("two");
//						if (ct == n)
//							return count;
                    } else
                        for (int k = 0; k < n; k++) {
                            if (ac[i] == null && ac[i][k] != 0 && HelpMethods.notIn(k, taken, ct)) {
                                System.arraycopy(ac[i], 0, a[k], 0, n);
                                b[k] = bc[i];
                                taken[ct] = k;
                                ct++;
                                ac[i] = null;
                                if (i != j)
                                    count++;
                                System.out.println("three");
//								if (ct == n)
//									return count;
                            }
                        }
                }
            } else {
                if (ct == n)
                    return count;
                else {
                    int j = HelpMethods.selectMax(ac[i]);
                    if (HelpMethods.notIn(j, taken, ct)) {
                        System.arraycopy(ac[i], 0, a[j], 0, n);
                        b[j] = bc[i];
                        taken[ct] = j;
                        ct++;
                        ac[i] = null;
                        if (i != j)
                            count++;
                        System.out.println("four");
//						if (ct == n)
//							return count;
                    } else
                        for (int k = 0; k < n; k++) {
                            if (ac[i] != null && ac[i][k] != 0 && HelpMethods.notIn(k, taken, ct)) {
                                System.arraycopy(ac[i], 0, a[k], 0, n);
                                b[k] = bc[i];
                                taken[ct] = k;
                                ct++;
                                ac[i] = null;
                                if (i != k)
                                    count++;
                                System.out.println("five");
//								if (ct == n)
//									return count;
                            }
                        }
                }
            }
            for (int j = 0; j < n; j++)
                System.out.println("taken" + j + " : " + taken[j]);
            System.out.println("ct " + ct);
        }

        return count;
    }
}
