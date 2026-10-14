/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.PortableServer.AdapterActivatorOperations;
import org.omg.PortableServer.POA;
import org.omg.PortableServer._AdapterActivatorLocalBase;

public class AdapterActivatorLocalTie
extends _AdapterActivatorLocalBase {
    private static final long serialVersionUID = 1L;
    private AdapterActivatorOperations _delegate;

    public AdapterActivatorLocalTie(AdapterActivatorOperations delegate) {
        this._delegate = delegate;
    }

    public AdapterActivatorOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(AdapterActivatorOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public boolean unknown_adapter(POA parent, String name) {
        return this._delegate.unknown_adapter(parent, name);
    }
}

