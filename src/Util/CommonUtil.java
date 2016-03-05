package Util;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Brandon on 2016-02-28.
 *
 * Holds commonly used static methods
 */
public class CommonUtil {

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
}
