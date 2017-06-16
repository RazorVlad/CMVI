package gui.methodsPanes;

import gui.mainPane.MainFrame;
import gui.methodsObjects.DetObject;
import gui.methodsObjects.GaussObject;
import gui.methodsObjects.GaussSeidelObject;
import gui.methodsObjects.JacobyObject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * Created by razor on 12.06.2017.
 */
public class GaussMethodsPane extends JLayeredPane {
    private int var;
    private JTable table;
    private JTable table1;
    private JTable table2;
    private JLabel labelInput;
    private JLabel labelSize;
    private JLabel labelMatrix;
    private JLabel labelNumsCount;
    private JLabel labelAfterComa;
    private JLabel labelSolve;
    private final JScrollPane scrollPane = new JScrollPane();
    private final JScrollPane scrollPane_1 = new JScrollPane();
    private final JScrollPane scrollPane_2 = new JScrollPane();
    private final JSpinner spinnerMatrixSize = new JSpinner();
    private final JSpinner spinnerNumsCount = new JSpinner();
    private JSlider slider;
    private JButton buttonSetTable;
    private JButton buttonSolve;
    private JLabel labelCellWidth;

    public GaussMethodsPane(int var1, ResourceBundle bundle) {
        this.var = var1;
        setLayout(new BorderLayout());
        //TODO
        JPanel leftPanel = new JPanel();
//        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setLayout(null);
        leftPanel.setPreferredSize(new Dimension(115, 500));
        leftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.setBounds(0, 0, 120, 500);
        add(leftPanel, BorderLayout.LINE_START);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        add(rightPanel, BorderLayout.LINE_END);
        leftPanel.add(Box.createRigidArea(new Dimension(5, 25)));

        labelInput = new JLabel(bundle.getString("labels.input"), SwingConstants.LEFT);
        labelInput.setBounds(6, 28, 84, 16);
        labelInput.setPreferredSize(new Dimension(105, 16));
        labelInput.setMaximumSize(new Dimension(150, 16));
        labelInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(labelInput);

        labelSize = new JLabel(bundle.getString("labels.size"), SwingConstants.LEFT);
        labelSize.setBounds(6, 47, 84, 16);
        labelSize.setPreferredSize(new Dimension(105, 16));
        labelSize.setMaximumSize(new Dimension(150, 16));
        labelSize.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(labelSize);

        labelMatrix = new JLabel(bundle.getString("labels.matrix"), SwingConstants.LEFT);
        labelMatrix.setBounds(6, 66, 55, 16);
        labelMatrix.setPreferredSize(new Dimension(105, 16));
        labelMatrix.setMaximumSize(new Dimension(150, 16));
        labelMatrix.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelMatrix.setHorizontalAlignment(SwingConstants.LEFT);
        leftPanel.add(labelMatrix);

        leftPanel.add(Box.createRigidArea(new Dimension(5, 10)));

        spinnerMatrixSize.setModel(new SpinnerNumberModel(new Integer(2), new
                Integer(2), null, new Integer(1)));
        spinnerMatrixSize.setBounds(0, 94, 105, 26);
        spinnerMatrixSize.setPreferredSize(new Dimension(105, 26));
        spinnerMatrixSize.setMaximumSize(new Dimension(150, 26));
        spinnerMatrixSize.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(spinnerMatrixSize);

        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        buttonSetTable = new JButton(bundle.getString("buttons.accept"));
        buttonSetTable.addActionListener(new ActionListener() {// Создание
            // таблиц X и B
            public void actionPerformed(ActionEvent arg0) {
                labelCellWidth.setEnabled(true);
                slider.setEnabled(true);
                final int n = Integer.parseInt(spinnerMatrixSize.getValue().toString());// считывание
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
        buttonSetTable.setBounds(0, 144, 105, 48);
        buttonSetTable.setPreferredSize(new Dimension(105, 48));
        buttonSetTable.setMaximumSize(new Dimension(150, 48));
        buttonSetTable.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(buttonSetTable);


        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        labelCellWidth = new JLabel(bundle.getString("labels.cellWidth"));
        labelCellWidth.setEnabled(false);
        labelCellWidth.setBounds(10, 221, 94, 23);
        leftPanel.add(labelCellWidth);

        slider = new JSlider();
        slider.setEnabled(false);
        slider.setMaximum(200);
        slider.setValue(40);
        slider.setMinimum(25);
        slider.setBounds(0, 256, 104, 23);
        slider.setPreferredSize(new Dimension(105, 23));
        slider.setMaximumSize(new Dimension(150, 23));
        leftPanel.add(slider);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                final int n = Integer.parseInt(spinnerMatrixSize.getValue().toString());// считывание
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


        leftPanel.add(Box.createVerticalGlue());

        labelNumsCount = new JLabel(bundle.getString("labels.numsCount"));
        labelNumsCount.setBounds(0, 312, 116, 16);
        leftPanel.add(labelNumsCount);

        labelAfterComa = new JLabel(bundle.getString("labels.afterComa") + ":");
        labelAfterComa.setBounds(0, 329, 104, 16);
        leftPanel.add(labelAfterComa);

        spinnerNumsCount.setModel(new

                SpinnerNumberModel(4, 4, 30, 1));
        spinnerNumsCount.setBounds(0, 357, 104, 28);
        spinnerNumsCount.setPreferredSize(new Dimension(105, 28));
        spinnerNumsCount.setMaximumSize(new Dimension(150, 28));
        leftPanel.add(spinnerNumsCount);

        buttonSolve = new JButton("\u0420\u0435\u0448\u0438\u0442\u044C");
        buttonSolve.addActionListener(new ActionListener() {// Запуск
            // вычисление
            // решения
            public void actionPerformed(ActionEvent arg0) {

                if (var == 1) {
                    GaussObject gausOb = new GaussObject(spinnerNumsCount, spinnerMatrixSize, table, table2);
                    scrollPane_1.setViewportView(gausOb.getAnswerTable());
                }
                if (var == 2) {
                    JacobyObject jo = new JacobyObject(spinnerNumsCount, spinnerMatrixSize, table, table2);
                    scrollPane_1.setViewportView(jo.getResultTable());
                }
                if (var == 3) {
                    GaussSeidelObject gzo = new GaussSeidelObject(spinnerNumsCount, spinnerMatrixSize, table, table2);
                    scrollPane_1.setViewportView(gzo.getAnswerTable());
                }
                if (var == 4) {
                    DetObject detob = new DetObject(spinnerMatrixSize, spinnerNumsCount, table);
                    scrollPane_1.setViewportView(detob.getAnswertable());
                }
            }
        });
        buttonSolve.setBounds(0, 397, 105, 65);
        buttonSolve.setPreferredSize(new Dimension(105, 65));
        buttonSolve.setMaximumSize(new Dimension(150, 65));
        buttonSolve.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(buttonSolve);


        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(116, 25, 288, 360);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_2.setBounds(416, 25, 84, 345);
        scrollPane_2.setPreferredSize(new Dimension(84, 345));
        rightPanel.add(scrollPane_2, BorderLayout.CENTER);


        scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane_1.setBounds(116, 397, 288, 65);
        scrollPane_1.setPreferredSize(new Dimension(288, 75));
        centerPanel.add(scrollPane_1, BorderLayout.PAGE_END);

        labelSolve = new JLabel(bundle.getString("labels.solution") + ":", SwingConstants.CENTER);
        labelSolve.setPreferredSize(new Dimension(100, 75));
        labelSolve.setBounds(427, 426, 61, 14);
        rightPanel.add(labelSolve, BorderLayout.PAGE_END);


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
            spinnerMatrixSize.setValue(x);
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
                    System.out.println("setTable value " + val);
                }
            }
            labelCellWidth.setEnabled(true);
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
        System.out.println("мы тут");
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

    public void initComponentsI18n(ResourceBundle bundle) {
        labelCellWidth.setText(bundle.getString("labels.cellWidth"));
        labelInput.setText(bundle.getString("labels.input"));
        labelSize.setText(bundle.getString("labels.size"));
        labelMatrix.setText(bundle.getString("labels.matrix"));
        labelNumsCount.setText(bundle.getString("labels.numsCount"));
        labelAfterComa = new JLabel(bundle.getString("labels.afterComa") + ":");
        labelSolve.setText(bundle.getString("labels.solution") + ":");
        buttonSetTable.setText(bundle.getString("buttons.accept"));
    }
}
