package Util;

import Data.ResistType;
import Data.Resists;

/**
 * Created by Brandon on 2016-02-19.
 */
public class GameConstants {

    public static final int NORAML_RESIST_PENALTY = 0;

    public static final int CRUEL_RESIST_PENALTY = -20;

    public static final int MERCILESS_RESIST_PENALTY = -60;

    public static final int BASE_RESISTANCE_CAP = 75;

    public static final Resists KRAITLYN_REWARD_NORMAL = new Resists(10, 10, 10, 0);

    private GameConstants() {
        // prevent instantiation
    }
}
