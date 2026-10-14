/*
 * Decompiled with CFR 0.152.
 */
package org.omg.BiDirPolicy;

import org.omg.BiDirPolicy.BidirectionalPolicy;
import org.omg.BiDirPolicy.BidirectionalPolicyHelper;
import org.omg.BiDirPolicy.BidirectionalPolicyOperations;
import org.omg.BiDirPolicy.BidirectionalPolicyPOA;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.PortableServer.POA;

public class BidirectionalPolicyPOATie
extends BidirectionalPolicyPOA {
    private BidirectionalPolicyOperations _delegate;
    private POA _poa;

    public BidirectionalPolicyPOATie(BidirectionalPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public BidirectionalPolicyPOATie(BidirectionalPolicyOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public BidirectionalPolicy _this() {
        Object __o = this._this_object();
        BidirectionalPolicy __r = BidirectionalPolicyHelper.narrow(__o);
        return __r;
    }

    @Override
    public BidirectionalPolicy _this(ORB orb) {
        Object __o = this._this_object(orb);
        BidirectionalPolicy __r = BidirectionalPolicyHelper.narrow(__o);
        return __r;
    }

    public BidirectionalPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(BidirectionalPolicyOperations delegate) {
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
    public short value() {
        return this._delegate.value();
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

