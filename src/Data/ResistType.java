package Data;

/**
 * Created by Brandon on 2016-02-19.
 *
 * Represents a resist type
 */
public enum ResistType {
    FIRE, COLD, LIGHTNING, CHAOS;

    /**
     *
     * @return the unique identifier representing additional resistance of this type
     */
    public String getAdditionalId() {
        return String.format("+%%d%%%% to %s Resistance", this);
    }

    /**
     *
     * @return the unique identifier representing maximum resistance of this type
     */
    public String getMaxId() {
        return String.format("+%%d%%%% to maximum %s Resistance", this);
    }

    @Override
    public String toString() {
        String name = super.toString();
        return name.substring(0, 1) + name.substring(1).toLowerCase();
    }
}
