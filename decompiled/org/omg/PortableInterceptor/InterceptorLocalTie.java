/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.PortableInterceptor.InterceptorOperations;
import org.omg.PortableInterceptor._InterceptorLocalBase;

public class InterceptorLocalTie
extends _InterceptorLocalBase {
    private static final long serialVersionUID = 1L;
    private InterceptorOperations _delegate;

    public InterceptorLocalTie(InterceptorOperations delegate) {
        this._delegate = delegate;
    }

    public InterceptorOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(InterceptorOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public String name() {
        return this._delegate.name();
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

