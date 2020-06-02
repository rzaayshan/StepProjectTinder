package org.step.tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.step.tinder.filter.CanLogin;
import org.step.tinder.filter.IsLogin;
import org.step.tinder.entity.TemplateEngine;
import org.step.tinder.filter.IsLoginMes;
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

        ServletContextHandler context = new ServletContextHandler();
        ResourceHandler resource = new ResourceHandler();

        TemplateEngine engine = TemplateEngine.folder("src/main/resources/content");

        resource.setResourceBase("src/main/resources/content");

        context.addServlet(new ServletHolder(new LikeServlet(engine, conn)),"/like");
        context.addServlet(new ServletHolder(new List(engine, conn)),"/list");
        context.addServlet(new ServletHolder(new Login()),"/login");
        context.addServlet(new ServletHolder(new Logout()),"/logout");
        context.addServlet(new ServletHolder(new Chat(engine, conn)),"/message");

        context.addFilter(new FilterHolder(new CanLogin(conn)),"/like", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(new IsLogin(conn)),"/like", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(new IsLogin(conn)),"/list", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(new IsLoginMes(conn)),"/message", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(new HandlerList(resource, context));

        server.start();
        server.join();


    }
}
