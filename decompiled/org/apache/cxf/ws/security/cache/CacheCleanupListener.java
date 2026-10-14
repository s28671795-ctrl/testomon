/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.cache;

import java.io.Closeable;
import java.io.IOException;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientLifeCycleListener;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.ServerLifeCycleListener;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.wss4j.common.cache.ReplayCache;

public class CacheCleanupListener
implements ServerLifeCycleListener,
ClientLifeCycleListener {
    @Override
    public void clientCreated(Client client) {
    }

    @Override
    public void startServer(Server server) {
    }

    @Override
    public void clientDestroyed(Client client) {
        this.shutdownResources(client.getEndpoint().getEndpointInfo());
    }

    @Override
    public void stopServer(Server server) {
        this.shutdownResources(server.getEndpoint().getEndpointInfo());
    }

    protected void shutdownResources(EndpointInfo info) {
        ReplayCache rc;
        TokenStore ts = (TokenStore)info.getProperty("org.apache.cxf.ws.security.tokenstore.TokenStore");
        if (ts instanceof Closeable) {
            this.close((Closeable)((Object)ts));
        }
        if ((rc = (ReplayCache)info.getProperty("ws-security.nonce.cache.instance")) != null) {
            this.close(rc);
        }
        if ((rc = (ReplayCache)info.getProperty("ws-security.timestamp.cache.instance")) != null) {
            this.close(rc);
        }
        if ((rc = (ReplayCache)info.getProperty("ws-security.saml.cache.instance")) != null) {
            this.close(rc);
        }
    }

    private void close(Closeable ts) {
        try {
            ts.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }
}

