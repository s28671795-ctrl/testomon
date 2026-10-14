/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.dispatch;

import net.engio.mbassy.dispatch.IHandlerInvocation;
import net.engio.mbassy.dispatch.IMessageDispatcher;
import net.engio.mbassy.subscription.AbstractSubscriptionContextAware;

public abstract class DelegatingMessageDispatcher
extends AbstractSubscriptionContextAware
implements IMessageDispatcher {
    private final IMessageDispatcher delegate;

    public DelegatingMessageDispatcher(IMessageDispatcher delegate) {
        super(delegate.getContext());
        this.delegate = delegate;
    }

    protected IMessageDispatcher getDelegate() {
        return this.delegate;
    }

    @Override
    public IHandlerInvocation getInvocation() {
        return this.delegate.getInvocation();
    }
}

