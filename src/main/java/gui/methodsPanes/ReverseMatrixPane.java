package gui.methodsPanes;

import gui.methodsObjects.ReverseGaussObject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by razor on 08.06.2017.
 */
public class ReverseMatrixPane extends JLayeredPane {

    final JButton button_revers_Gauss = new JButton("\u0420\u0435\u0448\u0438\u0442\u044C");
    final JScrollPane scrollPane_3 = new JScrollPane();
    final JScrollPane scrollPane_4 = new JScrollPane();
    final JSpinner spinner = new JSpinner();
    JTable table;
    JTable table2;

    public ReverseMatrixPane() {
        setBounds(6, 6, 499, 470);
        setLayout(null);


        scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane_3.setBounds(20, 57, 435, 185);
        add(scrollPane_3);

        JLabel label_3 = new JLabel(
                "\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E \u0437\u043D\u0430\u043A\u043E\u0432 \u043F\u043E\u0441\u043B\u0435 \u0437\u0430\u043F\u044F\u0442\u043E\u0439");
        label_3.setBounds(0, 256, 234, 14);
        add(label_3);

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

        button_revers_Gauss.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ReverseGaussObject rgo = new ReverseGaussObject(spinner, spinner_1, table);
                table2 = rgo.getTable2();
                scrollPane_4.setViewportView(table2);
            }
        });
        button_revers_Gauss.setBounds(356, 253, 99, 23);
        add(button_revers_Gauss);

        JButton buttonApply = new JButton("\u041F\u0440\u0438\u043C\u0435\u043D\u0438\u0442\u044C");
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

        JLabel label_10 = new JLabel(
                "\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u0440\u0430\u0437\u043C\u0435\u0440\u043D\u043E\u0441\u0442\u044C \u043C\u0430\u0442\u0440\u0438\u0446\u044B");
        label_10.setBounds(0, 27, 191, 14);
        add(label_10);
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
            System.out.println("Data load error");
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
        System.out.println("table1 ok");
        if (table2 != null) {
            System.out.println(table2 != null);
            printTable(table2, pw);
        }
    }
}
