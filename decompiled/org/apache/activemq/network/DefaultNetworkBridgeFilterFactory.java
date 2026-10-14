/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.NetworkBridgeFilter;
import org.apache.activemq.network.NetworkBridgeFilterFactory;

public class DefaultNetworkBridgeFilterFactory
implements NetworkBridgeFilterFactory {
    @Override
    public NetworkBridgeFilter create(ConsumerInfo info, BrokerId[] remoteBrokerPath, int messageTTL, int consumerTTL) {
        return new NetworkBridgeFilter(info, remoteBrokerPath[0], messageTTL, consumerTTL);
    }
}

