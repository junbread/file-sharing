package edu.skku.database.s2014312794.ui.finance;

import edu.skku.database.s2014312794.model.User;
import edu.skku.database.s2014312794.security.LoginContextHolder;
import edu.skku.database.s2014312794.service.UserService;
import edu.skku.database.s2014312794.ui.AbstractMenu;
import edu.skku.database.s2014312794.ui.Option;
import edu.skku.database.s2014312794.util.ConsoleUtil;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionMenu extends AbstractMenu {

    @Override
    public String name() {
        return "Subscription";
    }

    @Override
    public String description() {
        return "manage your monthly subscription status";
    }

    private void subscribe() {
        User loginUser = LoginContextHolder.getLoginUser();
        loginUser.setSubscription(true);

        UserService.updateUser(loginUser.getId(), loginUser);

        System.out.println("Your subscription is now activated.");
    }

    private void unsubscribe() {
        User loginUser = LoginContextHolder.getLoginUser();
        loginUser.setSubscription(false);

        UserService.updateUser(loginUser.getId(), loginUser);

        System.out.println("Your subscription is now deactivated.");
    }

    @Override
    protected void doBody() {
        Boolean subscription = LoginContextHolder.getLoginUser().getSubscription();
        LocalDate nextPaymentDue = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());

        List<Option<Runnable>> options = new ArrayList<>();
        if (subscription) {
            System.out.printf("Subscription activated. Next payment due is %s.\n\n", nextPaymentDue);
            options.add(new Option<>("Unsubscribe", "deactivate your subscription.", this::unsubscribe));
        } else {
            System.out.println("Subscription deactivated. To use our system, you need to subscribe.\n");
            options.add(new Option<>("Subscribe", "activate your subscription.", this::subscribe));
        }

        ConsoleUtil.selectOption(options, false).value().run();
    }
}
