/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.FixedDef;
import org.omg.CORBA.FixedDefHelper;
import org.omg.CORBA.FixedDefOperations;
import org.omg.CORBA.FixedDefPOA;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.PortableServer.POA;

public class FixedDefPOATie
extends FixedDefPOA {
    private FixedDefOperations _delegate;
    private POA _poa;

    public FixedDefPOATie(FixedDefOperations delegate) {
        this._delegate = delegate;
    }

    public FixedDefPOATie(FixedDefOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public FixedDef _this() {
        Object __o = this._this_object();
        FixedDef __r = FixedDefHelper.narrow(__o);
        return __r;
    }

    @Override
    public FixedDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        FixedDef __r = FixedDefHelper.narrow(__o);
        return __r;
    }

    public FixedDefOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(FixedDefOperations delegate) {
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
    public void scale(short a) {
        this._delegate.scale(a);
    }

    @Override
    public void digits(short a) {
        this._delegate.digits(a);
    }

    @Override
    public short digits() {
        return this._delegate.digits();
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
    public short scale() {
        return this._delegate.scale();
    }

    @Override
    public DefinitionKind def_kind() {
        return this._delegate.def_kind();
    }
}

