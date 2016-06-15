package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Brandon on 2016-03-09.
 *
 * Holds all the build information for the program
 */
public class BuildsModel extends CharacterSelectionObservable implements Iterable<Character>, Serializable {

    private List<Character> characters;

    private Character selected;

    public BuildsModel() {
        characters = new ArrayList<>();
        selected = new Character();
        characters.add(selected);
    }

    public void addCharacter() {
        addCharacter(new Character());
    }

    public void addCharacter(Character other) {
        characters.add(other);
        selected = other;
        notifyCharacterChanged();
    }

    public void removeCharacter(Character other) {
        removeCharacterInternal(other);
        notifyCharacterChanged();
    }

    public void removeCharacters(Collection<Character> remove) {
        for (Character c : remove) {
            removeCharacterInternal(c);
        }
        notifyCharacterChanged();
    }

    private void removeCharacterInternal(Character other) {
        characters.remove(other);
        if (other == selected) {
            selected = characters.get(0);
        }
    }

    public Character getSelected() {
        return selected;
    }

    public void setSelected(Character other) {
        selected = other;
        notifyCharacterChanged();
    }

    public int numCharacters() {
        return characters.size();
    }

    @Override
    public Iterator<Character> iterator() {
        return characters.iterator();
    }
}
