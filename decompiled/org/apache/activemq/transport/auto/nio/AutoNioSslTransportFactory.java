/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.auto.nio;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLEngine;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.BrokerServiceAware;
import org.apache.activemq.broker.SslContext;
import org.apache.activemq.openwire.OpenWireFormatFactory;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.auto.AutoTransportUtils;
import org.apache.activemq.transport.auto.nio.AutoNIOSSLTransportServer;
import org.apache.activemq.transport.nio.NIOSSLTransport;
import org.apache.activemq.transport.nio.NIOSSLTransportFactory;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.transport.tcp.TcpTransportFactory;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;
import org.apache.activemq.wireformat.WireFormat;

public class AutoNioSslTransportFactory
extends NIOSSLTransportFactory
implements BrokerServiceAware {
    protected BrokerService brokerService;
    boolean allowLinkStealingSet = false;
    private Set<String> enabledProtocols;

    @Override
    public void setBrokerService(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    @Override
    protected AutoNIOSSLTransportServer createTcpTransportServer(URI location, ServerSocketFactory serverSocketFactory) throws IOException, URISyntaxException {
        return new AutoNIOSSLTransportServer(this.context, this, location, serverSocketFactory, this.brokerService, this.enabledProtocols){

            @Override
            protected Transport createTransport(Socket socket, WireFormat format, SSLEngine engine, TcpTransport.InitBuffer initBuffer, ByteBuffer inputBuffer, TcpTransportFactory detectedFactory) throws IOException {
                NIOSSLTransport nioSslTransport = (NIOSSLTransport)detectedFactory.createTransport(format, socket, engine, initBuffer, inputBuffer);
                if (format.getClass().toString().contains("MQTT") && !AutoNioSslTransportFactory.this.allowLinkStealingSet) {
                    this.setAllowLinkStealing(true);
                }
                if (AutoNioSslTransportFactory.this.context != null) {
                    nioSslTransport.setSslContext(AutoNioSslTransportFactory.this.context);
                }
                nioSslTransport.setNeedClientAuth(this.isNeedClientAuth());
                nioSslTransport.setWantClientAuth(this.isWantClientAuth());
                return nioSslTransport;
            }
        };
    }

    @Override
    public TransportServer doBind(URI location) throws IOException {
        try {
            if (SslContext.getCurrentSslContext() != null) {
                try {
                    this.context = SslContext.getCurrentSslContext().getSSLContext();
                }
                catch (Exception e) {
                    throw new IOException(e);
                }
            }
            HashMap<String, String> options = new HashMap<String, String>(URISupport.parseParameters(location));
            Map<String, Object> autoProperties = IntrospectionSupport.extractProperties(options, "auto.");
            this.enabledProtocols = AutoTransportUtils.parseProtocols((String)autoProperties.get("protocols"));
            ServerSocketFactory serverSocketFactory = this.createServerSocketFactory();
            AutoNIOSSLTransportServer server = this.createTcpTransportServer(location, serverSocketFactory);
            server.setWireFormatFactory(new OpenWireFormatFactory());
            if (options.get("allowLinkStealing") != null) {
                this.allowLinkStealingSet = true;
            }
            IntrospectionSupport.setProperties(server, options);
            server.setAutoTransportOptions(IntrospectionSupport.extractProperties(options, "auto."));
            server.setTransportOption(IntrospectionSupport.extractProperties(options, "transport."));
            server.setWireFormatOptions(AutoTransportUtils.extractWireFormatOptions(options));
            server.bind();
            return server;
        }
        catch (URISyntaxException e) {
            throw IOExceptionSupport.create(e);
        }
    }
}

