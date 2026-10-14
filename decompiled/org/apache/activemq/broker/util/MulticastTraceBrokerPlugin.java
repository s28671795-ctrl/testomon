/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.activemq.broker.util.UDPTraceBrokerPlugin;

public class MulticastTraceBrokerPlugin
extends UDPTraceBrokerPlugin {
    private int timeToLive = 1;

    public MulticastTraceBrokerPlugin() {
        try {
            this.destination = new URI("multicast://224.1.2.3:61616");
        }
        catch (URISyntaxException uRISyntaxException) {
            // empty catch block
        }
    }

    @Override
    protected DatagramSocket createSocket() throws IOException {
        MulticastSocket s2 = new MulticastSocket();
        s2.setSendBufferSize(this.maxTraceDatagramSize);
        s2.setBroadcast(this.broadcast);
        s2.setLoopbackMode(true);
        s2.setTimeToLive(this.timeToLive);
        return s2;
    }

    public int getTimeToLive() {
        return this.timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }
}

