package edu.skku.database.s2014312794.ui.finance;

import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.ui.Menu;
import edu.skku.database.s2014312794.util.ConsoleUtil;

import java.util.Arrays;
import java.util.List;

public class FinanceMenu extends AbstractMenu {

    private static final List<Menu> options = Arrays.asList(new BillMenu(), new SubscriptionMenu());

    @Override
    public String name() {
        return "Finance";
    }

    @Override
    public String description() {
        return "manage your balance and subscription information.";
    }

    @Override
    protected void doBody() {
        ConsoleUtil.selectOption(options, false).run();
    }
}
