/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.multicast;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.transport.multicast.MulticastDatagramHeaderMarshaller;
import org.apache.activemq.transport.udp.CommandChannel;
import org.apache.activemq.transport.udp.CommandDatagramSocket;
import org.apache.activemq.transport.udp.DatagramHeaderMarshaller;
import org.apache.activemq.transport.udp.UdpTransport;
import org.apache.activemq.util.ServiceStopper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MulticastTransport
extends UdpTransport {
    private static final Logger LOG = LoggerFactory.getLogger(MulticastTransport.class);
    private static final int DEFAULT_IDLE_TIME = 5000;
    private MulticastSocket socket;
    private InetAddress mcastAddress;
    private int mcastPort;
    private int timeToLive = 1;
    private boolean loopBackMode;
    private long keepAliveInterval = 5000L;

    public MulticastTransport(OpenWireFormat wireFormat, URI remoteLocation) throws UnknownHostException, IOException {
        super(wireFormat, remoteLocation);
    }

    public long getKeepAliveInterval() {
        return this.keepAliveInterval;
    }

    public void setKeepAliveInterval(long keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
    }

    public boolean isLoopBackMode() {
        return this.loopBackMode;
    }

    public void setLoopBackMode(boolean loopBackMode) {
        this.loopBackMode = loopBackMode;
    }

    public int getTimeToLive() {
        return this.timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    @Override
    protected String getProtocolName() {
        return "Multicast";
    }

    @Override
    protected String getProtocolUriScheme() {
        return "multicast://";
    }

    @Override
    protected void bind(DatagramSocket socket, SocketAddress localAddress) throws SocketException {
    }

    @Override
    protected void doStop(ServiceStopper stopper) throws Exception {
        super.doStop(stopper);
        if (this.socket != null) {
            try {
                this.socket.leaveGroup(this.getMulticastAddress());
            }
            catch (IOException e) {
                stopper.onException(this, e);
            }
            this.socket.close();
        }
    }

    @Override
    protected CommandChannel createCommandChannel() throws IOException {
        this.socket = new MulticastSocket(this.mcastPort);
        this.socket.setLoopbackMode(this.loopBackMode);
        this.socket.setTimeToLive(this.timeToLive);
        LOG.debug("Joining multicast address: " + this.getMulticastAddress());
        this.socket.joinGroup(this.getMulticastAddress());
        this.socket.setSoTimeout((int)this.keepAliveInterval);
        return new CommandDatagramSocket(this, this.getWireFormat(), this.getDatagramSize(), this.getTargetAddress(), this.createDatagramHeaderMarshaller(), this.getSocket());
    }

    protected InetAddress getMulticastAddress() {
        return this.mcastAddress;
    }

    protected MulticastSocket getSocket() {
        return this.socket;
    }

    protected void setSocket(MulticastSocket socket) {
        this.socket = socket;
    }

    @Override
    protected InetSocketAddress createAddress(URI remoteLocation) throws UnknownHostException, IOException {
        this.mcastAddress = InetAddress.getByName(remoteLocation.getHost());
        this.mcastPort = remoteLocation.getPort();
        return new InetSocketAddress(this.mcastAddress, this.mcastPort);
    }

    @Override
    protected DatagramHeaderMarshaller createDatagramHeaderMarshaller() {
        return new MulticastDatagramHeaderMarshaller("udp://dummyHostName:" + this.getPort());
    }
}

