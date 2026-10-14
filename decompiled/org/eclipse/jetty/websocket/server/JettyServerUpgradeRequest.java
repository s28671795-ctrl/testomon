/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.server;

import jakarta.servlet.http.HttpServletRequest;
import java.net.SocketAddress;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.eclipse.jetty.websocket.api.UpgradeRequest;

public interface JettyServerUpgradeRequest
extends UpgradeRequest {
    public Object getSession();

    public X509Certificate[] getCertificates();

    public HttpServletRequest getHttpServletRequest();

    public Locale getLocale();

    public Enumeration<Locale> getLocales();

    public SocketAddress getLocalSocketAddress();

    public SocketAddress getRemoteSocketAddress();

    public String getRequestPath();

    public Object getServletAttribute(String var1);

    public Map<String, Object> getServletAttributes();

    public Map<String, List<String>> getServletParameters();

    public boolean isUserInRole(String var1);

    public void setServletAttribute(String var1, Object var2);
}

