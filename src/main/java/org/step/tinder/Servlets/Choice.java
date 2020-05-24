package org.step.tinder.Servlets;

import org.step.tinder.DAO.DaoLikes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class Choice extends HttpServlet {
    static int i=0;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DaoLikes daoLikes = new DaoLikes();
        daoLikes.connect();
        String whom = req.getParameter("uname");
        String who = Arrays.stream(req.getCookies()).filter(c->c.getName().equals("uname"))
                .map(Cookie::getValue).findFirst().get();
        if(req.getParameter("choice").equals("Like"))
            daoLikes.addLikes(who,whom);
        req.setAttribute("i",++i);
        RequestDispatcher r = req.getRequestDispatcher("/users");
        r.forward(req,resp);
    }



}
