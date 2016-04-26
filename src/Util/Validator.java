package Util;

import Model.*;
import Model.Character;
import UI.BuildPlanner;
import UI.SkillTreePreferences;

import java.util.*;

/**
 * Created by Brandon on 2016-04-15.
 *
 * A utility class for creating label messages when validating requirements
 */
public class Validator {

    public static String getValidationMessageForItem(CharacterStats stats, DisplayableItem item) {
        if (item == null) {
            return "No item selected";
        } else if (stats.hasRequiredAttributes(item.getRequirements())) {
            return "All attribute requirements are met";
        }
        return getRequirementsMessage(stats, item.getRequirements());
    }

    public static String getValidationMessageForCharacter(SkillTreePreferences prefs, CharacterStats stats) {
        List<ResistType> uncapped = getUncappedResists(stats, prefs);
        if (!uncapped.isEmpty()) {
            return getUncappedResMessage(uncapped, prefs.getDifficulty());
        }
        return String.format("Resistances are capped for %s difficulty", prefs.getDifficulty());
    }

    private static String getRequirementsMessage(CharacterStats stats, Requirements requirements) {
        return String.format("Missing %s requirements", CommonUtil.joinCollection(getMissingAttributes(stats, requirements)));
    }

    private static String getUncappedResMessage(List<ResistType> uncapped, Difficulty difficulty) {
        String resists = CommonUtil.joinCollection(uncapped);
        if (uncapped.size() > 1) {
            return String.format("%s resistances are uncapped for %s", resists, difficulty);
        }
        return String.format("%s resistance is uncapped for %s", resists, difficulty);
    }

    private static List<ResistType> getUncappedResists(CharacterStats stats, SkillTreePreferences prefs) {
        List<ResistType> uncapped = new ArrayList<>();
        for (ResistType type : ResistType.ELEMENTAL) {
            if (!stats.isResistanceCapped(type, prefs.getDifficulty())) {
                uncapped.add(type);
            }
        }
        return uncapped;
    }

    private static List<AttributeType> getMissingAttributes(CharacterStats stats, Requirements requirements) {
        List<AttributeType> missing = new ArrayList<>();
        for (AttributeType type : AttributeType.values()) {
            if (!stats.hasRequiredAttribute(type, requirements.getAttributeRequirement(type))) {
                missing.add(type);
            }
        }
        return missing;
    }

    public static String getValidationMessage(Character character, SkillTreePreferences prefs) {
        CharacterStats stats = character.getStats(prefs);
        List<ResistType> uncapped = getUncappedResists(stats, prefs);
        Map<AttributeType, Integer> map = getMaxAttributes(character);
        List<AttributeType> missing = new ArrayList<>();
        for (AttributeType type : AttributeType.values()) {
            if (!stats.hasRequiredAttribute(type, map.get(type))) {
                missing.add(type);
            }
        }
        if (uncapped.isEmpty() && missing.isEmpty()) {
            return "All resistance and attribute requirements met";
        }
        return getValidationMessage(stats, prefs, uncapped, map);
    }

    private static String getValidationMessage(CharacterStats stats, SkillTreePreferences prefs, List<ResistType> uncapped, Map<AttributeType, Integer> map) {
        List<String> messages = new ArrayList<>();
        for (ResistType type : uncapped) {
            messages.add(String.format("%d %s resistance", stats.getMaxResist(type) - stats.getEffectiveResist(type, prefs.getDifficulty()), type));
        }
        for (AttributeType type : map.keySet()) {
            int diff = map.get(type) - stats.calculateAttributeValue(type);
            if (diff > 0) {
                messages.add(String.format("%d to %s", diff, type));
            }
        }
        return String.format("Missing the following: %s", CommonUtil.joinCollection(messages));
    }

    private static Map<AttributeType, Integer> getMaxAttributes(Character character) {
        Map<AttributeType, Integer> map = new HashMap<>();
        for (AttributeType type : AttributeType.values()) {
            map.put(type, 0);
        }
        getMaxAttributes(map, character.getItems());
        getMaxAttributes(map, character.getGems());
        return map;
    }

    private static void getMaxAttributes(Map<AttributeType, Integer> map, Collection<? extends DisplayableItem> items) {
        for (DisplayableItem item : items) {
            for (AttributeType type : AttributeType.values()) {
                map.put(type, Math.max(item.getRequirements().getAttributeRequirement(type), map.get(type)));
            }
        }
    }

    private Validator() {
        // prevent instantiation
    }
}
