/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECKey;
import java.security.spec.ECParameterSpec;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;
import org.conscrypt.AbstractConscryptEngine;
import org.conscrypt.AbstractSessionContext;
import org.conscrypt.ActiveSession;
import org.conscrypt.AllocatedBuffer;
import org.conscrypt.ApplicationProtocolSelector;
import org.conscrypt.ApplicationProtocolSelectorAdapter;
import org.conscrypt.BufferAllocator;
import org.conscrypt.BufferUtils;
import org.conscrypt.ClientSessionContext;
import org.conscrypt.ConscryptSession;
import org.conscrypt.ExternalSession;
import org.conscrypt.HandshakeListener;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.NativeSsl;
import org.conscrypt.NativeSslSession;
import org.conscrypt.OpenSSLECGroupContext;
import org.conscrypt.OpenSSLKey;
import org.conscrypt.PSKKeyManager;
import org.conscrypt.PeerInfoProvider;
import org.conscrypt.Platform;
import org.conscrypt.Preconditions;
import org.conscrypt.SSLNullSession;
import org.conscrypt.SSLParametersImpl;
import org.conscrypt.SSLUtils;
import org.conscrypt.SessionSnapshot;

final class ConscryptEngine
extends AbstractConscryptEngine
implements NativeCrypto.SSLHandshakeCallbacks,
SSLParametersImpl.AliasChooser,
SSLParametersImpl.PSKCallbacks {
    private static final SSLEngineResult NEED_UNWRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
    private static final SSLEngineResult NEED_UNWRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
    private static final SSLEngineResult NEED_WRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
    private static final SSLEngineResult NEED_WRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
    private static final SSLEngineResult CLOSED_NOT_HANDSHAKING = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
    private static BufferAllocator defaultBufferAllocator = null;
    private final SSLParametersImpl sslParameters;
    private BufferAllocator bufferAllocator = defaultBufferAllocator;
    private ByteBuffer lazyDirectBuffer;
    private String peerHostname;
    private int state = 0;
    private boolean handshakeFinished;
    private final NativeSsl ssl;
    private final NativeSsl.BioWrapper networkBio;
    private ActiveSession activeSession;
    private SessionSnapshot closedSession;
    private final SSLSession externalSession = Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider(){

        @Override
        public ConscryptSession provideSession() {
            return ConscryptEngine.this.provideSession();
        }
    }));
    private OpenSSLKey channelIdPrivateKey;
    private int maxSealOverhead;
    private HandshakeListener handshakeListener;
    private final ByteBuffer[] singleSrcBuffer = new ByteBuffer[1];
    private final ByteBuffer[] singleDstBuffer = new ByteBuffer[1];
    private final PeerInfoProvider peerInfoProvider;

    ConscryptEngine(SSLParametersImpl sslParameters) {
        this.sslParameters = sslParameters;
        this.peerInfoProvider = PeerInfoProvider.nullProvider();
        this.ssl = ConscryptEngine.newSsl(sslParameters, this, this);
        this.networkBio = this.ssl.newBio();
    }

    ConscryptEngine(String host2, int port, SSLParametersImpl sslParameters) {
        this.sslParameters = sslParameters;
        this.peerInfoProvider = PeerInfoProvider.forHostAndPort(host2, port);
        this.ssl = ConscryptEngine.newSsl(sslParameters, this, this);
        this.networkBio = this.ssl.newBio();
    }

    ConscryptEngine(SSLParametersImpl sslParameters, PeerInfoProvider peerInfoProvider, SSLParametersImpl.AliasChooser aliasChooser) {
        this.sslParameters = sslParameters;
        this.peerInfoProvider = Preconditions.checkNotNull(peerInfoProvider, "peerInfoProvider");
        this.ssl = ConscryptEngine.newSsl(sslParameters, this, aliasChooser);
        this.networkBio = this.ssl.newBio();
    }

    private static NativeSsl newSsl(SSLParametersImpl sslParameters, ConscryptEngine engine, SSLParametersImpl.AliasChooser aliasChooser) {
        try {
            return NativeSsl.newInstance(sslParameters, engine, aliasChooser, engine);
        }
        catch (SSLException e) {
            throw new RuntimeException(e);
        }
    }

    static void setDefaultBufferAllocator(BufferAllocator bufferAllocator) {
        defaultBufferAllocator = bufferAllocator;
    }

    static BufferAllocator getDefaultBufferAllocator() {
        return defaultBufferAllocator;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    void setBufferAllocator(BufferAllocator bufferAllocator) {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.isHandshakeStarted()) {
                throw new IllegalStateException("Could not set buffer allocator after the initial handshake has begun.");
            }
            this.bufferAllocator = bufferAllocator;
        }
    }

    @Override
    int maxSealOverhead() {
        return this.maxSealOverhead;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    void setChannelIdEnabled(boolean enabled) {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.getUseClientMode()) {
                throw new IllegalStateException("Not allowed in client mode");
            }
            if (this.isHandshakeStarted()) {
                throw new IllegalStateException("Could not enable/disable Channel ID after the initial handshake has begun.");
            }
            this.sslParameters.channelIdEnabled = enabled;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    byte[] getChannelId() throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.getUseClientMode()) {
                throw new IllegalStateException("Not allowed in client mode");
            }
            if (this.isHandshakeStarted()) {
                throw new IllegalStateException("Channel ID is only available after handshake completes");
            }
            return this.ssl.getTlsChannelId();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    void setChannelIdPrivateKey(PrivateKey privateKey) {
        if (!this.getUseClientMode()) {
            throw new IllegalStateException("Not allowed in server mode");
        }
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.isHandshakeStarted()) {
                throw new IllegalStateException("Could not change Channel ID private key after the initial handshake has begun.");
            }
            if (privateKey == null) {
                this.sslParameters.channelIdEnabled = false;
                this.channelIdPrivateKey = null;
                return;
            }
            this.sslParameters.channelIdEnabled = true;
            try {
                ECParameterSpec ecParams = null;
                if (privateKey instanceof ECKey) {
                    ecParams = ((ECKey)((Object)privateKey)).getParams();
                }
                if (ecParams == null) {
                    ecParams = OpenSSLECGroupContext.getCurveByName("prime256v1").getECParameterSpec();
                }
                this.channelIdPrivateKey = OpenSSLKey.fromECPrivateKeyForTLSStackOnly(privateKey, ecParams);
            }
            catch (InvalidKeyException invalidKeyException) {
                // empty catch block
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    void setHandshakeListener(HandshakeListener handshakeListener) {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.isHandshakeStarted()) {
                throw new IllegalStateException("Handshake listener must be set before starting the handshake.");
            }
            this.handshakeListener = handshakeListener;
        }
    }

    private boolean isHandshakeStarted() {
        switch (this.state) {
            case 0: 
            case 1: {
                return false;
            }
        }
        return true;
    }

    @Override
    void setHostname(String hostname) {
        this.sslParameters.setUseSni(hostname != null);
        this.peerHostname = hostname;
    }

    @Override
    String getHostname() {
        return this.peerHostname != null ? this.peerHostname : this.peerInfoProvider.getHostname();
    }

    @Override
    public String getPeerHost() {
        return this.peerHostname != null ? this.peerHostname : this.peerInfoProvider.getHostnameOrIP();
    }

    @Override
    public int getPeerPort() {
        return this.peerInfoProvider.getPort();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void beginHandshake() throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            this.beginHandshakeInternal();
        }
    }

    private void beginHandshakeInternal() throws SSLException {
        switch (this.state) {
            case 0: {
                throw new IllegalStateException("Client/server mode must be set before handshake");
            }
            case 1: {
                break;
            }
            case 6: 
            case 7: 
            case 8: {
                throw new SSLHandshakeException("Engine has already been closed");
            }
            default: {
                return;
            }
        }
        this.transitionTo(2);
        boolean releaseResources = true;
        try {
            NativeSslSession cachedSession;
            this.ssl.initialize(this.getHostname(), this.channelIdPrivateKey);
            if (this.getUseClientMode() && (cachedSession = this.clientSessionContext().getCachedSession(this.getHostname(), this.getPeerPort(), this.sslParameters)) != null) {
                cachedSession.offerToResume(this.ssl);
            }
            this.maxSealOverhead = this.ssl.getMaxSealOverhead();
            this.handshake();
            releaseResources = false;
        }
        catch (IOException e) {
            String message = e.getMessage();
            if (message.contains("unexpected CCS")) {
                String logMessage = String.format("ssl_unexpected_ccs: host=%s", this.getPeerHost());
                Platform.logEvent(logMessage);
            }
            this.closeAll();
            throw SSLUtils.toSSLHandshakeException(e);
        }
        finally {
            if (releaseResources) {
                this.closeAndFreeResources();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void closeInbound() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8 || this.state == 6) {
                return;
            }
            if (this.isHandshakeStarted()) {
                if (this.state == 7) {
                    this.transitionTo(8);
                } else {
                    this.transitionTo(6);
                }
                this.freeIfDone();
            } else {
                this.closeAndFreeResources();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void closeOutbound() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8 || this.state == 7) {
                return;
            }
            if (this.isHandshakeStarted()) {
                if (this.state == 6) {
                    this.transitionTo(8);
                } else {
                    this.transitionTo(7);
                }
                this.sendSSLShutdown();
                this.freeIfDone();
            } else {
                this.closeAndFreeResources();
            }
        }
    }

    @Override
    public Runnable getDelegatedTask() {
        return null;
    }

    @Override
    public String[] getEnabledCipherSuites() {
        return this.sslParameters.getEnabledCipherSuites();
    }

    @Override
    public String[] getEnabledProtocols() {
        return this.sslParameters.getEnabledProtocols();
    }

    @Override
    public boolean getEnableSessionCreation() {
        return this.sslParameters.getEnableSessionCreation();
    }

    @Override
    public SSLParameters getSSLParameters() {
        SSLParameters params = super.getSSLParameters();
        Platform.getSSLParameters(params, this.sslParameters, this);
        return params;
    }

    @Override
    public void setSSLParameters(SSLParameters p) {
        super.setSSLParameters(p);
        Platform.setSSLParameters(p, this.sslParameters, this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            return this.getHandshakeStatusInternal();
        }
    }

    private SSLEngineResult.HandshakeStatus getHandshakeStatusInternal() {
        if (this.handshakeFinished) {
            return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
        }
        switch (this.state) {
            case 2: {
                return ConscryptEngine.pendingStatus(this.pendingOutboundEncryptedBytes());
            }
            case 3: {
                return SSLEngineResult.HandshakeStatus.NEED_WRAP;
            }
            case 0: 
            case 1: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: {
                return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
            }
        }
        throw new IllegalStateException("Unexpected engine state: " + this.state);
    }

    int pendingOutboundEncryptedBytes() {
        return this.networkBio.getPendingWrittenBytes();
    }

    private int pendingInboundCleartextBytes() {
        return this.ssl.getPendingReadableBytes();
    }

    private static SSLEngineResult.HandshakeStatus pendingStatus(int pendingOutboundBytes) {
        return pendingOutboundBytes > 0 ? SSLEngineResult.HandshakeStatus.NEED_WRAP : SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
    }

    @Override
    public boolean getNeedClientAuth() {
        return this.sslParameters.getNeedClientAuth();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    SSLSession handshakeSession() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 2) {
                return Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider(){

                    @Override
                    public ConscryptSession provideSession() {
                        return ConscryptEngine.this.provideHandshakeSession();
                    }
                }));
            }
            return null;
        }
    }

    @Override
    public SSLSession getSession() {
        return this.externalSession;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ConscryptSession provideSession() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8) {
                return this.closedSession != null ? this.closedSession : SSLNullSession.getNullSession();
            }
            if (this.state < 3) {
                return SSLNullSession.getNullSession();
            }
            return this.activeSession;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ConscryptSession provideHandshakeSession() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            return this.state == 2 ? this.activeSession : SSLNullSession.getNullSession();
        }
    }

    private ConscryptSession provideAfterHandshakeSession() {
        return this.state < 2 ? SSLNullSession.getNullSession() : this.provideSession();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return NativeCrypto.getSupportedCipherSuites();
    }

    @Override
    public String[] getSupportedProtocols() {
        return NativeCrypto.getSupportedProtocols();
    }

    @Override
    public boolean getUseClientMode() {
        return this.sslParameters.getUseClientMode();
    }

    @Override
    public boolean getWantClientAuth() {
        return this.sslParameters.getWantClientAuth();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isInboundDone() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            return (this.state == 8 || this.state == 6 || this.ssl.wasShutdownReceived()) && this.pendingInboundCleartextBytes() == 0;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isOutboundDone() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            return (this.state == 8 || this.state == 7 || this.ssl.wasShutdownSent()) && this.pendingOutboundEncryptedBytes() == 0;
        }
    }

    @Override
    public void setEnabledCipherSuites(String[] suites) {
        this.sslParameters.setEnabledCipherSuites(suites);
    }

    @Override
    public void setEnabledProtocols(String[] protocols) {
        this.sslParameters.setEnabledProtocols(protocols);
    }

    @Override
    public void setEnableSessionCreation(boolean flag) {
        this.sslParameters.setEnableSessionCreation(flag);
    }

    @Override
    public void setNeedClientAuth(boolean need) {
        this.sslParameters.setNeedClientAuth(need);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setUseClientMode(boolean mode) {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.isHandshakeStarted()) {
                throw new IllegalArgumentException("Can not change mode after handshake: state == " + this.state);
            }
            this.transitionTo(1);
            this.sslParameters.setUseClientMode(mode);
        }
    }

    @Override
    public void setWantClientAuth(boolean want) {
        this.sslParameters.setWantClientAuth(want);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            SSLEngineResult sSLEngineResult;
            try {
                sSLEngineResult = this.unwrap(this.singleSrcBuffer(src), this.singleDstBuffer(dst));
                this.resetSingleSrcBuffer();
                this.resetSingleDstBuffer();
            }
            catch (Throwable throwable) {
                this.resetSingleSrcBuffer();
                this.resetSingleDstBuffer();
                throw throwable;
            }
            return sSLEngineResult;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts) throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            SSLEngineResult sSLEngineResult;
            try {
                sSLEngineResult = this.unwrap(this.singleSrcBuffer(src), dsts);
                this.resetSingleSrcBuffer();
            }
            catch (Throwable throwable) {
                this.resetSingleSrcBuffer();
                throw throwable;
            }
            return sSLEngineResult;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts, int offset, int length) throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            SSLEngineResult sSLEngineResult;
            try {
                sSLEngineResult = this.unwrap(this.singleSrcBuffer(src), 0, 1, dsts, offset, length);
                this.resetSingleSrcBuffer();
            }
            catch (Throwable throwable) {
                this.resetSingleSrcBuffer();
                throw throwable;
            }
            return sSLEngineResult;
        }
    }

    @Override
    SSLEngineResult unwrap(ByteBuffer[] srcs, ByteBuffer[] dsts) throws SSLException {
        Preconditions.checkArgument(srcs != null, "srcs is null");
        Preconditions.checkArgument(dsts != null, "dsts is null");
        return this.unwrap(srcs, 0, srcs.length, dsts, 0, dsts.length);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    SSLEngineResult unwrap(ByteBuffer[] srcs, int srcsOffset, int srcsLength, ByteBuffer[] dsts, int dstsOffset, int dstsLength) throws SSLException {
        Preconditions.checkArgument(srcs != null, "srcs is null");
        Preconditions.checkArgument(dsts != null, "dsts is null");
        Preconditions.checkPositionIndexes(srcsOffset, srcsOffset + srcsLength, srcs.length);
        Preconditions.checkPositionIndexes(dstsOffset, dstsOffset + dstsLength, dsts.length);
        int dstLength = ConscryptEngine.calcDstsLength(dsts, dstsOffset, dstsLength);
        int endOffset = dstsOffset + dstsLength;
        int srcsEndOffset = srcsOffset + srcsLength;
        long srcLength = ConscryptEngine.calcSrcsLength(srcs, srcsOffset, srcsEndOffset);
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            int pendingCleartextBytes;
            int bytesProduced;
            int bytesConsumed;
            SSLEngineResult.HandshakeStatus handshakeStatus;
            block32: {
                switch (this.state) {
                    case 1: {
                        this.beginHandshakeInternal();
                        break;
                    }
                    case 6: 
                    case 8: {
                        this.freeIfDone();
                        return new SSLEngineResult(SSLEngineResult.Status.CLOSED, this.getHandshakeStatusInternal(), 0, 0);
                    }
                    case 0: {
                        throw new IllegalStateException("Client/server mode must be set before calling unwrap");
                    }
                }
                handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
                if (!this.handshakeFinished) {
                    handshakeStatus = this.handshake();
                    if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
                        return NEED_WRAP_OK;
                    }
                    if (this.state == 8) {
                        return NEED_WRAP_CLOSED;
                    }
                }
                boolean noCleartextDataAvailable = this.pendingInboundCleartextBytes() <= 0;
                int lenRemaining = 0;
                if (srcLength > 0L && noCleartextDataAvailable) {
                    if (srcLength < 5L) {
                        return new SSLEngineResult(SSLEngineResult.Status.BUFFER_UNDERFLOW, this.getHandshakeStatus(), 0, 0);
                    }
                    int packetLength = SSLUtils.getEncryptedPacketLength(srcs, srcsOffset);
                    if (packetLength < 0) {
                        throw new SSLException("Unable to parse TLS packet header");
                    }
                    if (srcLength < (long)packetLength) {
                        return new SSLEngineResult(SSLEngineResult.Status.BUFFER_UNDERFLOW, this.getHandshakeStatus(), 0, 0);
                    }
                    lenRemaining = packetLength;
                } else if (noCleartextDataAvailable) {
                    return new SSLEngineResult(SSLEngineResult.Status.BUFFER_UNDERFLOW, this.getHandshakeStatus(), 0, 0);
                }
                bytesConsumed = 0;
                if (lenRemaining > 0 && srcsOffset < srcsEndOffset) {
                    do {
                        ByteBuffer src;
                        int remaining;
                        if ((remaining = (src = srcs[srcsOffset]).remaining()) == 0) {
                            ++srcsOffset;
                            continue;
                        }
                        int written = this.writeEncryptedData(src, Math.min(lenRemaining, remaining));
                        if (written > 0) {
                            bytesConsumed += written;
                            if ((lenRemaining -= written) == 0 || written != remaining) break;
                            ++srcsOffset;
                            continue;
                        }
                        NativeCrypto.SSL_clear_error();
                        break;
                    } while (srcsOffset < srcsEndOffset);
                }
                bytesProduced = 0;
                try {
                    if (dstLength > 0) {
                        for (int idx = dstsOffset; idx < endOffset; ++idx) {
                            ByteBuffer dst = dsts[idx];
                            if (!dst.hasRemaining()) continue;
                            int bytesRead = this.readPlaintextData(dst);
                            if (bytesRead > 0) {
                                bytesProduced += bytesRead;
                                if (!dst.hasRemaining()) continue;
                                break block32;
                            }
                            switch (bytesRead) {
                                case -3: 
                                case -2: {
                                    return this.newResult(bytesConsumed, bytesProduced, handshakeStatus);
                                }
                                case -6: {
                                    this.closeAll();
                                    return new SSLEngineResult(SSLEngineResult.Status.CLOSED, this.pendingOutboundEncryptedBytes() > 0 ? SSLEngineResult.HandshakeStatus.NEED_WRAP : SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, bytesConsumed, bytesProduced);
                                }
                            }
                            this.closeAll();
                            throw this.newSslExceptionWithMessage("SSL_read");
                        }
                        break block32;
                    }
                    this.ssl.forceRead();
                }
                catch (InterruptedIOException e) {
                    return this.newResult(bytesConsumed, bytesProduced, handshakeStatus);
                }
                catch (IOException e) {
                    this.closeAll();
                    throw this.convertException(e);
                }
            }
            int n = pendingCleartextBytes = this.handshakeFinished ? this.pendingInboundCleartextBytes() : 0;
            if (pendingCleartextBytes > 0) {
                return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, this.mayFinishHandshake(handshakeStatus == SSLEngineResult.HandshakeStatus.FINISHED ? handshakeStatus : this.getHandshakeStatusInternal()), bytesConsumed, bytesProduced);
            }
            return this.newResult(bytesConsumed, bytesProduced, handshakeStatus);
        }
    }

    private static int calcDstsLength(ByteBuffer[] dsts, int dstsOffset, int dstsLength) {
        int capacity = 0;
        for (int i = 0; i < dsts.length; ++i) {
            ByteBuffer dst = dsts[i];
            Preconditions.checkArgument(dst != null, "dsts[%d] is null", i);
            if (dst.isReadOnly()) {
                throw new ReadOnlyBufferException();
            }
            if (i < dstsOffset || i >= dstsOffset + dstsLength) continue;
            capacity += dst.remaining();
        }
        return capacity;
    }

    private static long calcSrcsLength(ByteBuffer[] srcs, int srcsOffset, int srcsEndOffset) {
        long len = 0L;
        for (int i = srcsOffset; i < srcsEndOffset; ++i) {
            ByteBuffer src = srcs[i];
            if (src == null) {
                throw new IllegalArgumentException("srcs[" + i + "] is null");
            }
            len += (long)src.remaining();
        }
        return len;
    }

    private SSLEngineResult.HandshakeStatus handshake() throws SSLException {
        try {
            try {
                int ssl_error_code = this.ssl.doHandshake();
                switch (ssl_error_code) {
                    case 2: {
                        return ConscryptEngine.pendingStatus(this.pendingOutboundEncryptedBytes());
                    }
                    case 3: {
                        return SSLEngineResult.HandshakeStatus.NEED_WRAP;
                    }
                }
            }
            catch (IOException e) {
                this.closeAll();
                throw e;
            }
            this.activeSession.onPeerCertificateAvailable(this.getPeerHost(), this.getPeerPort());
            this.finishHandshake();
            return SSLEngineResult.HandshakeStatus.FINISHED;
        }
        catch (Exception e) {
            throw SSLUtils.toSSLHandshakeException(e);
        }
    }

    private void finishHandshake() throws SSLException {
        this.handshakeFinished = true;
        if (this.handshakeListener != null) {
            this.handshakeListener.onHandshakeFinished();
        }
    }

    private int writePlaintextData(ByteBuffer src, int len) throws SSLException {
        try {
            int pos = src.position();
            int sslWrote = src.isDirect() ? this.writePlaintextDataDirect(src, pos, len) : this.writePlaintextDataHeap(src, pos, len);
            if (sslWrote > 0) {
                src.position(pos + sslWrote);
            }
            return sslWrote;
        }
        catch (Exception e) {
            throw this.convertException(e);
        }
    }

    private int writePlaintextDataDirect(ByteBuffer src, int pos, int len) throws IOException {
        return this.ssl.writeDirectByteBuffer(this.directByteBufferAddress(src, pos), len);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int writePlaintextDataHeap(ByteBuffer src, int pos, int len) throws IOException {
        AllocatedBuffer allocatedBuffer = null;
        try {
            ByteBuffer buffer;
            if (this.bufferAllocator != null) {
                allocatedBuffer = this.bufferAllocator.allocateDirectBuffer(len);
                buffer = allocatedBuffer.nioBuffer();
            } else {
                buffer = this.getOrCreateLazyDirectBuffer();
            }
            int limit = src.limit();
            int bytesToWrite = Math.min(len, buffer.remaining());
            src.limit(pos + bytesToWrite);
            buffer.put(src);
            buffer.flip();
            src.limit(limit);
            src.position(pos);
            int n = this.writePlaintextDataDirect(buffer, 0, bytesToWrite);
            return n;
        }
        finally {
            if (allocatedBuffer != null) {
                allocatedBuffer.release();
            }
        }
    }

    private int readPlaintextData(ByteBuffer dst) throws IOException {
        try {
            int pos = dst.position();
            int limit = dst.limit();
            int len = Math.min(16709, limit - pos);
            if (dst.isDirect()) {
                int bytesRead = this.readPlaintextDataDirect(dst, pos, len);
                if (bytesRead > 0) {
                    dst.position(pos + bytesRead);
                }
                return bytesRead;
            }
            return this.readPlaintextDataHeap(dst, len);
        }
        catch (CertificateException e) {
            throw this.convertException(e);
        }
    }

    private int readPlaintextDataDirect(ByteBuffer dst, int pos, int len) throws IOException, CertificateException {
        return this.ssl.readDirectByteBuffer(this.directByteBufferAddress(dst, pos), len);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int readPlaintextDataHeap(ByteBuffer dst, int len) throws IOException, CertificateException {
        AllocatedBuffer allocatedBuffer = null;
        try {
            ByteBuffer buffer;
            if (this.bufferAllocator != null) {
                allocatedBuffer = this.bufferAllocator.allocateDirectBuffer(len);
                buffer = allocatedBuffer.nioBuffer();
            } else {
                buffer = this.getOrCreateLazyDirectBuffer();
            }
            int bytesToRead = Math.min(len, buffer.remaining());
            int bytesRead = this.readPlaintextDataDirect(buffer, 0, bytesToRead);
            if (bytesRead > 0) {
                buffer.position(bytesRead);
                buffer.flip();
                dst.put(buffer);
            }
            int n = bytesRead;
            return n;
        }
        finally {
            if (allocatedBuffer != null) {
                allocatedBuffer.release();
            }
        }
    }

    private SSLException convertException(Throwable e) {
        if (e instanceof SSLHandshakeException || !this.handshakeFinished) {
            return SSLUtils.toSSLHandshakeException(e);
        }
        return SSLUtils.toSSLException(e);
    }

    private int writeEncryptedData(ByteBuffer src, int len) throws SSLException {
        try {
            int pos = src.position();
            int bytesWritten = src.isDirect() ? this.writeEncryptedDataDirect(src, pos, len) : this.writeEncryptedDataHeap(src, pos, len);
            if (bytesWritten > 0) {
                src.position(pos + bytesWritten);
            }
            return bytesWritten;
        }
        catch (IOException e) {
            this.closeAll();
            throw new SSLException(e);
        }
    }

    private int writeEncryptedDataDirect(ByteBuffer src, int pos, int len) throws IOException {
        return this.networkBio.writeDirectByteBuffer(this.directByteBufferAddress(src, pos), len);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int writeEncryptedDataHeap(ByteBuffer src, int pos, int len) throws IOException {
        AllocatedBuffer allocatedBuffer = null;
        try {
            ByteBuffer buffer;
            if (this.bufferAllocator != null) {
                allocatedBuffer = this.bufferAllocator.allocateDirectBuffer(len);
                buffer = allocatedBuffer.nioBuffer();
            } else {
                buffer = this.getOrCreateLazyDirectBuffer();
            }
            int limit = src.limit();
            int bytesToCopy = Math.min(Math.min(limit - pos, len), buffer.remaining());
            src.limit(pos + bytesToCopy);
            buffer.put(src);
            src.limit(limit);
            src.position(pos);
            int bytesWritten = this.writeEncryptedDataDirect(buffer, 0, bytesToCopy);
            src.position(pos);
            int n = bytesWritten;
            return n;
        }
        finally {
            if (allocatedBuffer != null) {
                allocatedBuffer.release();
            }
        }
    }

    private ByteBuffer getOrCreateLazyDirectBuffer() {
        if (this.lazyDirectBuffer == null) {
            this.lazyDirectBuffer = ByteBuffer.allocateDirect(Math.max(16384, 16709));
        }
        this.lazyDirectBuffer.clear();
        return this.lazyDirectBuffer;
    }

    private long directByteBufferAddress(ByteBuffer directBuffer, int pos) {
        return NativeCrypto.getDirectBufferAddress(directBuffer) + (long)pos;
    }

    private SSLEngineResult readPendingBytesFromBIO(ByteBuffer dst, int bytesConsumed, int bytesProduced, SSLEngineResult.HandshakeStatus status) throws SSLException {
        try {
            int pendingNet = this.pendingOutboundEncryptedBytes();
            if (pendingNet > 0) {
                int capacity = dst.remaining();
                if (capacity < pendingNet) {
                    return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, this.mayFinishHandshake(status == SSLEngineResult.HandshakeStatus.FINISHED ? status : this.getHandshakeStatus(pendingNet)), bytesConsumed, bytesProduced);
                }
                int produced = this.readEncryptedData(dst, pendingNet);
                if (produced <= 0) {
                    NativeCrypto.SSL_clear_error();
                } else {
                    bytesProduced += produced;
                    pendingNet -= produced;
                }
                return new SSLEngineResult(this.getEngineStatus(), this.mayFinishHandshake(status == SSLEngineResult.HandshakeStatus.FINISHED ? status : this.getHandshakeStatus(pendingNet)), bytesConsumed, bytesProduced);
            }
            return null;
        }
        catch (Exception e) {
            throw this.convertException(e);
        }
    }

    private int readEncryptedData(ByteBuffer dst, int pending) throws SSLException {
        try {
            int bytesRead = 0;
            int pos = dst.position();
            if (dst.remaining() >= pending) {
                int limit = dst.limit();
                int len = Math.min(pending, limit - pos);
                if (dst.isDirect()) {
                    bytesRead = this.readEncryptedDataDirect(dst, pos, len);
                    if (bytesRead > 0) {
                        dst.position(pos + bytesRead);
                    }
                } else {
                    bytesRead = this.readEncryptedDataHeap(dst, len);
                }
            }
            return bytesRead;
        }
        catch (Exception e) {
            throw this.convertException(e);
        }
    }

    private int readEncryptedDataDirect(ByteBuffer dst, int pos, int len) throws IOException {
        return this.networkBio.readDirectByteBuffer(this.directByteBufferAddress(dst, pos), len);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int readEncryptedDataHeap(ByteBuffer dst, int len) throws IOException {
        AllocatedBuffer allocatedBuffer = null;
        try {
            ByteBuffer buffer;
            if (this.bufferAllocator != null) {
                allocatedBuffer = this.bufferAllocator.allocateDirectBuffer(len);
                buffer = allocatedBuffer.nioBuffer();
            } else {
                buffer = this.getOrCreateLazyDirectBuffer();
            }
            int bytesToRead = Math.min(len, buffer.remaining());
            int bytesRead = this.readEncryptedDataDirect(buffer, 0, bytesToRead);
            if (bytesRead > 0) {
                buffer.position(bytesRead);
                buffer.flip();
                dst.put(buffer);
            }
            int n = bytesRead;
            return n;
        }
        finally {
            if (allocatedBuffer != null) {
                allocatedBuffer.release();
            }
        }
    }

    private SSLEngineResult.HandshakeStatus mayFinishHandshake(SSLEngineResult.HandshakeStatus status) throws SSLException {
        if (!this.handshakeFinished && status == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
            return this.handshake();
        }
        return status;
    }

    private SSLEngineResult.HandshakeStatus getHandshakeStatus(int pending) {
        return !this.handshakeFinished ? ConscryptEngine.pendingStatus(pending) : SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    }

    private SSLEngineResult.Status getEngineStatus() {
        switch (this.state) {
            case 6: 
            case 7: 
            case 8: {
                return SSLEngineResult.Status.CLOSED;
            }
        }
        return SSLEngineResult.Status.OK;
    }

    private void closeAll() {
        this.closeOutbound();
        this.closeInbound();
    }

    private void freeIfDone() {
        if (this.isInboundDone() && this.isOutboundDone()) {
            this.closeAndFreeResources();
        }
    }

    private SSLException newSslExceptionWithMessage(String err) {
        if (!this.handshakeFinished) {
            return new SSLException(err);
        }
        return new SSLHandshakeException(err);
    }

    private SSLEngineResult newResult(int bytesConsumed, int bytesProduced, SSLEngineResult.HandshakeStatus status) throws SSLException {
        return new SSLEngineResult(this.getEngineStatus(), this.mayFinishHandshake(status == SSLEngineResult.HandshakeStatus.FINISHED ? status : this.getHandshakeStatusInternal()), bytesConsumed, bytesProduced);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public SSLEngineResult wrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            SSLEngineResult sSLEngineResult;
            try {
                sSLEngineResult = this.wrap(this.singleSrcBuffer(src), dst);
                this.resetSingleSrcBuffer();
            }
            catch (Throwable throwable) {
                this.resetSingleSrcBuffer();
                throw throwable;
            }
            return sSLEngineResult;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public SSLEngineResult wrap(ByteBuffer[] srcs, int srcsOffset, int srcsLength, ByteBuffer dst) throws SSLException {
        Preconditions.checkArgument(srcs != null, "srcs is null");
        Preconditions.checkArgument(dst != null, "dst is null");
        Preconditions.checkPositionIndexes(srcsOffset, srcsOffset + srcsLength, srcs.length);
        if (dst.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        if (srcsOffset != 0 || srcsLength != srcs.length) {
            srcs = Arrays.copyOfRange(srcs, srcsOffset, srcsOffset + srcsLength);
        }
        BufferUtils.checkNotNull(srcs);
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            SSLEngineResult pendingNetResult;
            switch (this.state) {
                case 1: {
                    this.beginHandshakeInternal();
                    break;
                }
                case 7: 
                case 8: {
                    SSLEngineResult pendingNetResult2 = this.readPendingBytesFromBIO(dst, 0, 0, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING);
                    if (pendingNetResult2 != null) {
                        this.freeIfDone();
                        return pendingNetResult2;
                    }
                    return new SSLEngineResult(SSLEngineResult.Status.CLOSED, this.getHandshakeStatusInternal(), 0, 0);
                }
                case 0: {
                    throw new IllegalStateException("Client/server mode must be set before calling wrap");
                }
            }
            SSLEngineResult.HandshakeStatus handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
            if (!this.handshakeFinished) {
                handshakeStatus = this.handshake();
                if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
                    return NEED_UNWRAP_OK;
                }
                if (this.state == 8) {
                    return NEED_UNWRAP_CLOSED;
                }
            }
            int dataLength = (int)Math.min(BufferUtils.remaining(srcs), 16384L);
            if (dst.remaining() < SSLUtils.calculateOutNetBufSize(dataLength)) {
                return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, this.getHandshakeStatusInternal(), 0, 0);
            }
            int bytesProduced = 0;
            int bytesConsumed = 0;
            if (dataLength > 0) {
                int result;
                boolean isCopy = false;
                ByteBuffer outputBuffer = BufferUtils.getBufferLargerThan(srcs, 16384);
                if (outputBuffer == null) {
                    outputBuffer = BufferUtils.copyNoConsume(srcs, this.getOrCreateLazyDirectBuffer(), 16384);
                    isCopy = true;
                }
                if ((result = this.writePlaintextData(outputBuffer, Math.min(16384, outputBuffer.remaining()))) > 0) {
                    SSLEngineResult pendingNetResult3;
                    bytesConsumed = result;
                    if (isCopy) {
                        BufferUtils.consume(srcs, bytesConsumed);
                    }
                    if ((pendingNetResult3 = this.readPendingBytesFromBIO(dst, bytesConsumed, bytesProduced, handshakeStatus)) != null) {
                        if (pendingNetResult3.getStatus() != SSLEngineResult.Status.OK) {
                            return pendingNetResult3;
                        }
                        bytesProduced = pendingNetResult3.bytesProduced();
                    }
                } else {
                    int sslError = this.ssl.getError(result);
                    switch (sslError) {
                        case 6: {
                            this.closeAll();
                            SSLEngineResult pendingNetResult4 = this.readPendingBytesFromBIO(dst, bytesConsumed, bytesProduced, handshakeStatus);
                            return pendingNetResult4 != null ? pendingNetResult4 : CLOSED_NOT_HANDSHAKING;
                        }
                        case 2: {
                            SSLEngineResult pendingNetResult5 = this.readPendingBytesFromBIO(dst, bytesConsumed, bytesProduced, handshakeStatus);
                            return pendingNetResult5 != null ? pendingNetResult5 : new SSLEngineResult(this.getEngineStatus(), SSLEngineResult.HandshakeStatus.NEED_UNWRAP, bytesConsumed, bytesProduced);
                        }
                        case 3: {
                            SSLEngineResult pendingNetResult6 = this.readPendingBytesFromBIO(dst, bytesConsumed, bytesProduced, handshakeStatus);
                            return pendingNetResult6 != null ? pendingNetResult6 : NEED_WRAP_CLOSED;
                        }
                    }
                    this.closeAll();
                    throw this.newSslExceptionWithMessage("SSL_write: error " + sslError);
                }
            }
            if (bytesConsumed == 0 && (pendingNetResult = this.readPendingBytesFromBIO(dst, 0, bytesProduced, handshakeStatus)) != null) {
                return pendingNetResult;
            }
            return this.newResult(bytesConsumed, bytesProduced, handshakeStatus);
        }
    }

    @Override
    public int clientPSKKeyRequested(String identityHint, byte[] identity, byte[] key) {
        return this.ssl.clientPSKKeyRequested(identityHint, identity, key);
    }

    @Override
    public int serverPSKKeyRequested(String identityHint, String identity, byte[] key) {
        return this.ssl.serverPSKKeyRequested(identityHint, identity, key);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onSSLStateChange(int type, int val) {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            switch (type) {
                case 16: {
                    this.transitionTo(2);
                    break;
                }
                case 32: {
                    if (this.state != 2 && this.state != 4) {
                        throw new IllegalStateException("Completed handshake while in mode " + this.state);
                    }
                    this.transitionTo(3);
                    break;
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void serverCertificateRequested() throws IOException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            this.ssl.configureServerCertificate();
        }
    }

    @Override
    public void onNewSessionEstablished(long sslSessionNativePtr) {
        try {
            NativeCrypto.SSL_SESSION_up_ref(sslSessionNativePtr);
            NativeRef.SSL_SESSION ref = new NativeRef.SSL_SESSION(sslSessionNativePtr);
            NativeSslSession nativeSession = NativeSslSession.newInstance(ref, this.activeSession);
            AbstractSessionContext ctx = this.sessionContext();
            ctx.cacheSession(nativeSession);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public long serverSessionRequested(byte[] id) {
        return 0L;
    }

    @Override
    public void verifyCertificateChain(byte[][] certChain, String authMethod) throws CertificateException {
        try {
            if (certChain == null || certChain.length == 0) {
                throw new CertificateException("Peer sent no certificate");
            }
            X509Certificate[] peerCertChain = SSLUtils.decodeX509CertificateChain(certChain);
            X509TrustManager x509tm = this.sslParameters.getX509TrustManager();
            if (x509tm == null) {
                throw new CertificateException("No X.509 TrustManager");
            }
            this.activeSession.onPeerCertificatesReceived(this.getPeerHost(), this.getPeerPort(), peerCertChain);
            if (this.getUseClientMode()) {
                Platform.checkServerTrusted(x509tm, peerCertChain, authMethod, this);
            } else {
                String authType = peerCertChain[0].getPublicKey().getAlgorithm();
                Platform.checkClientTrusted(x509tm, peerCertChain, authType, this);
            }
        }
        catch (CertificateException e) {
            throw e;
        }
        catch (Exception e) {
            throw new CertificateException(e);
        }
    }

    @Override
    public void clientCertificateRequested(byte[] keyTypeBytes, int[] signatureAlgs, byte[][] asn1DerEncodedPrincipals) throws CertificateEncodingException, SSLException {
        this.ssl.chooseClientCertificate(keyTypeBytes, signatureAlgs, asn1DerEncodedPrincipals);
    }

    private void sendSSLShutdown() {
        try {
            this.ssl.shutdown();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private void closeAndFreeResources() {
        this.transitionTo(8);
        if (this.ssl != null) {
            this.ssl.close();
        }
        if (this.networkBio != null) {
            this.networkBio.close();
        }
    }

    protected void finalize() throws Throwable {
        try {
            this.closeAndFreeResources();
        }
        finally {
            super.finalize();
        }
    }

    @Override
    public String chooseServerAlias(X509KeyManager keyManager, String keyType) {
        if (keyManager instanceof X509ExtendedKeyManager) {
            X509ExtendedKeyManager ekm = (X509ExtendedKeyManager)keyManager;
            return ekm.chooseEngineServerAlias(keyType, null, this);
        }
        return keyManager.chooseServerAlias(keyType, null, null);
    }

    @Override
    public String chooseClientAlias(X509KeyManager keyManager, X500Principal[] issuers, String[] keyTypes) {
        if (keyManager instanceof X509ExtendedKeyManager) {
            X509ExtendedKeyManager ekm = (X509ExtendedKeyManager)keyManager;
            return ekm.chooseEngineClientAlias(keyTypes, issuers, this);
        }
        return keyManager.chooseClientAlias(keyTypes, issuers, null);
    }

    @Override
    public String chooseServerPSKIdentityHint(PSKKeyManager keyManager) {
        return keyManager.chooseServerKeyIdentityHint(this);
    }

    @Override
    public String chooseClientPSKIdentity(PSKKeyManager keyManager, String identityHint) {
        return keyManager.chooseClientKeyIdentity(identityHint, this);
    }

    @Override
    public SecretKey getPSKKey(PSKKeyManager keyManager, String identityHint, String identity) {
        return keyManager.getKey(identityHint, identity, this);
    }

    @Override
    void setUseSessionTickets(boolean useSessionTickets) {
        this.sslParameters.setUseSessionTickets(useSessionTickets);
    }

    @Override
    String[] getApplicationProtocols() {
        return this.sslParameters.getApplicationProtocols();
    }

    @Override
    void setApplicationProtocols(String[] protocols) {
        this.sslParameters.setApplicationProtocols(protocols);
    }

    @Override
    void setApplicationProtocolSelector(ApplicationProtocolSelector selector) {
        this.setApplicationProtocolSelector(selector == null ? null : new ApplicationProtocolSelectorAdapter(this, selector));
    }

    @Override
    byte[] getTlsUnique() {
        return this.ssl.getTlsUnique();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    byte[] exportKeyingMaterial(String label, byte[] context, int length) throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state < 3 || this.state == 8) {
                return null;
            }
        }
        return this.ssl.exportKeyingMaterial(label, context, length);
    }

    void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter adapter) {
        this.sslParameters.setApplicationProtocolSelector(adapter);
    }

    @Override
    public int selectApplicationProtocol(byte[] protocols) {
        ApplicationProtocolSelectorAdapter adapter = this.sslParameters.getApplicationProtocolSelector();
        if (adapter == null) {
            return 3;
        }
        return adapter.selectApplicationProtocol(protocols);
    }

    @Override
    public String getApplicationProtocol() {
        return this.provideAfterHandshakeSession().getApplicationProtocol();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getHandshakeApplicationProtocol() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            return this.state >= 2 ? this.getApplicationProtocol() : null;
        }
    }

    private ByteBuffer[] singleSrcBuffer(ByteBuffer src) {
        this.singleSrcBuffer[0] = src;
        return this.singleSrcBuffer;
    }

    private void resetSingleSrcBuffer() {
        this.singleSrcBuffer[0] = null;
    }

    private ByteBuffer[] singleDstBuffer(ByteBuffer src) {
        this.singleDstBuffer[0] = src;
        return this.singleDstBuffer;
    }

    private void resetSingleDstBuffer() {
        this.singleDstBuffer[0] = null;
    }

    private ClientSessionContext clientSessionContext() {
        return this.sslParameters.getClientSessionContext();
    }

    private AbstractSessionContext sessionContext() {
        return this.sslParameters.getSessionContext();
    }

    private void transitionTo(int newState) {
        switch (newState) {
            case 2: {
                this.handshakeFinished = false;
                this.activeSession = new ActiveSession(this.ssl, this.sslParameters.getSessionContext());
                break;
            }
            case 8: {
                if (this.ssl.isClosed() || this.state < 2 || this.state >= 8) break;
                this.closedSession = new SessionSnapshot(this.activeSession);
                break;
            }
        }
        this.state = newState;
    }
}

