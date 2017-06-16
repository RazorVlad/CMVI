package math;

import math.gauss.Gauss;

public class Krylov {

    private double[][] a, leftB;
    private double[] rightB;
    double[] coef;
    double[][] vectors;// собственные векторы
    double[] numbers;// собственные значения
    String krylov = "<h2><center>Нахождение собственных значений и собственного вектора матрицы с помощью метода Крылова</center></h2>";

    public double[][] getVectors() {
        return vectors;
    }

    public void setVectors(double[][] vectors) {
        this.vectors = vectors;
    }

    public double[] getNumbers() {
        return numbers;
    }

    public void setNumbers(double[] numbers) {
        this.numbers = numbers;
        for (int i = 0; i < numbers.length; i++)
            System.out.println("number " + i + " " + numbers[i]);
    }

    public void setKrylov(String krylov) {
        this.krylov = krylov;
    }

    public String getKrylov() {
        return krylov;
    }

    public double[][] getLeftB() {
        return leftB;
    }

    public void setLeftB(double[][] leftB) {
        this.leftB = leftB;
    }

    public double[] getRightB() {
        return rightB;
    }

    public void setRightB(double[] rightB) {
        this.rightB = rightB;
    }

    public double[][] getA() {
        return a;
    }

    public void setA(double[][] a) {
        this.a = a;
    }

    public Krylov(double[][] a) {
        this.a = a;
    }

    // строит вспомогательную систему, не трогать!
    private void makeBsystem() {
        int n = getA().length;
        double[] x0 = new double[n];
        x0[0] = 1;
        for (int i = 1; i < n; i++)
            x0[i] = 1;
        double[][] transLeft = new double[n][n];
        double[][][] powA = new double[n][n][n];
        powA[0] = getA();
        for (int t = 0; t < n; t++)
            for (int k = 0; k < n; k++)
                System.out.println("powA 0 " + t + k + " : " + powA[0][t][k]);
        for (int i = 1; i < n; i++) {
            powA[i] = MatrixActions.matrixMultiplication(getA(), powA[i - 1]);
            for (int t = 0; t < n; t++)
                for (int k = 0; k < n; k++)
                    System.out.println("powA " + i + " " + t + k + " : "
                            + powA[i][t][k]);
        }
        for (int i = 0; i < n - 1; i++) {
            transLeft[i] = MatrixActions.vectMatrMultiplication(powA[n - i - 2],
                    x0);
        }
        for (int i = 0; i < n; i++)
            transLeft[n - 1][i] = x0[i];
        setLeftB(MatrixActions.transMatrix(transLeft));
        setRightB(MatrixActions.vectMatrMultiplication(powA[n - 1], x0));

    }

    // ищем собственные значения
    public void findNumbers(double[][] arr) {
        //makeBsystem();
        int arrl = arr.length;
        double[][] left = new double[arrl][arrl];
        double[] right = new double[arrl];
        for (int i = 0; i < arrl; i++) {
            for (int j = 0; j < arrl - 1; j++) {
                left[i][j] = Math.pow(arr[i][i], arrl - j - 1);
            }
            left[i][arrl - 1] = 1;
        }
        for (int i = 0; i < arrl; i++) {
            right[i] = -Math.pow(arr[i][i], arrl);
        }
        Gauss g = new Gauss();
        //coef = g.solve(getLeftB(), getRightB());
        coef = g.solve(left, right, true);
        int n = coef.length;
        String s = "";
        double coeff;
        for (int i = 0; i < n; i++) {
            s += "1";
            for (int j = i; j < n; j++)
                s += "*x";
            if (i != 0) {
                coeff = Math.round(coef[/* g.getTrueIndexes()[n - 1] */i - 1] * 1000);
                s += "*(" + (coeff / 1000) + ")+";
            } else
                s += "+";
        }
        coeff = Math.round(coef[/* g.getTrueIndexes()[n - 1] */n - 1] * 1000);
        s += "(" + (coeff / 1000) + ")";
        System.out.println(s);
        setKrylov(s);
//		Expression z = ExpressionTree.parse(s);
//		Newton newton = new Newton(z);
//		numbers = newton.solve();
//		for (int i = 0; i < numbers.length; i++)
//			System.out.println("number " + i + " " + numbers[i]);

    }

    public double minNumber() {
        if (numbers.length == 0)
            return 0.0;
        double min = numbers[0];
        for (int i = 0; i < numbers.length; i++)
            if (numbers[i] < min)
                min = numbers[i];
        return min;
    }

    public double maxNumber() {
        if (numbers.length == 0)
            return 0.0;
        double max = numbers[0];
        for (int i = 0; i < numbers.length; i++)
            if (numbers[i] > max)
                max = numbers[i];
        return max;
    }

    // ищем собственные векторы, ГЛАВНАЯ, НАЙДЕТ ВСЕ
    public void findVectors() {
        //findNumbers();
        if (numbers.length == 0) {
            System.out.println("У матрицы нет действительных "
                    + "собственных значений и, соответственно,"
                    + "мы не можем найти ее собственные векторы.");
            vectors = null;
        } else if (numbers.length == 3 && numbers.length == getA().length) {
            int n = numbers.length;
            // int m = getA().length;
            vectors = new double[n][n];
            double[][] beta = new double[n][n];
            for (int i = 0; i < n; i++)
                beta[i][1] = 0;
            for (int i = 0; i < n; i++)
                beta[i][0] = 1;
            for (int i = 0; i < n; i++) {
                beta[i][2] = 1;
                for (int j = 0; j < n; j++)
                    if (i != j) {
                        beta[i][1] -= numbers[j];
                        beta[i][2] *= numbers[j];
                    }
            }

            double[][] x = MatrixActions.transMatrix(leftB);
            double[][] y = new double[n][n];
            for (int i = 0; i < n; i++)
                for (int k = 0; k < n; k++)
                    y[i][k] = 0;
            for (int i = 0; i < n; i++)
                for (int k = 0; k < n; k++)
                    for (int j = 0; j < n; j++)
                        y[i][k] += beta[i][j] * x[j][k];
            vectors = y;

            // //////////////////////////////////////////////
            // double[][] beta = new double[n][m-1];
            // for(int i = 0; i < n; i++){
            // beta[i][0] = coef[0] + numbers[i];
            // for(int j = 1; j < m-1; j++){
            // beta[i][j] = beta[i][j-1]*numbers[i];
            // }
            // }
            //
            // double [][][] x = new double[n][m-1][m];
            // double[][] transLeft = GaussSeidel.transMatrix(leftB);
            // for(int i = 0; i < m-1; i++){
            // for(int j = 0; j < n; j++)
            // System.arraycopy(transLeft[i], 0, x[j][i], 0, m);
            // }
            // for(int i = 0; i < n; i++){
            // for(int j = 0; j < m-1; j++){
            // for(int k = 0; k < m; k++)
            // x[i][j][k] *= beta[i][j];
            // }
            // } 3   1   1
            //
            // double[] y = new double[n];
            // for(int i = 0; i < n; i++)
            // y[i] = 0;
            // ///////////////////////////////////////////
            /*
			 * for(int j = 0; j < m; j++) for(int k = 0; k < m-1; k++) y[] +=
			 * x[k][j];
			 */
        }
    }

    public static void main(String[] args) {
        double[][] a = {{2, 0, -1}, {1, 1, -1}, {-1, 0, 2}};
        Krylov k = new Krylov(a);
        //k.findNumbers();

    }

}
