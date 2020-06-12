package edu.skku.database.s2014312794.ui.item;

import edu.skku.database.s2014312794.model.UserRole;
import edu.skku.database.s2014312794.security.LoginContextHolder;
import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.ui.Menu;
import edu.skku.database.s2014312794.util.ConsoleUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemMenu extends AbstractMenu {

    public static final Map<UserRole, List<Menu>> options = new HashMap<>();

    static {
        options.put(UserRole.PROVIDER, Arrays.asList(new ItemUploadMenu(), new ItemManageMenu()));
        options.put(UserRole.SUBSCRIBER, Arrays.asList(new ItemDownloadMenu()));
    }

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
        List<Menu> currentRoleOptions = options.get(LoginContextHolder.getLoginUser().getRole());
        ConsoleUtil.selectOption(currentRoleOptions, false).run();
    }
}
