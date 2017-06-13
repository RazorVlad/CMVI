package gui.methodsPanes;

import javax.swing.*;
import java.awt.*;

/**
 * Created by razor on 09.06.2017.
 */
public class IntegralPane extends JLayeredPane {
    private final JLabel lblDx = new JLabel("dx");
    private final JLabel label_integral = new JLabel("\u222B");

    public String getTextField_2Text() {
        return textField_2.getText();
    }

    public void setTextField_2Text(String text) {
        textField_2.setText(text);
    }

    public String getTextField_1Text() {

        return textField_1.getText();
    }

    public void setTextField_1Text(String text) {

        textField_1.setText(text);
    }

    public String getTextFieldS() {
        return textFieldS.getText();
    }

    public void setTextFieldS(String text) {
        textFieldS.setText(text);
    }

    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textFieldS;

    public IntegralPane() {
        textFieldS = new JTextField();
        textFieldS.setBounds(63, 30, 380, 33);
        add(textFieldS);
        textFieldS.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(63, 0, 57, 26);
        add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(63, 68, 57, 26);
        add(textField_2);
        textField_2.setColumns(10);

        label_integral.setFont(new

                Font("SansSerif", Font.PLAIN, 50));
        label_integral.setBounds(38, 6, 29, 80);
        add(label_integral);

        lblDx.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));

        lblDx.setBounds(444, 29, 35, 29);
        add(lblDx);
    }
}
