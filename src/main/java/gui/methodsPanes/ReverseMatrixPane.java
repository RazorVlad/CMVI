package gui.methodsPanes;

import gui.methodsObjects.ReverseGaussObject;

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
 * Created by razor on 08.06.2017.
 */
public class ReverseMatrixPane extends JLayeredPane {

    private final JScrollPane scrollPane_3 = new JScrollPane();
    private final JScrollPane scrollPane_4 = new JScrollPane();
    private final JSpinner spinner = new JSpinner();

    private JLabel labelInputNumsCountAfterComa;
    private JLabel labelInputMatrixSize;

    private JButton button_revers_Gauss;
    private JButton buttonApply;

    private JTable table;
    private JTable table2;

    private ResourceBundle bundle;

    public ReverseMatrixPane(ResourceBundle bundle) {
        this.bundle=bundle;
        setBounds(6, 6, 499, 470);
        setLayout(null);

        scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane_3.setBounds(20, 57, 435, 185);
        add(scrollPane_3);

        labelInputNumsCountAfterComa = new JLabel(bundle.getString("labels.inputNumsCountAfterComa"));
        labelInputNumsCountAfterComa.setBounds(0, 256, 234, 14);
        add(labelInputNumsCountAfterComa);

        spinner.setModel(new SpinnerNumberModel(new Integer(2), new Integer(2), null, new Integer(1)));
        spinner.setBounds(193, 20, 41, 28);
        add(spinner);

        scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane_4.setBounds(20, 282, 435, 177);
        add(scrollPane_4);

        final JSpinner spinner_1 = new JSpinner();
        spinner_1.setModel(new SpinnerNumberModel(new Integer(3), new Integer(3), null, new Integer(1)));
        spinner_1.setBounds(197, 249, 41, 28);
        add(spinner_1);

        final JSlider slider_3 = new JSlider();
        slider_3.setMaximum(400);
        slider_3.setBounds(246, 256, 110, 21);
        add(slider_3);
        slider_3.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                final int n = Integer.parseInt(spinner.getValue().toString());// считывание
                for (int i = 0; i < n; i++) {
                    table.getColumnModel().getColumn(i).setPreferredWidth(slider_3.getValue());
                }
                try {
                    for (int i = 0; i < table2.getColumnCount(); i++) {
                        table2.getColumnModel().getColumn(i).setPreferredWidth(slider_3.getValue());
                    }
                    table2.getTableHeader().resizeAndRepaint();
                } catch (Exception eee) {
                }
                table.getTableHeader().resizeAndRepaint();
            }
        });

        button_revers_Gauss = new JButton(bundle.getString("buttons.solve"));
        button_revers_Gauss.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ReverseGaussObject rgo = new ReverseGaussObject(spinner, spinner_1, table);
                table2 = rgo.getTable2();
                scrollPane_4.setViewportView(table2);
            }
        });
        button_revers_Gauss.setBounds(356, 253, 99, 23);
        add(button_revers_Gauss);

        buttonApply = new JButton(bundle.getString("buttons.accept"));
        buttonApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int n = Integer.parseInt(spinner.getValue().toString());// считывание
                // размерности
                // матрицы
                slider_3.setEnabled(true);
                table = new JTable(n, n);
                for (int i = 0; i < n; i++) {
                    table.getColumnModel().getColumn(i).setMinWidth(45);
                    table.getColumnModel().getColumn(i).setMaxWidth(99999);
                    table.getColumnModel().getColumn(i).setPreferredWidth(45);
                }
                for (int i = 0; i < n; i++)
                    table.getColumnModel().getColumn(i).setHeaderValue("x" + (i + 1));// выставление
                // заглавий
                // колонок
                // матрицы Х
                table.getTableHeader().resizeAndRepaint();
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.setBounds(141, 12, 438, 305);
                scrollPane_3.setViewportView(table);

            }
        });
        buttonApply.setBounds(356, 23, 99, 23);
        add(buttonApply);

        labelInputMatrixSize = new JLabel(bundle.getString("labels.inputMatrixSize"));
        labelInputMatrixSize.setBounds(0, 27, 191, 14);
        add(labelInputMatrixSize);
    }

    public void load(StringTokenizer st, BufferedReader br) {
        try {
            int x = Integer.parseInt(st.nextToken());
            table = new JTable(x, x);
            setTable(x, table, br);

            table2 = new JTable(x, x);
            setTable(x, table2, br);

            spinner.setValue(x);
            scrollPane_3.setViewportView(table);
            scrollPane_4.setViewportView(table2);

        } catch (Exception ee) {
        }
    }

    public void setTable(int x, JTable table, BufferedReader br) {
        String s;
        StringTokenizer st;
        try {
            for (int i = 0; i < x; i++) {
                s = br.readLine();
                st = new StringTokenizer(s);
                for (int j = 0; j < x; j++) {
                    double val = Double.parseDouble(st.nextToken());
                    table.getModel().setValueAt(val, i, j);
                }
                table.getColumnModel().getColumn(i).setHeaderValue("x" + (i + 1));
                table.getColumnModel().getColumn(i).setMinWidth(25);
                table.getColumnModel().getColumn(i).setMaxWidth(200);
                table.getColumnModel().getColumn(i).setPreferredWidth(40);
            }
            table.getTableHeader().resizeAndRepaint();
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.setBounds(0, 0, 438, 305);
        } catch (Exception e) {
            System.out.println("data error");
        }
    }

    public void printTable(JTable table, PrintWriter pw) {
        int x = table.getRowCount();
        String s = "";
        try {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < x; j++) s = s + table.getValueAt(i, j).toString() + " ";
                pw.println(s);
                s = "";
            }
        } catch (Exception e) {
        }
    }

    public void saveData(PrintWriter pw) {
        int x;
        x = table.getRowCount();
        pw.println(x + " ");// + y);

        printTable(table, pw);
        if (table2 != null) {
            System.out.println(table2 != null);
            printTable(table2, pw);
        }
    }
    public void initComponentsI18n(ResourceBundle bundle) {
        this.bundle = bundle;
        button_revers_Gauss.setText(bundle.getString("buttons.solve"));
        labelInputNumsCountAfterComa.setText(bundle.getString("labels.inputNumsCountAfterComa"));
        labelInputMatrixSize.setText(bundle.getString("labels.inputMatrixSize"));
    }
}
