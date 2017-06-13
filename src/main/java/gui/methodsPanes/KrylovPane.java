package gui.methodsPanes;

import gui.methodsObjects.KrylovObject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by razor on 10.06.2017.
 */
public class KrylovPane extends JLayeredPane {

    final JSpinner spinner_5 = new JSpinner();
    final JSlider slider_1 = new JSlider();
    private JTable table9;
    private JTable table;

    public String getKrylov() {
        return krylov;
    }

    private String krylov;

    final JScrollPane scrollPane_9 = new JScrollPane();
    final JScrollPane scrollPane = new JScrollPane();
    final JScrollPane scrollPane_2 = new JScrollPane();
    private JTable table2;
    private JTable table1;
    private JTextField textField_3;
    JLabel lblNewLabel_3 = new JLabel(
            "\u0421\u043E\u0431\u0441\u0442\u0432\u0435\u043D\u043D\u044B\u0435 \u0432\u0435\u043A\u0442\u043E\u0440\u044B");


    public KrylovPane() {
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

        final JLabel label_9 = new JLabel(
                "\u0428\u0438\u0440\u0438\u043D\u0430 \u044F\u0447\u0435\u0435\u043A");
        label_9.setEnabled(false);
        label_9.setBounds(16, 131, 94, 23);
        add(label_9);

        JButton button_10 = new JButton("\u041F\u0440\u0438\u043C\u0435\u043D\u0438\u0442\u044C");
        button_10.addActionListener(new

                                            ActionListener() {
                                                public void actionPerformed(ActionEvent e) {
                                                    label_9.setEnabled(true);
                                                    slider_1.setEnabled(true);
                                                    final int n = Integer.parseInt(spinner_5.getValue().toString());// считывание
                                                    // размерности
                                                    // матрицы
                                                    table9 = new JTable(n, n);
                                                    for (int i = 0; i < n; i++) {
                                                        table9.getColumnModel().getColumn(i).setMinWidth(25);
                                                        table9.getColumnModel().getColumn(i).setMaxWidth(200);
                                                        table9.getColumnModel().getColumn(i).setPreferredWidth(40);
                                                    }
                                                    for (int i = 0; i < n; i++)
                                                        table9.getColumnModel().getColumn(i).setHeaderValue("x" + (i + 1));// выставление
                                                    // заглавий
                                                    // колонок матрицы Х
                                                    table9.getTableHeader().resizeAndRepaint();
                                                    table9.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                                                    table9.setBounds(141, 12, 438, 305);
                                                    scrollPane_9.setViewportView(table9);
                                                }
                                            });
        button_10.setBounds(5, 91, 105, 28);
        add(button_10);

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

        JButton button_solveKrylov = new JButton("\u0420\u0435\u0448\u0438\u0442\u044C");
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

        JLabel lblNewLabel_2 = new JLabel(
                "\u0421\u043E\u0431\u0441\u0442\u0432\u0435\u043D\u043D\u044B\u0435 \u0437\u043D\u0430\u0447\u0435\u043D\u0438\u044F");
        lblNewLabel_2.setBounds(22, 269, 141, 16);
        add(lblNewLabel_2);

        lblNewLabel_3.setBounds(277, 269, 141, 16);
        add(lblNewLabel_3);

        JLabel label_16 = new JLabel("\u0412\u0432\u0435\u0434\u0438\u0442\u0435");
        label_16.setBounds(6, 6, 55, 16);
        add(label_16);

        JLabel label_17 = new JLabel("\u0440\u0430\u0437\u043C\u0435\u0440\u043D\u043E\u0441\u0442\u044C");
        label_17.setBounds(6, 25, 105, 16);
        add(label_17);

        JLabel label_18 = new JLabel("\u043C\u0430\u0442\u0440\u0438\u0446\u044B");
        label_18.setBounds(6, 43, 55, 16);
        add(label_18);

        JLabel label_19 = new JLabel(
                "\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E \u0437\u043D\u0430\u043A\u043E\u0432");
        label_19.setBounds(6, 184, 116, 16);
        add(label_19);

        JLabel label_20 = new JLabel(
                "\u043F\u043E\u0441\u043B\u0435 \u0437\u0430\u043F\u044F\u0442\u043E\u0439:");
        label_20.setBounds(6, 201, 104, 16);
        add(label_20);

        JLabel label_21 = new JLabel(
                "\u0425\u0430\u0440\u0430\u043A\u0442\u0435\u0440\u0438\u0441\u0442\u0438\u0447\u0435\u0441\u043A\u043E\u0435 \u0443\u0440\u0430\u0432\u043D\u0435\u043D\u0438\u0435 \u043C\u0430\u0442\u0440\u0438\u0446\u044B:");
        label_21.setBounds(212, 212, 288, 16);
        add(label_21);
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
}
