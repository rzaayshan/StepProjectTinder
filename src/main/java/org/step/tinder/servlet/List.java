package org.step.tinder.servlet;

import org.step.tinder.DAO.DaoLikes;
import org.step.tinder.entity.Crip;
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
import java.util.Optional;

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
        HashMap<String, Object> data = new HashMap<>();
        Optional<String> op = Arrays.stream(req.getCookies()).filter(c -> c.getName().equals("uname"))
                .map(c -> Crip.decode(c.getValue())).findFirst();
        if(op.isPresent()){
            String uname = op.get();
            LinkedList<User> liked = daoLikes.getLikes(uname,true);
            data.put("users",liked);
        }
        engine.render("people-list.ftl", data, resp);
    }
}
