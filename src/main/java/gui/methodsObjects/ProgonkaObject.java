package gui.methodsObjects;

import graphbuilder.math.Expression;
import graphbuilder.math.ExpressionTree;
import gui.mainPane.MainFrame;
import gui.methodsPanes.ProgonkaPane;

import javax.swing.*;

/**
 * Created by razor on 09.06.2017.
 */
public class ProgonkaObject {
    private JTable answerTable;

    public JTable getAnswerTable() {
        return answerTable;
    }

    public boolean isSolved() {

        return isSolved;
    }

    private boolean isSolved = false;
    private math.Progonka progonka;

    public ProgonkaObject(ProgonkaPane progonkaPane) {
        progonka = new math.Progonka();

        progonka.setN((int) MainFrame.Eps);
        try {
            Expression p = ExpressionTree.parse(progonkaPane.getTableFuncValue(0));
            progonka.setP(p);
            Expression q = ExpressionTree.parse(progonkaPane.getTableFuncValue(1));
            progonka.setQ(q);
            Expression f = ExpressionTree.parse(progonkaPane.getTableFuncValue(2));
            progonka.setF(f);
            progonka.seta(Double.parseDouble(progonkaPane.getTextField_a()));
            progonka.setb(Double.parseDouble(progonkaPane.getTextField_b()));
            progonka.setA(Double.parseDouble(progonkaPane.getTextField_A()));
            progonka.setB(Double.parseDouble(progonkaPane.getTextField_B()));
        } catch (Exception ee) {
            JOptionPane.showMessageDialog(null, "Проверьте вводимые данные!", "Ошибка",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        double[] alfa = new double[2], beta = new double[2];
        for (int i = 0; i < progonkaPane.getTableColumnCount(); i++) {
            alfa[i] = progonkaPane.getTableValue(0, i);
            beta[i] = progonkaPane.getTableValue(1, i);
        }
        progonka.setAlfa(alfa);
        progonka.setBeta(beta);
        double[] y = progonka.solve();
        isSolved = true;
//        slider_2.setVisible(true);
        double[] x = new double[y.length];
        double a = progonka.geta();
        double step = progonka.getH();
        for (int i = 0; i < y.length - 1; i++) {
            x[i] = a;
            a += step;
        }
        x[(y.length - 1)] = progonka.getb();
        JTable answerTable = new JTable(2, y.length + 1);
        answerTable.getModel().setValueAt("Yi", 1, 0);
        answerTable.getModel().setValueAt("Xi", 0, 0);
        answerTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        answerTable.getColumnModel().getColumn(0).setHeaderValue((""));
        for (int i = 1; i < y.length + 1; i++) {
            answerTable.getColumnModel().getColumn(i).setHeaderValue((i - 1));

            answerTable.getModel().setValueAt(y[i - 1], 1, i);
            answerTable.getModel().setValueAt(x[i - 1], 0, i);

            answerTable.getColumnModel().getColumn(i).setMinWidth(25);
            answerTable.getColumnModel().getColumn(i).setMaxWidth(200);
            answerTable.getColumnModel().getColumn(i).setPreferredWidth(90);
        }
        answerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        answerTable.getTableHeader().resizeAndRepaint();
        MainFrame.methodSolution = progonka.getProgonka();
        this.answerTable = answerTable;
    }
}
