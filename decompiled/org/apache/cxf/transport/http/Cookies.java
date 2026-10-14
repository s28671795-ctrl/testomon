/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.transport.http.Cookie;
import org.apache.cxf.transport.http.Headers;

public class Cookies {
    private final Map<String, Cookie> sessionCookies = new ConcurrentHashMap<String, Cookie>(4, 0.75f, 4);
    private boolean maintainSession;

    public Map<String, Cookie> getSessionCookies() {
        return this.sessionCookies;
    }

    public void readFromHeaders(Headers headers) {
        List<String> c;
        if (this.maintainSession && (c = headers.headerMap().get("Set-Cookie")) != null) {
            this.handleSetCookie(c);
        }
    }

    public void writeToMessageHeaders(Message message) {
        this.maintainSession = MessageUtils.getContextualBoolean(message, Message.MAINTAIN_SESSION, false);
        if (this.maintainSession && !this.sessionCookies.isEmpty()) {
            new Headers(message).writeSessionCookies(this.sessionCookies);
        }
    }

    private void handleSetCookie(List<String> headers) {
        if (headers == null || headers.isEmpty()) {
            return;
        }
        for (String header : headers) {
            String[] cookies;
            for (String cookie : cookies = header.split(",")) {
                String[] parts = cookie.split(";");
                String[] kv = parts[0].split("=", 2);
                if (kv.length != 2) continue;
                String name = kv[0].trim();
                String value = kv[1].trim();
                Cookie newCookie = new Cookie(name, value);
                for (int i = 1; i < parts.length; ++i) {
                    kv = parts[i].split("=", 2);
                    name = kv[0].trim();
                    String string = value = kv.length > 1 ? kv[1].trim() : null;
                    if (name.equalsIgnoreCase("discard")) {
                        newCookie.setMaxAge(0);
                        continue;
                    }
                    if (name.equalsIgnoreCase("max-age") && value != null) {
                        try {
                            newCookie.setMaxAge(Integer.parseInt(value));
                        }
                        catch (NumberFormatException numberFormatException) {}
                        continue;
                    }
                    if (!name.equalsIgnoreCase("path") || value == null) continue;
                    newCookie.setPath(value);
                }
                if (newCookie.getMaxAge() != 0) {
                    this.sessionCookies.put(newCookie.getName(), newCookie);
                    continue;
                }
                this.sessionCookies.remove(newCookie.getName());
            }
        }
    }
}

