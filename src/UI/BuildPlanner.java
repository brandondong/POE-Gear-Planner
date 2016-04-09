/*
 * Created by JFormDesigner on Mon Mar 07 01:05:20 PST 2016
 */

package UI;

import Model.*;
import Model.Character;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author Brandon Dong
 */
public class BuildPlanner extends JFrame {

    private BuildsModel model;

    private Map<Character, SkillTreePreferences> preferences;

    public BuildPlanner(BuildsModel model) {
        this.model = model;
        preferences = new HashMap<>();
        initComponents();
        initBuildName();
    }

    private void initBuildName() {
        refreshBuildName();
        textFieldBuildName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshModelBuildName();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshModelBuildName();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshModelBuildName();
            }

            private void refreshModelBuildName() {
                String text = textFieldBuildName.getText();
                if (text.length() > 0) {
                    model.getSelected().setName(textFieldBuildName.getText());
                } else {
                    model.getSelected().setName(Character.NEW_CHARACTER_NAME);
                }
                buildsListPart1.refreshListItems();
            }
        });
    }

    /**
     * Refreshes the UI when a new build is selected
     */
    public void refreshBuildSelected() {
        refreshBuildName();
        buildsListPart1.refreshListItems();
        gemListPart1.refreshListGems();
        skillTreeFormPart1.refreshSettings();
        itemInfoPart1.refreshItemSelected();
    }

    public void refreshStatsChanged() {
        buildsListPart1.refreshListItems();
        itemInfoPart1.refreshLabelValidate();
        skillTreeFormPart1.refreshTextPaneInfo();
    }

    public void refreshItemSelected() {
        itemInfoPart1.refreshItemSelected();
    }

    public void refreshItemsChanged() {
        gemListPart1.refreshListGems();
    }

    private void refreshBuildName() {
        textFieldBuildName.setText(model.getSelected().getName());
    }

    public SkillTreePreferences getPreferences() {
        Character selected = model.getSelected();
        if (preferences.get(selected) == null) {
            preferences.put(selected, new SkillTreePreferences());
        }
        return preferences.get(selected);
    }

    public BuildsModel getModel() {
        return model;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Brandon Dong
        skillTreeFormPart1 = new SkillTreeFormPart(this);
        panel1 = new JPanel();
        labelBuildName = new JLabel();
        hSpacer1 = new JPanel(null);
        textFieldBuildName = new JTextField();
        label2 = new JLabel();
        gearListPart1 = new GearListPart();
        gemListPart1 = new GemListPart(this);
        itemInfoPart1 = new ItemInfoPart(this);
        buildsListPart1 = new BuildsListPart(this);

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Path of Exile Gear BuildPlanner");
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0E-4};
        contentPane.add(skillTreeFormPart1, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 10), 0, 0));

        //======== panel1 ========
        {

            // JFormDesigner evaluation mark
            panel1.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0, 1.0E-4};

            //---- labelBuildName ----
            labelBuildName.setText("Build name:");
            labelBuildName.setFont(new Font("Tahoma", Font.BOLD, 11));
            panel1.add(labelBuildName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
            panel1.add(hSpacer1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
            panel1.add(textFieldBuildName, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- label2 ----
            label2.setBorder(new CompoundBorder(
                new TitledBorder(""),
                new EmptyBorder(5, 5, 5, 5)));
            label2.setText("All item requirements are met.");
            panel1.add(label2, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
            panel1.add(gearListPart1, new GridBagConstraints(0, 2, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
            panel1.add(gemListPart1, new GridBagConstraints(0, 3, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        contentPane.add(panel1, new GridBagConstraints(1, 0, 1, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 10), 0, 0));
        contentPane.add(itemInfoPart1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 10, 0), 0, 0));
        contentPane.add(buildsListPart1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Brandon Dong
    private SkillTreeFormPart skillTreeFormPart1;
    private JPanel panel1;
    private JLabel labelBuildName;
    private JPanel hSpacer1;
    private JTextField textFieldBuildName;
    private JLabel label2;
    private GearListPart gearListPart1;
    private GemListPart gemListPart1;
    private ItemInfoPart itemInfoPart1;

    private BuildsListPart buildsListPart1;

    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //for testing
        new BuildPlanner(new BuildsModel()).setVisible(true);
    }
}
