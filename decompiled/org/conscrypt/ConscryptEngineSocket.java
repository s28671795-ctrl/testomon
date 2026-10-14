/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;
import org.conscrypt.AllocatedBuffer;
import org.conscrypt.ApplicationProtocolSelector;
import org.conscrypt.ApplicationProtocolSelectorAdapter;
import org.conscrypt.BufferAllocator;
import org.conscrypt.ConscryptEngine;
import org.conscrypt.EmptyArray;
import org.conscrypt.HandshakeListener;
import org.conscrypt.OpenSSLSocketImpl;
import org.conscrypt.Platform;
import org.conscrypt.SSLParametersImpl;
import org.conscrypt.SSLUtils;

class ConscryptEngineSocket
extends OpenSSLSocketImpl
implements SSLParametersImpl.AliasChooser {
    private static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocate(0);
    private final ConscryptEngine engine;
    private final Object stateLock = new Object();
    private final Object handshakeLock = new Object();
    private SSLOutputStream out;
    private SSLInputStream in;
    private BufferAllocator bufferAllocator = ConscryptEngine.getDefaultBufferAllocator();
    private int state = 0;

    ConscryptEngineSocket(SSLParametersImpl sslParameters) throws IOException {
        this.engine = ConscryptEngineSocket.newEngine(sslParameters, this);
    }

    ConscryptEngineSocket(String hostname, int port, SSLParametersImpl sslParameters) throws IOException {
        super(hostname, port);
        this.engine = ConscryptEngineSocket.newEngine(sslParameters, this);
    }

    ConscryptEngineSocket(InetAddress address, int port, SSLParametersImpl sslParameters) throws IOException {
        super(address, port);
        this.engine = ConscryptEngineSocket.newEngine(sslParameters, this);
    }

    ConscryptEngineSocket(String hostname, int port, InetAddress clientAddress, int clientPort, SSLParametersImpl sslParameters) throws IOException {
        super(hostname, port, clientAddress, clientPort);
        this.engine = ConscryptEngineSocket.newEngine(sslParameters, this);
    }

    ConscryptEngineSocket(InetAddress address, int port, InetAddress clientAddress, int clientPort, SSLParametersImpl sslParameters) throws IOException {
        super(address, port, clientAddress, clientPort);
        this.engine = ConscryptEngineSocket.newEngine(sslParameters, this);
    }

    ConscryptEngineSocket(Socket socket, String hostname, int port, boolean autoClose, SSLParametersImpl sslParameters) throws IOException {
        super(socket, hostname, port, autoClose);
        this.engine = ConscryptEngineSocket.newEngine(sslParameters, this);
    }

    private static ConscryptEngine newEngine(SSLParametersImpl sslParameters, ConscryptEngineSocket socket) {
        SSLParametersImpl modifiedParams = Platform.supportsX509ExtendedTrustManager() ? sslParameters.cloneWithTrustManager(ConscryptEngineSocket.getDelegatingTrustManager(sslParameters.getX509TrustManager(), socket)) : sslParameters;
        ConscryptEngine engine = new ConscryptEngine(modifiedParams, socket.peerInfoProvider(), socket);
        engine.setHandshakeListener(new HandshakeListener(){

            @Override
            public void onHandshakeFinished() {
                ConscryptEngineSocket.this.onHandshakeFinished();
            }
        });
        engine.setUseClientMode(sslParameters.getUseClientMode());
        return engine;
    }

    private static X509TrustManager getDelegatingTrustManager(X509TrustManager delegate, final ConscryptEngineSocket socket) {
        if (delegate instanceof X509ExtendedTrustManager) {
            final X509ExtendedTrustManager extendedDelegate = (X509ExtendedTrustManager)delegate;
            return new X509ExtendedTrustManager(){

                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s2, Socket socket2) throws CertificateException {
                    throw new AssertionError((Object)"Should not be called");
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s2, Socket socket2) throws CertificateException {
                    throw new AssertionError((Object)"Should not be called");
                }

                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s2, SSLEngine sslEngine) throws CertificateException {
                    extendedDelegate.checkClientTrusted(x509Certificates, s2, socket);
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s2, SSLEngine sslEngine) throws CertificateException {
                    extendedDelegate.checkServerTrusted(x509Certificates, s2, socket);
                }

                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s2) throws CertificateException {
                    extendedDelegate.checkClientTrusted(x509Certificates, s2);
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s2) throws CertificateException {
                    extendedDelegate.checkServerTrusted(x509Certificates, s2);
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return extendedDelegate.getAcceptedIssuers();
                }
            };
        }
        return delegate;
    }

    @Override
    public final SSLParameters getSSLParameters() {
        return this.engine.getSSLParameters();
    }

    @Override
    public final void setSSLParameters(SSLParameters sslParameters) {
        this.engine.setSSLParameters(sslParameters);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void startHandshake() throws IOException {
        this.checkOpen();
        try {
            Object object = this.handshakeLock;
            synchronized (object) {
                Object object2 = this.stateLock;
                synchronized (object2) {
                    if (this.state != 0) {
                        return;
                    }
                    this.state = 2;
                    this.engine.beginHandshake();
                    this.in = new SSLInputStream();
                    this.out = new SSLOutputStream();
                }
                this.doHandshake();
            }
        }
        catch (SSLException e) {
            this.close();
            throw e;
        }
        catch (IOException e) {
            this.close();
            throw e;
        }
        catch (Exception e) {
            this.close();
            throw SSLUtils.toSSLHandshakeException(e);
        }
    }

    private void doHandshake() throws IOException {
        try {
            boolean finished = false;
            block10: while (!finished) {
                switch (this.engine.getHandshakeStatus()) {
                    case NEED_UNWRAP: {
                        if (this.in.processDataFromSocket(EmptyArray.BYTE, 0, 0) >= 0) continue block10;
                        throw SSLUtils.toSSLHandshakeException(new EOFException("connection closed"));
                    }
                    case NEED_WRAP: {
                        this.out.writeInternal(ConscryptEngineSocket.EMPTY_BUFFER);
                        this.out.flushInternal();
                        continue block10;
                    }
                    case NEED_TASK: {
                        throw new IllegalStateException("Engine tasks are unsupported");
                    }
                    case NOT_HANDSHAKING: 
                    case FINISHED: {
                        finished = true;
                        continue block10;
                    }
                }
                throw new IllegalStateException("Unknown handshake status: " + (Object)((Object)this.engine.getHandshakeStatus()));
            }
        }
        catch (SSLException e) {
            this.drainOutgoingQueue();
            this.close();
            throw e;
        }
        catch (IOException e) {
            this.close();
            throw e;
        }
        catch (Exception e) {
            this.close();
            throw SSLUtils.toSSLHandshakeException(e);
        }
    }

    @Override
    public final InputStream getInputStream() throws IOException {
        this.checkOpen();
        this.waitForHandshake();
        return this.in;
    }

    @Override
    public final OutputStream getOutputStream() throws IOException {
        this.checkOpen();
        this.waitForHandshake();
        return this.out;
    }

    @Override
    public final SSLSession getHandshakeSession() {
        return this.engine.handshakeSession();
    }

    @Override
    public final SSLSession getSession() {
        if (this.isConnected()) {
            try {
                this.waitForHandshake();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return this.engine.getSession();
    }

    @Override
    final SSLSession getActiveSession() {
        return this.engine.getSession();
    }

    @Override
    public final boolean getEnableSessionCreation() {
        return this.engine.getEnableSessionCreation();
    }

    @Override
    public final void setEnableSessionCreation(boolean flag) {
        this.engine.setEnableSessionCreation(flag);
    }

    @Override
    public final String[] getSupportedCipherSuites() {
        return this.engine.getSupportedCipherSuites();
    }

    @Override
    public final String[] getEnabledCipherSuites() {
        return this.engine.getEnabledCipherSuites();
    }

    @Override
    public final void setEnabledCipherSuites(String[] suites) {
        this.engine.setEnabledCipherSuites(suites);
    }

    @Override
    public final String[] getSupportedProtocols() {
        return this.engine.getSupportedProtocols();
    }

    @Override
    public final String[] getEnabledProtocols() {
        return this.engine.getEnabledProtocols();
    }

    @Override
    public final void setEnabledProtocols(String[] protocols) {
        this.engine.setEnabledProtocols(protocols);
    }

    @Override
    public final void setHostname(String hostname) {
        this.engine.setHostname(hostname);
        super.setHostname(hostname);
    }

    @Override
    public final void setUseSessionTickets(boolean useSessionTickets) {
        this.engine.setUseSessionTickets(useSessionTickets);
    }

    @Override
    public final void setChannelIdEnabled(boolean enabled) {
        this.engine.setChannelIdEnabled(enabled);
    }

    @Override
    public final byte[] getChannelId() throws SSLException {
        return this.engine.getChannelId();
    }

    @Override
    public final void setChannelIdPrivateKey(PrivateKey privateKey) {
        this.engine.setChannelIdPrivateKey(privateKey);
    }

    @Override
    byte[] getTlsUnique() {
        return this.engine.getTlsUnique();
    }

    @Override
    byte[] exportKeyingMaterial(String label, byte[] context, int length) throws SSLException {
        return this.engine.exportKeyingMaterial(label, context, length);
    }

    @Override
    public final boolean getUseClientMode() {
        return this.engine.getUseClientMode();
    }

    @Override
    public final void setUseClientMode(boolean mode) {
        this.engine.setUseClientMode(mode);
    }

    @Override
    public final boolean getWantClientAuth() {
        return this.engine.getWantClientAuth();
    }

    @Override
    public final boolean getNeedClientAuth() {
        return this.engine.getNeedClientAuth();
    }

    @Override
    public final void setNeedClientAuth(boolean need) {
        this.engine.setNeedClientAuth(need);
    }

    @Override
    public final void setWantClientAuth(boolean want) {
        this.engine.setWantClientAuth(want);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void close() throws IOException {
        int previousState;
        if (this.stateLock == null) {
            return;
        }
        Object object = this.stateLock;
        synchronized (object) {
            previousState = this.state;
            if (this.state == 8) {
                return;
            }
            this.state = 8;
            this.stateLock.notifyAll();
        }
        try {
            this.engine.closeInbound();
            this.engine.closeOutbound();
            if (previousState >= 2) {
                this.drainOutgoingQueue();
                this.engine.closeOutbound();
            }
        }
        finally {
            try {
                super.close();
            }
            finally {
                if (this.in != null) {
                    this.in.release();
                }
            }
        }
    }

    @Override
    public void setHandshakeTimeout(int handshakeTimeoutMilliseconds) throws SocketException {
    }

    @Override
    final void setApplicationProtocols(String[] protocols) {
        this.engine.setApplicationProtocols(protocols);
    }

    @Override
    final String[] getApplicationProtocols() {
        return this.engine.getApplicationProtocols();
    }

    @Override
    public final String getApplicationProtocol() {
        return this.engine.getApplicationProtocol();
    }

    @Override
    public final String getHandshakeApplicationProtocol() {
        return this.engine.getHandshakeApplicationProtocol();
    }

    @Override
    public final void setApplicationProtocolSelector(ApplicationProtocolSelector selector) {
        this.setApplicationProtocolSelector(selector == null ? null : new ApplicationProtocolSelectorAdapter(this, selector));
    }

    @Override
    final void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter selector) {
        this.engine.setApplicationProtocolSelector(selector);
    }

    void setBufferAllocator(BufferAllocator bufferAllocator) {
        this.engine.setBufferAllocator(bufferAllocator);
        this.bufferAllocator = bufferAllocator;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void onHandshakeFinished() {
        boolean notify = false;
        Object object = this.stateLock;
        synchronized (object) {
            if (this.state != 8) {
                if (this.state == 2) {
                    this.state = 4;
                } else if (this.state == 3) {
                    this.state = 5;
                }
                this.stateLock.notifyAll();
                notify = true;
            }
        }
        if (notify) {
            this.notifyHandshakeCompletedListeners();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void waitForHandshake() throws IOException {
        this.startHandshake();
        Object object = this.stateLock;
        synchronized (object) {
            while (this.state != 5 && this.state != 4 && this.state != 8) {
                try {
                    this.stateLock.wait();
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Interrupted waiting for handshake", e);
                }
            }
            if (this.state == 8) {
                throw new SocketException("Socket is closed");
            }
        }
    }

    private void drainOutgoingQueue() {
        try {
            while (this.engine.pendingOutboundEncryptedBytes() > 0) {
                this.out.writeInternal(ConscryptEngineSocket.EMPTY_BUFFER);
                this.out.flushInternal();
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private OutputStream getUnderlyingOutputStream() throws IOException {
        return super.getOutputStream();
    }

    private InputStream getUnderlyingInputStream() throws IOException {
        return super.getInputStream();
    }

    @Override
    public final String chooseServerAlias(X509KeyManager keyManager, String keyType) {
        return keyManager.chooseServerAlias(keyType, null, this);
    }

    @Override
    public final String chooseClientAlias(X509KeyManager keyManager, X500Principal[] issuers, String[] keyTypes) {
        return keyManager.chooseClientAlias(keyTypes, issuers, this);
    }

    private final class SSLInputStream
    extends InputStream {
        private final Object readLock = new Object();
        private final byte[] singleByte = new byte[1];
        private final ByteBuffer fromEngine;
        private final ByteBuffer fromSocket;
        private final int fromSocketArrayOffset;
        private final AllocatedBuffer allocatedBuffer;
        private InputStream socketInputStream;

        SSLInputStream() {
            if (ConscryptEngineSocket.this.bufferAllocator != null) {
                this.allocatedBuffer = ConscryptEngineSocket.this.bufferAllocator.allocateDirectBuffer(ConscryptEngineSocket.this.engine.getSession().getApplicationBufferSize());
                this.fromEngine = this.allocatedBuffer.nioBuffer();
            } else {
                this.allocatedBuffer = null;
                this.fromEngine = ByteBuffer.allocateDirect(ConscryptEngineSocket.this.engine.getSession().getApplicationBufferSize());
            }
            this.fromEngine.flip();
            this.fromSocket = ByteBuffer.allocate(ConscryptEngineSocket.this.engine.getSession().getPacketBufferSize());
            this.fromSocketArrayOffset = this.fromSocket.arrayOffset();
        }

        @Override
        public void close() throws IOException {
            ConscryptEngineSocket.this.close();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void release() {
            Object object = this.readLock;
            synchronized (object) {
                if (this.allocatedBuffer != null) {
                    this.allocatedBuffer.release();
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int read() throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.readLock;
            synchronized (object) {
                int count = this.read(this.singleByte, 0, 1);
                if (count == -1) {
                    return -1;
                }
                if (count != 1) {
                    throw new SSLException("read incorrect number of bytes " + count);
                }
                return this.singleByte[0] & 0xFF;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int read(byte[] b) throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.readLock;
            synchronized (object) {
                return this.read(b, 0, b.length);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.readLock;
            synchronized (object) {
                return this.readUntilDataAvailable(b, off, len);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int available() throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.readLock;
            synchronized (object) {
                this.init();
                return this.fromEngine.remaining();
            }
        }

        private boolean isHandshaking(SSLEngineResult.HandshakeStatus status) {
            switch (status) {
                case NEED_UNWRAP: 
                case NEED_WRAP: 
                case NEED_TASK: {
                    return true;
                }
            }
            return false;
        }

        private int readUntilDataAvailable(byte[] b, int off, int len) throws IOException {
            int count;
            while ((count = this.processDataFromSocket(b, off, len)) == 0) {
            }
            return count;
        }

        private int processDataFromSocket(byte[] b, int off, int len) throws IOException {
            boolean needMoreDataFromSocket;
            Platform.blockGuardOnNetwork();
            ConscryptEngineSocket.this.checkOpen();
            this.init();
            do {
                if (this.fromEngine.remaining() > 0) {
                    int readFromEngine = Math.min(this.fromEngine.remaining(), len);
                    this.fromEngine.get(b, off, readFromEngine);
                    return readFromEngine;
                }
                needMoreDataFromSocket = true;
                this.fromSocket.flip();
                this.fromEngine.clear();
                boolean engineHandshaking = this.isHandshaking(ConscryptEngineSocket.this.engine.getHandshakeStatus());
                SSLEngineResult engineResult = ConscryptEngineSocket.this.engine.unwrap(this.fromSocket, this.fromEngine);
                this.fromSocket.compact();
                this.fromEngine.flip();
                switch (engineResult.getStatus()) {
                    case BUFFER_UNDERFLOW: {
                        if (engineResult.bytesProduced() == 0) break;
                        needMoreDataFromSocket = false;
                        break;
                    }
                    case OK: {
                        if (!engineHandshaking && this.isHandshaking(engineResult.getHandshakeStatus()) && this.isHandshakeFinished()) {
                            this.renegotiate();
                            return 0;
                        }
                        needMoreDataFromSocket = false;
                        break;
                    }
                    case CLOSED: {
                        return -1;
                    }
                    default: {
                        throw new SSLException("Unexpected engine result " + (Object)((Object)engineResult.getStatus()));
                    }
                }
                if (needMoreDataFromSocket || engineResult.bytesProduced() != 0) continue;
                return 0;
            } while (!needMoreDataFromSocket || this.readFromSocket() != -1);
            return -1;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean isHandshakeFinished() {
            Object object = ConscryptEngineSocket.this.stateLock;
            synchronized (object) {
                return ConscryptEngineSocket.this.state >= 4;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void renegotiate() throws IOException {
            Object object = ConscryptEngineSocket.this.handshakeLock;
            synchronized (object) {
                ConscryptEngineSocket.this.doHandshake();
            }
        }

        private void init() throws IOException {
            if (this.socketInputStream == null) {
                this.socketInputStream = ConscryptEngineSocket.this.getUnderlyingInputStream();
            }
        }

        private int readFromSocket() throws IOException {
            try {
                int pos = this.fromSocket.position();
                int lim = this.fromSocket.limit();
                int read = this.socketInputStream.read(this.fromSocket.array(), this.fromSocketArrayOffset + pos, lim - pos);
                if (read > 0) {
                    this.fromSocket.position(pos + read);
                }
                return read;
            }
            catch (EOFException e) {
                return -1;
            }
        }
    }

    private final class SSLOutputStream
    extends OutputStream {
        private final Object writeLock = new Object();
        private final ByteBuffer target;
        private final int targetArrayOffset;
        private OutputStream socketOutputStream;

        SSLOutputStream() {
            this.target = ByteBuffer.allocate(ConscryptEngineSocket.this.engine.getSession().getPacketBufferSize());
            this.targetArrayOffset = this.target.arrayOffset();
        }

        @Override
        public void close() throws IOException {
            ConscryptEngineSocket.this.close();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void write(int b) throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.writeLock;
            synchronized (object) {
                this.write(new byte[]{(byte)b});
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void write(byte[] b) throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.writeLock;
            synchronized (object) {
                this.writeInternal(ByteBuffer.wrap(b));
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.writeLock;
            synchronized (object) {
                this.writeInternal(ByteBuffer.wrap(b, off, len));
            }
        }

        private void writeInternal(ByteBuffer buffer) throws IOException {
            Platform.blockGuardOnNetwork();
            ConscryptEngineSocket.this.checkOpen();
            this.init();
            int len = buffer.remaining();
            do {
                this.target.clear();
                SSLEngineResult engineResult = ConscryptEngineSocket.this.engine.wrap(buffer, this.target);
                if (engineResult.getStatus() != SSLEngineResult.Status.OK && engineResult.getStatus() != SSLEngineResult.Status.CLOSED) {
                    throw new SSLException("Unexpected engine result " + (Object)((Object)engineResult.getStatus()));
                }
                if (this.target.position() != engineResult.bytesProduced()) {
                    throw new SSLException("Engine bytesProduced " + engineResult.bytesProduced() + " does not match bytes written " + this.target.position());
                }
                if ((len -= engineResult.bytesConsumed()) != buffer.remaining()) {
                    throw new SSLException("Engine did not read the correct number of bytes");
                }
                if (engineResult.getStatus() == SSLEngineResult.Status.CLOSED && engineResult.bytesProduced() == 0) {
                    if (len <= 0) break;
                    throw new SocketException("Socket closed");
                }
                this.target.flip();
                this.writeToSocket();
            } while (len > 0);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void flush() throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.writeLock;
            synchronized (object) {
                this.flushInternal();
            }
        }

        private void flushInternal() throws IOException {
            ConscryptEngineSocket.this.checkOpen();
            this.init();
            this.socketOutputStream.flush();
        }

        private void init() throws IOException {
            if (this.socketOutputStream == null) {
                this.socketOutputStream = ConscryptEngineSocket.this.getUnderlyingOutputStream();
            }
        }

        private void writeToSocket() throws IOException {
            this.socketOutputStream.write(this.target.array(), this.targetArrayOffset, this.target.limit());
        }
    }
}

