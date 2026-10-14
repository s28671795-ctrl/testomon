/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.ETF.BufferHolder;
import org.omg.ETF.BufferListOperations;
import org.omg.ETF._BufferListLocalBase;

public class BufferListLocalTie
extends _BufferListLocalBase {
    private static final long serialVersionUID = 1L;
    private BufferListOperations _delegate;

    public BufferListLocalTie(BufferListOperations delegate) {
        this._delegate = delegate;
    }

    public BufferListOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(BufferListOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public void release_buffers() {
        this._delegate.release_buffers();
    }

    @Override
    public void get_buffer(int index, BufferHolder buf) {
        this._delegate.get_buffer(index, buf);
    }

    @Override
    public int add_buffer(int size, BufferHolder buf) {
        return this._delegate.add_buffer(size, buf);
    }

    @Override
    public int num_buffers() {
        return this._delegate.num_buffers();
    }
}

