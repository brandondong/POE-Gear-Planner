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
    public int getStatValue(String id) {
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
    public int getAdditionalResistValue(ResistType type) {
        return getStatValue(type.getAdditionalId());
    }

    /**
     *
     * @param type the {@link ResistType} of interest
     * @return the maximum resistance of that type
     */
    public int getMaximumResistValue(ResistType type) {
        return getStatValue(type.getMaxId());
    }

    /**
     *
     * @param type the {@link AttributeType} of interest
     * @return the flat addition value of that type
     */
    public int getAdditionalAttributeValue(AttributeType type) {
        return getStatValue(type.getAdditionalId());
    }

    /**
     *
     * @param type the {@link AttributeType} of interest
     * @return the percentage addition value of that type
     */
    public int getPercentAttributeValue(AttributeType type) {
        return getStatValue(type.getPercentId());
    }

    /**
     *
     * @param type the {@link AttributeType} of interest
     * @return the total attribute value of that type
     */
    public int calculateAttributeValue(AttributeType type) {
        return (int) (getAdditionalAttributeValue(type) * (1 + ((double) getPercentAttributeValue(type)) / 100));
    }

    /**
     *
     * @param type the {@link AttributeType} of interest
     * @return the required value of that attribute type
     */
    public int getRequiredAttributeValue(AttributeType type) {
        return getStatValue(type.getRequirementId());
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
}
