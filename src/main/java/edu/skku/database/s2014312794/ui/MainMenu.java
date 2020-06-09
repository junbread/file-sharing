package edu.skku.database.s2014312794.ui;

import edu.skku.database.s2014312794.security.LoginContextHolder;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Menu {

    private static List<Menu> options;

    static {
        options = new ArrayList<>();
        //options.add();
    }

    @Override
    public boolean run() {
        String selectStr;

        System.out.printf("Your current role is %s. To change this, contact to administrator.\n", LoginContextHolder.getLoginUser().getRole());

        Console c = System.console();
        while (true) {
            System.out.println("Please select number (input x to exit program)");
            System.out.println("--------------------------------------------");

            for (int i = 0; i < options.size(); i++)
                System.out.printf("%d) %s: %s\n", i + 1, options.get(i).name(), options.get(i).description());

            System.out.println("--------------------------------------------");
            System.out.print("Select: ");

            selectStr = c.readLine();

            try {
                int selection = Integer.parseInt(selectStr) - 1;

                assert selection > 0 && selection <= options.size();
                options.get(selection).run();

            } catch (Throwable e) {
                if (selectStr.equals("x")) {
                    System.out.println("Exit");
                    System.exit(0);
                } else
                    System.out.println("\nInvalid input. Please try again.");
            }
        }
    }

    @Override
    public String name() {
        return "Main";
    }

    @Override
    public String description() {
        return "Main application menu.";
    }
}
