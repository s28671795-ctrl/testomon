/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerFactoryOperations;
import org.omg.PortableServer.POAManagerFactoryPackage.ManagerAlreadyExists;
import org.omg.PortableServer._POAManagerFactoryLocalBase;

public class POAManagerFactoryLocalTie
extends _POAManagerFactoryLocalBase {
    private static final long serialVersionUID = 1L;
    private POAManagerFactoryOperations _delegate;

    public POAManagerFactoryLocalTie(POAManagerFactoryOperations delegate) {
        this._delegate = delegate;
    }

    public POAManagerFactoryOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(POAManagerFactoryOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public POAManager find(String id) {
        return this._delegate.find(id);
    }

    @Override
    public POAManager[] list() {
        return this._delegate.list();
    }

    @Override
    public POAManager create_POAManager(String id, Policy[] policies) throws ManagerAlreadyExists, PolicyError {
        return this._delegate.create_POAManager(id, policies);
    }
}

