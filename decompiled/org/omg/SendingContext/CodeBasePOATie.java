/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SendingContext;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Repository;
import org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.omg.PortableServer.POA;
import org.omg.SendingContext.CodeBase;
import org.omg.SendingContext.CodeBaseHelper;
import org.omg.SendingContext.CodeBaseOperations;
import org.omg.SendingContext.CodeBasePOA;

public class CodeBasePOATie
extends CodeBasePOA {
    private CodeBaseOperations _delegate;
    private POA _poa;

    public CodeBasePOATie(CodeBaseOperations delegate) {
        this._delegate = delegate;
    }

    public CodeBasePOATie(CodeBaseOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public CodeBase _this() {
        Object __o = this._this_object();
        CodeBase __r = CodeBaseHelper.narrow(__o);
        return __r;
    }

    @Override
    public CodeBase _this(ORB orb) {
        Object __o = this._this_object(orb);
        CodeBase __r = CodeBaseHelper.narrow(__o);
        return __r;
    }

    public CodeBaseOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(CodeBaseOperations delegate) {
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
    public Repository get_ir() {
        return this._delegate.get_ir();
    }

    @Override
    public String[] bases(String x) {
        return this._delegate.bases(x);
    }

    @Override
    public FullValueDescription meta(String x) {
        return this._delegate.meta(x);
    }

    @Override
    public String[] implementations(String[] x) {
        return this._delegate.implementations(x);
    }

    @Override
    public String implementation(String x) {
        return this._delegate.implementation(x);
    }

    @Override
    public String implementationx(String x) {
        return this._delegate.implementationx(x);
    }

    @Override
    public FullValueDescription[] metas(String[] x) {
        return this._delegate.metas(x);
    }
}

