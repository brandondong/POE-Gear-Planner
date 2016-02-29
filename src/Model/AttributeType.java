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

    public GemType getType() {
        return GemType.valueOf(super.toString());
    }

    /**
     *
     * @param s the abbreviated attribute type in item info json
     * @return the matching {@link AttributeType} for the abbreviation
     * @throws IllegalArgumentException if abbreviation matches none
     */
    public static AttributeType getTypeFromAbbreviation(String s) throws IllegalArgumentException {
        if (s.equals("Str")) {
            return STRENGTH;
        } else if (s.equals("Dex")) {
            return DEXTERITY;
        } else if (s.equals("Int")) {
            return INTELLIGENCE;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        String name = super.toString();
        return name.substring(0, 1) + name.substring(1).toLowerCase();
    }
}
