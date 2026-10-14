/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import org.apache.activemq.network.DemandForwardingBridge;
import org.apache.activemq.network.NetworkBridgeConfiguration;
import org.apache.activemq.network.NetworkBridgeListener;
import org.apache.activemq.transport.Transport;

public interface BridgeFactory {
    public DemandForwardingBridge createNetworkBridge(NetworkBridgeConfiguration var1, Transport var2, Transport var3, NetworkBridgeListener var4);
}

