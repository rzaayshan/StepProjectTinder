package org.step.tinder.servlet;

import org.step.tinder.DAO.DaoLikes;
import org.step.tinder.entity.AddChoice;
import org.step.tinder.entity.Profile;
import org.step.tinder.entity.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Like extends HttpServlet {
    private static int i=0;
    private final TemplateEngine engine;
    private final AddChoice addChoice;

    public Like(TemplateEngine engine, Connection conn) {
        this.engine = engine;
        this.addChoice=new AddChoice(new DaoLikes(conn));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LinkedList<Profile> unlikes = Start.unlikes;
        HashMap<String, String> user = new HashMap<>();
        String whom = req.getParameter("uname");
        String who = Arrays.stream(req.getCookies()).filter(c->c.getName().equals("uname"))
                .map(Cookie::getValue).findFirst().get();
        String choice = req.getParameter("choice");
        addChoice.add(who,whom,choice);
        if(i>=unlikes.size()){
            i=0;
            resp.sendRedirect("/list");
        }
        user.put("uname",unlikes.get(i).getUname());
        user.put("image",unlikes.get(i).getImage());
        user.put("name",unlikes.get(i).getName());
        user.put("surname",unlikes.get(i).getSurname());


        engine.render("like-page.ftl", user, resp);


    }



}
