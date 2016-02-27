package Model;

/**
 * Created by Brandon on 2016-02-20.
 *
 * Represents the character attributes
 */
public enum AttributeType {
    INTELLIGENCE, DEXTERITY, STRENGTH;

    /**
     *
     * @return the unique identifier representing a flat addition of this type
     */
    public String getId() {
        return String.format("+%%.1f to %s", this);
    }

    @Override
    public String toString() {
        String name = super.toString();
        return name.substring(0, 1) + name.substring(1).toLowerCase();
    }
}
