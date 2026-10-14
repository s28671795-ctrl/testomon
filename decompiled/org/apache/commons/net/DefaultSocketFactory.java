/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;

public class DefaultSocketFactory
extends SocketFactory {
    private final Proxy connProxy;

    public DefaultSocketFactory() {
        this(null);
    }

    public DefaultSocketFactory(Proxy proxy) {
        this.connProxy = proxy;
    }

    @Override
    public Socket createSocket() throws IOException {
        if (this.connProxy != null) {
            return new Socket(this.connProxy);
        }
        return new Socket();
    }

    @Override
    public Socket createSocket(String host2, int port) throws UnknownHostException, IOException {
        if (this.connProxy != null) {
            Socket s2 = new Socket(this.connProxy);
            s2.connect(new InetSocketAddress(host2, port));
            return s2;
        }
        return new Socket(host2, port);
    }

    @Override
    public Socket createSocket(InetAddress address, int port) throws IOException {
        if (this.connProxy != null) {
            Socket s2 = new Socket(this.connProxy);
            s2.connect(new InetSocketAddress(address, port));
            return s2;
        }
        return new Socket(address, port);
    }

    @Override
    public Socket createSocket(String host2, int port, InetAddress localAddr, int localPort) throws UnknownHostException, IOException {
        if (this.connProxy != null) {
            Socket s2 = new Socket(this.connProxy);
            s2.bind(new InetSocketAddress(localAddr, localPort));
            s2.connect(new InetSocketAddress(host2, port));
            return s2;
        }
        return new Socket(host2, port, localAddr, localPort);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddr, int localPort) throws IOException {
        if (this.connProxy != null) {
            Socket s2 = new Socket(this.connProxy);
            s2.bind(new InetSocketAddress(localAddr, localPort));
            s2.connect(new InetSocketAddress(address, port));
            return s2;
        }
        return new Socket(address, port, localAddr, localPort);
    }

    public ServerSocket createServerSocket(int port) throws IOException {
        return new ServerSocket(port);
    }

    public ServerSocket createServerSocket(int port, int backlog) throws IOException {
        return new ServerSocket(port, backlog);
    }

    public ServerSocket createServerSocket(int port, int backlog, InetAddress bindAddr) throws IOException {
        return new ServerSocket(port, backlog, bindAddr);
    }
}

