/*
 * Created by JFormDesigner on Sat Mar 05 18:51:51 PST 2016
 */

package UI;

import Model.BuildsModel;
import Model.DisplayableItem;
import Model.Gem;
import Util.GameConstants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Brandon Dong
 */
public class GemListPart extends JPanel {

    private BuildsModel model;

    private BuildPlanner planner;

    public GemListPart(BuildsModel model, BuildPlanner planner) {
        this.model = model;
        this.planner = planner;
        initComponents();
        initSpinnerLevel();
        initComboGemNames();
        initListGems();
        initButtonRemove();
        initButtonAdd();
    }

    private void initButtonAdd() {
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gem gem = new Gem((String) comboGemNames.getSelectedItem(), (Integer) spinnerLevel.getValue());
                model.getSelected().addGem(gem);
                planner.refreshItemsChanged();
            }
        });
    }

    private void initButtonRemove() {
        refreshButtonRemove();
        buttonRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getSelected().removeGems(listGems.getSelectedValuesList());
                planner.refreshItemsChanged();
            }
        });
    }

    private void refreshButtonRemove() {
        buttonRemove.setEnabled(listGems.getSelectedValue() != null);
    }

    private void initListGems() {
        refreshListGems();
        listGems.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Object value = listGems.getSelectedValue();
                if (value != null) {
                    model.setSelectedItem((DisplayableItem) value);
                    planner.refreshItemSelected();
                }
                refreshButtonRemove();
            }
        });
        listGems.setCellRenderer(new gemCellRenderer());
    }

    public void refreshListGems() {
        DefaultListModel<Gem> gemModel = new DefaultListModel<>();
        for (Gem gem : model.getSelected().getGems()) {
            gemModel.addElement(gem);
        }
        listGems.setModel(gemModel);
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

    private class gemCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component label = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (!isSelected) {
                label.setForeground(((Gem) value).getType().getColor());
            }
            return label;
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Brandon Dong
        label1 = new JLabel();
        comboGemNames = new JComboBox();
        label2 = new JLabel();
        spinnerLevel = new JSpinner();
        buttonAdd = new JButton();
        scrollPane1 = new JScrollPane();
        listGems = new JList();
        buttonRemove = new JButton();

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

        //---- buttonAdd ----
        buttonAdd.setText("Add");
        add(buttonAdd, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(listGems);
        }
        add(scrollPane1, new GridBagConstraints(0, 2, 4, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- buttonRemove ----
        buttonRemove.setText("Remove");
        add(buttonRemove, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
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
    private JButton buttonAdd;
    private JScrollPane scrollPane1;
    private JList listGems;

    private JButton buttonRemove;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
