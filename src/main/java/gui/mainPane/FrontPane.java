package gui.mainPane;

import gui.resources.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * Created by razor on 15.06.2017.
 */
public class FrontPane extends JLayeredPane {

    ResourceBundle bundle;
    private JLabel labelChooseMethodGroup;
    private JLabel labelChooseMethod;
    protected final JButton PUSH;
    protected JComboBox comboBoxMethod;
    protected final JComboBox comboBoxMethodGroup;

    public FrontPane(ResourceBundle bundle) {
        this.bundle = bundle;
        setDoubleBuffered(true);
        setOpaque(true);
        setPreferredSize(new Dimension(200, 300));
        setLayout(null);

        PUSH = new JButton("");
        PUSH.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("button/move.jpg")));
        PUSH.setPressedIcon(new ImageIcon(getClass().getClassLoader().getResource("button/press.jpg")));
        PUSH.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
        PUSH.setBorder(null);
        PUSH.setIcon(new ImageIcon(getClass().getClassLoader().getResource("button/main.jpg")));
        PUSH.setBounds(173, 300, 155, 121);
        add(PUSH);

        comboBoxMethod = new JComboBox();// выбор метода
        comboBoxMethod.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                PUSH.setEnabled(true);
            }
        });
        comboBoxMethod.setToolTipText("");
        comboBoxMethod.setModel(new DefaultComboBoxModel(getComboBoxModel(0)));
        comboBoxMethod.setSelectedIndex(0);
        comboBoxMethod.setBounds(11, 123, 477, 32);
        add(comboBoxMethod);

        comboBoxMethodGroup = new JComboBox();// выбор группы методов

        String[] model = new String[Constants.methodGroupsNames.length];
        for (int i = 0; i < model.length; i++) {
            model[i] = bundle.getString(Constants.methodGroupsNames[i]);
        }
        comboBoxMethodGroup.setModel(new DefaultComboBoxModel(model));
        comboBoxMethodGroup.setSelectedIndex(0);
        comboBoxMethodGroup.setBounds(11, 56, 477, 32);
        add(comboBoxMethodGroup);
        comboBoxMethodGroup.setToolTipText("");
        comboBoxMethodGroup.addItemListener(new ItemListener() {// считывание выбранной
            // группыметодов
            public void itemStateChanged(ItemEvent arg0) {
                int selectedIndex = comboBoxMethodGroup.getSelectedIndex();
                comboBoxMethod.setModel(new DefaultComboBoxModel(getComboBoxModel(selectedIndex)));
            }
        });

//        PUSH.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent arg0) {
//                int indexGroup = comboBoxMethodGroup.getSelectedIndex();// индекс выбранной группы методов
//                int indexMethod = comboBoxMethod.getSelectedIndex();// индекс выбранного метода
//                setVisible(indexGroup, indexMethod);
//            }
//        });


        labelChooseMethodGroup = new JLabel(bundle.getString("labels.chooseMethodGroup"));
        labelChooseMethodGroup.setBounds(21, 30, 201, 14);
        add(labelChooseMethodGroup);

        labelChooseMethod = new JLabel(
                bundle.getString("labels.chooseMethod"));
        labelChooseMethod.setBounds(21, 100, 155, 20);
        add(labelChooseMethod);
    }

    public String[] getComboBoxModel(int index) {
        int methodsNumber = Constants.methodsNumbers[index].length;
        String[] model = new String[methodsNumber];
        for (int j = 0; j < methodsNumber; j++) {
            model[j] = bundle.getString(Constants.methodNames[Constants.methodsNumbers[index][j]]);
        }
        return model;
    }

    public void initComponentsI18n(ResourceBundle bundle) {
        this.bundle = bundle;
        labelChooseMethodGroup.setText(bundle.getString("labels.chooseMethodGroup"));
        labelChooseMethod.setText(bundle.getString("labels.chooseMethod"));
    }
}
