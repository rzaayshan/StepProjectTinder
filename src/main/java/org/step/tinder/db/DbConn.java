package org.step.tinder.db;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConn {
    @SneakyThrows
    public static Connection create(String url) {
        return DriverManager.getConnection(url);
    }
}
