package Model;

import Util.CommonUtil;

/**
 * Created by Brandon on 2016-03-29.
 *
 * Represents the different Ascendancy classes
 */
public enum Ascendancy {
    JUGGERNAUT(1, CharacterClass.MARAUDER), BERSERKER(2, CharacterClass.MARAUDER), CHIEFTAIN(3, CharacterClass.MARAUDER),
    RAIDER(1, CharacterClass.RANGER), DEADEYE(2, CharacterClass.RANGER), PATHFINDER(3, CharacterClass.RANGER),
    OCCULTIST(1, CharacterClass.WITCH), ELEMENTALIST(2, CharacterClass.WITCH), NECROMANCER(3, CharacterClass.WITCH),
    SLAYER(1, CharacterClass.DUELIST), GLADIATOR(2, CharacterClass.DUELIST), CHAMPION(3, CharacterClass.DUELIST),
    INQUISITOR(1, CharacterClass.TEMPLAR), HIEROPHANT(2, CharacterClass.TEMPLAR), GUARDIAN(3, CharacterClass.TEMPLAR),
    ASSASSIN(1, CharacterClass.SHADOW), TRICKSTER(2, CharacterClass.SHADOW), SABOTEUR(3, CharacterClass.SHADOW),
    ASCENDANT(1, CharacterClass.SCION);

    private int pos;

    private CharacterClass characterClass;

    Ascendancy(int pos, CharacterClass characterClass) {
        this.pos = pos;
        this.characterClass = characterClass;
    }

    public static Ascendancy getAscendancy(int pos, CharacterClass characterClass) {
        for (Ascendancy asc : Ascendancy.values()) {
            if (asc.getPos() == pos && asc.getCharacterClass() == characterClass) {
                return asc;
            }
        }
        return null;
    }

    public int getPos() {
        return pos;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    @Override
    public String toString() {
        return CommonUtil.formatUppercaseString(super.toString());
    }
}
