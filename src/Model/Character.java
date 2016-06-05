package Model;

import UI.SkillTreePreferences;
import Util.CommonUtil;
import Util.Validator;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.security.Key;
import java.util.*;

/**
 * Created by Brandon on 2016-02-20.
 *
 * Represents a character with information about stats, gems, and items
 */
public class Character extends StatsChangeObservable {

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
    public boolean hasAttributeRequirements(SkillTreePreferences prefs) {
        CharacterStats combined = getStats(prefs);
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

    public void setStats(CharacterStats stats) {
        this.stats = stats;
        notifyStatsListeners();
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

    public StyledDocument displayInfo(SkillTreePreferences prefs) throws BadLocationException {
        return new CharacterDisplayer().displayInfo(prefs);
    }

    /**
     *
     * @param prefs the {@link SkillTreePreferences} for this character
     * @return stats with or without gear in consideration depending on the preferences
     */
    public CharacterStats getStats(SkillTreePreferences prefs) {
        CharacterStats combined = new CharacterStats();
        combined.addStats(stats);
        if (characterClass != null) {
            combined.addStats(characterClass.getBaseStats());
        }
        if (prefs.isWithGear()) {
            for (Item item : items) {
                combined.addStats(item.getStats());
            }
        }
        return combined;
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
        return Validator.getValidationMessageForCharacter(prefs, getStats(prefs));
    }

    @Override
    public void addGem(Gem gem) {
        super.addGem(gem);
        notifyItemListeners();
    }

    @Override
    public void removeGems(Collection<Gem> toRemove) {
        super.removeGems(toRemove);
        notifyItemListeners();
    }

    @Override
    public void addItem(Item item) {
        super.addItem(item);
        notifyItemListeners();
        notifyStatsListeners();
    }

    @Override
    public void removeItems(Collection<Item> toRemove) {
        super.removeItems(toRemove);
        notifyItemListeners();
        notifyStatsListeners();
    }

    @Override
    public String toString() {
        return getName();
    }

    private class CharacterDisplayer {

        public StyledDocument displayInfo(SkillTreePreferences prefs) throws BadLocationException {
            if (characterClass != null) {
                StyledDocument doc = new DefaultStyledDocument();
                CharacterStats stat = getStats(prefs);
                StringBuilder title = new StringBuilder(characterClass.toString());
                if (ascendancy != null) {
                    title.append("\n").append(ascendancy);
                }
                doc.insertString(0, title.append("\n\n").toString(), CommonUtil.getLargeFont());
                displayAttributes(doc, stat);
                displayResistances(doc, stat, prefs);
                doc.insertString(doc.getLength(), "\n", CommonUtil.getLargeFont());
                displayStats(doc, stat);
                doc.insertString(doc.getLength(), "\n", CommonUtil.getLargeFont());
                displayKeystones(doc);
                doc.insertString(doc.getLength(), String.format("\nPredicted life at level %d: %d",
                        prefs.getLevel(), getStats(prefs).getLifeAtLevel(prefs.getLevel())), CommonUtil.getRegularFont());
                return doc;
            }
            return null;
        }

        private void displayResistances(StyledDocument doc, CharacterStats stat, SkillTreePreferences prefs) throws BadLocationException {
            doc.insertString(doc.getLength(), "Resistances:", CommonUtil.getLargeFont());
            for (ResistType type : ResistType.values()) {
                String insert = String.format("\n%d / %d%% %s Resistance", stat.getEffectiveResist(type, prefs.getDifficulty()),
                        stat.getMaxResist(type), type);
                AttributeSet set = CommonUtil.getRegularFont();
                if (!stat.isResistanceCapped(type, prefs.getDifficulty())) {
                    set = CommonUtil.getWarningFont();
                }
                doc.insertString(doc.getLength(), insert, set);
            }
            doc.insertString(doc.getLength(), "\n", CommonUtil.getRegularFont());
        }

        private void displayAttributes(StyledDocument doc, CharacterStats stat) throws BadLocationException {
            doc.insertString(doc.getLength(), "Attributes:", CommonUtil.getLargeFont());
            StringBuilder builder = new StringBuilder();
            for (AttributeType type : AttributeType.values()) {
                builder.append("\n").append(String.format("%d to %s", stat.calculateAttributeValue(type), type));
            }
            doc.insertString(doc.getLength(), builder.append("\n").toString(), CommonUtil.getRegularFont());
        }

        private void displayStats(StyledDocument doc, CharacterStats stat) throws BadLocationException {
            doc.insertString(doc.getLength(), "Passives:", CommonUtil.getLargeFont());
            for (StatType type : StatType.values()) {
                createSection(doc, stat, type);
            }
            doc.insertString(doc.getLength(), "\n", CommonUtil.getRegularFont());
        }

        private void createSection(StyledDocument doc, CharacterStats stat, StatType type) throws BadLocationException {
            boolean shouldAdd = false;
            StringBuilder builder = new StringBuilder();
            for (Stat next : stat) {
                if (next.getType() == type) {
                    builder.append("\n").append(next);
                    shouldAdd = true;
                }
            }
            if (shouldAdd) {
                doc.insertString(doc.getLength(), String.format("\n%s", type), CommonUtil.getRegularFont(true));
                doc.insertString(doc.getLength(), builder.toString(), CommonUtil.getRegularFont());
            }
        }

        private void displayKeystones(StyledDocument doc) throws BadLocationException {
            displayNotable(doc, "Keystones", keystones);
            displayNotable(doc, "Ascendancy", ascendancyNodes);
        }

        private void displayNotable(StyledDocument doc, String title, Collection<?> nodes) throws BadLocationException {
            if (!nodes.isEmpty()) {
                doc.insertString(doc.getLength(), String.format("%s:", title), CommonUtil.getLargeFont());
                StringBuilder builder = new StringBuilder();
                for (Object next : nodes) {
                    builder.append(String.format("\n%s", next));
                }
                doc.insertString(doc.getLength(), builder.append("\n").toString(), CommonUtil.getRegularFont());
            }
        }
    }
}
