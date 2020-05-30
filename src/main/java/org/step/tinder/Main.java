package org.step.tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.step.tinder.Filters.CanLogin;
import org.step.tinder.Filters.IsLogin;
import org.step.tinder.entity.TemplateEngine;
import org.step.tinder.Servlets.*;
import org.step.tinder.db.DbConn;
import org.step.tinder.db.DbSetup;
import org.step.tinder.heroku.HerokuEnv;

import javax.servlet.DispatcherType;
import java.sql.Connection;
import java.util.EnumSet;


public class Main {
    public static void main(String[] args) throws Exception {

        DbSetup.migrate(HerokuEnv.jdbc_url(),HerokuEnv.jdbc_username(),HerokuEnv.jdbc_password());
        Connection conn = DbConn.create(HerokuEnv.jdbc_url(),HerokuEnv.jdbc_username(),HerokuEnv.jdbc_password());

        Server server = new Server(HerokuEnv.port());

        ServletContextHandler handler = new ServletContextHandler();

        TemplateEngine engine = TemplateEngine.folder("content");

        handler.addServlet(new ServletHolder(new Start(engine, conn)),"/start");
        handler.addServlet(new ServletHolder(new Like(engine)),"/users");
        handler.addServlet(new ServletHolder(new Choice(conn)),"/choice");
        handler.addServlet(new ServletHolder(new List(engine, conn)),"/liked");
        handler.addServlet(new ServletHolder(new Login()),"/login");
        handler.addServlet(new ServletHolder(new Login()),"/");
        handler.addServlet(new ServletHolder(new Logout()),"/logout");
        handler.addServlet(new ServletHolder(new Chat(engine, conn)),"/message");
        handler.addServlet(new ServletHolder(new StaticServlet("css")), "/css/*");
        handler.addFilter(new FilterHolder(new CanLogin(conn)),"/start", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new IsLogin(conn)),"/users", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new IsLogin(conn)),"/liked", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new IsLogin(conn)),"/choice", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new IsLogin(conn)),"/message", EnumSet.of(DispatcherType.REQUEST));



        server.setHandler(handler);

        server.start();
        server.join();


    }
}
