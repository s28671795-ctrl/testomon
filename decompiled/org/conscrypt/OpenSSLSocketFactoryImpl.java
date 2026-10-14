/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.conscrypt.NativeCrypto;
import org.conscrypt.Platform;
import org.conscrypt.Preconditions;
import org.conscrypt.SSLParametersImpl;
import org.conscrypt.SSLUtils;

final class OpenSSLSocketFactoryImpl
extends SSLSocketFactory {
    private static boolean useEngineSocketByDefault = SSLUtils.USE_ENGINE_SOCKET_BY_DEFAULT;
    private final SSLParametersImpl sslParameters;
    private final IOException instantiationException;
    private boolean useEngineSocket = useEngineSocketByDefault;

    OpenSSLSocketFactoryImpl() {
        SSLParametersImpl sslParametersLocal = null;
        IOException instantiationExceptionLocal = null;
        try {
            sslParametersLocal = SSLParametersImpl.getDefault();
        }
        catch (KeyManagementException e) {
            instantiationExceptionLocal = new IOException("Delayed instantiation exception:", e);
        }
        this.sslParameters = sslParametersLocal;
        this.instantiationException = instantiationExceptionLocal;
    }

    OpenSSLSocketFactoryImpl(SSLParametersImpl sslParameters) {
        this.sslParameters = sslParameters;
        this.instantiationException = null;
    }

    static void setUseEngineSocketByDefault(boolean useEngineSocket) {
        useEngineSocketByDefault = useEngineSocket;
        SocketFactory defaultFactory = SSLSocketFactory.getDefault();
        if (defaultFactory instanceof OpenSSLSocketFactoryImpl) {
            ((OpenSSLSocketFactoryImpl)defaultFactory).setUseEngineSocket(useEngineSocket);
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
    public Socket createSocket() throws IOException {
        if (this.instantiationException != null) {
            throw this.instantiationException;
        }
        if (this.useEngineSocket) {
            return Platform.createEngineSocket((SSLParametersImpl)this.sslParameters.clone());
        }
        return Platform.createFileDescriptorSocket((SSLParametersImpl)this.sslParameters.clone());
    }

    @Override
    public Socket createSocket(String hostname, int port) throws IOException, UnknownHostException {
        if (this.useEngineSocket) {
            return Platform.createEngineSocket(hostname, port, (SSLParametersImpl)this.sslParameters.clone());
        }
        return Platform.createFileDescriptorSocket(hostname, port, (SSLParametersImpl)this.sslParameters.clone());
    }

    @Override
    public Socket createSocket(String hostname, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        if (this.useEngineSocket) {
            return Platform.createEngineSocket(hostname, port, localHost, localPort, (SSLParametersImpl)this.sslParameters.clone());
        }
        return Platform.createFileDescriptorSocket(hostname, port, localHost, localPort, (SSLParametersImpl)this.sslParameters.clone());
    }

    @Override
    public Socket createSocket(InetAddress address, int port) throws IOException {
        if (this.useEngineSocket) {
            return Platform.createEngineSocket(address, port, (SSLParametersImpl)this.sslParameters.clone());
        }
        return Platform.createFileDescriptorSocket(address, port, (SSLParametersImpl)this.sslParameters.clone());
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        if (this.useEngineSocket) {
            return Platform.createEngineSocket(address, port, localAddress, localPort, (SSLParametersImpl)this.sslParameters.clone());
        }
        return Platform.createFileDescriptorSocket(address, port, localAddress, localPort, (SSLParametersImpl)this.sslParameters.clone());
    }

    @Override
    public Socket createSocket(Socket socket, String hostname, int port, boolean autoClose) throws IOException {
        Preconditions.checkNotNull(socket, "socket");
        if (!socket.isConnected()) {
            throw new SocketException("Socket is not connected.");
        }
        if (!this.useEngineSocket && this.hasFileDescriptor(socket)) {
            return Platform.createFileDescriptorSocket(socket, hostname, port, autoClose, (SSLParametersImpl)this.sslParameters.clone());
        }
        return Platform.createEngineSocket(socket, hostname, port, autoClose, (SSLParametersImpl)this.sslParameters.clone());
    }

    private boolean hasFileDescriptor(Socket s2) {
        try {
            Platform.getFileDescriptor(s2);
            return true;
        }
        catch (RuntimeException re) {
            return false;
        }
    }
}

