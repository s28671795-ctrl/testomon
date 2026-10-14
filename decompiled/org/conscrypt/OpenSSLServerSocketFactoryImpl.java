/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.security.KeyManagementException;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import org.conscrypt.ConscryptServerSocket;
import org.conscrypt.NativeCrypto;
import org.conscrypt.SSLParametersImpl;
import org.conscrypt.SSLUtils;

final class OpenSSLServerSocketFactoryImpl
extends SSLServerSocketFactory {
    private static boolean useEngineSocketByDefault = SSLUtils.USE_ENGINE_SOCKET_BY_DEFAULT;
    private SSLParametersImpl sslParameters;
    private IOException instantiationException;
    private boolean useEngineSocket = useEngineSocketByDefault;

    OpenSSLServerSocketFactoryImpl() {
        try {
            this.sslParameters = SSLParametersImpl.getDefault();
            this.sslParameters.setUseClientMode(false);
        }
        catch (KeyManagementException e) {
            this.instantiationException = new IOException("Delayed instantiation exception:");
            this.instantiationException.initCause(e);
        }
    }

    OpenSSLServerSocketFactoryImpl(SSLParametersImpl sslParameters) {
        this.sslParameters = (SSLParametersImpl)sslParameters.clone();
        this.sslParameters.setUseClientMode(false);
    }

    static void setUseEngineSocketByDefault(boolean useEngineSocket) {
        useEngineSocketByDefault = useEngineSocket;
        ServerSocketFactory defaultFactory = SSLServerSocketFactory.getDefault();
        if (defaultFactory instanceof OpenSSLServerSocketFactoryImpl) {
            ((OpenSSLServerSocketFactoryImpl)defaultFactory).setUseEngineSocket(useEngineSocket);
        }
    }

    void setUseEngineSocket(boolean useEngineSocket) {
        this.useEngineSocket = useEngineSocket;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return this.sslParameters.getEnabledCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return NativeCrypto.getSupportedCipherSuites();
    }

    @Override
    public ServerSocket createServerSocket() throws IOException {
        return new ConscryptServerSocket((SSLParametersImpl)this.sslParameters.clone()).setUseEngineSocket(this.useEngineSocket);
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        return new ConscryptServerSocket(port, (SSLParametersImpl)this.sslParameters.clone()).setUseEngineSocket(this.useEngineSocket);
    }

    @Override
    public ServerSocket createServerSocket(int port, int backlog) throws IOException {
        return new ConscryptServerSocket(port, backlog, (SSLParametersImpl)this.sslParameters.clone()).setUseEngineSocket(this.useEngineSocket);
    }

    @Override
    public ServerSocket createServerSocket(int port, int backlog, InetAddress iAddress) throws IOException {
        return new ConscryptServerSocket(port, backlog, iAddress, (SSLParametersImpl)this.sslParameters.clone()).setUseEngineSocket(this.useEngineSocket);
    }
}

