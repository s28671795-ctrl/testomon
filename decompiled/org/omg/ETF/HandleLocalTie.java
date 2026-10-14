/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.ETF.Connection;
import org.omg.ETF.HandleOperations;
import org.omg.ETF._HandleLocalBase;

public class HandleLocalTie
extends _HandleLocalBase {
    private static final long serialVersionUID = 1L;
    private HandleOperations _delegate;

    public HandleLocalTie(HandleOperations delegate) {
        this._delegate = delegate;
    }

    public HandleOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(HandleOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public void signal_data_available(Connection conn) {
        this._delegate.signal_data_available(conn);
    }

    @Override
    public void closed_by_peer(Connection conn) {
        this._delegate.closed_by_peer(conn);
    }

    @Override
    public boolean add_input(Connection conn) {
        return this._delegate.add_input(conn);
    }
}

