package Util;

import Model.*;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Brandon on 2016-02-19.
 *
 * Holds game constants and data
 */
public class GameConstants {

    public static final int NORMAL_RESIST_PENALTY = 0;

    public static final int CRUEL_RESIST_PENALTY = -20;

    public static final int MERCILESS_RESIST_PENALTY = -60;

    public static final int BASE_RESISTANCE_CAP = 75;

    public static final String PASSIVE_TREE_URL_PREFIX = "passive-skill-tree/";

    public static final JSONObject SKILL_TREE_DATA = getJSONData("skilltree.json");

    public static final Map<Integer, SimpleNode> SKILL_TREE_NODES = getSkillTreeNodes();

    public static final Map<String, GemData> GEM_DATABASE = initGemData();

    /**
     *
     * @return the json data of the Passive Skill Tree
     */
    private static JSONObject getJSONData(String file) {
        try {
            return new JSONObject(FileUtils.readFileToString(new File(file)));
        } catch (JSONException e) {
            Logger.addError(String.format("Error trying to parse %s.", file), e);
        } catch (IOException e) {
            Logger.addError(String.format("Error trying to read %s.", file), e);
        }
        return null;
    }

    /**
     * Creates a map of all nodes for easy access later
     *
     * @return a map containing all the nodes of the Passive Skill Tree
     */
    private static Map<Integer, SimpleNode> getSkillTreeNodes() {
        try {
            Map<Integer, SimpleNode> data = new HashMap<>();
            JSONArray array = SKILL_TREE_DATA.getJSONArray("nodes");
            for (int i = 0; i < array.length(); i++) {
                SimpleNode node = parseNode(array.getJSONObject(i));
                data.put(node.getId(), node);
            }
            return data;
        } catch (JSONException e) {
            Logger.addError("Could not parse node information from skilltree.json.", e);
            return new HashMap<>();
        }
    }

    private static SimpleNode parseNode(JSONObject obj) throws JSONException {
        int id = obj.getInt("id");
        JSONArray array = obj.getJSONArray("sd");
        if (obj.getBoolean("ks")) {
            return new KeystoneNode(id, obj.getString("dn"), array.getString(0));
        } else if (obj.getString("dn").equals("Jewel Socket")) {
            return new SimpleNode(id);
        }
        return new StatsNode(id, parseStats(array));
    }

    private static Stats parseStats(JSONArray array) throws JSONException {
        Stats stats = new Stats();
        for (int i = 0; i < array.length(); i++) {
            stats.addStat(new Stat(array.getString(i)));
        }
        return stats;
    }

    /**
     *
     * @return a map containing all gem data
     */
    private static Map<String, GemData> initGemData() {
        try {
            Map<String, GemData> map = new HashMap<>();
            JSONObject obj = getJSONData("gemdata.json");
            for (Iterator<String> keys = obj.keys(); keys.hasNext();) {
                String key = keys.next();
                GemData data = getGemData(key, obj.getJSONObject(key));
                map.put(key, data);
            }
            return map;
        } catch (JSONException e) {
            Logger.addError("Could not parse node information from gemdata.json.", e);
            return new HashMap<>();
        }
    }

    private static GemData getGemData(String name, JSONObject obj) throws JSONException {
        GemData data = new GemData(name);
        for (Iterator<String> keys = obj.keys(); keys.hasNext();) {
            String key = keys.next();
            data.addAttribute(AttributeType.valueOf(key.toUpperCase()), convertToList(obj.getJSONArray(key)));
        }
        return data;
    }

    private static List<Integer> convertToList(JSONArray array) throws JSONException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getInt(i));
        }
        return list;
    }

    private GameConstants() {
        // prevent instantiation
    }
}
