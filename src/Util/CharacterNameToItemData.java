package Util;

import Model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 2016-02-28.
 *
 * Gets item data from account and character name
 */
public class CharacterNameToItemData {

    public static final String GET_ITEMS_URL_PREFIX = "https://www.pathofexile.com/character-window/get-items?";

    public static final String ACCOUNT_NAME_PARAM = "accountName";

    public static final String CHARACTER_NAME_PARAM = "character";

    public static List<Item> getItemsFromCharacter(String accountName, String charName) {
        try {
            List<Item> items = new ArrayList<>();
            String source = CommonUtil.getUrlSource(getUrl(accountName, charName));
            JSONArray array = new JSONObject(source).getJSONArray("items");
            for (int i = 0; i < array.length(); i++) {
                addItem(array.getJSONObject(i), items);
            }
            return items;
        } catch (Exception e) {
            Logger.addError("Failed to retrieve character item information.", e);
            return new ArrayList<>();
        }
    }

    private static void addItem(JSONObject obj, List<Item> items) throws JSONException {
        String name = obj.getString("typeLine");
        if (!name.contains("Flask")) {
            String prefix = obj.getString("name").replaceAll("<<.*>>", "");
            if (prefix.length() > 0) {
                name = String.format("%s %s", prefix, name);
            }
            Requirements requirements = getRequirements(obj.getJSONArray("requirements"));
            items.add(new Item(requirements, getStats(obj), name));
        }
    }

    private static Requirements getRequirements(JSONArray array) throws JSONException {
        Requirements require = new Requirements();
        for (int i = 0; i < array.length(); i++) {
            addRequirement(require, array.getJSONObject(i));
        }
        return require;
    }

    private static void addRequirement(Requirements require, JSONObject obj) throws JSONException {
        String name = obj.getString("name");
        try {
            AttributeType type = AttributeType.getTypeFromAbbreviation(name);
            JSONArray array = obj.getJSONArray("values");
            require.setAttributeRequirement(type, Integer.valueOf(array.getJSONArray(0).getString(0)));
        } catch (IllegalArgumentException e) {
        }
    }

    private static Stats getStats(JSONObject obj) throws JSONException {
        Stats stats = new Stats();
        if (obj.has("implicitMods")) {
            stats.addStats(getStats(obj.getJSONArray("implicitMods")));
        }
        if (obj.has("explicitMods")) {
            stats.addStats(getStats(obj.getJSONArray("explicitMods")));
        }
        return stats;
    }

    private static Stats getStats(JSONArray array) throws JSONException {
        Stats stats = new Stats();
        for (int i = 0; i < array.length(); i++) {
            stats.addStat(array.getString(i));
        }
        return stats;
    }

    public static List<Gem> getGemsFromCharacter(String accountName, String charName) {
        try {
            String source = CommonUtil.getUrlSource(getUrl(accountName, charName));
        } catch (Exception e) {
            Logger.addWarning("Failed to retrieve character item information.");
            return new ArrayList<>();
        }
        return null;
    }

    private static String getUrl(String accountName, String charName) {
        return String.format("%s%s=%s&%s=%s", GET_ITEMS_URL_PREFIX, ACCOUNT_NAME_PARAM,
                accountName, CHARACTER_NAME_PARAM, charName);
    }

    private CharacterNameToItemData() {
        // prevent instantiation
    }

    public static void main(String[] args) {
        for (Item item : getItemsFromCharacter("agentvenom1", "WTBsurvivability")) {
            System.out.println(item + "\n");
        }
    }
}
