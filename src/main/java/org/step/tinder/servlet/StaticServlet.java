package org.step.tinder.servlet;

import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticServlet extends HttpServlet {

  private final String subPath;

  public StaticServlet(String subPath) {
    this.subPath = subPath;
  }

  @SneakyThrows
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String filename = req.getPathInfo();
    URL url = this.getClass().getClassLoader().getResource(filename);
    Path path = Paths.get(url.toURI());
    try (OutputStream os = resp.getOutputStream()) {
      Files.copy(path, os);
      //Files.copy(this.getClass().getClassLoader().getResource(subPath),os);
    }
  }

}
