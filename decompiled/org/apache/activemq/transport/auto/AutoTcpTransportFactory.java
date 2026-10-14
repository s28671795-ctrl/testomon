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
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.BrokerServiceAware;
import org.apache.activemq.openwire.OpenWireFormatFactory;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.auto.AutoTcpTransportServer;
import org.apache.activemq.transport.auto.AutoTransportUtils;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.transport.tcp.TcpTransportFactory;
import org.apache.activemq.transport.tcp.TcpTransportServer;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;
import org.apache.activemq.wireformat.WireFormat;

public class AutoTcpTransportFactory
extends TcpTransportFactory
implements BrokerServiceAware {
    protected BrokerService brokerService;
    boolean allowLinkStealingSet = false;
    private Set<String> enabledProtocols;

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

    @Override
    protected AutoTcpTransportServer createTcpTransportServer(URI location, ServerSocketFactory serverSocketFactory) throws IOException, URISyntaxException {
        AutoTcpTransportServer server = new AutoTcpTransportServer(this, location, serverSocketFactory, this.brokerService, this.enabledProtocols){

            @Override
            protected TcpTransport createTransport(Socket socket, WireFormat format, TcpTransportFactory detectedTransportFactory, TcpTransport.InitBuffer initBuffer) throws IOException {
                AutoTcpTransportFactory.this.setDefaultLinkStealing(format, this);
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

