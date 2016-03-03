package UI;

import Util.CommonUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

/**
 * Created by Brandon on 2016-03-03.
 *
 * A part containing useful links to redirect to
 */
public class UsefulLinks extends JFrame {
    private JLabel usefulLinksLabel;
    private JLabel officialLabel;
    private JPanel rootPanel;

    public UsefulLinks() {
        super("Testing");
        setContentPane(rootPanel);
        pack();
        setVisible(true);
    }

    private void createUIComponents() {
        officialLabel = new JLabel("Official Website");
        initLinkLabel(officialLabel);
        officialLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CommonUtil.openWebpage("https://www.pathofexile.com");
            }
        });
    }

    private void initLinkLabel(JLabel label) {
        Font font = label.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        label.setFont(font.deriveFont(attributes));
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        new UsefulLinks();
    }
}
