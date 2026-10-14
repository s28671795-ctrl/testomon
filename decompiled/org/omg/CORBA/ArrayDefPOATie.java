/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ArrayDef;
import org.omg.CORBA.ArrayDefHelper;
import org.omg.CORBA.ArrayDefOperations;
import org.omg.CORBA.ArrayDefPOA;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.PortableServer.POA;

public class ArrayDefPOATie
extends ArrayDefPOA {
    private ArrayDefOperations _delegate;
    private POA _poa;

    public ArrayDefPOATie(ArrayDefOperations delegate) {
        this._delegate = delegate;
    }

    public ArrayDefPOATie(ArrayDefOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public ArrayDef _this() {
        Object __o = this._this_object();
        ArrayDef __r = ArrayDefHelper.narrow(__o);
        return __r;
    }

    @Override
    public ArrayDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        ArrayDef __r = ArrayDefHelper.narrow(__o);
        return __r;
    }

    public ArrayDefOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ArrayDefOperations delegate) {
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
    public TypeCode element_type() {
        return this._delegate.element_type();
    }

    @Override
    public int length() {
        return this._delegate.length();
    }

    @Override
    public IDLType element_type_def() {
        return this._delegate.element_type_def();
    }

    @Override
    public void length(int a) {
        this._delegate.length(a);
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

