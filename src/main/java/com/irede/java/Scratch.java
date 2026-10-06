package com.irede.java;

import com.irede.java.config.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Scratch {
    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.conect()){
            System.out.println("Conectou: " + !conn.isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
