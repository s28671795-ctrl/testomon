/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.auto.nio;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.net.ServerSocketFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.BrokerServiceAware;
import org.apache.activemq.openwire.OpenWireFormatFactory;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.auto.AutoTcpTransportServer;
import org.apache.activemq.transport.auto.AutoTransportUtils;
import org.apache.activemq.transport.auto.nio.AutoNIOTransport;
import org.apache.activemq.transport.nio.NIOTransportFactory;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.transport.tcp.TcpTransportFactory;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;
import org.apache.activemq.wireformat.WireFormat;

public class AutoNioTransportFactory
extends NIOTransportFactory
implements BrokerServiceAware {
    protected BrokerService brokerService;
    boolean allowLinkStealingSet = false;
    private Set<String> enabledProtocols;

    @Override
    public void setBrokerService(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    @Override
    protected AutoTcpTransportServer createTcpTransportServer(URI location, ServerSocketFactory serverSocketFactory) throws IOException, URISyntaxException {
        return new AutoTcpTransportServer(this, location, serverSocketFactory, this.brokerService, this.enabledProtocols){

            @Override
            protected TcpTransport createTransport(Socket socket, WireFormat format, TcpTransportFactory detectedTransportFactory, TcpTransport.InitBuffer initBuffer) throws IOException {
                TcpTransport nioTransport = null;
                nioTransport = detectedTransportFactory.getClass().equals(NIOTransportFactory.class) ? new AutoNIOTransport(format, socket, initBuffer) : detectedTransportFactory.createTransport(format, socket, initBuffer);
                if (format.getClass().toString().contains("MQTT") && !AutoNioTransportFactory.this.allowLinkStealingSet) {
                    this.setAllowLinkStealing(true);
                }
                return nioTransport;
            }
        };
    }

    @Override
    public TransportServer doBind(URI location) throws IOException {
        try {
            HashMap<String, String> options = new HashMap<String, String>(URISupport.parseParameters(location));
            Map<String, Object> autoProperties = IntrospectionSupport.extractProperties(options, "auto.");
            this.enabledProtocols = AutoTransportUtils.parseProtocols((String)autoProperties.get("protocols"));
            ServerSocketFactory serverSocketFactory = this.createServerSocketFactory();
            AutoTcpTransportServer server = this.createTcpTransportServer(location, serverSocketFactory);
            server.setWireFormatFactory(new OpenWireFormatFactory());
            if (options.get("allowLinkStealing") != null) {
                this.allowLinkStealingSet = true;
            }
            IntrospectionSupport.setProperties(server, options);
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

