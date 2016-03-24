/*
 * Created by JFormDesigner on Sat Mar 05 18:51:51 PST 2016
 */

package UI;

import Model.BuildsModel;
import Model.DisplayableItem;
import Model.Gem;
import Util.GameConstants;
import Util.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

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
        refreshButtonAdd((String) comboGemNames.getSelectedItem());
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gem gem = new Gem((String) comboGemNames.getSelectedItem(), (Integer) spinnerLevel.getValue());
                model.getSelected().addGem(gem);
                planner.refreshItemsChanged();
            }
        });
    }

    private void refreshButtonAdd(String text) {
        buttonAdd.setEnabled(GameConstants.GEM_DATABASE.containsKey(text));
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
        listGems.setCellRenderer(new GemCellRenderer());
    }

    public void refreshListGems() {
        DefaultListModel<Gem> gemModel = new DefaultListModel<>();
        for (Gem gem : model.getSelected().getGems()) {
            gemModel.addElement(gem);
        }
        listGems.setModel(gemModel);
    }

    private void initComboGemNames() {
        comboGemNames.setModel(getDefaultModel());
        final JTextField textField = (JTextField) comboGemNames.getEditor().getEditorComponent();
        textField.setDocument(new SearchDocument());
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Object item = comboGemNames.getSelectedItem();
                    if (item != null && (comboGemNames.isPopupVisible() || buttonAdd.isEnabled())) {
                        textField.setText((String) item);
                        textField.setSelectionStart(textField.getText().length());
                        refreshButtonAdd((String) item);
                        buttonAdd.doClick();
                    }
                }
            }
        });
    }

    private class SearchDocument extends PlainDocument {

        private SearchDocument() {
            Object selection = comboGemNames.getSelectedItem();
            if (selection != null) {
                try {
                    setText(selection.toString(), null);
                } catch (BadLocationException e) {
                    Logger.addError("Could not initialize gem combo box", e);
                }
            }
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            super.insertString(offs, str, a);
            String gemName = lookupItem(getText(0, getLength()));
            if (gemName != null) {
                comboGemNames.setSelectedItem(gemName);
                setText(gemName, a);
                JTextField editor = (JTextField) comboGemNames.getEditor().getEditorComponent();
                editor.setSelectionStart(offs + str.length());
                editor.setSelectionEnd(getLength());
                comboGemNames.showPopup();
            } else {
                comboGemNames.hidePopup();
            }
            refreshButtonAdd(getText(0, getLength()));
        }

        @Override
        public void remove(int offs, int len) throws BadLocationException {
            super.remove(offs, len);
            if (getLength() == 0) {
                comboGemNames.hidePopup();
            } else if (lookupItem(getText(0, getLength())) != null) {
                comboGemNames.showPopup();
            }
            refreshButtonAdd(getText(0, getLength()));
        }

        private void setText(String str, AttributeSet a) throws BadLocationException {
            super.remove(0, getLength());
            super.insertString(0, str, a);
        }

        private String lookupItem(String str) {
            if (str.length() > 0) {
                for (int i = 0; i < comboGemNames.getModel().getSize(); i++) {
                    String gem = (String) comboGemNames.getItemAt(i);
                    if (gem.toLowerCase().startsWith(str.toLowerCase())) {
                        return gem;
                    }
                }
            }
            return null;
        }
    }

    private ComboBoxModel getDefaultModel() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<String> keys = new ArrayList<>(GameConstants.GEM_DATABASE.keySet());
        Collections.sort(keys);
        for (String data : keys) {
            model.addElement(data);
        }
        return model;
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
    private class GemCellRenderer extends DefaultListCellRenderer {
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
