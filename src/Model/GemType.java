package Model;

import java.awt.*;

/**
 * Created by Brandon on 2016-02-28.
 *
 * Represents the base type of a gem
 */
public enum GemType {
    INTELLIGENCE(new Color(55, 104, 180)), STRENGTH(new Color(209, 1, 1)), DEXTERITY(new Color(26, 128, 40)), WHITE(Color.gray);

    private Color color;

    GemType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
