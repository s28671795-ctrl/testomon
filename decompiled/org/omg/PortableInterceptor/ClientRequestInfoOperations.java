/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.IOP.ServiceContext;
import org.omg.IOP.TaggedComponent;
import org.omg.IOP.TaggedProfile;
import org.omg.PortableInterceptor.RequestInfoOperations;

public interface ClientRequestInfoOperations
extends RequestInfoOperations {
    public Object target();

    public Object effective_target();

    public TaggedProfile effective_profile();

    public Any received_exception();

    public String received_exception_id();

    public TaggedComponent get_effective_component(int var1);

    public TaggedComponent[] get_effective_components(int var1);

    public Policy get_request_policy(int var1);

    public void add_request_service_context(ServiceContext var1, boolean var2);
}

