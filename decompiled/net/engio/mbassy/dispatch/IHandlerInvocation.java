/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.dispatch;

import net.engio.mbassy.bus.MessagePublication;
import net.engio.mbassy.subscription.ISubscriptionContextAware;

public interface IHandlerInvocation<HANDLER, MESSAGE>
extends ISubscriptionContextAware {
    public void invoke(HANDLER var1, MESSAGE var2, MessagePublication var3);
}

