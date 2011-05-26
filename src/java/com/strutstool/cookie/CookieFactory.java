package com.strutstool.cookie;

/**
 *
 * @author maycon
 */
public interface CookieFactory {
    public void setCookie(String name, String value);
    public void setCookie(String name, String value, String path);
    public void setCookie(String name, String value, int maxAge);
    public void setCookie(String name, String value, int maxAge, String path);
    public String getCookie(String name);
    public void removeCookie(String name);
}
