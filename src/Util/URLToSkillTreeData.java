package Util;

import Model.*;
import Model.Character;

import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;

/**
 * Created by Brandon on 2016-02-21.
 *
 * Helper class for converting a Passive Skill Tree URL into character data
 */
public class URLToSkillTreeData {

    /**
     *
     * @param url the Passive Skill Tree URL of the character
     * @param character the {@link Character} to be populated
     * @return <code>true</code> if the skill tree data was successfully added
     */
    public static boolean decodeURL(String url, Character character) {
        try {
            byte[] array = decodeURLIntoBase64(url);
            character.setCharacterClass(CharacterClass.getCharacterClass(array[4]));
            addStatsAndKeystones(array, character);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] decodeURLIntoBase64(String url) {
        int start = url.indexOf(GameConstants.PASSIVE_TREE_URL_PREFIX) + GameConstants.PASSIVE_TREE_URL_PREFIX.length();
        url = url.substring(start).replace("-", "+").replace("_", "/");
        return DatatypeConverter.parseBase64Binary(url);
    }

    private static void addStatsAndKeystones(byte[] array, Character character) {
        for (int i = 6; i + 1 < array.length; i += 2) {
            byte[] int16 = { array[i], array[i + 1] };
            int id = ByteBuffer.wrap(int16).getShort() & 0xffff;
            SimpleNode node = GameConstants.SKILL_TREE_NODES.get(id);
            if (node != null) {
                character.incNumNodes();
                if (node instanceof KeystoneNode) {
                    character.addKeystone((KeystoneNode) node);
                } else if (node instanceof StatsNode) {
                    character.addStats(((StatsNode) node).getStats());
                } else {
                    character.incNumJewels();
                }
            }
        }
    }

    public static void main(String[] args) {
        Character character = new Character();
        decodeURL("https://www.pathofexile.com/fullscreen-passive-skill-tree/AAAAAwMBAW8GSQceDXwQlxFQE2wV1xcvGFYYahpsGyUc3B0UHU8dqh3ZIvQo-iqYLKYsvzLRNAo1uTdmN4NDVEQERUdGcUlRSbFLrkyzTblSKVXgVmNXyVgHWFpakVxrZp5odGqsa9tsC20ZeC985X0Yf8aCx4RIhWCGrohAiEKLeox2jxqPRo_6kDOTJ5UumK2boZ2unjyhL6aspuunCKcrqH2qxLQMtzG4k76Kv5fBxcNtyT3K08y80B_T-9fP2QvZW9pi2t3fiuGI4vfpAuq66-7sGOyK74jv6_DV8h70cffB99f5N_no-oD60v5J", character);
        character.addEquipement(CharacterNameToItemData.getEquipmentFromCharacter("agentvenom1", "WTBsurvivability"));
        System.out.println(character);
        System.out.println(character.getLifeAtLevel(95));
        System.out.println(character.getPredictedLevel());
    }
}
