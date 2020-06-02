package org.step.tinder.filter;

import lombok.extern.log4j.Log4j2;
import org.step.tinder.DAO.DaoUsers;
import org.step.tinder.cookies.Cookieh;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@Log4j2
public class CanLogin implements HttpPostFilter {
    private final Connection conn;

    public CanLogin(Connection conn) {
        this.conn = conn;

    }

    @Override
    public void doHttpPostFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain){
        try{
            DaoUsers users = new DaoUsers(conn);
            String uname = req.getParameter("uname");
            String pass = req.getParameter("pass");
            if(users.checkUser(uname,pass)){
                Cookieh.addCookie(resp,"uname",uname);
                Cookieh.addCookie(resp,"pass",pass);
                chain.doFilter(req,resp);
            }else{
                resp.sendRedirect("/login");
            }
        }catch (IOException | ServletException ex){
            log.error(ex.getMessage());
        }
    }

}
