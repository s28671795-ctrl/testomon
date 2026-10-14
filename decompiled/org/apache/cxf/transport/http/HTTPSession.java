/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpSession
 */
package org.apache.cxf.transport.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.cxf.transport.Session;

public class HTTPSession
implements Session {
    private HttpServletRequest request;
    private HttpSession session;

    public HTTPSession(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Object get(Object key) {
        return this.getSession().getAttribute(key.toString());
    }

    @Override
    public void put(Object key, Object value) {
        this.getSession().setAttribute(key.toString(), value);
    }

    public HttpSession getSession() {
        if (this.session == null) {
            this.session = this.request.getSession();
        }
        return this.session;
    }
}

