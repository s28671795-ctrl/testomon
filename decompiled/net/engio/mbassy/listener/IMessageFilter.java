/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.listener;

import net.engio.mbassy.subscription.SubscriptionContext;

public interface IMessageFilter<M> {
    public boolean accepts(M var1, SubscriptionContext var2);
}

