package gui.methodsObjects;

import graphbuilder.math.Expression;
import graphbuilder.math.ExpressionTree;
import gui.mainPane.MainFrame;
import gui.methodsPanes.NewtonPane;

import javax.swing.*;

/**
 * Created by razor on 08.06.2017.
 */
public class NewtonObject {
    private JTable answerTable;
    private JTable table;
    private math.Newton nwt;

    public JTable getAnswerTable() {
        return answerTable;
    }

    public NewtonObject(NewtonPane newtonPane) {
        int intervalsCount = newtonPane.getIntervalsCount();
        String s = newtonPane.getTextFieldS();
        Expression z = ExpressionTree.parse(s);

        math.Newton nwt;

        double[] a = new double[intervalsCount], b = new double[intervalsCount], c = new double[intervalsCount];
        if (intervalsCount != 0) {
            try {
                for (int i = 0; i < intervalsCount; i++) {
                    a[i] = newtonPane.getTableValue(0, i);
                    b[i] = newtonPane.getTableValue(1, i);
                }
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(null, "Проверьте вводимые данные!", "Ошибка",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            nwt = new math.Newton(z, a, b);
        } else {
            nwt = new math.Newton(z);
            JTable table = new JTable(2, nwt.getA().size());
            for (int i = 0; i < nwt.getA().size(); i++) {
                table.getColumnModel().getColumn(i).setMinWidth(50);
                table.getColumnModel().getColumn(i).setMaxWidth(200);
                table.getColumnModel().getColumn(i).setPreferredWidth(60);
                table.getModel().setValueAt(nwt.getAi(i), 0, i);
                table.getModel().setValueAt(nwt.getBi(i), 1, i);
            }
            this.table = table;
            intervalsCount = table.getColumnCount();
//            scrollPane_5.setViewportView(table);

        }
        nwt.setH(MainFrame.Eps);
        c = nwt.solve();
//        slider_2.setVisible(true);

        JTable answerTable = new JTable(1, c.length);
        for (int i = 0; i < c.length; i++) {
            answerTable.getColumnModel().getColumn(i).setHeaderValue("x" + (i + 1));
            // table2.isEditing();

            answerTable.getModel().setValueAt(c[i], 0, i);

            answerTable.getColumnModel().getColumn(i).setMinWidth(25);
            answerTable.getColumnModel().getColumn(i).setMaxWidth(200);
            answerTable.getColumnModel().getColumn(i).setPreferredWidth(90);
        }
        answerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        answerTable.getTableHeader().resizeAndRepaint();
//        scrollPane_6.setViewportView(answerTable);

        MainFrame.methodSolution = nwt.getNewton();
        this.answerTable = answerTable;
    }
}
