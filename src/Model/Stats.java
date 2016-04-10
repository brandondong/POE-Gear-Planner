package Model;

import Util.Predicate;

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
     * @param type the {@link ResistType} of interest
     * @return the additional resistance of that type
     */
    public int getAdditionalResistValue(ResistType type) {
        return getResistValue(type, false);
    }

    /**
     *
     * @param type the {@link ResistType} of interest
     * @return the maximum resistance of that type
     */
    public int getMaximumResistValue(ResistType type) {
        return getResistValue(type, true);
    }

    private int getResistValue(final ResistType type, final boolean isMaximum) {
        return getValueWithCondition(new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return (input.contains(type.toString()) || input.contains("all")) && input.contains("Resistance")
                        && !input.contains("Minions") && !input.contains("Penetrates")
                        && input.contains("maximum") == isMaximum;
            }
        });
    }

    /**
     *
     * @param type the {@link AttributeType} of interest
     * @return the flat addition value of that type
     */
    public int getAdditionalAttributeValue(final AttributeType type) {
        return getValueWithCondition(new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return (input.contains(type.toString()) || input.contains("all Attributes"))
                        && input.contains("to");
            }
        });
    }

    /**
     *
     * @param type the {@link AttributeType} of interest
     * @return the percentage addition value of that type
     */
    public int getPercentAttributeValue(final AttributeType type) {
        return getValueWithCondition(new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input.contains(type.toString()) && input.contains("increased");
            }
        });
    }

    /**
     *
     * @return the total value of flat added life
     */
    public int getFlatLifeValue() {
        return getValueWithCondition(new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input.contains("to maximum Life");
            }
        });
    }

    /**
     *
     * @return the total value of percent increased life
     */
    public int getPercentLifeValue() {
        return getValueWithCondition(new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input.contains("increased maximum Life");
            }
        });
    }

    private int getValueWithCondition(Predicate<String> cond) {
        int value = 0;
        for (Stat stat : this) {
            if (cond.apply(stat.getId())) {
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
    public int calculateAttributeValue(AttributeType type) {
        return ((int) (getAdditionalAttributeValue(type) * (1 + ((double) getPercentAttributeValue(type)) / 100)));
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
     * @param description a description in the format of what you would see on the Passive Skill Tree
     */
    public void addStat(String description) {
        addStat(new Stat(description));
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
        if (builder.length() > 0) {
            return builder.substring(0, builder.length() - 1);
        }
        return "No Passive Skill Tree stats allocated";
    }
}
