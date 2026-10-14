/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.auto;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ServerSocketFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.BrokerServiceAware;
import org.apache.activemq.openwire.OpenWireFormatFactory;
import org.apache.activemq.transport.InactivityIOException;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.transport.auto.AutoTransportUtils;
import org.apache.activemq.transport.protocol.AmqpProtocolVerifier;
import org.apache.activemq.transport.protocol.MqttProtocolVerifier;
import org.apache.activemq.transport.protocol.OpenWireProtocolVerifier;
import org.apache.activemq.transport.protocol.ProtocolVerifier;
import org.apache.activemq.transport.protocol.StompProtocolVerifier;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.transport.tcp.TcpTransportFactory;
import org.apache.activemq.transport.tcp.TcpTransportServer;
import org.apache.activemq.util.FactoryFinder;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.wireformat.WireFormat;
import org.apache.activemq.wireformat.WireFormatFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoTcpTransportServer
extends TcpTransportServer {
    private static final Logger LOG = LoggerFactory.getLogger(AutoTcpTransportServer.class);
    protected Map<String, Map<String, Object>> wireFormatOptions;
    protected Map<String, Object> autoTransportOptions;
    protected Set<String> enabledProtocols;
    protected final Map<String, ProtocolVerifier> protocolVerifiers = new ConcurrentHashMap<String, ProtocolVerifier>();
    protected BrokerService brokerService;
    protected final ThreadPoolExecutor newConnectionExecutor;
    protected final ThreadPoolExecutor protocolDetectionExecutor;
    protected int maxConnectionThreadPoolSize = Integer.MAX_VALUE;
    protected int protocolDetectionTimeOut = 30000;
    private static final FactoryFinder TRANSPORT_FACTORY_FINDER = new FactoryFinder("META-INF/services/org/apache/activemq/transport/");
    private final ConcurrentMap<String, TransportFactory> transportFactories = new ConcurrentHashMap<String, TransportFactory>();
    private static final FactoryFinder WIREFORMAT_FACTORY_FINDER = new FactoryFinder("META-INF/services/org/apache/activemq/wireformat/");

    public WireFormatFactory findWireFormatFactory(String scheme2, Map<String, Map<String, Object>> options) throws IOException {
        WireFormatFactory wff = null;
        try {
            wff = (WireFormatFactory)WIREFORMAT_FACTORY_FINDER.newInstance(scheme2);
            if (options != null) {
                HashMap<String, Object> wfOptions = new HashMap<String, Object>();
                if (options.get(AutoTransportUtils.ALL) != null) {
                    wfOptions.putAll(options.get(AutoTransportUtils.ALL));
                }
                if (options.get(scheme2) != null) {
                    wfOptions.putAll(options.get(scheme2));
                }
                IntrospectionSupport.setProperties(wff, wfOptions);
            }
            return wff;
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create("Could not create wire format factory for: " + scheme2 + ", reason: " + e, e);
        }
    }

    public TransportFactory findTransportFactory(String scheme2, Map<String, ?> options) throws IOException {
        TransportFactory tf;
        scheme2 = this.append(scheme2, "nio");
        if ((scheme2 = this.append(scheme2, "ssl")).isEmpty()) {
            scheme2 = "tcp";
        }
        if ((tf = (TransportFactory)this.transportFactories.get(scheme2)) == null) {
            try {
                tf = (TransportFactory)TRANSPORT_FACTORY_FINDER.newInstance(scheme2);
                if (options != null) {
                    IntrospectionSupport.setProperties(tf, options);
                }
                this.transportFactories.put(scheme2, tf);
            }
            catch (Throwable e) {
                throw IOExceptionSupport.create("Transport scheme NOT recognized: [" + scheme2 + "]", e);
            }
        }
        return tf;
    }

    protected String append(String currentScheme, String scheme2) {
        if (this.getBindLocation().getScheme().contains(scheme2)) {
            if (!currentScheme.isEmpty()) {
                currentScheme = currentScheme + "+";
            }
            currentScheme = currentScheme + scheme2;
        }
        return currentScheme;
    }

    public AutoTcpTransportServer(TcpTransportFactory transportFactory, URI location, ServerSocketFactory serverSocketFactory, BrokerService brokerService, Set<String> enabledProtocols) throws IOException, URISyntaxException {
        super(transportFactory, location, serverSocketFactory);
        this.newConnectionExecutor = new ThreadPoolExecutor(this.maxConnectionThreadPoolSize, this.maxConnectionThreadPoolSize, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        this.newConnectionExecutor.allowCoreThreadTimeOut(true);
        this.protocolDetectionExecutor = new ThreadPoolExecutor(this.maxConnectionThreadPoolSize, this.maxConnectionThreadPoolSize, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        this.protocolDetectionExecutor.allowCoreThreadTimeOut(true);
        this.brokerService = brokerService;
        this.enabledProtocols = enabledProtocols;
        this.initProtocolVerifiers();
    }

    public int getMaxConnectionThreadPoolSize() {
        return this.maxConnectionThreadPoolSize;
    }

    public void setMaxConnectionThreadPoolSize(int maxConnectionThreadPoolSize) {
        this.maxConnectionThreadPoolSize = maxConnectionThreadPoolSize;
        this.newConnectionExecutor.setCorePoolSize(maxConnectionThreadPoolSize);
        this.newConnectionExecutor.setMaximumPoolSize(maxConnectionThreadPoolSize);
        this.protocolDetectionExecutor.setCorePoolSize(maxConnectionThreadPoolSize);
        this.protocolDetectionExecutor.setMaximumPoolSize(maxConnectionThreadPoolSize);
    }

    public void setProtocolDetectionTimeOut(int protocolDetectionTimeOut) {
        this.protocolDetectionTimeOut = protocolDetectionTimeOut;
    }

    @Override
    public void setWireFormatFactory(WireFormatFactory factory2) {
        super.setWireFormatFactory(factory2);
        this.initOpenWireProtocolVerifier();
    }

    protected void initProtocolVerifiers() {
        this.initOpenWireProtocolVerifier();
        if (this.isAllProtocols() || this.enabledProtocols.contains(AutoTransportUtils.AMQP)) {
            this.protocolVerifiers.put(AutoTransportUtils.AMQP, new AmqpProtocolVerifier());
        }
        if (this.isAllProtocols() || this.enabledProtocols.contains(AutoTransportUtils.STOMP)) {
            this.protocolVerifiers.put(AutoTransportUtils.STOMP, new StompProtocolVerifier());
        }
        if (this.isAllProtocols() || this.enabledProtocols.contains(AutoTransportUtils.MQTT)) {
            this.protocolVerifiers.put(AutoTransportUtils.MQTT, new MqttProtocolVerifier());
        }
    }

    protected void initOpenWireProtocolVerifier() {
        if (this.isAllProtocols() || this.enabledProtocols.contains(AutoTransportUtils.OPENWIRE)) {
            OpenWireProtocolVerifier owpv = this.wireFormatFactory instanceof OpenWireFormatFactory ? new OpenWireProtocolVerifier(((OpenWireFormatFactory)this.wireFormatFactory).isSizePrefixDisabled()) : new OpenWireProtocolVerifier(new OpenWireFormatFactory().isSizePrefixDisabled());
            this.protocolVerifiers.put(AutoTransportUtils.OPENWIRE, owpv);
        }
    }

    protected boolean isAllProtocols() {
        return this.enabledProtocols == null || this.enabledProtocols.isEmpty();
    }

    @Override
    protected void handleSocket(final Socket socket) {
        final AutoTcpTransportServer server = this;
        this.newConnectionExecutor.submit(new Runnable(){

            @Override
            public void run() {
                server.doHandleSocket(socket);
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected TcpTransportServer.TransportInfo configureTransport(TcpTransportServer server, Socket socket) throws Exception {
        final InputStream is = socket.getInputStream();
        final AtomicInteger readBytes = new AtomicInteger(0);
        final ByteBuffer data = ByteBuffer.allocate(8);
        Future<?> future = this.protocolDetectionExecutor.submit(new Runnable(){

            @Override
            public void run() {
                try {
                    do {
                        int read;
                        if ((read = is.read()) == -1) {
                            throw new IOException("Connection failed, stream is closed.");
                        }
                        data.put((byte)read);
                        readBytes.incrementAndGet();
                    } while (readBytes.get() < 8 && !Thread.interrupted());
                }
                catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        });
        try {
            this.waitForProtocolDetectionFinish(future, readBytes);
        }
        finally {
            future.cancel(true);
        }
        data.flip();
        ProtocolInfo protocolInfo = this.detectProtocol(data.array());
        TcpTransport.InitBuffer initBuffer = new TcpTransport.InitBuffer(readBytes.get(), ByteBuffer.allocate(readBytes.get()));
        initBuffer.buffer.put(data.array());
        if (protocolInfo.detectedTransportFactory instanceof BrokerServiceAware) {
            ((BrokerServiceAware)((Object)protocolInfo.detectedTransportFactory)).setBrokerService(this.brokerService);
        }
        WireFormat format = protocolInfo.detectedWireFormatFactory.createWireFormat();
        TcpTransport transport = this.createTransport(socket, format, protocolInfo.detectedTransportFactory, initBuffer);
        return new TcpTransportServer.TransportInfo(this, format, transport, protocolInfo.detectedTransportFactory);
    }

    protected void waitForProtocolDetectionFinish(Future<?> future, AtomicInteger readBytes) throws Exception {
        try {
            if (this.protocolDetectionTimeOut > 0) {
                future.get(this.protocolDetectionTimeOut, TimeUnit.MILLISECONDS);
            } else {
                future.get();
            }
        }
        catch (TimeoutException e) {
            throw new InactivityIOException("Client timed out before wire format could be detected.  8 bytes are required to detect the protocol but only: " + readBytes.get() + " byte(s) were sent.");
        }
    }

    protected TcpTransport createTransport(Socket socket, WireFormat format, TcpTransportFactory detectedTransportFactory, TcpTransport.InitBuffer initBuffer) throws IOException {
        return new TcpTransport(format, socket, initBuffer);
    }

    public void setWireFormatOptions(Map<String, Map<String, Object>> wireFormatOptions) {
        this.wireFormatOptions = wireFormatOptions;
    }

    public void setEnabledProtocols(Set<String> enabledProtocols) {
        this.enabledProtocols = enabledProtocols;
    }

    public void setAutoTransportOptions(Map<String, Object> autoTransportOptions) {
        this.autoTransportOptions = autoTransportOptions;
        if (autoTransportOptions.get("protocols") != null) {
            this.enabledProtocols = AutoTransportUtils.parseProtocols((String)autoTransportOptions.get("protocols"));
        }
    }

    @Override
    protected void doStop(ServiceStopper stopper) throws Exception {
        if (this.newConnectionExecutor != null) {
            this.newConnectionExecutor.shutdownNow();
            try {
                if (!this.newConnectionExecutor.awaitTermination(3L, TimeUnit.SECONDS)) {
                    LOG.warn("Auto Transport newConnectionExecutor didn't shutdown cleanly");
                }
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        if (this.protocolDetectionExecutor != null) {
            this.protocolDetectionExecutor.shutdownNow();
            try {
                if (!this.protocolDetectionExecutor.awaitTermination(3L, TimeUnit.SECONDS)) {
                    LOG.warn("Auto Transport protocolDetectionExecutor didn't shutdown cleanly");
                }
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        super.doStop(stopper);
    }

    protected ProtocolInfo detectProtocol(byte[] buffer) throws IOException {
        TcpTransportFactory detectedTransportFactory = this.transportFactory;
        WireFormatFactory detectedWireFormatFactory = this.wireFormatFactory;
        boolean found = false;
        for (String scheme2 : this.protocolVerifiers.keySet()) {
            if (!this.protocolVerifiers.get(scheme2).isProtocol(buffer)) continue;
            LOG.debug("Detected protocol " + scheme2);
            detectedWireFormatFactory = this.findWireFormatFactory(scheme2, this.wireFormatOptions);
            if (scheme2.equals("default")) {
                scheme2 = "";
            }
            detectedTransportFactory = (TcpTransportFactory)this.findTransportFactory(scheme2, this.transportOptions);
            found = true;
            break;
        }
        if (!found) {
            throw new IllegalStateException("Could not detect the wire format");
        }
        return new ProtocolInfo(detectedTransportFactory, detectedWireFormatFactory);
    }

    protected class ProtocolInfo {
        public final TcpTransportFactory detectedTransportFactory;
        public final WireFormatFactory detectedWireFormatFactory;

        public ProtocolInfo(TcpTransportFactory detectedTransportFactory, WireFormatFactory detectedWireFormatFactory) {
            this.detectedTransportFactory = detectedTransportFactory;
            this.detectedWireFormatFactory = detectedWireFormatFactory;
        }
    }
}

