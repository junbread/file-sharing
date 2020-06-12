package edu.skku.database.s2014312794.ui.main;

import edu.skku.database.s2014312794.model.UserRole;
import edu.skku.database.s2014312794.security.LoginContextHolder;
import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.ui.Menu;
import edu.skku.database.s2014312794.ui.admin.AdminMenu;
import edu.skku.database.s2014312794.ui.finance.FinanceMenu;
import edu.skku.database.s2014312794.ui.item.ItemMenu;
import edu.skku.database.s2014312794.util.ConsoleUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainMenu extends AbstractMenu {

    public static final Map<UserRole, List<Menu>> options = new HashMap<>();

    static {
        options.put(UserRole.PROVIDER, Arrays.asList(new FinanceMenu(), new ItemMenu()));
        options.put(UserRole.SUBSCRIBER, Arrays.asList(new FinanceMenu(), new ItemMenu()));
        options.put(UserRole.ADMIN, Arrays.asList(new AdminMenu()));
    }

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

        System.out.printf("Your current role is %s. To change this, contact to administrator.\n\n",
                LoginContextHolder.getLoginUser().getRole());
    }

    @Override
    protected void doBody() {
        List<Menu> currentRoleOptions = options.get(LoginContextHolder.getLoginUser().getRole());
        ConsoleUtil.selectOption(currentRoleOptions, false).run();
    }
}
