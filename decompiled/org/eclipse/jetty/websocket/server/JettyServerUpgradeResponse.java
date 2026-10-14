/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.server;

import java.io.IOException;
import java.util.List;
import org.eclipse.jetty.websocket.api.ExtensionConfig;
import org.eclipse.jetty.websocket.api.UpgradeResponse;

public interface JettyServerUpgradeResponse
extends UpgradeResponse {
    public void addHeader(String var1, String var2);

    public void setHeader(String var1, String var2);

    public void setHeader(String var1, List<String> var2);

    public void sendForbidden(String var1) throws IOException;

    public void sendError(int var1, String var2) throws IOException;

    public void setAcceptedSubProtocol(String var1);

    public void setExtensions(List<ExtensionConfig> var1);

    public void setStatusCode(int var1);

    public boolean isCommitted();
}

