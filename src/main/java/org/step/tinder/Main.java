package org.step.tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.step.tinder.filter.CanLogin;
import org.step.tinder.filter.IsLogin;
import org.step.tinder.entity.TemplateEngine;
import org.step.tinder.servlet.*;
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

        //handler.addServlet(new ServletHolder(new Start(engine, conn)),"dd7nlooejjena8@ec2-35-171-31-33.compute-1.amazonaws.com/start");
        handler.addServlet(new ServletHolder(new Like(engine, conn)),"/like");
        handler.addServlet(new ServletHolder(new List(engine, conn)),"/list");
        handler.addServlet(new ServletHolder(new Login()),"/login");
        handler.addServlet(new ServletHolder(new Login()),"/");
        handler.addServlet(new ServletHolder(new Logout()),"/logout");
        handler.addServlet(new ServletHolder(new Chat(engine, conn)),"/message");
        handler.addServlet(new ServletHolder(new StaticServlet("css")), "/css/*");
        handler.addFilter(new FilterHolder(new CanLogin(conn)),"/like", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new IsLogin(conn)),"/like", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new IsLogin(conn)),"/list", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new IsLogin(conn)),"/message", EnumSet.of(DispatcherType.REQUEST));



        server.setHandler(handler);

        server.start();
        server.join();


    }
}
