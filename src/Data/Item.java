package Data;

/**
 * Created by Brandon on 2016-02-20.
 *
 * Represents an item with stats and requirements
 */
public class Item {

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

    public Requirements getRequirements() {
        return requirements;
    }
}
