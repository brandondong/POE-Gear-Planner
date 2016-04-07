package UI;

import Model.Difficulty;
import Model.DisplayableItem;
/**
 * Created by Brandon on 2016-04-03.
 *
 * Represents preference settings for each character
 */
public class SkillTreePreferences {

    private String url;

    private DisplayableItem selected;

    private boolean withGear;

    private Difficulty difficulty;

    private int level;

    public SkillTreePreferences() {
        url = "";
        withGear = true;
        difficulty = Difficulty.MERCILESS;
        level = 90;
    }

    public DisplayableItem getSelected() {
        return selected;
    }

    public void setSelected(DisplayableItem selected) {
        this.selected = selected;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isWithGear() {
        return withGear;
    }

    public void setWithGear(boolean withGear) {
        this.withGear = withGear;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
