package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brandon on 2016-02-20.
 *
 * Represents an item or gem's attribute requirements
 */
public class Requirements {

    private Map<AttributeType, Integer> requirements;

    /**
     * Initializes the attribute requirements to be 0
     */
    public Requirements() {
        requirements = new HashMap<>();
    }

    /**
     *
     * @param type the {@link AttributeType} to be returned
     * @return the value of the attribute requirement
     */
    public int getAttributeRequirement(AttributeType type) {
        Integer value = requirements.get(type);
        if (value != null) {
            return value;
        }
        return 0;
    }

    /**
     *
     * @param type the {@link AttributeType} to be returned
     * @param value the value of the attribute requirement
     */
    public void setAttributeRequirement(AttributeType type, int value) {
        requirements.put(type, value);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (AttributeType type : requirements.keySet()) {
            builder.append("\n");
            builder.append(String.format("Requires %d %s", requirements.get(type), type));
        }
        if (builder.length() > 0) {
            return builder.substring(1);
        }
        return "No attribute requirements";
    }

}
