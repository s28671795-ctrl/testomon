/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.NetworkBridgeFilter;

public interface NetworkBridgeFilterFactory {
    public NetworkBridgeFilter create(ConsumerInfo var1, BrokerId[] var2, int var3, int var4);
}

