package gui;

import graphbuilder.math.Expression;
import gui.methodsPanes.*;
import gui.resources.FinalTexts;
import gui.resources.MethodNames;
import gui.resources.SwitchModule;
import keypoint.PngEncoder;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;

public class Methods extends JFrame {

    private int indexGroup, indexMethod, var;
    public static double Eps = 0.001;
    public static String methodSolution = "";
    private JTextField EpsTextField;

    private JPanel contentPane;

    final KrylovPane layeredPane_Krylov = new KrylovPane();
    Layer1 layeredPane_1 = new Layer1(var);
    final JLayeredPane layeredPane = new JLayeredPane();
    final ReverseMatrixPane reverseMatrixPane = new ReverseMatrixPane();
    final Layer3 layeredPane_3 = new Layer3(var);
    final InterpolationPane interpolationPane = new InterpolationPane();
    final JScrollPane scrollPane_3 = new JScrollPane();
    final JSpinner spinner = new JSpinner();
    final JLabel lblEps = new JLabel("\u041F\u043E\u0433\u0440\u0435\u0448\u043D\u043E\u0441\u0442\u044C E =");
    final JLabel lblH = new JLabel(
            "\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E \u0440\u0430\u0437\u0431\u0438\u0435\u043D\u0438\u0439 ");

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


    public void saveToFile(String fileName) throws IOException {
        PngEncoder enc = new PngEncoder(buffer);
        enc.setCompressionLevel(9);
        FileOutputStream fw = new FileOutputStream(fileName);
        fw.write(enc.pngEncode());
        fw.close();
    }

    Image buffer;
    private JTable table;
    private JTable table1;
    int index1 = 0;
    math.Lagrange l;

    Expression k;
    int index = 0;
    private final String HTML_START = "<html><head><title></title></head><body bgcolor=#C5D7FB><table border=0 align=center><tr><td width=800>";
    private final String HTML_TOP = "<img src=Top1.jpg border=0 width=800 height=200 alt=Факультет Информатики и управления align=top>&nbsp;";
    private final String HTML_END = "</td></tr></table></body></html>";


    private JFileChooser dlg = new JFileChooser(".");

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


    public void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuFile = new JMenu("Файл");
        menuBar.add(menuFile);

        JMenuItem menuOpen = new JMenuItem("\u041E\u0442\u043A\u0440\u044B\u0442\u044C");
        menuFile.add(menuOpen);

        JMenuItem menuSave = new JMenuItem("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
        menuFile.add(menuSave);
        menuSave.addActionListener(new ActionListener() { // сохранение
            public void actionPerformed(ActionEvent arg0) {

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
                            layeredPane_3.SaveData(var, pw);
                            break;
                        case 5:
                            reverseMatrixPane.saveData(pw);
                            break;
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 6:
                            layeredPane_1.saveData(pw);
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
        });

        JMenuItem menuClose = new JMenuItem("Закрыть");
        menuFile.add(menuClose);
//        JPanel panel = new JPanel();
        JMenu menu_metods = new JMenu("Методы");
        menuBar.add(menu_metods);

        JMenu[] methodsGroups = new JMenu[MethodNames.methodGroupsNames.length];

        JMenuItem[] methods = new JMenuItem[12];
        int k = 0;
        for (int i = 0; i < methodsGroups.length; i++) {
            methodsGroups[i] = new JMenu(MethodNames.methodGroupsNames[i]);
            menu_metods.add(methodsGroups[i]);
            for (int j = 0; j < MethodNames.methodsNumbers[i].length; j++) {
                methods[k] = new JMenuItem(MethodNames.methodNames[MethodNames.methodsNumbers[i][j]]);
                methodsGroups[i].add(methods[k]);
                methods[k].addActionListener(new MenuActionListener(i, j));
                k++;
            }
        }


        JMenu menu_report = new JMenu("Отчет");
        menuBar.add(menu_report);
        JMenuItem menuSaveReport = new JMenuItem(
                "\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C \u043E\u0442\u0447\u0451\u0442");
        menuSaveReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (dlg.showSaveDialog(Methods.this) != JFileChooser.APPROVE_OPTION)
                    return;

                String fileName = dlg.getSelectedFile().getAbsolutePath() + ".html";
                String shortName = dlg.getSelectedFile().getName() + ".html";
                saveReport(fileName, shortName);
                /*
                 * String from = ".../button/Top1.jpg"; String to =
				 * dlg.getSelectedFile().getParent()+"Top1.jpg";
				 *
				 * File From = new File(from); if (!From.exists()) {
				 * System.out.println(From + " does not exist!");
				 *
				 * }
				 *
				 * File To = new File(to); if (!To.delete()){
				 * System.out.println("You can't move!");
				 *
				 * }
				 *
				 * From.renameTo(To);
				 */


            }
        });

        JMenuItem menuShowReport = new JMenuItem(
                "\u041F\u0440\u043E\u0441\u043C\u043E\u0442\u0440 \u043E\u0442\u0447\u0451\u0442\u0430");
        menuShowReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Report r = new Report();
                r.Rep(getReportHtml());
                r.show();
            }
        });
        menu_report.add(menuShowReport);
        menu_report.add(menuSaveReport);

        JMenu menu_about = new JMenu("О программе");
        menu_about.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, FinalTexts.aboutText, FinalTexts.aboutLabel,
                        JOptionPane.INFORMATION_MESSAGE);

            }
        });
        menuBar.add(menu_about);

        JMenu menu_help = new JMenu("Помощь");
        menu_help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        menu_help.addMouseListener(new MouseAdapter() {
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
        menuBar.add(menu_help);


        menuOpen.addActionListener(new ActionListener() { // out file
            public void actionPerformed(ActionEvent arg0) {
                menuOpen();
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
        setTitle("\u0427\u0438\u0441\u043B\u0435\u043D\u043D\u044B\u0435 \u043C\u0435\u0442\u043E\u0434\u044B");
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                Methods.class.getResource("/javax/swing/plaf/metal/icons/Inform.gif")));
        setBounds(100, 100, 529, 570);

        createMenuBar();

        layeredPane_3.setVisible(false);
        interpolationPane.setVisible(false);
        JPanel panel = new JPanel();

        layeredPane.setBounds(6, 6, 499, 470);
        panel.add(layeredPane);

        interpolationPane.setBounds(6, 6, 499, 470);
        panel.add(interpolationPane);

        layeredPane_Krylov.setVisible(false);
        layeredPane_Krylov.setBounds(6, 6, 500, 470);
        panel.add(layeredPane_Krylov);


        layeredPane_3.setBounds(6, 6, 500, 470);
        panel.add(layeredPane_3);

        contentPane = new

                JPanel();
        contentPane.setBorder(new

                EmptyBorder(5, 5, 5, 5));

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

        layeredPane_1.setVisible(false);

        layeredPane_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {

            }
        });

        layeredPane_1.setBounds(6, 6, 499, 470);
        panel.add(layeredPane_1);

        lblH.setVisible(false);
        lblH.setBounds(107, 12, 139, 16);
        contentPane.add(lblH);

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

        comboBoxMethod.setModel(new

                DefaultComboBoxModel(
                new String[]{
                        "\u041C\u0435\u0442\u043E\u0434\u044B \u043B\u0438\u043D\u0435\u0439\u043D\u043E\u0439 \u0430\u043B\u0433\u0435\u0431\u0440\u044B",
                        "\u041C\u0435\u0442\u043E\u0434\u044B \u0440\u0435\u0448\u0435\u043D\u0438\u044F \u043D\u0435\u043B\u0438\u043D\u0435\u0439\u043D\u044B\u0445 \u0443\u0440\u0430\u0432\u043D\u0435\u043D\u0438\u0439",
                        "\u041D\u0430\u0445\u043E\u0436\u0434\u0435\u043D\u0438\u0435 \u0441\u043E\u0431\u0441\u0442\u0432\u0435\u043D\u043D\u044B\u0445 \u0437\u043D\u0430\u0447\u0435\u043D\u0438\u0439 \u0438 \u0441\u043E\u0431\u0441\u0442\u0432\u0435\u043D\u043D\u044B\u0445 \u0432\u0435\u043A\u0442\u043E\u0440\u0430 \u043C\u0430\u0442\u0440\u0438\u0446\u044B",
                        "\u0418\u043D\u0442\u0435\u0440\u043F\u043E\u043B\u0438\u0440\u043E\u0432\u0430\u043D\u0438\u0435 \u0444\u0443\u043D\u043A\u0446\u0438\u0439",
                        "\u0427\u0438\u0441\u043B\u0435\u043D\u043D\u043E\u0435 \u0438\u043D\u0442\u0435\u0433\u0440\u0438\u0440\u043E\u0432\u0430\u043D\u0438\u0435",
                        "\u041A\u0440\u0430\u0435\u0432\u044B\u0435 \u0437\u0430\u0434\u0430\u0447\u0438 \u0434\u043B\u044F \u043E\u0431\u044B\u043A\u043D\u043E\u0432\u0435\u043D\u043D\u044B\u0445 \u0434\u0438\u0444\u0444\u0435\u0440\u0435\u043D\u0446\u0438\u0430\u043B\u044C\u043D\u044B\u0445 \u0443\u0440\u0430\u0432\u043D\u0435\u043D\u0438\u0439"
                }));
        comboBoxMethod.setSelectedIndex(0);
        comboBoxMethod.setBounds(11, 56, 477, 32);
        layeredPane.add(comboBoxMethod);
        comboBoxMethod.setToolTipText("");
        final JButton PUSH = new JButton("");
        PUSH.setRolloverIcon(new

                ImageIcon("button/move.jpg"));
        PUSH.setPressedIcon(new

                ImageIcon("button/press.jpg"));
        PUSH.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
        PUSH.setBorder(null);
        PUSH.setIcon(new

                ImageIcon("button/main.jpg"));

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
                indexGroup = comboBoxMethod.getSelectedIndex();// индекс выбранной группы
                // методов
                indexMethod = comboBoxGroup.getSelectedIndex();// индекс выбранного
                // метода

                SetFunctionVisible(SwitchModule.getMethodNumber(indexGroup, indexMethod));

                layeredPane.setVisible(false);
                // var=14;
            }
        });
        /*
         * btnNewButton.addItemListener(new ItemListener() { public void
		 * itemStateChanged(ItemEvent arg0) { } });
		 */
        PUSH.setBounds(173, 300, 155, 121);
        layeredPane.add(PUSH);

        JLabel label = new JLabel(
                "\u0412\u044B\u0431\u0435\u0440\u0438\u0442\u0435 \u043A\u0430\u0442\u0435\u0433\u043E\u0440\u0438\u044E \u043C\u0435\u0442\u043E\u0434\u043E\u0432");
        label.setBounds(21, 30, 201, 14);
        layeredPane.add(label);

        JLabel label_1 = new JLabel(
                "\u0412\u044B\u0431\u0435\u0440\u0438\u0442\u0435 \u043C\u0435\u0442\u043E\u0434");
        label_1.setBounds(21, 100, 155, 20);
        layeredPane.add(label_1);

    }

    static void renderSplashFrame(Graphics2D g, int frame) {
        final String[] comps = {"foo", "bar", "baz"};
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120, 140, 200, 40);
        g.setPaintMode();
        g.setColor(Color.BLACK);
        g.drawString("Loading " + comps[(frame / 5) % 3] + "...", 120, 150);
    }

    public void SetFunctionVisible(int var) {
        this.var = var;
        setTitle(MethodNames.methodNames[var]);
        EpsTextField.setVisible(false);
        layeredPane_1.setVisible(false);
        reverseMatrixPane.setVisible(false);
        layeredPane.setVisible(false);
        interpolationPane.setVisible(false);
        layeredPane_Krylov.setVisible(false);
        EpsTextField.setVisible(false);
        lblEps.setVisible(false);
        lblH.setVisible(false);
        switch (var) {

            case 2:
            case 3:
                EpsTextField.setText("0.0001");
                EpsTextField.setVisible(true);
                lblEps.setVisible(true);
            case 1:
            case 4:

                layeredPane_1.SetVisible(var);
                layeredPane_1.setVisible(true);
                break;
            case 5:
                reverseMatrixPane.setVisible(true);
                break;
            case 7:
            case 8:
            case 11:
            case 12:
                EpsTextField.setText("0.0001");
                EpsTextField.setVisible(true);
                lblEps.setVisible(true);
                lblH.setVisible(false);
                layeredPane_3.setVisible(true);
                layeredPane_3.setVisible(this.var);
                break;

            case 9://setTitle("Метод Крылова");
                layeredPane_Krylov.setVisible(true);
                break;
            case 10://setTitle("Интерполяция Лагранжа");
                interpolationPane.setVisible(true);
                break;

            case 13://setTitle("Метод прогонки");
                lblH.setVisible(true);
                EpsTextField.setText("10");
                EpsTextField.setVisible(true);
                layeredPane_3.setVisible(true);
                layeredPane_3.setVisible(this.var);
                break;
            case 14:
        }
    }


//    public math.Simpson getSimp() {
//        return simp;
//    }

    public void menuOpen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Чтение данных из файла");
        chooser.setCurrentDirectory(new File("."));
        chooser.showOpenDialog(chooser);
        FileReader fr;
        try {
            fr = new FileReader(chooser.getSelectedFile().getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            StringTokenizer st = new StringTokenizer(s);
            SetFunctionVisible(Integer.parseInt(st.nextToken()));
            s = br.readLine();
            st = new StringTokenizer(s);

            switch (var) {
                case 7:
                case 8:
                case 11:
                case 12:
                case 13:
                    layeredPane_3.Open(var, br, st);
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
                    layeredPane_1.openData(st, br);
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
            model[j] = MethodNames.methodNames[MethodNames.methodsNumbers[index][j]];
        }

        return model;
    }

    public void saveReport(String fileName, String shortName) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            out.write(GetReportString(shortName));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public String getReportHtml() {
        return HTML_START + methodSolution + HTML_END;
    }

    public byte[] GetReportString(String shortName) {
        byte[] result = null;
        try {
            if (var == 10) {
                result = (HTML_START + HTML_TOP + methodSolution
                        + "<br><br> График функции:<br><img src=" + shortName + ".png>" + HTML_END)
                        .getBytes("utf-8");
            } else {
                result = (HTML_START + HTML_TOP + methodSolution + HTML_END).getBytes("utf-8");
            }
        } catch (Exception e) {

        }
        return result;
    }

    public byte[] reportString(String s) {
        byte[] result = null;
        try {
            result = (HTML_START + HTML_TOP + s + HTML_END).getBytes("utf-8");
        } catch (Exception e) {

        }
        return result;
    }

    class MenuActionListener implements ActionListener {
        private int i;
        private int j;
        public MenuActionListener(int methodGroupIndex, int methodIndex) {
            this.i = methodGroupIndex;
            this.j = methodIndex;
        }
        public void actionPerformed(ActionEvent e) {
            SetFunctionVisible(MethodNames.methodsNumbers[i][j]);
        }
    }
}
