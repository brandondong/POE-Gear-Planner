package Data;

import Util.GameConstants;
import Util.Logger;
import org.json.JSONObject;

/**
 * Created by Brandon on 2016-02-20.
 *
 * Represents the different character classes in Path of Exile
 */
public enum CharacterClass {
    SCION(0), MARAUDER(1), TEMPLAR(5), WITCH(3), SHADOW(6), RANGER(2), DUELIST(4);

    private int position;

    CharacterClass(int position) {
        this.position = position;
    }

    /**
     *
     * @return the base attribute stats of a character class
     */
    public Stats getBaseStats() {
        try {
            JSONObject data = GameConstants.SKILL_TREE_DATA.getJSONObject("characterData");
            JSONObject classData = data.getJSONObject(String.valueOf(position));

            Stats stats = new Stats();
            stats.addStat(new Stat(AttributeType.STRENGTH.getAdditionalId(), classData.getInt("base_str")));
            stats.addStat(new Stat(AttributeType.DEXTERITY.getAdditionalId(), classData.getInt("base_dex")));
            stats.addStat(new Stat(AttributeType.INTELLIGENCE.getAdditionalId(), classData.getInt("base_int")));
            return stats;
        } catch (Exception e) {
            Logger.addError("Could not parse class base stats.", e);
        }
        return new Stats();
    }
}
