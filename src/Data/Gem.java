package Data;

/**
 * Created by Brandon on 2016-02-20.
 *
 * Represents a skill gem
 */
public class Gem {

    private Requirements requirements;

    private String name;

    private AttributeType type;

    private int level;

    /**
     *
     * @param requirements the {@link Requirements} of the gem
     * @param name the name of the gem
     * @param type the {@link AttributeType} of the gem
     * @param level the level of the gem
     */
    public Gem(Requirements requirements, String name, AttributeType type, int level) {
        this.requirements = requirements;
        this.name = name;
        this.type = type;
        this.level = level;
    }

    public Requirements getRequirements() {
        return requirements;
    }
}
