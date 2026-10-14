/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextOperations;
import org.omg.CosNaming.NamingContextPOA;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;

public class NamingContextPOATie
extends NamingContextPOA {
    private NamingContextOperations _delegate;
    private POA _poa;

    public NamingContextPOATie(NamingContextOperations delegate) {
        this._delegate = delegate;
    }

    public NamingContextPOATie(NamingContextOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public NamingContext _this() {
        Object __o = this._this_object();
        NamingContext __r = NamingContextHelper.narrow(__o);
        return __r;
    }

    @Override
    public NamingContext _this(ORB orb) {
        Object __o = this._this_object(orb);
        NamingContext __r = NamingContextHelper.narrow(__o);
        return __r;
    }

    public NamingContextOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(NamingContextOperations delegate) {
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
    public void list(int how_many, BindingListHolder bl, BindingIteratorHolder bi) {
        this._delegate.list(how_many, bl, bi);
    }

    @Override
    public void bind(NameComponent[] n, Object obj) throws NotFound, AlreadyBound, CannotProceed, InvalidName {
        this._delegate.bind(n, obj);
    }

    @Override
    public NamingContext bind_new_context(NameComponent[] n) throws NotFound, AlreadyBound, CannotProceed, InvalidName {
        return this._delegate.bind_new_context(n);
    }

    @Override
    public void rebind_context(NameComponent[] n, NamingContext nc) throws NotFound, CannotProceed, InvalidName {
        this._delegate.rebind_context(n, nc);
    }

    @Override
    public void bind_context(NameComponent[] n, NamingContext nc) throws NotFound, AlreadyBound, CannotProceed, InvalidName {
        this._delegate.bind_context(n, nc);
    }

    @Override
    public void unbind(NameComponent[] n) throws NotFound, CannotProceed, InvalidName {
        this._delegate.unbind(n);
    }

    @Override
    public NamingContext new_context() {
        return this._delegate.new_context();
    }

    @Override
    public void destroy() throws NotEmpty {
        this._delegate.destroy();
    }

    @Override
    public void rebind(NameComponent[] n, Object obj) throws NotFound, CannotProceed, InvalidName {
        this._delegate.rebind(n, obj);
    }

    @Override
    public Object resolve(NameComponent[] n) throws NotFound, CannotProceed, InvalidName {
        return this._delegate.resolve(n);
    }
}

