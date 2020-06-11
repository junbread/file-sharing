package edu.skku.database.s2014312794.ui.item;

import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.ui.Menu;
import edu.skku.database.s2014312794.util.ConsoleUtil;

import java.util.Arrays;
import java.util.List;

public class ItemMenu extends AbstractMenu {

    public static final List<Menu> options = Arrays.asList(new ItemSearchMenu(), new ItemManageMenu());

    @Override
    public String name() {
        return "Items";
    }

    @Override
    public String description() {
        return "search/download items. providers can also manage the uploads.";
    }

    @Override
    protected void doBody() {
        ConsoleUtil.selectOption(options, false).run();
    }
}
