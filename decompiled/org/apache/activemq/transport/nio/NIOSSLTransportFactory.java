/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.nio;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Map;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocketFactory;
import org.apache.activemq.broker.SslContext;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.nio.NIOSSLTransport;
import org.apache.activemq.transport.nio.NIOSSLTransportServer;
import org.apache.activemq.transport.nio.NIOTransportFactory;
import org.apache.activemq.transport.tcp.SslTransport;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.transport.tcp.TcpTransportServer;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.wireformat.WireFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NIOSSLTransportFactory
extends NIOTransportFactory {
    private static final Logger LOG = LoggerFactory.getLogger(NIOSSLTransportFactory.class);
    protected SSLContext context;

    @Override
    protected TcpTransportServer createTcpTransportServer(URI location, ServerSocketFactory serverSocketFactory) throws IOException, URISyntaxException {
        return new NIOSSLTransportServer(this.context, this, location, serverSocketFactory);
    }

    @Override
    public TransportServer doBind(URI location) throws IOException {
        if (SslContext.getCurrentSslContext() != null) {
            try {
                this.context = SslContext.getCurrentSslContext().getSSLContext();
            }
            catch (Exception e) {
                throw new IOException(e);
            }
        }
        return super.doBind(location);
    }

    @Override
    public Transport compositeConfigure(Transport transport, WireFormat format, Map options) {
        if (transport instanceof SslTransport) {
            SslTransport sslTransport = transport.narrow(SslTransport.class);
            IntrospectionSupport.setProperties(sslTransport, options);
        } else if (transport instanceof NIOSSLTransport) {
            NIOSSLTransport sslTransport = transport.narrow(NIOSSLTransport.class);
            IntrospectionSupport.setProperties(sslTransport, options);
        }
        return super.compositeConfigure(transport, format, options);
    }

    @Override
    protected Transport createTransport(URI location, WireFormat wf) throws UnknownHostException, IOException {
        URI localLocation = null;
        String path = location.getPath();
        if (path != null && path.length() > 0) {
            int localPortIndex = path.indexOf(58);
            try {
                Integer.parseInt(path.substring(localPortIndex + 1, path.length()));
                String localString = location.getScheme() + ":/" + path;
                localLocation = new URI(localString);
            }
            catch (Exception e) {
                LOG.warn("path isn't a valid local location for SslTransport to use", e);
            }
        }
        SocketFactory socketFactory = this.createSocketFactory();
        return new SslTransport(wf, (SSLSocketFactory)socketFactory, location, localLocation, false);
    }

    @Override
    public TcpTransport createTransport(WireFormat wireFormat, Socket socket, SSLEngine engine, TcpTransport.InitBuffer initBuffer, ByteBuffer inputBuffer) throws IOException {
        return new NIOSSLTransport(wireFormat, socket, engine, initBuffer, inputBuffer);
    }

    @Override
    protected SocketFactory createSocketFactory() throws IOException {
        if (SslContext.getCurrentSslContext() != null) {
            SslContext ctx = SslContext.getCurrentSslContext();
            try {
                return ctx.getSSLContext().getSocketFactory();
            }
            catch (Exception e) {
                throw IOExceptionSupport.create(e);
            }
        }
        return SSLSocketFactory.getDefault();
    }
}

