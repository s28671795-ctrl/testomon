/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.jetty.util;

import javax.net.ssl.SSLParameters;
import nl.altindag.ssl.SSLFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public final class JettySslUtils {
    private JettySslUtils() {
    }

    public static SslContextFactory.Client forClient(SSLFactory sslFactory) {
        SslContextFactory.Client sslContextFactory = JettySslUtils.createSslContextFactory(sslFactory, new SslContextFactory.Client());
        sslContextFactory.setHostnameVerifier(sslFactory.getHostnameVerifier());
        return sslContextFactory;
    }

    public static SslContextFactory.Server forServer(SSLFactory sslFactory) {
        SslContextFactory.Server sslContextFactory = JettySslUtils.createSslContextFactory(sslFactory, new SslContextFactory.Server());
        SSLParameters sslParameters = sslFactory.getSslParameters();
        if (sslParameters.getNeedClientAuth()) {
            sslContextFactory.setNeedClientAuth(true);
        }
        if (sslParameters.getWantClientAuth()) {
            sslContextFactory.setWantClientAuth(true);
        }
        return sslContextFactory;
    }

    private static <T extends SslContextFactory> T createSslContextFactory(SSLFactory sslFactory, T sslContextFactory) {
        sslContextFactory.setSslContext(sslFactory.getSslContext());
        SSLParameters sslParameters = sslFactory.getSslParameters();
        sslContextFactory.setIncludeProtocols(sslParameters.getProtocols());
        sslContextFactory.setIncludeCipherSuites(sslParameters.getCipherSuites());
        return sslContextFactory;
    }
}

