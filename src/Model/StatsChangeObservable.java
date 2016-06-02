package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by Brandon on 2016-06-01.
 *
 * Handles stats and item change events by notifying observers
 */
public abstract class StatsChangeObservable extends Equipment {

    private static List<Observer> itemListeners = new ArrayList<>();

    private static List<Observer> statsListeners = new ArrayList<>();

    public void addItemListener(Observer observer) {
        itemListeners.add(observer);
    }

    public void addStatsListener(Observer observer) {
        statsListeners.add(observer);
    }

    /**
     * Notifies listeners that items have been modified
     */
    public void notifyItemListeners() {
        for (Observer observer : itemListeners) {
            observer.update(null, null);
        }
    }

    /**
     * Notifies listeners that character stats have changed
     */
    public void notifyStatsListeners() {
        for (Observer observer : statsListeners) {
            observer.update(null, null);
        }
    }
}
