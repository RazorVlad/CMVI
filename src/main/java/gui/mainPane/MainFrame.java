package gui.mainPane;

import gui.methodsPanes.*;
import gui.resources.Constants;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainFrame extends JFrame {

    private int indexGroup;
    private int indexMethod;
    int var;
    JTextField EpsTextField;

    private Locale currentLocale;
    public static ResourceBundle bundle;
    private Menu menuBar;

    public static double Eps = 0.001;
    public static String methodSolution = "";

    private JFileChooser dlg = new JFileChooser(".");

    KrylovPane layeredPane_Krylov;
    GaussMethodsPane gaussMethodsPane;
    FrontPane frontPane;
    ReverseMatrixPane reverseMatrixPane;
    CalcMethodsPane calcMethodsPane;
    InterpolationPane interpolationPane;

    JLabel lblEps;
    JLabel lblH;

    private static void SetSkin() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SetSkin();
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainFrame() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                final SplashScreen splash = SplashScreen.getSplashScreen();
                if (splash == null) {
                    return;
                }
                Graphics2D g = splash.createGraphics();
                if (g == null) {
                    return;
                }
                for (int i = 0; i < 100; i++) {
                    gui.resources.splash.renderSplashFrame(g, i);
                    splash.update();
                    try {
                        Thread.sleep(90);
                    } catch (InterruptedException eq) {
                    }
                }
                splash.close();
                setVisible(true);
                toFront();
            }
        });


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        bundle = ResourceBundle.getBundle("MethodsBundle");
        setTitle(bundle.getString("title"));
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                MainFrame.class.getResource("/javax/swing/plaf/metal/icons/Inform.gif")));
        setBounds(100, 100, 529, 570);


        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(6, 37, 511, 476);
        contentPane.add(panel);

        frontPane = new FrontPane(bundle);
        frontPane.setBounds(6, 6, 499, 470);
        panel.add(frontPane);


        createMenuBar();

        calcMethodsPane = new CalcMethodsPane(var, bundle);
        calcMethodsPane.setVisible(false);

        interpolationPane = new InterpolationPane(bundle);
        interpolationPane.setVisible(false);


        interpolationPane.setBounds(6, 6, 499, 470);
        panel.add(interpolationPane);

        layeredPane_Krylov = new KrylovPane(bundle);
        layeredPane_Krylov.setVisible(false);
        layeredPane_Krylov.setBounds(6, 6, 500, 470);
        panel.add(layeredPane_Krylov);


        calcMethodsPane.setBounds(6, 6, 500, 470);
        panel.add(calcMethodsPane);


        lblH = new JLabel(bundle.getString("labels.epsilon"));

        EpsTextField = new JTextField();
        EpsTextField.setBorder(new LineBorder(UIManager.getColor("nimbusFocus"), 2, true));
        EpsTextField.setVisible(false);
        EpsTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    Eps = Double.parseDouble(EpsTextField.getText());
                } catch (Exception e1) {

                }
            }
        });
        EpsTextField.setText("1");
        EpsTextField.setBounds(258, 6, 122, 28);
        contentPane.add(EpsTextField);
        EpsTextField.setColumns(10);

        gaussMethodsPane = new GaussMethodsPane(var, bundle);
        gaussMethodsPane.setVisible(false);
        gaussMethodsPane.setBounds(6, 6, 499, 470);
        panel.add(gaussMethodsPane);

        lblH.setVisible(false);
        lblH.setBounds(107, 12, 139, 16);
        contentPane.add(lblH);

        reverseMatrixPane = new ReverseMatrixPane(bundle);
        reverseMatrixPane.setVisible(false);
        panel.add(reverseMatrixPane);

        lblEps = new JLabel(bundle.getString("labels.epsilon"));
        lblEps.setVisible(false);
        lblEps.setBounds(157, 6, 103, 28);
        contentPane.add(lblEps);

        frontPane.PUSH.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                indexGroup = frontPane.comboBoxMethodGroup.getSelectedIndex();// индекс выбранной группы методов
                indexMethod = frontPane.comboBoxMethod.getSelectedIndex();// индекс выбранного метода
                setVisible(indexGroup, indexMethod);
            }
        });
    }

//    private static void renderSplashFrame(Graphics2D g, int frame) {
//        final String[] comps = {"foo", "bar", "baz"};
//        g.setComposite(AlphaComposite.Clear);
//        g.fillRect(120, 140, 200, 40);
//        g.setPaintMode();
//        g.setColor(Color.BLACK);
//        g.drawString("Loading " + comps[(frame / 5) % 3] + "...", 120, 150);
//    }

    private void createMenuBar() {

        menuBar = new Menu(this, bundle);
        setJMenuBar(menuBar);
        menuBar.menuSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Save.save(MainFrame.this, var);
            }
        });
        menuBar.menuRus.addActionListener(new ActionListener() { // сохранение
            public void actionPerformed(ActionEvent arg0) {
                currentLocale = new Locale("ru", "RU");
                initComponentsI18n();
            }
        });

        menuBar.menuEng.addActionListener(new ActionListener() { // сохранение
            public void actionPerformed(ActionEvent arg0) {
                currentLocale = new Locale("en", "US");
                initComponentsI18n();
            }
        });
        int k = 0;
        for (int i = 0; i < Constants.methodGroupsNames.length; i++) {
            for (int j = 0; j < Constants.methodsNumbers[i].length; j++) {
                menuBar.methods[k].addActionListener(new MenuActionListener(i, j));
                k++;
            }
        }

        menuBar.menuSaveReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (dlg.showSaveDialog(MainFrame.this) != JFileChooser.APPROVE_OPTION)
                    return;
                String fileName = dlg.getSelectedFile().getAbsolutePath();
                String shortName = dlg.getSelectedFile().getName();
                menuBar.saveReport(fileName, shortName, var);
            }
        });

        menuBar.menu_about.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, Constants.aboutText, bundle.getString("menu.about"),
                        JOptionPane.INFORMATION_MESSAGE);

            }
        });

        menuBar.menu_help.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Desktop desktop = null;
                if (Desktop.isDesktopSupported()) {
                    desktop = Desktop.getDesktop();
                }
                try {
                    ClassLoader classLoader = getClass().getClassLoader();
                    File file = new File(classLoader.getResource("html/Contents.html").getFile());
                    desktop.open(file);
                } catch (IOException ioe) {
                }
            }
        });
        menuBar.menuOpen.addActionListener(new ActionListener() { // out file
            public void actionPerformed(ActionEvent arg0) {
                Load.OpenFile(MainFrame.this);
            }
        });
    }

    private void initComponentsI18n() {
        try {
            bundle = ResourceBundle.getBundle("MethodsBundle", currentLocale);
            lblEps.setText(bundle.getString("labels.epsilon"));
            lblH.setText(bundle.getString("labels.epsilon"));
            menuBar.initComponentI18n(bundle);
            frontPane.initComponentsI18n(bundle);
            calcMethodsPane.initComponentsI18n(bundle);
            gaussMethodsPane.initComponentsI18n(bundle);
            interpolationPane.initComponentsI18n(bundle);
            layeredPane_Krylov.initComponentsI18n(bundle);
            reverseMatrixPane.initComponentsI18n(bundle);
            setTitle(bundle.getString(Constants.methodNames[var]));

        } catch (Exception e) {
        }
    }

    private void setVisible(int methodGroup, int method) {
        frontPane.setVisible(false);
        var = Constants.methodsNumbers[methodGroup][method];
        setTitle(bundle.getString(Constants.methodNames[var]));
        VisibleDispetcher.SetFunctionVisible(MainFrame.this, var);
    }

    class MenuActionListener implements ActionListener {
        private int i;
        private int j;

        public MenuActionListener(int methodGroupIndex, int methodIndex) {
            this.i = methodGroupIndex;
            this.j = methodIndex;
        }

        public void actionPerformed(ActionEvent e) {
            setVisible(i, j);
        }
    }
}
