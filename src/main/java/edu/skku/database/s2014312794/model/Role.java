package edu.skku.database.s2014312794.model;

import edu.skku.database.s2014312794.ui.Menu;

import java.util.Collections;
import java.util.List;

public enum Role implements Model {
    // TODO Add role-specific menu
    SUBSCRIBER(Collections.emptyList()),
    PROVIDER(Collections.emptyList()),
    ADMIN(Collections.emptyList());

    private final List<Menu> menus;

    Role(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Menu> getAvailableMenus() {
        return menus;
    }
}
