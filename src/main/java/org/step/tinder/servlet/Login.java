package org.step.tinder.servlet;

import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Log4j2
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try{
            OutputStream os = resp.getOutputStream();
            Files.copy(Paths.get("src","main","resources","content","index.html"),os);

        }catch (IOException ex){
            log.error("Problem with login page");
        }

    }
}
