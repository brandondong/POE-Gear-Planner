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
            Character character = new Character(getClass(array));
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

    private static CharacterClass getClass(byte[] array) {
        int classPos = array[4];
        for (CharacterClass charClass : CharacterClass.values()) {
            if (classPos == charClass.getPosition()) {
                return charClass;
            }
        }
        return CharacterClass.SCION;
    }

    public static void main(String[] args) {
        Character character = decodeURL("https://www.pathofexile.com/passive-skill-tree/AAAAAwAALai31r6A21k=");
        System.out.println(character);
    }
}
