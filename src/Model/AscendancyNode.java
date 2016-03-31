package Model;

import java.util.List;

/**
 * Created by Brandon on 2016-03-31.
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
}
