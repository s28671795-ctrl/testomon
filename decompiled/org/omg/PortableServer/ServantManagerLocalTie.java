/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.PortableServer.ServantManagerOperations;
import org.omg.PortableServer._ServantManagerLocalBase;

public class ServantManagerLocalTie
extends _ServantManagerLocalBase {
    private static final long serialVersionUID = 1L;
    private ServantManagerOperations _delegate;

    public ServantManagerLocalTie(ServantManagerOperations delegate) {
        this._delegate = delegate;
    }

    public ServantManagerOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ServantManagerOperations delegate) {
        this._delegate = delegate;
    }
}

