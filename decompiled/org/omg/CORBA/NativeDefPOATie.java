/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ContainedPackage.Description;
import org.omg.CORBA.Container;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.NativeDef;
import org.omg.CORBA.NativeDefHelper;
import org.omg.CORBA.NativeDefOperations;
import org.omg.CORBA.NativeDefPOA;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Repository;
import org.omg.CORBA.TypeCode;
import org.omg.PortableServer.POA;

public class NativeDefPOATie
extends NativeDefPOA {
    private NativeDefOperations _delegate;
    private POA _poa;

    public NativeDefPOATie(NativeDefOperations delegate) {
        this._delegate = delegate;
    }

    public NativeDefPOATie(NativeDefOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public NativeDef _this() {
        Object __o = this._this_object();
        NativeDef __r = NativeDefHelper.narrow(__o);
        return __r;
    }

    @Override
    public NativeDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        NativeDef __r = NativeDefHelper.narrow(__o);
        return __r;
    }

    public NativeDefOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(NativeDefOperations delegate) {
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
    public DefinitionKind def_kind() {
        return this._delegate.def_kind();
    }

    @Override
    public TypeCode type() {
        return this._delegate.type();
    }

    @Override
    public String absolute_name() {
        return this._delegate.absolute_name();
    }

    @Override
    public void id(String a) {
        this._delegate.id(a);
    }

    @Override
    public String name() {
        return this._delegate.name();
    }

    @Override
    public String version() {
        return this._delegate.version();
    }

    @Override
    public Description describe() {
        return this._delegate.describe();
    }

    @Override
    public void version(String a) {
        this._delegate.version(a);
    }

    @Override
    public String id() {
        return this._delegate.id();
    }

    @Override
    public Repository containing_repository() {
        return this._delegate.containing_repository();
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }

    @Override
    public Container defined_in() {
        return this._delegate.defined_in();
    }

    @Override
    public void move(Container new_container, String new_name, String new_version) {
        this._delegate.move(new_container, new_name, new_version);
    }

    @Override
    public void name(String a) {
        this._delegate.name(a);
    }
}

