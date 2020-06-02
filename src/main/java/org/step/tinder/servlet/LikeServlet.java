package org.step.tinder.servlet;

import lombok.extern.log4j.Log4j2;
import org.step.tinder.DAO.DaoLikes;
import org.step.tinder.entity.Like;
import org.step.tinder.entity.TemplateEngine;
import org.step.tinder.entity.User;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;

@Log4j2
public class LikeServlet extends HttpServlet {
    private int i=0;
    private final TemplateEngine engine;
    private final DaoLikes daoLikes;
    private LinkedList<User> unlikes;
    private final Connection conn;

    public LikeServlet(TemplateEngine engine, Connection conn) {
        this.engine = engine;
        this.daoLikes=new DaoLikes(conn);
        this.conn=conn;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        HashMap<String, Object> data = new HashMap<>();
        try{
            getUnlikes(req);
            if(unlikes.isEmpty()){
                resp.sendRedirect("/list");
            }
            else{
                data = createData(req);
            }
        }catch (IOException ex){
            log.error("Problem with redirect to list");
            i=0;
            data = createData(req);
        }
        engine.render("like-page.ftl", data, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        HashMap<String,Object> data;
        try{
            addChoice(req);
            i++;
            if(isCheckedAll(unlikes)){
                i=0;
                resp.sendRedirect("/list");
            }
            data = createData(req);
        }catch (IOException ex){
            log.error("Problem with redirect to list");
            i=0;
            data = createData(req);
        }
        engine.render("like-page.ftl", data, resp);
    }

    private void getUnlikes(HttpServletRequest req){
        DaoLikes daoLikes = new DaoLikes(conn);
        String uname = req.getParameter("uname");
        unlikes = daoLikes.getLikes(uname,false);
    }


    private void addChoice(HttpServletRequest req){
        String choice = req.getParameter("choice");
        if(choice.equals("Like")){
            String who = req.getParameter("uname");
            String whom = req.getParameter("whom");
            daoLikes.put(new Like(who,whom));
        }
    }

    private boolean isCheckedAll(LinkedList<User> unlikes){
        return i>=unlikes.size();
    }

    private HashMap<String, Object> createData(HttpServletRequest req){
        HashMap<String, Object> data = new HashMap<>();
        data.put("uname",req.getParameter("uname"));
        data.put("whom",unlikes.get(i).getUname());
        data.put("image",unlikes.get(i).getImage());
        data.put("name",unlikes.get(i).getName());
        data.put("surname",unlikes.get(i).getSurname());
        return data;
    }
}
