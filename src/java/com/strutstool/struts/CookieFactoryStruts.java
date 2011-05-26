package com.strutstool.struts;

import com.strutstool.cookie.CookieFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author maycon
 */
public class CookieFactoryStruts implements CookieFactory {
    private HttpServletRequest request;
    private HttpServletResponse response;

    public CookieFactoryStruts() {}

    public CookieFactoryStruts(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void setCookie(String name, String value) {
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(name, value);
        response.addCookie(cookie);
    }

    public void setCookie(String name, String value, int maxAge) {
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(name, value);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public void setCookie(String name, String value, String path) {
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(name, value);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    public void setCookie(String name, String value, int maxAge, String path) {
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    public String getCookie(String name) {
        javax.servlet.http.Cookie cookie = getCookieObject(name);

        if (cookie == null) {
            return null;
        } else {
            return cookie.getValue();
        }
    }

    protected javax.servlet.http.Cookie getCookieObject(String name) {
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(name)) {
                    return cookies[i];
                }
            }
        }
        return null;
    }

    public void removeCookie(String name) {
        javax.servlet.http.Cookie cookie = getCookieObject(name);
        cookie.setMaxAge(0);
        cookie.setValue("");
        response.addCookie(cookie);
    }
}
