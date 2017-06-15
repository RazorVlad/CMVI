package gui.methodsObjects;

import gui.mainPane.Methods;

import javax.swing.*;
import java.math.BigDecimal;

/**
 * Created by razor on 08.06.2017.
 */
public class GaussObject {
    private JTable answerTable;
    private math.Gauss g;

    public JTable getAnswerTable() {
        return answerTable;
    }

    public GaussObject(JSpinner spinner_3, JSpinner spinner_2, JTable table, JTable table2) {
        g = new math.Gauss();
        int e = Integer.parseInt(spinner_3.getValue().toString());// считывание необходимого количества точек после
        // запятой
        int n = Integer.parseInt(spinner_2.getValue().toString());// Считывание размерности матрицы
        double[][] a = new double[n][n];
        double[] b = new double[n];
        double[] x = new double[n];
        try {
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a.length; j++) {// Считывание значений из таблицы А
                    a[i][j] = Double.parseDouble(table.getValueAt(i, j).toString());
                }
                b[i] = Double.parseDouble(table2.getValueAt(i, 0).toString());// Считывание значений из таблицы В
            }
        } catch (Exception ee) {
            JOptionPane.showMessageDialog(null, "Проверьте вводимые данные!", "Ошибка",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        x = g.solve(a, b);
        JTable answerTable = new JTable(1, table.getRowCount());// Создание таблицы решений
        answerTable.setBounds(0, 0, 500, 473);
        answerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < n; i++) {
            answerTable.getColumnModel().getColumn(i).setMinWidth(45);
            answerTable.getColumnModel().getColumn(i).setMaxWidth(99999);
            answerTable.getColumnModel().getColumn(i).setPreferredWidth(35 + 10 * (e));
        }
        for (int i = 0; i < table.getRowCount(); i++) {
            answerTable.getColumnModel().getColumn(i).setHeaderValue("x" + (i + 1));// подписывание колонок
            BigDecimal p = new BigDecimal(x[i]); // Округление значений
            p = p.setScale(e, BigDecimal.ROUND_HALF_UP);// с требуемой точностью
            answerTable.getModel().setValueAt(p, 0, i);// Заполнение таблицы решений
        }
        answerTable.getTableHeader().resizeAndRepaint();
        Methods.methodSolution = g.getGauss();
        this.answerTable = answerTable;
    }
}
