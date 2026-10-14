/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.subscription;

import net.engio.mbassy.bus.BusRuntime;
import net.engio.mbassy.listener.MetadataReader;
import net.engio.mbassy.subscription.SubscriptionFactory;
import net.engio.mbassy.subscription.SubscriptionManager;

public interface ISubscriptionManagerProvider {
    public SubscriptionManager createManager(MetadataReader var1, SubscriptionFactory var2, BusRuntime var3);
}

