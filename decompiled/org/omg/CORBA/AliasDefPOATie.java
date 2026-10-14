/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AliasDef;
import org.omg.CORBA.AliasDefHelper;
import org.omg.CORBA.AliasDefOperations;
import org.omg.CORBA.AliasDefPOA;
import org.omg.CORBA.ContainedPackage.Description;
import org.omg.CORBA.Container;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Repository;
import org.omg.CORBA.TypeCode;
import org.omg.PortableServer.POA;

public class AliasDefPOATie
extends AliasDefPOA {
    private AliasDefOperations _delegate;
    private POA _poa;

    public AliasDefPOATie(AliasDefOperations delegate) {
        this._delegate = delegate;
    }

    public AliasDefPOATie(AliasDefOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public AliasDef _this() {
        Object __o = this._this_object();
        AliasDef __r = AliasDefHelper.narrow(__o);
        return __r;
    }

    @Override
    public AliasDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        AliasDef __r = AliasDefHelper.narrow(__o);
        return __r;
    }

    public AliasDefOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(AliasDefOperations delegate) {
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
    public IDLType original_type_def() {
        return this._delegate.original_type_def();
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
    public void original_type_def(IDLType a) {
        this._delegate.original_type_def(a);
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

