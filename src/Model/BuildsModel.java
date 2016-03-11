package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Brandon on 2016-03-09.
 *
 * Holds all the build information for the program
 */
public class BuildsModel implements Iterable<Character> {

    private List<Character> characters;

    private Character selected;

    public BuildsModel() {
        characters = new ArrayList<>();
        selected = new Character(CharacterClass.SCION);
        characters.add(selected);
    }

    public void addCharacter() {
        addCharacter(new Character(CharacterClass.SCION));
    }

    public void addCharacter(Character other) {
        characters.add(other);
        selected = other;
    }

    public void removeCharacter(Character other) {
        if (other == selected) {
            characters.remove(other);
            selected = characters.get(0);
        } else {
            characters.remove(other);
        }
    }

    public Character getSelected() {
        return selected;
    }

    public void setSelected(Character other) {
        selected = other;
    }

    public int numCharacters() {
        return characters.size();
    }

    @Override
    public Iterator<Character> iterator() {
        return characters.iterator();
    }
}