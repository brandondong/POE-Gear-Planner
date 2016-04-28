package Util;

import Model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     *
     * @param accountName the name of the account
     * @param charName the character name
     * @return the {@link Equipment} in-game on that character
     */
    public static Equipment getEquipmentFromCharacter(String accountName, String charName) {
        try {
            Equipment equipment = new Equipment();
            String source = CommonUtil.getUrlSource(getUrl(accountName, charName));
            equipment.getItems().addAll(getItemsFromCharacter(source));
            equipment.getGems().addAll(getGemsFromCharacter(source));
            return equipment;
        } catch (Exception e) {
            Logger.addError("Failed to retrieve character information.", e);
            return new Equipment();
        }
    }

    private static List<Item> getItemsFromCharacter(String source) throws JSONException {
        List<Item> items = new ArrayList<>();
        JSONArray array = new JSONObject(source).getJSONArray("items");
        for (int i = 0; i < array.length(); i++) {
            addItem(array.getJSONObject(i), items);
        }
        return items;
    }

    private static List<Gem> getGemsFromCharacter(String source) throws JSONException {
        List<Gem> gems = new ArrayList<>();
        JSONArray array = new JSONObject(source).getJSONArray("items");
        for (int i = 0; i < array.length(); i++) {
            addGems(array.getJSONObject(i).getJSONArray("socketedItems"), gems);
        }
        return gems;
    }

    private static void addGems(JSONArray array, List<Gem> gems) throws JSONException {
        for (int i = 0; i < array.length(); i++) {
            addGem(array.getJSONObject(i), gems);
        }
    }

    private static void addGem(JSONObject obj, List<Gem> gems) throws JSONException {
        gems.add(new Gem(obj.getString("typeLine"), getLevel(obj.getJSONArray("properties"))));
    }

    private static int getLevel(JSONArray array) throws JSONException {
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            if (obj.getString("name").equals("Level")) {
                String value = obj.getJSONArray("values").getJSONArray(0).getString(0);
                return Integer.valueOf(value.replace(" (Max)", ""));
            }
        }
        return 0;
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
        for (String param : Arrays.asList("implicitMods", "explicitMods", "craftedMods")) {
            if (obj.has(param)) {
                stats.addStats(getStats(obj.getJSONArray(param)));
            }
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

    private static String getUrl(String accountName, String charName) {
        return String.format("%s%s=%s&%s=%s", GET_ITEMS_URL_PREFIX, ACCOUNT_NAME_PARAM,
                accountName, CHARACTER_NAME_PARAM, charName);
    }

    private CharacterNameToItemData() {
        // prevent instantiation
    }

    public static void main(String[] args) {
        System.out.println(getEquipmentFromCharacter("agentvenom1", "WTBsurvivability"));
    }
}
