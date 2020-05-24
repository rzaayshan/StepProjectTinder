package org.step.tinder.Filters;

import org.step.tinder.DAO.DaoUsers;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IsLogin implements Filter {
    DaoUsers users = new DaoUsers();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        users.connect();
        Cookie []cookies = req.getCookies();

        if(cookies!=null){
            String uname = "";
            String pass = "";
            for(Cookie c:cookies){
                if(c.getName().equals("uname"))
                    uname=c.getValue();
                else if(c.getName().equals("pass"))
                    pass=c.getValue();
            }
            if(users.checkUser(uname,pass)){
                Cookie cookie1 = new Cookie("uname",uname);
                Cookie cookie2 = new Cookie("pass",pass);
                resp.addCookie(cookie1);
                resp.addCookie(cookie2);
                chain.doFilter(req,resp);
            }
        }

        else resp.sendRedirect("/login");

    }

    @Override
    public void destroy() {

    }
}
