package UI;

import Model.DisplayableItem;
/**
 * Created by Brandon on 2016-04-03.
 *
 * Represents preference settings for each character
 */
public class SkillTreePreferences {

    private boolean isUrlLoaded;

    private String url;

    private DisplayableItem selected;

    public SkillTreePreferences() {
        isUrlLoaded = false;
        url = "";
    }

    public DisplayableItem getSelected() {
        return selected;
    }

    public void setSelected(DisplayableItem selected) {
        this.selected = selected;
    }

    public boolean isUrlLoaded() {
        return isUrlLoaded;
    }

    public void setIsUrlLoaded(boolean isUrlLoaded) {
        this.isUrlLoaded = isUrlLoaded;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
