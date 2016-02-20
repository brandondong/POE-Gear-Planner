package Data;

import Util.GameConstants;

/**
 * Created by Brandon on 2016-02-19.
 *
 * Represents the resistances of a character
 */
public class CharacterResists extends Resists {

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
        return getBaseResist(type) + difficulty.getResistancePenalty();
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
        return getAdditionalMaxResist(type) + GameConstants.BASE_RESISTANCE_CAP;
    }
}
