package edu.skku.database.s2014312794;

import edu.skku.database.s2014312794.ui.Menu;
import edu.skku.database.s2014312794.ui.LoginMenu;
import edu.skku.database.s2014312794.ui.MainMenu;

public class Runner {
    public static void main(String[] args) {
        Menu login = new LoginMenu();
        Menu main = new MainMenu();

        login.run();
        main.run();
    }
}
