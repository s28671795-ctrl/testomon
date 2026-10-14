/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Object;
import org.omg.IOP.CodecFactory;
import org.omg.PortableInterceptor.ClientRequestInterceptor;
import org.omg.PortableInterceptor.IORInterceptor;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import org.omg.PortableInterceptor.ORBInitInfoPackage.InvalidName;
import org.omg.PortableInterceptor.PolicyFactory;
import org.omg.PortableInterceptor.ServerRequestInterceptor;

public interface ORBInitInfoOperations {
    public String[] arguments();

    public String orb_id();

    public CodecFactory codec_factory();

    public void register_initial_reference(String var1, Object var2) throws InvalidName;

    public Object resolve_initial_references(String var1) throws InvalidName;

    public void add_client_request_interceptor(ClientRequestInterceptor var1) throws DuplicateName;

    public void add_server_request_interceptor(ServerRequestInterceptor var1) throws DuplicateName;

    public void add_ior_interceptor(IORInterceptor var1) throws DuplicateName;

    public int allocate_slot_id();

    public void register_policy_factory(int var1, PolicyFactory var2);
}

