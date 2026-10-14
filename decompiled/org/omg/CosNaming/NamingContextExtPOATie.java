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
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextExtOperations;
import org.omg.CosNaming.NamingContextExtPOA;
import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;

public class NamingContextExtPOATie
extends NamingContextExtPOA {
    private NamingContextExtOperations _delegate;
    private POA _poa;

    public NamingContextExtPOATie(NamingContextExtOperations delegate) {
        this._delegate = delegate;
    }

    public NamingContextExtPOATie(NamingContextExtOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public NamingContextExt _this() {
        Object __o = this._this_object();
        NamingContextExt __r = NamingContextExtHelper.narrow(__o);
        return __r;
    }

    @Override
    public NamingContextExt _this(ORB orb) {
        Object __o = this._this_object(orb);
        NamingContextExt __r = NamingContextExtHelper.narrow(__o);
        return __r;
    }

    public NamingContextExtOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(NamingContextExtOperations delegate) {
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
    public String to_url(String addr, String sn) throws InvalidName, InvalidAddress {
        return this._delegate.to_url(addr, sn);
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
    public Object resolve_str(String n) throws NotFound, CannotProceed, InvalidName {
        return this._delegate.resolve_str(n);
    }

    @Override
    public void rebind(NameComponent[] n, Object obj) throws NotFound, CannotProceed, InvalidName {
        this._delegate.rebind(n, obj);
    }

    @Override
    public void destroy() throws NotEmpty {
        this._delegate.destroy();
    }

    @Override
    public NameComponent[] to_name(String sn) throws InvalidName {
        return this._delegate.to_name(sn);
    }

    @Override
    public Object resolve(NameComponent[] n) throws NotFound, CannotProceed, InvalidName {
        return this._delegate.resolve(n);
    }

    @Override
    public String to_string(NameComponent[] n) throws InvalidName {
        return this._delegate.to_string(n);
    }
}

