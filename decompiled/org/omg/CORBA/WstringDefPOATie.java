/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.WstringDef;
import org.omg.CORBA.WstringDefHelper;
import org.omg.CORBA.WstringDefOperations;
import org.omg.CORBA.WstringDefPOA;
import org.omg.PortableServer.POA;

public class WstringDefPOATie
extends WstringDefPOA {
    private WstringDefOperations _delegate;
    private POA _poa;

    public WstringDefPOATie(WstringDefOperations delegate) {
        this._delegate = delegate;
    }

    public WstringDefPOATie(WstringDefOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public WstringDef _this() {
        Object __o = this._this_object();
        WstringDef __r = WstringDefHelper.narrow(__o);
        return __r;
    }

    @Override
    public WstringDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        WstringDef __r = WstringDefHelper.narrow(__o);
        return __r;
    }

    public WstringDefOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(WstringDefOperations delegate) {
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
    public void bound(int a) {
        this._delegate.bound(a);
    }

    @Override
    public int bound() {
        return this._delegate.bound();
    }

    @Override
    public TypeCode type() {
        return this._delegate.type();
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }

    @Override
    public DefinitionKind def_kind() {
        return this._delegate.def_kind();
    }
}

