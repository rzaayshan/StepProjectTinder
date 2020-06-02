package org.step.tinder.db;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
public class DbConn {
    @SneakyThrows
    public static Connection create(String url, String username, String password){
        try {
            return DriverManager.getConnection(url,username,password);
        } catch (SQLException throwables) {
            log.error("Problem with connect");
            throw new SQLException();
        }
    }
}
