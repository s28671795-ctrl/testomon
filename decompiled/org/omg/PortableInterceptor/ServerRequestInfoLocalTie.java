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
import org.omg.PortableInterceptor.InvalidSlot;
import org.omg.PortableInterceptor.ServerRequestInfoOperations;
import org.omg.PortableInterceptor._ServerRequestInfoLocalBase;

public class ServerRequestInfoLocalTie
extends _ServerRequestInfoLocalBase {
    private static final long serialVersionUID = 1L;
    private ServerRequestInfoOperations _delegate;

    public ServerRequestInfoLocalTie(ServerRequestInfoOperations delegate) {
        this._delegate = delegate;
    }

    public ServerRequestInfoOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ServerRequestInfoOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public void add_reply_service_context(ServiceContext service_context, boolean replace) {
        this._delegate.add_reply_service_context(service_context, replace);
    }

    @Override
    public boolean target_is_a(String id) {
        return this._delegate.target_is_a(id);
    }

    @Override
    public ServiceContext get_request_service_context(int id) {
        return this._delegate.get_request_service_context(id);
    }

    @Override
    public byte[] adapter_id() {
        return this._delegate.adapter_id();
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
    public String orb_id() {
        return this._delegate.orb_id();
    }

    @Override
    public Policy get_server_policy(int type) {
        return this._delegate.get_server_policy(type);
    }

    @Override
    public String server_id() {
        return this._delegate.server_id();
    }

    @Override
    public Any sending_exception() {
        return this._delegate.sending_exception();
    }

    @Override
    public String target_most_derived_interface() {
        return this._delegate.target_most_derived_interface();
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
    public void set_slot(int id, Any data) throws InvalidSlot {
        this._delegate.set_slot(id, data);
    }

    @Override
    public String[] adapter_name() {
        return this._delegate.adapter_name();
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
    public byte[] object_id() {
        return this._delegate.object_id();
    }

    @Override
    public String operation() {
        return this._delegate.operation();
    }

    @Override
    public Object forward_reference() {
        return this._delegate.forward_reference();
    }
}

