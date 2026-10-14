/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;

public interface DispatchSelector {
    public boolean canDispatch(Subscription var1, MessageReference var2) throws Exception;
}

