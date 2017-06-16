package gui.methodsObjects;

import gui.mainPane.MainFrame;
import math.gauss.ReverseMatrix;

import javax.swing.*;
import java.math.BigDecimal;

/**
 * Created by razor on 08.06.2017.
 */
public class ReverseGaussObject {

    public JTable getTable2() {
        return table2;
    }

    private ReverseMatrix g;
    private JTable table2;

    public ReverseGaussObject(JSpinner spinner, JSpinner spinner_1, JTable table) {
        g = new ReverseMatrix();
        int n = Integer.parseInt(spinner.getValue().toString());
        double[][] revA;
        double[][] a = new double[n][n];// Считывание значений таблицы
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                a[i][j] = Double.parseDouble(table.getValueAt(i, j).toString());
            }
        }
        JTable table2 = new JTable(n, n);
        int m = Integer.parseInt(spinner_1.getValue().toString());
        for (int i = 0; i < n; i++) {
            table2.getColumnModel().getColumn(i).setMinWidth(35);
            table2.getColumnModel().getColumn(i).setMaxWidth(999999);
            table2.getColumnModel().getColumn(i).setPreferredWidth(35 + 10 * (m - 2));
        }
        for (int i = 0; i < n; i++)
            table2.getColumnModel().getColumn(i).setHeaderValue("x" + (i + 1));// выставление
        // заглавий
        // колонок
        // матрицы Х
        table2.getTableHeader().resizeAndRepaint();
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table2.setBounds(141, 12, 438, 305);
//        scrollPane_4.setViewportView(table2);
        revA = g.reverseMatrix(a);
        for (int i = 0; i < revA.length; i++) {
            for (int j = 0; j < revA.length; j++) {
                table2.getColumnModel();
                BigDecimal p = new BigDecimal(revA[i][j]); // Округление
                // значений
                p = p.setScale(m, BigDecimal.ROUND_HALF_UP);// с
                // требуемой
                // точностью
                table2.getModel().setValueAt(p, i, j);
            }
        }
        table2.getTableHeader().resizeAndRepaint();

        MainFrame.methodSolution = g.getSolveText();
        this.table2 = table2;
    }
}
