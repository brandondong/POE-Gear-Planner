package Data;

/**
 * Created by Brandon on 2016-02-20.
 *
 * Represents the attributes of a character
 */
public class CharacterAttributes extends Attributes {

    /**
     *
     * @param other the attributes which the character is checked to have
     * @return <code>true</code> if the character satisfies the attribute requirements
     */
    public boolean hasRequiredAttributes(Attributes other) {
        for (AttributeType type : AttributeType.values()) {
            if (!hasRequiredAttribute(type, other.getAttributeValue(type))) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param type the {@link AttributeType} to be checked
     * @param value the value of the specified attribute type
     * @return <code>true</code> if the character has a greater or equal amount of that attribute type
     */
    public boolean hasRequiredAttribute(AttributeType type, int value) {
        return getAttributeValue(type) >= value;
    }
}
