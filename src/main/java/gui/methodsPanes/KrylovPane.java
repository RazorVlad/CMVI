package gui.methodsPanes;

import gui.methodsObjects.KrylovObject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * Created by razor on 10.06.2017.
 */
public class KrylovPane extends JLayeredPane {

    private final JSpinner spinner_5 = new JSpinner();

    private final JSlider slider_1 = new JSlider();

    private JTable table9;
    private JTable table;

    public String getKrylov() {
        return krylov;
    }

    private JButton button_solveKrylov;
    private JButton buttonAccept;

    private JLabel labelCellWidth;
    private JLabel labelEigenvalues;
    private JLabel labelInput;
    private JLabel labelSize;
    private JLabel labelMatrix;
    private JLabel labelNumsCount;
    private JLabel labelAfterComa;
    private JLabel labelMatrixEquation;
    private JLabel labelEigenVectors;

    private String krylov;
    private ResourceBundle bundle;

    final JScrollPane scrollPane_9 = new JScrollPane();
    final JScrollPane scrollPane = new JScrollPane();
    final JScrollPane scrollPane_2 = new JScrollPane();

    private JTable table2;
    private JTable table1;

    private JTextField textField_3;

    public KrylovPane(ResourceBundle bundle) {
        this.bundle = bundle;
        spinner_5.setModel(new

                SpinnerNumberModel(new Integer(2), new

                Integer(2), null, new

                Integer(1)));
        spinner_5.setBounds(6, 64, 105, 26);
        add(spinner_5);

        slider_1.setValue(40);
        slider_1.setMinimum(25);
        slider_1.setMaximum(200);
        slider_1.setEnabled(false);
        slider_1.setBounds(6, 151, 104, 23);
        slider_1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                final int n = Integer.parseInt(spinner_5.getValue().toString());// считывание
                for (int i = 0; i < n; i++) {
                    table9.getColumnModel().getColumn(i).setPreferredWidth(slider_1.getValue());
                }
                try {
                    for (int i = 1; i < table2.getColumnCount(); i++) {
                        table2.getColumnModel().getColumn(i).setPreferredWidth(slider_1.getValue());
                    }
                    for (int i = 1; i < table1.getColumnCount(); i++) {
                        table1.getColumnModel().getColumn(i).setPreferredWidth(slider_1.getValue());
                    }
                    table2.getTableHeader().resizeAndRepaint();
                    table1.getTableHeader().resizeAndRepaint();
                } catch (Exception eee) {
                }
                table9.getTableHeader().resizeAndRepaint();
            }
        });
        add(slider_1);

        scrollPane_9.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_9.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane_9.setBounds(122, 6, 372, 194);
        add(scrollPane_9);

        textField_3 = new

                JTextField();
        textField_3.setBounds(206, 229, 288, 28);
        add(textField_3);
        textField_3.setColumns(10);

        labelCellWidth = new JLabel(bundle.getString("labels.cellWidth"));
        labelCellWidth.setEnabled(false);
        labelCellWidth.setBounds(16, 131, 94, 23);
        add(labelCellWidth);

        buttonAccept = new JButton(bundle.getString("buttons.accept"));
        buttonAccept.addActionListener(new

                                               ActionListener() {
                                                   public void actionPerformed(ActionEvent e) {
                                                       setTable();
                                                   }
                                               });
        buttonAccept.setBounds(5, 91, 105, 28);
        add(buttonAccept);

        final JSpinner spinner_6 = new JSpinner();
        spinner_6.setModel(new

                SpinnerNumberModel(new Integer(3), new

                Integer(3), null, new

                Integer(1)));
        spinner_6.setBounds(6, 229, 104, 28);
        add(spinner_6);

        final JScrollPane scrollPane_10 = new JScrollPane();
        scrollPane_10.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_10.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane_10.setBounds(6, 291, 179, 179);
        add(scrollPane_10);

        final JScrollPane scrollPane_11 = new JScrollPane();
        scrollPane_11.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_11.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane_11.setBounds(197, 291, 297, 179);
        add(scrollPane_11);

        button_solveKrylov = new JButton(bundle.getString("buttons.solve"));
        button_solveKrylov.addActionListener(new

                                                     ActionListener() {
                                                         public void actionPerformed(ActionEvent e) {
                                                             KrylovObject ko = new KrylovObject(spinner_6, spinner_5, table9);
                                                             krylov = ko.getSolveText();
                                                             textField_3.setText(ko.getAnswer());
                                                             scrollPane_10.setViewportView(ko.getTable());
                                                             scrollPane_11.setViewportView(ko.getTable2());

                                                         }
                                                     });
        button_solveKrylov.setBounds(128, 229, 71, 28);
        add(button_solveKrylov);

        labelEigenvalues = new JLabel(bundle.getString("labels.eigenvalues"));
        labelEigenvalues.setBounds(22, 269, 141, 16);
        add(labelEigenvalues);

        labelEigenVectors = new JLabel(bundle.getString("labels.eigenVectors"));
        labelEigenVectors.setBounds(277, 269, 141, 16);
        add(labelEigenVectors);

        labelInput = new JLabel(bundle.getString("labels.input"));
        labelInput.setBounds(6, 6, 55, 16);
        add(labelInput);

        labelSize = new JLabel(bundle.getString("labels.size"));
        labelSize.setBounds(6, 25, 105, 16);
        add(labelSize);

        labelMatrix = new JLabel(bundle.getString("labels.matrix"));
        labelMatrix.setBounds(6, 43, 55, 16);
        add(labelMatrix);

        labelNumsCount = new JLabel(bundle.getString("labels.numsCount"));
        labelNumsCount.setBounds(6, 184, 116, 16);
        add(labelNumsCount);

        labelAfterComa = new JLabel(bundle.getString("labels.afterComa") + ":");
        labelAfterComa.setBounds(6, 201, 104, 16);
        add(labelAfterComa);

        labelMatrixEquation = new JLabel(bundle.getString("labels.characteristicMatrixEquation"));
        labelMatrixEquation.setBounds(212, 212, 288, 16);
        add(labelMatrixEquation);
    }

    public void saveData(PrintWriter pw) {
        int x = table9.getRowCount();
        String s = "";
        pw.println(x + " ");// + y);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                s = s + table9.getValueAt(i, j).toString() + " ";
            }
            pw.println(s);
            s = "";
        }
        if (table2 != null) {
            try {
                for (int j = 0; j < x; j++) {
                    s = s + table2.getValueAt(j, 0).toString() + " ";
                }
                pw.println(s);
            } catch (Exception ee) {
            }
        }
    }

    public void OpenData(StringTokenizer st, BufferedReader br) {
        String s;
        try {
//TODO переделать сохранение и считывание матрицв
            int x = Integer.parseInt(st.nextToken());
            // int y = Integer.parseInt(st.nextToken());
//            spinner_2.setValue(x);
            spinner_5.setValue(x);
            table = new JTable(x, x);
            table9 = new JTable(x, x);

            for (int i = 0; i < x; i++) {
                s = br.readLine();
                st = new StringTokenizer(s);
                for (int j = 0; j < x; j++) {
                    for (int k = 0; k < x; k++) {
                        table.getColumnModel().getColumn(k).setMinWidth(25);
                        table.getColumnModel().getColumn(k).setMaxWidth(200);
                        table.getColumnModel().getColumn(k).setPreferredWidth(40);

                        table9.getColumnModel().getColumn(k).setMinWidth(25);
                        table9.getColumnModel().getColumn(k).setMaxWidth(200);
                        table9.getColumnModel().getColumn(k).setPreferredWidth(40);
                    }
                    double val = Double.parseDouble(st.nextToken());
                    table.getModel().setValueAt(val, i, j);
                    table9.getModel().setValueAt(val, i, j);
                }
            }
//            label_4.setEnabled(true);
//            slider.setEnabled(true);

            for (int k = 0; k < x; k++) {
                table.getColumnModel().getColumn(k).setHeaderValue("x" + (k + 1));
                table9.getColumnModel().getColumn(k).setHeaderValue("x" + (k + 1));
            }
            table.getTableHeader().resizeAndRepaint();
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.setBounds(0, 0, 438, 305);

            table9.getTableHeader().resizeAndRepaint();
            table9.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table9.setBounds(0, 0, 438, 305);
            scrollPane_9.setViewportView(table9);
            if (scrollPane.isVisible())
                scrollPane.setViewportView(table);
//                    if (layeredPane_2.isVisible()) {
//                        spinner.setValue(x);
//                        scrollPane_3.setViewportView(table);
//                    }

            // if (x != y) {
            try {
                s = br.readLine();
                st = new StringTokenizer(s);
                table2 = new JTable(x, 1);
                table2.setBounds(0, 0, 38, 322);
                for (int i = 0; i < x; i++) {
                    table2.getModel().setValueAt(Double.parseDouble(st.nextToken()), i, 0);
                    table2.getColumnModel().getColumn(0).setHeaderValue("b");
                    scrollPane_2.setViewportView(table2);
                }
            } catch (Exception ee) {
            }

            br.close();
        } catch (Exception ee) {
        }
    }

    private void setTable() {
        labelCellWidth.setEnabled(true);
        slider_1.setEnabled(true);
        final int n = Integer.parseInt(spinner_5.getValue().toString());// считывание размерности матрицы
        table9 = new JTable(n, n);
        for (int i = 0; i < n; i++) {
            table9.getColumnModel().getColumn(i).setMinWidth(25);
            table9.getColumnModel().getColumn(i).setMaxWidth(200);
            table9.getColumnModel().getColumn(i).setPreferredWidth(40);
            table9.getColumnModel().getColumn(i).setHeaderValue("x" + (i + 1));// выставлениезаглавий колонок матрицы Х
        }
        table9.getTableHeader().resizeAndRepaint();
        table9.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table9.setBounds(141, 12, 438, 305);
        scrollPane_9.setViewportView(table9);
    }

    private void setTableData() {

    }


    public void initComponentsI18n(ResourceBundle bundle) {
        this.bundle = bundle;
        button_solveKrylov.setText(bundle.getString("buttons.solve"));
        labelEigenvalues.setText(bundle.getString("labels.eigenvalues"));
        labelEigenVectors.setText(bundle.getString("labels.eigenVectors"));
        labelInput.setText(bundle.getString("labels.input"));
        labelSize.setText(bundle.getString("labels.size"));
        labelMatrix.setText(bundle.getString("labels.matrix"));
        labelNumsCount.setText(bundle.getString("labels.numsCount"));
        labelAfterComa.setText(bundle.getString("labels.afterComa") + ":");
        labelMatrixEquation.setText(bundle.getString("labels.characteristicMatrixEquation"));
    }

}
