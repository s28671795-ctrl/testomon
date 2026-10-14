/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http_jetty;

import java.net.URL;
import org.apache.cxf.transport.http_jetty.JettyHTTPHandler;
import org.eclipse.jetty.server.Handler;

public interface ServerEngine {
    public void addServant(URL var1, JettyHTTPHandler var2);

    public void removeServant(URL var1);

    public Handler getServant(URL var1);
}

