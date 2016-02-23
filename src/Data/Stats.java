package Data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Brandon on 2016-02-20.
 *
 * Represents the stats of an item, character, node etc.
 */
public class Stats implements Iterable<Stat> {

    private Map<String, Stat> stats;

    /**
     * Initializes all the stats to be 0
     */
    public Stats() {
        stats = new HashMap<>();
    }

    /**
     *
     * @param id the unique identifier of the stat of interest
     * @return the value of the stat on this item
     */
    public double getStatValue(String id) {
        Stat stat = stats.get(id);
        if (stat != null) {
            return stat.getValue();
        }
        return 0;
    }

    /**
     *
     * @param type the {@link ResistType} of interest
     * @return the additional resistance of that type
     */
    public double getAdditionalResistValue(ResistType type) {
        return getResistValue(type, false);
    }

    /**
     *
     * @param type the {@link ResistType} of interest
     * @return the maximum resistance of that type
     */
    public double getMaximumResistValue(ResistType type) {
        return getResistValue(type, true);
    }

    private double getResistValue(ResistType type, boolean isMaximum) {
        double value = 0;
        for (Stat stat : this) {
            String id = stat.getId();
            if ((id.contains(type.toString()) || id.contains("all")) && id.contains("Resistance")
                    && !id.contains("Minions") && !id.contains("Penetrates") && id.contains("maximum") == isMaximum) {
                value += stat.getValue();
            }
        }
        return value;
    }

    /**
     *
     * @param type the {@link AttributeType} of interest
     * @return the flat addition value of that type
     */
    public double getAdditionalAttributeValue(AttributeType type) {
        double value = 0;
        for (Stat stat : this) {
            String id = stat.getId();
            if ((id.contains(type.toString()) || id.contains("all Attributes"))
                    && id.contains("to")) {
                value += stat.getValue();
            }
        }
        return value;
    }

    /**
     *
     * @param type the {@link AttributeType} of interest
     * @return the percentage addition value of that type
     */
    public double getPercentAttributeValue(AttributeType type) {
        double value = 0;
        for (Stat stat : this) {
            String id = stat.getId();
            if (id.contains(type.toString()) && id.contains("increased")) {
                value += stat.getValue();
            }
        }
        return value;
    }

    /**
     *
     * @param type the {@link AttributeType} of interest
     * @return the total attribute value of that type
     */
    public double calculateAttributeValue(AttributeType type) {
        return ((int) (getAdditionalAttributeValue(type) * (1 + getPercentAttributeValue(type) / 100)));
    }

    /**
     * Adds another set of stats onto the current set
     *
     * @param other the stats to be added
     */
    public void addStats(Stats other) {
        for (Stat stat : other) {
            addStat(stat);
        }
    }

    /**
     *
     * @param id the unique identifier of the stat
     * @param value the value of the stat to be added
     */
    public void addStat(String id, int value) {
        addStat(new Stat(id, value));
    }

    /**
     * Adds a single stat onto the current set
     *
     * @param other the stat to be added
     */
    public void addStat(Stat other) {
        String id = other.getId();
        stats.put(id, other.addStat(stats.get(id)));
    }

    @Override
    public Iterator<Stat> iterator() {
        return stats.values().iterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Stat stat : this) {
            builder.append(stat.toString());
            builder.append("\n");
        }
        return builder.substring(0, builder.length() - 1);
    }
}
