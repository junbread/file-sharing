package edu.skku.database.s2014312794.ui.main;

import edu.skku.database.s2014312794.model.User;
import edu.skku.database.s2014312794.model.UserRole;
import edu.skku.database.s2014312794.service.SecurityService;
import edu.skku.database.s2014312794.ui.Menu;

import java.io.Console;
import java.time.LocalDate;

public class RegisterMenu implements Menu {

    @Override
    public String name() {
        return "Register";
    }

    @Override
    public String description() {
        return "create a new user account.";
    }

    @Override
    public void run() {
        String id;
        String pw;
        String pwConfirm;
        String name;
        String accountNumber;
        String address;
        String phone;
        LocalDate birthday;
        UserRole role;

        Console c = System.console();
        while (true) {
            id = c.readLine("ID: ");

            if (!SecurityService.isDuplicated(id))
                break;

            System.out.println("The ID is already kept by another user. Please try with different ID.");
        }

        while (true) {
            pw = String.valueOf(c.readPassword("PW: "));
            pwConfirm = String.valueOf(c.readPassword("Confirm PW: "));

            if (pwConfirm.equals(pw))
                break;

            System.out.println("PW and PW Confirm is not match. Please try again.");
        }

        name = c.readLine("Name: ");
        role = UserRole.valueOf(c.readLine("Role (PROVIDER/SUBSCRIBER): "));
        accountNumber = c.readLine("Account Number: ");
        address = c.readLine("Address (optional): ");
        phone = c.readLine("Phone (optional): ");

        String birthdayStr = c.readLine("Birthday (optional, YYYY-MM-DD): ");
        if (!birthdayStr.isEmpty()) birthday = LocalDate.parse(birthdayStr);
        else birthday = null;

        User newUser = new User(id, name, role, accountNumber, address, phone, birthday, null, false);
        SecurityService.register(newUser, pw);

        System.out.println("Register Success. Please Log In.");
    }
}
