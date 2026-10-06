package com.irede.java.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String url = "jdbc:mysql://localhost:3306/taskmanager";
    private static final String user = "admin";
    private static final String password = "admin";

    public static Connection conect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}
