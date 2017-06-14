package gui.resources;

/**
 * Created by razor on 10.06.2017.
 */
public interface MethodNames {
//    String[] methodNames = new String[]{
//            "",//0
//            "Решение СЛАУ методом Гаусса",//1
//            "Решение СЛАУ методом Якоби(простой итерации)",//2
//            "Решение СЛАУ методом Гаусса-Зейделя",//3
//            "Нахождение определителя матрицы методом Гаусса",//4
//            "Нахождение обратной матрицы методом Гаусса",//5
//            "",
//            "Метод простой итерации(нелинейные уравнения)",//7
//            "Метод Ньютона",//8
//            "Метод Крылова",//9
//            "Интерполяция Лагранжа",//10
//            "Метод трапеций",//11
//            "Формула Симпсона (Метод парабол)",//12
//            "Метод прогонки"//13
//    };
    String[] methodNames = new String[]{
            "",//0
            "methods.SLAU.Gauss",//1
            "methods.SLAU.Yacoby",//2
            "methods.SLAU.Gauss-Seidel",//3
            "methods.det.Gauss",//4
            "methods.reverse.Gauss",//5
            "",
            "methods.iterations",//7
            "methods.Newton",//8
            "methods.Krylov",//9
            "methods.interpolation.Lagrange",//10
            "methods.trapezium",//11
            "methods.Simpson",//12
            "methods.progonka"//13
    };
    String[] methodGroupsNames = new String[]{
            "methodsGroup.linearAlgebra",//методы линейной алгебры
            "methodsGroup.NonlinearEquations",//Методы решения нелинейных уравнений
            "methodsGroup.matrixEigenvaluesAndEigenvectors",
            "methodsGroup.interpolation",
            "methodsGroup.NumericalIntegration",//Численное интегрирование
            "methodsGroup.DifferentialEquations"//Краевые задачи для обыкновенных дифференциальных уравнений
    };
    int[][] methodsNumbers = new int[][]{
            {1, 2, 3, 4, 5},
            {7, 8},
            {9},
            {10},
            {11, 12},
            {13}
    };

    String[] buttonLabels = new String[]{
            "+", "-", "*", "/", "\u221A",
            "exp", "n!", "mod", "\u03C0", "x^y",
            "ln", "sin", "cos", "tg", ",",
            "log(x, y)", "sh", "ch", "th", "x",
            "lg", "arcsin", "arccos", "arctg", "(",
            "()", "arcsh", "arcch", "arcth", ")"
    };
    String[] buttonFuntions = new String[]{
            "+", "-", "*", "/", "sqrt()",
            "e", "fact()", "mod()", "Pi", "^",
            "ln()", "sin()", "cos()", "tan()", ".",
            "log(, )", "Sinh()", "cosh()", "Tanh()", "x",
            "lg()", "Asin()", "Acos()", "Atan()", "(",
            "()", "Asinh()", "arcch()", "arcth()", ")"};
}