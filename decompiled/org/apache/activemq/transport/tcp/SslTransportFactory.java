/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.tcp;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.apache.activemq.broker.SslContext;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.tcp.SslTransport;
import org.apache.activemq.transport.tcp.SslTransportServer;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.transport.tcp.TcpTransportFactory;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;
import org.apache.activemq.wireformat.WireFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SslTransportFactory
extends TcpTransportFactory {
    private static final Logger LOG = LoggerFactory.getLogger(SslTransportFactory.class);

    @Override
    public TransportServer doBind(URI location) throws IOException {
        try {
            HashMap<String, String> options = new HashMap<String, String>(URISupport.parseParameters(location));
            ServerSocketFactory serverSocketFactory = this.createServerSocketFactory();
            SslTransportServer server = this.createSslTransportServer(location, (SSLServerSocketFactory)serverSocketFactory);
            server.setWireFormatFactory(this.createWireFormatFactory(options));
            IntrospectionSupport.setProperties(server, options);
            Map<String, Object> transportOptions = IntrospectionSupport.extractProperties(options, "transport.");
            server.setTransportOption(transportOptions);
            server.bind();
            return server;
        }
        catch (URISyntaxException e) {
            throw IOExceptionSupport.create(e);
        }
    }

    protected SslTransportServer createSslTransportServer(URI location, SSLServerSocketFactory serverSocketFactory) throws IOException, URISyntaxException {
        return new SslTransportServer(this, location, serverSocketFactory);
    }

    @Override
    public Transport compositeConfigure(Transport transport, WireFormat format, Map options) {
        SslTransport sslTransport = transport.narrow(SslTransport.class);
        IntrospectionSupport.setProperties(sslTransport, options);
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
    protected ServerSocketFactory createServerSocketFactory() throws IOException {
        if (SslContext.getCurrentSslContext() != null) {
            SslContext ctx = SslContext.getCurrentSslContext();
            try {
                return ctx.getSSLContext().getServerSocketFactory();
            }
            catch (Exception e) {
                throw IOExceptionSupport.create(e);
            }
        }
        return SSLServerSocketFactory.getDefault();
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

    @Override
    public SslTransport createTransport(WireFormat wireFormat, Socket socket, TcpTransport.InitBuffer initBuffer) throws IOException {
        return new SslTransport(wireFormat, (SSLSocket)socket, initBuffer);
    }
}

