package org.step.tinder.Filters;

import org.step.tinder.DAO.DaoUsers;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CanLogin implements Filter {
    DaoUsers users = new DaoUsers();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uname = req.getParameter("uname");
        String pass = req.getParameter("pass");
        users.connect();
        if(users.checkUser(uname,pass)){
            Cookie cookie1 = new Cookie("uname",uname);
            Cookie cookie2 = new Cookie("pass",pass);
            resp.addCookie(cookie1);
            resp.addCookie(cookie2);
            chain.doFilter(req,resp);
        }
        else resp.sendRedirect("/login");

    }

    @Override
    public void destroy() {

    }
}
