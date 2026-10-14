/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import nl.altindag.ssl.SSLFactory;
import nl.altindag.ssl.socket.FenixSSLServerSocketFactory;
import nl.altindag.ssl.socket.FenixSSLSocketFactory;

public final class SSLSocketUtils {
    private SSLSocketUtils() {
    }

    public static SSLSocketFactory createSslSocketFactory(SSLContext sslContext, SSLParameters sslParameters) {
        return new FenixSSLSocketFactory(sslContext.getSocketFactory(), sslParameters);
    }

    public static SSLSocketFactory createSslSocketFactory(SSLSocketFactory sslSocketFactory, SSLParameters sslParameters) {
        return new FenixSSLSocketFactory(sslSocketFactory, sslParameters);
    }

    public static SSLSocketFactory createUnsafeSslSocketFactory() {
        return SSLFactory.builder().withUnsafeTrustMaterial().build().getSslSocketFactory();
    }

    public static SSLServerSocketFactory createSslServerSocketFactory(SSLContext sslContext, SSLParameters sslParameters) {
        return new FenixSSLServerSocketFactory(sslContext.getServerSocketFactory(), sslParameters);
    }

    public static SSLServerSocketFactory createSslServerSocketFactory(SSLServerSocketFactory sslServerSocketFactory, SSLParameters sslParameters) {
        return new FenixSSLServerSocketFactory(sslServerSocketFactory, sslParameters);
    }
}

