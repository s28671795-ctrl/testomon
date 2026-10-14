/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

import java.net.HttpCookie;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.websocket.api.ExtensionConfig;

public interface UpgradeRequest {
    public List<HttpCookie> getCookies();

    public List<ExtensionConfig> getExtensions();

    public String getHeader(String var1);

    public int getHeaderInt(String var1);

    public Map<String, List<String>> getHeaders();

    public List<String> getHeaders(String var1);

    public String getHost();

    public String getHttpVersion();

    public String getMethod();

    public String getOrigin();

    public Map<String, List<String>> getParameterMap();

    public String getProtocolVersion();

    public String getQueryString();

    public URI getRequestURI();

    public List<String> getSubProtocols();

    public Principal getUserPrincipal();

    public boolean hasSubProtocol(String var1);

    public boolean isSecure();
}

