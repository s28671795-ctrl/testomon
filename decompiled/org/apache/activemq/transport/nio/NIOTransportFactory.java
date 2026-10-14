/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.nio.NIOTransport;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.transport.tcp.TcpTransportFactory;
import org.apache.activemq.transport.tcp.TcpTransportServer;
import org.apache.activemq.wireformat.WireFormat;

public class NIOTransportFactory
extends TcpTransportFactory {
    @Override
    protected TcpTransportServer createTcpTransportServer(URI location, ServerSocketFactory serverSocketFactory) throws IOException, URISyntaxException {
        return new TcpTransportServer(this, location, serverSocketFactory){

            @Override
            protected Transport createTransport(Socket socket, WireFormat format) throws IOException {
                return new NIOTransport(format, socket);
            }
        };
    }

    @Override
    protected TcpTransport createTcpTransport(WireFormat wf, SocketFactory socketFactory, URI location, URI localLocation) throws UnknownHostException, IOException {
        return new NIOTransport(wf, socketFactory, location, localLocation);
    }

    @Override
    public TcpTransport createTransport(WireFormat wireFormat, Socket socket, TcpTransport.InitBuffer initBuffer) throws IOException {
        return new NIOTransport(wireFormat, socket, initBuffer);
    }

    @Override
    protected ServerSocketFactory createServerSocketFactory() {
        return new ServerSocketFactory(){

            @Override
            public ServerSocket createServerSocket(int port) throws IOException {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(port));
                return serverSocketChannel.socket();
            }

            @Override
            public ServerSocket createServerSocket(int port, int backlog) throws IOException {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(port), backlog);
                return serverSocketChannel.socket();
            }

            @Override
            public ServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) throws IOException {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(ifAddress, port), backlog);
                return serverSocketChannel.socket();
            }
        };
    }

    @Override
    protected SocketFactory createSocketFactory() throws IOException {
        return new SocketFactory(){

            @Override
            public Socket createSocket() throws IOException {
                SocketChannel channel = SocketChannel.open();
                return channel.socket();
            }

            @Override
            public Socket createSocket(String host2, int port) throws IOException, UnknownHostException {
                SocketChannel channel = SocketChannel.open();
                channel.connect(new InetSocketAddress(host2, port));
                return channel.socket();
            }

            @Override
            public Socket createSocket(InetAddress address, int port) throws IOException {
                SocketChannel channel = SocketChannel.open();
                channel.connect(new InetSocketAddress(address, port));
                return channel.socket();
            }

            @Override
            public Socket createSocket(String address, int port, InetAddress localAddresss, int localPort) throws IOException, UnknownHostException {
                SocketChannel channel = SocketChannel.open();
                channel.socket().bind(new InetSocketAddress(localAddresss, localPort));
                channel.connect(new InetSocketAddress(address, port));
                return channel.socket();
            }

            @Override
            public Socket createSocket(InetAddress address, int port, InetAddress localAddresss, int localPort) throws IOException {
                SocketChannel channel = SocketChannel.open();
                channel.socket().bind(new InetSocketAddress(localAddresss, localPort));
                channel.connect(new InetSocketAddress(address, port));
                return channel.socket();
            }
        };
    }
}

