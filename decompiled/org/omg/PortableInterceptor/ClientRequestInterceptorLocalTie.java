/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.PortableInterceptor.ClientRequestInfo;
import org.omg.PortableInterceptor.ClientRequestInterceptorOperations;
import org.omg.PortableInterceptor.ForwardRequest;
import org.omg.PortableInterceptor._ClientRequestInterceptorLocalBase;

public class ClientRequestInterceptorLocalTie
extends _ClientRequestInterceptorLocalBase {
    private static final long serialVersionUID = 1L;
    private ClientRequestInterceptorOperations _delegate;

    public ClientRequestInterceptorLocalTie(ClientRequestInterceptorOperations delegate) {
        this._delegate = delegate;
    }

    public ClientRequestInterceptorOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ClientRequestInterceptorOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public String name() {
        return this._delegate.name();
    }

    @Override
    public void receive_other(ClientRequestInfo ri) throws ForwardRequest {
        this._delegate.receive_other(ri);
    }

    @Override
    public void send_poll(ClientRequestInfo ri) {
        this._delegate.send_poll(ri);
    }

    @Override
    public void receive_reply(ClientRequestInfo ri) {
        this._delegate.receive_reply(ri);
    }

    @Override
    public void send_request(ClientRequestInfo ri) throws ForwardRequest {
        this._delegate.send_request(ri);
    }

    @Override
    public void receive_exception(ClientRequestInfo ri) throws ForwardRequest {
        this._delegate.receive_exception(ri);
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

