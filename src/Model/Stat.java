package Model;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Brandon on 2016-02-20.
 *
 * An immutable representation of a basic stat with a unique identifier and value
 */
public class Stat implements Serializable {

    private static final Pattern VALUE_PATTERN = Pattern.compile("[\\d\\.]+");

    private final String id;

    private final double value;

    private final boolean isNumeric;

    /**
     * Tries to parse the stat description into a formatted id and value
     *
     * @param description a description in the format of what you would see on the Passive Skill Tree
     */
    public Stat(String description) {
        if (description.matches(".*\\d+.*")) {
            id = parseId(description);
            value = parseValue(description);
            isNumeric = true;
        } else {
            id = description;
            value = 0;
            isNumeric = false;
        }
    }

    /**
     *
     * @param id a formatted unique identifier for the stat type (e.g. "%.1f%% increased Armour")
     * @param value the value of the stat
     */
    private Stat(String id, double value) {
        this.id = id;
        this.value = value;
        isNumeric = true;
    }

    /**
     * Adds a stat if both share the same unique identifier
     *
     * @param other the stat to be added
     * @return a stat representing the two stats combined in magnitude or the original if they cannot be added
     */
    public Stat addStat(Stat other) {
        if (other != null && id.equals(other.getId()) && isNumeric) {
            return new Stat(id, value + other.getValue());
        }
        return this;
    }

    /**
     *
     * @return a description in the format of what you would see on the Passive Skill Tree
     */
    public String getDescription() {
        if (!isNumeric) {
            return id;
        } else if (value == (int) value) {
            return String.format(id.replace(".1f", "d"), (int) value);
        }
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
    public double getValue() {
        return value;
    }

    /**
     *
     * @return the {@link StatType} based on the stat description
     */
    public StatType getType() {
        if (id.contains("Minions") || id.contains("Zombies") || id.contains("Spectres") || id.contains("Skeletons")) {
            return StatType.MINION;
        } else if (id.contains("Aura") || id.contains("Reserved")) {
            return StatType.AURA;
        } else if (id.contains("Curse")) {
            return StatType.CURSE;
        } else if (id.contains("Mana")) {
            return StatType.MANA;
        } else if (id.contains("Life") || id.contains("Shield") || id.contains("Resistance")
                || id.contains("Armour") || id.contains("Evasion")
                || id.contains("Block") || id.contains("Damage from Critical") || id.contains("taken")) {
            return StatType.DEFENCE;
        } else if (id.contains("Speed") || id.contains("Damage") || id.contains("Area") || id.contains("Critical")) {
            return StatType.OFFENCE;
        }
        return StatType.MISCELLANEOUS;
    }

    private String parseId(String description) {
        return description.replaceFirst("%", "%%").replaceFirst("[\\d\\.]+", "%.1f");
    }

    private double parseValue(String description) {
        Matcher m = VALUE_PATTERN.matcher(description);
        if (m.find()) {
            return Double.valueOf(m.group());
        }
        return 0;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
