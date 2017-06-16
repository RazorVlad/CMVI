package gui.resources;

/**
 * Created by razor on 10.06.2017.
 */
public interface Constants {
    String HTML_START = "<html><head><title></title></head><body bgcolor=#C5D7FB><table border=0 align=center><tr><td width=800>";
    String HTML_TOP = "<img src=Top1.jpg border=0 width=800 height=200 alt=Факультет Информатики и управления align=top/>&nbsp;";
    String HTML_END = "</td></tr></table></body></html>";

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

//    String aboutText = "Программное обеспечение\n"
//            + "для решения математических задач численными методами\n\"\n"
//            + "\b\t<b>Разработчики:</b>\n"
//            + "Чирва Я.А.\n"
//            + "Крамаренко В.И.\n"
//            + "Мартынцева А.В.\n"
//            + "Затчаева К.В.\n"
//            + "Макарычева Е.В.\n"
//            + "\b\tРуководители:\n" + "ас.каф.СУ Бабич И.И.\n"
//            + "к.т.н., проф. каф. АСУ Гужва В.А.\n\n"
//            + "\bХарьков 2011/2012\n";
//
//    String aboutLabel = "О программе";

    String aboutText = "\u041f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u043d\u043e\u0435 \u043e\u0431\u0435\u0441\u043f\u0435\u0447\u0435\u043d\u0438\u0435 \n"
            + "\u0434\u043b\u044f \u0440\u0435\u0448\u0435\u043d\u0438\u044f \u043c\u0430\u0442\u0435\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438\u0445 \u0437\u0430\u0434\u0430\u0447 \u0447\u0438\u0441\u043b\u0435\u043d\u043d\u044b\u043c\u0438 \u043c\u0435\u0442\u043e\u0434\u0430\u043c\u0438 \n\n"
            + " \t \u0420\u0430\u0437\u0440\u0430\u0431\u043e\u0442\u0447\u0438\u043a\u0438:\n"
            + "\u0427\u0438\u0440\u0432\u0430 \u042f.\u0410.\n"
            + "\u041a\u0440\u0430\u043c\u0430\u0440\u0435\u043d\u043a\u043e \u0412.\u0418.\n"
            + "\u041c\u0430\u0440\u0442\u044b\u043d\u0446\u0435\u0432\u0430 \u0410.\u0412.\n"
            + "\u0417\u0430\u0442\u0447\u0430\u0435\u0432\u0430 \u041a.\u0412.\n"
            + "\u041c\u0430\u043a\u0430\u0440\u044b\u0447\u0435\u0432\u0430 \u0415.\u0412.\n"
            + "\t \u0420\u0443\u043a\u043e\u0432\u043e\u0434\u0438\u0442\u0435\u043b\u0438:\n" + "\u0430\u0441.\u043a\u0430\u0444.\u0421\u0423 \u0411\u0430\u0431\u0438\u0447 \u0418.\u0418.\n"
            + "\u043a.\u0442.\u043d., \u043f\u0440\u043e\u0444. \u043a\u0430\u0444. \u0410\u0421\u0423 \u0413\u0443\u0436\u0432\u0430 \u0412.\u0410.\n\n"
            + "\u0425\u0430\u0440\u044c\u043a\u043e\u0432 2011/2012\n";
}