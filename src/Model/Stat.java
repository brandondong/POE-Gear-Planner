package Model;

/**
 * Created by Brandon on 2016-02-20.
 *
 * An immutable representation of a basic stat with a unique identifier and value
 */
public class Stat {

    private String id;

    private double value;

    private StatType type;

    /**
     *
     * @param description a description in the format of what you would see on the Passive Skill Tree
     */
    public Stat(String description) {
        id = parseId(description);
        value = parseValue(description);
        classifyType();
    }

    /**
     *
     * @param id a formatted unique identifier for the stat type (e.g. "%.1f%% increased Armour")
     * @param value the value of the stat
     */
    public Stat(String id, double value) {
        this.id = id;
        this.value = value;
        classifyType();
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
        if (value == 0) {
            return id;
        } else if (value == (int) value) {
            return String.format(id.replace(".1f", "d"), (int) value);
        }
        return String.format(id, value);
    }

    private void classifyType() {
        if (id.contains("Minions") || id.contains("Zombies") || id.contains("Spectres") || id.contains("Skeletons")) {
            type = StatType.MINION;
        } else if (id.contains("Aura") || id.contains("Reserved")) {
            type = StatType.AURA;
        } else if (id.contains("Curse")) {
            type = StatType.CURSE;
        } else if (id.contains("Mana")) {
            type = StatType.MANA;
        } else if (id.contains("Life") || id.contains("Shield") || id.contains("Resistance")
                || id.contains("Armour") || id.contains("Evasion")
                || id.contains("Block") || id.contains("Damage from Critical") || id.contains("taken")) {
            type = StatType.DEFENCE;
        } else if (id.contains("Speed") || id.contains("Damage") || id.contains("Area") || id.contains("Critical")) {
            type = StatType.OFFENCE;
        } else {
            type = StatType.MISCELLANEOUS;
        }
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
    public double getValue() {
        return value;
    }

    public StatType getType() {
        return type;
    }

    private String parseId(String description) {
        return description.replaceAll("%", "%%").replaceFirst("[\\d\\.]+", "%.1f");
    }

    private double parseValue(String description) {
        if (description.matches(".*\\d+.*")) {
            return Double.valueOf(description.replaceAll("[^\\d\\.]", ""));
        }
        return 0;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
