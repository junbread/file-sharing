package edu.skku.database.s2014312794.util;

import de.vandermeer.asciitable.AsciiTable;
import edu.skku.database.s2014312794.ui.Selectable;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ConsoleUtil {
    public static class BackSelectedException extends RuntimeException {
    }

    private static final BackSelectedException BACK = new BackSelectedException();

    public static int selectOption(AsciiTable table, boolean disableBack) throws BackSelectedException {
        int rowSize = table.getRawContent().size() - 1; // exclude header

        Console console = System.console();

        String selectStr;
        while (true) {
            if (disableBack)
                System.out.println("\nPlease select option (input x to exit program)");
            else
                System.out.println("\nPlease select option (input b to back, input x to exit program)");

            System.out.println(table.render());

            selectStr = readInput(console);

            try {
                int selection = Integer.parseInt(selectStr) - 1;
                if (selection < 0 || selection >= rowSize)
                    throw new IllegalArgumentException();

                return selection;
            } catch (Exception e) {
                switch (selectStr) {
                    case "b":
                        if (disableBack) break;
                        throw BACK;
                    case "x":
                        System.out.println("Exit");
                        System.exit(0);
                }

                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public static <T extends Selectable> List<T> selectMultipleOptions(List<T> options, boolean disableBack) throws BackSelectedException {
        String selectStr;
        Console console = System.console();

        while (true) {
            if (disableBack)
                System.out.println("\nPlease select options. separate with commas. (input x to exit program)");
            else
                System.out.println("\nPlease select options separate with commas. (input b to back, input x to exit program)");

            System.out.println("┌──────────────────────────────────────────────────────────────────────────────┐");

            for (int i = 0; i < options.size(); i++)
                System.out.printf("%d) %s: %s\n", i + 1, options.get(i).name(), options.get(i).description());

            System.out.println("└──────────────────────────────────────────────────────────────────────────────┘");

            selectStr = readInput(console);

            try {
                List<T> results = new ArrayList<>();
                for (String s : selectStr.split(" *, *")) {
                    int i = Integer.parseInt(s) - 1;
                    if (i < 0 || i >= options.size())
                        throw new IllegalArgumentException();

                    results.add(options.get(i));
                }

                return results;
            } catch (Exception e) {
                switch (selectStr) {
                    case "b":
                        if (disableBack) break;
                        throw BACK;
                    case "x":
                        System.out.println("Exit");
                        System.exit(0);
                }

                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public static <T extends Selectable> T selectOption(List<T> options, boolean disableBack) throws BackSelectedException {
        String selectStr;
        Console console = System.console();

        while (true) {
            if (disableBack)
                System.out.println("\nPlease select option (input x to exit program)");
            else
                System.out.println("\nPlease select option (input b to back, input x to exit program)");

            System.out.println("┌──────────────────────────────────────────────────────────────────────────────┐");

            for (int i = 0; i < options.size(); i++)
                System.out.printf("%d) %s: %s\n", i + 1, options.get(i).name(), options.get(i).description());

            System.out.println("└──────────────────────────────────────────────────────────────────────────────┘");

            selectStr = readInput(console);

            try {
                int selection = Integer.parseInt(selectStr) - 1;
                if (selection < 0 || selection >= options.size())
                    throw new IllegalArgumentException();

                return options.get(selection);
            } catch (Exception e) {
                switch (selectStr) {
                    case "b":
                        if (disableBack) break;
                        throw BACK;
                    case "x":
                        System.out.println("Exit");
                        System.exit(0);
                }

                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public static String readInput(Predicate<String> predicate) {
        String selectStr;
        Console console = System.console();

        while (true) {
            selectStr = readInput(console);

            if (predicate.test(selectStr))
                return selectStr;

            System.out.println("Invalid input. Please try again.");
        }
    }

    private static String readInput(Console c) {
        return c.readLine("Select: ").trim();
    }

}
