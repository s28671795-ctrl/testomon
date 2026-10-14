/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import nl.altindag.ssl.util.internal.ValidationUtils;

public final class FenixSSLServerSocketFactory
extends SSLServerSocketFactory {
    private final SSLServerSocketFactory sslServerSocketFactory;
    private final SSLParameters sslParameters;

    public FenixSSLServerSocketFactory(SSLServerSocketFactory sslServerSocketFactory, SSLParameters sslParameters) {
        this.sslServerSocketFactory = ValidationUtils.requireNotNull(sslServerSocketFactory, (String)ValidationUtils.GENERIC_EXCEPTION_MESSAGE.apply("SSLServerSocketFactory"));
        this.sslParameters = ValidationUtils.requireNotNull(sslParameters, (String)ValidationUtils.GENERIC_EXCEPTION_MESSAGE.apply("SSLParameters"));
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return this.sslParameters.getCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return this.sslParameters.getCipherSuites();
    }

    @Override
    public ServerSocket createServerSocket() throws IOException {
        ServerSocket serverSocket = this.sslServerSocketFactory.createServerSocket();
        return this.withSslParameters(serverSocket);
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        ServerSocket serverSocket = this.sslServerSocketFactory.createServerSocket(port);
        return this.withSslParameters(serverSocket);
    }

    @Override
    public ServerSocket createServerSocket(int port, int backlog) throws IOException {
        ServerSocket serverSocket = this.sslServerSocketFactory.createServerSocket(port, backlog);
        return this.withSslParameters(serverSocket);
    }

    @Override
    public ServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) throws IOException {
        ServerSocket serverSocket = this.sslServerSocketFactory.createServerSocket(port, backlog, ifAddress);
        return this.withSslParameters(serverSocket);
    }

    private ServerSocket withSslParameters(ServerSocket socket) {
        if (socket instanceof SSLServerSocket) {
            SSLServerSocket sslSocket = (SSLServerSocket)socket;
            sslSocket.setSSLParameters(this.sslParameters);
        }
        return socket;
    }
}

