package Util;

import Data.*;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    public static final JSONObject SKILL_TREE_DATA = getSkillTreeData();

    public static final Map<Integer, SimpleNode> SKILL_TREE_NODES = getSkillTreeNodes();

    /**
     *
     * @return the json data of the Passive Skill Tree
     */
    private static JSONObject getSkillTreeData() {
        try {
            return new JSONObject(FileUtils.readFileToString(new File("skilltree.json")));
        } catch (JSONException e) {
            Logger.addError("Error trying to parse skill tree data.", e);
        } catch (IOException e) {
            Logger.addError("Error trying to read skilltree.json.", e);
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
        }
        return new HashMap<>();
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

    private GameConstants() {
        // prevent instantiation
    }
}
