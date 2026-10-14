/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import org.apache.activemq.command.Message;
import org.apache.activemq.network.NetworkBridge;

public interface NetworkBridgeListener {
    public void bridgeFailed();

    public void onStart(NetworkBridge var1);

    public void onStop(NetworkBridge var1);

    public void onOutboundMessage(NetworkBridge var1, Message var2);

    public void onInboundMessage(NetworkBridge var1, Message var2);
}

