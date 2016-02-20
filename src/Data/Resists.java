package Data;

import Util.GameConstants;

/**
 * Created by Brandon on 2016-02-19.
 */
public class Resists {

    private int fireResist;

    private int coldResist;

    private int lightningResist;

    private int chaosResist;

    private int maxFire;

    private int maxCold;

    private int maxLightning;

    private int maxChaos;

    /**
     * Initializes the resistances with no additional maximum resistances
     *
     * @param fireResist the fire resistance
     * @param coldResist the cold resistance
     * @param lightningResist the lightning resistance
     * @param chaosResist the chaos resistance
     */
    public Resists(int fireResist, int coldResist, int lightningResist, int chaosResist) {
        this(fireResist, coldResist, lightningResist, chaosResist, 0, 0, 0, 0);
    }

    /**
     *
     * @param fireResist the fire resistance
     * @param coldResist the cold resistance
     * @param lightningResist the lightning resistance
     * @param chaosResist the chaos resistance
     * @param maxFire the additional fire resistance
     * @param maxCold the additional cold resistance
     * @param maxLightning the additional lightning resistance
     * @param maxChaos the additional chaos resistance
     */
    public Resists(int fireResist, int coldResist, int lightningResist, int chaosResist, int maxFire, int maxCold, int maxLightning, int maxChaos) {
        this.fireResist = fireResist;
        this.coldResist = coldResist;
        this.lightningResist = lightningResist;
        this.chaosResist = chaosResist;
        this.maxFire = maxFire;
        this.maxCold = maxCold;
        this.maxLightning = maxLightning;
        this.maxChaos = maxChaos;
    }

    /**
     *
     * @param type the {@link ResistType} to be returned
     * @param difficulty the {@link Difficulty} in which the resist should be calculated
     * @return the effective resistance of the specified type at the chosen difficulty
     */
    public int getResist(ResistType type, Difficulty difficulty) {
        return Math.min(getTotalResist(type, difficulty), getMaxResist(type));
    }

    /**
     *
     * @param type the {@link ResistType} to be returned
     * @param difficulty the {@link Difficulty} in which the resist should be calculated
     * @return the total resistance of the specified type at the chosen difficulty
     */
    public int getTotalResist(ResistType type, Difficulty difficulty) {
        if (type == ResistType.FIRE) {
            return fireResist + difficulty.getResistancePenalty();
        } else if (type == ResistType.COLD) {
            return coldResist + difficulty.getResistancePenalty();
        } else if (type == ResistType.LIGHTNING) {
            return lightningResist + difficulty.getResistancePenalty();
        }
        return chaosResist + difficulty.getResistancePenalty();
    }

    /**
     *
     * @param type the {@link ResistType} to be returned
     * @return the base resistance of the specified type
     */
    public int getBaseResist(ResistType type) {
        return getTotalResist(type, Difficulty.NORMAL);
    }

    /**
     * Adds another set of resistances onto the current set
     *
     * @param resists the resistances to be added
     */
    public void addResists(Resists resists) {
        fireResist += resists.getBaseResist(ResistType.FIRE);
        coldResist += resists.getBaseResist(ResistType.COLD);
        lightningResist += resists.getBaseResist(ResistType.LIGHTNING);
        chaosResist += resists.getBaseResist(ResistType.CHAOS);

        maxFire = resists.getAdditionalMaxResist(ResistType.FIRE);
        maxCold = resists.getAdditionalMaxResist(ResistType.COLD);
        maxLightning = resists.getAdditionalMaxResist(ResistType.LIGHTNING);
        maxChaos = resists.getAdditionalMaxResist(ResistType.CHAOS);
    }

    /**
     *
     * @param type the {@link ResistType} to be returned
     * @return the maximum resistance of the specified type
     */
    public int getMaxResist(ResistType type) {
        return getAdditionalMaxResist(type) + GameConstants.BASE_RESISTANCE_CAP;
    }

    /**
     *
     * @param type the {@link ResistType} to be returned
     * @return the additional maximum resistance of the specified type
     */
    public int getAdditionalMaxResist(ResistType type) {
        if (type == ResistType.FIRE) {
            return maxFire;
        } else if (type == ResistType.COLD) {
            return maxCold;
        } else if (type == ResistType.LIGHTNING) {
            return maxLightning;
        }
        return maxChaos;
    }
}
