package UI;

import javax.swing.*;

/**
 * Created by Brandon on 2016-03-03.
 *
 * The main Jframe to hold all the UI components
 */
public class Main extends JFrame {

    public Main() {
        super("Path of Exile Gear Planner");
        init();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void init() {
        getContentPane().add(new SkillTreeFormPart());
        getContentPane().add(new BuildsListPart());
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //for testing
        new Main().setVisible(true);
    }
}
