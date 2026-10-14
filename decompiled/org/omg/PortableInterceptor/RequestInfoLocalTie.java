/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.Dynamic.Parameter;
import org.omg.IOP.ServiceContext;
import org.omg.PortableInterceptor.InvalidSlot;
import org.omg.PortableInterceptor.RequestInfoOperations;
import org.omg.PortableInterceptor._RequestInfoLocalBase;

public class RequestInfoLocalTie
extends _RequestInfoLocalBase {
    private static final long serialVersionUID = 1L;
    private RequestInfoOperations _delegate;

    public RequestInfoLocalTie(RequestInfoOperations delegate) {
        this._delegate = delegate;
    }

    public RequestInfoOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(RequestInfoOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public ServiceContext get_request_service_context(int id) {
        return this._delegate.get_request_service_context(id);
    }

    @Override
    public ServiceContext get_reply_service_context(int id) {
        return this._delegate.get_reply_service_context(id);
    }

    @Override
    public Object forward_reference() {
        return this._delegate.forward_reference();
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
    public short sync_scope() {
        return this._delegate.sync_scope();
    }

    @Override
    public short reply_status() {
        return this._delegate.reply_status();
    }

    @Override
    public boolean response_expected() {
        return this._delegate.response_expected();
    }

    @Override
    public String[] contexts() {
        return this._delegate.contexts();
    }

    @Override
    public String operation() {
        return this._delegate.operation();
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
    public Any get_slot(int id) throws InvalidSlot {
        return this._delegate.get_slot(id);
    }

    @Override
    public int request_id() {
        return this._delegate.request_id();
    }
}

