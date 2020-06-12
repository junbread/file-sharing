package edu.skku.database.s2014312794.ui.admin;

import de.vandermeer.asciitable.AsciiTable;
import edu.skku.database.s2014312794.model.UserStat;
import edu.skku.database.s2014312794.service.StatService;
import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.util.TableUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SubscriberStatMenu extends AbstractMenu {

    @Override
    public String name() {
        return "Subscriber Statistics";
    }

    @Override
    public String description() {
        return "show the highest and lowest accessed subscribers.";
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
        System.out.println("Top 3 highest access users");

        List<UserStat> highestAccessSubscribers = StatService.getSubscriberStats(false, 3);
        System.out.println(makeTable(highestAccessSubscribers).render(80));

        System.out.println("\nTop 3 lowest access users");

        List<UserStat> lowestAccessSubscribers = StatService.getSubscriberStats(true, 3);
        System.out.println(makeTable(lowestAccessSubscribers).render(80));
    }

}
