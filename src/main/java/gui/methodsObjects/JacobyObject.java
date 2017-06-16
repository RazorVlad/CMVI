package gui.methodsObjects;

import gui.mainPane.MainFrame;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ResourceBundle;

/**
 * Created by razor on 08.06.2017.
 */
public class JacobyObject {
    private JTable resultTable;
    private math.Yacoby yacoby;

    public JTable getResultTable() {
        return resultTable;
    }

    public JacobyObject(JSpinner spinner_3, JSpinner spinner_2, JTable table, JTable table2) {

        yacoby = new math.Yacoby();

        int e = Integer.parseInt(spinner_3.getValue().toString());// считывание необходимого количества точек
        // после запятой
        int n = Integer.parseInt(spinner_2.getValue().toString());// Считывание размерности матрицы
        double[][] a2 = new double[n][n + 1];
        double[] x2 = new double[n];
        double eps = MainFrame.Eps;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {// Считывание значений из таблицы А
                a2[i][j] = Double.parseDouble(table.getValueAt(i, j).toString());
            }
        }
        for (int i = 0; i < n; i++) {
            a2[i][n] = Double.parseDouble(table2.getValueAt(i, 0).toString());
        }
        x2 = yacoby.solveJacoby(a2, eps, MainFrame.bundle);
        JTable resultTable = new JTable(1, table.getRowCount());// Создание таблицы
        // решений
        resultTable.setBounds(0, 0, 500, 473);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < n; i++) {
            resultTable.getColumnModel().getColumn(i).setMinWidth(45);
            resultTable.getColumnModel().getColumn(i).setMaxWidth(99999);
            resultTable.getColumnModel().getColumn(i).setPreferredWidth(35 + 10 * (e - 2));
        }
        try {
            for (int i = 0; i < table.getRowCount(); i++) {
                resultTable.getColumnModel().getColumn(i).setHeaderValue("x" + (i + 1));// подписывание колонок
                BigDecimal p = new BigDecimal(x2[i]); // Округление значений
                p = p.setScale(e, BigDecimal.ROUND_HALF_UP);// с требуемой точностью
                resultTable.getModel().setValueAt(p, 0, i);// Заполнение таблицы решений
            }
            resultTable.getTableHeader().resizeAndRepaint();
        } catch (Exception e1) {
            resultTable = new JTable(0, 0);// Создание таблицы решений
            JOptionPane.showMessageDialog(null, "Не забыть вставить приведение матрицы!",
                    "Напоминалка", JOptionPane.INFORMATION_MESSAGE);
        }
        MainFrame.methodSolution = yacoby.getSolveText();
        this.resultTable = resultTable;

    }
}
