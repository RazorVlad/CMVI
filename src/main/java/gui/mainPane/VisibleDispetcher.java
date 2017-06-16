package gui.mainPane;

/**
 * Created by razor on 15.06.2017.
 */
public class VisibleDispetcher {
    public static void SetFunctionVisible(MainFrame main, int var) {
//            main.setTitle(bundle.getString(Constants.methodNames[var]));
        main.EpsTextField.setVisible(false);
        main.gaussMethodsPane.setVisible(false);
        main.reverseMatrixPane.setVisible(false);
        main.frontPane.setVisible(false);
        main.interpolationPane.setVisible(false);
        main.layeredPane_Krylov.setVisible(false);
        main.calcMethodsPane.setVisible(false);
        main.lblEps.setVisible(false);
        main.lblH.setVisible(false);
        switch (var) {

            case 2:
            case 3:
                main.EpsTextField.setText("0.0001");
                main.EpsTextField.setVisible(true);
                main.lblEps.setVisible(true);
            case 1:
            case 4:

                main.gaussMethodsPane.SetVisible(var);
                main.gaussMethodsPane.setVisible(true);
                break;
            case 5:
                main.reverseMatrixPane.setVisible(true);
                break;
            case 7:
            case 8:
            case 11:
            case 12:
                main.EpsTextField.setText("0.0001");
                main.EpsTextField.setVisible(true);
                main.lblEps.setVisible(true);
                main.calcMethodsPane.setVisible(true);
                main.calcMethodsPane.setVisible(var);
                break;

            case 9://setTitle("Метод Крылова");
                main.layeredPane_Krylov.setVisible(true);
                break;
            case 10://setTitle("Интерполяция Лагранжа");
                main.interpolationPane.setVisible(true);
                break;

            case 13://setTitle("Метод прогонки");
                main.lblH.setVisible(true);
                main.EpsTextField.setText("10");
                main.EpsTextField.setVisible(true);
                main.calcMethodsPane.setVisible(true);
                main.calcMethodsPane.setVisible(var);
                break;
            case 14:
        }
    }
}

