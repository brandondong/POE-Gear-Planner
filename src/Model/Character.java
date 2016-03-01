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

    private int numJewels;

    public Character(CharacterClass characterClass) {
        this.characterClass = characterClass;
        name = NEW_CHARACTER_NAME;
        stats = new CharacterStats();
        keystones = new HashSet<>();
    }

    /**
     *
     * @return <code>true</code> if the character meets the attribute requirements of their items and gems
     */
    public boolean hasAttributeRequirements() {
        for (Item item : items) {
            if (!combinedStats().hasRequiredAttributes(item.getRequirements())) {
                return false;
            }
        }
        for (Gem gem : gems) {
            if (!combinedStats().hasRequiredAttributes(gem.getRequirements())) {
                return false;
            }
        }
        return true;
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

    public void addStats(Stats other) {
        stats.addStats(other);
    }

    public void addKeystone(KeystoneNode node) {
        keystones.add(node);
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

    public int getNumJewels() {
        return numJewels;
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
            builder.append("\n").append(String.format("%d to %s", ((int) combined.calculateAttributeValue(type)), type));
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
        return getInfo();
    }
}
