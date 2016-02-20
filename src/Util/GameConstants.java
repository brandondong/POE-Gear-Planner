package Util;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

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

    public static final JSONObject SKILL_TREE_DATA = getSkillTreeData();

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

    private GameConstants() {
        // prevent instantiation
    }
}
