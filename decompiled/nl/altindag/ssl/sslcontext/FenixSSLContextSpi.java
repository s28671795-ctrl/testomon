/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.sslcontext;

import java.security.SecureRandom;
import java.util.Objects;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLContextSpi;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import nl.altindag.ssl.util.SSLParametersUtils;
import nl.altindag.ssl.util.SSLSocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FenixSSLContextSpi
extends SSLContextSpi {
    private static final Logger LOGGER = LoggerFactory.getLogger(FenixSSLContextSpi.class);
    private final SSLContext sslContext;
    private final SSLParameters sslParameters;

    FenixSSLContextSpi(SSLContext sslContext, SSLParameters sslParameters) {
        this.sslContext = sslContext;
        this.sslParameters = sslParameters;
    }

    @Override
    protected void engineInit(KeyManager[] km4, TrustManager[] tm, SecureRandom sr) {
        LOGGER.debug("The provided parameters are being ignored as the SSLContext has already been initialized");
    }

    @Override
    protected SSLSocketFactory engineGetSocketFactory() {
        return SSLSocketUtils.createSslSocketFactory(this.sslContext, this.engineGetDefaultSSLParameters());
    }

    @Override
    protected SSLServerSocketFactory engineGetServerSocketFactory() {
        return SSLSocketUtils.createSslServerSocketFactory(this.sslContext, this.engineGetDefaultSSLParameters());
    }

    @Override
    protected SSLEngine engineCreateSSLEngine() {
        return this.getSSLEngine(null, 0);
    }

    @Override
    protected SSLEngine engineCreateSSLEngine(String host2, int port) {
        return this.getSSLEngine(host2, port);
    }

    private SSLEngine getSSLEngine(String peerHost, int peerPort) {
        SSLEngine sslEngine = Objects.nonNull(peerHost) ? this.sslContext.createSSLEngine(peerHost, peerPort) : this.sslContext.createSSLEngine();
        sslEngine.setSSLParameters(this.engineGetDefaultSSLParameters());
        return sslEngine;
    }

    @Override
    protected SSLSessionContext engineGetServerSessionContext() {
        return this.sslContext.getServerSessionContext();
    }

    @Override
    protected SSLSessionContext engineGetClientSessionContext() {
        return this.sslContext.getClientSessionContext();
    }

    @Override
    protected SSLParameters engineGetDefaultSSLParameters() {
        return SSLParametersUtils.copy(this.sslParameters);
    }

    @Override
    protected SSLParameters engineGetSupportedSSLParameters() {
        return SSLParametersUtils.copy(this.sslContext.getSupportedSSLParameters());
    }
}

