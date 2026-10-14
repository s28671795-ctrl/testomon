/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyHelper;
import org.omg.CORBA.PolicyOperations;
import org.omg.CORBA.PolicyPOA;
import org.omg.PortableServer.POA;

public class PolicyPOATie
extends PolicyPOA {
    private PolicyOperations _delegate;
    private POA _poa;

    public PolicyPOATie(PolicyOperations delegate) {
        this._delegate = delegate;
    }

    public PolicyPOATie(PolicyOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public Policy _this() {
        Object __o = this._this_object();
        Policy __r = PolicyHelper.narrow(__o);
        return __r;
    }

    @Override
    public Policy _this(ORB orb) {
        Object __o = this._this_object(orb);
        Policy __r = PolicyHelper.narrow(__o);
        return __r;
    }

    public PolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(PolicyOperations delegate) {
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
    public int policy_type() {
        return this._delegate.policy_type();
    }

    @Override
    public Policy copy() {
        return this._delegate.copy();
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

