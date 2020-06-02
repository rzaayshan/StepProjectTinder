package org.step.tinder.filter;

import lombok.extern.log4j.Log4j2;
import org.step.tinder.DAO.DaoUsers;
import org.step.tinder.cookies.Cookieh;
import org.step.tinder.cookies.Crip;

import javax.servlet.*;
import javax.servlet.http.Cookie;
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
        try{
            DaoUsers users = new DaoUsers(conn);
            Optional<String> uname = Cookieh.getCookie(req,"uname");
            Optional<String> pass = Cookieh.getCookie(req,"pass");
            if(uname.isPresent() && pass.isPresent()){
                if(users.checkUser(uname.get(),pass.get())){
                    chain.doFilter(req,resp);
                }
            } else{
                resp.sendRedirect("/login");
            }
        }catch (IOException | ServletException ex){
            log.error(ex.getMessage());
        }
    }
}






