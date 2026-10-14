/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.udp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.TransportLoggerSupport;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.transport.CommandJoiner;
import org.apache.activemq.transport.InactivityMonitor;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.reliable.DefaultReplayStrategy;
import org.apache.activemq.transport.reliable.ExceptionIfDroppedReplayStrategy;
import org.apache.activemq.transport.reliable.ReliableTransport;
import org.apache.activemq.transport.reliable.ReplayStrategy;
import org.apache.activemq.transport.reliable.Replayer;
import org.apache.activemq.transport.tcp.TcpTransportFactory;
import org.apache.activemq.transport.udp.ResponseRedirectInterceptor;
import org.apache.activemq.transport.udp.UdpTransport;
import org.apache.activemq.transport.udp.UdpTransportServer;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;
import org.apache.activemq.wireformat.WireFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class UdpTransportFactory
extends TransportFactory {
    private static final Logger log = LoggerFactory.getLogger(TcpTransportFactory.class);

    @Override
    public TransportServer doBind(URI location) throws IOException {
        try {
            HashMap<String, String> options = new HashMap<String, String>(URISupport.parseParameters(location));
            if (options.containsKey("port")) {
                throw new IllegalArgumentException("The port property cannot be specified on a UDP server transport - please use the port in the URI syntax");
            }
            WireFormat wf = this.createWireFormat(options);
            int port = location.getPort();
            OpenWireFormat openWireFormat = this.asOpenWireFormat(wf);
            UdpTransport transport = (UdpTransport)this.createTransport(location.getPort(), wf);
            Transport configuredTransport = this.configure(transport, wf, options, true);
            UdpTransportServer server = new UdpTransportServer(location, transport, configuredTransport, this.createReplayStrategy());
            return server;
        }
        catch (URISyntaxException e) {
            throw IOExceptionSupport.create(e);
        }
        catch (Exception e) {
            throw IOExceptionSupport.create(e);
        }
    }

    @Override
    public Transport configure(Transport transport, WireFormat format, Map options) throws Exception {
        return this.configure(transport, format, options, false);
    }

    @Override
    public Transport compositeConfigure(Transport transport, WireFormat format, Map options) {
        IntrospectionSupport.setProperties(transport, options);
        UdpTransport udpTransport = (UdpTransport)transport;
        transport = new CommandJoiner(transport, this.asOpenWireFormat(format));
        if (udpTransport.isTrace()) {
            try {
                transport = TransportLoggerSupport.createTransportLogger(transport);
            }
            catch (Throwable e) {
                log.error("Could not create TransportLogger, reason: " + e, e);
            }
        }
        transport = new InactivityMonitor(transport, format);
        if (format instanceof OpenWireFormat) {
            transport = this.configureClientSideNegotiator(transport, format, udpTransport);
        }
        return transport;
    }

    @Override
    protected Transport createTransport(URI location, WireFormat wf) throws UnknownHostException, IOException {
        OpenWireFormat wireFormat = this.asOpenWireFormat(wf);
        return new UdpTransport(wireFormat, location);
    }

    protected Transport createTransport(int port, WireFormat wf) throws UnknownHostException, IOException {
        OpenWireFormat wireFormat = this.asOpenWireFormat(wf);
        return new UdpTransport(wireFormat, port);
    }

    protected Transport configure(Transport transport, WireFormat format, Map options, boolean acceptServer) throws Exception {
        IntrospectionSupport.setProperties(transport, options);
        UdpTransport udpTransport = (UdpTransport)transport;
        OpenWireFormat openWireFormat = this.asOpenWireFormat(format);
        if (udpTransport.isTrace()) {
            transport = TransportLoggerSupport.createTransportLogger(transport);
        }
        transport = new InactivityMonitor(transport, format);
        if (!acceptServer && format instanceof OpenWireFormat) {
            transport = this.configureClientSideNegotiator(transport, format, udpTransport);
        }
        if (acceptServer) {
            udpTransport.setReplayEnabled(false);
            transport = new CommandJoiner(transport, openWireFormat);
            return transport;
        }
        ReliableTransport reliableTransport = new ReliableTransport(transport, udpTransport);
        Replayer replayer = reliableTransport.getReplayer();
        reliableTransport.setReplayStrategy(this.createReplayStrategy(replayer));
        return new CommandJoiner(reliableTransport, openWireFormat);
    }

    protected ReplayStrategy createReplayStrategy(Replayer replayer) {
        if (replayer != null) {
            return new DefaultReplayStrategy(5);
        }
        return new ExceptionIfDroppedReplayStrategy(1);
    }

    protected ReplayStrategy createReplayStrategy() {
        return new DefaultReplayStrategy(5);
    }

    protected Transport configureClientSideNegotiator(Transport transport, WireFormat format, UdpTransport udpTransport) {
        return new ResponseRedirectInterceptor(transport, udpTransport);
    }

    protected OpenWireFormat asOpenWireFormat(WireFormat wf) {
        OpenWireFormat answer = (OpenWireFormat)wf;
        return answer;
    }
}

