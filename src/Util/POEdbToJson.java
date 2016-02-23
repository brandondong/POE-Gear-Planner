package Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 2016-02-23.
 *
 * Helper class to extract gem JSON data from poedb.tw
 */
public class POEdbToJson {

    public static final String POEDB_GEM_PREFIX = "http://poedb.tw/us/gem.php?n=";

    public static void writeGemData(String name) throws Exception {
        String source = getUrlSource(createUrlFromGemName(name));
        List<String> atts = getAttributes(source);
        System.out.println(atts);
    }

    private static String createUrlFromGemName(String name) {
        name = name.replace(" ", "+");
        return POEDB_GEM_PREFIX + name;
    }

    private static String getUrlSource(String url) throws IOException {
        URL site = new URL(url);
        URLConnection c = site.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                c.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            a.append(inputLine);
        in.close();

        return a.toString();
    }

    private static List<String> getAttributes(String source) {
        List<String> l = new ArrayList<>();
        int start = source.indexOf("Requires Level<th>") + "Requires Level<th>".length();
        int end = source.indexOf("Mana Cost");
        String atts = source.substring(start, end);
        while (atts.length() > 0) {
            int index = atts.indexOf("<th>");
            l.add(atts.substring(0, index));
            atts = atts.substring(index + "<th>".length());
        }
        return l;
    }

    private POEdbToJson() {
        // prevent instantiation
    }

    public static void main(String[] args) throws Exception {
        writeGemData("Fireball");
    }
}
