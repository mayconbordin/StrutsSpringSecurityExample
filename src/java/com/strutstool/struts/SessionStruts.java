package com.strutstool.struts;

import com.strutstool.session.Session;
import java.util.Map;

/**
 *
 * @author maycon
 */
public class SessionStruts implements Session {
    private Map<String, Object> session;

    public SessionStruts(Map<String, Object> session) {
        this.session = session;
    }

    public void setAttribute(String name, Object value) {
        session.put(name, value);
    }

    public Object getAttribute(String name) {
        return session.get(name);
    }

    public void removeAttribute(String name) {
        session.remove(name);
    }

}