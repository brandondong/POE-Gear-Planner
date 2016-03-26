package Model;

import Util.CommonUtil;
import Util.GameConstants;
import Util.GemData;

import javax.swing.text.*;
import java.awt.*;

/**
 * Created by Brandon on 2016-02-20.
 *
 * Represents a skill gem
 */
public class Gem implements DisplayableItem {

    private Requirements requirements;

    private String name;

    private int level;

    private GemType type;

    /**
     *
     * @param name the name of the gem
     * @param level the level of the gem
     */
    public Gem(String name, int level) {
        this.name = name;
        this.level = level;
        initRequirements();
        initType();
    }

    private void initRequirements() {
        requirements = new Requirements();
        GemData data = GameConstants.GEM_DATABASE.get(name);
        for (AttributeType type : data.getAttributeTypes()) {
            int value = data.getAttributeAtLevel(type, level);
            requirements.setAttributeRequirement(type, value);
        }
    }

    private void initType() {
        int max = 0;
        type = GemType.WHITE;
        GemData data = GameConstants.GEM_DATABASE.get(name);
        for (AttributeType attType : data.getAttributeTypes()) {
            int next = data.getAttributeAtLevel(attType, 20);
            if (next > max) {
                max = next;
                type = attType.getType();
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public GemType getType() {
        return type;
    }

    public Requirements getRequirements() {
        return requirements;
    }

    @Override
    public String toString() {
        return String.format("%s, level %d", name, level);
    }

    @Override
    public StyledDocument displayItem() throws BadLocationException {
        StyledDocument doc = new DefaultStyledDocument();
        doc.insertString(0, String.format("%s\nLevel %d\n\n", name, level), CommonUtil.getLargeFont(getType().getColor()));
        doc.insertString(doc.getLength(), requirements.toString(), CommonUtil.getRegularFont());
        return doc;
    }

    public static void main(String[] args) {
        Gem gem = new Gem("Essence Drain", -1);
        System.out.println(gem.getName());
        System.out.println(gem.getType());
        System.out.println(gem.getRequirements());
    }
}
