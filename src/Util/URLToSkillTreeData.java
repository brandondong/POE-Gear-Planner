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
     * @param url A Passive Skill Tree URL
     * @return character data from the skill tree created by the URL
     */
    public static Character decodeURL(String url) {
        try {
            byte[] array = decodeURLIntoBase64(url);
            Character character = new Character(CharacterClass.getCharacterClass(array[4]));
            addStatsAndKeystones(array, character);
            return character;
        } catch (Exception e) {
            Logger.addWarning("Invalid URL. Must be a Passive Skill Tree URL from pathofexile.com");
        }
        return new Character(CharacterClass.SCION);
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
        Character character = decodeURL("https://www.pathofexile.com/fullscreen-passive-skill-tree/AAAAAwABA3UDlgSzBg4J9gthDF8OPBQgFE0UcRZvGFYYkRo4Gj4abBynJKolvCcvJ-0o-jWSNuk6UjrhOyg8BTwtPfxBh0bXSshN41BHU6VYY1h3WfNaUl8EXz9gQ2P9ZlRmnmhlaHRo8moeajtuPXGFcg9yqXaseuZ7w3zZggeCm4LHgwmDX4PMjM-QVZErkc6Ub5SgmK2aO5tdna6f36IApleomqyqr2y3PrvtvJ--gMBmxFjE9sauz37SIdR82E3Yvdl82mLbT9-_4MPjauoY7w7vfPAf8i_yQfJF8pfz3fZI9zL46_no-tL8S_4K_o8=");
        System.out.println(character);
    }
}
