/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Policy;
import org.omg.PortableInterceptor.ProcessingModePolicyOperations;
import org.omg.PortableInterceptor._ProcessingModePolicyLocalBase;

public class ProcessingModePolicyLocalTie
extends _ProcessingModePolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private ProcessingModePolicyOperations _delegate;

    public ProcessingModePolicyLocalTie(ProcessingModePolicyOperations delegate) {
        this._delegate = delegate;
    }

    public ProcessingModePolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ProcessingModePolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public short processing_mode() {
        return this._delegate.processing_mode();
    }

    @Override
    public int policy_type() {
        return this._delegate.policy_type();
    }

    @Override
    public Policy copy() {
        return this._delegate.copy();
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

