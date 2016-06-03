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

    private static List<Observer> observers = new ArrayList<>();

    public void addItemSelectionListener(Observer observer) {
        observers.add(observer);
    }

    public void notifyItemSelectionListeners() {
        for (Observer observer : observers) {
            observer.update(null, null);
        }
    }
}
