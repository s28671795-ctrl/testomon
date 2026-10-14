/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CurrentOperations;
import org.omg.CORBA._CurrentLocalBase;

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
}

