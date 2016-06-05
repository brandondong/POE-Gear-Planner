package UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by Brandon on 2016-06-02.
 *
 * Handles preference change events and notifies listeners
 */
public abstract class PreferencesObservable {

    private static List<Observer> itemListeners = new ArrayList<>();

    private static List<Observer> statListeners = new ArrayList<>();

    public void addItemSelectionListener(Observer observer) {
        itemListeners.add(observer);
    }

    public void addStatSettingsChangeListener(Observer observer) {
        statListeners.add(observer);
    }

    public void notifyItemSelectionListeners() {
        for (Observer observer : itemListeners) {
            observer.update(null, null);
        }
    }

    public void notifyStatSettingsChangeListeners() {
        for (Observer observer : statListeners) {
            observer.update(null, null);
        }
    }
}
