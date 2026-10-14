/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.RTCORBA.CurrentOperations;
import org.omg.RTCORBA._CurrentLocalBase;

public class CurrentLocalTie
extends _CurrentLocalBase {
    private static final long serialVersionUID = 1L;
    private CurrentOperations _delegate;

    public CurrentLocalTie(CurrentOperations delegate) {
        this._delegate = delegate;
    }

    public CurrentOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(CurrentOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public void the_priority(short a) {
        this._delegate.the_priority(a);
    }

    @Override
    public short the_priority() {
        return this._delegate.the_priority();
    }
}

