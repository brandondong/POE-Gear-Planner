package Model;

import UI.SkillTreePreferences;
import Util.CommonUtil;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Brandon on 2016-02-20.
 *
 * Represents a character with information about stats, gems, and items
 */
public class Character extends Equipment {

    public static final String NEW_CHARACTER_NAME = "New Build";

    private CharacterClass characterClass;

    private Ascendancy ascendancy;

    private CharacterStats stats;

    private Set<KeystoneNode> keystones;

    private Set<AscendancyNode> ascendancyNodes;

    private String name;

    private int numJewels;

    public Character() {
        name = NEW_CHARACTER_NAME;
        stats = new CharacterStats();
        keystones = new HashSet<>();
        ascendancyNodes = new HashSet<>();
        numJewels = 0;
    }

    /**
     *
     * @return <code>true</code> if the character meets the attribute requirements of their items and gems
     */
    public boolean hasAttributeRequirements() {
        CharacterStats combined = combinedStats();
        for (Item item : items) {
            if (!combined.hasRequiredAttributes(item.getRequirements())) {
                return false;
            }
        }
        for (Gem gem : gems) {
            if (!combined.hasRequiredAttributes(gem.getRequirements())) {
                return false;
            }
        }
        return true;
    }

    public boolean hasUncappedResistances(Difficulty difficulty) {
        return stats.hasUncappedResistances(difficulty);
    }

    /**
     *
     * @return a new set of stats representing the character's stats considering passives, gear, and base stats
     */
    private CharacterStats combinedStats() {
        CharacterStats combined = new CharacterStats();
        combined.addStats(stats);
        if (characterClass != null) {
            combined.addStats(characterClass.getBaseStats());
        }
        for (Item item : items) {
            combined.addStats(item.getStats());
        }
        return combined;
    }

    public int getLifeAtLevel(int level) {
        CharacterStats combined = combinedStats();
        return (int) ((38 + (level * 12) + (combined.calculateAttributeValue(AttributeType.STRENGTH) / 2) +
                        combined.getFlatLifeValue()) * (1 + ((double) combined.getPercentLifeValue()) / 100));
    }

    public void addStats(Stats other) {
        stats.addStats(other);
    }

    public void addKeystone(KeystoneNode node) {
        keystones.add(node);
    }

    public void addAscendancy(AscendancyNode node) {
        ascendancyNodes.add(node);
    }

    public void incNumJewels() {
        numJewels++;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public CharacterStats getStats() {
        return stats;
    }

    public Set<KeystoneNode> getKeystones() {
        return keystones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumJewels() {
        return numJewels;
    }

    public void setAscendancy(Ascendancy ascendancy) {
        this.ascendancy = ascendancy;
    }

    /**
     *
     * @return the character data presented in a nice way for debugging
     */
    public String getInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append("\n").append(characterClass).append("\n").append(ascendancy);
        CharacterStats combined = combinedStats();
        for (AttributeType type : AttributeType.values()) {
            builder.append("\n").append(String.format("%d to %s", combined.calculateAttributeValue(type), type));
        }
        builder.append("\n");
        for (ResistType type : ResistType.values()) {
            builder.append("\n").append(String.format("%d %s Resistance", combined.getEffectiveResist(type, Difficulty.MERCILESS), type));
        }
        builder.append("\n\nSTATS:\n").append(stats).append("\n");
        builder.append(String.format("Number of Jewel Sockets: %d", numJewels));
        if (!keystones.isEmpty()) {
            builder.append("\n");
            for (KeystoneNode keystone : keystones) {
                builder.append("\n").append(keystone);
            }
        }
        if (!ascendancyNodes.isEmpty()) {
            builder.append("\n");
            for (AscendancyNode node : ascendancyNodes) {
                builder.append("\n").append(node);
            }
        }
        String items = super.toString();
        if (items.length() > 0) {
            builder.append("\n\n").append(items);
        }
        return builder.toString();
    }

    public StyledDocument displayInfo(SkillTreePreferences prefs) throws BadLocationException {
        if (characterClass != null) {
            StyledDocument doc = new DefaultStyledDocument();
            StringBuilder title = new StringBuilder(characterClass.toString());
            if (ascendancy != null) {
                title.append("\n").append(ascendancy);
            }
            title.append("\n\nStats:");
            doc.insertString(0, title.toString(), CommonUtil.getLargeFont());
            displayAttributes(prefs, doc);
            displayStats(prefs, doc);
            return doc;
        }
        return null;
    }

    private void displayAttributes(SkillTreePreferences prefs, StyledDocument doc) throws BadLocationException {
        CharacterStats display = getStats(prefs);
        StringBuilder builder = new StringBuilder();
        for (AttributeType type : AttributeType.values()) {
            builder.append("\n").append(String.format("%d to %s", display.calculateAttributeValue(type), type));
        }
        doc.insertString(doc.getLength(), builder.toString(), CommonUtil.getRegularFont());
    }

    private void displayStats(SkillTreePreferences prefs, StyledDocument doc) throws BadLocationException {
        doc.insertString(doc.getLength(), String.format("\n%s", getStats(prefs)), CommonUtil.getRegularFont());
    }

    public CharacterStats getStats(SkillTreePreferences prefs) {
        if (prefs.isWithGear()) {
            return combinedStats();
        }
        return stats;
    }

    public String getDisplayString() {
        if (ascendancy != null) {
            return String.format("%s (%s)", toString(), ascendancy);
        } else if (characterClass != null) {
            return String.format("%s (%s)", toString(), characterClass);
        }
        return toString();
    }

    public String validate(SkillTreePreferences prefs) {
        List<ResistType> uncapped = new ArrayList<>();
        CharacterStats stats = combinedStats();
        for (ResistType type : ResistType.ELEMENTAL) {
            if (!stats.isResistanceCapped(type, prefs.getDifficulty())) {
                uncapped.add(type);
            }
        }
        if (!uncapped.isEmpty()) {
            return getUncappedResMessage(uncapped, prefs.getDifficulty());
        }
        return String.format("Resistances are capped for %s difficulty", prefs.getDifficulty());
    }

    private String getUncappedResMessage(List<ResistType> uncapped, Difficulty difficulty) {
        String resists = CommonUtil.joinCollection(uncapped);
        if (uncapped.size() > 1) {
            return String.format("%s resistances are uncapped for %s", resists, difficulty);
        }
        return String.format("%s resistance is uncapped for %s", resists, difficulty);
    }

    @Override
    public String toString() {
        return getName();
    }
}
