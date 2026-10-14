/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.PortableInterceptor.IORInfo;
import org.omg.PortableInterceptor.IORInterceptor_3_0Operations;
import org.omg.PortableInterceptor.ObjectReferenceTemplate;
import org.omg.PortableInterceptor._IORInterceptor_3_0LocalBase;

public class IORInterceptor_3_0LocalTie
extends _IORInterceptor_3_0LocalBase {
    private static final long serialVersionUID = 1L;
    private IORInterceptor_3_0Operations _delegate;

    public IORInterceptor_3_0LocalTie(IORInterceptor_3_0Operations delegate) {
        this._delegate = delegate;
    }

    public IORInterceptor_3_0Operations _delegate() {
        return this._delegate;
    }

    public void _delegate(IORInterceptor_3_0Operations delegate) {
        this._delegate = delegate;
    }

    @Override
    public String name() {
        return this._delegate.name();
    }

    @Override
    public void adapter_manager_state_changed(String id, short state) {
        this._delegate.adapter_manager_state_changed(id, state);
    }

    @Override
    public void establish_components(IORInfo info) {
        this._delegate.establish_components(info);
    }

    @Override
    public void components_established(IORInfo info) {
        this._delegate.components_established(info);
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }

    @Override
    public void adapter_state_changed(ObjectReferenceTemplate[] templates, short state) {
        this._delegate.adapter_state_changed(templates, state);
    }
}

