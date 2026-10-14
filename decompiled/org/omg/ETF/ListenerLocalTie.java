/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.ETF.Connection;
import org.omg.ETF.Handle;
import org.omg.ETF.ListenerOperations;
import org.omg.ETF.Profile;
import org.omg.ETF._ListenerLocalBase;

public class ListenerLocalTie
extends _ListenerLocalBase {
    private static final long serialVersionUID = 1L;
    private ListenerOperations _delegate;

    public ListenerLocalTie(ListenerOperations delegate) {
        this._delegate = delegate;
    }

    public ListenerOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ListenerOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public void listen() {
        this._delegate.listen();
    }

    @Override
    public Connection accept() {
        return this._delegate.accept();
    }

    @Override
    public void completed_data(Connection conn) {
        this._delegate.completed_data(conn);
    }

    @Override
    public Profile endpoint() {
        return this._delegate.endpoint();
    }

    @Override
    public void set_handle(Handle up) {
        this._delegate.set_handle(up);
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

