package UI;

import Model.BuildsModel;
import Model.Difficulty;
import Util.Logger;
import Util.URLToSkillTreeData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
/*
 * Created by JFormDesigner on Fri Mar 04 01:34:48 PST 2016
 */

/**
 * @author Brandon Dong
 */
public class SkillTreeFormPart extends JPanel {

    private static final String EMPTY_URL = "Copy and paste your Path of Exile Skill Tree URL above";

    private static final String INVALID_URL = "Failed to load Passive Skill Tree URL";

    private static final String VALID_URL = "Passive Skill Tree URL loaded successfully";

    private BuildPlanner planner;

    private BuildsModel model;

	public SkillTreeFormPart(BuildPlanner planner) {
        this.planner = planner;
        model = planner.getModel();
		initComponents();
        initComboDifficulty();
        initSpinnerLevel();
        initButtonLoad();
        initTextField();
        initLabelValidate();
        initTextPaneInfo();
        initButtons();
	}

    private void initTextPaneInfo() {
        refreshTextPaneInfo();
    }

    private void refreshTextPaneInfo() {
        try {
            StyledDocument doc = model.getSelected().displayInfo(planner.getPreferences());
            if (doc != null) {
                textPaneInfo.setStyledDocument(doc);
            } else {
                textPaneInfo.setStyledDocument(new DefaultStyledDocument());
            }
        } catch (BadLocationException e) {
            Logger.addError("Error trying to display character information", e);
        }
    }

    public void refreshSettings() {
        refreshTextField();
        refreshLabelValidate();
        refreshTextPaneInfo();
        refreshSpinnerLevel();
        refreshComboDifficulty();
        refreshButtons();
    }

    private void initLabelValidate() {
        refreshLabelValidate();
    }

    private void refreshLabelValidate() {
        if (planner.getPreferences().getUrl().isEmpty()) {
            labelValidate.setText(EMPTY_URL);
        } else if (planner.getPreferences().isUrlLoaded()) {
            labelValidate.setText(VALID_URL);
        } else {
            labelValidate.setText(INVALID_URL);
        }
    }

    private void initTextField() {
        refreshTextField();
        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                refreshButtonLoad();
            }
        });
    }

    private void refreshTextField() {
        textField1.setText(planner.getPreferences().getUrl());
    }

    private void initButtonLoad() {
        refreshButtonLoad();
        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = textField1.getText();
                SkillTreePreferences preferences = planner.getPreferences();
                preferences.setUrl(url);
                preferences.setIsUrlLoaded(URLToSkillTreeData.decodeURL(url, model.getSelected()));
                refreshLabelValidate();
                refreshTextPaneInfo();
                planner.refreshBuildSelected();
            }
        });
    }

    private void refreshButtonLoad() {
        buttonLoad.setEnabled(!textField1.getText().isEmpty());
    }

    private void initSpinnerLevel() {
        refreshSpinnerLevel();
        spinnerLevel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = (int) spinnerLevel.getValue();
                if (value > 100) {
                    spinnerLevel.setValue(100);
                } else if (value < 1) {
                    spinnerLevel.setValue(1);
                }
                planner.getPreferences().setLevel((Integer) spinnerLevel.getValue());
            }
        });
    }

    private void refreshSpinnerLevel() {
        spinnerLevel.setValue(planner.getPreferences().getLevel());
    }

    private void initComboDifficulty() {
        for (Difficulty difficulty : Difficulty.values()) {
            comboDifficulty.addItem(difficulty);
        }
        refreshComboDifficulty();
        comboDifficulty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                planner.getPreferences().setDifficulty((Difficulty) comboDifficulty.getSelectedItem());
            }
        });
    }

    private void refreshComboDifficulty() {
        comboDifficulty.setSelectedItem(planner.getPreferences().getDifficulty());
    }

    private void refreshButtons() {
        if (planner.getPreferences().isWithGear()) {
            radioButton1.setSelected(true);
        } else {
            radioButton2.setSelected(true);
        }
    }

    private void initButtons() {
        refreshButtons();
        radioButton1.addActionListener(new ButtonActionListener());
        radioButton2.addActionListener(new ButtonActionListener());
    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            planner.getPreferences().setWithGear(radioButton1.isSelected());
        }
    }

    private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Brandon Dong
        textField1 = new JTextField();
        buttonLoad = new JButton();
        panel2 = new JPanel();
        labelValidate = new JLabel();
        scrollPane1 = new JScrollPane();
        textPaneInfo = new JTextPane();
        panel1 = new JPanel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        radioButton1 = new JRadioButton();
        radioButton2 = new JRadioButton();
        comboDifficulty = new JComboBox();
        hSpacer1 = new JPanel(null);
        spinnerLevel = new JSpinner();

        //======== this ========
        setBorder(new TitledBorder(null, "Passive Skill Tree", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.BOLD, 12)));
        setPreferredSize(new Dimension(352, 539));

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 0.0, 1.0E-4};
        add(textField1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- buttonLoad ----
        buttonLoad.setText("Load");
        add(buttonLoad, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== panel2 ========
        {
            panel2.setBorder(new CompoundBorder(
                new TitledBorder(""),
                new EmptyBorder(5, 5, 5, 5)));
            panel2.setLayout(new GridBagLayout());
            ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
            ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

            //---- labelValidate ----
            labelValidate.setText("Copy and paste your Path of Exile skill tree URL above");
            panel2.add(labelValidate, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel2, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== scrollPane1 ========
        {

            //---- textPaneInfo ----
            textPaneInfo.setEditable(false);
            scrollPane1.setViewportView(textPaneInfo);
        }
        add(scrollPane1, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== panel1 ========
        {
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- label2 ----
            label2.setText("Show stats:");
            panel1.add(label2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- label3 ----
            label3.setText("Choose difficulty:");
            panel1.add(label3, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- label4 ----
            label4.setText("Level:");
            panel1.add(label4, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- radioButton1 ----
            radioButton1.setText("Without gear");
            radioButton1.setSelected(true);
            panel1.add(radioButton1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- radioButton2 ----
            radioButton2.setText("With gear");
            panel1.add(radioButton2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
            panel1.add(comboDifficulty, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
            panel1.add(hSpacer1, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- spinnerLevel ----
            spinnerLevel.setPreferredSize(new Dimension(46, 18));
            panel1.add(spinnerLevel, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
        }
        add(panel1, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(radioButton1);
        buttonGroup1.add(radioButton2);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Brandon Dong
    private JTextField textField1;
    private JButton buttonLoad;
    private JPanel panel2;
    private JLabel labelValidate;
    private JScrollPane scrollPane1;
    private JTextPane textPaneInfo;
    private JPanel panel1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JComboBox<Difficulty> comboDifficulty;
    private JPanel hSpacer1;
    private JSpinner spinnerLevel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
