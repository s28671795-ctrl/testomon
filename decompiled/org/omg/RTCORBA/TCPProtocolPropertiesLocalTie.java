/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.RTCORBA.TCPProtocolPropertiesOperations;
import org.omg.RTCORBA._TCPProtocolPropertiesLocalBase;

public class TCPProtocolPropertiesLocalTie
extends _TCPProtocolPropertiesLocalBase {
    private static final long serialVersionUID = 1L;
    private TCPProtocolPropertiesOperations _delegate;

    public TCPProtocolPropertiesLocalTie(TCPProtocolPropertiesOperations delegate) {
        this._delegate = delegate;
    }

    public TCPProtocolPropertiesOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(TCPProtocolPropertiesOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public boolean no_delay() {
        return this._delegate.no_delay();
    }

    @Override
    public int recv_buffer_size() {
        return this._delegate.recv_buffer_size();
    }

    @Override
    public void recv_buffer_size(int a) {
        this._delegate.recv_buffer_size(a);
    }

    @Override
    public boolean keep_alive() {
        return this._delegate.keep_alive();
    }

    @Override
    public void keep_alive(boolean a) {
        this._delegate.keep_alive(a);
    }

    @Override
    public int send_buffer_size() {
        return this._delegate.send_buffer_size();
    }

    @Override
    public boolean dont_route() {
        return this._delegate.dont_route();
    }

    @Override
    public void dont_route(boolean a) {
        this._delegate.dont_route(a);
    }

    @Override
    public void no_delay(boolean a) {
        this._delegate.no_delay(a);
    }

    @Override
    public void send_buffer_size(int a) {
        this._delegate.send_buffer_size(a);
    }
}

