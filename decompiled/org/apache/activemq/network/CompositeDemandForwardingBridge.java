/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import org.apache.activemq.command.Command;
import org.apache.activemq.network.DemandForwardingBridgeSupport;
import org.apache.activemq.network.NetworkBridgeConfiguration;
import org.apache.activemq.transport.Transport;

public class CompositeDemandForwardingBridge
extends DemandForwardingBridgeSupport {
    public CompositeDemandForwardingBridge(NetworkBridgeConfiguration configuration, Transport localBroker, Transport remoteBroker) {
        super(configuration, localBroker, remoteBroker);
        this.remoteBrokerName = remoteBroker.toString();
    }

    protected void serviceLocalBrokerInfo(Command command) throws InterruptedException {
    }
}

