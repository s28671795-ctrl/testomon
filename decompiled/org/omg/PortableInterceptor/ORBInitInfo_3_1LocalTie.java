/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.IOP.CodecFactory;
import org.omg.PortableInterceptor.ClientRequestInterceptor;
import org.omg.PortableInterceptor.IORInterceptor;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import org.omg.PortableInterceptor.ORBInitInfoPackage.InvalidName;
import org.omg.PortableInterceptor.ORBInitInfo_3_1Operations;
import org.omg.PortableInterceptor.PolicyFactory;
import org.omg.PortableInterceptor.ServerRequestInterceptor;
import org.omg.PortableInterceptor._ORBInitInfo_3_1LocalBase;

public class ORBInitInfo_3_1LocalTie
extends _ORBInitInfo_3_1LocalBase {
    private static final long serialVersionUID = 1L;
    private ORBInitInfo_3_1Operations _delegate;

    public ORBInitInfo_3_1LocalTie(ORBInitInfo_3_1Operations delegate) {
        this._delegate = delegate;
    }

    public ORBInitInfo_3_1Operations _delegate() {
        return this._delegate;
    }

    public void _delegate(ORBInitInfo_3_1Operations delegate) {
        this._delegate = delegate;
    }

    @Override
    public Object resolve_initial_references(String id) throws InvalidName {
        return this._delegate.resolve_initial_references(id);
    }

    @Override
    public void add_client_request_interceptor(ClientRequestInterceptor interceptor) throws DuplicateName {
        this._delegate.add_client_request_interceptor(interceptor);
    }

    @Override
    public void add_ior_interceptor(IORInterceptor interceptor) throws DuplicateName {
        this._delegate.add_ior_interceptor(interceptor);
    }

    @Override
    public String orb_id() {
        return this._delegate.orb_id();
    }

    @Override
    public CodecFactory codec_factory() {
        return this._delegate.codec_factory();
    }

    @Override
    public void add_client_request_interceptor_with_policy(ClientRequestInterceptor interceptor, Policy[] policies) throws DuplicateName, PolicyError {
        this._delegate.add_client_request_interceptor_with_policy(interceptor, policies);
    }

    @Override
    public String[] arguments() {
        return this._delegate.arguments();
    }

    @Override
    public int allocate_slot_id() {
        return this._delegate.allocate_slot_id();
    }

    @Override
    public void add_server_request_interceptor_with_policy(ServerRequestInterceptor interceptor, Policy[] policies) throws DuplicateName, PolicyError {
        this._delegate.add_server_request_interceptor_with_policy(interceptor, policies);
    }

    @Override
    public void add_server_request_interceptor(ServerRequestInterceptor interceptor) throws DuplicateName {
        this._delegate.add_server_request_interceptor(interceptor);
    }

    @Override
    public void add_ior_interceptor_with_policy(IORInterceptor interceptor, Policy[] policies) throws DuplicateName, PolicyError {
        this._delegate.add_ior_interceptor_with_policy(interceptor, policies);
    }

    @Override
    public void register_policy_factory(int type, PolicyFactory policy_factory) {
        this._delegate.register_policy_factory(type, policy_factory);
    }

    @Override
    public void register_initial_reference(String id, Object obj) throws InvalidName {
        this._delegate.register_initial_reference(id, obj);
    }
}

