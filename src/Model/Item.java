package Model;

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
        return String.format("%s\n%s\n%s", name, requirements, stats);
    }

    @Override
    public StyledDocument displayItem() {
        return null;
    }
}
