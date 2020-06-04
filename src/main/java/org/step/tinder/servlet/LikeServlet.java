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
        HashMap<String, Object> data;
        try{
            getUnlikes(req);
            if(unlikes.isEmpty()){
                resp.sendRedirect("/list");
            }
            data = createData(unlikes.get(i));
            engine.render("like-page.ftl", data, resp);
        }catch (IOException ex){
            log.error("Problem with redirect to list");
            data = createData(unlikes.get(i));
            engine.render("like-page.ftl", data, resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        HashMap<String,Object> data;
        try{
            addChoice(req);
            i++;
            if(isCheckedAll()){
                i=0;
                resp.sendRedirect("/list");
            }
            data = createData(unlikes.get(i));
            engine.render("like-page.ftl", data, resp);
        }catch (IOException ex){
            log.error("Problem with redirect to list");
            i=0;
            data = createData(unlikes.get(i));
            engine.render("like-page.ftl", data, resp);
        }
    }
    private boolean isCheckedAll(){
        return i>=unlikes.size();
    }

    private void getUnlikes(HttpServletRequest req){
        DaoLikes daoLikes = new DaoLikes(conn);
        String uname = req.getParameter("uname");
        unlikes = daoLikes.getLikes(uname,false);
    }


    private void addChoice(HttpServletRequest req){
        String choice = req.getParameter("choice");
        if(choice.equals("Like")){
            String who = (String) req.getSession().getAttribute("uname");
            String whom = req.getParameter("whom");
            daoLikes.put(new Like(who,whom));
        }
    }

    private HashMap<String, Object> createData(User user){
        HashMap<String, Object> data = new HashMap<>();
        data.put("whom",user.getUname());
        data.put("image",user.getImage());
        data.put("name",user.getName());
        data.put("surname",user.getSurname());
        return data;
    }
}
