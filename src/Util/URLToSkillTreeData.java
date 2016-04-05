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
        for (int i = start; i + 1 < array.length; i += 2) {
            byte[] int16 = { array[i], array[i + 1] };
            int id = ByteBuffer.wrap(int16).getShort() & 0xffff;
            SimpleNode node = GameConstants.SKILL_TREE_NODES.get(id);
            if (node != null) {
                if (node instanceof KeystoneNode) {
                    character.addKeystone((KeystoneNode) node);
                } else if (node instanceof StatsNode) {
                    character.addStats(((StatsNode) node).getStats());
                } else if (node instanceof AscendancyNode) {
                    character.addAscendancy((AscendancyNode) node);
                } else {
                    character.incNumJewels();
                }
            }
        }
    }

    public static void main(String[] args) {
        Character character = new Character();
        decodeURL("https://www.pathofexile.com/fullscreen-passive-skill-tree/AAAABAMBAQFvAx4HHg18DkgPqxB7EQ8RLxFQEZYTbRQJFX4V1xa_GGobJR1PHdkfQSL0JIsmiCsKLKYsvzQKNbk3ZjeDOw09X0NUSRNJUUmxS65Ms05tUilSU1KvVeBWY1fJWAdYWlxAXGtd8mf8adhqjGwLbRltbHWedoJ4L3zlfqF_xoRIhMWGrofLiEKJ4It6j_qSdJMfkyeVLpeVl_SboZu1nDKhL6LZpcSm66crp5usmLQMtUi3MLtNu_y-Or6KwQDBxcM6w23G98gMyBTK088V0B_Xz9lb2xrdX9-K4vfmWOd06QLquutj7BjviO_r8NX0cfVL9W_31_k3", character);
        //character.addEquipement(CharacterNameToItemData.getEquipmentFromCharacter("agentvenom1", "WTBsurvivability"));
        System.out.println(character.getInfo());
        System.out.println(character.getLifeAtLevel(95));
    }
}
