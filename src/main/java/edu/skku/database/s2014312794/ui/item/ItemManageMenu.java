package edu.skku.database.s2014312794.ui.item;

import de.vandermeer.asciitable.AsciiTable;
import edu.skku.database.s2014312794.model.*;
import edu.skku.database.s2014312794.security.LoginContextHolder;
import edu.skku.database.s2014312794.service.ItemService;
import edu.skku.database.s2014312794.service.StatService;
import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.ui.Option;
import edu.skku.database.s2014312794.util.ConsoleUtil;
import edu.skku.database.s2014312794.util.TableUtil;

import java.io.Console;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ItemManageMenu extends AbstractMenu {

    @Override
    public String name() {
        return "Manage Item";
    }

    @Override
    public String description() {
        return "show and modify uploaded item information.";
    }

    private void showStatistics(Item item) {
        List<DownloadLog> downloadLogs = StatService.getDownloadLogs(item);

        System.out.printf("Total %d downloaded.\n", downloadLogs.size());

        AsciiTable table = TableUtil.createTable(
                Arrays.asList("Name", "Download Time"),
                downloadLogs.stream()
                        .map(l -> Arrays.asList(l.getUserName(), l.getDownloadTime()))
                        .collect(Collectors.toList())
        );

        System.out.println(table.render());
    }

    private void doUpdate(Item item) {
        System.out.println("\nPlease write down new item fields. Leave blank if you don't want to update.");

        Console c = System.console();

        String inputStr;

        inputStr = c.readLine("Type (VIEWER/PROGRAM/IMAGE/VIDEO/AUDIO/DOCUMENT): ");
        if (!inputStr.isEmpty())
            item.setType(ItemType.valueOf(inputStr));
        inputStr = c.readLine("Name: ");
        if (!inputStr.isEmpty())
            item.setName(inputStr);
        inputStr = c.readLine("Size (in MBytes): ");
        if (!inputStr.isEmpty())
            item.setSize(Integer.parseInt(inputStr));
        inputStr = c.readLine("Hardware (MAC/PC/WORKSTATION/ALL): ");
        if (!inputStr.isEmpty())
            item.setHardware(ItemHardware.valueOf(inputStr));
        inputStr = c.readLine("OS (MAC/WINDOWS/LINUX/ALL): ");
        if (!inputStr.isEmpty())
            item.setOs(ItemOs.valueOf(inputStr));
        inputStr = c.readLine("Description: ");
        if (!inputStr.isEmpty())
            item.setDescription(inputStr);

        ItemService.updateItem(item.getId(), item);
        System.out.print("\nSuccessfully updated selected item.\n");
    }

    private void doDelete(Item item) {
        ItemService.deleteItem(item.getId());
        System.out.print("\nSuccessfully deleted selected item.\n");
    }

    @Override
    public void doBody() {
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("author_id", LoginContextHolder.getLoginUser().getId());

        List<Item> items = ItemService.getItems(criteria);

        System.out.printf("You have uploaded %d items.\n", items.size());

        // build table
        List<String> header =
                Arrays.asList("Type", "Name", "Category", "Size", "Hardware", "OS", "Description");
        List<List<?>> contents =
                items.stream().map(it -> Arrays.asList(
                        it.getType(), it.getName(), it.getCategory().getName(),
                        it.getSize(), it.getHardware(), it.getOs(), it.getDescription()
                )).collect(Collectors.toList());

        int selection = ConsoleUtil.selectOption(TableUtil.createIndexedTable(header, contents), false);

        System.out.printf("Selected Item: %s\n", items.get(selection).getName());

        List<Option<Consumer<Item>>> options = Arrays.asList(
                new Option<>("Statistics", "show detailed statistics about the item", this::showStatistics),
                new Option<>("Update", "modify selected item", this::doUpdate),
                new Option<>("Delete", "delete selected item", this::doDelete));

        ConsoleUtil.selectOption(options, false).value().accept(items.get(selection));
    }
}
