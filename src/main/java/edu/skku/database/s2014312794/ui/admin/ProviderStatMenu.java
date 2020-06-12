package edu.skku.database.s2014312794.ui.admin;

import de.vandermeer.asciitable.AsciiTable;
import edu.skku.database.s2014312794.model.UserStat;
import edu.skku.database.s2014312794.service.StatService;
import edu.skku.database.s2014312794.service.UserService;
import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.util.ConsoleUtil;
import edu.skku.database.s2014312794.util.TableUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProviderStatMenu extends AbstractMenu {

    @Override
    public String name() {
        return "Provider Statistics";
    }

    @Override
    public String description() {
        return "show the highest and lowest accessed providers.";
    }

    @Override
    protected boolean isLoop() {
        return false;
    }

    private AsciiTable makeTable(List<UserStat> userStats) {
        return TableUtil.createTable(
                Arrays.asList("Id", "Name", "Downloads", "Item Name", "Item Downloads", "Item Deleted"),
                userStats.stream().flatMap(u ->
                        Stream.concat(
                                Stream.of(Arrays.asList(u.getId(), u.getName(), u.getDownloadCnt(), "", "", "")),
                                u.getItemStats().stream().map(i -> Arrays.asList("", "", "", i.getName(), i.getDownloadCnt(), i.isDeleted()))
                        )
                ).collect(Collectors.toList())
        );
    }

    @Override
    protected void doBody() {
        System.out.println("Top 3 highest download providers");

        List<UserStat> highestAccessProviders = StatService.getProviderStats(false, 3);
        System.out.println(makeTable(highestAccessProviders).render(80));

        System.out.println("\nTop 3 lowest download providers");

        List<UserStat> lowestAccessProviders = StatService.getProviderStats(true, 3);
        System.out.println(makeTable(lowestAccessProviders).render(80));

        System.out.println("Do you want to delete low download provider accounts? Items uploaded by them will be also deleted! (y/n)");
        String inputStr = ConsoleUtil.readInput(s -> s.matches("[yn]"));
        if (inputStr.equals("y"))
            doDelete(lowestAccessProviders);
    }

    private void doDelete(List<UserStat> lowestAccessedProviders) {
        lowestAccessedProviders.forEach(i -> UserService.deleteUser(i.getId()));
        System.out.println("Successfully deleted accounts.");
    }
}
