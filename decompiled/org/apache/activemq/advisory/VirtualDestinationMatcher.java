/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.advisory;

import org.apache.activemq.broker.region.virtual.VirtualDestination;
import org.apache.activemq.command.ActiveMQDestination;

public interface VirtualDestinationMatcher {
    public boolean matches(VirtualDestination var1, ActiveMQDestination var2);
}

