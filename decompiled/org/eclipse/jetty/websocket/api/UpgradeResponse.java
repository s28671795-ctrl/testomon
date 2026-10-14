/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jetty.websocket.api.ExtensionConfig;

public interface UpgradeResponse {
    public String getAcceptedSubProtocol();

    public List<ExtensionConfig> getExtensions();

    public String getHeader(String var1);

    public Set<String> getHeaderNames();

    public Map<String, List<String>> getHeaders();

    public List<String> getHeaders(String var1);

    public int getStatusCode();
}

