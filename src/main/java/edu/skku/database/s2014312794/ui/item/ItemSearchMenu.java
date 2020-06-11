package edu.skku.database.s2014312794.ui.item;

import de.vandermeer.asciitable.AsciiTable;
import edu.skku.database.s2014312794.model.Item;
import edu.skku.database.s2014312794.model.ItemHardware;
import edu.skku.database.s2014312794.service.ItemService;
import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.util.ConsoleUtil;

import java.util.*;
import java.util.stream.Collectors;

public class ItemSearchMenu extends AbstractMenu {

    @Override
    public String name() {
        return "Search Item";
    }

    @Override
    public String description() {
        return "Searches items based on given criteria.";
    }

    private void doDownload(Item item) {
    }

    @Override
    protected void doBody() {
        System.out.println("Which criteria do you use? To use multiple criteria, separate with comma.");
        System.out.println("ex) 1,2,3");
        System.out.println("1) Category");
        System.out.println("2) OS/Hardware");
        System.out.println("3) Keyword");

        Map<String, String> criteria = new HashMap<>();

        List<Integer> selections;
        while (true) try {
            selections = ConsoleUtil.readInput(
                    s -> s.matches("[12] *(, *[12] *)*"),
                    s -> Arrays.stream(s.split(" *, *")).map(Integer::parseInt).collect(Collectors.toList())
            );
            break;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input. Please try again.");
        }

        if (selections.contains(1)) { // Category
            while (true) try {
                System.out.println("Input category. You can specify any category name.");
                criteria.put("category", ConsoleUtil.readInput(s -> !s.isEmpty(), s -> s));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }

        if (selections.contains(2)) { // OS/Hardware
            while (true) try {
                System.out.println("Input hardware.");
                criteria.put("hardware", ConsoleUtil.readInput(
                        s -> Arrays.stream(ItemHardware.values()).anyMatch(t -> t.name().equalsIgnoreCase(s)),
                        String::toUpperCase));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }

        List<Item> items = ItemService.getItems(criteria);

        System.out.printf("%d items matched. Select to download.\n", items.size());


        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("No.", "Name", "Type", "Size", "Description", "Last modified");
        table.addRule();
        for (int i = 0; i < items.size(); i++) {
            Item it = items.get(i);
            table.addRow(i, it.getName(), it.getType(), it.getSize(), it.getDescription(), it.getUpdateTime());
            table.addRule();
        }

        int selection = ConsoleUtil.selectOption(table, false);
        doDownload(items.get(selection));

    }
}
