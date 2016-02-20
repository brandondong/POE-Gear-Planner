package Data;

import Util.GameConstants;

/**
 * Created by Brandon on 2016-02-19.
 */
public enum Difficulty {
    NORMAL(GameConstants.NORAML_RESIST_PENALTY),
    CRUEL(GameConstants.CRUEL_RESIST_PENALTY),
    MERCILESS(GameConstants.MERCILESS_RESIST_PENALTY);

    private int resistancePenalty;

    private Difficulty(int resistancePenalty) {
        this.resistancePenalty = resistancePenalty;
    }

    /**
     *
     * @return the resistance penalty for this difficulty
     */
    public int getResistancePenalty() {
        return resistancePenalty;
    }
}
