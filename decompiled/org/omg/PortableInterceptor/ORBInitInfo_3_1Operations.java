/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.PortableInterceptor.ClientRequestInterceptor;
import org.omg.PortableInterceptor.IORInterceptor;
import org.omg.PortableInterceptor.ORBInitInfoOperations;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import org.omg.PortableInterceptor.ServerRequestInterceptor;

public interface ORBInitInfo_3_1Operations
extends ORBInitInfoOperations {
    public void add_client_request_interceptor_with_policy(ClientRequestInterceptor var1, Policy[] var2) throws DuplicateName, PolicyError;

    public void add_server_request_interceptor_with_policy(ServerRequestInterceptor var1, Policy[] var2) throws DuplicateName, PolicyError;

    public void add_ior_interceptor_with_policy(IORInterceptor var1, Policy[] var2) throws DuplicateName, PolicyError;
}

