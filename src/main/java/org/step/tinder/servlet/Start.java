package org.step.tinder.servlet;


import org.step.tinder.DAO.DaoLikes;
import org.step.tinder.entity.TemplateEngine;
import org.step.tinder.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;

public class Start extends HttpServlet {
    private final TemplateEngine engine;
    private final Connection conn;
    static LinkedList<User> unlikes;

    public Start(TemplateEngine engine, Connection conn) {
        this.conn = conn;
        this.engine = engine;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        getUnlikes(req);
        if(unlikes.isEmpty()){
            resp.sendRedirect("/list");
        }
        else{
            HashMap<String, Object> data = createData(req);
            engine.render("like-page.ftl", data, resp);
        }
    }

    private void getUnlikes(HttpServletRequest req){
        DaoLikes daoLikes = new DaoLikes(conn);
        String uname = req.getParameter("uname");
        unlikes = daoLikes.getLikes(uname,false);
    }

    private HashMap<String, Object> createData(HttpServletRequest req){
        HashMap<String, Object> data = new HashMap<>();
        data.put("uname", req.getParameter("uname"));
        data.put("whom",unlikes.get(0).getUname());
        data.put("image",unlikes.get(0).getImage());
        data.put("name",unlikes.get(0).getName());
        data.put("surname",unlikes.get(0).getSurname());
        return data;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/login");
    }
}
