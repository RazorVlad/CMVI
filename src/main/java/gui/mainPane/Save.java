package gui.mainPane;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by razor on 15.06.2017.
 */
public class Save {
    public static void save(MainFrame main, int var) {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setCurrentDirectory(new File("."));
            jFileChooser.showSaveDialog(jFileChooser);
            PrintWriter pw;
            pw = new PrintWriter(jFileChooser.getSelectedFile().getAbsolutePath() + ".txt");
            String s = var + " ";
            pw.println(s);
            s = "";
            System.out.println("зашли сюда, var="+var);
            switch (var) {
                case 7:
                case 8:
                case 11:
                case 12:
                case 13:
                    main.calcMethodsPane.SaveData(var, pw);
                    break;
                case 5:
                    main.reverseMatrixPane.saveData(pw);
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                    main.gaussMethodsPane.saveData(pw);
                    break;
                case 9:
                    main.layeredPane_Krylov.saveData(pw);
                    break;
                case 10:
                    main.interpolationPane.saveData(s, pw);
                    break;
                default:
                    break;
            }
            pw.close();
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
        }
    }
}
