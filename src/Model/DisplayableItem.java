package Model;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 * Created by Brandon on 2016-03-15.
 *
 * Represents an item that can be displayed
 */
public interface DisplayableItem {

    /**
     *
     * @return a styled document to be displayed representing this item
     */
    StyledDocument displayItem() throws BadLocationException;
}
