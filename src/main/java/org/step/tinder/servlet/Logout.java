package org.step.tinder.servlet;

import org.step.tinder.cookies.Cookieh;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Logout extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookieh.removeCookie(req,resp);
        resp.sendRedirect("/login");
    }
}
