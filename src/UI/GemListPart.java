/*
 * Created by JFormDesigner on Sat Mar 05 18:51:51 PST 2016
 */

package UI;

import Util.GameConstants;
import Util.GemData;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Brandon Dong
 */
public class GemListPart extends JPanel {
    public GemListPart() {
        initComponents();
        initSpinnerLevel();
        initComboGemNames();
    }

    private void initComboGemNames() {
        for (String data : GameConstants.GEM_DATABASE.keySet()) {
            comboGemNames.addItem(data);
        }
    }

    private void initSpinnerLevel() {
        spinnerLevel.setValue(20);
        spinnerLevel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = (int) spinnerLevel.getValue();
                spinnerLevel.setValue(Math.max(1, value));
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Brandon Dong
        label1 = new JLabel();
        comboGemNames = new JComboBox();
        label2 = new JLabel();
        spinnerLevel = new JSpinner();
        button1 = new JButton();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        button2 = new JButton();

        //======== this ========
        setBorder(new CompoundBorder(
            new TitledBorder(null, "Gems", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 12)),
            new EmptyBorder(5, 5, 5, 5)));
        setPreferredSize(new Dimension(400, 300));

        // JFormDesigner evaluation mark
        setBorder(new javax.swing.border.CompoundBorder(
            new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Gem name:");
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- comboGemNames ----
        comboGemNames.setEditable(true);
        add(comboGemNames, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label2 ----
        label2.setText("Level:");
        add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- spinnerLevel ----
        spinnerLevel.setPreferredSize(new Dimension(46, 18));
        spinnerLevel.setMinimumSize(new Dimension(46, 20));
        add(spinnerLevel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- button1 ----
        button1.setText("Add");
        add(button1, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(list1);
        }
        add(scrollPane1, new GridBagConstraints(0, 2, 4, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- button2 ----
        button2.setText("Remove");
        add(button2, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Brandon Dong
    private JLabel label1;
    private JComboBox comboGemNames;
    private JLabel label2;
    private JSpinner spinnerLevel;
    private JButton button1;
    private JScrollPane scrollPane1;
    private JList list1;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
