/*
 * Created by JFormDesigner on Sun Mar 06 15:12:14 PST 2016
 */

package UI;

import Model.BuildsModel;
import Model.Gem;
import Model.Item;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Brandon Dong
 */
public class GearListPart extends JPanel {

    private BuildPlanner planner;

    private BuildsModel model;

    public GearListPart(BuildPlanner planner) {
        this.planner = planner;
        this.model = planner.getModel();
        initComponents();
        initGearList();
        initButtonRemove();
        initButtonAdd();
    }

    private void initButtonAdd() {
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddItemDialog.create(planner);
            }
        });
    }

    private void initButtonRemove() {
        refreshButtonRemove();
        buttonRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getSelected().removeItems(listGear.getSelectedValuesList());
            }
        });
    }

    private void refreshButtonRemove() {
        buttonRemove.setEnabled(listGear.getSelectedValue() != null);
    }

    private void initGearList() {
        refreshGearList();
        listGear.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Item value = listGear.getSelectedValue();
                if (value != null) {
                    planner.getPreferences().setSelected(value);
                }
                refreshButtonRemove();
            }
        });
    }

    public void refreshGearList() {
        DefaultComboBoxModel<Item> cModel = new DefaultComboBoxModel<>();
        for (Item item : model.getSelected().getItems()) {
            cModel.addElement(item);
        }
        listGear.setModel(cModel);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Brandon Dong
        scrollPane1 = new JScrollPane();
        listGear = new JList();
        buttonRemove = new JButton();
        buttonAdd = new JButton();

        //======== this ========
        setBorder(new CompoundBorder(
            new TitledBorder(null, "Gear", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 12)),
            new EmptyBorder(5, 5, 5, 5)));
        setPreferredSize(new Dimension(400, 301));

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(listGear);
        }
        add(scrollPane1, new GridBagConstraints(0, 0, 2, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- buttonRemove ----
        buttonRemove.setText("Remove");
        add(buttonRemove, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- buttonAdd ----
        buttonAdd.setText("Add new item");
        add(buttonAdd, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Brandon Dong
    private JScrollPane scrollPane1;
    private JList<Item> listGear;
    private JButton buttonRemove;
    private JButton buttonAdd;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
