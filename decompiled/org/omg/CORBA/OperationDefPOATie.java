/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ContainedPackage.Description;
import org.omg.CORBA.Container;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.OperationDef;
import org.omg.CORBA.OperationDefHelper;
import org.omg.CORBA.OperationDefOperations;
import org.omg.CORBA.OperationDefPOA;
import org.omg.CORBA.OperationMode;
import org.omg.CORBA.ParameterDescription;
import org.omg.CORBA.Repository;
import org.omg.CORBA.TypeCode;
import org.omg.PortableServer.POA;

public class OperationDefPOATie
extends OperationDefPOA {
    private OperationDefOperations _delegate;
    private POA _poa;

    public OperationDefPOATie(OperationDefOperations delegate) {
        this._delegate = delegate;
    }

    public OperationDefPOATie(OperationDefOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public OperationDef _this() {
        Object __o = this._this_object();
        OperationDef __r = OperationDefHelper.narrow(__o);
        return __r;
    }

    @Override
    public OperationDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        OperationDef __r = OperationDefHelper.narrow(__o);
        return __r;
    }

    public OperationDefOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(OperationDefOperations delegate) {
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
    public Repository containing_repository() {
        return this._delegate.containing_repository();
    }

    @Override
    public DefinitionKind def_kind() {
        return this._delegate.def_kind();
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
    public void name(String a) {
        this._delegate.name(a);
    }

    @Override
    public void params(ParameterDescription[] a) {
        this._delegate.params(a);
    }

    @Override
    public IDLType result_def() {
        return this._delegate.result_def();
    }

    @Override
    public String[] contexts() {
        return this._delegate.contexts();
    }

    @Override
    public Container defined_in() {
        return this._delegate.defined_in();
    }

    @Override
    public OperationMode mode() {
        return this._delegate.mode();
    }

    @Override
    public String version() {
        return this._delegate.version();
    }

    @Override
    public void result_def(IDLType a) {
        this._delegate.result_def(a);
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
    public ParameterDescription[] params() {
        return this._delegate.params();
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
    public TypeCode result() {
        return this._delegate.result();
    }

    @Override
    public void contexts(String[] a) {
        this._delegate.contexts(a);
    }

    @Override
    public void mode(OperationMode a) {
        this._delegate.mode(a);
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }

    @Override
    public ExceptionDef[] exceptions() {
        return this._delegate.exceptions();
    }

    @Override
    public void exceptions(ExceptionDef[] a) {
        this._delegate.exceptions(a);
    }

    @Override
    public void id(String a) {
        this._delegate.id(a);
    }
}

