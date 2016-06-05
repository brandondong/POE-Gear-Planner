package Util;

import Model.*;
import Model.Character;

import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Brandon on 2016-02-21.
 *
 * Helper class for converting a Passive Skill Tree URL into character data
 */
public class URLToSkillTreeData {

    private static final Pattern SKILL_TREE_PATTERN = Pattern.compile("passive-skill-tree/(.+)");

    /**
     *
     * @param url the Passive Skill Tree URL of the character
     * @param character the {@link Character} to be populated
     * @return <code>true</code> if the skill tree data was successfully added
     */
    public static boolean decodeURL(String url, Character character) {
        try {
            byte[] array = decodeURLIntoBase64(url);
            CharacterClass charClass = CharacterClass.getCharacterClass(array[4]);
            character.setCharacterClass(charClass);
            character.setAscendancy(Ascendancy.getAscendancy(array[5], charClass));
            addStatsAndKeystones(array, character);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] decodeURLIntoBase64(String url) {
        Matcher m = SKILL_TREE_PATTERN.matcher(url);
        if (m.find()) {
            return DatatypeConverter.parseBase64Binary(m.group(1).replace("-", "+").replace("_", "/"));
        }
        return new byte[0];
    }

    private static void addStatsAndKeystones(byte[] array, Character character) {
        int start = 6;
        byte[] check = { array[3], array[2], array[1], array[0] };
        if (ByteBuffer.wrap(check).getInt() > 3) {
            start = 7;
        }
        CharacterStats stats = new CharacterStats();
        for (int i = start; i + 1 < array.length; i += 2) {
            byte[] int16 = { array[i], array[i + 1] };
            int id = ByteBuffer.wrap(int16).getShort() & 0xffff;
            SimpleNode node = GameConstants.SKILL_TREE_NODES.get(id);
            if (node != null) {
                if (node instanceof KeystoneNode) {
                    character.addKeystone((KeystoneNode) node);
                } else if (node instanceof StatsNode) {
                    stats.addStats(((StatsNode) node).getStats());
                } else if (node instanceof AscendancyNode) {
                    character.addAscendancy((AscendancyNode) node);
                } else {
                    character.incNumJewels();
                }
            }
        }
        character.setStats(stats);
    }
}
