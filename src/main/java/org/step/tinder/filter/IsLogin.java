package org.step.tinder.filter;

import org.step.tinder.DAO.DaoUsers;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

public class IsLogin implements HttpFilter {
    private final Connection conn;

    public IsLogin(Connection conn) {
        this.conn = conn;
    }



    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        DaoUsers users = new DaoUsers(conn);

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
        } else{
            resp.sendRedirect("/login");
        }

    }
}






