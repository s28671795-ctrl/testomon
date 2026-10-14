/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.nio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.security.cert.X509Certificate;
import java.util.concurrent.CountDownLatch;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transport.nio.NIOInputStream;
import org.apache.activemq.transport.nio.NIOOutputStream;
import org.apache.activemq.transport.nio.NIOTransport;
import org.apache.activemq.transport.nio.SelectorManager;
import org.apache.activemq.transport.nio.SelectorSelection;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.wireformat.WireFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NIOSSLTransport
extends NIOTransport {
    private static final Logger LOG = LoggerFactory.getLogger(NIOSSLTransport.class);
    protected boolean needClientAuth;
    protected boolean wantClientAuth;
    protected String[] enabledCipherSuites;
    protected String[] enabledProtocols;
    protected boolean verifyHostName = false;
    protected SSLContext sslContext;
    protected SSLEngine sslEngine;
    protected SSLSession sslSession;
    protected volatile boolean handshakeInProgress = false;
    protected SSLEngineResult.Status status = null;
    protected SSLEngineResult.HandshakeStatus handshakeStatus = null;
    protected TaskRunnerFactory taskRunnerFactory;
    volatile boolean hasSslEngine = false;
    protected final CountDownLatch initialized = new CountDownLatch(1);
    boolean openWireInititialized = false;

    public NIOSSLTransport(WireFormat wireFormat, SocketFactory socketFactory, URI remoteLocation, URI localLocation) throws UnknownHostException, IOException {
        super(wireFormat, socketFactory, remoteLocation, localLocation);
    }

    public NIOSSLTransport(WireFormat wireFormat, Socket socket, SSLEngine engine, TcpTransport.InitBuffer initBuffer, ByteBuffer inputBuffer) throws IOException {
        super(wireFormat, socket, initBuffer);
        this.sslEngine = engine;
        if (engine != null) {
            this.sslSession = engine.getSession();
        }
        this.inputBuffer = inputBuffer;
    }

    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initializeStreams() throws IOException {
        if (this.sslEngine != null) {
            this.hasSslEngine = true;
        }
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
            if (!this.hasSslEngine) {
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
            }
            outputStream2 = new NIOOutputStream(this.channel);
            outputStream2.setEngine(this.sslEngine);
            this.dataOut = new DataOutputStream(outputStream2);
            this.buffOut = outputStream2;
            if (!this.hasSslEngine) {
                this.sslEngine.beginHandshake();
            }
            this.handshakeStatus = this.sslEngine.getHandshakeStatus();
            if (!this.hasSslEngine) {
                this.doHandshake();
            }
            this.selection = SelectorManager.getInstance().register(this.channel, new SelectorManager.Listener(){

                @Override
                public void onSelect(SelectorSelection selection) {
                    try {
                        NIOSSLTransport.this.initialized.await();
                    }
                    catch (InterruptedException error) {
                        NIOSSLTransport.this.onException(IOExceptionSupport.create(error));
                    }
                    NIOSSLTransport.this.serviceRead();
                }

                @Override
                public void onError(SelectorSelection selection, Throwable error) {
                    if (error instanceof IOException) {
                        NIOSSLTransport.this.onException((IOException)error);
                    } else {
                        NIOSSLTransport.this.onException(IOExceptionSupport.create(error));
                    }
                }
            });
            this.doInit();
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

    protected void doInit() throws Exception {
        this.taskRunnerFactory.execute(new Runnable(){

            @Override
            public void run() {
                NIOSSLTransport.this.serviceRead();
                NIOSSLTransport.this.initialized.countDown();
            }
        });
    }

    protected void doOpenWireInit() throws Exception {
        if (this.initBuffer != null && !this.openWireInititialized && this.wireFormat instanceof OpenWireFormat) {
            this.initBuffer.buffer.flip();
            if (this.initBuffer.buffer.hasRemaining()) {
                this.nextFrameSize = -1;
                this.receiveCounter += this.initBuffer.readSize;
                this.processCommand(this.initBuffer.buffer);
                this.processCommand(this.initBuffer.buffer);
                this.initBuffer.buffer.clear();
                this.openWireInititialized = true;
            }
        }
    }

    protected void finishHandshake() throws Exception {
        if (this.handshakeInProgress) {
            this.handshakeInProgress = false;
            this.nextFrameSize = -1;
            this.sslSession = this.sslEngine.getSession();
        }
    }

    @Override
    public void serviceRead() {
        try {
            if (this.handshakeInProgress) {
                this.doHandshake();
            }
            this.doOpenWireInit();
            ByteBuffer plain = ByteBuffer.allocate(this.sslSession.getApplicationBufferSize());
            plain.position(plain.limit());
            while (true) {
                if (!plain.hasRemaining()) {
                    int readCount = this.secureRead(plain);
                    if (readCount == 0) break;
                    if (readCount == -1) {
                        this.onException(new EOFException());
                        this.selection.close();
                        break;
                    }
                    this.receiveCounter += readCount;
                }
                if (this.status != SSLEngineResult.Status.OK || this.handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) continue;
                this.processCommand(plain);
            }
        }
        catch (IOException e) {
            this.onException(e);
        }
        catch (Throwable e) {
            this.onException(IOExceptionSupport.create(e));
        }
    }

    protected void processCommand(ByteBuffer plain) throws Exception {
        if (this.nextFrameSize == -1) {
            long maxFrameSize;
            if (plain.remaining() < 32) {
                if (this.currentBuffer == null) {
                    this.currentBuffer = ByteBuffer.allocate(4);
                }
                while (this.currentBuffer.hasRemaining() && plain.hasRemaining()) {
                    this.currentBuffer.put(plain.get());
                }
                if (this.currentBuffer.hasRemaining()) {
                    return;
                }
                this.currentBuffer.flip();
                this.nextFrameSize = this.currentBuffer.getInt();
            } else if (this.currentBuffer != null) {
                while (this.currentBuffer.hasRemaining()) {
                    this.currentBuffer.put(plain.get());
                }
                this.currentBuffer.flip();
                this.nextFrameSize = this.currentBuffer.getInt();
            } else {
                this.nextFrameSize = plain.getInt();
            }
            if (this.wireFormat instanceof OpenWireFormat && (long)this.nextFrameSize > (maxFrameSize = ((OpenWireFormat)this.wireFormat).getMaxFrameSize())) {
                throw new IOException("Frame size of " + this.nextFrameSize / 0x100000 + " MB larger than max allowed " + maxFrameSize / 0x100000L + " MB");
            }
            this.currentBuffer = ByteBuffer.allocate(this.nextFrameSize + 4);
            this.currentBuffer.putInt(this.nextFrameSize);
        } else if (this.currentBuffer != null) {
            if (this.currentBuffer.remaining() >= plain.remaining()) {
                this.currentBuffer.put(plain);
            } else {
                byte[] fill = new byte[this.currentBuffer.remaining()];
                plain.get(fill);
                this.currentBuffer.put(fill);
            }
            if (this.currentBuffer.hasRemaining()) {
                return;
            }
            this.currentBuffer.flip();
            Object command = this.wireFormat.unmarshal(new DataInputStream(new NIOInputStream(this.currentBuffer)));
            this.doConsume(command);
            this.nextFrameSize = -1;
            this.currentBuffer = null;
        }
    }

    protected int secureRead(ByteBuffer plain) throws Exception {
        SSLEngineResult res;
        if (this.inputBuffer.position() == 0 || !this.inputBuffer.hasRemaining() || this.status == SSLEngineResult.Status.BUFFER_UNDERFLOW) {
            int bytesRead = this.channel.read(this.inputBuffer);
            if (bytesRead == 0 && !this.sslEngine.getHandshakeStatus().equals((Object)SSLEngineResult.HandshakeStatus.NEED_UNWRAP)) {
                return 0;
            }
            if (bytesRead == -1) {
                this.sslEngine.closeInbound();
                if (this.inputBuffer.position() == 0 || this.status == SSLEngineResult.Status.BUFFER_UNDERFLOW) {
                    return -1;
                }
            }
        }
        plain.clear();
        this.inputBuffer.flip();
        while ((res = this.sslEngine.unwrap(this.inputBuffer, plain)).getStatus() == SSLEngineResult.Status.OK && res.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_UNWRAP && res.bytesProduced() == 0) {
        }
        if (res.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
            this.finishHandshake();
        }
        this.status = res.getStatus();
        this.handshakeStatus = res.getHandshakeStatus();
        if (this.status == SSLEngineResult.Status.CLOSED) {
            this.sslEngine.closeInbound();
            return -1;
        }
        this.inputBuffer.compact();
        plain.flip();
        return plain.remaining();
    }

    protected void doHandshake() throws Exception {
        this.handshakeInProgress = true;
        Selector selector = null;
        SelectionKey key = null;
        boolean readable = true;
        try {
            block18: while (true) {
                SSLEngineResult.HandshakeStatus handshakeStatus = this.sslEngine.getHandshakeStatus();
                switch (handshakeStatus) {
                    case NEED_UNWRAP: {
                        if (readable) {
                            this.secureRead(ByteBuffer.allocate(this.sslSession.getApplicationBufferSize()));
                        }
                        if (this.status != SSLEngineResult.Status.BUFFER_UNDERFLOW) break;
                        long now = System.currentTimeMillis();
                        if (selector == null) {
                            selector = Selector.open();
                            key = this.channel.register(selector, 1);
                        } else {
                            key.interestOps(1);
                        }
                        int keyCount = selector.select(this.getSoTimeout());
                        if (keyCount == 0 && this.getSoTimeout() > 0 && System.currentTimeMillis() - now >= (long)this.getSoTimeout()) {
                            throw new SocketTimeoutException("Timeout during handshake");
                        }
                        readable = key.isReadable();
                        break;
                    }
                    case NEED_TASK: {
                        Runnable task;
                        while ((task = this.sslEngine.getDelegatedTask()) != null) {
                            task.run();
                        }
                        continue block18;
                    }
                    case NEED_WRAP: {
                        ((NIOOutputStream)this.buffOut).write(ByteBuffer.allocate(0));
                        break;
                    }
                    case FINISHED: 
                    case NOT_HANDSHAKING: {
                        this.finishHandshake();
                        return;
                    }
                }
            }
        }
        finally {
            if (key != null) {
                try {
                    key.cancel();
                }
                catch (Exception exception) {}
            }
            if (selector != null) {
                try {
                    selector.close();
                }
                catch (Exception exception) {}
            }
        }
    }

    @Override
    protected void doStart() throws Exception {
        this.taskRunnerFactory = new TaskRunnerFactory("ActiveMQ NIOSSLTransport Task");
        super.doStart();
    }

    @Override
    protected void doStop(ServiceStopper stopper) throws Exception {
        this.initialized.countDown();
        if (this.taskRunnerFactory != null) {
            this.taskRunnerFactory.shutdownNow();
            this.taskRunnerFactory = null;
        }
        if (this.channel != null) {
            this.channel.close();
            this.channel = null;
        }
        super.doStop(stopper);
    }

    @Override
    public void doConsume(Object command) {
        if (command instanceof ConnectionInfo) {
            ConnectionInfo connectionInfo = (ConnectionInfo)command;
            connectionInfo.setTransportContext(this.getPeerCertificates());
        }
        super.doConsume(command);
    }

    @Override
    public X509Certificate[] getPeerCertificates() {
        X509Certificate[] clientCertChain;
        block3: {
            clientCertChain = null;
            try {
                if (this.sslEngine.getSession() != null) {
                    clientCertChain = (X509Certificate[])this.sslEngine.getSession().getPeerCertificates();
                }
            }
            catch (SSLPeerUnverifiedException e) {
                if (!LOG.isTraceEnabled()) break block3;
                LOG.trace("Failed to get peer certificates.", e);
            }
        }
        return clientCertChain;
    }

    public boolean isNeedClientAuth() {
        return this.needClientAuth;
    }

    public void setNeedClientAuth(boolean needClientAuth) {
        this.needClientAuth = needClientAuth;
    }

    public boolean isWantClientAuth() {
        return this.wantClientAuth;
    }

    public void setWantClientAuth(boolean wantClientAuth) {
        this.wantClientAuth = wantClientAuth;
    }

    public String[] getEnabledCipherSuites() {
        return this.enabledCipherSuites;
    }

    public void setEnabledCipherSuites(String[] enabledCipherSuites) {
        this.enabledCipherSuites = enabledCipherSuites;
    }

    public String[] getEnabledProtocols() {
        return this.enabledProtocols;
    }

    public void setEnabledProtocols(String[] enabledProtocols) {
        this.enabledProtocols = enabledProtocols;
    }

    public boolean isVerifyHostName() {
        return this.verifyHostName;
    }

    public void setVerifyHostName(boolean verifyHostName) {
        this.verifyHostName = verifyHostName;
    }
}

