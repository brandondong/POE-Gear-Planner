package Data;

/**
 * Created by Brandon on 2016-02-21.
 *
 * Represents a node on the Passive Skill Tree
 */
public class Node {

    private int id;

    private Stats stats;

    /**
     *
     * @param id the unique identifier of the node as specified in skilltree.json
     * @param stats the {@link Stats} that this node gives
     */
    public Node(int id, Stats stats) {
        this.id = id;
        this.stats = stats;
    }

    public int getId()  {
        return id;
    }

    public Stats getStats() {
        return stats;
    }
}
