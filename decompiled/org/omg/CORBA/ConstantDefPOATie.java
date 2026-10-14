/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.ConstantDef;
import org.omg.CORBA.ConstantDefHelper;
import org.omg.CORBA.ConstantDefOperations;
import org.omg.CORBA.ConstantDefPOA;
import org.omg.CORBA.ContainedPackage.Description;
import org.omg.CORBA.Container;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Repository;
import org.omg.CORBA.TypeCode;
import org.omg.PortableServer.POA;

public class ConstantDefPOATie
extends ConstantDefPOA {
    private ConstantDefOperations _delegate;
    private POA _poa;

    public ConstantDefPOATie(ConstantDefOperations delegate) {
        this._delegate = delegate;
    }

    public ConstantDefPOATie(ConstantDefOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public ConstantDef _this() {
        Object __o = this._this_object();
        ConstantDef __r = ConstantDefHelper.narrow(__o);
        return __r;
    }

    @Override
    public ConstantDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        ConstantDef __r = ConstantDefHelper.narrow(__o);
        return __r;
    }

    public ConstantDefOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ConstantDefOperations delegate) {
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
    public void value(Any a) {
        this._delegate.value(a);
    }

    @Override
    public IDLType type_def() {
        return this._delegate.type_def();
    }

    @Override
    public Repository containing_repository() {
        return this._delegate.containing_repository();
    }

    @Override
    public DefinitionKind def_kind() {
        return this._delegate.def_kind();
    }

    @Override
    public void type_def(IDLType a) {
        this._delegate.type_def(a);
    }

    @Override
    public String absolute_name() {
        return this._delegate.absolute_name();
    }

    @Override
    public String name() {
        return this._delegate.name();
    }

    @Override
    public TypeCode type() {
        return this._delegate.type();
    }

    @Override
    public void name(String a) {
        this._delegate.name(a);
    }

    @Override
    public Container defined_in() {
        return this._delegate.defined_in();
    }

    @Override
    public String version() {
        return this._delegate.version();
    }

    @Override
    public Any value() {
        return this._delegate.value();
    }

    @Override
    public void version(String a) {
        this._delegate.version(a);
    }

    @Override
    public Description describe() {
        return this._delegate.describe();
    }

    @Override
    public String id() {
        return this._delegate.id();
    }

    @Override
    public void move(Container new_container, String new_name, String new_version) {
        this._delegate.move(new_container, new_name, new_version);
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }

    @Override
    public void id(String a) {
        this._delegate.id(a);
    }
}

