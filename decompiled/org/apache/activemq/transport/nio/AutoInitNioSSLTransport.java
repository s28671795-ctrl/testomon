/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.nio;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLParameters;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transport.nio.NIOOutputStream;
import org.apache.activemq.transport.nio.NIOSSLTransport;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.wireformat.WireFormat;

public class AutoInitNioSSLTransport
extends NIOSSLTransport {
    private volatile byte[] readData;
    private final AtomicInteger readSize = new AtomicInteger();

    public AutoInitNioSSLTransport(WireFormat wireFormat, SocketFactory socketFactory, URI remoteLocation, URI localLocation) throws UnknownHostException, IOException {
        super(wireFormat, socketFactory, remoteLocation, localLocation);
    }

    public AutoInitNioSSLTransport(WireFormat wireFormat, Socket socket) throws IOException {
        super(wireFormat, socket, null, null, null);
    }

    @Override
    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    public ByteBuffer getInputBuffer() {
        return this.inputBuffer;
    }

    @Override
    protected void initializeStreams() throws IOException {
        NIOOutputStream outputStream2 = null;
        try {
            this.channel = this.socket.getChannel();
            this.channel.configureBlocking(false);
            if (this.sslContext == null) {
                this.sslContext = SSLContext.getDefault();
            }
            String remoteHost = null;
            int remotePort = -1;
            try {
                URI remoteAddress = new URI(this.getRemoteAddress());
                remoteHost = remoteAddress.getHost();
                remotePort = remoteAddress.getPort();
            }
            catch (Exception remoteAddress) {
                // empty catch block
            }
            this.sslEngine = remoteHost != null && remotePort != -1 ? this.sslContext.createSSLEngine(remoteHost, remotePort) : this.sslContext.createSSLEngine();
            if (this.verifyHostName) {
                SSLParameters sslParams = new SSLParameters();
                sslParams.setEndpointIdentificationAlgorithm("HTTPS");
                this.sslEngine.setSSLParameters(sslParams);
            }
            this.sslEngine.setUseClientMode(false);
            if (this.enabledCipherSuites != null) {
                this.sslEngine.setEnabledCipherSuites(this.enabledCipherSuites);
            }
            if (this.enabledProtocols != null) {
                this.sslEngine.setEnabledProtocols(this.enabledProtocols);
            }
            if (this.wantClientAuth) {
                this.sslEngine.setWantClientAuth(this.wantClientAuth);
            }
            if (this.needClientAuth) {
                this.sslEngine.setNeedClientAuth(this.needClientAuth);
            }
            this.sslSession = this.sslEngine.getSession();
            this.inputBuffer = ByteBuffer.allocate(this.sslSession.getPacketBufferSize());
            this.inputBuffer.clear();
            outputStream2 = new NIOOutputStream(this.channel);
            outputStream2.setEngine(this.sslEngine);
            this.dataOut = new DataOutputStream(outputStream2);
            this.buffOut = outputStream2;
            this.sslEngine.beginHandshake();
            this.handshakeStatus = this.sslEngine.getHandshakeStatus();
            this.doHandshake();
        }
        catch (Exception e) {
            try {
                if (outputStream2 != null) {
                    outputStream2.close();
                }
                super.closeStreams();
            }
            catch (Exception exception) {
                // empty catch block
            }
            throw new IOException(e);
        }
    }

    @Override
    protected void doOpenWireInit() throws Exception {
    }

    public SSLEngine getSslSession() {
        return this.sslEngine;
    }

    public byte[] getReadData() {
        return this.readData != null ? this.readData : new byte[]{};
    }

    public AtomicInteger getReadSize() {
        return this.readSize;
    }

    @Override
    public void serviceRead() {
        try {
            if (this.handshakeInProgress) {
                this.doHandshake();
            }
            ByteBuffer plain = ByteBuffer.allocate(this.sslSession.getApplicationBufferSize());
            plain.position(plain.limit());
            while (true) {
                if (!plain.hasRemaining()) {
                    int readCount = this.secureRead(plain);
                    if (readCount == 0) break;
                    if (readCount == -1) {
                        this.onException(new EOFException());
                        break;
                    }
                    this.receiveCounter += readCount;
                    this.readSize.addAndGet(readCount);
                }
                if (this.status != SSLEngineResult.Status.OK || this.handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) continue;
                this.processCommand(plain);
                if (this.receiveCounter >= 8) break;
            }
        }
        catch (IOException e) {
            this.onException(e);
        }
        catch (Throwable e) {
            this.onException(IOExceptionSupport.create(e));
        }
    }

    @Override
    protected void processCommand(ByteBuffer plain) throws Exception {
        ByteBuffer newBuffer = ByteBuffer.allocate(this.receiveCounter);
        if (this.readData != null) {
            newBuffer.put(this.readData);
        }
        newBuffer.put(plain);
        newBuffer.flip();
        this.readData = newBuffer.array();
    }

    @Override
    public void doStart() throws Exception {
        this.taskRunnerFactory = new TaskRunnerFactory("ActiveMQ NIOSSLTransport Task");
        this.connect();
    }

    @Override
    protected void doStop(ServiceStopper stopper) throws Exception {
        if (this.taskRunnerFactory != null) {
            this.taskRunnerFactory.shutdownNow();
            this.taskRunnerFactory = null;
        }
    }
}

