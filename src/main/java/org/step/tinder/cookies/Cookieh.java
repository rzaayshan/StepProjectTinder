package org.step.tinder.cookies;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

public class Cookieh {
    public static void addCookie(HttpServletResponse resp, String name, String value){
        Cookie cookie = new Cookie(name,Crip.en(value));
        cookie.setMaxAge(60*60*24*7);
        resp.addCookie(cookie);
    }

    public static Optional<String> getCookie(HttpServletRequest req, String name){
        Cookie []cookies = req.getCookies();
        return Arrays.stream(cookies).filter(c->c.getName().equals(name))
                .map(c->Crip.de(c.getValue())).findFirst();
    }

    public static void removeCookie(HttpServletRequest req, HttpServletResponse resp){
        Cookie []cookies = req.getCookies();
        Arrays.stream(cookies)
                .forEach(c -> {
                    c.setMaxAge(0);
                    resp.addCookie(c);
                });
    }
}
