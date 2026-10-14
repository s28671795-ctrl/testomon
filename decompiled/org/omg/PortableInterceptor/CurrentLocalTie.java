/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Any;
import org.omg.PortableInterceptor.CurrentOperations;
import org.omg.PortableInterceptor.InvalidSlot;
import org.omg.PortableInterceptor._CurrentLocalBase;

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
    public Any get_slot(int id) throws InvalidSlot {
        return this._delegate.get_slot(id);
    }

    @Override
    public void set_slot(int id, Any data) throws InvalidSlot {
        this._delegate.set_slot(id, data);
    }
}

