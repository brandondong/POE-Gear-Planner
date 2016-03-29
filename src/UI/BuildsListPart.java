/*
 * Created by JFormDesigner on Sat Mar 05 12:23:08 PST 2016
 */

package UI;

import Model.*;
import Model.Character;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Brandon Dong
 */
public class BuildsListPart extends JPanel {

    private BuildsModel model;

    private BuildPlanner planner;

    public BuildsListPart(BuildsModel model, BuildPlanner planner) {
        this.model = model;
        this.planner = planner;
        initComponents();
        initListItems();
        initButtonCreate();
        initButtonEdit();
        initButtonRemove();
    }

    private void initButtonRemove() {
        refreshButtonRemove();
        buttonRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Character> selection = listItems.getSelectedValuesList();
                model.removeCharacters(selection);
                planner.refreshBuildSelected();
            }
        });
    }

    private void refreshButtonRemove() {
        buttonRemove.setEnabled(listItems.getSelectedValue() != null &&
                listItems.getSelectedValuesList().size() < model.numCharacters());
    }

    private void refreshButtonEdit() {
        buttonEdit.setEnabled(listItems.getSelectedValue() != null && listItems.getSelectedValue() != model.getSelected());
    }

    private void initButtonEdit() {
        refreshButtonEdit();
        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setSelected((Character) listItems.getSelectedValue());
                planner.refreshBuildSelected();
            }
        });
    }

    private void initButtonCreate() {
        buttonCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addCharacter();
                planner.refreshBuildSelected();
            }
        });
    }

    public void refreshListItems() {
        DefaultListModel<Character> listModel = new DefaultListModel<>();
        for (Character character : model) {
            listModel.addElement(character);
        }
        listItems.setModel(listModel);
    }

    private void initListItems() {
        refreshListItems();
        listItems.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                refreshButtonEdit();
                refreshButtonRemove();
            }
        });
        listItems.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                String text = ((Character) value).getDisplayString();
                if (value == model.getSelected()) {
                    text = String.format("* %s", text);
                }
                label.setText(text);
                return label;
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Brandon Dong
        scrollPane1 = new JScrollPane();
        listItems = new JList();
        buttonEdit = new JButton();
        buttonRemove = new JButton();
        buttonCreate = new JButton();
        hSpacer1 = new JPanel(null);
        label1 = new JLabel();

        //======== this ========
        setBorder(new CompoundBorder(
            new TitledBorder(null, "Builds", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 12)),
            new EmptyBorder(5, 5, 5, 5)));
        setPreferredSize(new Dimension(300, 191));
        setMinimumSize(new Dimension(242, 164));

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 0.0, 1.0E-4};

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(listItems);
        }
        add(scrollPane1, new GridBagConstraints(0, 0, 4, 3, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- buttonEdit ----
        buttonEdit.setText("Edit");
        add(buttonEdit, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- buttonRemove ----
        buttonRemove.setText("Remove");
        add(buttonRemove, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- buttonCreate ----
        buttonCreate.setText("Create new build");
        add(buttonCreate, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        add(hSpacer1, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- label1 ----
        label1.setText("* Currently selected");
        add(label1, new GridBagConstraints(2, 3, 3, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Brandon Dong
    private JScrollPane scrollPane1;
    private JList listItems;
    private JButton buttonEdit;
    private JButton buttonRemove;
    private JButton buttonCreate;
    private JPanel hSpacer1;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
