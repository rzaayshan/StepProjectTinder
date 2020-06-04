package org.step.tinder.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface HttpFilter extends Filter {

    default void init(FilterConfig filterConfig){}

    void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException;

    default void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
        if(servletRequest instanceof HttpServletRequest){
            doHttpFilter((HttpServletRequest) servletRequest,(HttpServletResponse) servletResponse,filterChain);
        }
        else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }


    default void destroy(){}
}
