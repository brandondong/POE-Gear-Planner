package Model;

/**
 * Created by Brandon on 2016-02-19.
 *
 * Represents a resist type
 */
public enum ResistType {
    FIRE, COLD, LIGHTNING, CHAOS;

    @Override
    public String toString() {
        String name = super.toString();
        return name.substring(0, 1) + name.substring(1).toLowerCase();
    }
}
