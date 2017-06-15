package gui.mainPane;

import gui.methodsPanes.*;
import gui.resources.FinalTexts;
import gui.resources.MethodNames;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class Methods extends JFrame {

    private int indexGroup, indexMethod, var;
    protected JTextField EpsTextField;

    private Locale currentLocale;
    private ResourceBundle bundle;
    private MenuPane menuBar;


    public static double Eps = 0.001;
    public static String methodSolution = "";


    private JFileChooser dlg = new JFileChooser(".");

    protected KrylovPane layeredPane_Krylov;
    protected GaussMethodsPane gaussMethodsPane;
    protected JLayeredPane layeredPane = new JLayeredPane();
    protected ReverseMatrixPane reverseMatrixPane;
    protected CalcMethodsPane calcMethodsPane;
    protected InterpolationPane interpolationPane;

    private JLabel labelChooseMethodGroup;
    private JLabel labelChooseMethod;
    protected JLabel lblEps;
    protected JLabel lblH;

    public static void SetSkin() {
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
                    Methods frame = new Methods();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Methods() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                final SplashScreen splash = SplashScreen.getSplashScreen();
                if (splash == null) {
                    System.out.println("SplashScreen.getSplashScreen() returned null");
                    return;
                }
                Graphics2D g = splash.createGraphics();
                if (g == null) {
                    System.out.println("g is null");
                    return;
                }
                for (int i = 0; i < 100; i++) {
                    renderSplashFrame(g, i);
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


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bundle = ResourceBundle.getBundle("MethodsBundle");
        setTitle(bundle.getString("title"));
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                Methods.class.getResource("/javax/swing/plaf/metal/icons/Inform.gif")));
        setBounds(100, 100, 529, 570);


        lblEps = new JLabel(bundle.getString("labels.epsilon"));
        lblH = new JLabel(bundle.getString("labels.epsilon"));
        createMenuBar();

        calcMethodsPane = new CalcMethodsPane(var, bundle);
        calcMethodsPane.setVisible(false);

        interpolationPane = new InterpolationPane(bundle);
        interpolationPane.setVisible(false);
        JPanel panel = new JPanel();

        layeredPane.setBounds(6, 6, 499, 470);
        panel.add(layeredPane);

        interpolationPane.setBounds(6, 6, 499, 470);
        panel.add(interpolationPane);

        layeredPane_Krylov = new KrylovPane(bundle);
        layeredPane_Krylov.setVisible(false);
        layeredPane_Krylov.setBounds(6, 6, 500, 470);
        panel.add(layeredPane_Krylov);


        calcMethodsPane.setBounds(6, 6, 500, 470);
        panel.add(calcMethodsPane);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        EpsTextField = new JTextField();
        EpsTextField.setBorder(new LineBorder(UIManager.getColor("nimbusFocus"), 2, true));
        EpsTextField.setVisible(false);
        EpsTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    Eps = Double.parseDouble(EpsTextField.getText().toString());
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

        layeredPane.setDoubleBuffered(true);
        layeredPane.setOpaque(true);
        layeredPane.setPreferredSize(new

                Dimension(200, 300));
        layeredPane.setLayout(null);

        panel.setBounds(6, 37, 511, 476);
        contentPane.add(panel);
        panel.setLayout(null);
        final JComboBox comboBoxMethod = new JComboBox();// выбор группы методов

        String[] model = new String[MethodNames.methodGroupsNames.length];
        for (int i = 0; i < model.length; i++) {
            model[i] = bundle.getString(MethodNames.methodGroupsNames[i]);
        }
        comboBoxMethod.setModel(new DefaultComboBoxModel(model));
        comboBoxMethod.setSelectedIndex(0);
        comboBoxMethod.setBounds(11, 56, 477, 32);
        layeredPane.add(comboBoxMethod);
        comboBoxMethod.setToolTipText("");

        final JButton PUSH = new JButton("");
        PUSH.setRolloverIcon(new ImageIcon("button/move.jpg"));
        PUSH.setPressedIcon(new ImageIcon("button/press.jpg"));
        PUSH.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
        PUSH.setBorder(null);
        PUSH.setIcon(new ImageIcon("button/main.jpg"));

        final JComboBox comboBoxGroup = new JComboBox();// выбор метода
        comboBoxGroup.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                PUSH.setEnabled(true);
            }
        });
        comboBoxGroup.setToolTipText("");
        comboBoxGroup.setBounds(11, 123, 477, 32);
        layeredPane.add(comboBoxGroup);
        comboBoxGroup.setModel(new DefaultComboBoxModel(getComboBoxModel(0)));
        comboBoxGroup.setSelectedIndex(0);

        comboBoxMethod.addItemListener(new ItemListener() {// считывание выбранной
            // группыметодов
            public void itemStateChanged(ItemEvent arg0) {
                int selectedIndex = comboBoxMethod.getSelectedIndex();
                comboBoxGroup.setModel(new DefaultComboBoxModel(getComboBoxModel(selectedIndex)));
            }
        });


        lblEps.setVisible(false);
        lblEps.setBounds(157, 6, 103, 28);
        contentPane.add(lblEps);
        PUSH.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                EpsTextField.setVisible(true);
                lblEps.setVisible(true);
                indexGroup = comboBoxMethod.getSelectedIndex();// индекс выбранной группы методов
                indexMethod = comboBoxGroup.getSelectedIndex();// индекс выбранного метода

                VisibleDispetcher.SetFunctionVisible(Methods.this,MethodNames.methodsNumbers[indexGroup][indexMethod]);
                layeredPane.setVisible(false);
            }
        });

        PUSH.setBounds(173, 300, 155, 121);
        layeredPane.add(PUSH);

        labelChooseMethodGroup = new JLabel(bundle.getString("labels.chooseMethodGroup"));
        labelChooseMethodGroup.setBounds(21, 30, 201, 14);
        layeredPane.add(labelChooseMethodGroup);

        labelChooseMethod = new JLabel(
                bundle.getString("labels.chooseMethod"));
        labelChooseMethod.setBounds(21, 100, 155, 20);
        layeredPane.add(labelChooseMethod);

    }

    static void renderSplashFrame(Graphics2D g, int frame) {
        final String[] comps = {"foo", "bar", "baz"};
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120, 140, 200, 40);
        g.setPaintMode();
        g.setColor(Color.BLACK);
        g.drawString("Loading " + comps[(frame / 5) % 3] + "...", 120, 150);
    }

    public void menuOpen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(bundle.getString("choose.readData"));
        chooser.setCurrentDirectory(new File("."));
        chooser.showOpenDialog(chooser);
        FileReader fr;
        try {
            fr = new FileReader(chooser.getSelectedFile().getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            StringTokenizer st = new StringTokenizer(s);
//            SetFunctionVisible(Integer.parseInt(st.nextToken()));
            s = br.readLine();
            st = new StringTokenizer(s);

            switch (var) {
                case 7:
                case 8:
                case 11:
                case 12:
                case 13:
                    calcMethodsPane.Open(var, br, st);
                    break;
                case 10:
                    try {
                        interpolationPane.load(st);

                        br.close();
                    } catch (Exception e) {
                    }
                    break;
                case 5:
                    try {
                        reverseMatrixPane.load(st, br);
                        br.close();
                    } catch (Exception e) {
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                    gaussMethodsPane.openData(st, br);
                case 6:
                case 9:
                    layeredPane_Krylov.OpenData(st, br);
                default:
                    break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String[] getComboBoxModel(int index) {

        int methodsNumber = MethodNames.methodsNumbers[index].length;
        String[] model = new String[methodsNumber];
        for (int j = 0; j < methodsNumber; j++) {
            model[j] = bundle.getString(MethodNames.methodNames[MethodNames.methodsNumbers[index][j]]);
        }

        return model;
    }

    private void createMenuBar() {
        {
            menuBar = new MenuPane(bundle);
            setJMenuBar(menuBar);
            menuBar.menuSave.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    save();
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
            for (int i = 0; i < MethodNames.methodGroupsNames.length; i++) {
                for (int j = 0; j < MethodNames.methodsNumbers[i].length; j++) {
                    menuBar.methods[k].addActionListener(new MenuActionListener(i, j));
                    k++;
                }
            }

            menuBar.menuSaveReport.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if (dlg.showSaveDialog(Methods.this) != JFileChooser.APPROVE_OPTION)
                        return;
                    String fileName = dlg.getSelectedFile().getAbsolutePath();
                    String shortName = dlg.getSelectedFile().getName();
                    menuBar.saveReport(fileName, shortName, var);
                }
            });

            menuBar.menu_about.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(null, FinalTexts.aboutText, FinalTexts.aboutLabel,
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
                        desktop.open(new File("html/Contents.html"));
                    } catch (IOException ioe) {
                    }
                }
            });
            menuBar.menuOpen.addActionListener(new ActionListener() { // out file
                public void actionPerformed(ActionEvent arg0) {
                    menuOpen();
                }
            });
        }
    }

    private void initComponentsI18n() {
        try {
            bundle = ResourceBundle.getBundle("MethodsBundle", currentLocale);
            labelChooseMethodGroup.setText(bundle.getString("labels.chooseMethodGroup"));
            labelChooseMethod.setText(bundle.getString("labels.chooseMethod"));
            lblEps.setText(bundle.getString("labels.epsilon"));
            lblH.setText(bundle.getString("labels.epsilon"));
            menuBar.initComponentI18n(bundle);
            calcMethodsPane.initComponentsI18n(bundle);
            gaussMethodsPane.initComponentsI18n(bundle);
            interpolationPane.initComponentsI18n(bundle);
            layeredPane_Krylov.initComponentsI18n(bundle);
            reverseMatrixPane.initComponentsI18n(bundle);

        } catch (Exception e) {
        }
    }

    public void save() {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setCurrentDirectory(new File("."));
            jFileChooser.showSaveDialog(jFileChooser);
            PrintWriter pw;
            pw = new PrintWriter(jFileChooser.getSelectedFile().getAbsolutePath() + ".txt");
            String s = var + " ";
            pw.println(s);
            s = "";
            switch (var) {
                case 7:
                case 8:
                case 11:
                case 12:
                case 13:
                    calcMethodsPane.SaveData(var, pw);
                    break;
                case 5:
                    reverseMatrixPane.saveData(pw);
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 6:
                    gaussMethodsPane.saveData(pw);
                    break;
                case 9:
                    layeredPane_Krylov.saveData(pw);
                    break;
                case 10:
                    interpolationPane.saveData(s, pw);
                    break;
                default:
                    break;
            }
            pw.close();
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
        }
    }

    class MenuActionListener implements ActionListener {
        private int i;
        private int j;

        public MenuActionListener(int methodGroupIndex, int methodIndex) {
            this.i = methodGroupIndex;
            this.j = methodIndex;
        }

        public void actionPerformed(ActionEvent e) {
            VisibleDispetcher.SetFunctionVisible(Methods.this,MethodNames.methodsNumbers[i][j]);
        }
    }
}
