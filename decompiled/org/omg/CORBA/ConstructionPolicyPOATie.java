/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ConstructionPolicy;
import org.omg.CORBA.ConstructionPolicyHelper;
import org.omg.CORBA.ConstructionPolicyOperations;
import org.omg.CORBA.ConstructionPolicyPOA;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.PortableServer.POA;

public class ConstructionPolicyPOATie
extends ConstructionPolicyPOA {
    private ConstructionPolicyOperations _delegate;
    private POA _poa;

    public ConstructionPolicyPOATie(ConstructionPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public ConstructionPolicyPOATie(ConstructionPolicyOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public ConstructionPolicy _this() {
        Object __o = this._this_object();
        ConstructionPolicy __r = ConstructionPolicyHelper.narrow(__o);
        return __r;
    }

    @Override
    public ConstructionPolicy _this(ORB orb) {
        Object __o = this._this_object(orb);
        ConstructionPolicy __r = ConstructionPolicyHelper.narrow(__o);
        return __r;
    }

    public ConstructionPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ConstructionPolicyOperations delegate) {
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
    public void make_domain_manager(InterfaceDef object_type, boolean constr_policy) {
        this._delegate.make_domain_manager(object_type, constr_policy);
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

