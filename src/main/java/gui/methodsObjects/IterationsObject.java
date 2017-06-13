package gui.methodsObjects;

import graphbuilder.math.Expression;
import graphbuilder.math.ExpressionTree;
import gui.Methods;
import gui.methodsPanes.NewtonPane;

import javax.swing.*;

/**
 * Created by razor on 09.06.2017.
 */
public class IterationsObject {
    private boolean isSolved = false;
    private JTable answerTable;
    private JTable table;
    private math.Iterations iterations;

    public boolean isSolved() {
        return isSolved;
    }

    public JTable getTable() {
        return table;
    }

    public JTable getAnswerTable() {
        return answerTable;
    }

    public IterationsObject(NewtonPane newtonPane) {
        Expression z = ExpressionTree.parse(newtonPane.getTextFieldS());
        int index1 = newtonPane.getIntervalsCount();
        double[] a = new double[index1];
        double[] b = new double[index1];
        double[] c = new double[index1];

        if (index1 != 0) {
            try {
                for (int i = 0; i < index1; i++) {
                    a[i] = newtonPane.getTableValue(0, i);
                    b[i] = newtonPane.getTableValue(1, i);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Проверьте вводимые данные!", "Ошибка",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            iterations = new math.Iterations(z, a, b);
        } else {
            iterations = new math.Iterations(z);
            JTable table = new JTable(2, iterations.getA().size());
            for (int i = 0; i < iterations.getA().size(); i++) {
                table.getColumnModel().getColumn(i).setMinWidth(50);
                table.getColumnModel().getColumn(i).setMaxWidth(200);
                table.getColumnModel().getColumn(i).setPreferredWidth(60);
                table.getModel().setValueAt(iterations.getAi(i), 0, i);
                table.getModel().setValueAt(iterations.getBi(i), 1, i);
            }

            iterations.setH(gui.Methods.Eps);
            try {
                c = iterations.solve();
                isSolved = true;
            } catch (Exception e2) {
                isSolved = false;
                JOptionPane.showMessageDialog(null, "Проверьте вводимые данные!", "Ошибка",// нарушены
                        // условия
                        // сходимости
                        // метода
                        JOptionPane.INFORMATION_MESSAGE);
            }

            JTable table2 = new JTable(1, c.length);
            for (int i = 0; i < c.length; i++) {
                table2.getColumnModel().getColumn(i).setHeaderValue("x" + (i + 1));
                table2.getModel().setValueAt(c[i], 0, i);
                table2.getColumnModel().getColumn(i).setMinWidth(25);
                table2.getColumnModel().getColumn(i).setMaxWidth(200);
                table2.getColumnModel().getColumn(i).setPreferredWidth(90);
            }
            table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table2.getTableHeader().resizeAndRepaint();

            Methods.methodSolution = iterations.getIterations();
            this.answerTable = table2;
            this.table = table;
        }
    }
}
