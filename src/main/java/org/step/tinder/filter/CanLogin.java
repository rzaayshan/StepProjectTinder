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
            String cookie1n = Crip.encode("uname");
            String cookie1v = Crip.encode(uname);
            String cookie2n = Crip.encode("pass");
            String cookie2v = Crip.encode(pass);
            Cookie cookie1 = new Cookie(cookie1n,cookie1v);
            Cookie cookie2 = new Cookie(cookie2n,cookie2v);
            resp.addCookie(cookie1);
            resp.addCookie(cookie2);
            chain.doFilter(req,resp);
        }else{
            resp.sendRedirect("/login");
        }

    }

}
