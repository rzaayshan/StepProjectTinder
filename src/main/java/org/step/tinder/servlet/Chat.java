package org.step.tinder.servlet;

import lombok.extern.log4j.Log4j2;
import org.step.tinder.DAO.DaoMessage;
import org.step.tinder.DAO.DaoUsers;
import org.step.tinder.entity.Message;
import org.step.tinder.entity.TemplateEngine;
import org.step.tinder.entity.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Log4j2
public class Chat extends HttpServlet {
    private final TemplateEngine engine;
    private final DaoMessage daoMessage;
    private final DaoUsers daoUsers;

    public Chat(TemplateEngine engine, Connection conn) {
        this.engine = engine;
        daoMessage = new DaoMessage(conn);
        daoUsers = new DaoUsers(conn);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        String to = req.getParameter("to");
        if(daoUsers.get(to).isPresent()){
            HashMap<String, Object> data = createData(req);
            engine.render("chat.ftl", data, resp);
        }
        else {
            try {
                resp.sendRedirect("/list");
            } catch (IOException exception) {
                log.error(exception.getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        String to = req.getParameter("to");
        if(daoUsers.get(to).isPresent()) {
            addMessage(req);
            HashMap<String, Object> data = createData(req);
            engine.render("chat.ftl", data, resp);
        }
        else {
            try {
                resp.sendRedirect("/list");
            } catch (IOException exception) {
                log.error(exception.getMessage());
            }
        }
    }

    private HashMap<String, Object> createData(HttpServletRequest req){
        String from = (String) req.getSession().getAttribute("uname");
        String to = req.getParameter("to");
        List<Message> messages = daoMessage.getMessages(from,to);
        String image = daoUsers.get(to).get().getImage();

        HashMap<String, Object> data = new HashMap<>();

        data.put("messages", messages);
        data.put("to",to);
        data.put("from",from);
        data.put("image",image);

        return data;
    }

    private void addMessage(HttpServletRequest req){
        String from = (String) req.getSession().getAttribute("uname");
        String to = req.getParameter("to");
        String mes = req.getParameter("mes");
        daoMessage.put(new Message(from,to,LocalDateTime.now(),mes));
    }
}
