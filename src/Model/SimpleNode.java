package Model;

import java.io.Serializable;

/**
 * Created by Brandon on 2016-02-22.
 *
 * Represents a jewel socket or a node with no stats on the Passive Skill Tree
 */
public class SimpleNode implements Serializable {

    private int id;

    public SimpleNode(int id) {
        this.id = id;

    }

    public int getId() {
        return id;
    }
}
