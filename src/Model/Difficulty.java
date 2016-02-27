package Model;

import Util.GameConstants;

/**
 * Created by Brandon on 2016-02-19.
 *
 * Represents a game difficulty
 */
public enum Difficulty {
    NORMAL(GameConstants.NORMAL_RESIST_PENALTY),
    CRUEL(GameConstants.CRUEL_RESIST_PENALTY),
    MERCILESS(GameConstants.MERCILESS_RESIST_PENALTY);

    private int resistancePenalty;

    Difficulty(int resistancePenalty) {
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
