package gui.methodsPanes;

import graphbuilder.math.Expression;
import graphbuilder.math.ExpressionTree;
import gui.Graf;
import gui.PanelG;
import gui.methodsObjects.*;

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
    private math.Simpson simpson;
    private math.Iterations iterations;
    private math.Progonka progonka;
    private math.Trapezium trap;
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

        JButton btnSum = new JButton("+");
        panel_1.add(btnSum);
        btnSum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "+");
            }
        });
        btnSum.setPreferredSize(new Dimension(90, 25));

        JButton btnDifference = new JButton("-");
        btnDifference.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "-");
            }
        });
        btnDifference.setPreferredSize(new Dimension(90, 25));
        panel_1.add(btnDifference);

        JButton btnMultiplication = new JButton("*");
        btnMultiplication.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "*");
            }
        });
        btnMultiplication.setPreferredSize(new Dimension(90, 25));
        panel_1.add(btnMultiplication);

        JButton btnDivision = new JButton("/");
        btnDivision.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "/");
            }
        });
        btnDivision.setPreferredSize(new Dimension(90, 25));
        panel_1.add(btnDivision);

        JButton btnSqrt = new JButton("\u221A");
        btnSqrt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "sqrt()");
            }
        });
        btnSqrt.setPreferredSize(new Dimension(90, 25));
        panel_1.add(btnSqrt);


        JButton btnExp = new JButton("exp");
        panel_1.add(btnExp);
        btnExp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "e");
            }
        });
        btnExp.setPreferredSize(new Dimension(90, 25));

        JButton btnN = new JButton("n!");
        btnN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "fact()");
            }
        });
        btnN.setPreferredSize(new Dimension(90, 25));
        panel_1.add(btnN);

        JButton btnMod = new JButton("mod");
        btnMod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "mod()");
            }
        });
        btnMod.setPreferredSize(new Dimension(90, 25));
        panel_1.add(btnMod);

        JButton btnPi = new JButton("\u03C0");
        btnPi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "Pi");
            }
        });
        btnPi.setPreferredSize(new Dimension(90, 25));
        panel_1.add(btnPi);
        btnPi.setFont(new Font("SimSun", Font.PLAIN, 16));

        JButton btnPow = new JButton("x^y");
        btnPow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "^");
            }
        });
        btnPow.setPreferredSize(new Dimension(90, 25));
        panel_1.add(btnPow);

        JButton btnLn = new JButton("ln");
        btnLn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "ln()");
            }
        });
        btnLn.setPreferredSize(new Dimension(90, 25));
        panel_1.add(btnLn);

        JButton btnSin = new JButton("sin");
        btnSin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "sin()");
            }
        });
        btnSin.setPreferredSize(new Dimension(90, 25));
        btnSin.setSize(new Dimension(50, 10));
        panel_1.add(btnSin);

        JButton btnCos = new JButton("cos");
        btnCos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "cos()");
            }
        });
        btnCos.setPreferredSize(new Dimension(90, 25));
        btnCos.setSize(new Dimension(50, 10));
        panel_1.add(btnCos);

        JButton btnTan = new JButton("tg");
        btnTan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "tan()");
            }
        });
        btnTan.setPreferredSize(new Dimension(90, 25));
        btnTan.setSize(new Dimension(50, 10));
        panel_1.add(btnTan);

        JButton btnZap = new JButton(",");
        btnZap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, ".");
            }
        });
        btnZap.setPreferredSize(new Dimension(90, 25));
        panel_1.add(btnZap);

        JButton btnLog = new JButton("log(x, y)");
        btnLog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "log(, )");
            }
        });
        btnLog.setPreferredSize(new Dimension(90, 25));
        panel_1.add(btnLog);

        JButton btnSinh = new JButton("sh");
        btnSinh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "Sinh()");
            }
        });
        btnSinh.setPreferredSize(new Dimension(90, 25));
        btnSinh.setSize(new Dimension(50, 10));
        panel_1.add(btnSinh);

        JButton btnCosh = new JButton("ch");
        btnCosh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "cosh()");
            }
        });
        btnCosh.setPreferredSize(new Dimension(90, 25));
        btnCosh.setSize(new Dimension(50, 10));
        panel_1.add(btnCosh);

        JButton btnTanh = new JButton("th");
        btnTanh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "Tanh()");
            }
        });
        btnTanh.setPreferredSize(new Dimension(90, 25));
        btnTanh.setSize(new Dimension(50, 10));
        panel_1.add(btnTanh);

        JButton btnPerem = new JButton("x");
        btnPerem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "x");

            }
        });
        btnPerem.setPreferredSize(new Dimension(90, 25));
        panel_1.add(btnPerem);

        JButton btnLg = new JButton("lg");
        btnLg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "lg()");
            }
        });
        btnLg.setPreferredSize(new Dimension(90, 25));
        panel_1.add(btnLg);

        JButton btnAsin = new JButton("arcsin");
        btnAsin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "Asin()");
            }
        });
        btnAsin.setPreferredSize(new Dimension(90, 25));
        btnAsin.setSize(new Dimension(50, 10));
        panel_1.add(btnAsin);

        JButton btnAcos = new JButton("arccos");
        btnAcos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "Acos()");
            }
        });
        btnAcos.setPreferredSize(new Dimension(90, 25));
        btnAcos.setSize(new Dimension(50, 10));
        panel_1.add(btnAcos);

        JButton btnAtan = new JButton("arctg");
        btnAtan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "Atan()");
            }
        });
        btnAtan.setPreferredSize(new Dimension(90, 25));
        btnAtan.setSize(new Dimension(50, 10));
        panel_1.add(btnAtan);


        JButton btnSkob = new JButton("(");
        btnSkob.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "(");
            }
        });
        btnSkob.setPreferredSize(new Dimension(90, 25));
        panel_1.add(btnSkob);

        JButton button_7 = new JButton("()");
        button_7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setButton(progPane, newtonPane, integralPane, "()");
            }
        });
        button_7.setPreferredSize(new Dimension(90, 25));
        panel_1.add(button_7);

        JButton btnAsinh = new JButton("arcsh");
        btnAsinh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "Asinh()");
            }
        });
        btnAsinh.setPreferredSize(new Dimension(90, 25));
        btnAsinh.setSize(new Dimension(50, 10));
        panel_1.add(btnAsinh);

        JButton btnAcosh = new JButton("arcch");
        btnAcosh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "arcch()");
            }
        });
        btnAcosh.setPreferredSize(new Dimension(90, 25));
        btnAcosh.setSize(new Dimension(50, 10));
        panel_1.add(btnAcosh);

        JButton btnAtanh = new JButton("arcth");
        btnAtanh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setButton(progPane, newtonPane, integralPane, "arcth()");
            }
        });
        btnAtanh.setPreferredSize(new Dimension(90, 25));
        btnAtanh.setSize(new Dimension(50, 10));
        panel_1.add(btnAtanh);


        JButton buttonRightBracket = new JButton(")");
        buttonRightBracket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setButton(progPane, newtonPane, integralPane, ")");
            }
        });
        buttonRightBracket.setPreferredSize(new Dimension(90, 25));
        panel_1.add(buttonRightBracket);

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


}
