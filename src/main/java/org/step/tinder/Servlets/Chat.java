package org.step.tinder.Servlets;

import org.step.tinder.DAO.DaoMessage;
import org.step.tinder.DAO.DaoUsers;
import org.step.tinder.Helpers.Message;
import org.step.tinder.Helpers.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Chat extends HttpServlet {
    private final TemplateEngine engine;

    public Chat(TemplateEngine engine) {
        this.engine = engine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Object> data = new HashMap<>();
        String from = Arrays.stream(req.getCookies()).filter(c->c.getName().equals("uname"))
                .map(Cookie::getValue).findFirst().get();
        DaoMessage dao = new DaoMessage();
        DaoUsers daoUsers = new DaoUsers();
        daoUsers.connect();
        String to = req.getParameter("to");

        List<Message> messages = dao.getMessages(from,to);
        data.put("messages", messages);
        data.put("to",to);
        data.put("from",from);

        data.put("image",daoUsers.getProfile(to).getImage());

        engine.render2("chat.ftl", data, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Object> data = new HashMap<>();
        DaoMessage dao = new DaoMessage();
        String mes = req.getParameter("mes");
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        dao.addMessage(from,to,mes);
        List<Message> messages = dao.getMessages(from,to);
        data.put("messages", messages);
        data.put("to",to);
        data.put("from",from);

        engine.render2("chat.ftl", data, resp);

    }
}
