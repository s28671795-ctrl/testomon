/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.PortableInterceptor.IORInfo;
import org.omg.PortableInterceptor.IORInterceptorOperations;
import org.omg.PortableInterceptor._IORInterceptorLocalBase;

public class IORInterceptorLocalTie
extends _IORInterceptorLocalBase {
    private static final long serialVersionUID = 1L;
    private IORInterceptorOperations _delegate;

    public IORInterceptorLocalTie(IORInterceptorOperations delegate) {
        this._delegate = delegate;
    }

    public IORInterceptorOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(IORInterceptorOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public String name() {
        return this._delegate.name();
    }

    @Override
    public void establish_components(IORInfo info) {
        this._delegate.establish_components(info);
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

