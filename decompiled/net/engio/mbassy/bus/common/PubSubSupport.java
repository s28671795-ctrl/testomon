/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus.common;

import net.engio.mbassy.bus.IMessagePublication;
import net.engio.mbassy.bus.common.RuntimeProvider;

public interface PubSubSupport<T>
extends RuntimeProvider {
    public void subscribe(Object var1);

    public boolean unsubscribe(Object var1);

    public IMessagePublication publish(T var1);
}

