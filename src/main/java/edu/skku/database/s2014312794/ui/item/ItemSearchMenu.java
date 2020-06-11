package edu.skku.database.s2014312794.ui.item;

import de.vandermeer.asciitable.AsciiTable;
import edu.skku.database.s2014312794.model.Category;
import edu.skku.database.s2014312794.model.Item;
import edu.skku.database.s2014312794.security.LoginContextHolder;
import edu.skku.database.s2014312794.service.CategoryService;
import edu.skku.database.s2014312794.service.ItemService;
import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.ui.Option;
import edu.skku.database.s2014312794.util.ConsoleUtil;
import edu.skku.database.s2014312794.util.TableUtil;

import java.util.*;
import java.util.stream.Collectors;

public class ItemSearchMenu extends AbstractMenu {

    @Override
    public String name() {
        return "Download Item";
    }

    @Override
    public String description() {
        return "Search and download items based on given criteria.";
    }

    private void download(Item item) {
        System.out.printf("\nDownload Item: %s...\n", item.getName());
        System.out.printf("Download Link: %s\n", item.getUrl());

        ItemService.insertDownloadLog(LoginContextHolder.getLoginUser(), item);

        List<Item> dependencies = ItemService.getDependencies(item.getId());
        if (!dependencies.isEmpty()) {
            System.out.println("There are items required to use this item.");
            AsciiTable table = TableUtil.createTable(
                    Arrays.asList("Name", "Size", "Hardware", "OS"),
                    dependencies.stream().map(d -> Arrays.asList(d.getName(), d.getSize(), d.getHardware(), d.getOs())).collect(Collectors.toList())
            );
            System.out.println(table.render());

            System.out.println("Do you want to download together? You need to pay for these too. (y/n)");
            String selectedStr = ConsoleUtil.readInput(s -> s.matches("[yn]"));
            if (selectedStr.equals("y")) {
                dependencies.forEach(this::download);
            }
        }
    }

    private Map<String, String> doCriteria() {
        System.out.println("Which criteria do you use?");

        Map<String, String> criteria = new HashMap<>();
        List<Option<Runnable>> options = Arrays.asList(
                new Option<>("Category", "general category. ex) Education", () -> {
                    System.out.println("Category Information");

                    List<Option<Category>> categories =
                            CategoryService.getCategories()
                                    .stream()
                                    .map(c -> new Option<>(c.getName(), "", c))
                                    .collect(Collectors.toList());

                    Category selectedCategory = ConsoleUtil.selectOption(categories, true).value();
                    criteria.put("category_id", selectedCategory.getName());
                }),
                new Option<>("OS", "computer's operating system.", () -> {
                    System.out.println("OS Information (WINDOWS/MAC/LINUX)");
                    criteria.put("hardware", ConsoleUtil.readInput(s -> s.matches("(WINDOWS|MAC|LINUX)")));
                }),
                new Option<>("Hardware", "computer's hardware platform.", () -> {
                    System.out.println("Hardware Information (PC/MAC/WORKSTATION)");
                    criteria.put("hardware", ConsoleUtil.readInput(s -> s.matches("(PC|MAC|WORKSTATION)")));
                }),
                new Option<>("Name", "item's name.", () -> {
                    System.out.println("Item Name Information");
                    criteria.put("name", ConsoleUtil.readInput(s -> true));
                })
        );

        ConsoleUtil.selectMultipleOptions(options, false).forEach(s -> s.value().run());

        return criteria;
    }

    @Override
    protected void doBody() {
        List<Item> items = ItemService.getItems(doCriteria());

        System.out.printf("%d items matched. Select to download.\n", items.size());

        AsciiTable table = TableUtil.createIndexedTable(
                Arrays.asList("Name", "Category", "Author", "Type", "Size", "Hardware", "OS", "Last modified", "Description"),
                items.stream().map(it -> Arrays.asList(
                        it.getName(), it.getCategory().getName(), it.getAuthor().getName(), it.getType(),
                        it.getSize(), it.getHardware(), it.getOs(), it.getUpdateTime(), it.getDescription()
                )).collect(Collectors.toList())
        );

        int selectedIdx = ConsoleUtil.selectOption(table, false);
        download(items.get(selectedIdx));
    }
}
