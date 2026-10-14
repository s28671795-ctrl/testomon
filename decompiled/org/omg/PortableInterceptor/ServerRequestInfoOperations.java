/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Any;
import org.omg.CORBA.Policy;
import org.omg.IOP.ServiceContext;
import org.omg.PortableInterceptor.InvalidSlot;
import org.omg.PortableInterceptor.RequestInfoOperations;

public interface ServerRequestInfoOperations
extends RequestInfoOperations {
    public Any sending_exception();

    public String server_id();

    public String orb_id();

    public String[] adapter_name();

    public byte[] object_id();

    public byte[] adapter_id();

    public String target_most_derived_interface();

    public Policy get_server_policy(int var1);

    public void set_slot(int var1, Any var2) throws InvalidSlot;

    public boolean target_is_a(String var1);

    public void add_reply_service_context(ServiceContext var1, boolean var2);
}

