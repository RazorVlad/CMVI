package gui.mainPane;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ReportFrame extends JFrame {

    final JPanel contentPane;
    final JScrollPane scrollPane = new JScrollPane();
    private JTextPane textPane = new JTextPane();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ReportFrame frame = new ReportFrame();

                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void Rep(String report) {
        textPane.setContentType("text/html");
        textPane.setText(report);
    }

    /**
     * Create the frame.
     */
    public ReportFrame() {
        setTitle("\u041E\u0442\u0447\u0451\u0442");
        setBounds(100, 100, 600, 480);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        textPane.setBorder(new BevelBorder(BevelBorder.RAISED,
                SystemColor.textHighlight, new Color(153, 180, 209), new Color(
                30, 144, 255), new Color(102, 0, 255)));
        contentPane.add(new JScrollPane(textPane), BorderLayout.CENTER);
        textPane.setEditable(false);
    }
}
