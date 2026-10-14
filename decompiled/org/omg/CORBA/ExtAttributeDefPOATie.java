/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AttributeMode;
import org.omg.CORBA.ContainedPackage.Description;
import org.omg.CORBA.Container;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.ExceptionDescription;
import org.omg.CORBA.ExtAttributeDef;
import org.omg.CORBA.ExtAttributeDefHelper;
import org.omg.CORBA.ExtAttributeDefOperations;
import org.omg.CORBA.ExtAttributeDefPOA;
import org.omg.CORBA.ExtAttributeDescription;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Repository;
import org.omg.CORBA.TypeCode;
import org.omg.PortableServer.POA;

public class ExtAttributeDefPOATie
extends ExtAttributeDefPOA {
    private ExtAttributeDefOperations _delegate;
    private POA _poa;

    public ExtAttributeDefPOATie(ExtAttributeDefOperations delegate) {
        this._delegate = delegate;
    }

    public ExtAttributeDefPOATie(ExtAttributeDefOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public ExtAttributeDef _this() {
        Object __o = this._this_object();
        ExtAttributeDef __r = ExtAttributeDefHelper.narrow(__o);
        return __r;
    }

    @Override
    public ExtAttributeDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        ExtAttributeDef __r = ExtAttributeDefHelper.narrow(__o);
        return __r;
    }

    public ExtAttributeDefOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ExtAttributeDefOperations delegate) {
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
    public IDLType type_def() {
        return this._delegate.type_def();
    }

    @Override
    public ExceptionDescription[] set_exceptions() {
        return this._delegate.set_exceptions();
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
    public void mode(AttributeMode a) {
        this._delegate.mode(a);
    }

    @Override
    public void get_exceptions(ExceptionDescription[] a) {
        this._delegate.get_exceptions(a);
    }

    @Override
    public AttributeMode mode() {
        return this._delegate.mode();
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
    public void version(String a) {
        this._delegate.version(a);
    }

    @Override
    public Description describe() {
        return this._delegate.describe();
    }

    @Override
    public ExtAttributeDescription describe_attribute() {
        return this._delegate.describe_attribute();
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
    public ExceptionDescription[] get_exceptions() {
        return this._delegate.get_exceptions();
    }

    @Override
    public void set_exceptions(ExceptionDescription[] a) {
        this._delegate.set_exceptions(a);
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

