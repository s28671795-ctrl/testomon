/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.udp;

import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.DatagramChannel;
import java.security.cert.X509Certificate;
import org.apache.activemq.Service;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.Endpoint;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportThreadSupport;
import org.apache.activemq.transport.reliable.ExceptionIfDroppedReplayStrategy;
import org.apache.activemq.transport.reliable.ReplayBuffer;
import org.apache.activemq.transport.reliable.ReplayStrategy;
import org.apache.activemq.transport.reliable.Replayer;
import org.apache.activemq.transport.udp.ByteBufferPool;
import org.apache.activemq.transport.udp.CommandChannel;
import org.apache.activemq.transport.udp.CommandDatagramChannel;
import org.apache.activemq.transport.udp.DatagramEndpoint;
import org.apache.activemq.transport.udp.DatagramHeaderMarshaller;
import org.apache.activemq.transport.udp.DefaultBufferPool;
import org.apache.activemq.util.InetAddressUtil;
import org.apache.activemq.util.IntSequenceGenerator;
import org.apache.activemq.util.ServiceStopper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UdpTransport
extends TransportThreadSupport
implements Transport,
Service,
Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(UdpTransport.class);
    private static final int MAX_BIND_ATTEMPTS = 50;
    private static final long BIND_ATTEMPT_DELAY = 100L;
    private CommandChannel commandChannel;
    private OpenWireFormat wireFormat;
    private ByteBufferPool bufferPool;
    private ReplayStrategy replayStrategy = new ExceptionIfDroppedReplayStrategy();
    private ReplayBuffer replayBuffer;
    private int datagramSize = 4096;
    private SocketAddress targetAddress;
    private SocketAddress originalTargetAddress;
    private DatagramChannel channel;
    private boolean trace;
    private boolean useLocalHost = false;
    private int port;
    private int minmumWireFormatVersion;
    private String description;
    private IntSequenceGenerator sequenceGenerator;
    private boolean replayEnabled = true;

    protected UdpTransport(OpenWireFormat wireFormat) throws IOException {
        this.wireFormat = wireFormat;
    }

    public UdpTransport(OpenWireFormat wireFormat, URI remoteLocation) throws UnknownHostException, IOException {
        this(wireFormat);
        this.targetAddress = this.createAddress(remoteLocation);
        this.description = remoteLocation.toString() + "@";
    }

    public UdpTransport(OpenWireFormat wireFormat, SocketAddress socketAddress) throws IOException {
        this(wireFormat);
        this.targetAddress = socketAddress;
        this.description = this.getProtocolName() + "ServerConnection@";
    }

    public UdpTransport(OpenWireFormat wireFormat, int port) throws UnknownHostException, IOException {
        this(wireFormat);
        this.port = port;
        this.targetAddress = null;
        this.description = this.getProtocolName() + "Server@";
    }

    public Replayer createReplayer() throws IOException {
        if (this.replayEnabled) {
            return this.getCommandChannel();
        }
        return null;
    }

    @Override
    public void oneway(Object command) throws IOException {
        this.oneway(command, this.targetAddress);
    }

    public void oneway(Object command, SocketAddress address) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Sending oneway from: " + this + " to target: " + this.targetAddress + " command: " + command);
        }
        this.checkStarted();
        this.commandChannel.write((Command)command, address);
    }

    public String toString() {
        if (this.description != null) {
            return this.description + this.port;
        }
        return this.getProtocolUriScheme() + this.targetAddress + "@" + this.port;
    }

    @Override
    public void run() {
        LOG.trace("Consumer thread starting for: " + this.toString());
        while (!this.isStopped()) {
            try {
                Command command = this.commandChannel.read();
                this.doConsume(command);
            }
            catch (AsynchronousCloseException e) {
                try {
                    this.stop();
                }
                catch (Exception e2) {
                    LOG.warn("Caught in: " + this + " while closing: " + e2 + ". Now Closed", e2);
                }
            }
            catch (SocketException e) {
                LOG.debug("Socket closed: " + e, e);
                try {
                    this.stop();
                }
                catch (Exception e2) {
                    LOG.warn("Caught in: " + this + " while closing: " + e2 + ". Now Closed", e2);
                }
            }
            catch (EOFException e) {
                LOG.debug("Socket closed: " + e, e);
                try {
                    this.stop();
                }
                catch (Exception e2) {
                    LOG.warn("Caught in: " + this + " while closing: " + e2 + ". Now Closed", e2);
                }
            }
            catch (Exception e) {
                try {
                    this.stop();
                }
                catch (Exception e2) {
                    LOG.warn("Caught in: " + this + " while closing: " + e2 + ". Now Closed", e2);
                }
                if (e instanceof IOException) {
                    this.onException((IOException)e);
                    continue;
                }
                LOG.error("Caught: " + e, e);
                e.printStackTrace();
            }
        }
    }

    public void setTargetEndpoint(Endpoint newTarget) {
        DatagramEndpoint endpoint;
        SocketAddress address;
        if (newTarget instanceof DatagramEndpoint && (address = (endpoint = (DatagramEndpoint)newTarget).getAddress()) != null) {
            if (this.originalTargetAddress == null) {
                this.originalTargetAddress = this.targetAddress;
            }
            this.targetAddress = address;
            this.commandChannel.setTargetAddress(address);
        }
    }

    public boolean isTrace() {
        return this.trace;
    }

    public void setTrace(boolean trace) {
        this.trace = trace;
    }

    public int getDatagramSize() {
        return this.datagramSize;
    }

    public void setDatagramSize(int datagramSize) {
        this.datagramSize = datagramSize;
    }

    public boolean isUseLocalHost() {
        return this.useLocalHost;
    }

    public void setUseLocalHost(boolean useLocalHost) {
        this.useLocalHost = useLocalHost;
    }

    public CommandChannel getCommandChannel() throws IOException {
        if (this.commandChannel == null) {
            this.commandChannel = this.createCommandChannel();
        }
        return this.commandChannel;
    }

    public void setCommandChannel(CommandDatagramChannel commandChannel) {
        this.commandChannel = commandChannel;
    }

    public ReplayStrategy getReplayStrategy() {
        return this.replayStrategy;
    }

    public void setReplayStrategy(ReplayStrategy replayStrategy) {
        this.replayStrategy = replayStrategy;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMinmumWireFormatVersion() {
        return this.minmumWireFormatVersion;
    }

    public void setMinmumWireFormatVersion(int minmumWireFormatVersion) {
        this.minmumWireFormatVersion = minmumWireFormatVersion;
    }

    @Override
    public OpenWireFormat getWireFormat() {
        return this.wireFormat;
    }

    public IntSequenceGenerator getSequenceGenerator() {
        if (this.sequenceGenerator == null) {
            this.sequenceGenerator = new IntSequenceGenerator();
        }
        return this.sequenceGenerator;
    }

    public void setSequenceGenerator(IntSequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    public boolean isReplayEnabled() {
        return this.replayEnabled;
    }

    public void setReplayEnabled(boolean replayEnabled) {
        this.replayEnabled = replayEnabled;
    }

    public ByteBufferPool getBufferPool() {
        if (this.bufferPool == null) {
            this.bufferPool = new DefaultBufferPool();
        }
        return this.bufferPool;
    }

    public void setBufferPool(ByteBufferPool bufferPool) {
        this.bufferPool = bufferPool;
    }

    public ReplayBuffer getReplayBuffer() {
        return this.replayBuffer;
    }

    public void setReplayBuffer(ReplayBuffer replayBuffer) throws IOException {
        this.replayBuffer = replayBuffer;
        this.getCommandChannel().setReplayBuffer(replayBuffer);
    }

    protected InetSocketAddress createAddress(URI remoteLocation) throws UnknownHostException, IOException {
        String host2 = this.resolveHostName(remoteLocation.getHost());
        return new InetSocketAddress(host2, remoteLocation.getPort());
    }

    protected String resolveHostName(String host2) throws UnknownHostException {
        String localName = InetAddressUtil.getLocalHostName();
        if (localName != null && this.isUseLocalHost() && localName.equals(host2)) {
            return "localhost";
        }
        return host2;
    }

    @Override
    protected void doStart() throws Exception {
        this.getCommandChannel().start();
        super.doStart();
    }

    protected CommandChannel createCommandChannel() throws IOException {
        SocketAddress localAddress = this.createLocalAddress();
        this.channel = DatagramChannel.open();
        this.channel = this.connect(this.channel, this.targetAddress);
        DatagramSocket socket = this.channel.socket();
        this.bind(socket, localAddress);
        if (this.port == 0) {
            this.port = socket.getLocalPort();
        }
        return this.createCommandDatagramChannel();
    }

    protected CommandChannel createCommandDatagramChannel() {
        return new CommandDatagramChannel(this, this.getWireFormat(), this.getDatagramSize(), this.getTargetAddress(), this.createDatagramHeaderMarshaller(), this.getChannel(), this.getBufferPool());
    }

    protected void bind(DatagramSocket socket, SocketAddress localAddress) throws IOException {
        this.channel.configureBlocking(true);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Binding to address: " + localAddress);
        }
        for (int i = 0; i < 50; ++i) {
            try {
                socket.bind(localAddress);
                return;
            }
            catch (BindException e) {
                if (i + 1 == 50) {
                    throw e;
                }
                try {
                    Thread.sleep(100L);
                    continue;
                }
                catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }
        }
    }

    protected DatagramChannel connect(DatagramChannel channel, SocketAddress targetAddress2) throws IOException {
        return channel;
    }

    protected SocketAddress createLocalAddress() {
        return new InetSocketAddress(this.port);
    }

    @Override
    protected void doStop(ServiceStopper stopper) throws Exception {
        if (this.channel != null) {
            this.channel.close();
        }
    }

    protected DatagramHeaderMarshaller createDatagramHeaderMarshaller() {
        return new DatagramHeaderMarshaller();
    }

    protected String getProtocolName() {
        return "Udp";
    }

    protected String getProtocolUriScheme() {
        return "udp://";
    }

    protected SocketAddress getTargetAddress() {
        return this.targetAddress;
    }

    protected DatagramChannel getChannel() {
        return this.channel;
    }

    protected void setChannel(DatagramChannel channel) {
        this.channel = channel;
    }

    public InetSocketAddress getLocalSocketAddress() {
        if (this.channel == null) {
            return null;
        }
        return (InetSocketAddress)this.channel.socket().getLocalSocketAddress();
    }

    @Override
    public String getRemoteAddress() {
        if (this.targetAddress != null) {
            return "" + this.targetAddress;
        }
        return null;
    }

    @Override
    public int getReceiveCounter() {
        if (this.commandChannel == null) {
            return 0;
        }
        return this.commandChannel.getReceiveCounter();
    }

    @Override
    public X509Certificate[] getPeerCertificates() {
        return null;
    }

    @Override
    public void setPeerCertificates(X509Certificate[] certificates) {
    }
}

