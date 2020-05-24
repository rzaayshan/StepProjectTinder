package org.step.tinder.Servlets;

import org.step.tinder.DAO.DaoLikes;
import org.step.tinder.Helpers.Profile;
import org.step.tinder.Helpers.TemplateEngine;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class List extends HttpServlet {
    private final TemplateEngine engine;

    public List(TemplateEngine engine) {
        this.engine = engine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        DaoLikes daoLikes = new DaoLikes();
        daoLikes.connect();
        String uname = Arrays.stream(req.getCookies()).filter(c->c.getName().equals("uname"))
                .map(Cookie::getValue).findFirst().get();
        LinkedList<Profile> liked = daoLikes.getLikes(uname,true);
        HashMap<String, Object> data = new HashMap<>();
        data.put("users",liked);
        engine.render2("people-list.ftl", data, resp);
    }
}
