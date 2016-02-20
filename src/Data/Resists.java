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

    public Resists(int fireResist, int coldResist, int lightningResist, int chaosResist) {
        this.fireResist = fireResist;
        this.coldResist = coldResist;
        this.lightningResist = lightningResist;
        this.chaosResist = chaosResist;
    }

    /**
     *
     * @param type the {@link ResistType} to be returned
     * @param difficulty the resistance penalty specified in {@link Util.GameConstants}
     * @return the resistance of the specified type at the chosen difficulty
     */
    public int getResist(ResistType type, int difficulty) {
        if (type == ResistType.FIRE) {
            return fireResist - difficulty;
        } else if (type == ResistType.COLD) {
            return coldResist - difficulty;
        } else if (type == ResistType.LIGHTNING) {
            return lightningResist - difficulty;
        }
        return chaosResist - difficulty;
    }

    /**
     *
     * @param type the {@link ResistType} to be returned
     * @return the base resistance of the specified type
     */
    public int getBaseResist(ResistType type) {
        return getResist(type, GameConstants.NORAML_RESIST_PENALTY);
    }

    /**
     *
     * @param resists the resistances to be added
     */
    public void addResists(Resists resists) {
        fireResist += resists.getBaseResist(ResistType.FIRE);
        coldResist += resists.getBaseResist(ResistType.COLD);
        lightningResist += resists.getBaseResist(ResistType.LIGHTNING);
        chaosResist += resists.getBaseResist(ResistType.CHAOS);
    }
}
