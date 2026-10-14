/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.io.Inet;
import akka.io.Tcp;

public final class TcpSO$
implements Inet.SoJavaFactories {
    public static final TcpSO$ MODULE$ = new TcpSO$();

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

    public Tcp.SO.KeepAlive keepAlive(boolean on) {
        return new Tcp.SO.KeepAlive(on);
    }

    public Tcp.SO.OOBInline oobInline(boolean on) {
        return new Tcp.SO.OOBInline(on);
    }

    public Tcp.SO.TcpNoDelay tcpNoDelay(boolean on) {
        return new Tcp.SO.TcpNoDelay(on);
    }

    private TcpSO$() {
    }
}

