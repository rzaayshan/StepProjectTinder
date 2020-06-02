package org.step.tinder.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

interface HttpPostFilter extends HttpFilter {
  void doHttpPostFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;

  default void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (request.getMethod().equalsIgnoreCase("POST")) {
      doHttpPostFilter(request, response, chain);
    } else {
      chain.doFilter(request, response);
    }
  }
}
