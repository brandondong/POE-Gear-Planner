package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Brandon on 2016-02-29.
 *
 * Represents the equipment on a character
 */
public class Equipment {

    protected List<Gem> gems;

    protected List<Item> items;

    /**
     * Initializes the equipment to have no items or gems
     */
    public Equipment() {
        gems = new ArrayList<>();
        items = new ArrayList<>();
    }

    /**
     *
     * @param other the equipment to be added
     */
    public void addEquipement(Equipment other) {
        items.addAll(other.getItems());
        gems.addAll(other.getGems());
    }

    public List<Gem> getGems() {
        return gems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItems(Collection<Item> toRemove) {
        items.removeAll(toRemove);
    }

    public void addGem(Gem gem) {
        gems.add(gem);
    }

    public void removeGems(Collection<Gem> toRemove) {
        gems.removeAll(toRemove);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (!items.isEmpty()) {
            builder.append("ITEMS:\n").append(collectionToString(items));
        }
        if (!gems.isEmpty()) {
            builder.append("GEMS:\n").append(collectionToString(gems));
        }
        if (builder.length() > 0) {
            return builder.substring(0, builder.length() - 2);
        }
        return builder.toString();
    }

    private String collectionToString(Collection<?> collect) {
        StringBuilder builder = new StringBuilder();
        for (Object obj : collect) {
            builder.append(obj).append("\n\n");
        }
        return builder.toString();
    }
}
