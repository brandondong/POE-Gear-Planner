package Model;

import Util.CommonUtil;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;

/**
 * Created by Brandon on 2016-02-20.
 *
 * Represents an item with stats and requirements
 */
public class Item implements DisplayableItem {

    private Requirements requirements;

    private Stats stats;

    private String name;

    public Item(Requirements requirements, Stats stats, String name) {
        this.requirements = requirements;
        this.stats = stats;
        this.name = name;
    }

    public Stats getStats() {
        return stats;
    }

    @Override
    public Requirements getRequirements() {
        return requirements;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public StyledDocument displayItem() throws BadLocationException {
        StyledDocument doc = new DefaultStyledDocument();
        doc.insertString(0, String.format("%s\n\n", name), CommonUtil.getLargeFont());
        doc.insertString(doc.getLength(), String.format("%s\n\n", requirements.toString()), CommonUtil.getRegularFont());
        doc.insertString(doc.getLength(), stats.toString(), CommonUtil.getRegularFont());
        return doc;
    }
}
