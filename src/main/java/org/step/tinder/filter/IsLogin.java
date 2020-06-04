package org.step.tinder.filter;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.step.tinder.DAO.DaoUsers;
import org.step.tinder.cookies.Cookieh;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@Log4j2
public class IsLogin implements HttpGetFilter {
    private final Connection conn;

    public IsLogin(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void doHttpGetFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain){
        filter(req, resp, chain, conn, log);
    }

    static void filter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, Connection conn, Logger log) {
        try{
            DaoUsers daoUsers = new DaoUsers(conn);
            Optional<String> info = Cookieh.getCookie(req,"info");
            if(info.isPresent() && daoUsers.checkCookie(info.get())){
                chain.doFilter(req,resp);
            }
            else{
                resp.sendRedirect("/login"); }
        }catch (IOException | ServletException ex){
            log.error(ex.getMessage());
        }
    }
}






