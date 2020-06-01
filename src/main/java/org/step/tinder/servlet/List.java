package org.step.tinder.servlet;

import org.step.tinder.DAO.DaoLikes;
import org.step.tinder.entity.TemplateEngine;
import org.step.tinder.entity.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class List extends HttpServlet {
    private final TemplateEngine engine;
    private final Connection conn;

    public List(TemplateEngine engine, Connection conn) {
        this.engine = engine;
        this.conn = conn;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        DaoLikes daoLikes = new DaoLikes(conn);
        String uname = Arrays.stream(req.getCookies()).filter(c-> URLDecoder.decode(c.getName()).equals("uname"))
                .map(c->URLDecoder.decode(c.getValue())).findFirst().get();
        LinkedList<User> liked = daoLikes.getLikes(uname,true);
        HashMap<String, Object> data = new HashMap<>();
        data.put("users",liked);
        engine.render("people-list.ftl", data, resp);
    }
}
