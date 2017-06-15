package gui.methodsObjects;

import gui.mainPane.Methods;

import javax.swing.*;
import java.math.BigDecimal;

/**
 * Created by razor on 08.06.2017.
 */
public class DetObject {
    private JTable answertable;
    private math.Gauss g;

    public JTable getAnswertable() {
        return answertable;
    }

    public DetObject(JSpinner spinner_2, JSpinner spinner_3, JTable table) {

        g = new math.Gauss();

        final int n = Integer.parseInt(spinner_2.getValue().toString());
        final int e = Integer.parseInt(spinner_3.getValue().toString());
        double[][] a = new double[n][n];// Считывание значений таблицы
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                a[i][j] = Double.parseDouble(table.getValueAt(i, j).toString());
            }
        }
        double det = g.det(a);// Получение значения определителя
        JTable answerTable = new JTable(1, 1);// Создание таблицы для вывода определителя
        answerTable.setBounds(0, 0, 500, 473);
        answerTable.getColumnModel().getColumn(0).setHeaderValue("det");// подпись столбца определителя
        BigDecimal p = new BigDecimal(det); // округление значения определителя
        p = p.setScale(e, BigDecimal.ROUND_HALF_UP);// с заданной точностью
        answerTable.getModel().setValueAt(p, 0, 0);// Выставление значения определителя в таблицу
        answerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        answerTable.getTableHeader().resizeAndRepaint();
        Methods.methodSolution = g.getDet();
        this.answertable = answerTable;
    }
}
