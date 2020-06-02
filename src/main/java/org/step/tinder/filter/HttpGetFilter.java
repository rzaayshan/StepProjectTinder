package org.step.tinder.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface HttpGetFilter extends HttpFilter{
    void doHttpGetFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;

    default void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            doHttpGetFilter(request, response, chain);
        } else {
            chain.doFilter(request, response);
        }
    }
}
