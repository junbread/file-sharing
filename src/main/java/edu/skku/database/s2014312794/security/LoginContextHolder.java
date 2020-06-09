package edu.skku.database.s2014312794.security;

import edu.skku.database.s2014312794.model.User;

public class LoginContextHolder {
    private static class InstanceHolder {
        public static final LoginContextHolder INSTANCE = new LoginContextHolder();
    }

    public LoginContextHolder() {
    }

    private static LoginContextHolder getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private User loginUser;

    public static boolean isLogin() {
        return LoginContextHolder.getInstance().loginUser != null;
    }

    public static User getLoginUser() {
        return LoginContextHolder.getInstance().loginUser;
    }

    public static void setLoginUser(User loginUser) {
        LoginContextHolder.getInstance().loginUser = loginUser;
    }

}
