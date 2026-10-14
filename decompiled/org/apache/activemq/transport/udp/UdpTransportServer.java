/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.Command;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.transport.CommandJoiner;
import org.apache.activemq.transport.InactivityMonitor;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportListener;
import org.apache.activemq.transport.TransportServerSupport;
import org.apache.activemq.transport.reliable.ReliableTransport;
import org.apache.activemq.transport.reliable.ReplayStrategy;
import org.apache.activemq.transport.udp.DatagramEndpoint;
import org.apache.activemq.transport.udp.UdpTransport;
import org.apache.activemq.util.ServiceStopper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class UdpTransportServer
extends TransportServerSupport {
    private static final Logger LOG = LoggerFactory.getLogger(UdpTransportServer.class);
    private final UdpTransport serverTransport;
    private final ReplayStrategy replayStrategy;
    private final Transport configuredTransport;
    private boolean usingWireFormatNegotiation;
    private final Map<DatagramEndpoint, Transport> transports = new HashMap<DatagramEndpoint, Transport>();
    private boolean allowLinkStealing;

    public UdpTransportServer(URI connectURI, UdpTransport serverTransport, Transport configuredTransport, ReplayStrategy replayStrategy) {
        super(connectURI);
        this.serverTransport = serverTransport;
        this.configuredTransport = configuredTransport;
        this.replayStrategy = replayStrategy;
    }

    public String toString() {
        return "UdpTransportServer@" + this.serverTransport;
    }

    public void run() {
    }

    public UdpTransport getServerTransport() {
        return this.serverTransport;
    }

    @Override
    public void setBrokerInfo(BrokerInfo brokerInfo) {
    }

    @Override
    protected void doStart() throws Exception {
        LOG.info("Starting " + this);
        this.configuredTransport.setTransportListener(new TransportListener(){

            @Override
            public void onCommand(Object o) {
                Command command = (Command)o;
                UdpTransportServer.this.processInboundConnection(command);
            }

            @Override
            public void onException(IOException error) {
                LOG.error("Caught: " + error, error);
            }

            @Override
            public void transportInterupted() {
            }

            @Override
            public void transportResumed() {
            }
        });
        this.configuredTransport.start();
    }

    @Override
    protected void doStop(ServiceStopper stopper) throws Exception {
        this.configuredTransport.stop();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void processInboundConnection(Command command) {
        DatagramEndpoint endpoint = (DatagramEndpoint)command.getFrom();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received command on: " + this + " from address: " + endpoint + " command: " + command);
        }
        Transport transport = null;
        Map<DatagramEndpoint, Transport> map = this.transports;
        synchronized (map) {
            transport = this.transports.get(endpoint);
            if (transport == null) {
                if (this.usingWireFormatNegotiation && !command.isWireFormatInfo()) {
                    LOG.error("Received inbound server communication from: " + command.getFrom() + " expecting WireFormatInfo but was command: " + command);
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Creating a new UDP server connection");
                    }
                    try {
                        transport = this.createTransport(command, endpoint);
                        transport = this.configureTransport(transport);
                        this.transports.put(endpoint, transport);
                    }
                    catch (IOException e) {
                        LOG.error("Caught: " + e, e);
                        this.getAcceptListener().onAcceptError(e);
                    }
                }
            } else {
                LOG.warn("Discarding duplicate command to server from: " + endpoint + " command: " + command);
            }
        }
    }

    protected Transport configureTransport(Transport transport) {
        transport = new InactivityMonitor(transport, this.serverTransport.getWireFormat());
        this.getAcceptListener().onAccept(transport);
        return transport;
    }

    protected Transport createTransport(final Command command, DatagramEndpoint endpoint) throws IOException {
        if (endpoint == null) {
            throw new IOException("No endpoint available for command: " + command);
        }
        SocketAddress address = endpoint.getAddress();
        OpenWireFormat connectionWireFormat = this.serverTransport.getWireFormat().copy();
        UdpTransport transport = new UdpTransport(connectionWireFormat, address);
        final ReliableTransport reliableTransport = new ReliableTransport((Transport)transport, transport);
        reliableTransport.getReplayer();
        reliableTransport.setReplayStrategy(this.replayStrategy);
        return new CommandJoiner(reliableTransport, connectionWireFormat){

            @Override
            public void start() throws Exception {
                super.start();
                reliableTransport.onCommand(command);
            }
        };
    }

    @Override
    public InetSocketAddress getSocketAddress() {
        return this.serverTransport.getLocalSocketAddress();
    }

    @Override
    public boolean isSslServer() {
        return false;
    }

    @Override
    public boolean isAllowLinkStealing() {
        return this.allowLinkStealing;
    }

    @Override
    public void setAllowLinkStealing(boolean allowLinkStealing) {
        this.allowLinkStealing = allowLinkStealing;
    }
}

