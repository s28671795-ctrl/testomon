/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.BindingHolder;
import org.omg.CosNaming.BindingIterator;
import org.omg.CosNaming.BindingIteratorHelper;
import org.omg.CosNaming.BindingIteratorOperations;
import org.omg.CosNaming.BindingIteratorPOA;
import org.omg.CosNaming.BindingListHolder;
import org.omg.PortableServer.POA;

public class BindingIteratorPOATie
extends BindingIteratorPOA {
    private BindingIteratorOperations _delegate;
    private POA _poa;

    public BindingIteratorPOATie(BindingIteratorOperations delegate) {
        this._delegate = delegate;
    }

    public BindingIteratorPOATie(BindingIteratorOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public BindingIterator _this() {
        Object __o = this._this_object();
        BindingIterator __r = BindingIteratorHelper.narrow(__o);
        return __r;
    }

    @Override
    public BindingIterator _this(ORB orb) {
        Object __o = this._this_object(orb);
        BindingIterator __r = BindingIteratorHelper.narrow(__o);
        return __r;
    }

    public BindingIteratorOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(BindingIteratorOperations delegate) {
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
    public boolean next_one(BindingHolder b) {
        return this._delegate.next_one(b);
    }

    @Override
    public boolean next_n(int how_many, BindingListHolder bl) {
        return this._delegate.next_n(how_many, bl);
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

