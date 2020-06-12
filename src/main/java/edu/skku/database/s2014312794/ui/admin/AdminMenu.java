package edu.skku.database.s2014312794.ui.admin;

import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.ui.Menu;
import edu.skku.database.s2014312794.util.ConsoleUtil;

import java.util.Arrays;
import java.util.List;

public class AdminMenu extends AbstractMenu {

    private static List<Menu> options = Arrays.asList(new SubscriberStatMenu(), new ProviderStatMenu(), new ItemStatMenu());

    @Override
    public String name() {
        return "Admin";
    }

    @Override
    public String description() {
        return "about system managements.";
    }

    @Override
    protected void doBody() {
        ConsoleUtil.selectOption(options, false).run();
    }
}
