/*
 * Created by JFormDesigner on Sun Mar 13 00:49:25 PST 2016
 */

package UI;

import Model.AttributeType;
import Model.CharacterStats;
import Model.DisplayableItem;
import Util.Logger;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

/**
 * @author Brandon Dong
 */
public class ItemInfoPart extends JPanel {

    private BuildPlanner planner;

    public ItemInfoPart(BuildPlanner planner) {
        this.planner = planner;
        initComponents();
        initDisplay();
    }

    private void initDisplay() {
        refreshDisplay();
    }

    public void refreshItemSelected() {
        refreshLabelValidate();
        refreshDisplay();
    }

    public void refreshLabelValidate() {
        CharacterStats stats = planner.getModel().getSelected().combinedStats();
        DisplayableItem item = planner.getPreferences().getSelected();
        String labelText;
        if (item == null) {
            labelText = "No item selected";
        } else if (stats.hasRequiredAttributes(planner.getPreferences().getSelected().getRequirements())) {
            labelText = "All attribute requirements are met";
        } else {
            labelText = "Missing attribute requirements";
        }
        labelValidate.setText(labelText);
    }

    private void refreshDisplay() {
        if (planner.getPreferences().getSelected() != null) {
            try {
                textPane1.setStyledDocument(planner.getPreferences().getSelected().displayItem());
            } catch (BadLocationException e) {
                Logger.addError("Could not show selected item", e);
            }
        } else {
            textPane1.setStyledDocument(new DefaultStyledDocument());
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Brandon Dong
        panel1 = new JPanel();
        labelValidate = new JLabel();
        scrollPane1 = new JScrollPane();
        textPane1 = new JTextPane();

        //======== this ========
        setBorder(new CompoundBorder(
            new TitledBorder(null, "Item Information", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                new Font("Tahoma", Font.BOLD, 11)),
            new EmptyBorder(5, 5, 5, 5)));
        setPreferredSize(new Dimension(28, 200));

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

        //======== panel1 ========
        {
            panel1.setBorder(new CompoundBorder(
                new TitledBorder(""),
                new EmptyBorder(5, 5, 5, 5)));
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

            //---- labelValidate ----
            labelValidate.setText("No item selected");
            panel1.add(labelValidate, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== scrollPane1 ========
        {

            //---- textPane1 ----
            textPane1.setEditable(false);
            scrollPane1.setViewportView(textPane1);
        }
        add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Brandon Dong
    private JPanel panel1;
    private JLabel labelValidate;
    private JScrollPane scrollPane1;
    private JTextPane textPane1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
