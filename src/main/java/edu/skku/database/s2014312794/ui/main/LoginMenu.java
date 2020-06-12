package edu.skku.database.s2014312794.ui.main;

import edu.skku.database.s2014312794.model.User;
import edu.skku.database.s2014312794.security.LoginContextHolder;
import edu.skku.database.s2014312794.service.SecurityService;
import edu.skku.database.s2014312794.service.UserService;
import edu.skku.database.s2014312794.ui.Menu;

import java.io.Console;

public class LoginMenu implements Menu {

    private static final Menu main = new MainMenu();

    @Override
    public String name() {
        return "Log In";
    }

    @Override
    public String description() {
        return "Authenticate yourself using ID and PW.";
    }

    @Override
    public void run() {
        String id;
        String pw;

        Console c = System.console();
        while (true) {
            id = c.readLine("ID: ");
            pw = String.valueOf(c.readPassword("PW: "));

            // Do Authentication
            if (SecurityService.login(id, pw)) {
                User loginUser = UserService.getUser(id);
                LoginContextHolder.setLoginUser(loginUser);

                break;
            }

            System.out.print("\nYour identity is incorrect. Please try again.\n");
        }

        System.out.printf("\nLogin Success. Welcome back %s!\n", LoginContextHolder.getLoginUser().getName());

        main.run();
    }

}
