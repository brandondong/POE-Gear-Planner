package Data;

/**
 * Created by Brandon on 2016-02-20.
 */
public enum AttributeType {
    INTELLIGENCE, DEXTERITY, STRENGTH;

    /**
     *
     * @return the unique identifier representing a flat addition of this type
     */
    public String getAdditionalId() {
        return String.format("+%%d to %s", this);
    }

    /**
     *
     * @return the unique identifier representing a percentage addition of this type
     */
    public String getPercentId() {
        return String.format("%%d%%%% increased %s ", this);
    }

    public String getRequirementId() {
        return String.format("Requires %%d %s", this);
    }

    @Override
    public String toString() {
        String name = super.toString();
        return name.substring(0, 1) + name.substring(1).toLowerCase();
    }
}
