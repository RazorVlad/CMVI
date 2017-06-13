package gui.methodsPanes;

import gui.methodsObjects.DetObject;
import gui.methodsObjects.GaussObject;
import gui.methodsObjects.GaussSeidelObject;
import gui.methodsObjects.JacobyObject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by razor on 12.06.2017.
 */
public class Layer1 extends JLayeredPane {
    int var;
    math.Gauss g;
    math.GaussSeidel gz;

    private JTable table;
    private JTable table1;
    private JTable table2;
    final JScrollPane scrollPane = new JScrollPane();
    final JScrollPane scrollPane_1 = new JScrollPane();
    final JScrollPane scrollPane_2 = new JScrollPane();
    final JSpinner spinner_2 = new JSpinner();
    final JSpinner spinner_3 = new JSpinner();
    private JSlider slider;
    //    final JButton btnJacoby = new JButton("Решить");
    final JButton button_primenit = new JButton("\u041F\u0440\u0438\u043C\u0435\u043D\u0438\u0442\u044C");
    //    final JButton btnGaussZeidel = new JButton("\u0420\u0435\u0448\u0438\u0442\u044C");
    final JButton btnGauss = new JButton("\u0420\u0435\u0448\u0438\u0442\u044C");
//    final JButton btnDet = new JButton("\u0420\u0435\u0448\u0438\u0442\u044C");

    final JLabel label_4 = new JLabel("\u0428\u0438\u0440\u0438\u043D\u0430 \u044F\u0447\u0435\u0435\u043A");

    public Layer1(int var1) {
        this.var = var1;
        label_4.setEnabled(false);
        label_4.setBounds(10, 221, 94, 23);
        add(label_4);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(116, 25, 288, 360);
        add(scrollPane);

        scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_2.setBounds(416, 25, 84, 345);
        add(scrollPane_2);

        spinner_2.setModel(new

                SpinnerNumberModel(new Integer(2), new

                Integer(2), null, new

                Integer(1)));
        spinner_2.setBounds(0, 94, 105, 26);
        add(spinner_2);

        button_primenit.addActionListener(new ActionListener() {// Создание
            // таблиц X и B
            public void actionPerformed(ActionEvent arg0) {
                label_4.setEnabled(true);
                slider.setEnabled(true);
                final int n = Integer.parseInt(spinner_2.getValue().toString());// считывание
                // размерности
                // матрицы
                table = new JTable(n, n);
                for (int i = 0; i < n; i++) {
                    table.getColumnModel().getColumn(i).setMinWidth(25);
                    table.getColumnModel().getColumn(i).setMaxWidth(200);
                    table.getColumnModel().getColumn(i).setPreferredWidth(40);
                }
                for (int i = 0; i < n; i++)
                    table.getColumnModel().getColumn(i).setHeaderValue("x" + (i + 1));// выставление
                // заглавий
                // колонок матрицы Х
                table.getTableHeader().resizeAndRepaint();
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                table.setBounds(141, 12, 438, 305);
                scrollPane.setViewportView(table);

                table2 = new JTable(n, 1);
                table2.setBounds(431, 25, 38, 322);
                table2.getColumnModel().getColumn(0).setHeaderValue("b");// Выставление
                // заглавий
                // колонок матрицы В
                scrollPane_2.setViewportView(table2);
            }
        });
        button_primenit.setBounds(0, 144, 105, 48);
        add(button_primenit);

        btnGauss.setVisible(false);
        btnGauss.addActionListener(new ActionListener() {// Запуск
            // вычисление
            // решения
            public void actionPerformed(ActionEvent arg0) {

                if (var == 1) {
                    GaussObject gausOb = new GaussObject(spinner_3, spinner_2, table, table2);
                    scrollPane_1.setViewportView(gausOb.getAnswerTable());
                }
                if (var == 2) {
                    JacobyObject jo = new JacobyObject(spinner_3, spinner_2, table, table2);
                    scrollPane_1.setViewportView(jo.getResultTable());
                }
                if (var == 3) {
                    GaussSeidelObject gzo = new GaussSeidelObject(spinner_3, spinner_2, table, table2);
                    scrollPane_1.setViewportView(gzo.getAnswerTable());
                }
                if (var == 4) {
                    DetObject detob = new DetObject(spinner_2, spinner_3, table);
                    scrollPane_1.setViewportView(detob.getAnswertable());
                }
            }
        });
        btnGauss.setBounds(0, 397, 105, 65);
        add(btnGauss);

        slider = new

                JSlider();
        slider.setEnabled(false);
        slider.setMaximum(200);
        slider.setValue(40);
        slider.setMinimum(25);
        slider.setBounds(0, 256, 104, 23);
        add(slider);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                final int n = Integer.parseInt(spinner_2.getValue().toString());// считывание
                // размерности
                // матрицы
                for (int i = 0; i < n; i++) {
                    table.getColumnModel().getColumn(i).setPreferredWidth(slider.getValue());
                }
                try {
                    for (int i = 0; i < table1.getColumnCount(); i++) {
                        table1.getColumnModel().getColumn(i).setPreferredWidth(slider.getValue());
                    }
                    // table.repaint();
                    table1.getTableHeader().resizeAndRepaint();
                } catch (Exception eee) {
                }
                table.getTableHeader().resizeAndRepaint();
            }
        });

        spinner_3.setModel(new

                SpinnerNumberModel(4, 4, 30, 1));
        spinner_3.setBounds(0, 357, 104, 28);
        add(spinner_3);

        scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane_1.setBounds(116, 397, 288, 65);
        add(scrollPane_1);

        JLabel label_2 = new JLabel("\u0420\u0435\u0448\u0435\u043D\u0438\u0435");
        label_2.setBounds(427, 426, 61, 14);
        add(label_2);

//        btnGaussZeidel.setVisible(false);
//        btnGaussZeidel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent arg0) {
////                GaussSeidelObject gzo = new GaussSeidelObject(spinner_3, spinner_2, table, table2, gz);
////                scrollPane_1.setViewportView(gzo.getAnswerTable());
//            }
//        });
//        btnGaussZeidel.setBounds(0, 397, 104, 65);
//        add(btnGaussZeidel);
//
//        btnJacoby.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent arg0) {
//
////                JacobyObject jo = new JacobyObject(spinner_3, spinner_2, table, table2);
////                scrollPane_1.setViewportView(jo.getResultTable());
//
//            }
//        });
//        btnJacoby.setBounds(0, 397, 104, 65);
//        add(btnJacoby);

        JLabel label_11 = new JLabel("\u0412\u0432\u0435\u0434\u0438\u0442\u0435");
        label_11.setBounds(6, 28, 84, 16);
        add(label_11);

        JLabel label_12 = new JLabel("\u0440\u0430\u0437\u043C\u0435\u0440\u043D\u043E\u0441\u0442\u044C");
        label_12.setBounds(6, 47, 84, 16);
        add(label_12);

        JLabel label_13 = new JLabel("\u043C\u0430\u0442\u0440\u0438\u0446\u044B");
        label_13.setBounds(6, 66, 55, 16);
        add(label_13);

        JLabel label_14 = new JLabel(
                "\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E \u0437\u043D\u0430\u043A\u043E\u0432");
        label_14.setBounds(0, 312, 116, 16);
        add(label_14);

        JLabel label_15 = new JLabel(
                "\u043F\u043E\u0441\u043B\u0435 \u0437\u0430\u043F\u044F\u0442\u043E\u0439:");
        label_15.setBounds(0, 329, 104, 16);
        add(label_15);
    }

    public void SetVisible(int var) {
        table = new JTable(0, 0);
        table1 = new JTable(0, 0);
        table2 = new JTable(0, 0);// Создание таблицы решений

        if (var == 4) {
            scrollPane_2.setVisible(false);
        } else {
            scrollPane_2.setVisible(true);
            scrollPane_1.setViewportView(table1);
        }
        this.var = var;
    }

    public void openData(StringTokenizer st, BufferedReader br) {
        try {
            int x = Integer.parseInt(st.nextToken());
            spinner_2.setValue(x);
            table = new JTable(x, x);
            String s;
            for (int i = 0; i < x; i++) {
                s = br.readLine();
                st = new StringTokenizer(s);
                for (int j = 0; j < x; j++) {
                    for (int k = 0; k < x; k++) {
                        table.getColumnModel().getColumn(k).setMinWidth(25);
                        table.getColumnModel().getColumn(k).setMaxWidth(200);
                        table.getColumnModel().getColumn(k).setPreferredWidth(40);
                    }
                    double val = Double.parseDouble(st.nextToken());
                    table.getModel().setValueAt(val, i, j);
                }
            }
            label_4.setEnabled(true);
            slider.setEnabled(true);

            for (int k = 0; k < x; k++) {
                table.getColumnModel().getColumn(k).setHeaderValue("x" + (k + 1));
//                table9.getColumnModel().getColumn(k).setHeaderValue("x" + (k + 1));
            }
            table.getTableHeader().resizeAndRepaint();
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.setBounds(0, 0, 438, 305);
            scrollPane.setViewportView(table);
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

            // }
            br.close();
        } catch (Exception ee) {
        }
    }

    public void saveData(PrintWriter pw) {
        int x;
        String s = "";
        x = table.getRowCount();
        pw.println(x + " ");// + y);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                s = s + table.getValueAt(i, j).toString() + " ";
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

}
