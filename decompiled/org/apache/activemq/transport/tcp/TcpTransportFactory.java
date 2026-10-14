/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.tcp;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLEngine;
import org.apache.activemq.TransportLoggerSupport;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.transport.InactivityMonitor;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.WireFormatNegotiator;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.transport.tcp.TcpTransportServer;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;
import org.apache.activemq.wireformat.WireFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpTransportFactory
extends TransportFactory {
    private static final Logger LOG = LoggerFactory.getLogger(TcpTransportFactory.class);

    @Override
    public TransportServer doBind(URI location) throws IOException {
        try {
            HashMap<String, String> options = new HashMap<String, String>(URISupport.parseParameters(location));
            ServerSocketFactory serverSocketFactory = this.createServerSocketFactory();
            TcpTransportServer server = this.createTcpTransportServer(location, serverSocketFactory);
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

    protected TcpTransportServer createTcpTransportServer(URI location, ServerSocketFactory serverSocketFactory) throws IOException, URISyntaxException {
        return new TcpTransportServer(this, location, serverSocketFactory);
    }

    @Override
    public Transport compositeConfigure(Transport transport, WireFormat format, Map options) {
        boolean useInactivityMonitor;
        TcpTransport tcpTransport = transport.narrow(TcpTransport.class);
        IntrospectionSupport.setProperties(tcpTransport, options);
        Map<String, Object> socketOptions = IntrospectionSupport.extractProperties(options, "socket.");
        tcpTransport.setSocketOptions(socketOptions);
        if (tcpTransport.isTrace()) {
            try {
                transport = TransportLoggerSupport.createTransportLogger(transport, tcpTransport.getLogWriterName(), tcpTransport.isDynamicManagement(), tcpTransport.isStartLogging(), tcpTransport.getJmxPort());
            }
            catch (Throwable e) {
                LOG.error("Could not create TransportLogger object for: " + tcpTransport.getLogWriterName() + ", reason: " + e, e);
            }
        }
        if ((useInactivityMonitor = "true".equals(this.getOption(options, "useInactivityMonitor", "true"))) && this.isUseInactivityMonitor(transport)) {
            transport = this.createInactivityMonitor(transport, format);
            IntrospectionSupport.setProperties(transport, options);
        }
        if (format instanceof OpenWireFormat) {
            transport = new WireFormatNegotiator(transport, (OpenWireFormat)format, tcpTransport.getMinmumWireFormatVersion());
        }
        return super.compositeConfigure(transport, format, options);
    }

    protected boolean isUseInactivityMonitor(Transport transport) {
        return true;
    }

    @Override
    protected Transport createTransport(URI location, WireFormat wf) throws UnknownHostException, IOException {
        URI localLocation;
        block3: {
            localLocation = null;
            String path = location.getPath();
            if (path != null && path.length() > 0) {
                int localPortIndex = path.indexOf(58);
                try {
                    Integer.parseInt(path.substring(localPortIndex + 1, path.length()));
                    String localString = location.getScheme() + ":/" + path;
                    localLocation = new URI(localString);
                }
                catch (Exception e) {
                    LOG.warn("path isn't a valid local location for TcpTransport to use: {}", (Object)e.getMessage());
                    if (!LOG.isDebugEnabled()) break block3;
                    LOG.debug("Failure detail", e);
                }
            }
        }
        SocketFactory socketFactory = this.createSocketFactory();
        return this.createTcpTransport(wf, socketFactory, location, localLocation);
    }

    public TcpTransport createTransport(WireFormat wireFormat, Socket socket, TcpTransport.InitBuffer initBuffer) throws IOException {
        throw new IOException("createTransport() method not implemented!");
    }

    public TcpTransport createTransport(WireFormat wireFormat, Socket socket, SSLEngine engine, TcpTransport.InitBuffer initBuffer, ByteBuffer inputBuffer) throws IOException {
        throw new IOException("createTransport() method not implemented!");
    }

    protected TcpTransport createTcpTransport(WireFormat wf, SocketFactory socketFactory, URI location, URI localLocation) throws UnknownHostException, IOException {
        return new TcpTransport(wf, socketFactory, location, localLocation);
    }

    protected ServerSocketFactory createServerSocketFactory() throws IOException {
        return ServerSocketFactory.getDefault();
    }

    protected SocketFactory createSocketFactory() throws IOException {
        return SocketFactory.getDefault();
    }

    protected Transport createInactivityMonitor(Transport transport, WireFormat format) {
        return new InactivityMonitor(transport, format);
    }
}

