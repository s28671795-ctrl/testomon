/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.PortableInterceptor.ForwardRequest;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.omg.PortableInterceptor.ServerRequestInterceptorOperations;
import org.omg.PortableInterceptor._ServerRequestInterceptorLocalBase;

public class ServerRequestInterceptorLocalTie
extends _ServerRequestInterceptorLocalBase {
    private static final long serialVersionUID = 1L;
    private ServerRequestInterceptorOperations _delegate;

    public ServerRequestInterceptorLocalTie(ServerRequestInterceptorOperations delegate) {
        this._delegate = delegate;
    }

    public ServerRequestInterceptorOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ServerRequestInterceptorOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public void receive_request(ServerRequestInfo ri) throws ForwardRequest {
        this._delegate.receive_request(ri);
    }

    @Override
    public String name() {
        return this._delegate.name();
    }

    @Override
    public void send_other(ServerRequestInfo ri) throws ForwardRequest {
        this._delegate.send_other(ri);
    }

    @Override
    public void send_exception(ServerRequestInfo ri) throws ForwardRequest {
        this._delegate.send_exception(ri);
    }

    @Override
    public void receive_request_service_contexts(ServerRequestInfo ri) throws ForwardRequest {
        this._delegate.receive_request_service_contexts(ri);
    }

    @Override
    public void send_reply(ServerRequestInfo ri) {
        this._delegate.send_reply(ri);
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

