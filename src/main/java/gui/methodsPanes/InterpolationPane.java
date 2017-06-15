package gui.methodsPanes;

import gui.resources.InterpolationGraphPane;
import keypoint.PngEncoder;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * Created by razor on 08.06.2017.
 */
public class InterpolationPane extends JLayeredPane {
    private JFileChooser dlg = new JFileChooser(".");
    private final JScrollPane interpScroll = new JScrollPane();
    private JTextField textFieldValueX;
    private JTextField textFieldValueY;
    private JTable table;
    private int index;
    private Image buffer;
    private math.Lagrange l;
    private JButton btnGraph;
    private JButton buttonYxValue;
    private JButton buttonSaveGraph;
    private JLabel labelInputX;
    private JLabel labelDots;
    private ResourceBundle bundle;

    public InterpolationPane(ResourceBundle bundleValue) {
        this.bundle = bundleValue;
        setBounds(6, 6, 499, 470);

        interpScroll.setBorder(new LineBorder(Color.GRAY, 2, true));

        interpScroll.setBounds(6, 25, 127, 238);

        final InterpolationGraphPane panelGraph = new InterpolationGraphPane();
        panelGraph.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        panelGraph.setBounds(145, 6, 340, 344);
        add(panelGraph);

        JButton btnPlus = new JButton("+");
        btnPlus.setBounds(6, 265, 59, 28);
        add(btnPlus);
        btnPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table = changeNumsCount(table, index, +1);
                interpScroll.setViewportView(table);
                index++;

            }
        });
        setLayer(interpScroll, 0);
        add(interpScroll);

        JButton btnMinus = new JButton("-");
        btnMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final int n = index;
                if (n > 1) {
                    table = changeNumsCount(table, index, -1);
                    interpScroll.setViewportView(table);
                    index--;
                }
            }
        });
        btnMinus.setBounds(74, 265, 59, 28);
        add(btnMinus);

        JScrollPane scrollPane_8 = new JScrollPane();
        scrollPane_8.setBorder(new LineBorder(Color.GRAY, 2, true));
        scrollPane_8.setAutoscrolls(true);
        scrollPane_8.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane_8.setBounds(145, 358, 343, 106);
        add(scrollPane_8);

        final JTextPane textPane_3 = new JTextPane();
        textPane_3.setEditable(false);
        scrollPane_8.setViewportView(textPane_3);

        btnGraph = new JButton(bundle.getString("buttons.buildGraph"));
        btnGraph.addActionListener(new ActionListener() {// Интерполяция
            // Лагранжа
            public void actionPerformed(ActionEvent e) {
                textPane_3.setContentType("text/html");
                double[] x = new double[index];
                double[] y = new double[index];
                int acces = 0;
                try {
                    for (int i = 0; i < index; i++) {
                        x[i] = Double.parseDouble(table.getValueAt(i, 0).toString());
                        y[i] = Double.parseDouble(table.getValueAt(i, 1).toString());
                    }
                } catch (Exception eeee) {
                    JOptionPane.showMessageDialog(null, "Введены некорректные символы!", "Ошибка",// нарушены
                            // условия
                            // сходимости
                            // метода
                            JOptionPane.INFORMATION_MESSAGE);
                    acces = 1;
                }
                for (int i = 0; i < x.length; i++) {
                    for (int j = 0; j < x.length; j++) {
                        if (x[i] == x[j] && i != j) {
                            acces = 1;
                        }
                    }
                }
                if (acces == 0) {
                    l = new math.Lagrange(x, y);
                    // Graphics g = panelGraph.getGraphics();
                    buffer = new BufferedImage(336, 345, BufferedImage.TYPE_INT_RGB);
                    // Graphics g = panelGraph.getGraphics();
                    panelGraph.setXY(x);
                    panelGraph.setYX(y);
                    textPane_3.setText(bundle.getString("labels.shortType") + "<br>L = " + l.PolToString(l.eqInfo())
                            + "<br>" + bundle.getString("labels.longType") + "<br>L = " + l.getLagrString());
                    Graphics g = buffer.getGraphics();
                    panelGraph.Graf_paint(g);

                    g = panelGraph.getGraphics();
                    panelGraph.Graf_paint(g);
                    try {
                        saveToFile("lagr.png");
                    } catch (IOException e1) {
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Координата X не должна повторяться!", "Ошибка",// нарушены
                            // условия
                            // сходимости
                            // метода
                            JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });
        btnGraph.setBounds(6, 293, 127, 28);
        add(btnGraph);

        buttonSaveGraph = new JButton(bundle.getString("buttons.save"));
        buttonSaveGraph.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (dlg.showSaveDialog(InterpolationPane.this) != JFileChooser.APPROVE_OPTION)
                        return;
                    FileOutputStream out = null;
                    String fileName = dlg.getSelectedFile().getAbsolutePath() + ".png";
                    saveToFile(fileName);
                } catch (IOException e1) {
                }
            }
        });
        buttonSaveGraph.setBounds(6, 322, 127, 28);
        add(buttonSaveGraph);

        labelDots = new JLabel(bundle.getString("labels.dots"));
        labelDots.setBounds(49, 6, 55, 16);
        add(labelDots);

        textFieldValueX = new JTextField();
        textFieldValueX.setBounds(6, 405, 64, 28);
        add(textFieldValueX);
        textFieldValueX.setColumns(10);

        buttonYxValue = new JButton(bundle.getString("buttons.valueYx"));
        buttonYxValue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double y = 0;
                try {
                    y = l.getLagrY(Double.parseDouble(textFieldValueX.getText().toString()));
                    y = Math.round(y * 1000);
                    textFieldValueY.setText(Double.toString(y / 1000));
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, bundle.getString("errors.incorrectSymbol"), bundle.getString("errors.error"),// нарушены
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        buttonYxValue.setBounds(6, 436, 127, 28);
        add(buttonYxValue);

        textFieldValueY = new JTextField();
        textFieldValueY.setEditable(false);
        textFieldValueY.setBounds(69, 405, 64, 28);
        add(textFieldValueY);
        textFieldValueY.setColumns(10);

        labelInputX = new JLabel(bundle.getString("labels.inputX"));
        labelInputX.setBounds(39, 364, 68, 16);
        add(labelInputX);

        JLabel lblXYx = new JLabel("x:                y(x):");
        lblXYx.setBounds(36, 390, 89, 16);
        add(lblXYx);
    }

    public void load(StringTokenizer st) {
        try {
            index = Integer.parseInt(st.nextToken());
            table = new JTable(index, 2);
            for (int i = 0; i < 2; i++) {
                table.getColumnModel().getColumn(i).setMinWidth(50);
                table.getColumnModel().getColumn(i).setMaxWidth(200);
                table.getColumnModel().getColumn(i).setPreferredWidth(60);
            }
            table.getColumnModel().getColumn(0).setHeaderValue("x");
            table.getColumnModel().getColumn(1).setHeaderValue("y");
            for (int i = 0; i < index; i++) {
                for (int j = 0; j < 2; j++)
                    table.getModel().setValueAt(Double.parseDouble(st.nextToken()), i, j);
            }
            table.getTableHeader().resizeAndRepaint();
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.revalidate();
            table.repaint();
            interpScroll.setViewportView(table);
        } catch (Exception ee) {
        }
    }

    public void saveData(String s, PrintWriter pw) {
        s += index + " ";
        for (int i = 0; i < index; i++)
            for (int j = 0; j < 2; j++)
                s += table.getModel().getValueAt(i, j) + " ";
        pw.print(s);
    }

    public void saveToFile(String fileName) throws IOException {
        PngEncoder enc = new PngEncoder(buffer);
        enc.setCompressionLevel(9);
        FileOutputStream fw = new FileOutputStream(fileName);
        fw.write(enc.pngEncode());
        fw.close();
    }

    public JTable changeNumsCount(JTable table, int n, int type) {
        double[][] m;
        if (type > 0) m = new double[n + 1][2];
        else m = new double[n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                m[i][j] = Double.parseDouble(table.getValueAt(i, j).toString());

            }
        }
        table = new JTable(n + type, 2);
        for (int i = 0; i < 2; i++) {
            table.getColumnModel().getColumn(i).setMinWidth(50);
            table.getColumnModel().getColumn(i).setMaxWidth(200);
            table.getColumnModel().getColumn(i).setPreferredWidth(60);
        }

        table.getColumnModel().getColumn(0).setHeaderValue("x");
        table.getColumnModel().getColumn(1).setHeaderValue("y");
        for (int i = 0; i < n + type; i++) {
            for (int j = 0; j < 2; j++) {
                table.setValueAt(m[i][j], i, j);
            }
        }
        table.getTableHeader().resizeAndRepaint();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.revalidate();
        table.repaint();
        return table;
    }

    public void initComponentsI18n(ResourceBundle bundle) {
        this.bundle = bundle;
        btnGraph.setText(bundle.getString("buttons.buildGraph"));
        labelInputX.setText(bundle.getString("labels.inputX"));
        buttonYxValue.setText(bundle.getString("buttons.valueYx"));
    }
}
