/*
 * Created by JFormDesigner on Fri Apr 29 23:29:45 PDT 2016
 */

package UI;

import Model.BuildsModel;
import Model.Item;
import Util.AccountNameToCharacters;
import Util.CharacterNameToItemData;
import Util.CommonUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;

/**
 * @author Brandon Dong
 */
public class AddItemDialog extends JDialog {

    private static AddItemDialog INSTANCE;

    private static final String ROOT_NODE = "Characters";

    private String oldName = "";

    private BuildPlanner planner;

    private BuildsModel model;

    /**
     * Creates an add item dialog, using the previous settings if a dialog existed previously
     * @param owner the {@link Frame} to be displayed in
     */
    public static void create(BuildPlanner owner) {
        if (INSTANCE == null) {
            INSTANCE = new AddItemDialog(owner);
        }
        INSTANCE.setVisible(true);
    }

    private AddItemDialog(BuildPlanner planner) {
        super(planner);
        this.planner = planner;
        model = planner.getModel();
        initComponents();
        initButtonLoad();
        initTextField();
        initTree();
        initButtonAdd();
        initList();
        initButtonRemove();
        initButtonRemoveAll();
        initDialogButtons();
    }

    private void initDialogButtons() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ListModel<Item> listModel = listAddedItems.getModel();
                for (int i = 0; i < listModel.getSize(); i++) {
                    model.getSelected().addItem(listModel.getElementAt(i));
                }
                removeAllItems();
                planner.refreshItemsChanged();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    private void initButtonRemoveAll() {
        refreshbuttonRemoveAll();
        buttonRemoveAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAllItems();
            }
        });
    }

    private void removeAllItems() {
        ((DefaultListModel<Item>) listAddedItems.getModel()).removeAllElements();
    }

    private void refreshbuttonRemoveAll() {
        buttonRemoveAll.setEnabled(listAddedItems.getModel().getSize() > 0);
    }

    private void initButtonRemove() {
        refreshButtonRemove();
        buttonRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel<Item> model = (DefaultListModel <Item>) listAddedItems.getModel();
                for (Item item : listAddedItems.getSelectedValuesList()) {
                    model.removeElement(item);
                }
            }
        });
    }

    private void refreshButtonRemove() {
        buttonRemove.setEnabled(!listAddedItems.getSelectedValuesList().isEmpty());
    }

    private void initList() {
        listAddedItems.setModel(new DefaultListModel<Item>());
        listAddedItems.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                refreshButtonRemove();
            }
        });
        listAddedItems.getModel().addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                refreshbuttonRemoveAll();
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                refreshbuttonRemoveAll();
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                refreshbuttonRemoveAll();
            }
        });
    }

    private void initButtonAdd() {
        refreshButtonAdd();
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<Item> items = getSelectedItems();
                DefaultListModel<Item> model = (DefaultListModel<Item>) listAddedItems.getModel();
                for (Item item : items) {
                    model.addElement(item);
                }
            }
        });
    }

    private Set<Item> getSelectedItems() {
        Set<Item> items = new HashSet<>();
        for (TreePath path : treeItems.getSelectionPaths()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            getSelectedRecursive(node, items);
        }
        return items;
    }

    private void getSelectedRecursive(DefaultMutableTreeNode node, Set<Item> items) {
        if (node.getUserObject() instanceof  Item) {
            items.add((Item) node.getUserObject());
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            getSelectedRecursive((DefaultMutableTreeNode) node.getChildAt(i), items);
        }
    }


    private void initTree() {
        setRootNode();
        treeItems.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                refreshButtonAdd();
            }
        });
    }

    private void refreshButtonAdd() {
        buttonAdd.setEnabled(treeItems.getSelectionCount() > 0);
    }

    private void setRootNode() {
        DefaultTreeModel model = (DefaultTreeModel) treeItems.getModel();
        model.setRoot(new DefaultMutableTreeNode(ROOT_NODE));
    }

    private void refreshTree() {
        new SwingWorker<Void, DefaultMutableTreeNode>() {

            private boolean rootNodeSet = false;

            @Override
            protected Void doInBackground() throws Exception {
                if (!oldName.isEmpty()) {
                    for (String name : AccountNameToCharacters.getCharacters(oldName)) {
                        publish(createCharacterNode(name));
                    }
                }
                return null;
            }

            @Override
            protected void process(List<DefaultMutableTreeNode> nodes) {
                if (!rootNodeSet) {
                    setRootNode();
                    rootNodeSet = true;
                }
                DefaultTreeModel model = (DefaultTreeModel) treeItems.getModel();
                for (DefaultMutableTreeNode node : nodes) {
                    ((DefaultMutableTreeNode) model.getRoot()).add(node);
                }
                model.reload();
            }
        }.execute();
    }

    private DefaultMutableTreeNode createCharacterNode(String name) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
        for (Item item : CharacterNameToItemData.getEquipmentFromCharacter(oldName, name).getItems()) {
            node.add(new DefaultMutableTreeNode(item));
        }
        return node;
    }
    private void initTextField() {
        textFieldAccount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        refreshButtonLoad();
                    }
                });
            }
        });
    }

    private void initButtonLoad() {
        refreshButtonLoad();
        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!oldName.equals(textFieldAccount.getText())) {
                    oldName = textFieldAccount.getText();
                    refreshTree();
                }
            }
        });
    }

    private void refreshButtonLoad() {
        buttonLoad.setEnabled(!textFieldAccount.getText().isEmpty());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Brandon Dong
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        textFieldAccount = new JTextField();
        buttonLoad = new JButton();
        panel1 = new JPanel();
        scrollPaneItems = new JScrollPane();
        treeItems = new JTree();
        buttonAdd = new JButton();
        scrollPane2 = new JScrollPane();
        listAddedItems = new JList();
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
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};

                //---- label1 ----
                label1.setText("Account Name:");
                contentPanel.add(label1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(textFieldAccount, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- buttonLoad ----
                buttonLoad.setText("Load Items");
                contentPanel.add(buttonLoad, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
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

                    //======== scrollPaneItems ========
                    {
                        scrollPaneItems.setViewportView(treeItems);
                    }
                    panel1.add(scrollPaneItems, new GridBagConstraints(0, 0, 1, 4, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                    //---- buttonAdd ----
                    buttonAdd.setText("Add >>");
                    panel1.add(buttonAdd, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //======== scrollPane2 ========
                    {
                        scrollPane2.setViewportView(listAddedItems);
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
                contentPanel.add(panel1, new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0,
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
    private JTextField textFieldAccount;
    private JButton buttonLoad;
    private JPanel panel1;
    private JScrollPane scrollPaneItems;
    private JTree treeItems;
    private JButton buttonAdd;
    private JScrollPane scrollPane2;
    private JList<Item> listAddedItems;
    private JButton buttonRemove;
    private JButton buttonRemoveAll;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
