package org.step.tinder.filter;

import org.step.tinder.DAO.DaoUsers;
import org.step.tinder.entity.Crip;

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
                    uname= Crip.de(c.getValue());
                else if(c.getName().equals("pass"))
                    pass=Crip.de(c.getValue());
            }
            if(users.checkUser(uname,pass)){
                chain.doFilter(req,resp);
            }
        } else{
            resp.sendRedirect("/login");
        }

    }
}






