package Model;

import Util.CommonUtil;
import Util.GameConstants;
import Util.Logger;
import org.json.JSONException;
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
     * @return the character class's base stats
     */
    public CharacterStats getBaseStats() {
        try {
            JSONObject classData = getClassData();
            CharacterStats stats = new CharacterStats();
            stats.addStat(AttributeType.STRENGTH.getId(), classData.getInt("base_str"));
            stats.addStat(AttributeType.DEXTERITY.getId(), classData.getInt("base_dex"));
            stats.addStat(AttributeType.INTELLIGENCE.getId(), classData.getInt("base_int"));
            return stats;
        } catch (Exception e) {
            Logger.addError("Could not parse class base stats.", e);
        }
        return new CharacterStats();
    }

    private JSONObject getClassData() throws JSONException {
        JSONObject data = GameConstants.SKILL_TREE_DATA.getJSONObject("characterData");
        return data.getJSONObject(String.valueOf(position));
    }

    /**
     *
     * @param position the standard numerical value representing the class as defined in skilltree.json
     * @return the {@link CharacterClass} representing the given input
     */
    public static CharacterClass getCharacterClass(int position) {
        for (CharacterClass charClass : CharacterClass.values()) {
            if (position == charClass.getPosition()) {
                return charClass;
            }
        }
        return null;
    }

    /**
     *
     * @return the corresponding number associated with the class as specified in skilltree.json
     */
    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return CommonUtil.formatUppercaseString(super.toString());
    }
}
