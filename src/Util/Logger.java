package Util;

/**
 * Created by Brandon on 2016-02-20.
 *
 * Logs errors to file and displays them on the screen
 */
public class Logger {

    public static void addError(String message, Exception e) {
        System.out.println(message);
        e.printStackTrace();
    }

    public static void addWarning(String message) {
        System.out.println(message);
    }

    private Logger() {
        // prevent instantiation
    }
}
