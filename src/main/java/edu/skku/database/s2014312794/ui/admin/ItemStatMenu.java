package edu.skku.database.s2014312794.ui.admin;

import de.vandermeer.asciitable.AsciiTable;
import edu.skku.database.s2014312794.model.ItemStat;
import edu.skku.database.s2014312794.service.ItemService;
import edu.skku.database.s2014312794.service.StatService;
import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.util.ConsoleUtil;
import edu.skku.database.s2014312794.util.TableUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemStatMenu extends AbstractMenu {

    @Override
    public String name() {
        return "Item Statistics";
    }

    @Override
    public String description() {
        return "show the highest accessing items and the lowest accessing items.";
    }

    @Override
    protected boolean isLoop() {
        return false;
    }

    private AsciiTable makeTable(List<ItemStat> itemStats) {
        return TableUtil.createTable(
                Arrays.asList("Id", "Name", "Downloads", "Deleted"),
                itemStats.stream().map(i -> Arrays.asList(i.getId(), i.getName(), i.getDownloadCnt(), i.isDeleted())).collect(Collectors.toList())
        );
    }

    @Override
    protected void doBody() {
        System.out.println("Top 3 highest access items (including deleted)");

        List<ItemStat> highestAccessedItems = StatService.getItemStats(false, 3);
        System.out.println(makeTable(highestAccessedItems).render(80));

        System.out.println("\nTop 3 lowest access items (including deleted)");

        List<ItemStat> lowestAccessedItems = StatService.getItemStats(true, 3);
        System.out.println(makeTable(lowestAccessedItems).render(80));

        System.out.println("Do you want to delete low accessed items? Logs will be remain. (y/n)");
        String inputStr = ConsoleUtil.readInput(s -> s.matches("[yn]"));
        if (inputStr.equals("y"))
            doDelete(lowestAccessedItems);
    }

    private void doDelete(List<ItemStat> lowestAccessedItems) {
        lowestAccessedItems.forEach(i -> ItemService.deleteItem(i.getId()));
        System.out.println("Successfully deleted items.");
    }
}
