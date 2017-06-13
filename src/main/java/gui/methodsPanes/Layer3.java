package gui.methodsPanes;

import graphbuilder.math.Expression;
import graphbuilder.math.ExpressionTree;
import gui.Graf;
import gui.PanelG;
import gui.methodsObjects.*;
import gui.resources.MethodNames;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by razor on 08.06.2017.
 */
public class Layer3 extends JLayeredPane {
    private int var;
    private JTable table2;

    private IntegralPane integralPane = new IntegralPane();
    private ProgonkaPane progPane = new ProgonkaPane();
    private NewtonPane newtonPane = new NewtonPane();

    private final JButton buttonGraf = new JButton(
            "\u041F\u043E\u0441\u043C\u043E\u0442\u0440\u0435\u0442\u044C \u0433\u0440\u0430\u0444\u0438\u043A");
    private final JButton button_iterations = new JButton("\u0420\u0435\u0448\u0438\u0442\u044C");
    private final JButton button_progonka = new JButton("\u0420\u0435\u0448\u0438\u0442\u044C");
    private final JButton btnNewton = new JButton("\u0420\u0435\u0448\u0438\u0442\u044C");
    private final JButton btn_simpson_trapezium = new JButton("\u0420\u0435\u0448\u0438\u0442\u044C");
    private final JScrollPane scrollPane_6 = new JScrollPane();

    public Layer3(int varVal) {
        this.var = varVal;

        scrollPane_6.setBounds(94, 367, 396, 80);
        add(scrollPane_6);

        final JSlider slider_2 = new JSlider();
        slider_2.setVisible(false);
        slider_2.setValue(40);
        slider_2.setMinimum(25);
        slider_2.setMaximum(200);
        slider_2.setBounds(6, 411, 89, 23);
        slider_2.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                int i = 0;
                if (var == 13) i = 1;
                for (i = 0; i < table2.getColumnCount(); i++) {
                    table2.getColumnModel().getColumn(i).setPreferredWidth(slider_2.getValue());
                }

                scrollPane_6.setViewportView(table2);
                table2.getTableHeader().resizeAndRepaint();
            }
        });
        add(slider_2);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new

                BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_1.setBounds(6, 173, 484, 189);
        add(panel_1);
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));


        JButton[] buttons = new JButton[30];
        for (int i = 0; i < 30; i++) {
            buttons[i] = new JButton(MethodNames.buttonLabels[i]);
            panel_1.add(buttons[i]);
            buttons[i].addActionListener(new ButtonActionListener(MethodNames.buttonFuntions[i]));
            buttons[i].setPreferredSize(new Dimension(90, 25));
            if(i==8)buttons[i].setFont(new Font("SimSun", Font.PLAIN, 16));
        }

        integralPane.setBounds(0, 30, 500, 100);
        add(integralPane);

        buttonGraf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (var == 13) {

                    PanelG g = new PanelG();
                    // Expression z = ExpressionTree.parse(textField.getText());

                    double a = (Double.parseDouble(progPane.getTextField_a()));
                    double b = (Double.parseDouble(progPane.getTextField_b()));
//                    double i1 = 0;
                    int n = table2.getColumnCount() - 1;
                    double step = (b - a) / (n - 1);
                    double[] XY = new double[n];
                    for (int i = 0; i < n; i++) {
                        XY[i] = a;
                        a += step;
                    }
                    double[] YX = new double[n];
                    for (int i = 0; i < n; i++)
                        YX[i] = progPane.getTableValue(1, i + 1);
                    g.setYX(YX);
                    g.setXY(XY);
                    g.setMaximum(b);
                    System.out.println(XY.length);
                    g.show();
                } else if (var == 7 || var == 8) {
                    Graf g = new Graf();
                    Expression z = ExpressionTree.parse(newtonPane.getTextFieldS());
                    g.setZ(z);
                    g.show();
                } else {
                    Graf g = new Graf();
                    Expression z = ExpressionTree.parse(integralPane.getTextFieldS());
                    g.setZ(z);
                    g.show();
                }
            }
        });
        buttonGraf.setBounds(6, 444, 484, 28);
        add(buttonGraf);

        btn_simpson_trapezium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (var == 12) {//simpson
                    SimpsonObject simpObj = new SimpsonObject(integralPane);
                    table2 = simpObj.getAnswerTable();
                    scrollPane_6.setViewportView(table2);
                }
                if (var == 11) {//trapezium
                    TrapeziumObject trapObj = new TrapeziumObject(integralPane);
                    table2 = trapObj.getTable2();
                    scrollPane_6.setViewportView(table2);
                }
            }
        });

        JLabel labelSolution = new JLabel("\u0420\u0435\u0448\u0435\u043D\u0438\u0435:");
        labelSolution.setFont(new

                Font("SansSerif", Font.BOLD, 12));
        labelSolution.setBounds(6, 367, 82, 16);
        add(labelSolution);
        btn_simpson_trapezium.setBounds(6, 386, 89, 23);
        add(btn_simpson_trapezium);

        button_iterations.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IterationsObject iterObj = new IterationsObject(newtonPane);
                table2 = iterObj.getAnswerTable();
                newtonPane.setTable(iterObj.getTable());
//                table = iterObj.getTable();
                slider_2.setVisible(iterObj.isSolved());
//                scrollPane_5.setViewportView(table);
                scrollPane_6.setViewportView(table2);
            }
        });
        button_iterations.setBounds(6, 386, 89, 23);
        add(button_iterations);

        newtonPane.setBounds(0, 0, 500, 200);
        add(newtonPane);

        btnNewton.setBounds(6, 386, 89, 23);
        btnNewton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                NewtonObject nob = new NewtonObject(newtonPane);

                scrollPane_6.setViewportView(nob.getAnswerTable());
                slider_2.setVisible(true);
                table2 = nob.getAnswerTable();
            }

        });
        add(btnNewton);


        progPane.setBounds(0, 0, 500, 200);
        add(progPane);

        button_progonka.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProgonkaObject progObj = new ProgonkaObject(progPane);
                if (progObj.isSolved()) slider_2.setVisible(true);
                table2 = progObj.getAnswerTable();
                scrollPane_6.setViewportView(table2);
            }
        });
        button_progonka.setBounds(6, 386, 89, 23);
        add(button_progonka);

    }

    public void setButton(ProgonkaPane progPane, NewtonPane newtonPane, IntegralPane integralPane, String text) {
        String s = "";
        if (integralPane.isVisible()) {
            s = integralPane.getTextFieldS();
            integralPane.setTextFieldS(s + text);
        }
        if (newtonPane.isVisible()) {
            s = newtonPane.getTextFieldS();
            newtonPane.setTextFieldS(s + text);
        }
        if (progPane.isVisible()) {
            int col = 0;
            System.out.println("1.col=" + progPane.getSelectedFuncColumn());
            if (progPane.getSelectedFuncColumn() >= 0)
                col = progPane.getSelectedFuncColumn();
            if (progPane.getTableFuncValue(col) != null)
                s = progPane.getTableFuncValue(col);
            s += text;
            progPane.setTableFuncValue(s, col);
        }
    }

    public void setVisible(int var) {
        this.var = var;
//        table = new JTable(0, 0);
//        table1 = new JTable(0, 0);
        table2 = new JTable(0, 0);// Создание таблицы решений
        switch (var) {
            case 7:
            case 8:
                integralPane.setVisible(false);
                progPane.setVisible(false);
                newtonPane.setVisible(true);
                btn_simpson_trapezium.setVisible(false);
                if (var == 7) {
                    btnNewton.setVisible(false);
                    button_iterations.setVisible(true);
                }
                if (var == 8) {
                    button_iterations.setVisible(false);
                    btnNewton.setVisible(true);
                }
                break;
            case 11:
            case 12:
                btnNewton.setVisible(false);
                button_iterations.setVisible(false);
                progPane.setVisible(false);
                newtonPane.setVisible(false);
                integralPane.setVisible(true);
                btn_simpson_trapezium.setVisible(true);

                break;
            case 13:
                integralPane.setVisible(false);
                newtonPane.setVisible(false);
                progPane.setVisible(true);
                btn_simpson_trapezium.setVisible(false);
                btnNewton.setVisible(false);
                button_iterations.setVisible(false);
                button_progonka.setVisible(true);
                break;
        }
        if (var != 13) scrollPane_6.setViewportView(table2);
    }

    public void Open(int var, BufferedReader br, StringTokenizer st) {
        String s;
        try {
            switch (var) {
                case 13:
                    progPane.loadData(st, br);
                    break;
                case 7:
                case 8:
                    newtonPane.loadData(st);
                    break;
                case 11:
                case 12:
                    integralPane.setTextField_2Text(st.nextToken());
                    integralPane.setTextField_1Text(st.nextToken());
                    integralPane.setTextFieldS(st.nextToken());
                    break;
                default:
                    break;
            }
            br.close();
        } catch (Exception e) {

        }
    }

    public void SaveData(int var, PrintWriter pw) {
        String s = "";

        switch (var) {
            case 13:
                progPane.saveData(pw);
                break;
            case 7:
            case 8:
                newtonPane.saveData(pw);
            case 11:
            case 12:
                s += integralPane.getTextField_2Text() + " ";
                s += integralPane.getTextField_1Text() + " ";
                s += integralPane.getTextFieldS() + " ";
                pw.print(s);
                break;
            default:
                break;
        }
    }

    class ButtonActionListener implements ActionListener {
        private String func;

        public ButtonActionListener(String func) {
            this.func = func;
        }

        public void actionPerformed(ActionEvent e) {
            setButton(progPane, newtonPane, integralPane, func);
        }
    }

}
