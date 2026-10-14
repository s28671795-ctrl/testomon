/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.conscrypt.AbstractConscryptEngine;
import org.conscrypt.ApplicationProtocolSelector;
import org.conscrypt.ApplicationProtocolSelectorAdapter;
import org.conscrypt.BufferAllocator;
import org.conscrypt.ConscryptEngine;
import org.conscrypt.HandshakeListener;
import org.conscrypt.Preconditions;

final class Java8EngineWrapper
extends AbstractConscryptEngine {
    private final ConscryptEngine delegate;
    private BiFunction<SSLEngine, List<String>, String> selector;

    Java8EngineWrapper(ConscryptEngine delegate) {
        this.delegate = Preconditions.checkNotNull(delegate, "delegate");
    }

    static SSLEngine getDelegate(SSLEngine engine) {
        if (engine instanceof Java8EngineWrapper) {
            return ((Java8EngineWrapper)engine).delegate;
        }
        return engine;
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer[] byteBuffers, ByteBuffer byteBuffer) throws SSLException {
        return this.delegate.wrap(byteBuffers, byteBuffer);
    }

    @Override
    public SSLParameters getSSLParameters() {
        return this.delegate.getSSLParameters();
    }

    @Override
    public void setSSLParameters(SSLParameters sslParameters) {
        this.delegate.setSSLParameters(sslParameters);
    }

    @Override
    void setBufferAllocator(BufferAllocator bufferAllocator) {
        this.delegate.setBufferAllocator(bufferAllocator);
    }

    @Override
    int maxSealOverhead() {
        return this.delegate.maxSealOverhead();
    }

    @Override
    void setChannelIdEnabled(boolean enabled) {
        this.delegate.setChannelIdEnabled(enabled);
    }

    @Override
    byte[] getChannelId() throws SSLException {
        return this.delegate.getChannelId();
    }

    @Override
    void setChannelIdPrivateKey(PrivateKey privateKey) {
        this.delegate.setChannelIdPrivateKey(privateKey);
    }

    @Override
    void setHandshakeListener(HandshakeListener handshakeListener) {
        this.delegate.setHandshakeListener(handshakeListener);
    }

    @Override
    void setHostname(String hostname) {
        this.delegate.setHostname(hostname);
    }

    @Override
    String getHostname() {
        return this.delegate.getHostname();
    }

    @Override
    public String getPeerHost() {
        return this.delegate.getPeerHost();
    }

    @Override
    public int getPeerPort() {
        return this.delegate.getPeerPort();
    }

    @Override
    public void beginHandshake() throws SSLException {
        this.delegate.beginHandshake();
    }

    @Override
    public void closeInbound() throws SSLException {
        this.delegate.closeInbound();
    }

    @Override
    public void closeOutbound() {
        this.delegate.closeOutbound();
    }

    @Override
    public Runnable getDelegatedTask() {
        return this.delegate.getDelegatedTask();
    }

    @Override
    public String[] getEnabledCipherSuites() {
        return this.delegate.getEnabledCipherSuites();
    }

    @Override
    public String[] getEnabledProtocols() {
        return this.delegate.getEnabledProtocols();
    }

    @Override
    public boolean getEnableSessionCreation() {
        return this.delegate.getEnableSessionCreation();
    }

    @Override
    public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        return this.delegate.getHandshakeStatus();
    }

    @Override
    public boolean getNeedClientAuth() {
        return this.delegate.getNeedClientAuth();
    }

    @Override
    SSLSession handshakeSession() {
        return this.delegate.handshakeSession();
    }

    @Override
    public SSLSession getSession() {
        return this.delegate.getSession();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return this.delegate.getSupportedCipherSuites();
    }

    @Override
    public String[] getSupportedProtocols() {
        return this.delegate.getSupportedProtocols();
    }

    @Override
    public boolean getUseClientMode() {
        return this.delegate.getUseClientMode();
    }

    @Override
    public boolean getWantClientAuth() {
        return this.delegate.getWantClientAuth();
    }

    @Override
    public boolean isInboundDone() {
        return this.delegate.isInboundDone();
    }

    @Override
    public boolean isOutboundDone() {
        return this.delegate.isOutboundDone();
    }

    @Override
    public void setEnabledCipherSuites(String[] suites) {
        this.delegate.setEnabledCipherSuites(suites);
    }

    @Override
    public void setEnabledProtocols(String[] protocols) {
        this.delegate.setEnabledProtocols(protocols);
    }

    @Override
    public void setEnableSessionCreation(boolean flag) {
        this.delegate.setEnableSessionCreation(flag);
    }

    @Override
    public void setNeedClientAuth(boolean need) {
        this.delegate.setNeedClientAuth(need);
    }

    @Override
    public void setUseClientMode(boolean mode) {
        this.delegate.setUseClientMode(mode);
    }

    @Override
    public void setWantClientAuth(boolean want) {
        this.delegate.setWantClientAuth(want);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
        return this.delegate.unwrap(src, dst);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts) throws SSLException {
        return this.delegate.unwrap(src, dsts);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts, int offset, int length) throws SSLException {
        return this.delegate.unwrap(src, dsts, offset, length);
    }

    @Override
    SSLEngineResult unwrap(ByteBuffer[] srcs, ByteBuffer[] dsts) throws SSLException {
        return this.delegate.unwrap(srcs, dsts);
    }

    @Override
    SSLEngineResult unwrap(ByteBuffer[] srcs, int srcsOffset, int srcsLength, ByteBuffer[] dsts, int dstsOffset, int dstsLength) throws SSLException {
        return this.delegate.unwrap(srcs, srcsOffset, srcsLength, dsts, dstsOffset, dstsLength);
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
        return this.delegate.wrap(src, dst);
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer[] srcs, int srcsOffset, int srcsLength, ByteBuffer dst) throws SSLException {
        return this.delegate.wrap(srcs, srcsOffset, srcsLength, dst);
    }

    @Override
    void setUseSessionTickets(boolean useSessionTickets) {
        this.delegate.setUseSessionTickets(useSessionTickets);
    }

    @Override
    void setApplicationProtocols(String[] protocols) {
        this.delegate.setApplicationProtocols(protocols);
    }

    @Override
    String[] getApplicationProtocols() {
        return this.delegate.getApplicationProtocols();
    }

    @Override
    public String getApplicationProtocol() {
        return this.delegate.getApplicationProtocol();
    }

    @Override
    void setApplicationProtocolSelector(ApplicationProtocolSelector selector) {
        this.delegate.setApplicationProtocolSelector(selector == null ? null : new ApplicationProtocolSelectorAdapter(this, selector));
    }

    @Override
    byte[] getTlsUnique() {
        return this.delegate.getTlsUnique();
    }

    @Override
    byte[] exportKeyingMaterial(String label, byte[] context, int length) throws SSLException {
        return this.delegate.exportKeyingMaterial(label, context, length);
    }

    @Override
    public String getHandshakeApplicationProtocol() {
        return this.delegate.getHandshakeApplicationProtocol();
    }

    @Override
    public void setHandshakeApplicationProtocolSelector(BiFunction<SSLEngine, List<String>, String> selector) {
        this.selector = selector;
        this.setApplicationProtocolSelector(Java8EngineWrapper.toApplicationProtocolSelector(selector));
    }

    @Override
    public BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector() {
        return this.selector;
    }

    private static ApplicationProtocolSelector toApplicationProtocolSelector(final BiFunction<SSLEngine, List<String>, String> selector) {
        return selector == null ? null : new ApplicationProtocolSelector(){

            @Override
            public String selectApplicationProtocol(SSLEngine engine, List<String> protocols) {
                return (String)selector.apply(engine, protocols);
            }

            @Override
            public String selectApplicationProtocol(SSLSocket socket, List<String> protocols) {
                throw new UnsupportedOperationException();
            }
        };
    }
}

