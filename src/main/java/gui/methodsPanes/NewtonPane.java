package gui.methodsPanes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by razor on 09.06.2017.
 */
public class NewtonPane extends JLayeredPane {
    public int getIntervalsCount() {
        return intervalsCount;
    }

    private int intervalsCount;

    public String getTextFieldS() {
        return textFieldS.getText();
    }

    public void setTextFieldS(String text) {
        textFieldS.setText(text);
    }

    private JTextField textFieldS;
    private final JLabel lblNewLabel = new JLabel(
            "\u041A\u043E\u0440\u043D\u0438 \u043D\u0430\u0445\u043E\u0434\u044F\u0442\u0441\u044F \u0432 \u0441\u043B\u0435\u0434\u0443\u044E\u0449\u0438\u0445 \u0438\u043D\u0442\u0435\u0440\u0432\u0430\u043B\u0430\u0445:");
    private final JLabel labelFx = new JLabel(" f(x)=");
    private final JLabel lblB = new JLabel("b");
    private final JLabel label_7 = new JLabel("\u0430");
    private final JLabel labelInputData = new JLabel(
            "\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u0434\u0430\u043D\u043D\u044B\u0435:");
    private final JButton button_plus = new JButton("+");
    private final JButton button_minus = new JButton("-");
    private final JScrollPane scrollPane_5 = new JScrollPane();

    public double getTableValue(int row, int col) {
        double value = 0;
        try {
            value = Double.parseDouble(table.getModel().getValueAt(row, col).toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Проверьте данные в таблице (строка " + row + " столбец " + col + "", "Ошибка",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        return value;
    }


    public void setTable(JTable table) {
        this.table = table;
        scrollPane_5.setViewportView(this.table);
    }

    private JTable table;

    public NewtonPane() {

        textFieldS = new JTextField();
        textFieldS.setBounds(63, 30, 380, 33);
        add(textFieldS);
        textFieldS.setColumns(10);

        scrollPane_5.setBounds(73, 88, 360, 80);
        add(scrollPane_5);
        lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNewLabel.setBounds(120, 66, 380, 23);
        add(lblNewLabel);

        labelFx.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
        labelFx.setBounds(12, 25, 76, 38);
        add(labelFx);

        label_7.setFont(new Font("SansSerif", Font.BOLD, 14));
        label_7.setBounds(63, 106, 18, 16);
        add(label_7);

        lblB.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblB.setBounds(63, 131, 18, 16);
        add(lblB);

        labelInputData.setFont(new

                Font("SansSerif", Font.BOLD, 12));
        labelInputData.setBounds(25, 8, 215, 16);
        add(labelInputData);

        button_plus.setPreferredSize(new Dimension(90, 25));
        button_plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                addValues(table);
                table = changeValuesQuantity(intervalsCount, table, 1);
                scrollPane_5.setViewportView(table);
                intervalsCount++;
            }
        });
        button_plus.setFont(new

                Font("SansSerif", Font.PLAIN, 16));
        button_plus.setBounds(6, 102, 57, 23);
        add(button_plus);

        button_minus.setPreferredSize(new

                Dimension(90, 25));
        button_minus.setBounds(6, 127, 57, 23);
        add(button_minus);
        button_minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (intervalsCount > 0) {
                    table = changeValuesQuantity(intervalsCount, table, -1);
                    scrollPane_5.setViewportView(table);
                    intervalsCount--;
                }
            }
        });
        button_minus.setFont(new Font("SansSerif", Font.PLAIN, 16));
    }

    public JTable changeValuesQuantity(int n, JTable table, int opIndex) {
        double[][] m;
        if (opIndex < 0) m = new double[2][n];
        else m = new double[2][n + opIndex];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = Double.parseDouble(table.getValueAt(i, j).toString());

            }
        }
        table = new JTable(2, n + opIndex);
        for (int i = 0; i < n + opIndex; i++) {
            table.getColumnModel().getColumn(i).setMinWidth(50);
            table.getColumnModel().getColumn(i).setMaxWidth(200);
            table.getColumnModel().getColumn(i).setPreferredWidth(60);
            table.getColumnModel().getColumn(i).setHeaderValue("x" + i + ",y" + i);
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n + opIndex; j++) {
                table.setValueAt(m[i][j], i, j);
            }
        }
        table.getTableHeader().resizeAndRepaint();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.revalidate();
        table.repaint();
        return table;
    }

    public void saveData(PrintWriter pw) {
        String s = intervalsCount + " ";
        for (int i = 0; i < intervalsCount; i++) {
            s += table.getModel().getValueAt(0, i);
            s += " ";
            s += table.getModel().getValueAt(1, i);
            s += " ";
        }
        s += getTextFieldS();
        pw.print(s);
    }

    public void loadData(StringTokenizer st) {
        intervalsCount = Integer.parseInt(st.nextToken());
        table = new JTable(2, intervalsCount);
        for (int i = 0; i < intervalsCount; i++) {
            table.getColumnModel().getColumn(i).setMinWidth(50);
            table.getColumnModel().getColumn(i).setMaxWidth(200);
            table.getColumnModel().getColumn(i).setPreferredWidth(60);
            table.getModel().setValueAt(Double.parseDouble(st.nextToken()), 0, i);
            table.getModel().setValueAt(Double.parseDouble(st.nextToken()), 1, i);
            table.getColumnModel().getColumn(i).setHeaderValue("x" + i + ",y" + i);
        }
        table.getTableHeader().resizeAndRepaint();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.revalidate();
        table.repaint();
        scrollPane_5.setViewportView(table);
        textFieldS.setText(st.nextToken());
    }
}
