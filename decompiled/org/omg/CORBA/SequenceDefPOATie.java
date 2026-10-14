/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SequenceDef;
import org.omg.CORBA.SequenceDefHelper;
import org.omg.CORBA.SequenceDefOperations;
import org.omg.CORBA.SequenceDefPOA;
import org.omg.CORBA.TypeCode;
import org.omg.PortableServer.POA;

public class SequenceDefPOATie
extends SequenceDefPOA {
    private SequenceDefOperations _delegate;
    private POA _poa;

    public SequenceDefPOATie(SequenceDefOperations delegate) {
        this._delegate = delegate;
    }

    public SequenceDefPOATie(SequenceDefOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public SequenceDef _this() {
        Object __o = this._this_object();
        SequenceDef __r = SequenceDefHelper.narrow(__o);
        return __r;
    }

    @Override
    public SequenceDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        SequenceDef __r = SequenceDefHelper.narrow(__o);
        return __r;
    }

    public SequenceDefOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(SequenceDefOperations delegate) {
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
    public TypeCode element_type() {
        return this._delegate.element_type();
    }

    @Override
    public IDLType element_type_def() {
        return this._delegate.element_type_def();
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

    @Override
    public void element_type_def(IDLType a) {
        this._delegate.element_type_def(a);
    }
}

