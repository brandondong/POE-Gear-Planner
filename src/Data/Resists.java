package Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brandon on 2016-02-19.
 *
 * Represents the resistances of an item
 */
public class Resists {

    private Map<ResistType, Integer> resistances;

    private Map<ResistType, Integer> maxRes;

    /**
     * Initializes the resistances and the additional maximum resistances to be 0
     */
    public Resists() {
        resistances = new HashMap<>();
        maxRes = new HashMap<>();
    }

    /**
     *
     * @param type the {@link ResistType} to be returned
     * @return the base resistance of the specified type
     */
    public int getBaseResist(ResistType type) {
        Integer res = resistances.get(type);
        if (res != null) {
            return res;
        }
        return 0;
    }

    /**
     * Adds another set of resistances onto the current set
     *
     * @param resists the resistances to be added
     */
    public void addResists(Resists resists) {
        for (ResistType type : ResistType.values()) {
            addResist(type, resists.getBaseResist(type));
            addMaxResist(type, resists.getAdditionalMaxResist(type));
        }
    }

    /**
     *
     * @param type the {@link ResistType} to be added
     * @param value the value of the resistance to be added
     */
    public void addResist(ResistType type, int value) {
        resistances.put(type, value + getBaseResist(type));
    }

    /**
     *
     * @param type the {@link ResistType} to be added
     * @param value the value of the additional maximum resistance to be added
     */
    public void addMaxResist(ResistType type, int value) {
        maxRes.put(type, value + getAdditionalMaxResist(type));
    }

    /**
     *
     * @param type the {@link ResistType} to be returned
     * @return the additional maximum resistance of the specified type
     */
    public int getAdditionalMaxResist(ResistType type) {
        Integer max = maxRes.get(type);
        if (max != null) {
            return max;
        }
        return 0;
    }
}
