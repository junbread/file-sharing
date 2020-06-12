package edu.skku.database.s2014312794.ui.main;

import edu.skku.database.s2014312794.ui.Menu;
import edu.skku.database.s2014312794.util.ConsoleUtil;

import java.util.Arrays;
import java.util.List;

public class WelcomeMenu implements Menu {

    private static final List<Menu> options = Arrays.asList(new LoginMenu(), new RegisterMenu());

    @Override
    public String name() {
        return "Welcome";
    }

    @Override
    public String description() {
        return "Prints welcome screen.";
    }

    @Override
    public void run() {
        System.out.println(
                "\u001b[34m" +
                        "                                                                     .88888888:.\n" +
                        "                                                                    88888888.88888.\n" +
                        "                                                                  .8888888888888888.\n" +
                        "                                                                  888888888888888888\n" +
                        "                                                                  88' _`88'_  `88888\n" +
                        "                                                                  88 88 88 88  88888\n" +
                        "                                                                  88_88_::_88_:88888\n" +
                        "                                                                  88:::,::,:::::8888\n" +
                        "   __ _ _            _                _                           88`:::::::::'`8888\n" +
                        "  / _(_) |          | |              (_)                         .88  `::::'    8:88.\n" +
                        " | |_ _| | ___   ___| |__   __ _ _ __ _ _ __   __ _             8888            `8:888.\n" +
                        " |  _| | |/ _ \\ / __| '_ \\ / _` | '__| | '_ \\ / _` |          .8888'             `888888.\n" +
                        " | | | | |  __/ \\__ \\ | | | (_| | |  | | | | | (_| |         .8888:..  .::.  ...:'8888888:.\n" +
                        " |_| |_|_|\\___| |___/_| |_|\\__,_|_|  |_|_| |_|\\__, |        .8888.'     :'     `'::`88:88888\n" +
                        "                                               __/ |       .8888        '         `.888:8888.\n" +
                        "                                              |___/       888:8         .           888:88888\n" +
                        "                                                        .888:88        .:           888:88888:\n" +
                        "                                                        8888888.       ::           88:888888\n" +
                        "                                                        `.::.888.      ::          .88888888\n" +
                        "                                                       .::::::.888.    ::         :::`8888'.:.\n" +
                        "                                                      ::::::::::.888   '         .::::::::::::\n" +
                        "                                                      ::::::::::::.8    '      .:8::::::::::::.\n" +
                        "                                                     .::::::::::::::.        .:888:::::::::::::\n" +
                        "                                                     :::::::::::::::88:.__..:88888:::::::::::'\n" +
                        "                                                      `'.:::::::::::88888888888.88:::::::::'\n" +
                        "                                                            `':::_:' -- '' -'-' `':_::::'`" +
                        "\u001b[0m\n\n"
        );

        ConsoleUtil.selectOption(options, true).run();
    }
}
