/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.dispatch;

import net.engio.mbassy.bus.MessagePublication;
import net.engio.mbassy.dispatch.IHandlerInvocation;
import net.engio.mbassy.subscription.ISubscriptionContextAware;

public interface IMessageDispatcher
extends ISubscriptionContextAware {
    public void dispatch(MessagePublication var1, Object var2, Iterable var3);

    public IHandlerInvocation getInvocation();
}

