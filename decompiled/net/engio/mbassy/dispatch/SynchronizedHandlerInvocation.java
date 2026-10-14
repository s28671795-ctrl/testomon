/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.dispatch;

import net.engio.mbassy.bus.MessagePublication;
import net.engio.mbassy.dispatch.IHandlerInvocation;
import net.engio.mbassy.subscription.AbstractSubscriptionContextAware;

public class SynchronizedHandlerInvocation
extends AbstractSubscriptionContextAware
implements IHandlerInvocation<Object, Object> {
    private IHandlerInvocation delegate;

    public SynchronizedHandlerInvocation(IHandlerInvocation delegate) {
        super(delegate.getContext());
        this.delegate = delegate;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void invoke(Object listener, Object message, MessagePublication publication) {
        Object object = listener;
        synchronized (object) {
            this.delegate.invoke(listener, message, publication);
        }
    }
}

