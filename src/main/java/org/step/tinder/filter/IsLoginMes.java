package org.step.tinder.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;


@Log4j2
public class IsLoginMes implements HttpFilter {
    private final Connection conn;

    public IsLoginMes(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain){
        IsLogin.filter(req, resp, chain, conn, log);
    }
}
