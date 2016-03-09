package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 2016-03-09.
 *
 * Holds all the build information for the program
 */
public class Model {

    private List<Character> characters;

    private Character selected;

    public Model() {
        characters = new ArrayList<>();
        selected = new Character(CharacterClass.SCION);
    }
}
