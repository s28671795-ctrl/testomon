/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.subscription;

import net.engio.mbassy.bus.BusRuntime;
import net.engio.mbassy.listener.MetadataReader;
import net.engio.mbassy.subscription.ISubscriptionManagerProvider;
import net.engio.mbassy.subscription.SubscriptionFactory;
import net.engio.mbassy.subscription.SubscriptionManager;

public class SubscriptionManagerProvider
implements ISubscriptionManagerProvider {
    @Override
    public SubscriptionManager createManager(MetadataReader reader, SubscriptionFactory factory2, BusRuntime runtime) {
        return new SubscriptionManager(reader, factory2, runtime);
    }
}

