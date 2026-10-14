/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.PortableInterceptor.ORBInitInfo;
import org.omg.PortableInterceptor.ORBInitializerOperations;
import org.omg.PortableInterceptor._ORBInitializerLocalBase;

public class ORBInitializerLocalTie
extends _ORBInitializerLocalBase {
    private static final long serialVersionUID = 1L;
    private ORBInitializerOperations _delegate;

    public ORBInitializerLocalTie(ORBInitializerOperations delegate) {
        this._delegate = delegate;
    }

    public ORBInitializerOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ORBInitializerOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public void post_init(ORBInitInfo info) {
        this._delegate.post_init(info);
    }

    @Override
    public void pre_init(ORBInitInfo info) {
        this._delegate.pre_init(info);
    }
}

