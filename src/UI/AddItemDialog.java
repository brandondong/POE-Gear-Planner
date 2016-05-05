/*
 * Created by JFormDesigner on Fri Apr 29 23:29:45 PDT 2016
 */

package UI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Brandon Dong
 */
public class AddItemDialog extends JDialog {

    private static AddItemDialog INSTANCE;

    public static AddItemDialog instance(Frame owner) {
        if (INSTANCE == null) {
            INSTANCE = new AddItemDialog(owner);
        }
        return INSTANCE;
    }

    private AddItemDialog(Frame owner) {
        super(owner);
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Brandon Dong
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        button1 = new JButton();
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        buttonAdd = new JButton();
        scrollPane2 = new JScrollPane();
        list2 = new JList();
        buttonRemove = new JButton();
        buttonRemoveAll = new JButton();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setTitle("Add Items");
        setModal(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

            // JFormDesigner evaluation mark
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};

                //---- label1 ----
                label1.setText("Account Name:");
                contentPanel.add(label1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(textField1, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label2 ----
                label2.setText("Character Name:");
                contentPanel.add(label2, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(textField2, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- button1 ----
                button1.setText("Load Items");
                contentPanel.add(button1, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //======== panel1 ========
                {
                    panel1.setBorder(new CompoundBorder(
                        new TitledBorder(""),
                        new EmptyBorder(5, 5, 5, 5)));
                    panel1.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
                    ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
                    ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
                    ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

                    //======== scrollPane1 ========
                    {
                        scrollPane1.setViewportView(list1);
                    }
                    panel1.add(scrollPane1, new GridBagConstraints(0, 0, 1, 4, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                    //---- buttonAdd ----
                    buttonAdd.setText("Add >>");
                    panel1.add(buttonAdd, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //======== scrollPane2 ========
                    {
                        scrollPane2.setViewportView(list2);
                    }
                    panel1.add(scrollPane2, new GridBagConstraints(2, 0, 1, 4, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));

                    //---- buttonRemove ----
                    buttonRemove.setText("<< Remove");
                    panel1.add(buttonRemove, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //---- buttonRemoveAll ----
                    buttonRemoveAll.setText("Remove All");
                    panel1.add(buttonRemoveAll, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
                }
                contentPanel.add(panel1, new GridBagConstraints(0, 6, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Brandon Dong
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JTextField textField2;
    private JButton button1;
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JList list1;
    private JButton buttonAdd;
    private JScrollPane scrollPane2;
    private JList list2;
    private JButton buttonRemove;
    private JButton buttonRemoveAll;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
