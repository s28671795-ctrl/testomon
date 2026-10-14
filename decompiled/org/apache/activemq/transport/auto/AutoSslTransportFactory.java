/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.auto;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.BrokerServiceAware;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.auto.AutoSslTransportServer;
import org.apache.activemq.transport.auto.AutoTransportUtils;
import org.apache.activemq.transport.tcp.SslTransportFactory;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.transport.tcp.TcpTransportFactory;
import org.apache.activemq.transport.tcp.TcpTransportServer;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;
import org.apache.activemq.wireformat.WireFormat;

public class AutoSslTransportFactory
extends SslTransportFactory
implements BrokerServiceAware {
    protected BrokerService brokerService;
    private Set<String> enabledProtocols;
    boolean allowLinkStealingSet = false;

    @Override
    public void setBrokerService(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    @Override
    public TransportServer doBind(URI location) throws IOException {
        try {
            HashMap<String, String> options = new HashMap<String, String>(URISupport.parseParameters(location));
            Map<String, Object> autoProperties = IntrospectionSupport.extractProperties(options, "auto.");
            this.enabledProtocols = AutoTransportUtils.parseProtocols((String)autoProperties.get("protocols"));
            ServerSocketFactory serverSocketFactory = this.createServerSocketFactory();
            AutoSslTransportServer server = this.createAutoSslTransportServer(location, (SSLServerSocketFactory)serverSocketFactory);
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

    protected AutoSslTransportServer createAutoSslTransportServer(URI location, SSLServerSocketFactory serverSocketFactory) throws IOException, URISyntaxException {
        AutoSslTransportServer server = new AutoSslTransportServer(this, location, serverSocketFactory, this.brokerService, this.enabledProtocols){

            @Override
            protected TcpTransport createTransport(Socket socket, WireFormat format, TcpTransportFactory detectedTransportFactory, TcpTransport.InitBuffer initBuffer) throws IOException {
                AutoSslTransportFactory.this.setDefaultLinkStealing(format, this);
                return super.createTransport(socket, format, detectedTransportFactory, initBuffer);
            }
        };
        return server;
    }

    private void setDefaultLinkStealing(WireFormat format, TcpTransportServer server) {
        if (format.getClass().toString().contains("MQTT") && !this.allowLinkStealingSet) {
            server.setAllowLinkStealing(true);
        }
    }
}

