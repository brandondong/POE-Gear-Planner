package Data;

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
        for (AttributeType type : AttributeType.values()) {
            requirements.put(type, 0);
        }
    }

    /**
     *
     * @param type the {@link AttributeType} to be returned
     * @return the value of the attribute requirement
     */
    public int getAttributeRequirement(AttributeType type) {
        return requirements.get(type);
    }

    /**
     *
     * @param type the {@link AttributeType} to be returned
     * @param value the value of the attribute requirement
     */
    public void setAttributeRequirement(AttributeType type, int value) {
        requirements.put(type, value);
    }

}
