package Model;

import Util.CommonUtil;

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
    public String getAdditionalDescription(int value) {
        return String.format("+%d to %s", value, this);
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
    public static AttributeType getTypeFromAbbreviation(String s) {
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
        return CommonUtil.formatUppercaseString(super.toString());
    }
}
