package gui.mainPane;

import gui.resources.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by razor on 15.06.2017.
 */
public class Menu extends JMenuBar {

    private ResourceBundle bundle;

    private JMenu menuFile;
    protected JMenuItem menuOpen;
    protected JMenuItem menuSave;
    private JMenuItem menuClose;
    private JMenu menu_methods;
    private JMenu[] methodsGroups;
    protected JMenuItem[] methods;
    private JMenu menuLanguage;
    private JMenu menu_report;
    protected JMenuItem menuSaveReport;
    protected JMenuItem menuShowReport;
    protected JMenu menu_about;
    protected JMenu menu_help;
    protected JMenuItem menuRus;
    protected JMenuItem menuEng;

    public Menu(MainFrame main,ResourceBundle bundle) {
        this.bundle = bundle;
//        JMenuBar menuBar = new JMenuBar();

        menuFile = new JMenu(bundle.getString("menu.file"));
        add(menuFile);

        menuOpen = new JMenuItem(bundle.getString("menu.open"));
        menuFile.add(menuOpen);

        menuSave = new JMenuItem(bundle.getString("menu.save"));
        menuFile.add(menuSave);

        menuClose = new JMenuItem(bundle.getString("menu.close"));
        menuFile.add(menuClose);

        menuLanguage = new JMenu(bundle.getString("menu.language"));
        add(menuLanguage);

        menuRus = new JMenuItem(bundle.getString("menu.language.russian"));
        menuLanguage.add(menuRus);

        menuEng = new JMenuItem(bundle.getString("menu.language.english"));
        menuLanguage.add(menuEng);

        menu_methods = new JMenu(bundle.getString("menu.methods"));
        add(menu_methods);

        methodsGroups = new JMenu[Constants.methodGroupsNames.length];

        methods = new JMenuItem[12];
        String methodName;
        int k = 0;
        for (int i = 0; i < methodsGroups.length; i++) {
            methodsGroups[i] = new JMenu(bundle.getString(Constants.methodGroupsNames[i]));
            menu_methods.add(methodsGroups[i]);
            for (int j = 0; j < Constants.methodsNumbers[i].length; j++) {
                methodName = Constants.methodNames[Constants.methodsNumbers[i][j]];
                methods[k] = new JMenuItem(bundle.getString(methodName));
                methodsGroups[i].add(methods[k]);
                k++;
            }
        }

        menu_report = new JMenu(bundle.getString("menu.report"));
        add(menu_report);

        menuSaveReport = new JMenuItem(bundle.getString("menu.saveReport"));

        menu_report.add(menuSaveReport);

        menuShowReport = new JMenuItem(bundle.getString("menu.showReport"));
        menuShowReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ReportFrame r = new ReportFrame();
                r.Rep(getReportHtml());
                r.show();
            }
        });
        menu_report.add(menuShowReport);


        menu_about = new JMenu(bundle.getString("menu.about"));

        add(menu_about);

        menu_help = new JMenu(bundle.getString("menu.help"));

        add(menu_help);

    }

    public void saveReport(String fileName, String shortName, int var) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName + ".html");
            out.write(GetReportStringByte(shortName + ".html", var));
            BufferedImage src = ImageIO.read(new FileInputStream("button/top1.jpg"));
            String imagePath = fileName.substring(0, (fileName.length() - shortName.length()));
            ImageIO.write(src, "JPG", new FileOutputStream(imagePath + "top1.jpg"));
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
        return Constants.HTML_START
                + MainFrame.methodSolution
                + Constants.HTML_END;
    }

    public String GetReportString(String shortName, int var) {
        String result;
        if (var == 10) {
            result = (Constants.HTML_START + Constants.HTML_TOP
                    + MainFrame.methodSolution
                    + "<br><br> "
                    + bundle.getString("graphics.functionGraph")
                    + ":<br><img src="
                    + shortName + ".png>"
                    + Constants.HTML_END);
        } else {
            result = (Constants.HTML_START + Constants.HTML_TOP + MainFrame.methodSolution + Constants.HTML_END);
        }
        return result;
    }

    public byte[] GetReportStringByte(String shortName, int var) {
        try {
            return GetReportString(shortName, var).getBytes("utf-8");

        } catch (Exception e) {
        }
        return null;
    }


    public void initComponentI18n(ResourceBundle bundle) {
        this.bundle = bundle;
        menuFile.setText(bundle.getString("menu.file"));
        menuOpen.setText(bundle.getString("menu.open"));
        menuSave.setText(bundle.getString("menu.save"));
        menuClose.setText(bundle.getString("menu.close"));
        menuLanguage.setText(bundle.getString("menu.language"));
        menu_methods.setText(bundle.getString("menu.methods"));
        menu_report.setText(bundle.getString("menu.report"));
        menuSaveReport.setText(bundle.getString("menu.saveReport"));
        menuShowReport.setText(bundle.getString("menu.showReport"));
        menu_about.setText(bundle.getString("menu.about"));
        menu_help.setText(bundle.getString("menu.help"));

        int k = 0;
        String methodName;
        for (int i = 0; i < methodsGroups.length; i++) {
            methodsGroups[i].setText(bundle.getString(Constants.methodGroupsNames[i]));
            for (int j = 0; j < Constants.methodsNumbers[i].length; j++) {
                methodName = Constants.methodNames[Constants.methodsNumbers[i][j]];
                methods[k].setText(bundle.getString(methodName));
                k++;
            }
        }
    }
}
