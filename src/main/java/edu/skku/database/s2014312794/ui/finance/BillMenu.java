package edu.skku.database.s2014312794.ui.finance;

import edu.skku.database.s2014312794.model.Bill;
import edu.skku.database.s2014312794.model.BillStatus;
import edu.skku.database.s2014312794.model.User;
import edu.skku.database.s2014312794.security.LoginContextHolder;
import edu.skku.database.s2014312794.service.PaymentService;
import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.ui.Option;
import edu.skku.database.s2014312794.util.ConsoleUtil;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

    private boolean showBill() {
        User loginUser = LoginContextHolder.getLoginUser();
        Optional<Bill> bill = PaymentService.getCurrentBill(loginUser);

        if (!bill.isPresent()) {
            System.out.println("Currently Bill is not issued!");
            return false;
        }

        String issueMonth = bill.get().getIssueDate().format(DateTimeFormatter.ofPattern("YYYY-MM"));
        String income = DecimalFormat.getCurrencyInstance(Locale.KOREA).format(bill.get().getIncome());
        String payment = DecimalFormat.getCurrencyInstance(Locale.KOREA).format(bill.get().getPayment());
        String balance = DecimalFormat.getCurrencyInstance(Locale.KOREA).format(bill.get().getBalance());

        StringBuilder sb = new StringBuilder();
        System.out.println("┌──────────────────────────────────────────────────────────────────────────────┐");
        sb.append("Bill : %s\n\n");
        sb.append("Net Income: %s\n");
        sb.append("Net Payment: %s\n");
        sb.append("Balance: %s\n");
        sb.append("Status: %s\n");
        System.out.printf(sb.toString(), issueMonth, income, payment, balance, bill.get().getStatus());
        System.out.println("└──────────────────────────────────────────────────────────────────────────────┘");

        return bill.get().getStatus() == BillStatus.PENDING;
    }

    private void doPayment() {
        System.out.println("Do you want to pay/receive your pending money? Make sure you have enough money in associated account.(y/n)");
        String selectionStr = ConsoleUtil.readInput(s -> s.matches("[yn]"));

        if (!selectionStr.equals("y")) {
            System.out.println("Cancelled.");
            return;
        }

        System.out.println("Connecting to Payment Server...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }

        PaymentService.doPayment(LoginContextHolder.getLoginUser());

        System.out.println("Finished! your remaining income/payments are all cleared.");
    }

    @Override
    protected void doBody() {
        if (!showBill())
            return;

        List<Option<Runnable>> options = Arrays.asList(new Option<>("Do payment", null, this::doPayment));
        ConsoleUtil.selectOption(options, false).value().run();
    }
}