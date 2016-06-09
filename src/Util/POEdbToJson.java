package Util;

import java.io.*;
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

    private static final String POEDB_GEM_PREFIX = "http://poedb.tw/us/gem.php?n=";

    private static final String POEDB_GEM_LIST_PREFIX = "http://poedb.tw/us/gem.php?cn=";

    private static final List<String> PAGES = Arrays.asList("Active+Skill+Gem", "Support+Skill+Gem");

    public static final String FILENAME = "gemdata2.json";

    /**
     * Writes the gem attribute data into the specified file
     *
     * @param name the gem name
     */
    public static void writeGemData(String name) throws Exception {
        String source = CommonUtil.getUrlSource(createUrlFromGemName(name));
        List<String> atts = getAttributes(source);
        int count = countColumns(source);
        writeToFile(atts, source, count, name);
    }

    private static int countColumns(String source) {
        int count = 1;
        int index = source.indexOf("<th>Level<th>") + "<th>".length();
        source = source.substring(index);
        while (source.indexOf("Experience") > 0) {
            source = source.substring(source.indexOf("<th>") + "<th>".length());
            count++;
        }
        return count;
    }

    private static void writeToFile(List<String> atts, String source, int cols, String name) throws Exception {
        List<List<Integer>> map = new ArrayList<>();
        for (int i = 0; i < atts.size(); i++) {
            map.add(new ArrayList<Integer>());
        }
        int start = source.indexOf("Experience<tr><td align='center'>") + "Experience<tr><td align='center'>".length();
        source = source.substring(start);
        for (int n = 0; n < 20; n++) {
            for (int i = 0; i < cols; i++) {
                int index = source.indexOf("<td align='center'>") + "<td align='center'>".length();
                if (i > 1 && i - 2 < atts.size()) {
                    try {
                        String value = source.substring(0, source.indexOf("<td align='center'>"));
                        if (value.length() > 0) {
                            map.get(i - 2).add(Integer.valueOf(value));
                        }
                    } catch (Exception e) {
                        writeToFile(atts, map, name);
                        return;
                    }
                }
                source = source.substring(index);
            }
        }
        writeToFile(atts, map, name);
    }

    private static void writeToFile(List<String> atts, List<List<Integer>> map, String name) throws Exception {
        File file = new File(FILENAME);
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(String.format("\"%s\": {\n", name));
        for (int i = 0; i < atts.size(); i++) {
            bw.write(String.format("\"%s\": %s", atts.get(i), map.get(i)));
            if (i < atts.size() - 1) {
                bw.write(",");
            }
            bw.write("\n");
        }
        bw.write("},\n");
        bw.close();
    }

    private static String createUrlFromGemName(String name) {
        name = name.replace(" ", "+");
        return POEDB_GEM_PREFIX + name;
    }

    private static List<String> getAttributes(String source) {
        List<String> l = new ArrayList<>();
        String identifier = "Level<th>Requires Level<th>";
        int start = source.indexOf(identifier) + identifier.length();
        int end = source.indexOf("Mana Multiplier<th>");
        if (end == -1) {
            end = source.indexOf("Mana Cost<th>");
        }
        if (end == -1) {
            end = source.indexOf("Mana Reserved<th>");
        }
        String atts = source.substring(start, end);
        while (atts.length() > 0) {
            int index = atts.indexOf("<th>");
            l.add(atts.substring(0, index));
            atts = atts.substring(index + "<th>".length());
        }
        return l;
    }

    /**
     *
     * @param source the website source
     * @return a list of gem names from the given website
     */
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

    public static void retrieveFromPOEdbAndWriteToFile() throws Exception {
        List<String> gems = new ArrayList<>();
        for (String i : PAGES) {
            String url = POEDB_GEM_LIST_PREFIX + i;
            String source = CommonUtil.getUrlSource(url);
            gems.addAll(getGemsList(source));
        }
        for (String gem : gems) {
            System.out.println("Attempting to write info about " + gem + " to file");
            writeGemData(gem);
        }
    }

    private POEdbToJson() {
        // prevent instantiation
    }

    public static void main(String[] args) throws Exception {
        retrieveFromPOEdbAndWriteToFile();
    }
}
