package org.step.tinder.servlet;

import lombok.extern.log4j.Log4j2;
import org.step.tinder.cookies.Cookieh;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class Logout extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{
            Cookieh.removeCookie(req,resp);
            resp.sendRedirect("/login");
        }catch (IOException ex){
            log.error("Problem with logout");
        }

    }
}
