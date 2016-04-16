package Util;

import Model.*;
import UI.BuildPlanner;
import UI.SkillTreePreferences;

import java.util.ArrayList;
import java.util.List;

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
        List<ResistType> uncapped = new ArrayList<>();
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

    private static String getRequirementsMessage(CharacterStats stats, Requirements requirements) {
        List<AttributeType> missing = new ArrayList<>();
        for (AttributeType type : AttributeType.values()) {
            if (!stats.hasRequiredAttribute(type, requirements.getAttributeRequirement(type))) {
                missing.add(type);
            }
        }
        return String.format("Missing %s requirements", CommonUtil.joinCollection(missing));
    }

    private static String getUncappedResMessage(List<ResistType> uncapped, Difficulty difficulty) {
        String resists = CommonUtil.joinCollection(uncapped);
        if (uncapped.size() > 1) {
            return String.format("%s resistances are uncapped for %s", resists, difficulty);
        }
        return String.format("%s resistance is uncapped for %s", resists, difficulty);
    }

    private Validator() {
        // prevent instantiation
    }
}
