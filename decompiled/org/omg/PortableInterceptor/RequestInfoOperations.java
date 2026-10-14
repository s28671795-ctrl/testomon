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

public interface RequestInfoOperations {
    public int request_id();

    public String operation();

    public Parameter[] arguments();

    public TypeCode[] exceptions();

    public String[] contexts();

    public String[] operation_context();

    public Any result();

    public boolean response_expected();

    public short sync_scope();

    public short reply_status();

    public Object forward_reference();

    public Any get_slot(int var1) throws InvalidSlot;

    public ServiceContext get_request_service_context(int var1);

    public ServiceContext get_reply_service_context(int var1);
}

