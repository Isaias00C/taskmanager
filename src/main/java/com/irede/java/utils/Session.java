package com.irede.java.utils;

import com.irede.java.models.User;

public final class Session {
    private static User currentUser;

    private Session() {}

    public static User getCurrentUser() { return currentUser; }
    public static void setCurrentUser(User user) { currentUser = user; }
    public static void clear() { currentUser = null; }
}
