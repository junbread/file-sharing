package edu.skku.database.s2014312794.ui.main;

import edu.skku.database.s2014312794.security.LoginContextHolder;
import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.ui.Menu;
import edu.skku.database.s2014312794.ui.item.ItemMenu;
import edu.skku.database.s2014312794.ui.finance.FinanceMenu;
import edu.skku.database.s2014312794.ui.stat.StatMenu;
import edu.skku.database.s2014312794.util.ConsoleUtil;

import java.util.Arrays;
import java.util.List;

public class MainMenu extends AbstractMenu {

    private static final List<Menu> options = Arrays.asList(new FinanceMenu(), new ItemMenu(), new StatMenu());

    @Override
    public String name() {
        return "Main";
    }

    @Override
    public String description() {
        return "Main application menu.";
    }

    @Override
    protected void doHeader() {
        super.doHeader();

        System.out.printf("Your current role is %s. To change this, contact to administrator.\n",
                LoginContextHolder.getLoginUser().getRole());
    }

    @Override
    protected void doBody() {
        ConsoleUtil.selectOption(options, false).run();
    }
}
