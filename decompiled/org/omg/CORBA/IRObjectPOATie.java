/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.IRObject;
import org.omg.CORBA.IRObjectHelper;
import org.omg.CORBA.IRObjectOperations;
import org.omg.CORBA.IRObjectPOA;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.PortableServer.POA;

public class IRObjectPOATie
extends IRObjectPOA {
    private IRObjectOperations _delegate;
    private POA _poa;

    public IRObjectPOATie(IRObjectOperations delegate) {
        this._delegate = delegate;
    }

    public IRObjectPOATie(IRObjectOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public IRObject _this() {
        Object __o = this._this_object();
        IRObject __r = IRObjectHelper.narrow(__o);
        return __r;
    }

    @Override
    public IRObject _this(ORB orb) {
        Object __o = this._this_object(orb);
        IRObject __r = IRObjectHelper.narrow(__o);
        return __r;
    }

    public IRObjectOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(IRObjectOperations delegate) {
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
    public DefinitionKind def_kind() {
        return this._delegate.def_kind();
    }
}

