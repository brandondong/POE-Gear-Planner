package Data;

/**
 * Created by Brandon on 2016-02-20.
 */
public class Gem {

    private Stats stats;

    private String name;

    private AttributeType type;

    private int level;

    /**
     *
     * @param stats the {@link Stats} which include the gem's attribute requirements
     * @param name the name of the gem
     * @param type the {@link AttributeType} of the gem
     * @param level the level of the gem
     */
    public Gem(Stats stats, String name, AttributeType type, int level) {
        this.stats = stats;
        this.name = name;
        this.type = type;
        this.level = level;
    }
}
