package Model;

import java.util.List;

/**
 * Created by Brandon on 2016-03-31.
 *
 * Represents an Ascendancy keystone on the Passive Skill Tree
 */
public class AscendancyNode extends SimpleNode {

    private String name;

    private List<String> description;

    public AscendancyNode(int id, String name, List<String> description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(name).append(":");
        for (String next : description) {
            s.append("\n").append(next);
        }
        return s.toString();
    }
}
