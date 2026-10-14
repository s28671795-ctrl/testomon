/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECKey;
import java.security.spec.ECParameterSpec;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;
import org.conscrypt.AbstractSessionContext;
import org.conscrypt.ActiveSession;
import org.conscrypt.ApplicationProtocolSelector;
import org.conscrypt.ApplicationProtocolSelectorAdapter;
import org.conscrypt.ArrayUtils;
import org.conscrypt.ClientSessionContext;
import org.conscrypt.ConscryptSession;
import org.conscrypt.ExternalSession;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.NativeSsl;
import org.conscrypt.NativeSslSession;
import org.conscrypt.OpenSSLECGroupContext;
import org.conscrypt.OpenSSLKey;
import org.conscrypt.OpenSSLSocketImpl;
import org.conscrypt.PSKKeyManager;
import org.conscrypt.Platform;
import org.conscrypt.SSLNullSession;
import org.conscrypt.SSLParametersImpl;
import org.conscrypt.SSLUtils;
import org.conscrypt.SessionSnapshot;

class ConscryptFileDescriptorSocket
extends OpenSSLSocketImpl
implements NativeCrypto.SSLHandshakeCallbacks,
SSLParametersImpl.PSKCallbacks,
SSLParametersImpl.AliasChooser {
    private static final boolean DBG_STATE = false;
    private int state = 0;
    private final NativeSsl ssl;
    private SSLInputStream is;
    private SSLOutputStream os;
    private final SSLParametersImpl sslParameters;
    private final Object guard = Platform.closeGuardGet();
    private OpenSSLKey channelIdPrivateKey;
    private final ActiveSession activeSession;
    private SessionSnapshot closedSession;
    private final SSLSession externalSession = Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider(){

        @Override
        public ConscryptSession provideSession() {
            return ConscryptFileDescriptorSocket.this.provideSession();
        }
    }));
    private int writeTimeoutMilliseconds = 0;
    private int handshakeTimeoutMilliseconds = -1;

    ConscryptFileDescriptorSocket(SSLParametersImpl sslParameters) throws IOException {
        this.sslParameters = sslParameters;
        this.ssl = ConscryptFileDescriptorSocket.newSsl(sslParameters, this);
        this.activeSession = new ActiveSession(this.ssl, sslParameters.getSessionContext());
    }

    ConscryptFileDescriptorSocket(String hostname, int port, SSLParametersImpl sslParameters) throws IOException {
        super(hostname, port);
        this.sslParameters = sslParameters;
        this.ssl = ConscryptFileDescriptorSocket.newSsl(sslParameters, this);
        this.activeSession = new ActiveSession(this.ssl, sslParameters.getSessionContext());
    }

    ConscryptFileDescriptorSocket(InetAddress address, int port, SSLParametersImpl sslParameters) throws IOException {
        super(address, port);
        this.sslParameters = sslParameters;
        this.ssl = ConscryptFileDescriptorSocket.newSsl(sslParameters, this);
        this.activeSession = new ActiveSession(this.ssl, sslParameters.getSessionContext());
    }

    ConscryptFileDescriptorSocket(String hostname, int port, InetAddress clientAddress, int clientPort, SSLParametersImpl sslParameters) throws IOException {
        super(hostname, port, clientAddress, clientPort);
        this.sslParameters = sslParameters;
        this.ssl = ConscryptFileDescriptorSocket.newSsl(sslParameters, this);
        this.activeSession = new ActiveSession(this.ssl, sslParameters.getSessionContext());
    }

    ConscryptFileDescriptorSocket(InetAddress address, int port, InetAddress clientAddress, int clientPort, SSLParametersImpl sslParameters) throws IOException {
        super(address, port, clientAddress, clientPort);
        this.sslParameters = sslParameters;
        this.ssl = ConscryptFileDescriptorSocket.newSsl(sslParameters, this);
        this.activeSession = new ActiveSession(this.ssl, sslParameters.getSessionContext());
    }

    ConscryptFileDescriptorSocket(Socket socket, String hostname, int port, boolean autoClose, SSLParametersImpl sslParameters) throws IOException {
        super(socket, hostname, port, autoClose);
        this.sslParameters = sslParameters;
        this.ssl = ConscryptFileDescriptorSocket.newSsl(sslParameters, this);
        this.activeSession = new ActiveSession(this.ssl, sslParameters.getSessionContext());
    }

    private static NativeSsl newSsl(SSLParametersImpl sslParameters, ConscryptFileDescriptorSocket engine) throws SSLException {
        return NativeSsl.newInstance(sslParameters, engine, engine, engine);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final void startHandshake() throws IOException {
        this.checkOpen();
        NativeSsl nativeSsl = this.ssl;
        // MONITORENTER : nativeSsl
        if (this.state != 0) {
            // MONITOREXIT : nativeSsl
            return;
        }
        this.transitionTo(2);
        // MONITOREXIT : nativeSsl
        boolean releaseResources = true;
        try {
            NativeSslSession cachedSession;
            Platform.closeGuardOpen(this.guard, "close");
            this.ssl.initialize(this.getHostname(), this.channelIdPrivateKey);
            if (this.getUseClientMode() && (cachedSession = this.clientSessionContext().getCachedSession(this.getHostnameOrIP(), this.getPort(), this.sslParameters)) != null) {
                cachedSession.offerToResume(this.ssl);
            }
            int savedReadTimeoutMilliseconds = this.getSoTimeout();
            int savedWriteTimeoutMilliseconds = this.getSoWriteTimeout();
            if (this.handshakeTimeoutMilliseconds >= 0) {
                this.setSoTimeout(this.handshakeTimeoutMilliseconds);
                this.setSoWriteTimeout(this.handshakeTimeoutMilliseconds);
            }
            NativeSsl nativeSsl2 = this.ssl;
            // MONITORENTER : nativeSsl2
            if (this.state == 8) {
                // MONITOREXIT : nativeSsl2
                return;
            }
            // MONITOREXIT : nativeSsl2
            try {
                this.ssl.doHandshake(Platform.getFileDescriptor(this.socket), this.getSoTimeout());
                this.activeSession.onPeerCertificateAvailable(this.getHostnameOrIP(), this.getPort());
            }
            catch (CertificateException e) {
                SSLHandshakeException wrapper = new SSLHandshakeException(e.getMessage());
                wrapper.initCause(e);
                throw wrapper;
            }
            catch (SSLException e) {
                NativeSsl wrapper = this.ssl;
                // MONITORENTER : wrapper
                if (this.state != 8) {
                    // MONITOREXIT : wrapper
                    String message = e.getMessage();
                    if (!message.contains("unexpected CCS")) throw e;
                    String logMessage = String.format("ssl_unexpected_ccs: host=%s", this.getHostnameOrIP());
                    Platform.logEvent(logMessage);
                    throw e;
                }
                // MONITOREXIT : wrapper
                if (!releaseResources) return;
                NativeSsl nativeSsl3 = this.ssl;
                // MONITORENTER : nativeSsl3
                this.transitionTo(8);
                this.ssl.notifyAll();
                // MONITOREXIT : nativeSsl3
                try {
                    this.shutdownAndFreeSslNative();
                    return;
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                return;
            }
            nativeSsl2 = this.ssl;
            // MONITORENTER : nativeSsl2
            if (this.state == 8) {
                // MONITOREXIT : nativeSsl2
                return;
            }
            // MONITOREXIT : nativeSsl2
            if (this.handshakeTimeoutMilliseconds >= 0) {
                this.setSoTimeout(savedReadTimeoutMilliseconds);
                this.setSoWriteTimeout(savedWriteTimeoutMilliseconds);
            }
            nativeSsl2 = this.ssl;
            // MONITORENTER : nativeSsl2
            boolean bl = releaseResources = this.state == 8;
            if (this.state == 2) {
                this.transitionTo(4);
            } else {
                this.transitionTo(5);
            }
            if (!releaseResources) {
                this.ssl.notifyAll();
            }
            // MONITOREXIT : nativeSsl2
            return;
        }
        catch (SSLProtocolException e) {
            throw (SSLHandshakeException)new SSLHandshakeException("Handshake failed").initCause(e);
        }
        finally {
            if (releaseResources) {
                NativeSsl nativeSsl4 = this.ssl;
            }
        }
    }

    @Override
    public final void clientCertificateRequested(byte[] keyTypeBytes, int[] signatureAlgs, byte[][] asn1DerEncodedPrincipals) throws CertificateEncodingException, SSLException {
        this.ssl.chooseClientCertificate(keyTypeBytes, signatureAlgs, asn1DerEncodedPrincipals);
    }

    @Override
    public final int clientPSKKeyRequested(String identityHint, byte[] identity, byte[] key) {
        return this.ssl.clientPSKKeyRequested(identityHint, identity, key);
    }

    @Override
    public final int serverPSKKeyRequested(String identityHint, String identity, byte[] key) {
        return this.ssl.serverPSKKeyRequested(identityHint, identity, key);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void onSSLStateChange(int type, int val) {
        if (type != 32) {
            return;
        }
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8) {
                return;
            }
            this.transitionTo(5);
        }
        this.notifyHandshakeCompletedListeners();
        nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            this.ssl.notifyAll();
        }
    }

    @Override
    public final void onNewSessionEstablished(long sslSessionNativePtr) {
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
    public final long serverSessionRequested(byte[] id) {
        return 0L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void serverCertificateRequested() throws IOException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            this.ssl.configureServerCertificate();
        }
    }

    @Override
    public final void verifyCertificateChain(byte[][] certChain, String authMethod) throws CertificateException {
        try {
            if (certChain == null || certChain.length == 0) {
                throw new CertificateException("Peer sent no certificate");
            }
            X509Certificate[] peerCertChain = SSLUtils.decodeX509CertificateChain(certChain);
            X509TrustManager x509tm = this.sslParameters.getX509TrustManager();
            if (x509tm == null) {
                throw new CertificateException("No X.509 TrustManager");
            }
            this.activeSession.onPeerCertificatesReceived(this.getHostnameOrIP(), this.getPort(), peerCertChain);
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final InputStream getInputStream() throws IOException {
        SSLInputStream returnVal;
        this.checkOpen();
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8) {
                throw new SocketException("Socket is closed.");
            }
            if (this.is == null) {
                this.is = new SSLInputStream();
            }
            returnVal = this.is;
        }
        this.waitForHandshake();
        return returnVal;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final OutputStream getOutputStream() throws IOException {
        SSLOutputStream returnVal;
        this.checkOpen();
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8) {
                throw new SocketException("Socket is closed.");
            }
            if (this.os == null) {
                this.os = new SSLOutputStream();
            }
            returnVal = this.os;
        }
        this.waitForHandshake();
        return returnVal;
    }

    private void assertReadableOrWriteableState() {
        if (this.state == 5 || this.state == 4) {
            return;
        }
        throw new AssertionError((Object)("Invalid state: " + this.state));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void waitForHandshake() throws IOException {
        this.startHandshake();
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            while (this.state != 5 && this.state != 4 && this.state != 8) {
                try {
                    this.ssl.wait();
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

    @Override
    public final SSLSession getSession() {
        return this.externalSession;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ConscryptSession provideSession() {
        boolean handshakeCompleted = false;
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8) {
                return this.closedSession != null ? this.closedSession : SSLNullSession.getNullSession();
            }
            try {
                boolean bl = handshakeCompleted = this.state >= 5;
                if (!handshakeCompleted && this.isConnected()) {
                    this.waitForHandshake();
                    handshakeCompleted = true;
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if (!handshakeCompleted) {
            return SSLNullSession.getNullSession();
        }
        return this.activeSession;
    }

    private ConscryptSession provideAfterHandshakeSession() {
        return this.state < 2 ? SSLNullSession.getNullSession() : this.provideSession();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ConscryptSession provideHandshakeSession() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            return this.state >= 2 && this.state < 5 ? this.activeSession : SSLNullSession.getNullSession();
        }
    }

    @Override
    final SSLSession getActiveSession() {
        return this.activeSession;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final SSLSession getHandshakeSession() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state >= 2 && this.state < 5) {
                return Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider(){

                    @Override
                    public ConscryptSession provideSession() {
                        return ConscryptFileDescriptorSocket.this.provideHandshakeSession();
                    }
                }));
            }
            return null;
        }
    }

    @Override
    public final boolean getEnableSessionCreation() {
        return this.sslParameters.getEnableSessionCreation();
    }

    @Override
    public final void setEnableSessionCreation(boolean flag) {
        this.sslParameters.setEnableSessionCreation(flag);
    }

    @Override
    public final String[] getSupportedCipherSuites() {
        return NativeCrypto.getSupportedCipherSuites();
    }

    @Override
    public final String[] getEnabledCipherSuites() {
        return this.sslParameters.getEnabledCipherSuites();
    }

    @Override
    public final void setEnabledCipherSuites(String[] suites) {
        this.sslParameters.setEnabledCipherSuites(suites);
    }

    @Override
    public final String[] getSupportedProtocols() {
        return NativeCrypto.getSupportedProtocols();
    }

    @Override
    public final String[] getEnabledProtocols() {
        return this.sslParameters.getEnabledProtocols();
    }

    @Override
    public final void setEnabledProtocols(String[] protocols) {
        this.sslParameters.setEnabledProtocols(protocols);
    }

    @Override
    public final void setUseSessionTickets(boolean useSessionTickets) {
        this.sslParameters.setUseSessionTickets(useSessionTickets);
    }

    @Override
    public final void setHostname(String hostname) {
        this.sslParameters.setUseSni(hostname != null);
        super.setHostname(hostname);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void setChannelIdEnabled(boolean enabled) {
        if (this.getUseClientMode()) {
            throw new IllegalStateException("Client mode");
        }
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state != 0) {
                throw new IllegalStateException("Could not enable/disable Channel ID after the initial handshake has begun.");
            }
        }
        this.sslParameters.channelIdEnabled = enabled;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final byte[] getChannelId() throws SSLException {
        if (this.getUseClientMode()) {
            throw new IllegalStateException("Client mode");
        }
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state != 5) {
                throw new IllegalStateException("Channel ID is only available after handshake completes");
            }
        }
        return this.ssl.getTlsChannelId();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void setChannelIdPrivateKey(PrivateKey privateKey) {
        if (!this.getUseClientMode()) {
            throw new IllegalStateException("Server mode");
        }
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state != 0) {
                throw new IllegalStateException("Could not change Channel ID private key after the initial handshake has begun.");
            }
        }
        if (privateKey == null) {
            this.sslParameters.channelIdEnabled = false;
            this.channelIdPrivateKey = null;
        } else {
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

    @Override
    public final boolean getUseClientMode() {
        return this.sslParameters.getUseClientMode();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void setUseClientMode(boolean mode) {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state != 0) {
                throw new IllegalArgumentException("Could not change the mode after the initial handshake has begun.");
            }
        }
        this.sslParameters.setUseClientMode(mode);
    }

    @Override
    public final boolean getWantClientAuth() {
        return this.sslParameters.getWantClientAuth();
    }

    @Override
    public final boolean getNeedClientAuth() {
        return this.sslParameters.getNeedClientAuth();
    }

    @Override
    public final void setNeedClientAuth(boolean need) {
        this.sslParameters.setNeedClientAuth(need);
    }

    @Override
    public final void setWantClientAuth(boolean want) {
        this.sslParameters.setWantClientAuth(want);
    }

    @Override
    public final void setSoWriteTimeout(int writeTimeoutMilliseconds) throws SocketException {
        this.writeTimeoutMilliseconds = writeTimeoutMilliseconds;
        Platform.setSocketWriteTimeout(this, writeTimeoutMilliseconds);
    }

    @Override
    public final int getSoWriteTimeout() throws SocketException {
        return this.writeTimeoutMilliseconds;
    }

    @Override
    public final void setHandshakeTimeout(int handshakeTimeoutMilliseconds) throws SocketException {
        this.handshakeTimeoutMilliseconds = handshakeTimeoutMilliseconds;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void close() throws IOException {
        SSLOutputStream sslOutputStream;
        SSLInputStream sslInputStream;
        if (this.ssl == null) {
            return;
        }
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8) {
                return;
            }
            int oldState = this.state;
            this.transitionTo(8);
            if (oldState == 0) {
                this.free();
                this.closeUnderlyingSocket();
                this.ssl.notifyAll();
                return;
            }
            if (oldState != 5 && oldState != 4) {
                this.ssl.interrupt();
                this.ssl.notifyAll();
                return;
            }
            this.ssl.notifyAll();
            sslInputStream = this.is;
            sslOutputStream = this.os;
        }
        if (sslInputStream != null || sslOutputStream != null) {
            this.ssl.interrupt();
        }
        if (sslInputStream != null) {
            sslInputStream.awaitPendingOps();
        }
        if (sslOutputStream != null) {
            sslOutputStream.awaitPendingOps();
        }
        this.shutdownAndFreeSslNative();
    }

    private void shutdownAndFreeSslNative() throws IOException {
        try {
            Platform.blockGuardOnNetwork();
            this.ssl.shutdown(Platform.getFileDescriptor(this.socket));
        }
        catch (IOException iOException) {
        }
        finally {
            this.free();
            this.closeUnderlyingSocket();
        }
    }

    private void closeUnderlyingSocket() throws IOException {
        super.close();
    }

    private void free() {
        if (!this.ssl.isClosed()) {
            this.ssl.close();
            Platform.closeGuardClose(this.guard);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final void finalize() throws Throwable {
        block7: {
            try {
                if (this.guard != null) {
                    Platform.closeGuardWarnIfOpen(this.guard);
                }
                if (this.ssl == null) break block7;
                NativeSsl nativeSsl = this.ssl;
                synchronized (nativeSsl) {
                    this.transitionTo(8);
                }
            }
            finally {
                super.finalize();
            }
        }
    }

    @Override
    public final void setApplicationProtocolSelector(ApplicationProtocolSelector selector) {
        this.setApplicationProtocolSelector(selector == null ? null : new ApplicationProtocolSelectorAdapter(this, selector));
    }

    @Override
    final void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter selector) {
        this.sslParameters.setApplicationProtocolSelector(selector);
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
    final void setApplicationProtocols(String[] protocols) {
        this.sslParameters.setApplicationProtocols(protocols);
    }

    @Override
    final String[] getApplicationProtocols() {
        return this.sslParameters.getApplicationProtocols();
    }

    @Override
    public final String getApplicationProtocol() {
        return this.provideAfterHandshakeSession().getApplicationProtocol();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final String getHandshakeApplicationProtocol() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            return this.state >= 2 && this.state < 5 ? this.getApplicationProtocol() : null;
        }
    }

    @Override
    public final SSLParameters getSSLParameters() {
        SSLParameters params = super.getSSLParameters();
        Platform.getSSLParameters(params, this.sslParameters, this);
        return params;
    }

    @Override
    public final void setSSLParameters(SSLParameters p) {
        super.setSSLParameters(p);
        Platform.setSSLParameters(p, this.sslParameters, this);
    }

    @Override
    public final String chooseServerPSKIdentityHint(PSKKeyManager keyManager) {
        return keyManager.chooseServerKeyIdentityHint(this);
    }

    @Override
    public final String chooseClientPSKIdentity(PSKKeyManager keyManager, String identityHint) {
        return keyManager.chooseClientKeyIdentity(identityHint, this);
    }

    @Override
    public final SecretKey getPSKKey(PSKKeyManager keyManager, String identityHint, String identity) {
        return keyManager.getKey(identityHint, identity, this);
    }

    @Override
    public final String chooseServerAlias(X509KeyManager keyManager, String keyType) {
        return keyManager.chooseServerAlias(keyType, null, this);
    }

    @Override
    public final String chooseClientAlias(X509KeyManager keyManager, X500Principal[] issuers, String[] keyTypes) {
        return keyManager.chooseClientAlias(keyTypes, issuers, this);
    }

    private ClientSessionContext clientSessionContext() {
        return this.sslParameters.getClientSessionContext();
    }

    private AbstractSessionContext sessionContext() {
        return this.sslParameters.getSessionContext();
    }

    private void transitionTo(int newState) {
        switch (newState) {
            case 8: {
                if (this.ssl.isClosed() || this.state < 2 || this.state >= 8) break;
                this.closedSession = new SessionSnapshot(this.activeSession);
                break;
            }
        }
        this.state = newState;
    }

    private class SSLOutputStream
    extends OutputStream {
        private final Object writeLock = new Object();

        SSLOutputStream() {
        }

        @Override
        public void write(int oneByte) throws IOException {
            byte[] buffer = new byte[]{(byte)(oneByte & 0xFF)};
            this.write(buffer);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void write(byte[] buf, int offset, int byteCount) throws IOException {
            Platform.blockGuardOnNetwork();
            ConscryptFileDescriptorSocket.this.checkOpen();
            ArrayUtils.checkOffsetAndCount(buf.length, offset, byteCount);
            if (byteCount == 0) {
                return;
            }
            Object object = this.writeLock;
            synchronized (object) {
                NativeSsl nativeSsl = ConscryptFileDescriptorSocket.this.ssl;
                synchronized (nativeSsl) {
                    if (ConscryptFileDescriptorSocket.this.state == 8) {
                        throw new SocketException("socket is closed");
                    }
                }
                ConscryptFileDescriptorSocket.this.ssl.write(Platform.getFileDescriptor(ConscryptFileDescriptorSocket.this.socket), buf, offset, byteCount, ConscryptFileDescriptorSocket.this.writeTimeoutMilliseconds);
                nativeSsl = ConscryptFileDescriptorSocket.this.ssl;
                synchronized (nativeSsl) {
                    if (ConscryptFileDescriptorSocket.this.state == 8) {
                        throw new SocketException("socket is closed");
                    }
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void awaitPendingOps() {
            Object object = this.writeLock;
            synchronized (object) {
            }
        }
    }

    private class SSLInputStream
    extends InputStream {
        private final Object readLock = new Object();

        SSLInputStream() {
        }

        @Override
        public int read() throws IOException {
            byte[] buffer = new byte[1];
            int result = this.read(buffer, 0, 1);
            return result != -1 ? buffer[0] & 0xFF : -1;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int read(byte[] buf, int offset, int byteCount) throws IOException {
            Platform.blockGuardOnNetwork();
            ConscryptFileDescriptorSocket.this.checkOpen();
            ArrayUtils.checkOffsetAndCount(buf.length, offset, byteCount);
            if (byteCount == 0) {
                return 0;
            }
            Object object = this.readLock;
            synchronized (object) {
                NativeSsl nativeSsl = ConscryptFileDescriptorSocket.this.ssl;
                synchronized (nativeSsl) {
                    if (ConscryptFileDescriptorSocket.this.state == 8) {
                        throw new SocketException("socket is closed");
                    }
                }
                int ret = ConscryptFileDescriptorSocket.this.ssl.read(Platform.getFileDescriptor(ConscryptFileDescriptorSocket.this.socket), buf, offset, byteCount, ConscryptFileDescriptorSocket.this.getSoTimeout());
                if (ret == -1) {
                    NativeSsl nativeSsl2 = ConscryptFileDescriptorSocket.this.ssl;
                    synchronized (nativeSsl2) {
                        if (ConscryptFileDescriptorSocket.this.state == 8) {
                            throw new SocketException("socket is closed");
                        }
                    }
                }
                return ret;
            }
        }

        @Override
        public int available() {
            return ConscryptFileDescriptorSocket.this.ssl.getPendingReadableBytes();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void awaitPendingOps() {
            Object object = this.readLock;
            synchronized (object) {
            }
        }
    }
}

