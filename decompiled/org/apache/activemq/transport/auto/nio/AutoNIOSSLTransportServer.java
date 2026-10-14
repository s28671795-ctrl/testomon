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
import java.util.Set;
import java.util.concurrent.Future;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.BrokerServiceAware;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.auto.AutoTcpTransportServer;
import org.apache.activemq.transport.nio.AutoInitNioSSLTransport;
import org.apache.activemq.transport.nio.NIOSSLTransport;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.transport.tcp.TcpTransportFactory;
import org.apache.activemq.transport.tcp.TcpTransportServer;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.wireformat.WireFormat;

public class AutoNIOSSLTransportServer
extends AutoTcpTransportServer {
    private SSLContext context;
    private boolean needClientAuth;
    private boolean wantClientAuth;

    public AutoNIOSSLTransportServer(SSLContext context, TcpTransportFactory transportFactory, URI location, ServerSocketFactory serverSocketFactory, BrokerService brokerService, Set<String> enabledProtocols) throws IOException, URISyntaxException {
        super(transportFactory, location, serverSocketFactory, brokerService, enabledProtocols);
        this.context = context;
    }

    protected Transport createTransport(Socket socket, WireFormat format, SSLEngine engine, TcpTransport.InitBuffer initBuffer, ByteBuffer inputBuffer, TcpTransportFactory detectedFactory) throws IOException {
        NIOSSLTransport transport = new NIOSSLTransport(format, socket, engine, initBuffer, inputBuffer);
        if (this.context != null) {
            transport.setSslContext(this.context);
        }
        transport.setNeedClientAuth(this.needClientAuth);
        transport.setWantClientAuth(this.wantClientAuth);
        return transport;
    }

    @Override
    protected TcpTransport createTransport(Socket socket, WireFormat format) throws IOException {
        throw new UnsupportedOperationException("method not supported");
    }

    @Override
    public boolean isSslServer() {
        return true;
    }

    public boolean isNeedClientAuth() {
        return this.needClientAuth;
    }

    public void setNeedClientAuth(boolean value) {
        this.needClientAuth = value;
    }

    public boolean isWantClientAuth() {
        return this.wantClientAuth;
    }

    public void setWantClientAuth(boolean value) {
        this.wantClientAuth = value;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected TcpTransportServer.TransportInfo configureTransport(TcpTransportServer server, Socket socket) throws Exception {
        final AutoInitNioSSLTransport in = new AutoInitNioSSLTransport(this.wireFormatFactory.createWireFormat(), socket);
        if (this.context != null) {
            in.setSslContext(this.context);
        }
        if (this.transportOptions != null) {
            IntrospectionSupport.setProperties(in, new HashMap(this.transportOptions));
        }
        Future<?> future = this.protocolDetectionExecutor.submit(new Runnable(){

            @Override
            public void run() {
                try {
                    in.start();
                }
                catch (Exception e) {
                    throw new IllegalStateException("Could not complete Transport start", e);
                }
                int attempts = 0;
                do {
                    if (attempts > 0) {
                        try {
                            int sleep = attempts >= 1024 ? 1024 : 4 * attempts;
                            Thread.sleep(sleep);
                        }
                        catch (InterruptedException e) {
                            break;
                        }
                    }
                    in.serviceRead();
                    ++attempts;
                } while (in.getReadSize().get() < 8 && !Thread.interrupted());
            }
        });
        try {
            this.waitForProtocolDetectionFinish(future, in.getReadSize());
        }
        finally {
            future.cancel(true);
        }
        in.stop();
        TcpTransport.InitBuffer initBuffer = new TcpTransport.InitBuffer(in.getReadSize().get(), ByteBuffer.allocate(in.getReadData().length));
        initBuffer.buffer.put(in.getReadData());
        AutoTcpTransportServer.ProtocolInfo protocolInfo = this.detectProtocol(in.getReadData());
        if (protocolInfo.detectedTransportFactory instanceof BrokerServiceAware) {
            ((BrokerServiceAware)((Object)protocolInfo.detectedTransportFactory)).setBrokerService(this.brokerService);
        }
        WireFormat format = protocolInfo.detectedWireFormatFactory.createWireFormat();
        Transport transport = this.createTransport(socket, format, in.getSslSession(), initBuffer, in.getInputBuffer(), protocolInfo.detectedTransportFactory);
        return new TcpTransportServer.TransportInfo(this, format, transport, protocolInfo.detectedTransportFactory);
    }
}

