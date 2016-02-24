package Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Brandon on 2016-02-23.
 *
 * Helper class to extract gem JSON data from poedb.tw
 */
public class POEdbToJson {

    public static final String POEDB_GEM_PREFIX = "http://poedb.tw/us/gem.php?n=";

    public static final String POEDB_GEM_LIST_PREFIX = "http://poedb.tw/us/gem.php?c=";

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

    public static List<String> getGemsList(String source) throws Exception {
        List<String> l = new ArrayList<>();
        while (source.contains("</a><td>")) {
            int index = source.indexOf("</a><td>");
            if (source.charAt(index - 1) != '>') {
                int start = source.indexOf("width='16'/>");
                l.add(source.substring(start + "width='16'/>".length(), index));
            }
            source = source.substring(index + "</a><td>".length());
        }
        return l;
    }

    private POEdbToJson() {
        // prevent instantiation
    }

    public static void main(String[] args) throws Exception {
        List<String> gems = new ArrayList<>();
        for (String i : Arrays.asList("18", "19", "vaal")) {
            String url = POEDB_GEM_LIST_PREFIX + i;
            String source = getUrlSource(url);
            gems.addAll(getGemsList(source));
        }
        System.out.println(gems);
        writeGemData("Fireball");
    }
}
