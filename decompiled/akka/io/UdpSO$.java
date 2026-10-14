/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.io.Inet;
import akka.io.Udp;

public final class UdpSO$
implements Inet.SoJavaFactories {
    public static final UdpSO$ MODULE$ = new UdpSO$();

    static {
        Inet.SoJavaFactories.$init$(MODULE$);
    }

    @Override
    public Inet.SO.ReceiveBufferSize receiveBufferSize(int size) {
        return Inet.SoJavaFactories.receiveBufferSize$(this, size);
    }

    @Override
    public Inet.SO.ReuseAddress reuseAddress(boolean on) {
        return Inet.SoJavaFactories.reuseAddress$(this, on);
    }

    @Override
    public Inet.SO.SendBufferSize sendBufferSize(int size) {
        return Inet.SoJavaFactories.sendBufferSize$(this, size);
    }

    @Override
    public Inet.SO.TrafficClass trafficClass(int tc) {
        return Inet.SoJavaFactories.trafficClass$(this, tc);
    }

    public Udp.SO.Broadcast broadcast(boolean on) {
        return new Udp.SO.Broadcast(on);
    }

    private UdpSO$() {
    }
}

