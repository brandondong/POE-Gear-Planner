package Model;

import javax.swing.text.StyledDocument;

/**
 * Created by Brandon on 2016-03-15.
 *
 * Represents an item that can be displayed
 */
public interface DisplayableItem {

    /**
     *
     * @param document the document of the text pane to be displayed on
     */
    void displayItem(StyledDocument document);
}
