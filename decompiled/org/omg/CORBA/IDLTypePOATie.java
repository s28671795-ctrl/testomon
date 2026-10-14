/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.IDLTypeHelper;
import org.omg.CORBA.IDLTypeOperations;
import org.omg.CORBA.IDLTypePOA;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.PortableServer.POA;

public class IDLTypePOATie
extends IDLTypePOA {
    private IDLTypeOperations _delegate;
    private POA _poa;

    public IDLTypePOATie(IDLTypeOperations delegate) {
        this._delegate = delegate;
    }

    public IDLTypePOATie(IDLTypeOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public IDLType _this() {
        Object __o = this._this_object();
        IDLType __r = IDLTypeHelper.narrow(__o);
        return __r;
    }

    @Override
    public IDLType _this(ORB orb) {
        Object __o = this._this_object(orb);
        IDLType __r = IDLTypeHelper.narrow(__o);
        return __r;
    }

    public IDLTypeOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(IDLTypeOperations delegate) {
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
    public void destroy() {
        this._delegate.destroy();
    }

    @Override
    public TypeCode type() {
        return this._delegate.type();
    }

    @Override
    public DefinitionKind def_kind() {
        return this._delegate.def_kind();
    }
}

