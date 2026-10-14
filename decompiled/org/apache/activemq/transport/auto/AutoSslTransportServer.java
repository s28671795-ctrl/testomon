/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.auto;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.transport.auto.AutoTcpTransportServer;
import org.apache.activemq.transport.tcp.SslTransportFactory;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.transport.tcp.TcpTransportFactory;
import org.apache.activemq.wireformat.WireFormat;

public class AutoSslTransportServer
extends AutoTcpTransportServer {
    private boolean needClientAuth;
    private boolean wantClientAuth;

    public AutoSslTransportServer(SslTransportFactory transportFactory, URI location, SSLServerSocketFactory serverSocketFactory, BrokerService brokerService, Set<String> enabledProtocols) throws IOException, URISyntaxException {
        super(transportFactory, location, serverSocketFactory, brokerService, enabledProtocols);
    }

    public void setNeedClientAuth(boolean needAuth) {
        this.needClientAuth = needAuth;
    }

    public boolean getNeedClientAuth() {
        return this.needClientAuth;
    }

    public boolean getWantClientAuth() {
        return this.wantClientAuth;
    }

    public void setWantClientAuth(boolean wantAuth) {
        this.wantClientAuth = wantAuth;
    }

    @Override
    public void bind() throws IOException {
        super.bind();
        if (this.needClientAuth) {
            ((SSLServerSocket)this.serverSocket).setNeedClientAuth(true);
        } else if (this.wantClientAuth) {
            ((SSLServerSocket)this.serverSocket).setWantClientAuth(true);
        }
    }

    @Override
    protected TcpTransport createTransport(Socket socket, WireFormat format, TcpTransportFactory detectedTransportFactory, TcpTransport.InitBuffer initBuffer) throws IOException {
        return detectedTransportFactory.createTransport(format, socket, initBuffer);
    }

    @Override
    public boolean isSslServer() {
        return true;
    }
}

