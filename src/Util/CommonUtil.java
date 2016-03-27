package Util;

import Model.Gem;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Brandon on 2016-02-28.
 *
 * Holds commonly used static methods
 */
public class CommonUtil {

    public static final Color DEFAULT_TEXT_COLOR = Color.black;

    public static final String WIKI_PREFIX = "http://pathofexile.gamepedia.com/";

    /**
     *
     * @param url the website url
     * @return the web page source for the given url
     * @throws IOException
     */
    public static String getUrlSource(String url) throws IOException {
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

    public static void openWebpage(Collection<Gem> gems) {
        Set<String> urls = new HashSet<>();
        for (Gem gem : gems) {
            urls.add(String.format("%s%s", WIKI_PREFIX, gem.getName().replace(" ", "_")));
        }
        for (String url : urls) {
            openWebpage(url);
        }
    }

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            Logger.addError("Could not open webpage.", e);
        }
    }

    /**
     *
     * @param name a string that is all upper case
     * @return the given string with only the first letter as an upper case
     */
    public static String formatUppercaseString(String name) {
        return name.substring(0, 1) + name.substring(1).toLowerCase();
    }

    public static AttributeSet getLargeFont() {
        return getLargeFont(DEFAULT_TEXT_COLOR);
    }

    public static AttributeSet getLargeFont(Color c) {
        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWord, c);
        StyleConstants.setBold(keyWord, true);
        StyleConstants.setFontSize(keyWord, 15);
        return keyWord;
    }

    public static AttributeSet getRegularFont() {
        return getRegularFont(DEFAULT_TEXT_COLOR);
    }

    public static AttributeSet getRegularFont(Color c) {
        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWord, c);
        StyleConstants.setLeftIndent(keyWord, 20);
        StyleConstants.setFontSize(keyWord, 12);
        return keyWord;
    }

    private CommonUtil() {
        // prevent instantiation
    }
}
