package Data;

/**
 * Created by Brandon on 2016-02-20.
 *
 * An immutable representation of a basic stat with a unique identifier and value
 */
public class Stat {

    private String id;

    private int value;

    /**
     *
     * @param description a description in the format of what you would see on the Passive Skill Tree
     */
    public Stat(String description) {
        id = parseId(description);
        value = parseValue(description);
    }

    /**
     *
     * @param id a formatted unique identifier for the stat type (e.g. "%d%% increased Armour")
     * @param value the value of the stat
     */
    public Stat(String id, int value) {
        this.id = id;
        this.value = value;
    }

    /**
     * Adds a stat if both share the same unique identifier
     *
     * @param other the stat to be added
     * @return a stat representing the two stats combined in magnitude or the original if they cannot be added
     */
    public Stat addStat(Stat other) {
        if (other != null && id.equals(other.getId())) {
            return new Stat(id, value + other.getValue());
        }
        return this;
    }

    /**
     *
     * @return a description in the format of what you would see on the Passive Skill Tree
     */
    public String getDescription() {
        return String.format(id, value);
    }

    /**
     *
     * @return the unique identifier
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return the value of this stat
     */
    public int getValue() {
        return value;
    }

    private String parseId(String description) {
        return description.replaceAll("%", "%%").replaceFirst("[\\d]+", "%d");
    }

    private int parseValue(String description) {
        return Integer.valueOf(description.replaceAll("[\\D]", ""));
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
