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

    public Character getSelected() {
        return selected;
    }

    @Override
    public Iterator<Character> iterator() {
        return characters.iterator();
    }
}
