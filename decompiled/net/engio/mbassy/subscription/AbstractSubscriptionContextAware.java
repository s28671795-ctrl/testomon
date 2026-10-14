/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.subscription;

import net.engio.mbassy.subscription.ISubscriptionContextAware;
import net.engio.mbassy.subscription.SubscriptionContext;

public class AbstractSubscriptionContextAware
implements ISubscriptionContextAware {
    private final SubscriptionContext context;

    public AbstractSubscriptionContextAware(SubscriptionContext context) {
        this.context = context;
    }

    @Override
    public final SubscriptionContext getContext() {
        return this.context;
    }
}

