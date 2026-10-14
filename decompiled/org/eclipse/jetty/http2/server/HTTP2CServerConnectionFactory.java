/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.server;

import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.server.HTTP2ServerConnection;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.util.annotation.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTP2CServerConnectionFactory
extends HTTP2ServerConnectionFactory
implements ConnectionFactory.Upgrading {
    private static final Logger LOG = LoggerFactory.getLogger(HTTP2CServerConnectionFactory.class);

    public HTTP2CServerConnectionFactory(@Name(value="config") HttpConfiguration httpConfiguration) {
        this(httpConfiguration, "h2c");
    }

    public HTTP2CServerConnectionFactory(@Name(value="config") HttpConfiguration httpConfiguration, String ... protocols) {
        super(httpConfiguration, protocols);
        for (String p : protocols) {
            if (HTTP2ServerConnection.isSupportedProtocol(p)) continue;
            throw new IllegalArgumentException("Unsupported HTTP2 Protocol variant: " + p);
        }
    }

    @Override
    public boolean isAcceptable(String protocol, String tlsProtocol, String tlsCipher) {
        return false;
    }

    @Override
    public Connection upgradeConnection(Connector connector, EndPoint endPoint, MetaData.Request request, HttpFields.Mutable response101) throws BadMessageException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} upgrading {}{}{}", this, request, System.lineSeparator(), request.getFields());
        }
        if (request.getContentLength() > 0L) {
            return null;
        }
        HTTP2ServerConnection connection = (HTTP2ServerConnection)this.newConnection(connector, endPoint);
        if (connection.upgrade(request, response101)) {
            return connection;
        }
        return null;
    }
}

