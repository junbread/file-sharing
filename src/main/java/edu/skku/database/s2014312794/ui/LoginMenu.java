package edu.skku.database.s2014312794.ui;

import edu.skku.database.s2014312794.security.LoginContextHolder;
import edu.skku.database.s2014312794.service.UserService;

import java.io.Console;

public class LoginMenu implements Menu {
    private void doLogin() {
        String id;
        String pw;

        Console c = System.console();
        while (true) {
            System.out.print("ID: ");
            id = c.readLine();

            System.out.print("PW: ");
            pw = String.valueOf(c.readPassword());

            if (UserService.isValidUser(id, pw)) {
                LoginContextHolder.setLoginUser(UserService.getUser(id));
                break;
            }

            System.out.println("\nYour identity is incorrect. Please try again.");
        }
    }

    @Override
    public boolean run() {

        System.out.println(
                "                 .88888888:.\n" +
                        "                88888888.88888.\n" +
                        "              .8888888888888888.\n" +
                        "              888888888888888888\n" +
                        "              88' _`88'_  `88888\n" +
                        "              88 88 88 88  88888\n" +
                        "              88_88_::_88_:88888\n" +
                        "              88:::,::,:::::8888\n" +
                        "              88`:::::::::'`8888\n" +
                        "             .88  `::::'    8:88.\n" +
                        "            8888            `8:888.\n" +
                        "          .8888'             `888888.\n" +
                        "         .8888:..  .::.  ...:'8888888:.\n" +
                        "        .8888.'     :'     `'::`88:88888\n" +
                        "       .8888        '         `.888:8888.\n" +
                        "      888:8         .           888:88888\n" +
                        "    .888:88        .:           888:88888:\n" +
                        "    8888888.       ::           88:888888\n" +
                        "    `.::.888.      ::          .88888888\n" +
                        "   .::::::.888.    ::         :::`8888'.:.\n" +
                        "  ::::::::::.888   '         .::::::::::::\n" +
                        "  ::::::::::::.8    '      .:8::::::::::::.\n" +
                        " .::::::::::::::.        .:888:::::::::::::\n" +
                        " :::::::::::::::88:.__..:88888:::::::::::'\n" +
                        "  `'.:::::::::::88888888888.88:::::::::'\n" +
                        "        `':::_:' -- '' -'-' `':_::::'`\n\n" +
                        ":::::::Welcome to File Sharing System:::::::\n" +
                        "--------------------------------------------"
        );

        doLogin();

        System.out.printf("\nLogin Success. Welcome back %s!\n\n", LoginContextHolder.getLoginUser().getName());

        return true;
    }

    @Override
    public String name() {
        return "Log In";
    }

    @Override
    public String description() {
        return "Authenticate yourself using ID and PW.";
    }
}
