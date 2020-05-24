package org.step.tinder.Servlets;

import org.step.tinder.Helpers.Profile;
import org.step.tinder.Helpers.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class Like extends HttpServlet {
    private final TemplateEngine engine;

    public Like(TemplateEngine engine) {
        this.engine = engine;

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LinkedList<Profile> unlikes = Start.unlikes;
        HashMap<String, String> user = new HashMap<>();
        int i = (int)req.getAttribute("i");
        if(i>=unlikes.size()){
            i=0;
            Choice.i=0;
            resp.sendRedirect("/liked");
        }

        user.put("uname",unlikes.get(i).getUname());
        user.put("image",unlikes.get(i).getImage());
        user.put("name",unlikes.get(i).getName());
        user.put("surname",unlikes.get(i).getSurname());


        engine.render("like-page.ftl", user, resp);


    }



}
