/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;
import org.conscrypt.AbstractSessionContext;
import org.conscrypt.AddressUtils;
import org.conscrypt.EmptyArray;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLKey;
import org.conscrypt.PSKKeyManager;
import org.conscrypt.Platform;
import org.conscrypt.SSLParametersImpl;
import org.conscrypt.SSLUtils;

final class NativeSsl {
    private final SSLParametersImpl parameters;
    private final NativeCrypto.SSLHandshakeCallbacks handshakeCallbacks;
    private final SSLParametersImpl.AliasChooser aliasChooser;
    private final SSLParametersImpl.PSKCallbacks pskCallbacks;
    private X509Certificate[] localCertificates;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private volatile long ssl;

    private NativeSsl(long ssl, SSLParametersImpl parameters, NativeCrypto.SSLHandshakeCallbacks handshakeCallbacks, SSLParametersImpl.AliasChooser aliasChooser, SSLParametersImpl.PSKCallbacks pskCallbacks) {
        this.ssl = ssl;
        this.parameters = parameters;
        this.handshakeCallbacks = handshakeCallbacks;
        this.aliasChooser = aliasChooser;
        this.pskCallbacks = pskCallbacks;
    }

    static NativeSsl newInstance(SSLParametersImpl parameters, NativeCrypto.SSLHandshakeCallbacks handshakeCallbacks, SSLParametersImpl.AliasChooser chooser, SSLParametersImpl.PSKCallbacks pskCallbacks) throws SSLException {
        AbstractSessionContext ctx = parameters.getSessionContext();
        long ssl = NativeCrypto.SSL_new(ctx.sslCtxNativePointer, ctx);
        return new NativeSsl(ssl, parameters, handshakeCallbacks, chooser, pskCallbacks);
    }

    BioWrapper newBio() {
        try {
            return new BioWrapper();
        }
        catch (SSLException e) {
            throw new RuntimeException(e);
        }
    }

    void offerToResumeSession(long sslSessionNativePointer) throws SSLException {
        NativeCrypto.SSL_set_session(this.ssl, this, sslSessionNativePointer);
    }

    byte[] getSessionId() {
        return NativeCrypto.SSL_session_id(this.ssl, this);
    }

    long getTime() {
        return NativeCrypto.SSL_get_time(this.ssl, this);
    }

    long getTimeout() {
        return NativeCrypto.SSL_get_timeout(this.ssl, this);
    }

    void setTimeout(long millis) {
        NativeCrypto.SSL_set_timeout(this.ssl, this, millis);
    }

    String getCipherSuite() {
        return NativeCrypto.cipherSuiteToJava(NativeCrypto.SSL_get_current_cipher(this.ssl, this));
    }

    X509Certificate[] getPeerCertificates() throws CertificateException {
        byte[][] encoded = NativeCrypto.SSL_get0_peer_certificates(this.ssl, this);
        return encoded == null ? null : SSLUtils.decodeX509CertificateChain(encoded);
    }

    X509Certificate[] getLocalCertificates() {
        return this.localCertificates;
    }

    byte[] getPeerCertificateOcspData() {
        return NativeCrypto.SSL_get_ocsp_response(this.ssl, this);
    }

    byte[] getTlsUnique() {
        return NativeCrypto.SSL_get_tls_unique(this.ssl, this);
    }

    byte[] exportKeyingMaterial(String label, byte[] context, int length) throws SSLException {
        if (label == null) {
            throw new NullPointerException("Label is null");
        }
        byte[] labelBytes = label.getBytes(Charset.forName("US-ASCII"));
        return NativeCrypto.SSL_export_keying_material(this.ssl, this, labelBytes, context, length);
    }

    byte[] getPeerTlsSctData() {
        return NativeCrypto.SSL_get_signed_cert_timestamp_list(this.ssl, this);
    }

    int clientPSKKeyRequested(String identityHint, byte[] identityBytesOut, byte[] key) {
        byte[] identityBytes;
        PSKKeyManager pskKeyManager = this.parameters.getPSKKeyManager();
        if (pskKeyManager == null) {
            return 0;
        }
        String identity = this.pskCallbacks.chooseClientPSKIdentity(pskKeyManager, identityHint);
        if (identity == null) {
            identity = "";
            identityBytes = EmptyArray.BYTE;
        } else if (identity.isEmpty()) {
            identityBytes = EmptyArray.BYTE;
        } else {
            try {
                identityBytes = identity.getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UTF-8 encoding not supported", e);
            }
        }
        if (identityBytes.length + 1 > identityBytesOut.length) {
            return 0;
        }
        if (identityBytes.length > 0) {
            System.arraycopy(identityBytes, 0, identityBytesOut, 0, identityBytes.length);
        }
        identityBytesOut[identityBytes.length] = 0;
        SecretKey secretKey = this.pskCallbacks.getPSKKey(pskKeyManager, identityHint, identity);
        byte[] secretKeyBytes = secretKey.getEncoded();
        if (secretKeyBytes == null) {
            return 0;
        }
        if (secretKeyBytes.length > key.length) {
            return 0;
        }
        System.arraycopy(secretKeyBytes, 0, key, 0, secretKeyBytes.length);
        return secretKeyBytes.length;
    }

    int serverPSKKeyRequested(String identityHint, String identity, byte[] key) {
        PSKKeyManager pskKeyManager = this.parameters.getPSKKeyManager();
        if (pskKeyManager == null) {
            return 0;
        }
        SecretKey secretKey = this.pskCallbacks.getPSKKey(pskKeyManager, identityHint, identity);
        byte[] secretKeyBytes = secretKey.getEncoded();
        if (secretKeyBytes == null) {
            return 0;
        }
        if (secretKeyBytes.length > key.length) {
            return 0;
        }
        System.arraycopy(secretKeyBytes, 0, key, 0, secretKeyBytes.length);
        return secretKeyBytes.length;
    }

    void chooseClientCertificate(byte[] keyTypeBytes, int[] signatureAlgs, byte[][] asn1DerEncodedPrincipals) throws SSLException, CertificateEncodingException {
        X500Principal[] issuers;
        Set<String> keyTypesSet = SSLUtils.getSupportedClientKeyTypes(keyTypeBytes, signatureAlgs);
        String[] keyTypes = keyTypesSet.toArray(new String[keyTypesSet.size()]);
        if (asn1DerEncodedPrincipals == null) {
            issuers = null;
        } else {
            issuers = new X500Principal[asn1DerEncodedPrincipals.length];
            for (int i = 0; i < asn1DerEncodedPrincipals.length; ++i) {
                issuers[i] = new X500Principal(asn1DerEncodedPrincipals[i]);
            }
        }
        X509KeyManager keyManager = this.parameters.getX509KeyManager();
        String alias = keyManager != null ? this.aliasChooser.chooseClientAlias(keyManager, issuers, keyTypes) : null;
        this.setCertificate(alias);
    }

    private void setCertificate(String alias) throws CertificateEncodingException, SSLException {
        OpenSSLKey key;
        if (alias == null) {
            return;
        }
        X509KeyManager keyManager = this.parameters.getX509KeyManager();
        if (keyManager == null) {
            return;
        }
        PrivateKey privateKey = keyManager.getPrivateKey(alias);
        if (privateKey == null) {
            return;
        }
        this.localCertificates = keyManager.getCertificateChain(alias);
        if (this.localCertificates == null) {
            return;
        }
        int numLocalCerts = this.localCertificates.length;
        PublicKey publicKey = numLocalCerts > 0 ? this.localCertificates[0].getPublicKey() : null;
        byte[][] encodedLocalCerts = new byte[numLocalCerts][];
        for (int i = 0; i < numLocalCerts; ++i) {
            encodedLocalCerts[i] = this.localCertificates[i].getEncoded();
        }
        try {
            key = OpenSSLKey.fromPrivateKeyForTLSStackOnly(privateKey, publicKey);
        }
        catch (InvalidKeyException e) {
            throw new SSLException(e);
        }
        NativeCrypto.setLocalCertsAndPrivateKey(this.ssl, this, encodedLocalCerts, key.getNativeRef());
    }

    String getVersion() {
        return NativeCrypto.SSL_get_version(this.ssl, this);
    }

    String getRequestedServerName() {
        return NativeCrypto.SSL_get_servername(this.ssl, this);
    }

    byte[] getTlsChannelId() throws SSLException {
        return NativeCrypto.SSL_get_tls_channel_id(this.ssl, this);
    }

    void initialize(String hostname, OpenSSLKey channelIdPrivateKey) throws IOException {
        boolean enableSessionCreation = this.parameters.getEnableSessionCreation();
        if (!enableSessionCreation) {
            NativeCrypto.SSL_set_session_creation_enabled(this.ssl, this, false);
        }
        NativeCrypto.SSL_accept_renegotiations(this.ssl, this);
        if (this.isClient()) {
            NativeCrypto.SSL_set_connect_state(this.ssl, this);
            NativeCrypto.SSL_enable_ocsp_stapling(this.ssl, this);
            if (this.parameters.isCTVerificationEnabled(hostname)) {
                NativeCrypto.SSL_enable_signed_cert_timestamps(this.ssl, this);
            }
        } else {
            NativeCrypto.SSL_set_accept_state(this.ssl, this);
            if (this.parameters.getOCSPResponse() != null) {
                NativeCrypto.SSL_enable_ocsp_stapling(this.ssl, this);
            }
        }
        if (this.parameters.getEnabledProtocols().length == 0 && this.parameters.isEnabledProtocolsFiltered) {
            throw new SSLHandshakeException("No enabled protocols; SSLv3 is no longer supported and was filtered from the list");
        }
        NativeCrypto.setEnabledProtocols(this.ssl, this, this.parameters.enabledProtocols);
        NativeCrypto.setEnabledCipherSuites(this.ssl, this, this.parameters.enabledCipherSuites, this.parameters.enabledProtocols);
        if (this.parameters.applicationProtocols.length > 0) {
            NativeCrypto.setApplicationProtocols(this.ssl, this, this.isClient(), this.parameters.applicationProtocols);
        }
        if (!this.isClient() && this.parameters.applicationProtocolSelector != null) {
            NativeCrypto.setHasApplicationProtocolSelector(this.ssl, this, true);
        }
        if (!this.isClient()) {
            NativeCrypto.SSL_set_options(this.ssl, this, 0x400000L);
            if (this.parameters.sctExtension != null) {
                NativeCrypto.SSL_set_signed_cert_timestamp_list(this.ssl, this, this.parameters.sctExtension);
            }
            if (this.parameters.ocspResponse != null) {
                NativeCrypto.SSL_set_ocsp_response(this.ssl, this, this.parameters.ocspResponse);
            }
        }
        this.enablePSKKeyManagerIfRequested();
        if (this.parameters.useSessionTickets) {
            NativeCrypto.SSL_clear_options(this.ssl, this, 16384L);
        } else {
            NativeCrypto.SSL_set_options(this.ssl, this, NativeCrypto.SSL_get_options(this.ssl, this) | 0x4000L);
        }
        if (this.parameters.getUseSni() && AddressUtils.isValidSniHostname(hostname)) {
            NativeCrypto.SSL_set_tlsext_host_name(this.ssl, this, hostname);
        }
        NativeCrypto.SSL_set_mode(this.ssl, this, 256L);
        this.setCertificateValidation();
        this.setTlsChannelId(channelIdPrivateKey);
    }

    void configureServerCertificate() throws IOException {
        this.verifyWithSniMatchers(this.getRequestedServerName());
        if (this.isClient()) {
            return;
        }
        X509KeyManager keyManager = this.parameters.getX509KeyManager();
        if (keyManager != null) {
            for (String keyType : this.getCipherKeyTypes()) {
                try {
                    this.setCertificate(this.aliasChooser.chooseServerAlias(keyManager, keyType));
                }
                catch (CertificateEncodingException e) {
                    throw new IOException(e);
                }
            }
        }
    }

    private void verifyWithSniMatchers(String serverName) throws SSLHandshakeException {
        if (!AddressUtils.isValidSniHostname(serverName)) {
            return;
        }
        if (!Platform.serverNamePermitted(this.parameters, serverName)) {
            throw new SSLHandshakeException("SNI match failed: " + serverName);
        }
    }

    private Set<String> getCipherKeyTypes() {
        HashSet<String> keyTypes = new HashSet<String>();
        for (long sslCipherNativePointer : NativeCrypto.SSL_get_ciphers(this.ssl, this)) {
            String keyType = SSLUtils.getServerX509KeyType(sslCipherNativePointer);
            if (keyType == null) continue;
            keyTypes.add(keyType);
        }
        return keyTypes;
    }

    void doHandshake(FileDescriptor fd, int timeoutMillis) throws CertificateException, IOException {
        this.lock.readLock().lock();
        try {
            if (this.isClosed() || fd == null || !fd.valid()) {
                throw new SocketException("Socket is closed");
            }
            NativeCrypto.SSL_do_handshake(this.ssl, this, fd, this.handshakeCallbacks, timeoutMillis);
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    int doHandshake() throws IOException {
        this.lock.readLock().lock();
        try {
            int n = NativeCrypto.ENGINE_SSL_do_handshake(this.ssl, this, this.handshakeCallbacks);
            return n;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    int read(FileDescriptor fd, byte[] buf, int offset, int len, int timeoutMillis) throws IOException {
        this.lock.readLock().lock();
        try {
            if (this.isClosed() || fd == null || !fd.valid()) {
                throw new SocketException("Socket is closed");
            }
            int n = NativeCrypto.SSL_read(this.ssl, this, fd, this.handshakeCallbacks, buf, offset, len, timeoutMillis);
            return n;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void write(FileDescriptor fd, byte[] buf, int offset, int len, int timeoutMillis) throws IOException {
        this.lock.readLock().lock();
        try {
            if (this.isClosed() || fd == null || !fd.valid()) {
                throw new SocketException("Socket is closed");
            }
            NativeCrypto.SSL_write(this.ssl, this, fd, this.handshakeCallbacks, buf, offset, len, timeoutMillis);
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    private void enablePSKKeyManagerIfRequested() throws SSLException {
        PSKKeyManager pskKeyManager = this.parameters.getPSKKeyManager();
        if (pskKeyManager != null) {
            boolean pskEnabled = false;
            for (String enabledCipherSuite : this.parameters.enabledCipherSuites) {
                if (enabledCipherSuite == null || !enabledCipherSuite.contains("PSK")) continue;
                pskEnabled = true;
                break;
            }
            if (pskEnabled) {
                if (this.isClient()) {
                    NativeCrypto.set_SSL_psk_client_callback_enabled(this.ssl, this, true);
                } else {
                    NativeCrypto.set_SSL_psk_server_callback_enabled(this.ssl, this, true);
                    String identityHint = this.pskCallbacks.chooseServerPSKIdentityHint(pskKeyManager);
                    NativeCrypto.SSL_use_psk_identity_hint(this.ssl, this, identityHint);
                }
            }
        }
    }

    private void setTlsChannelId(OpenSSLKey channelIdPrivateKey) throws SSLException {
        if (!this.parameters.channelIdEnabled) {
            return;
        }
        if (this.parameters.getUseClientMode()) {
            if (channelIdPrivateKey == null) {
                throw new SSLHandshakeException("Invalid TLS channel ID key specified");
            }
            NativeCrypto.SSL_set1_tls_channel_id(this.ssl, this, channelIdPrivateKey.getNativeRef());
        } else {
            NativeCrypto.SSL_enable_tls_channel_id(this.ssl, this);
        }
    }

    private void setCertificateValidation() throws SSLException {
        if (!this.isClient()) {
            X509TrustManager trustManager;
            X509Certificate[] issuers;
            boolean certRequested;
            if (this.parameters.getNeedClientAuth()) {
                NativeCrypto.SSL_set_verify(this.ssl, this, 3);
                certRequested = true;
            } else if (this.parameters.getWantClientAuth()) {
                NativeCrypto.SSL_set_verify(this.ssl, this, 1);
                certRequested = true;
            } else {
                NativeCrypto.SSL_set_verify(this.ssl, this, 0);
                certRequested = false;
            }
            if (certRequested && (issuers = (trustManager = this.parameters.getX509TrustManager()).getAcceptedIssuers()) != null && issuers.length != 0) {
                byte[][] issuersBytes;
                try {
                    issuersBytes = SSLUtils.encodeSubjectX509Principals(issuers);
                }
                catch (CertificateEncodingException e) {
                    throw new SSLException("Problem encoding principals", e);
                }
                NativeCrypto.SSL_set_client_CA_list(this.ssl, this, issuersBytes);
            }
        }
    }

    void interrupt() {
        NativeCrypto.SSL_interrupt(this.ssl, this);
    }

    void shutdown(FileDescriptor fd) throws IOException {
        NativeCrypto.SSL_shutdown(this.ssl, this, fd, this.handshakeCallbacks);
    }

    void shutdown() throws IOException {
        this.lock.readLock().lock();
        try {
            NativeCrypto.ENGINE_SSL_shutdown(this.ssl, this, this.handshakeCallbacks);
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    boolean wasShutdownReceived() {
        this.lock.readLock().lock();
        try {
            boolean bl = (NativeCrypto.SSL_get_shutdown(this.ssl, this) & 2) != 0;
            return bl;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    boolean wasShutdownSent() {
        this.lock.readLock().lock();
        try {
            boolean bl = (NativeCrypto.SSL_get_shutdown(this.ssl, this) & 1) != 0;
            return bl;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    int readDirectByteBuffer(long destAddress, int destLength) throws IOException, CertificateException {
        this.lock.readLock().lock();
        try {
            int n = NativeCrypto.ENGINE_SSL_read_direct(this.ssl, this, destAddress, destLength, this.handshakeCallbacks);
            return n;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    int writeDirectByteBuffer(long sourceAddress, int sourceLength) throws IOException {
        this.lock.readLock().lock();
        try {
            int n = NativeCrypto.ENGINE_SSL_write_direct(this.ssl, this, sourceAddress, sourceLength, this.handshakeCallbacks);
            return n;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    void forceRead() throws IOException {
        this.lock.readLock().lock();
        try {
            NativeCrypto.ENGINE_SSL_force_read(this.ssl, this, this.handshakeCallbacks);
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    int getPendingReadableBytes() {
        this.lock.readLock().lock();
        try {
            if (!this.isClosed()) {
                int n = NativeCrypto.SSL_pending_readable_bytes(this.ssl, this);
                return n;
            }
            int n = 0;
            return n;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    int getMaxSealOverhead() {
        return NativeCrypto.SSL_max_seal_overhead(this.ssl, this);
    }

    void close() {
        this.lock.writeLock().lock();
        try {
            if (!this.isClosed()) {
                long toFree = this.ssl;
                this.ssl = 0L;
                NativeCrypto.SSL_free(toFree, this);
            }
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    boolean isClosed() {
        return this.ssl == 0L;
    }

    int getError(int result) {
        return NativeCrypto.SSL_get_error(this.ssl, this, result);
    }

    byte[] getApplicationProtocol() {
        return NativeCrypto.getApplicationProtocol(this.ssl, this);
    }

    private boolean isClient() {
        return this.parameters.getUseClientMode();
    }

    protected final void finalize() throws Throwable {
        try {
            this.close();
        }
        finally {
            super.finalize();
        }
    }

    final class BioWrapper {
        private volatile long bio;

        private BioWrapper() throws SSLException {
            this.bio = NativeCrypto.SSL_BIO_new(NativeSsl.this.ssl, NativeSsl.this);
        }

        int getPendingWrittenBytes() {
            NativeSsl.this.lock.readLock().lock();
            try {
                int n = this.bio == 0L ? 0 : NativeCrypto.SSL_pending_written_bytes_in_BIO(this.bio);
                return n;
            }
            finally {
                NativeSsl.this.lock.readLock().unlock();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        int writeDirectByteBuffer(long address, int length) throws IOException {
            NativeSsl.this.lock.readLock().lock();
            try {
                if (NativeSsl.this.isClosed()) {
                    throw new SSLException("Connection closed");
                }
                int n = NativeCrypto.ENGINE_SSL_write_BIO_direct(NativeSsl.this.ssl, NativeSsl.this, this.bio, address, length, NativeSsl.this.handshakeCallbacks);
                return n;
            }
            finally {
                NativeSsl.this.lock.readLock().unlock();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        int readDirectByteBuffer(long destAddress, int destLength) throws IOException {
            NativeSsl.this.lock.readLock().lock();
            try {
                if (NativeSsl.this.isClosed()) {
                    throw new SSLException("Connection closed");
                }
                int n = NativeCrypto.ENGINE_SSL_read_BIO_direct(NativeSsl.this.ssl, NativeSsl.this, this.bio, destAddress, destLength, NativeSsl.this.handshakeCallbacks);
                return n;
            }
            finally {
                NativeSsl.this.lock.readLock().unlock();
            }
        }

        void close() {
            NativeSsl.this.lock.writeLock().lock();
            try {
                long toFree = this.bio;
                this.bio = 0L;
                if (toFree != 0L) {
                    NativeCrypto.BIO_free_all(toFree);
                }
            }
            finally {
                NativeSsl.this.lock.writeLock().unlock();
            }
        }
    }
}

