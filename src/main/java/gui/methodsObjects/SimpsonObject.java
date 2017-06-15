package gui.methodsObjects;

import graphbuilder.math.Expression;
import graphbuilder.math.ExpressionTree;
import gui.mainPane.Methods;
import gui.methodsPanes.IntegralPane;

import javax.swing.*;
import java.math.BigDecimal;

/**
 * Created by razor on 09.06.2017.
 */
public class SimpsonObject {
    private JTable answerTable;
    private math.Simpson simp;
    public JTable getAnswerTable() {
        return answerTable;
    }

    public SimpsonObject(IntegralPane integralPane) {
        Expression z = ExpressionTree.parse(integralPane.getTextFieldS());
        double a = 0;
        double b = 10;
        double c;
        try {
            a = Double.parseDouble(integralPane.getTextField_1Text());
            b = Double.parseDouble(integralPane.getTextField_2Text());
        } catch (Exception ee) {
            JOptionPane.showMessageDialog(null, "Неверный интервал!", "Ошибка",// нарушены
                    // условия сходимости метода
                    JOptionPane.INFORMATION_MESSAGE);
        }

        simp = new math.Simpson(z, a, b);
        simp.setEps(Methods.Eps);
        c = simp.integral();
        JTable answerTable = new JTable(1, 1);
        answerTable.getColumnModel().getColumn(0).setHeaderValue("\u0417\u043D\u0430\u0447\u0435\u043D\u0438\u0435\u0020\u0438\u043D\u0442\u0435\u0433\u0440\u0430\u043B\u0430");// выставление
        // заглавий
        // колонок
        // матрицы Х
        answerTable.getTableHeader().resizeAndRepaint();
        answerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        answerTable.setBounds(141, 12, 438, 305);
        answerTable.getColumnModel();
        BigDecimal p = new BigDecimal(c); // Округление значений
        p = p.setScale(4, BigDecimal.ROUND_HALF_UP);// с требуемой точностью
        answerTable.getModel().setValueAt(p, 0, 0);
        answerTable.getTableHeader().resizeAndRepaint();
        Methods.methodSolution=simp.getSimpson();
        this.answerTable = answerTable;
    }
}
