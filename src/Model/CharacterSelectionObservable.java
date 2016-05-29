package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by Brandon on 2016-05-28.
 *
 * Handles character selection events by notifying observers
 */
public abstract class CharacterSelectionObservable {

    private List<Observer> observers;

    public CharacterSelectionObservable() {
        observers = new ArrayList<>();
    }

    public void addCharacterChangedListener(Observer observer) {
        observers.add(observer);
    }

    public void notifyCharacterChanged(Character c) {
        for (Observer observer : observers) {
            observer.update(null, c);
        }
    }
}
