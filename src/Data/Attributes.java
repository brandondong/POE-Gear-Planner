package Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brandon on 2016-02-20.
 *
 * Represents the attributes of an item
 */
public class Attributes {

    private Map<AttributeType, Integer> attributes;

    /**
     * Initializes the attributes to be 0
     */
    public Attributes() {
        attributes = new HashMap<>();
    }

    /**
     *
     * @param type the {@link AttributeType} to be returned
     * @return the value of the specified attribute type
     */
    public int getAttributeValue(AttributeType type) {
        Integer value = attributes.get(type);
        if (value != null) {
            return value;
        }
        return 0;
    }

    /**
     * Adds another set of attributes to the current set
     *
     * @param other the attributes to be added
     */
    public void addAttributes(Attributes other) {
        for (AttributeType type : AttributeType.values()) {
            addAttribute(type, other.getAttributeValue(type));
        }
    }

    /**
     *
     * @param type the {@link AttributeType} to be added
     * @param value the value of the specified attribute type
     */
    public void addAttribute(AttributeType type, int value) {
        attributes.put(type, value + getAttributeValue(type));
    }
}
