package Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 2016-02-28.
 *
 * Used to get a list of characters from an account name
 */
public class AccountNameToCharacters {

    public static final String GET_CHARACTERS_URL_PREFIX = "https://www.pathofexile.com/character-window/get-characters?";

    public static final String ACCOUNT_NAME_PARAM = "accountName";

    public static List<String> getCharacters(String accountName) {
        try {
            List<String> chars = new ArrayList<>();
            String source = CommonUtil.getUrlSource(getUrl(accountName));
            JSONArray array = new JSONArray(source);
            for (int i = 0; i < array.length(); i++) {
                chars.add(array.getJSONObject(i).getString("name"));
            }
            return chars;
        } catch (Exception e) {
            Logger.addWarning("Failed to retrieve character list. Ensure that the account name " +
                    "is correct and that your profile is set to public.");
            return new ArrayList<>();
        }
    }

    private static String getUrl(String accountName) {
        return String.format("%s%s=%s", GET_CHARACTERS_URL_PREFIX, ACCOUNT_NAME_PARAM, accountName);
    }

    private AccountNameToCharacters() {
        // prevent instantiation
    }

    public static void main(String[] args) {
        System.out.println(getCharacters("agentvenom1"));
    }
}
