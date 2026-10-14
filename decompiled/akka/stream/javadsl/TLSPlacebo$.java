/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.TLSProtocol;
import akka.stream.javadsl.BidiFlow;
import akka.util.ByteString;

public final class TLSPlacebo$ {
    public static final TLSPlacebo$ MODULE$ = new TLSPlacebo$();
    private static final BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SessionBytes, NotUsed> forJava = new BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SessionBytes, NotUsed>(akka.stream.scaladsl.TLSPlacebo$.MODULE$.apply());

    public BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SessionBytes, NotUsed> getInstance() {
        return this.forJava();
    }

    private BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SessionBytes, NotUsed> forJava() {
        return forJava;
    }

    private TLSPlacebo$() {
    }
}

