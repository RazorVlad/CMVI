package gui.mainPane;

import gui.resources.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by razor on 15.06.2017.
 */
public class Load {
    public static void OpenFile(MainFrame main) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(main.bundle.getString("chooser.readData"));
        chooser.setCurrentDirectory(new File("."));
        chooser.showOpenDialog(chooser);
        FileReader fr;
        try {
            fr = new FileReader(chooser.getSelectedFile().getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            StringTokenizer st = new StringTokenizer(s);
            main.var = Integer.parseInt(st.nextToken());
            VisibleDispetcher.SetFunctionVisible(main, main.var);
            s = br.readLine();
            st = new StringTokenizer(s);
            switch (main.var) {
                case 7:
                case 8:
                case 11:
                case 12:
                case 13:
                    main.calcMethodsPane.Open(main.var, br, st);
                    break;
                case 10:
                    try {
                        main.interpolationPane.load(st);

                        br.close();
                    } catch (Exception e) {
                    }
                    break;
                case 5:
                    try {
                        main.reverseMatrixPane.load(st, br);
                        br.close();
                    } catch (Exception e) {
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                    main.gaussMethodsPane.openData(st, br);
                case 6:
                case 9:
                    main.layeredPane_Krylov.OpenData(st, br);
                default:
                    break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
