package gui.methodsObjects;

import gui.mainPane.MainFrame;

import javax.swing.*;
import java.math.BigDecimal;

/**
 * Created by razor on 08.06.2017.
 */
public class GaussSeidelObject {

    public JTable getAnswerTable() {
        return answerTable;
    }

    private JTable answerTable;
    private math.GaussSeidel gz;

    public GaussSeidelObject(JSpinner spinner_3, JSpinner spinner_2, JTable table, JTable table2) {

        gz = new math.GaussSeidel();
        int e = Integer.parseInt(spinner_3.getValue().toString());// считывание необходимого количества
        // точек после запятой
        int n = Integer.parseInt(spinner_2.getValue().toString());// Считывание размерности матрицы
        double[][] a2 = new double[n][n + 1];
        double[] x2 = new double[n];
        double eps = 0;
        for (int i = 0; i < e; i++)
            eps = 1 / 10;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {// Считывание значений из таблицы А
                a2[i][j] = Double.parseDouble(table.getValueAt(i, j).toString());
            }
        }
        for (int i = 0; i < n; i++) {
            a2[i][n] = Double.parseDouble(table2.getValueAt(i, 0).toString());
        }
        // x2=h.solve(a, b);
        x2 = gz.solveSeidel(a2, eps);
        JTable answerTable;
        if (x2 == null) {
            answerTable = new JTable(0, 0);// Создание таблицы решений
//            scrollPane_1.setViewportView(answerTable);
            JOptionPane.showMessageDialog(null, "У матрицы условий нет действительных "
                    + "собственных значений и приведение к виду, "
                    + "удобному для итераций невозможно. Попробуйте решить"
                    + " эту систему методом Гаусса.", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
        } else {
            answerTable = new JTable(1, table.getRowCount());// Создание таблицы решений
            answerTable.setBounds(0, 0, 500, 473);
            answerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for (int i = 0; i < n; i++) {
                answerTable.getColumnModel().getColumn(i).setMinWidth(25);
                answerTable.getColumnModel().getColumn(i).setMaxWidth(99999);
                answerTable.getColumnModel().getColumn(i).setPreferredWidth(45 + 10 * (e - 2));
            }
            try {
                for (int i = 0; i < table.getRowCount(); i++) {
                    answerTable.getColumnModel().getColumn(i).setHeaderValue("x" + (i + 1));// подписывание колонок
                    BigDecimal p = new BigDecimal(x2[i]); // Округление значений
                    p = p.setScale(e, BigDecimal.ROUND_HALF_UP);// с требуемой точностью
                    answerTable.getModel().setValueAt(p, 0, i);// Заполнение таблицы решений
                }
                answerTable.getTableHeader().resizeAndRepaint();
            } catch (Exception e1) {
                answerTable = new JTable(0, 0);// Создание таблицы решений
                JOptionPane.showMessageDialog(null, "Не забыть вставить приведение матрицы!",
                        "Напоминалка", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        MainFrame.methodSolution = gz.getSolveText();
        this.answerTable = answerTable;
    }
}
