/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLServerSocket;
import org.conscrypt.AbstractConscryptSocket;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLSocketImpl;
import org.conscrypt.Platform;
import org.conscrypt.SSLParametersImpl;

final class ConscryptServerSocket
extends SSLServerSocket {
    private final SSLParametersImpl sslParameters;
    private boolean channelIdEnabled;
    private boolean useEngineSocket;

    ConscryptServerSocket(SSLParametersImpl sslParameters) throws IOException {
        this.sslParameters = sslParameters;
    }

    ConscryptServerSocket(int port, SSLParametersImpl sslParameters) throws IOException {
        super(port);
        this.sslParameters = sslParameters;
    }

    ConscryptServerSocket(int port, int backlog, SSLParametersImpl sslParameters) throws IOException {
        super(port, backlog);
        this.sslParameters = sslParameters;
    }

    ConscryptServerSocket(int port, int backlog, InetAddress iAddress, SSLParametersImpl sslParameters) throws IOException {
        super(port, backlog, iAddress);
        this.sslParameters = sslParameters;
    }

    ConscryptServerSocket setUseEngineSocket(boolean useEngineSocket) {
        this.useEngineSocket = useEngineSocket;
        return this;
    }

    @Override
    public boolean getEnableSessionCreation() {
        return this.sslParameters.getEnableSessionCreation();
    }

    @Override
    public void setEnableSessionCreation(boolean flag) {
        this.sslParameters.setEnableSessionCreation(flag);
    }

    @Override
    public String[] getSupportedProtocols() {
        return NativeCrypto.getSupportedProtocols();
    }

    @Override
    public String[] getEnabledProtocols() {
        return this.sslParameters.getEnabledProtocols();
    }

    @Override
    public void setEnabledProtocols(String[] protocols) {
        this.sslParameters.setEnabledProtocols(protocols);
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return NativeCrypto.getSupportedCipherSuites();
    }

    @Override
    public String[] getEnabledCipherSuites() {
        return this.sslParameters.getEnabledCipherSuites();
    }

    void setChannelIdEnabled(boolean enabled) {
        this.channelIdEnabled = enabled;
    }

    boolean isChannelIdEnabled() {
        return this.channelIdEnabled;
    }

    @Override
    public void setEnabledCipherSuites(String[] suites) {
        this.sslParameters.setEnabledCipherSuites(suites);
    }

    @Override
    public boolean getWantClientAuth() {
        return this.sslParameters.getWantClientAuth();
    }

    @Override
    public void setWantClientAuth(boolean want) {
        this.sslParameters.setWantClientAuth(want);
    }

    @Override
    public boolean getNeedClientAuth() {
        return this.sslParameters.getNeedClientAuth();
    }

    @Override
    public void setNeedClientAuth(boolean need) {
        this.sslParameters.setNeedClientAuth(need);
    }

    @Override
    public void setUseClientMode(boolean mode) {
        this.sslParameters.setUseClientMode(mode);
    }

    @Override
    public boolean getUseClientMode() {
        return this.sslParameters.getUseClientMode();
    }

    @Override
    public Socket accept() throws IOException {
        OpenSSLSocketImpl socket = this.useEngineSocket ? Platform.createEngineSocket(this.sslParameters) : Platform.createFileDescriptorSocket(this.sslParameters);
        ((AbstractConscryptSocket)socket).setChannelIdEnabled(this.channelIdEnabled);
        this.implAccept(socket);
        return socket;
    }
}

