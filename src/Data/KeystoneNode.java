package Data;

/**
 * Created by Brandon on 2016-02-21.
 *
 * Represents a keystone node on the Passive Skill Tree
 */
public class KeystoneNode extends Node {

    private String name;

    private String description;

    /**
     *
     * @param id the unique identifier of the node as specified in skilltree.json
     * @param name the name of the node
     * @param description the keystone description
     */
    public KeystoneNode(int id, String name, String description) {
        super(id, new CharacterStats());
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLabel() {
        return String.format("%s: %s", name, description);
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
