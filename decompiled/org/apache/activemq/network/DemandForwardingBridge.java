/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import org.apache.activemq.network.DemandForwardingBridgeSupport;
import org.apache.activemq.network.NetworkBridgeConfiguration;
import org.apache.activemq.transport.Transport;

public class DemandForwardingBridge
extends DemandForwardingBridgeSupport {
    public DemandForwardingBridge(NetworkBridgeConfiguration configuration, Transport localBroker, Transport remoteBroker) {
        super(configuration, localBroker, remoteBroker);
    }
}

