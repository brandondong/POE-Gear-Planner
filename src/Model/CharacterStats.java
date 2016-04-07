package Model;

import Util.GameConstants;

/**
 * Created by Brandon on 2016-02-20.
 *
 * Represents the stats of a character
 */
public class CharacterStats extends Stats {

    /**
     *
     * @param difficulty the {@link Difficulty} in which the resists should be checked against
     * @return <code>true</code> if there are uncapped elemental resistances in the chosen difficulty
     */
    public boolean hasUncappedResistances(Difficulty difficulty) {
        for (ResistType type : ResistType.ELEMENTAL) {
            if (!isResistanceCapped(type, difficulty)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param type the {@link ResistType} to be checked
     * @param difficulty the {@link Difficulty} in which the resist should be checked against
     * @return <code>true</code> if the specified resist is capped in the chosen difficulty
     */
    public boolean isResistanceCapped(ResistType type, Difficulty difficulty) {
        return getTotalResist(type, difficulty) >= getMaxResist(type);
    }

    /**
     *
     * @param type the {@link ResistType} to be returned
     * @param difficulty the {@link Difficulty} in which the resist should be calculated
     * @return the total resistance of the specified type at the chosen difficulty
     */
    public int getTotalResist(ResistType type, Difficulty difficulty) {
        return getAdditionalResistValue(type) + difficulty.getResistancePenalty();
    }

    /**
     *
     * @param type the {@link ResistType} to be returned
     * @param difficulty the {@link Difficulty} in which the resist should be calculated
     * @return the effective resistance of the specified type at the chosen difficulty
     */
    public int getEffectiveResist(ResistType type, Difficulty difficulty) {
        return Math.min(getTotalResist(type, difficulty), getMaxResist(type));
    }

    /**
     *
     * @param type the {@link ResistType} to be returned
     * @return the maximum resistance of the specified type
     */
    public int getMaxResist(ResistType type) {
        return getMaximumResistValue(type) + GameConstants.BASE_RESISTANCE_CAP;
    }

    /**
     *
     * @param require the requirements to be checked against
     * @return <code>true</code> if the character meets the attribute requirements
     */
    public boolean hasRequiredAttributes(Requirements require) {
        for (AttributeType type : AttributeType.values()) {
            if (!hasRequiredAttribute(type, require.getAttributeRequirement(type))) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param type the {@link AttributeType} to be checked
     * @param value the requirement value of the specified attribute type
     * @return <code>true</code> if the character has a greater or equal amount of that attribute type
     */
    public boolean hasRequiredAttribute(AttributeType type, int value) {
        return calculateAttributeValue(type) >= value;
    }

    /**
     *
     * @param type the {@link ResistType} of interest
     * @param difficulty the {@link Difficulty} to be calculated against
     * @return an info display of a given resistance
     */
    public String getResInfo(ResistType type, Difficulty difficulty) {
        return String.format("%s (%d / %d%%)", type, getEffectiveResist(type, difficulty), getMaxResist(type));
    }
}
