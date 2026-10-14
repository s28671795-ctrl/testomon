/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.TypeCode;
import org.omg.Dynamic.Parameter;
import org.omg.IOP.ServiceContext;
import org.omg.IOP.TaggedComponent;
import org.omg.IOP.TaggedProfile;
import org.omg.PortableInterceptor.ClientRequestInfoOperations;
import org.omg.PortableInterceptor.InvalidSlot;
import org.omg.PortableInterceptor._ClientRequestInfoLocalBase;

public class ClientRequestInfoLocalTie
extends _ClientRequestInfoLocalBase {
    private static final long serialVersionUID = 1L;
    private ClientRequestInfoOperations _delegate;

    public ClientRequestInfoLocalTie(ClientRequestInfoOperations delegate) {
        this._delegate = delegate;
    }

    public ClientRequestInfoOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ClientRequestInfoOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public String received_exception_id() {
        return this._delegate.received_exception_id();
    }

    @Override
    public TaggedComponent get_effective_component(int id) {
        return this._delegate.get_effective_component(id);
    }

    @Override
    public Object effective_target() {
        return this._delegate.effective_target();
    }

    @Override
    public Object target() {
        return this._delegate.target();
    }

    @Override
    public ServiceContext get_request_service_context(int id) {
        return this._delegate.get_request_service_context(id);
    }

    @Override
    public Any get_slot(int id) throws InvalidSlot {
        return this._delegate.get_slot(id);
    }

    @Override
    public String[] contexts() {
        return this._delegate.contexts();
    }

    @Override
    public Any received_exception() {
        return this._delegate.received_exception();
    }

    @Override
    public void add_request_service_context(ServiceContext service_context, boolean replace) {
        this._delegate.add_request_service_context(service_context, replace);
    }

    @Override
    public Policy get_request_policy(int type) {
        return this._delegate.get_request_policy(type);
    }

    @Override
    public ServiceContext get_reply_service_context(int id) {
        return this._delegate.get_reply_service_context(id);
    }

    @Override
    public int request_id() {
        return this._delegate.request_id();
    }

    @Override
    public Parameter[] arguments() {
        return this._delegate.arguments();
    }

    @Override
    public Any result() {
        return this._delegate.result();
    }

    @Override
    public boolean response_expected() {
        return this._delegate.response_expected();
    }

    @Override
    public short sync_scope() {
        return this._delegate.sync_scope();
    }

    @Override
    public short reply_status() {
        return this._delegate.reply_status();
    }

    @Override
    public String[] operation_context() {
        return this._delegate.operation_context();
    }

    @Override
    public TypeCode[] exceptions() {
        return this._delegate.exceptions();
    }

    @Override
    public TaggedComponent[] get_effective_components(int id) {
        return this._delegate.get_effective_components(id);
    }

    @Override
    public String operation() {
        return this._delegate.operation();
    }

    @Override
    public TaggedProfile effective_profile() {
        return this._delegate.effective_profile();
    }

    @Override
    public Object forward_reference() {
        return this._delegate.forward_reference();
    }
}

