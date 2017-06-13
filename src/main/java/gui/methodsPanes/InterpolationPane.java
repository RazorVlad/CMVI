package gui.methodsPanes;

import gui.JPanelGraph;
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
import java.util.StringTokenizer;

/**
 * Created by razor on 08.06.2017.
 */
public class InterpolationPane extends JLayeredPane {
    private JFileChooser dlg = new JFileChooser(".");
    final JScrollPane interpScroll= new JScrollPane();
    private JTextField textField_8;
    private JTextField textField_9;
    JTable table;
    int index;
    Image buffer;
    math.Lagrange l;
    public InterpolationPane() {
        setBounds(6, 6, 499, 470);

        interpScroll.setBorder(new LineBorder(Color.GRAY, 2, true));

        interpScroll.setBounds(6, 25, 127, 238);

        final JPanelGraph panelGraph = new JPanelGraph();
        panelGraph.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        panelGraph.setBounds(145, 6, 340, 344);
        add(panelGraph);

        JButton btnPlus = new JButton("+");
        btnPlus.setBounds(6, 265, 59, 28);
        add(btnPlus);
        btnPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final int n = index + 1;
                double[][] m = new double[n][2];
                for (int i = 0; i < n - 1; i++) {
                    for (int j = 0; j < 2; j++) {
                        m[i][j] = Double.parseDouble(table.getValueAt(i, j).toString());

                    }
                }
                table = new JTable(n, 2);
                for (int i = 0; i < 2; i++) {
                    table.getColumnModel().getColumn(i).setMinWidth(50);
                    table.getColumnModel().getColumn(i).setMaxWidth(200);
                    table.getColumnModel().getColumn(i).setPreferredWidth(60);
                }

                table.getColumnModel().getColumn(0).setHeaderValue("x");
                table.getColumnModel().getColumn(1).setHeaderValue("y");
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < 2; j++) {
                        table.setValueAt(m[i][j], i, j);
                    }
                }
                table.getTableHeader().resizeAndRepaint();
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.revalidate();
                table.repaint();
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
                    double[][] m = new double[n][2];
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < 2; j++) {
                            m[i][j] = Double.parseDouble(table.getValueAt(i, j).toString());

                        }
                    }
                    table = new JTable(n - 1, 2);
                    for (int i = 0; i < 2; i++) {
                        table.getColumnModel().getColumn(i).setMinWidth(50);
                        table.getColumnModel().getColumn(i).setMaxWidth(200);
                        table.getColumnModel().getColumn(i).setPreferredWidth(60);
                    }

                    table.getColumnModel().getColumn(0).setHeaderValue("x");
                    table.getColumnModel().getColumn(1).setHeaderValue("y");
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = 0; j < 2; j++) {
                            table.setValueAt(m[i][j], i, j);
                        }
                    }
                    table.getTableHeader().resizeAndRepaint();
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    table.revalidate();
                    table.repaint();
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

        JButton btnGraph = new JButton("\u041F\u043E\u0441\u0442\u0440\u043E\u0438\u0442\u044C");
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
                    textPane_3.setText("Упрощенный вид:<br>L = " + l.PolToString(l.eqInfo())
                            + "<br>Не упрощенный вид:<br>L = " + l.getLagrString());
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

        JButton btnNewButton = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
        btnNewButton.addActionListener(new ActionListener() {
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
        btnNewButton.setBounds(6, 322, 127, 28);
        add(btnNewButton);

        JLabel label_5 = new JLabel("\u0422\u043E\u0447\u043A\u0438:");
        label_5.setBounds(49, 6, 55, 16);
        add(label_5);

        textField_8 = new JTextField();
        textField_8.setBounds(6, 405, 64, 28);
        add(textField_8);
        textField_8.setColumns(10);

        JButton btnNewButton_1 = new JButton("\u0417\u043D\u0430\u0447\u0435\u043D\u0438\u0435 y(x)");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double y = 0;
                try {
                    y = l.getLagrY(Double.parseDouble(textField_8.getText().toString()));
                    y = Math.round(y * 1000);
                    textField_9.setText(Double.toString(y / 1000));
                } catch (Exception eee) {
                    JOptionPane.showMessageDialog(null, "Введены некорректные символы!", "Ошибка",// нарушены
                            // условия
                            // сходимости
                            // метода
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btnNewButton_1.setBounds(6, 436, 127, 28);
        add(btnNewButton_1);

        textField_9 = new JTextField();
        textField_9.setEditable(false);
        textField_9.setBounds(69, 405, 64, 28);
        add(textField_9);
        textField_9.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 x");
        lblNewLabel_4.setBounds(39, 364, 68, 16);
        add(lblNewLabel_4);

        JLabel lblXYx = new JLabel("x:                y(x):");
        lblXYx.setBounds(36, 390, 89, 16);
        add(lblXYx);
    }

    public void load(StringTokenizer st){
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

    public void saveData(String s, PrintWriter pw){
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
}
