/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocket;
import org.apache.activemq.Service;
import org.apache.activemq.TransportLoggerSupport;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.openwire.OpenWireFormatFactory;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.transport.TransportServerThreadSupport;
import org.apache.activemq.transport.tcp.ExceededMaximumConnectionsException;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.transport.tcp.TcpTransportFactory;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.InetAddressUtil;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.ServiceListener;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.util.ServiceSupport;
import org.apache.activemq.wireformat.WireFormat;
import org.apache.activemq.wireformat.WireFormatFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpTransportServer
extends TransportServerThreadSupport
implements ServiceListener {
    private static final Logger LOG = LoggerFactory.getLogger(TcpTransportServer.class);
    protected volatile ServerSocket serverSocket;
    protected volatile Selector selector;
    protected int backlog = 5000;
    protected WireFormatFactory wireFormatFactory = new OpenWireFormatFactory();
    protected final TcpTransportFactory transportFactory;
    protected long maxInactivityDuration = 30000L;
    protected long maxInactivityDurationInitalDelay = 10000L;
    protected int minmumWireFormatVersion;
    protected boolean useQueueForAccept = true;
    protected boolean allowLinkStealing;
    protected boolean verifyHostName = false;
    protected boolean trace = false;
    protected int soTimeout = 0;
    protected int socketBufferSize = 65536;
    protected int connectionTimeout = 30000;
    protected String logWriterName = TransportLoggerSupport.defaultLogWriterName;
    protected boolean dynamicManagement = false;
    protected boolean startLogging = true;
    protected int jmxPort = TransportLoggerSupport.defaultJmxPort;
    protected final ServerSocketFactory serverSocketFactory;
    protected final BlockingQueue<Socket> socketQueue = new LinkedBlockingQueue<Socket>();
    protected Thread socketHandlerThread;
    protected int maximumConnections = Integer.MAX_VALUE;
    protected final AtomicInteger currentTransportCount = new AtomicInteger();

    public TcpTransportServer(TcpTransportFactory transportFactory, URI location, ServerSocketFactory serverSocketFactory) throws IOException, URISyntaxException {
        super(location);
        this.transportFactory = transportFactory;
        this.serverSocketFactory = serverSocketFactory;
    }

    public void bind() throws IOException {
        URI bind = this.getBindLocation();
        String host2 = bind.getHost();
        host2 = host2 == null || host2.length() == 0 ? "localhost" : host2;
        InetAddress addr = InetAddress.getByName(host2);
        try {
            this.serverSocket = this.serverSocketFactory.createServerSocket(bind.getPort(), this.backlog, addr);
            this.configureServerSocket(this.serverSocket);
        }
        catch (IOException e) {
            throw IOExceptionSupport.create("Failed to bind to server socket: " + bind + " due to: " + e, e);
        }
        try {
            this.setConnectURI(new URI(bind.getScheme(), bind.getUserInfo(), this.resolveHostName(this.serverSocket, addr), this.serverSocket.getLocalPort(), bind.getPath(), bind.getQuery(), bind.getFragment()));
        }
        catch (URISyntaxException e) {
            try {
                this.setConnectURI(new URI(bind.getScheme(), bind.getUserInfo(), addr.getHostAddress(), this.serverSocket.getLocalPort(), bind.getPath(), bind.getQuery(), bind.getFragment()));
            }
            catch (URISyntaxException e2) {
                throw IOExceptionSupport.create(e2);
            }
        }
    }

    private void configureServerSocket(ServerSocket socket) throws SocketException {
        socket.setSoTimeout(2000);
        if (this.transportOptions != null) {
            if (socket instanceof SSLServerSocket) {
                Object cipherSuites;
                if (this.transportOptions.containsKey("verifyHostName")) {
                    this.verifyHostName = Boolean.parseBoolean(this.transportOptions.get("verifyHostName").toString());
                } else {
                    this.transportOptions.put("verifyHostName", this.verifyHostName);
                }
                if (this.verifyHostName) {
                    SSLParameters sslParams = new SSLParameters();
                    sslParams.setEndpointIdentificationAlgorithm("HTTPS");
                    ((SSLServerSocket)this.serverSocket).setSSLParameters(sslParams);
                }
                if (this.transportOptions.containsKey("enabledCipherSuites") && !IntrospectionSupport.setProperty(socket, "enabledCipherSuites", cipherSuites = this.transportOptions.remove("enabledCipherSuites"))) {
                    throw new SocketException(String.format("Invalid transport options {enabledCipherSuites=%s}", cipherSuites));
                }
            }
            IntrospectionSupport.setProperties((Object)socket, this.transportOptions, false);
        }
    }

    public WireFormatFactory getWireFormatFactory() {
        return this.wireFormatFactory;
    }

    public void setWireFormatFactory(WireFormatFactory wireFormatFactory) {
        this.wireFormatFactory = wireFormatFactory;
    }

    @Override
    public void setBrokerInfo(BrokerInfo brokerInfo) {
    }

    public long getMaxInactivityDuration() {
        return this.maxInactivityDuration;
    }

    public void setMaxInactivityDuration(long maxInactivityDuration) {
        this.maxInactivityDuration = maxInactivityDuration;
    }

    public long getMaxInactivityDurationInitalDelay() {
        return this.maxInactivityDurationInitalDelay;
    }

    public void setMaxInactivityDurationInitalDelay(long maxInactivityDurationInitalDelay) {
        this.maxInactivityDurationInitalDelay = maxInactivityDurationInitalDelay;
    }

    public int getMinmumWireFormatVersion() {
        return this.minmumWireFormatVersion;
    }

    public void setMinmumWireFormatVersion(int minmumWireFormatVersion) {
        this.minmumWireFormatVersion = minmumWireFormatVersion;
    }

    public boolean isTrace() {
        return this.trace;
    }

    public void setTrace(boolean trace) {
        this.trace = trace;
    }

    public String getLogWriterName() {
        return this.logWriterName;
    }

    public void setLogWriterName(String logFormat) {
        this.logWriterName = logFormat;
    }

    public boolean isDynamicManagement() {
        return this.dynamicManagement;
    }

    public void setDynamicManagement(boolean useJmx) {
        this.dynamicManagement = useJmx;
    }

    public void setJmxPort(int jmxPort) {
        this.jmxPort = jmxPort;
    }

    public int getJmxPort() {
        return this.jmxPort;
    }

    public boolean isStartLogging() {
        return this.startLogging;
    }

    public void setStartLogging(boolean startLogging) {
        this.startLogging = startLogging;
    }

    public int getBacklog() {
        return this.backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

    public boolean isUseQueueForAccept() {
        return this.useQueueForAccept;
    }

    public void setUseQueueForAccept(boolean useQueueForAccept) {
        this.useQueueForAccept = useQueueForAccept;
    }

    @Override
    public void run() {
        if (!this.isStopped() && !this.isStopping()) {
            ServerSocketChannel channel;
            ServerSocket serverSocket = this.serverSocket;
            if (serverSocket == null) {
                this.onAcceptError(new IOException("Server started without a valid ServerSocket"));
            }
            if ((channel = serverSocket.getChannel()) != null) {
                this.doRunWithServerSocketChannel(channel);
            } else {
                this.doRunWithServerSocket(serverSocket);
            }
        }
    }

    private void doRunWithServerSocketChannel(ServerSocketChannel channel) {
        block20: {
            try {
                channel.configureBlocking(false);
                Selector selector = Selector.open();
                try {
                    channel.register(selector, 16);
                }
                catch (ClosedChannelException ex) {
                    try {
                        selector.close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    throw ex;
                }
                this.selector = selector;
                while (!this.isStopped()) {
                    int count = selector.select(10L);
                    if (count == 0) continue;
                    Set<SelectionKey> keys2 = selector.selectedKeys();
                    Iterator<SelectionKey> i = keys2.iterator();
                    while (i.hasNext()) {
                        block19: {
                            SelectionKey key = i.next();
                            if (key.isAcceptable()) {
                                try {
                                    SocketChannel sc = channel.accept();
                                    if (sc != null) {
                                        if (this.isStopped() || this.getAcceptListener() == null) {
                                            sc.close();
                                        } else if (this.useQueueForAccept) {
                                            this.socketQueue.put(sc.socket());
                                        } else {
                                            this.handleSocket(sc.socket());
                                        }
                                    }
                                }
                                catch (SocketTimeoutException sc) {
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                    if (!this.isStopping()) {
                                        this.onAcceptError(e);
                                    }
                                    if (this.isStopped()) break block19;
                                    LOG.warn("run()", e);
                                    this.onAcceptError(e);
                                }
                            }
                        }
                        i.remove();
                    }
                }
            }
            catch (IOException ex) {
                if (!this.isStopping()) {
                    this.onAcceptError(ex);
                }
                if (this.isStopped()) break block20;
                LOG.warn("run()", ex);
                this.onAcceptError(ex);
            }
        }
    }

    private void doRunWithServerSocket(ServerSocket serverSocket) {
        while (!this.isStopped()) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                if (socket == null) continue;
                if (this.isStopped() || this.getAcceptListener() == null) {
                    socket.close();
                    continue;
                }
                if (this.useQueueForAccept) {
                    this.socketQueue.put(socket);
                    continue;
                }
                this.handleSocket(socket);
            }
            catch (SocketTimeoutException socketTimeoutException) {
            }
            catch (Exception e) {
                if (!this.isStopping()) {
                    this.onAcceptError(e);
                    continue;
                }
                if (this.isStopped()) continue;
                LOG.warn("run()", e);
                this.onAcceptError(e);
            }
        }
    }

    protected Transport createTransport(Socket socket, WireFormat format) throws IOException {
        return new TcpTransport(format, socket);
    }

    public String toString() {
        return "" + this.getBindLocation();
    }

    protected String resolveHostName(ServerSocket socket, InetAddress bindAddress) throws UnknownHostException {
        String result = null;
        result = socket.isBound() ? (socket.getInetAddress().isAnyLocalAddress() ? InetAddressUtil.getLocalHostName() : socket.getInetAddress().getCanonicalHostName()) : bindAddress.getCanonicalHostName();
        return result;
    }

    @Override
    protected void doStart() throws Exception {
        if (this.useQueueForAccept) {
            Runnable run = new Runnable(){

                @Override
                public void run() {
                    block4: while (true) {
                        try {
                            while (!TcpTransportServer.this.isStopped() && !TcpTransportServer.this.isStopping()) {
                                Socket sock = TcpTransportServer.this.socketQueue.poll(1L, TimeUnit.SECONDS);
                                if (sock == null) continue;
                                try {
                                    TcpTransportServer.this.handleSocket(sock);
                                    continue block4;
                                }
                                catch (Throwable thrown) {
                                    if (!TcpTransportServer.this.isStopping()) {
                                        TcpTransportServer.this.onAcceptError(new Exception(thrown));
                                        continue;
                                    }
                                    if (TcpTransportServer.this.isStopped()) continue;
                                    LOG.warn("Unexpected error thrown during accept handling: ", thrown);
                                    TcpTransportServer.this.onAcceptError(new Exception(thrown));
                                }
                            }
                            break;
                        }
                        catch (InterruptedException e) {
                            if (TcpTransportServer.this.isStopped() && TcpTransportServer.this.isStopping()) break;
                            LOG.info("socketQueue interrupted - stopping");
                            TcpTransportServer.this.onAcceptError(e);
                            break;
                        }
                    }
                }
            };
            this.socketHandlerThread = new Thread(null, run, "ActiveMQ Transport Server Thread Handler: " + this.toString(), this.getStackSize());
            this.socketHandlerThread.setDaemon(true);
            this.socketHandlerThread.setPriority(8);
            this.socketHandlerThread.start();
        }
        super.doStart();
    }

    @Override
    protected void doStop(ServiceStopper stopper) throws Exception {
        Exception firstFailure;
        block10: {
            firstFailure = null;
            try {
                if (this.selector != null) {
                    this.selector.close();
                    this.selector = null;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                ServerSocket serverSocket = this.serverSocket;
                if (serverSocket != null) {
                    this.serverSocket = null;
                    serverSocket.close();
                }
            }
            catch (Exception error) {
                firstFailure = error;
            }
            if (this.socketHandlerThread != null) {
                this.socketHandlerThread.interrupt();
                this.socketHandlerThread = null;
            }
            try {
                super.doStop(stopper);
            }
            catch (Exception error) {
                if (firstFailure == null) break block10;
                firstFailure = error;
            }
        }
        if (firstFailure != null) {
            throw firstFailure;
        }
    }

    @Override
    public InetSocketAddress getSocketAddress() {
        return (InetSocketAddress)this.serverSocket.getLocalSocketAddress();
    }

    protected void handleSocket(Socket socket) {
        this.doHandleSocket(socket);
    }

    protected final void doHandleSocket(Socket socket) {
        block10: {
            boolean closeSocket = true;
            boolean countIncremented = false;
            try {
                int currentCount;
                do {
                    if ((currentCount = this.currentTransportCount.get()) < this.maximumConnections) continue;
                    throw new ExceededMaximumConnectionsException("Exceeded the maximum number of allowed client connections. See the 'maximumConnections' property on the TCP transport configuration URI in the ActiveMQ configuration file (e.g., activemq.xml)");
                } while (!this.currentTransportCount.compareAndSet(currentCount, currentCount + 1));
                countIncremented = true;
                HashMap<String, Object> options = new HashMap<String, Object>();
                options.put("maxInactivityDuration", this.maxInactivityDuration);
                options.put("maxInactivityDurationInitalDelay", this.maxInactivityDurationInitalDelay);
                options.put("minmumWireFormatVersion", this.minmumWireFormatVersion);
                options.put("trace", this.trace);
                options.put("soTimeout", this.soTimeout);
                options.put("socketBufferSize", this.socketBufferSize);
                options.put("connectionTimeout", this.connectionTimeout);
                options.put("logWriterName", this.logWriterName);
                options.put("dynamicManagement", this.dynamicManagement);
                options.put("startLogging", this.startLogging);
                options.put("jmxPort", this.jmxPort);
                options.putAll(this.transportOptions);
                TransportInfo transportInfo = this.configureTransport(this, socket);
                closeSocket = false;
                if (transportInfo.transport instanceof ServiceSupport) {
                    ((ServiceSupport)((Object)transportInfo.transport)).addServiceListener(this);
                }
                Transport configuredTransport = transportInfo.transportFactory.serverConfigure(transportInfo.transport, transportInfo.format, options);
                this.getAcceptListener().onAccept(configuredTransport);
            }
            catch (SocketTimeoutException currentCount) {
            }
            catch (Exception e) {
                if (closeSocket) {
                    try {
                        if (countIncremented) {
                            this.currentTransportCount.decrementAndGet();
                        }
                        socket.close();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if (!this.isStopping()) {
                    this.onAcceptError(e);
                }
                if (this.isStopped()) break block10;
                LOG.warn("run()", e);
                this.onAcceptError(e);
            }
        }
    }

    protected TransportInfo configureTransport(TcpTransportServer server, Socket socket) throws Exception {
        WireFormat format = this.wireFormatFactory.createWireFormat();
        Transport transport = this.createTransport(socket, format);
        return new TransportInfo(format, transport, this.transportFactory);
    }

    public int getSoTimeout() {
        return this.soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getSocketBufferSize() {
        return this.socketBufferSize;
    }

    public void setSocketBufferSize(int socketBufferSize) {
        this.socketBufferSize = socketBufferSize;
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getMaximumConnections() {
        return this.maximumConnections;
    }

    public void setMaximumConnections(int maximumConnections) {
        this.maximumConnections = maximumConnections;
    }

    public AtomicInteger getCurrentTransportCount() {
        return this.currentTransportCount;
    }

    @Override
    public void started(Service service2) {
    }

    @Override
    public void stopped(Service service2) {
        this.currentTransportCount.decrementAndGet();
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

    protected class TransportInfo {
        final WireFormat format;
        final Transport transport;
        final TransportFactory transportFactory;

        public TransportInfo(WireFormat format, Transport transport, TransportFactory transportFactory) {
            this.format = format;
            this.transport = transport;
            this.transportFactory = transportFactory;
        }
    }
}

