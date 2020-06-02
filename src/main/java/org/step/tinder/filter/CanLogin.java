package org.step.tinder.filter;

import org.step.tinder.DAO.DaoUsers;
import org.step.tinder.entity.Crip;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.net.URLEncoder;
import java.util.Base64;

public class CanLogin implements HttpFilter {
    private final Connection conn;

    public CanLogin(Connection conn) {
        this.conn = conn;

    }

    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        DaoUsers users = new DaoUsers(conn);
        String uname = req.getParameter("uname");
        String pass = req.getParameter("pass");
        if(users.checkUser(uname,pass)){
            Cookie cookie1 = new Cookie("uname",Crip.en(uname));
            Cookie cookie2 = new Cookie("pass",Crip.en(pass));
            resp.addCookie(cookie1);
            resp.addCookie(cookie2);
            chain.doFilter(req,resp);
        }else{
            resp.sendRedirect("/login");
        }

    }

}
