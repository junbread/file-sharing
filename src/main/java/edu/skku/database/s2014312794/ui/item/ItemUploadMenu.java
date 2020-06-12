package edu.skku.database.s2014312794.ui.item;

import de.vandermeer.asciitable.AsciiTable;
import edu.skku.database.s2014312794.model.*;
import edu.skku.database.s2014312794.security.LoginContextHolder;
import edu.skku.database.s2014312794.service.CategoryService;
import edu.skku.database.s2014312794.service.ItemService;
import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.ui.Option;
import edu.skku.database.s2014312794.util.ConsoleUtil;
import edu.skku.database.s2014312794.util.TableUtil;

import java.io.Console;
import java.util.*;
import java.util.stream.Collectors;

public class ItemUploadMenu extends AbstractMenu {

    @Override
    public String name() {
        return "Upload Item";
    }

    @Override
    public String description() {
        return "upload the item to the server.";
    }

    @Override
    protected boolean isLoop() {
        return false;
    }

    private Category selectCategory() {
        System.out.println("Category Information");

        List<Option<Category>> categories =
                CategoryService.getCategories()
                        .stream()
                        .map(c -> new Option<>(c.getName(), "", c))
                        .collect(Collectors.toList());

        return ConsoleUtil.selectOption(categories, true).value();
    }

    private List<Item> selectDependencies() {
        List<Item> dependencies = new ArrayList<>();

        Console c = System.console();

        System.out.println("Is your item has dependencies? (y/n)");
        String inputStr = ConsoleUtil.readInput(s -> s.matches("[yn]"));
        if (inputStr.equals("y")) {
            while (true) {
                System.out.println("Searching required program...");

                Map<String, Object> criteria = new HashMap<>();
                criteria.put("deleted", false);
                criteria.put("type", ItemType.PROGRAM.name());

                inputStr = c.readLine("OS Information (WINDOWS/MAC/LINUX/ALL): ");
                criteria.put("os", ItemOs.valueOf(inputStr).name());

                inputStr = c.readLine("Hardware Information (PC/MAC/WORKSTATION/ALL): ");
                criteria.put("hardware", ItemHardware.valueOf(inputStr).name());

                inputStr = c.readLine("Item Name: ");
                criteria.put("name", inputStr);

                List<Item> items = ItemService.getItems(criteria);

                if (items.isEmpty()) {
                    System.out.println("Nothing found. Please try another one.");
                    continue;
                }

                AsciiTable table = TableUtil.createIndexedTable(
                        Arrays.asList("Name", "Category", "Size", "Description"),
                        items.stream().map(it -> Arrays.asList(
                                it.getName(), it.getCategory().getName(),
                                it.getSize(), it.getDescription()
                        )).collect(Collectors.toList())
                );

                int selectedIdx = ConsoleUtil.selectOption(table, true);
                dependencies.add(items.get(selectedIdx));

                System.out.println("Is another item is required? (y/n)");
                inputStr = ConsoleUtil.readInput(s -> s.matches("[yn]"));
                if (!inputStr.equals("y"))
                    break;
            }
        }
        return dependencies;
    }

    private String generateUrl() {
        String urlBase = "https://junbread.win/file-sharing/download?id=";
        return urlBase + UUID.randomUUID().toString();
    }

    @Override
    protected void doBody() {
        System.out.println("Please write down new item fields.");

        Item item = new Item();

        item.setAuthor(LoginContextHolder.getLoginUser());
        item.setUrl(generateUrl());
        item.setCategory(selectCategory());

        Console c = System.console();
        String inputStr;

        inputStr = c.readLine("Type (PROGRAM/IMAGE/VIDEO/AUDIO/DOCUMENT): ");
        item.setType(ItemType.valueOf(inputStr));
        inputStr = c.readLine("Name: ");
        item.setName(inputStr);
        inputStr = c.readLine("Size (in MBytes): ");
        item.setSize(Integer.parseInt(inputStr));
        inputStr = c.readLine("Hardware (MAC/PC/WORKSTATION/ALL): ");
        item.setHardware(ItemHardware.valueOf(inputStr));
        inputStr = c.readLine("OS (MAC/WINDOWS/LINUX/ALL): ");
        item.setOs(ItemOs.valueOf(inputStr));
        inputStr = c.readLine("Description: ");
        item.setDescription(inputStr);

        List<Item> dependencies = selectDependencies();

        ItemService.insertItem(item, dependencies);

        System.out.print("\nSuccessfully uploaded selected item.\n");
    }
}
