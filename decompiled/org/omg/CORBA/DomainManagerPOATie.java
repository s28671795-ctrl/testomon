/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DomainManager;
import org.omg.CORBA.DomainManagerHelper;
import org.omg.CORBA.DomainManagerOperations;
import org.omg.CORBA.DomainManagerPOA;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.PortableServer.POA;

public class DomainManagerPOATie
extends DomainManagerPOA {
    private DomainManagerOperations _delegate;
    private POA _poa;

    public DomainManagerPOATie(DomainManagerOperations delegate) {
        this._delegate = delegate;
    }

    public DomainManagerPOATie(DomainManagerOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public DomainManager _this() {
        Object __o = this._this_object();
        DomainManager __r = DomainManagerHelper.narrow(__o);
        return __r;
    }

    @Override
    public DomainManager _this(ORB orb) {
        Object __o = this._this_object(orb);
        DomainManager __r = DomainManagerHelper.narrow(__o);
        return __r;
    }

    public DomainManagerOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(DomainManagerOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public POA _default_POA() {
        if (this._poa != null) {
            return this._poa;
        }
        return super._default_POA();
    }

    @Override
    public Policy get_domain_policy(int policy_type) {
        return this._delegate.get_domain_policy(policy_type);
    }
}

