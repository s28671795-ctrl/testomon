/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.KeyManagementException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.Properties;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLContextSpi;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.conscrypt.AbstractConscryptEngine;
import org.conscrypt.AbstractConscryptSocket;
import org.conscrypt.ApplicationProtocolSelector;
import org.conscrypt.BufferAllocator;
import org.conscrypt.ClientSessionContext;
import org.conscrypt.ConscryptEngine;
import org.conscrypt.ConscryptEngineSocket;
import org.conscrypt.ConscryptHostnameVerifier;
import org.conscrypt.HandshakeListener;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLContextImpl;
import org.conscrypt.OpenSSLProvider;
import org.conscrypt.OpenSSLServerSocketFactoryImpl;
import org.conscrypt.OpenSSLSocketFactoryImpl;
import org.conscrypt.Platform;
import org.conscrypt.SSLClientSessionCache;
import org.conscrypt.SSLParametersImpl;
import org.conscrypt.SSLServerSessionCache;
import org.conscrypt.ServerSessionContext;
import org.conscrypt.TrustManagerImpl;
import org.conscrypt.io.IoUtils;

public final class Conscrypt {
    private static final Version VERSION;

    private Conscrypt() {
    }

    public static boolean isAvailable() {
        try {
            Conscrypt.checkAvailability();
            return true;
        }
        catch (Throwable e) {
            return false;
        }
    }

    public static Version version() {
        return VERSION;
    }

    public static void checkAvailability() {
        NativeCrypto.checkAvailability();
    }

    public static boolean isConscrypt(Provider provider) {
        return provider instanceof OpenSSLProvider;
    }

    public static Provider newProvider() {
        Conscrypt.checkAvailability();
        return new OpenSSLProvider();
    }

    @Deprecated
    public static Provider newProvider(String providerName) {
        Conscrypt.checkAvailability();
        return Conscrypt.newProviderBuilder().setName(providerName).build();
    }

    public static ProviderBuilder newProviderBuilder() {
        return new ProviderBuilder();
    }

    public static int maxEncryptedPacketLength() {
        return 16709;
    }

    public static X509TrustManager getDefaultX509TrustManager() throws KeyManagementException {
        Conscrypt.checkAvailability();
        return SSLParametersImpl.getDefaultX509TrustManager();
    }

    public static boolean isConscrypt(SSLContext context) {
        return context.getProvider() instanceof OpenSSLProvider;
    }

    public static SSLContextSpi newPreferredSSLContextSpi() {
        Conscrypt.checkAvailability();
        return OpenSSLContextImpl.getPreferred();
    }

    public static void setClientSessionCache(SSLContext context, SSLClientSessionCache cache) {
        SSLSessionContext clientContext = context.getClientSessionContext();
        if (!(clientContext instanceof ClientSessionContext)) {
            throw new IllegalArgumentException("Not a conscrypt client context: " + clientContext.getClass().getName());
        }
        ((ClientSessionContext)clientContext).setPersistentCache(cache);
    }

    public static void setServerSessionCache(SSLContext context, SSLServerSessionCache cache) {
        SSLSessionContext serverContext = context.getServerSessionContext();
        if (!(serverContext instanceof ServerSessionContext)) {
            throw new IllegalArgumentException("Not a conscrypt client context: " + serverContext.getClass().getName());
        }
        ((ServerSessionContext)serverContext).setPersistentCache(cache);
    }

    public static boolean isConscrypt(SSLSocketFactory factory2) {
        return factory2 instanceof OpenSSLSocketFactoryImpl;
    }

    private static OpenSSLSocketFactoryImpl toConscrypt(SSLSocketFactory factory2) {
        if (!Conscrypt.isConscrypt(factory2)) {
            throw new IllegalArgumentException("Not a conscrypt socket factory: " + factory2.getClass().getName());
        }
        return (OpenSSLSocketFactoryImpl)factory2;
    }

    public static void setUseEngineSocketByDefault(boolean useEngineSocket) {
        OpenSSLSocketFactoryImpl.setUseEngineSocketByDefault(useEngineSocket);
        OpenSSLServerSocketFactoryImpl.setUseEngineSocketByDefault(useEngineSocket);
    }

    public static void setUseEngineSocket(SSLSocketFactory factory2, boolean useEngineSocket) {
        Conscrypt.toConscrypt(factory2).setUseEngineSocket(useEngineSocket);
    }

    public static boolean isConscrypt(SSLServerSocketFactory factory2) {
        return factory2 instanceof OpenSSLServerSocketFactoryImpl;
    }

    private static OpenSSLServerSocketFactoryImpl toConscrypt(SSLServerSocketFactory factory2) {
        if (!Conscrypt.isConscrypt(factory2)) {
            throw new IllegalArgumentException("Not a conscrypt server socket factory: " + factory2.getClass().getName());
        }
        return (OpenSSLServerSocketFactoryImpl)factory2;
    }

    public static void setUseEngineSocket(SSLServerSocketFactory factory2, boolean useEngineSocket) {
        Conscrypt.toConscrypt(factory2).setUseEngineSocket(useEngineSocket);
    }

    public static boolean isConscrypt(SSLSocket socket) {
        return socket instanceof AbstractConscryptSocket;
    }

    private static AbstractConscryptSocket toConscrypt(SSLSocket socket) {
        if (!Conscrypt.isConscrypt(socket)) {
            throw new IllegalArgumentException("Not a conscrypt socket: " + socket.getClass().getName());
        }
        return (AbstractConscryptSocket)socket;
    }

    public static void setHostname(SSLSocket socket, String hostname) {
        Conscrypt.toConscrypt(socket).setHostname(hostname);
    }

    public static String getHostname(SSLSocket socket) {
        return Conscrypt.toConscrypt(socket).getHostname();
    }

    public static String getHostnameOrIP(SSLSocket socket) {
        return Conscrypt.toConscrypt(socket).getHostnameOrIP();
    }

    public static void setUseSessionTickets(SSLSocket socket, boolean useSessionTickets) {
        Conscrypt.toConscrypt(socket).setUseSessionTickets(useSessionTickets);
    }

    public static void setChannelIdEnabled(SSLSocket socket, boolean enabled) {
        Conscrypt.toConscrypt(socket).setChannelIdEnabled(enabled);
    }

    public static byte[] getChannelId(SSLSocket socket) throws SSLException {
        return Conscrypt.toConscrypt(socket).getChannelId();
    }

    public static void setChannelIdPrivateKey(SSLSocket socket, PrivateKey privateKey) {
        Conscrypt.toConscrypt(socket).setChannelIdPrivateKey(privateKey);
    }

    public static String getApplicationProtocol(SSLSocket socket) {
        return Conscrypt.toConscrypt(socket).getApplicationProtocol();
    }

    public static void setApplicationProtocolSelector(SSLSocket socket, ApplicationProtocolSelector selector) {
        Conscrypt.toConscrypt(socket).setApplicationProtocolSelector(selector);
    }

    public static void setApplicationProtocols(SSLSocket socket, String[] protocols) {
        Conscrypt.toConscrypt(socket).setApplicationProtocols(protocols);
    }

    public static String[] getApplicationProtocols(SSLSocket socket) {
        return Conscrypt.toConscrypt(socket).getApplicationProtocols();
    }

    public static byte[] getTlsUnique(SSLSocket socket) {
        return Conscrypt.toConscrypt(socket).getTlsUnique();
    }

    public static byte[] exportKeyingMaterial(SSLSocket socket, String label, byte[] context, int length) throws SSLException {
        return Conscrypt.toConscrypt(socket).exportKeyingMaterial(label, context, length);
    }

    public static boolean isConscrypt(SSLEngine engine) {
        return engine instanceof AbstractConscryptEngine;
    }

    private static AbstractConscryptEngine toConscrypt(SSLEngine engine) {
        if (!Conscrypt.isConscrypt(engine)) {
            throw new IllegalArgumentException("Not a conscrypt engine: " + engine.getClass().getName());
        }
        return (AbstractConscryptEngine)engine;
    }

    public static void setBufferAllocator(SSLEngine engine, BufferAllocator bufferAllocator) {
        Conscrypt.toConscrypt(engine).setBufferAllocator(bufferAllocator);
    }

    public static void setBufferAllocator(SSLSocket socket, BufferAllocator bufferAllocator) {
        AbstractConscryptSocket s2 = Conscrypt.toConscrypt(socket);
        if (s2 instanceof ConscryptEngineSocket) {
            ((ConscryptEngineSocket)s2).setBufferAllocator(bufferAllocator);
        }
    }

    public static void setDefaultBufferAllocator(BufferAllocator bufferAllocator) {
        ConscryptEngine.setDefaultBufferAllocator(bufferAllocator);
    }

    public static void setHostname(SSLEngine engine, String hostname) {
        Conscrypt.toConscrypt(engine).setHostname(hostname);
    }

    public static String getHostname(SSLEngine engine) {
        return Conscrypt.toConscrypt(engine).getHostname();
    }

    public static int maxSealOverhead(SSLEngine engine) {
        return Conscrypt.toConscrypt(engine).maxSealOverhead();
    }

    public static void setHandshakeListener(SSLEngine engine, HandshakeListener handshakeListener) {
        Conscrypt.toConscrypt(engine).setHandshakeListener(handshakeListener);
    }

    public static void setChannelIdEnabled(SSLEngine engine, boolean enabled) {
        Conscrypt.toConscrypt(engine).setChannelIdEnabled(enabled);
    }

    public static byte[] getChannelId(SSLEngine engine) throws SSLException {
        return Conscrypt.toConscrypt(engine).getChannelId();
    }

    public static void setChannelIdPrivateKey(SSLEngine engine, PrivateKey privateKey) {
        Conscrypt.toConscrypt(engine).setChannelIdPrivateKey(privateKey);
    }

    public static SSLEngineResult unwrap(SSLEngine engine, ByteBuffer[] srcs, ByteBuffer[] dsts) throws SSLException {
        return Conscrypt.toConscrypt(engine).unwrap(srcs, dsts);
    }

    public static SSLEngineResult unwrap(SSLEngine engine, ByteBuffer[] srcs, int srcsOffset, int srcsLength, ByteBuffer[] dsts, int dstsOffset, int dstsLength) throws SSLException {
        return Conscrypt.toConscrypt(engine).unwrap(srcs, srcsOffset, srcsLength, dsts, dstsOffset, dstsLength);
    }

    public static void setUseSessionTickets(SSLEngine engine, boolean useSessionTickets) {
        Conscrypt.toConscrypt(engine).setUseSessionTickets(useSessionTickets);
    }

    public static void setApplicationProtocols(SSLEngine engine, String[] protocols) {
        Conscrypt.toConscrypt(engine).setApplicationProtocols(protocols);
    }

    public static String[] getApplicationProtocols(SSLEngine engine) {
        return Conscrypt.toConscrypt(engine).getApplicationProtocols();
    }

    public static void setApplicationProtocolSelector(SSLEngine engine, ApplicationProtocolSelector selector) {
        Conscrypt.toConscrypt(engine).setApplicationProtocolSelector(selector);
    }

    public static String getApplicationProtocol(SSLEngine engine) {
        return Conscrypt.toConscrypt(engine).getApplicationProtocol();
    }

    public static byte[] getTlsUnique(SSLEngine engine) {
        return Conscrypt.toConscrypt(engine).getTlsUnique();
    }

    public static byte[] exportKeyingMaterial(SSLEngine engine, String label, byte[] context, int length) throws SSLException {
        return Conscrypt.toConscrypt(engine).exportKeyingMaterial(label, context, length);
    }

    public static boolean isConscrypt(TrustManager trustManager) {
        return trustManager instanceof TrustManagerImpl;
    }

    private static TrustManagerImpl toConscrypt(TrustManager trustManager) {
        if (!Conscrypt.isConscrypt(trustManager)) {
            throw new IllegalArgumentException("Not a Conscrypt trust manager: " + trustManager.getClass().getName());
        }
        return (TrustManagerImpl)trustManager;
    }

    public static synchronized void setDefaultHostnameVerifier(ConscryptHostnameVerifier verifier) {
        TrustManagerImpl.setDefaultHostnameVerifier(verifier);
    }

    public static synchronized ConscryptHostnameVerifier getDefaultHostnameVerifier(TrustManager trustManager) {
        return TrustManagerImpl.getDefaultHostnameVerifier();
    }

    public static void setHostnameVerifier(TrustManager trustManager, ConscryptHostnameVerifier verifier) {
        Conscrypt.toConscrypt(trustManager).setHostnameVerifier(verifier);
    }

    public static ConscryptHostnameVerifier getHostnameVerifier(TrustManager trustManager) {
        return Conscrypt.toConscrypt(trustManager).getHostnameVerifier();
    }

    public static ConscryptHostnameVerifier wrapHostnameVerifier(final HostnameVerifier verifier) {
        return new ConscryptHostnameVerifier(){

            @Override
            public boolean verify(X509Certificate[] certificates, String hostname, SSLSession session) {
                return verifier.verify(hostname, session);
            }
        };
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        int major = -1;
        int minor = -1;
        int patch = -1;
        InputStream stream = null;
        try {
            stream = Conscrypt.class.getResourceAsStream("conscrypt.properties");
            if (stream != null) {
                Properties props = new Properties();
                props.load(stream);
                major = Integer.parseInt(props.getProperty("org.conscrypt.version.major", "-1"));
                minor = Integer.parseInt(props.getProperty("org.conscrypt.version.minor", "-1"));
                patch = Integer.parseInt(props.getProperty("org.conscrypt.version.patch", "-1"));
            }
        }
        catch (IOException iOException) {
        }
        finally {
            IoUtils.closeQuietly(stream);
        }
        VERSION = major >= 0 && minor >= 0 && patch >= 0 ? new Version(major, minor, patch) : null;
    }

    public static class ProviderBuilder {
        private String name = Platform.getDefaultProviderName();
        private boolean provideTrustManager = Platform.provideTrustManagerByDefault();
        private String defaultTlsProtocol = "TLSv1.3";

        private ProviderBuilder() {
        }

        public ProviderBuilder setName(String name) {
            this.name = name;
            return this;
        }

        @Deprecated
        public ProviderBuilder provideTrustManager() {
            return this.provideTrustManager(true);
        }

        public ProviderBuilder provideTrustManager(boolean provide) {
            this.provideTrustManager = provide;
            return this;
        }

        public ProviderBuilder defaultTlsProtocol(String defaultTlsProtocol) {
            this.defaultTlsProtocol = defaultTlsProtocol;
            return this;
        }

        public Provider build() {
            return new OpenSSLProvider(this.name, this.provideTrustManager, this.defaultTlsProtocol);
        }
    }

    public static class Version {
        private final int major;
        private final int minor;
        private final int patch;

        private Version(int major, int minor, int patch) {
            this.major = major;
            this.minor = minor;
            this.patch = patch;
        }

        public int major() {
            return this.major;
        }

        public int minor() {
            return this.minor;
        }

        public int patch() {
            return this.patch;
        }
    }
}

