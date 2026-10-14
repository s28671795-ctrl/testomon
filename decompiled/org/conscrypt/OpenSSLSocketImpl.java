/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.PrivateKey;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import org.conscrypt.AbstractConscryptSocket;
import org.conscrypt.EmptyArray;
import org.conscrypt.SSLUtils;

public abstract class OpenSSLSocketImpl
extends AbstractConscryptSocket {
    OpenSSLSocketImpl() throws IOException {
    }

    OpenSSLSocketImpl(String hostname, int port) throws IOException {
        super(hostname, port);
    }

    OpenSSLSocketImpl(InetAddress address, int port) throws IOException {
        super(address, port);
    }

    OpenSSLSocketImpl(String hostname, int port, InetAddress clientAddress, int clientPort) throws IOException {
        super(hostname, port, clientAddress, clientPort);
    }

    OpenSSLSocketImpl(InetAddress address, int port, InetAddress clientAddress, int clientPort) throws IOException {
        super(address, port, clientAddress, clientPort);
    }

    OpenSSLSocketImpl(Socket socket, String hostname, int port, boolean autoClose) throws IOException {
        super(socket, hostname, port, autoClose);
    }

    @Override
    public String getHostname() {
        return super.getHostname();
    }

    @Override
    public void setHostname(String hostname) {
        super.setHostname(hostname);
    }

    @Override
    public String getHostnameOrIP() {
        return super.getHostnameOrIP();
    }

    @Override
    public FileDescriptor getFileDescriptor$() {
        return super.getFileDescriptor$();
    }

    @Override
    public void setSoWriteTimeout(int writeTimeoutMilliseconds) throws SocketException {
        super.setSoWriteTimeout(writeTimeoutMilliseconds);
    }

    @Override
    public int getSoWriteTimeout() throws SocketException {
        return super.getSoWriteTimeout();
    }

    @Override
    public void setHandshakeTimeout(int handshakeTimeoutMilliseconds) throws SocketException {
        super.setHandshakeTimeout(handshakeTimeoutMilliseconds);
    }

    @Override
    public abstract SSLSession getHandshakeSession();

    @Override
    public abstract void setUseSessionTickets(boolean var1);

    @Override
    public abstract void setChannelIdEnabled(boolean var1);

    @Override
    public abstract byte[] getChannelId() throws SSLException;

    @Override
    public abstract void setChannelIdPrivateKey(PrivateKey var1);

    @Override
    @Deprecated
    public final byte[] getNpnSelectedProtocol() {
        return super.getNpnSelectedProtocol();
    }

    @Override
    @Deprecated
    public final void setNpnProtocols(byte[] npnProtocols) {
        super.setNpnProtocols(npnProtocols);
    }

    @Override
    @Deprecated
    public final void setAlpnProtocols(String[] alpnProtocols) {
        this.setApplicationProtocols(alpnProtocols == null ? EmptyArray.STRING : alpnProtocols);
    }

    @Override
    @Deprecated
    public final byte[] getAlpnSelectedProtocol() {
        return SSLUtils.toProtocolBytes(this.getApplicationProtocol());
    }

    @Override
    @Deprecated
    public final void setAlpnProtocols(byte[] protocols) {
        this.setApplicationProtocols(SSLUtils.decodeProtocols(protocols == null ? EmptyArray.BYTE : protocols));
    }
}

