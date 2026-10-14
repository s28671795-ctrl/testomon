/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.RTCORBA.MutexOperations;
import org.omg.RTCORBA._MutexLocalBase;

public class MutexLocalTie
extends _MutexLocalBase {
    private static final long serialVersionUID = 1L;
    private MutexOperations _delegate;

    public MutexLocalTie(MutexOperations delegate) {
        this._delegate = delegate;
    }

    public MutexOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(MutexOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public void lock() {
        this._delegate.lock();
    }

    @Override
    public void unlock() {
        this._delegate.unlock();
    }

    @Override
    public boolean try_lock(long max_wait) {
        return this._delegate.try_lock(max_wait);
    }
}

