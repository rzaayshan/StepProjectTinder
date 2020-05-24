package org.step.tinder.Servlets;


import org.step.tinder.DAO.DaoLikes;
import org.step.tinder.Helpers.Profile;
import org.step.tinder.Helpers.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class Start extends HttpServlet {
    private final TemplateEngine engine;
    static LinkedList<Profile> unlikes;

    public Start(TemplateEngine engine) {
        DaoLikes daoLikes = new DaoLikes();
        daoLikes.connect();
        this.engine = engine;
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DaoLikes daoLikes = new DaoLikes();
        daoLikes.connect();
        String uname = req.getParameter("uname");
        unlikes = daoLikes.getLikes(uname,false);
        if(unlikes.isEmpty()){
            resp.sendRedirect("/liked");
        }
        else{
            HashMap<String, String> user = new HashMap<>();
            user.put("uname",unlikes.get(0).getUname());
            user.put("image",unlikes.get(0).getImage());
            user.put("name",unlikes.get(0).getName());
            user.put("surname",unlikes.get(0).getSurname());


            engine.render("like-page.ftl", user, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/login");
    }
}
