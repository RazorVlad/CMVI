package gui.methodsObjects;

import graphbuilder.math.Expression;
import graphbuilder.math.ExpressionTree;
import gui.Methods;
import gui.methodsPanes.IntegralPane;

import javax.swing.*;
import java.math.BigDecimal;

/**
 * Created by razor on 08.06.2017.
 */
public class TrapeziumObject {
    private JTable table2;
    private math.Trapezium trap;

    public JTable getTable2() {
        return table2;
    }

    public TrapeziumObject(IntegralPane integralPane) {
        double a = 0, b = 10;
        double c;
        try {
            a = Double.parseDouble(integralPane.getTextField_1Text());
            b = Double.parseDouble(integralPane.getTextField_2Text());
        } catch (Exception ee) {
            JOptionPane.showMessageDialog(null, "\u041D\u0435\u0432\u0435\u0440\u043D\u044B\u0439\u0020\u0438\u043D\u0442\u0435\u0440\u0432\u0430\u043B!", "\u041E\u0448\u0438\u0431\u043A\u0430",// нарушены
                    // условия сходимости метода
                    JOptionPane.INFORMATION_MESSAGE);
        }
        Expression z = ExpressionTree.parse(integralPane.getTextFieldS());
        trap = new math.Trapezium(z, a, b);
        trap.setEps(gui.Methods.Eps);
        c = trap.integral();
        JTable table2 = new JTable(1, 1);
        table2.getColumnModel().getColumn(0).setHeaderValue("\u0417\u043D\u0430\u0447\u0435\u043D\u0438\u0435\u0020\u0438\u043D\u0442\u0435\u0433\u0440\u0430\u043B\u0430");// выставление
//        System.out.println("заголовок=" + "\u0417\u043D\u0430\u0447\u0435\u043D\u0438\u0435\u0020\u0438\u043D\u0442\u0435\u0433\u0440\u0430\u043B\u0430");
        // заглавий колонок матрицы Х
        table2.getTableHeader().resizeAndRepaint();
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table2.setBounds(0, 0, 438, 305);
//        scrollPane_6.setViewportView(table2);
        table2.getColumnModel();
        table2.getColumnModel().getColumn(0).setMinWidth(25);
        table2.getColumnModel().getColumn(0).setMaxWidth(200);
        table2.getColumnModel().getColumn(0).setPreferredWidth(100);
        BigDecimal p = new BigDecimal(c); // Округление значений
        p = p.setScale(4, BigDecimal.ROUND_HALF_UP);// с требуемой точностью
        table2.getModel().setValueAt(p, 0, 0);
        table2.getTableHeader().resizeAndRepaint();

        Methods.methodSolution = trap.getTrapezium();
        this.table2 = table2;
        // table_2.getModel().setValueAt(c, 0, 1);


    }
}
