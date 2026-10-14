/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Policy;
import org.omg.IOP.TaggedComponent;
import org.omg.PortableInterceptor.IORInfoOperations;
import org.omg.PortableInterceptor._IORInfoLocalBase;

public class IORInfoLocalTie
extends _IORInfoLocalBase {
    private static final long serialVersionUID = 1L;
    private IORInfoOperations _delegate;

    public IORInfoLocalTie(IORInfoOperations delegate) {
        this._delegate = delegate;
    }

    public IORInfoOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(IORInfoOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public void add_ior_component(TaggedComponent a_component) {
        this._delegate.add_ior_component(a_component);
    }

    @Override
    public Policy get_effective_policy(int type) {
        return this._delegate.get_effective_policy(type);
    }

    @Override
    public void add_ior_component_to_profile(TaggedComponent a_component, int profile_id) {
        this._delegate.add_ior_component_to_profile(a_component, profile_id);
    }
}

