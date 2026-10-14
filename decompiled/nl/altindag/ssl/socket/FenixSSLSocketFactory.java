/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import nl.altindag.ssl.util.internal.ValidationUtils;

public final class FenixSSLSocketFactory
extends SSLSocketFactory {
    private final SSLSocketFactory sslSocketFactory;
    private final SSLParameters sslParameters;

    public FenixSSLSocketFactory(SSLSocketFactory sslSocketFactory, SSLParameters sslParameters) {
        this.sslSocketFactory = ValidationUtils.requireNotNull(sslSocketFactory, (String)ValidationUtils.GENERIC_EXCEPTION_MESSAGE.apply("SSLSocketFactory"));
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
    public Socket createSocket() throws IOException {
        Socket socket = this.sslSocketFactory.createSocket();
        return this.withSslParameters(socket);
    }

    @Override
    public Socket createSocket(Socket socket, InputStream inputStream2, boolean autoClosable) throws IOException {
        Socket newSocket = this.sslSocketFactory.createSocket(socket, inputStream2, autoClosable);
        return this.withSslParameters(newSocket);
    }

    @Override
    public Socket createSocket(Socket socket, String host2, int port, boolean autoClosable) throws IOException {
        Socket newSocket = this.sslSocketFactory.createSocket(socket, host2, port, autoClosable);
        return this.withSslParameters(newSocket);
    }

    @Override
    public Socket createSocket(String host2, int port) throws IOException, UnknownHostException {
        Socket socket = this.sslSocketFactory.createSocket(host2, port);
        return this.withSslParameters(socket);
    }

    @Override
    public Socket createSocket(String host2, int port, InetAddress localAddress, int localPort) throws IOException, UnknownHostException {
        Socket socket = this.sslSocketFactory.createSocket(host2, port, localAddress, localPort);
        return this.withSslParameters(socket);
    }

    @Override
    public Socket createSocket(InetAddress address, int port) throws IOException {
        Socket socket = this.sslSocketFactory.createSocket(address, port);
        return this.withSslParameters(socket);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        Socket socket = this.sslSocketFactory.createSocket(address, port, localAddress, localPort);
        return this.withSslParameters(socket);
    }

    private Socket withSslParameters(Socket socket) {
        if (socket instanceof SSLSocket) {
            SSLSocket sslSocket = (SSLSocket)socket;
            sslSocket.setSSLParameters(this.sslParameters);
        }
        return socket;
    }
}

