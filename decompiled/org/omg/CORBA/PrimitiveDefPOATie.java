/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.PrimitiveDef;
import org.omg.CORBA.PrimitiveDefHelper;
import org.omg.CORBA.PrimitiveDefOperations;
import org.omg.CORBA.PrimitiveDefPOA;
import org.omg.CORBA.PrimitiveKind;
import org.omg.CORBA.TypeCode;
import org.omg.PortableServer.POA;

public class PrimitiveDefPOATie
extends PrimitiveDefPOA {
    private PrimitiveDefOperations _delegate;
    private POA _poa;

    public PrimitiveDefPOATie(PrimitiveDefOperations delegate) {
        this._delegate = delegate;
    }

    public PrimitiveDefPOATie(PrimitiveDefOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public PrimitiveDef _this() {
        Object __o = this._this_object();
        PrimitiveDef __r = PrimitiveDefHelper.narrow(__o);
        return __r;
    }

    @Override
    public PrimitiveDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        PrimitiveDef __r = PrimitiveDefHelper.narrow(__o);
        return __r;
    }

    public PrimitiveDefOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(PrimitiveDefOperations delegate) {
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
    public TypeCode type() {
        return this._delegate.type();
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }

    @Override
    public PrimitiveKind kind() {
        return this._delegate.kind();
    }

    @Override
    public DefinitionKind def_kind() {
        return this._delegate.def_kind();
    }
}

