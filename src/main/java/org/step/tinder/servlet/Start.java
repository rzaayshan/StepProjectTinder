package org.step.tinder.servlet;


import org.step.tinder.DAO.DaoLikes;
import org.step.tinder.entity.Profile;
import org.step.tinder.entity.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;

public class Start extends HttpServlet {
//    private final TemplateEngine engine;
//    private final Connection conn;
//    static LinkedList<Profile> unlikes;
//
//    public Start(TemplateEngine engine, Connection conn) {
//        this.conn = conn;
//        this.engine = engine;
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        getUnlikes(req);
//        if(unlikes.isEmpty()){
//            resp.sendRedirect("/list");
//        }
//        else{
//            HashMap<String, Object> data = createData();
//            engine.render2("like-page.ftl", data, resp);
//        }
//    }
//
//    private void getUnlikes(HttpServletRequest req){
//        DaoLikes daoLikes = new DaoLikes(conn);
//        String uname = req.getParameter("uname");
//        unlikes = daoLikes.getLikes(uname,false);
//    }
//
//    private HashMap<String, Object> createData(){
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("uname",unlikes.get(0).getUname());
//        data.put("image",unlikes.get(0).getImage());
//        data.put("name",unlikes.get(0).getName());
//        data.put("surname",unlikes.get(0).getSurname());
//        return data;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.sendRedirect("/login");
//    }
}
