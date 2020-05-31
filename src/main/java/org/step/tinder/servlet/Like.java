package org.step.tinder.servlet;

import org.step.tinder.DAO.DaoLikes;
import org.step.tinder.entity.Profile;
import org.step.tinder.entity.TemplateEngine;

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
    private final DaoLikes daoLikes;

    public Like(TemplateEngine engine, Connection conn) {
        this.engine = engine;
        this.daoLikes=new DaoLikes(conn);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addChoice(req);
        if(isCheckedAll(Start.unlikes)){
            i=0;
            resp.sendRedirect("/list");
        }
        HashMap<String,Object> data = createData(Start.unlikes);
        engine.render2("like-page.ftl", data, resp);
    }

    private void addChoice(HttpServletRequest req){
        String whom = req.getParameter("uname");
        String who = Arrays.stream(req.getCookies()).filter(c->c.getName().equals("uname"))
                .map(Cookie::getValue).findFirst().get();
        String choice = req.getParameter("choice");
        if(choice.equals("Like"))
            daoLikes.addLikes(who,whom);
    }

    private boolean isCheckedAll(LinkedList<Profile> unlikes){
        return ++i>=unlikes.size();
    }

    private HashMap<String, Object> createData(LinkedList<Profile> unlikes){
        HashMap<String, Object> data = new HashMap<>();
        data.put("uname",unlikes.get(i).getUname());
        data.put("image",unlikes.get(i).getImage());
        data.put("name",unlikes.get(i).getName());
        data.put("surname",unlikes.get(i).getSurname());
        return data;
    }



}
