package edu.skku.database.s2014312794.ui.finance;

import edu.skku.database.s2014312794.model.User;
import edu.skku.database.s2014312794.security.LoginContextHolder;
import edu.skku.database.s2014312794.ui.AbstractMenu;

import java.text.DecimalFormat;
import java.util.Locale;

public class BillMenu extends AbstractMenu {

    @Override
    public String name() {
        return "Bills";
    }

    @Override
    public String description() {
        return "Show your current balance, including sub fee, file download earnings, etc.";
    }

    @Override
    protected boolean isLoop() {
        return false;
    }

    @Override
    protected void doBody() {
        User loginUser = LoginContextHolder.getLoginUser();

        String netIncomeStr = DecimalFormat.getCurrencyInstance(Locale.KOREA).format(0);
        String netExpenseStr = DecimalFormat.getCurrencyInstance(Locale.KOREA).format(0);

        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------------------------------------\n");
        sb.append("Bill (Type: %s)\n");
        sb.append("\n");
        sb.append("Net Income: %s\n");
        sb.append("Remaining Payments: %s\n");
        sb.append("--------------------------------------------------------------------------------\n");

        System.out.printf(sb.toString(), loginUser.getRole(), netIncomeStr, netExpenseStr);

        return;
    }
}
