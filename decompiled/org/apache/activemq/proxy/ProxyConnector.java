/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.proxy;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.activemq.Service;
import org.apache.activemq.proxy.ProxyConnection;
import org.apache.activemq.transport.CompositeTransport;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportAcceptListener;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.transport.TransportFilter;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.util.ServiceStopper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyConnector
implements Service {
    private static final Logger LOG = LoggerFactory.getLogger(ProxyConnector.class);
    private TransportServer server;
    private URI bind;
    private URI remote;
    private URI localUri;
    private String name;
    private boolean proxyToLocalBroker = true;
    private final CopyOnWriteArrayList<ProxyConnection> connections = new CopyOnWriteArrayList();

    @Override
    public void start() throws Exception {
        this.getServer().setAcceptListener(new TransportAcceptListener(){

            @Override
            public void onAccept(Transport localTransport) {
                ProxyConnection connection = null;
                try {
                    Transport remoteTransport = ProxyConnector.this.createRemoteTransport(localTransport);
                    connection = new ProxyConnection(localTransport, remoteTransport);
                    connection.start();
                    ProxyConnector.this.connections.add(connection);
                }
                catch (Exception e) {
                    this.onAcceptError(e);
                    try {
                        if (connection != null) {
                            connection.stop();
                        }
                    }
                    catch (Exception eoc) {
                        LOG.error("Could not close broken connection: ", eoc);
                    }
                }
            }

            @Override
            public void onAcceptError(Exception error) {
                LOG.error("Could not accept connection: ", error);
            }
        });
        this.getServer().start();
        LOG.info("Proxy Connector {} started", (Object)this.getName());
    }

    @Override
    public void stop() throws Exception {
        ServiceStopper ss = new ServiceStopper();
        if (this.server != null) {
            ss.stop(this.server);
        }
        Iterator<ProxyConnection> iter = this.connections.iterator();
        while (iter.hasNext()) {
            LOG.info("Connector stopped: Stopping proxy.");
            ss.stop(iter.next());
        }
        this.connections.clear();
        ss.throwFirstException();
        LOG.info("Proxy Connector {} stopped", (Object)this.getName());
    }

    public URI getLocalUri() {
        return this.localUri;
    }

    public void setLocalUri(URI localURI) {
        this.localUri = localURI;
    }

    public URI getBind() {
        return this.bind;
    }

    public void setBind(URI bind) {
        this.bind = bind;
    }

    public URI getRemote() {
        return this.remote;
    }

    public void setRemote(URI remote) {
        this.remote = remote;
    }

    public TransportServer getServer() throws IOException, URISyntaxException {
        if (this.server == null) {
            this.server = this.createServer();
        }
        return this.server;
    }

    public void setServer(TransportServer server) {
        this.server = server;
    }

    protected TransportServer createServer() throws IOException, URISyntaxException {
        if (this.bind == null) {
            throw new IllegalArgumentException("You must specify either a server or the bind property");
        }
        return TransportFactory.bind(this.bind);
    }

    private Transport createRemoteTransport(final Transport local) throws Exception {
        Transport transport = TransportFactory.compositeConnect(this.remote);
        CompositeTransport ct = transport.narrow(CompositeTransport.class);
        if (ct != null && this.localUri != null && this.proxyToLocalBroker) {
            ct.add(false, new URI[]{this.localUri});
        }
        transport = new TransportFilter(transport){

            @Override
            public void stop() throws Exception {
                LOG.info("Stopping proxy.");
                super.stop();
                ProxyConnection dummy = new ProxyConnection(local, this);
                LOG.debug("Removing proxyConnection {}", (Object)dummy.toString());
                ProxyConnector.this.connections.remove(dummy);
            }
        };
        return transport;
    }

    public String getName() {
        if (this.name == null) {
            this.name = this.server != null ? this.server.getConnectURI().toString() : "proxy";
        }
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isProxyToLocalBroker() {
        return this.proxyToLocalBroker;
    }

    public void setProxyToLocalBroker(boolean proxyToLocalBroker) {
        this.proxyToLocalBroker = proxyToLocalBroker;
    }

    protected Integer getConnectionCount() {
        return this.connections.size();
    }
}

