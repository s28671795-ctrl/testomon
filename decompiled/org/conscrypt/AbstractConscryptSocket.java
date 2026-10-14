/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.conscrypt.ApplicationProtocolSelector;
import org.conscrypt.ApplicationProtocolSelectorAdapter;
import org.conscrypt.PeerInfoProvider;
import org.conscrypt.Platform;
import org.conscrypt.Preconditions;

abstract class AbstractConscryptSocket
extends SSLSocket {
    final Socket socket;
    private final boolean autoClose;
    private String peerHostname;
    private final int peerPort;
    private final PeerInfoProvider peerInfoProvider = new PeerInfoProvider(){

        @Override
        String getHostname() {
            return AbstractConscryptSocket.this.getHostname();
        }

        @Override
        String getHostnameOrIP() {
            return AbstractConscryptSocket.this.getHostnameOrIP();
        }

        @Override
        int getPort() {
            return AbstractConscryptSocket.this.getPort();
        }
    };
    private final List<HandshakeCompletedListener> listeners = new ArrayList<HandshakeCompletedListener>(2);
    private int readTimeoutMilliseconds;

    AbstractConscryptSocket() throws IOException {
        this.socket = this;
        this.peerHostname = null;
        this.peerPort = -1;
        this.autoClose = false;
    }

    AbstractConscryptSocket(String hostname, int port) throws IOException {
        super(hostname, port);
        this.socket = this;
        this.peerHostname = hostname;
        this.peerPort = port;
        this.autoClose = false;
    }

    AbstractConscryptSocket(InetAddress address, int port) throws IOException {
        super(address, port);
        this.socket = this;
        this.peerHostname = null;
        this.peerPort = -1;
        this.autoClose = false;
    }

    AbstractConscryptSocket(String hostname, int port, InetAddress clientAddress, int clientPort) throws IOException {
        super(hostname, port, clientAddress, clientPort);
        this.socket = this;
        this.peerHostname = hostname;
        this.peerPort = port;
        this.autoClose = false;
    }

    AbstractConscryptSocket(InetAddress address, int port, InetAddress clientAddress, int clientPort) throws IOException {
        super(address, port, clientAddress, clientPort);
        this.socket = this;
        this.peerHostname = null;
        this.peerPort = -1;
        this.autoClose = false;
    }

    AbstractConscryptSocket(Socket socket, String hostname, int port, boolean autoClose) throws IOException {
        this.socket = Preconditions.checkNotNull(socket, "socket");
        this.peerHostname = hostname;
        this.peerPort = port;
        this.autoClose = autoClose;
    }

    @Override
    public final void connect(SocketAddress endpoint) throws IOException {
        this.connect(endpoint, 0);
    }

    @Override
    public final void connect(SocketAddress endpoint, int timeout2) throws IOException {
        if (this.peerHostname == null && endpoint instanceof InetSocketAddress) {
            this.peerHostname = Platform.getHostStringFromInetSocketAddress((InetSocketAddress)endpoint);
        }
        if (this.isDelegating()) {
            this.socket.connect(endpoint, timeout2);
        } else {
            super.connect(endpoint, timeout2);
        }
    }

    @Override
    public void bind(SocketAddress bindpoint) throws IOException {
        if (this.isDelegating()) {
            this.socket.bind(bindpoint);
        } else {
            super.bind(bindpoint);
        }
    }

    @Override
    public void close() throws IOException {
        if (this.isDelegating()) {
            if (this.autoClose && !this.socket.isClosed()) {
                this.socket.close();
            }
        } else if (!super.isClosed()) {
            super.close();
        }
    }

    @Override
    public InetAddress getInetAddress() {
        if (this.isDelegating()) {
            return this.socket.getInetAddress();
        }
        return super.getInetAddress();
    }

    @Override
    public InetAddress getLocalAddress() {
        if (this.isDelegating()) {
            return this.socket.getLocalAddress();
        }
        return super.getLocalAddress();
    }

    @Override
    public int getLocalPort() {
        if (this.isDelegating()) {
            return this.socket.getLocalPort();
        }
        return super.getLocalPort();
    }

    @Override
    public SocketAddress getRemoteSocketAddress() {
        if (this.isDelegating()) {
            return this.socket.getRemoteSocketAddress();
        }
        return super.getRemoteSocketAddress();
    }

    @Override
    public SocketAddress getLocalSocketAddress() {
        if (this.isDelegating()) {
            return this.socket.getLocalSocketAddress();
        }
        return super.getLocalSocketAddress();
    }

    @Override
    public final int getPort() {
        if (this.isDelegating()) {
            return this.socket.getPort();
        }
        if (this.peerPort != -1) {
            return this.peerPort;
        }
        return super.getPort();
    }

    @Override
    public void addHandshakeCompletedListener(HandshakeCompletedListener listener) {
        Preconditions.checkArgument(listener != null, "Provided listener is null");
        this.listeners.add(listener);
    }

    @Override
    public void removeHandshakeCompletedListener(HandshakeCompletedListener listener) {
        Preconditions.checkArgument(listener != null, "Provided listener is null");
        if (!this.listeners.remove(listener)) {
            throw new IllegalArgumentException("Provided listener is not registered");
        }
    }

    public FileDescriptor getFileDescriptor$() {
        if (this.isDelegating()) {
            return Platform.getFileDescriptor(this.socket);
        }
        return Platform.getFileDescriptorFromSSLSocket(this);
    }

    @Override
    public final void setSoTimeout(int readTimeoutMilliseconds) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setSoTimeout(readTimeoutMilliseconds);
        } else {
            super.setSoTimeout(readTimeoutMilliseconds);
            this.readTimeoutMilliseconds = readTimeoutMilliseconds;
        }
    }

    @Override
    public final int getSoTimeout() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getSoTimeout();
        }
        return this.readTimeoutMilliseconds;
    }

    @Override
    public final void sendUrgentData(int data) throws IOException {
        throw new SocketException("Method sendUrgentData() is not supported.");
    }

    @Override
    public final void setOOBInline(boolean on) throws SocketException {
        throw new SocketException("Method setOOBInline() is not supported.");
    }

    @Override
    public boolean getOOBInline() throws SocketException {
        return false;
    }

    @Override
    public SocketChannel getChannel() {
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (this.isDelegating()) {
            return this.socket.getInputStream();
        }
        return super.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        if (this.isDelegating()) {
            return this.socket.getOutputStream();
        }
        return super.getOutputStream();
    }

    @Override
    public void setTcpNoDelay(boolean on) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setTcpNoDelay(on);
        } else {
            super.setTcpNoDelay(on);
        }
    }

    @Override
    public boolean getTcpNoDelay() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getTcpNoDelay();
        }
        return super.getTcpNoDelay();
    }

    @Override
    public void setSoLinger(boolean on, int linger) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setSoLinger(on, linger);
        } else {
            super.setSoLinger(on, linger);
        }
    }

    @Override
    public int getSoLinger() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getSoLinger();
        }
        return super.getSoLinger();
    }

    @Override
    public void setSendBufferSize(int size) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setSendBufferSize(size);
        } else {
            super.setSendBufferSize(size);
        }
    }

    @Override
    public int getSendBufferSize() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getSendBufferSize();
        }
        return super.getSendBufferSize();
    }

    @Override
    public void setReceiveBufferSize(int size) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setReceiveBufferSize(size);
        } else {
            super.setReceiveBufferSize(size);
        }
    }

    @Override
    public int getReceiveBufferSize() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getReceiveBufferSize();
        }
        return super.getReceiveBufferSize();
    }

    @Override
    public void setKeepAlive(boolean on) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setKeepAlive(on);
        } else {
            super.setKeepAlive(on);
        }
    }

    @Override
    public boolean getKeepAlive() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getKeepAlive();
        }
        return super.getKeepAlive();
    }

    @Override
    public void setTrafficClass(int tc) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setTrafficClass(tc);
        } else {
            super.setTrafficClass(tc);
        }
    }

    @Override
    public int getTrafficClass() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getTrafficClass();
        }
        return super.getTrafficClass();
    }

    @Override
    public void setReuseAddress(boolean on) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setReuseAddress(on);
        } else {
            super.setReuseAddress(on);
        }
    }

    @Override
    public boolean getReuseAddress() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getReuseAddress();
        }
        return super.getReuseAddress();
    }

    @Override
    public void shutdownInput() throws IOException {
        if (this.isDelegating()) {
            this.socket.shutdownInput();
        } else {
            super.shutdownInput();
        }
    }

    @Override
    public void shutdownOutput() throws IOException {
        if (this.isDelegating()) {
            this.socket.shutdownOutput();
        } else {
            super.shutdownOutput();
        }
    }

    @Override
    public boolean isConnected() {
        if (this.isDelegating()) {
            return this.socket.isConnected();
        }
        return super.isConnected();
    }

    @Override
    public boolean isBound() {
        if (this.isDelegating()) {
            return this.socket.isBound();
        }
        return super.isBound();
    }

    @Override
    public boolean isClosed() {
        if (this.isDelegating()) {
            return this.socket.isClosed();
        }
        return super.isClosed();
    }

    @Override
    public boolean isInputShutdown() {
        if (this.isDelegating()) {
            return this.socket.isInputShutdown();
        }
        return super.isInputShutdown();
    }

    @Override
    public boolean isOutputShutdown() {
        if (this.isDelegating()) {
            return this.socket.isOutputShutdown();
        }
        return super.isOutputShutdown();
    }

    @Override
    public void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
        if (this.isDelegating()) {
            this.socket.setPerformancePreferences(connectionTime, latency, bandwidth);
        } else {
            super.setPerformancePreferences(connectionTime, latency, bandwidth);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("SSL socket over ");
        if (this.isDelegating()) {
            builder.append(this.socket.toString());
        } else {
            builder.append(super.toString());
        }
        return builder.toString();
    }

    String getHostname() {
        return this.peerHostname;
    }

    void setHostname(String hostname) {
        this.peerHostname = hostname;
    }

    String getHostnameOrIP() {
        if (this.peerHostname != null) {
            return this.peerHostname;
        }
        InetAddress peerAddress = this.getInetAddress();
        if (peerAddress != null) {
            return Platform.getOriginalHostNameFromInetAddress(peerAddress);
        }
        return null;
    }

    void setSoWriteTimeout(int writeTimeoutMilliseconds) throws SocketException {
        throw new SocketException("Method setSoWriteTimeout() is not supported.");
    }

    int getSoWriteTimeout() throws SocketException {
        return 0;
    }

    void setHandshakeTimeout(int handshakeTimeoutMilliseconds) throws SocketException {
        throw new SocketException("Method setHandshakeTimeout() is not supported.");
    }

    final void checkOpen() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
    }

    final PeerInfoProvider peerInfoProvider() {
        return this.peerInfoProvider;
    }

    abstract SSLSession getActiveSession();

    abstract void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter var1);

    final void notifyHandshakeCompletedListeners() {
        ArrayList<HandshakeCompletedListener> listenersCopy = new ArrayList<HandshakeCompletedListener>(this.listeners);
        if (!listenersCopy.isEmpty()) {
            HandshakeCompletedEvent event = new HandshakeCompletedEvent(this, this.getActiveSession());
            for (HandshakeCompletedListener listener : listenersCopy) {
                try {
                    listener.handshakeCompleted(event);
                }
                catch (RuntimeException e) {
                    Thread thread2 = Thread.currentThread();
                    thread2.getUncaughtExceptionHandler().uncaughtException(thread2, e);
                }
            }
        }
    }

    private boolean isDelegating() {
        return this.socket != null && this.socket != this;
    }

    @Override
    public abstract SSLSession getHandshakeSession();

    abstract void setUseSessionTickets(boolean var1);

    abstract void setChannelIdEnabled(boolean var1);

    abstract byte[] getChannelId() throws SSLException;

    abstract void setChannelIdPrivateKey(PrivateKey var1);

    @Deprecated
    byte[] getNpnSelectedProtocol() {
        return null;
    }

    @Deprecated
    void setNpnProtocols(byte[] npnProtocols) {
    }

    @Deprecated
    abstract byte[] getAlpnSelectedProtocol();

    @Deprecated
    abstract void setAlpnProtocols(String[] var1);

    @Deprecated
    abstract void setAlpnProtocols(byte[] var1);

    abstract void setApplicationProtocols(String[] var1);

    abstract String[] getApplicationProtocols();

    @Override
    public abstract String getApplicationProtocol();

    @Override
    public abstract String getHandshakeApplicationProtocol();

    abstract void setApplicationProtocolSelector(ApplicationProtocolSelector var1);

    abstract byte[] getTlsUnique();

    abstract byte[] exportKeyingMaterial(String var1, byte[] var2, int var3) throws SSLException;
}

