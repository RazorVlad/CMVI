package gui.methodsObjects;

import Jama.Matrix;
import gui.mainPane.MainFrame;

import javax.swing.*;
import java.math.BigDecimal;

/**
 * Created by razor on 08.06.2017.
 */
public class KrylovObject {
    public JTable getTable() {
        return table;
    }

    private math.Krylov kr;
    private JTable table;

    public String getAnswer() {
        return answer;
    }

    public String getSolveText() {

        return solveText;
    }

    public JTable getTable2() {

        return table2;
    }

    private JTable table2;
    private String solveText;
    private String answer;

//    public KrylovObject(JTable table, JTable table2, String solveText, String answer) {
//        this.table = table;
//        this.table2 = table2;
//        this.solveText = solveText;
//        this.answer = answer;
//    }

    public KrylovObject(JSpinner spinner_6, JSpinner spinner_5, JTable table9) {
        String krylov_text = "";
        int e1 = Integer.parseInt(spinner_6.getValue().toString());// считывание необходимого количества точек
        // после запятой
        int n = Integer.parseInt(spinner_5.getValue().toString());// Считывание размерности матрицы
        double[][] a = new double[n][n];
        double[][] x;// = new double[n];
        double[][] x1;// = new double[n][n];
        try {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++)
                    // Считывание значений из таблицы А
                    a[i][j] = Double.parseDouble(table9.getValueAt(i, j).toString());
            }
        } catch (Exception ee) {
            JOptionPane.showMessageDialog(null, "Проверьте вводимые данные!", "Ошибка",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        Matrix matrA = new Matrix(a);
        Matrix eiVal = new Matrix(matrA.eig().getD().getArray());
        Matrix vect = new Matrix(matrA.eig().getV().getArray());

        x = eiVal.getArray();
        x1 = vect.getArray();
        kr = new math.Krylov(a);
        kr.findNumbers(x);
        krylov_text = "<h2><center>Нахождение собственных значений и собственного вектора матрицы с помощью метода Крылова</center></h2>";
        krylov_text += "<br>Исходная матрица:";
        krylov_text += "<table border=1>";
        for (int i = 0; i < n; i++) {
            krylov_text += "<tr>";
            for (int j = 0; j < n; j++) {
                krylov_text += "<td>" + a[i][j];
            }
        }
        krylov_text += "</table>";
        krylov_text += "<br><h3>Уравнение, корнями которого являются собственные значения матрицы:</h3>";
        krylov_text += kr.getKrylov();
        krylov_text += "<br><br><h3>Собственные значения:</h3>";
        krylov_text += "<table>";
        for (int i = 0; i < x.length; i++) {
            krylov_text += "<tr>" + (i + 1) + ") " + x[i][i];
        }
        krylov_text += "</table>";
        // //////////////////////////////////////
        krylov_text += "<br><h3>Собственные вектора:</h3>";
        krylov_text += "<table>";
        for (int i = 0; i < x1.length; i++) {
            krylov_text += "<tr>" + "X" + (i + 1);
            for (int j = 0; j < x1.length; j++) {
                krylov_text += "<td>" + x1[j][i] + " ; ";
            }
        }
        krylov_text += "</table>";

        JTable table1 = new JTable(x1.length, 2);// Создание таблицы решений
        table1.setBounds(0, 0, 500, 473);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table1.getColumnModel().getColumn(1).setMinWidth(45);
        table1.getColumnModel().getColumn(1).setMaxWidth(10000);
        table1.getColumnModel().getColumn(1).setPreferredWidth(35 + 10 * (e1));
        table1.getColumnModel().getColumn(0).setPreferredWidth(35);

        for (int i = 0; i < x.length; i++) {
            BigDecimal p = new BigDecimal(x[i][i]); // Округление значений с требуемой точностью
            p = p.setScale(e1, BigDecimal.ROUND_HALF_UP);//
            table1.getModel().setValueAt((1 + i), i, 0);
            table1.getModel().setValueAt(p, i, 1);// Заполнение таблицы решений
        }
        for (int i = 0; i < table1.getColumnCount(); i++)
            table1.getColumnModel().getColumn(i).setHeaderValue("");
        table1.getTableHeader().resizeAndRepaint();
//		textField_3.setText(kr.getKrylov());
        JTable table2 = new JTable(n, n + 1);
        for (int i = 0; i < n + 1; i++) {
            table2.getColumnModel().getColumn(i).setMinWidth(35);
            table2.getColumnModel().getColumn(i).setMaxWidth(10000);
            table2.getColumnModel().getColumn(i).setPreferredWidth(35 + 10 * (e1 - 2));
        }
        table2.getColumnModel().getColumn(0).setPreferredWidth(35);
        for (int i = 0; i < table2.getColumnCount(); i++)
            table2.getColumnModel().getColumn(i).setHeaderValue("");// выставление заглавий колонок матрицы Х
        table2.getTableHeader().resizeAndRepaint();

        table2.setBounds(141, 12, 438, 305);
        for (int i = 0; i < x1.length; i++) {
            for (int j = 0; j < x1.length; j++) {
                table2.getColumnModel();
                BigDecimal p = new BigDecimal(x1[j][i]); // Округление значений
                p = p.setScale(e1, BigDecimal.ROUND_HALF_UP);// с требуемой точностью
                table2.getModel().setValueAt(p, i, j + 1);
            }
            table2.getModel().setValueAt("x" + (i + 1), i, 0);
        }
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table2.getTableHeader().resizeAndRepaint();

        MainFrame.methodSolution = kr.getKrylov();
        this.table = table1;
        this.table2 = table2;
        this.answer = kr.getKrylov();
        this.solveText = krylov_text;
    }

}
