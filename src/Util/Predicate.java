package Util;

/**
 * Created by Brandon on 2016-04-09.
 *
 * Equivalent to the Java 8 Predicate class
 */
public interface Predicate<V> {

    boolean apply(V input);
}
