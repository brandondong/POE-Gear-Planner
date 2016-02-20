package Data;

import Util.GameConstants;

/**
 * Created by Brandon on 2016-02-20.
 */
public class CharacterStats extends Stats {

    /**
     *
     * @param difficulty the {@link Difficulty} in which the resists should be checked against
     * @return <code>true</code> if there are uncapped elemental resistances in the chosen difficulty
     */
    public boolean hasUncappedResistances(Difficulty difficulty) {
        return !isResistanceCapped(ResistType.FIRE, difficulty) || !isResistanceCapped(ResistType.COLD, difficulty)
                || !isResistanceCapped(ResistType.LIGHTNING, difficulty);
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
     * @return <code>true</code> if the character meets their attribute requirements
     */
    public boolean hasRequiredAttributes() {
        return hasRequiredAttributes(this);
    }

    /**
     *
     * @param other the stats to be checked against
     * @return <code>true</code> if the character meets the attribute requirements of the stats
     */
    public boolean hasRequiredAttributes(Stats other) {
        for (AttributeType type : AttributeType.values()) {
            if (!hasRequiredAttribute(type, other.getRequiredAttributeValue(type))) {
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
}
