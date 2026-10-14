/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.StringDef;
import org.omg.CORBA.StringDefHelper;
import org.omg.CORBA.StringDefOperations;
import org.omg.CORBA.StringDefPOA;
import org.omg.CORBA.TypeCode;
import org.omg.PortableServer.POA;

public class StringDefPOATie
extends StringDefPOA {
    private StringDefOperations _delegate;
    private POA _poa;

    public StringDefPOATie(StringDefOperations delegate) {
        this._delegate = delegate;
    }

    public StringDefPOATie(StringDefOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public StringDef _this() {
        Object __o = this._this_object();
        StringDef __r = StringDefHelper.narrow(__o);
        return __r;
    }

    @Override
    public StringDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        StringDef __r = StringDefHelper.narrow(__o);
        return __r;
    }

    public StringDefOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(StringDefOperations delegate) {
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

