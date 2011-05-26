package com.strutstool.session;

/**
 *
 * @author maycon
 */
public interface Session {
    public void setAttribute(String name, Object value);
    public Object getAttribute(String name);
    public void removeAttribute(String name);
}
