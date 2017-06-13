package gui.methodsPanes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by razor on 09.06.2017.
 */
public class ProgonkaPane extends JLayeredPane {

    public String getTableFuncValue(int index) {
        return tableFunc.getModel().getValueAt(0, index).toString();
    }

    public void setTableFuncValue(String value,int column) {
        tableFunc.getModel().setValueAt(value, 0, column);
    }

    public int getSelectedFuncColumn(){
       return tableFunc.getSelectedColumn();
    }


    public Double getTableValue(int row, int col) {
        double value = 0;

        try {
            value = Double.parseDouble(table.getModel().getValueAt(row, col).toString());
        } catch (Exception e) {
        }
        return value;
    }

    public int getTableColumnCount() {
        return table.getColumnCount();
    }

    public String getTextField_B() {
        return textField_B.getText();
    }

    public String getTextField_A() {

        return textField_A.getText();
    }

    public String getTextField_b() {

        return textField_b.getText();
    }

    public String getTextField_a() {

        return textField_a.getText();
    }

    private JTable table;
    private JTable tableFunc;

    private JTextField textField_a;
    private JTextField textField_b;
    private JTextField textField_A;
    private JTextField textField_B;

    private final JScrollPane scrollPane_func = new JScrollPane();
    private final JScrollPane scrollPane = new JScrollPane();

    private final JLabel labelAlfa = new JLabel("\u03B1");
    private final JLabel labelBeta = new JLabel("\u03B2");
    private final JLabel label_A = new JLabel("A");
    private final JLabel label_a = new JLabel("a =");
    private final JLabel label_B = new JLabel("B");
    private final JLabel label_b = new JLabel("b =");
    private final JLabel labelFunction = new JLabel("y'' + p(x)y' + q(x)y = f(x)");

    public ProgonkaPane() {
        textField_a = new JTextField();
        textField_a.setBounds(22, 25, 51, 23);
        add(textField_a);
        textField_a.setColumns(10);

        textField_b = new JTextField();
        textField_b.setColumns(10);
        textField_b.setBounds(22, 48, 51, 23);
        add(textField_b);

        label_a.setBounds(0, 30, 26, 16);
        add(label_a);

        label_b.setBounds(0, 52, 26, 16);
        add(label_b);

        textField_A = new JTextField();
        textField_A.setBounds(436, 101, 54, 23);
        add(textField_A);
        textField_A.setColumns(10);

        textField_B = new JTextField();
        textField_B.setBounds(436, 138, 54, 23);
        add(textField_B);
        textField_B.setColumns(10);

        label_A.setBounds(446, 88, 45, 16);
        add(label_A);

        label_B.setBounds(445, 127, 45, 16);
        add(label_B);

        labelAlfa.setBounds(63, 107, 18, 16);
        add(labelAlfa);

        labelBeta.setBounds(63, 134, 18, 16);
        add(labelBeta);

        labelFunction.setBounds(188, 0, 215, 16);
        add(labelFunction);

        scrollPane_func.setBounds(73, 18, 417, 58);
        add(scrollPane_func);

        tableFunc = new JTable();
        tableFunc.setModel(new DefaultTableModel(new Object[][]{
                {
                        " ", " ", null
                },}, new String[]

                {
                        "p(x)", "q(x)", "f(x)"
                }));
        scrollPane_func.setViewportView(tableFunc);

        scrollPane.setBounds(73, 88, 360, 80);
        add(scrollPane);
        scrollPane.setVisible(true);
        table = new JTable(2, 2);
        for (int i = 0; i < 2; i++) {
            table.getColumnModel().getColumn(i).setMinWidth(25);
            table.getColumnModel().getColumn(i).setMaxWidth(200);
            table.getColumnModel().getColumn(i).setPreferredWidth(100);
        }
        table.getColumnModel().getColumn(0).setHeaderValue("y'");
        table.getColumnModel().getColumn(1).setHeaderValue("y");
        table.getTableHeader().resizeAndRepaint();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setBounds(141, 12, 438, 305);
        scrollPane.setViewportView(table);
    }

    public void saveData(PrintWriter pw) {
        String s;
        s = tableFunc.getModel().getValueAt(0, 0).toString();
        pw.println(s);
        s = tableFunc.getModel().getValueAt(0, 1).toString();
        pw.println(s);
        s = tableFunc.getModel().getValueAt(0, 2).toString();
        pw.println(s);
        s = textField_a.getText() + " ";
        s += textField_b.getText() + " ";
        s += textField_A.getText() + " ";
        s += textField_B.getText() + " ";
        for (int i = 0; i < 2; i++) {
            s += table.getModel().getValueAt(0, i) + " ";
            s += table.getModel().getValueAt(1, i) + " ";
        }
        pw.print(s);
    }

    public void loadData(StringTokenizer st, BufferedReader br) {
        try {
            tableFunc.getModel().setValueAt(st.nextToken(), 0, 0);
            String s = br.readLine();
            st = new StringTokenizer(s);
            tableFunc.getModel().setValueAt(st.nextToken(), 0, 1);
            s = br.readLine();
            st = new StringTokenizer(s);
            tableFunc.getModel().setValueAt(st.nextToken(), 0, 2);
            s = br.readLine();
            st = new StringTokenizer(s);
            textField_a.setText(st.nextToken());
            textField_b.setText(st.nextToken());
            textField_A.setText(st.nextToken());
            textField_B.setText(st.nextToken());
            table = new JTable(2, 2);
            for (int i = 0; i < 2; i++) {
                table.getColumnModel().getColumn(i).setMinWidth(50);
                table.getColumnModel().getColumn(i).setMaxWidth(200);
                table.getColumnModel().getColumn(i).setPreferredWidth(60);
                table.getModel().setValueAt(Double.parseDouble(st.nextToken()), 0, i);
                table.getModel().setValueAt(Double.parseDouble(st.nextToken()), 1, i);
            }
            table.getColumnModel().getColumn(0).setHeaderValue("y'");
            table.getColumnModel().getColumn(1).setHeaderValue("y");
            table.getTableHeader().resizeAndRepaint();
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.revalidate();
            table.repaint();
            scrollPane.setViewportView(table);
        } catch (Exception e) {
        }
    }
}
