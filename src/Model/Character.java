package Model;

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

    private CharacterStats stats;

    private Set<KeystoneNode> keystones;

    private String name;

    private int numNodes;

    private int numJewels;

    public Character(CharacterClass characterClass) {
        this.characterClass = characterClass;
        name = NEW_CHARACTER_NAME;
        stats = new CharacterStats();
        keystones = new HashSet<>();
        numNodes = 0;
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
    public CharacterStats combinedStats() {
        CharacterStats combined = new CharacterStats();
        combined.addStats(stats);
        combined.addStats(characterClass.getBaseStats());
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

    public void incNumNodes() {
        numNodes++;
    }

    public void incNumJewels() {
        numJewels++;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
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

    public int getPredictedLevel() {
        return Math.max(1, numNodes - 20);
    }

    /**
     *
     * @return the character data presented in a nice way for debugging
     */
    public String getInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append("\n").append(characterClass);
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
        String items = super.toString();
        if (items.length() > 0) {
            builder.append("\n\n").append(items);
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return getName();
    }
}
