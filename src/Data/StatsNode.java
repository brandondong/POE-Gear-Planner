package Data;

/**
 * Created by Brandon on 2016-02-21.
 *
 * Represents a node with stats on the Passive Skill Tree
 */
public class StatsNode extends SimpleNode {

    private Stats stats;

    /**
     *
     * @param id the unique identifier of the node as specified in skilltree.json
     * @param stats the {@link Stats} that this node gives
     */
    public StatsNode(int id, Stats stats) {
        super(id);
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
    }
}
