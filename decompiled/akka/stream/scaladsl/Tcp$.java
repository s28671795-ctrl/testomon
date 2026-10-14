/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.annotation.InternalApi;
import akka.stream.Graph;
import akka.stream.TLSProtocol;
import akka.stream.scaladsl.BidiFlow;
import akka.stream.scaladsl.BidiFlow$;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.Tcp;
import akka.util.ByteString;
import java.io.Serializable;
import scala.Function1;

public final class Tcp$
implements ExtensionId<Tcp>,
ExtensionIdProvider {
    public static final Tcp$ MODULE$ = new Tcp$();
    private static final BidiFlow<ByteString, TLSProtocol.SendBytes, TLSProtocol.SslTlsInbound, ByteString, NotUsed> akka$stream$scaladsl$Tcp$$tlsWrapping;
    @InternalApi
    private static final int defaultBacklog;

    static {
        ExtensionId.$init$(MODULE$);
        akka$stream$scaladsl$Tcp$$tlsWrapping = BidiFlow$.MODULE$.fromFlows((Graph)((Object)Flow$.MODULE$.apply().map((Function1<ByteString, TLSProtocol.SendBytes> & Serializable)bytes -> new TLSProtocol.SendBytes((ByteString)bytes))), (Graph)((Object)Flow$.MODULE$.apply().collect(new Serializable(){
            private static final long serialVersionUID = 0L;

            public final <A1 extends TLSProtocol.SslTlsInbound, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof TLSProtocol.SessionBytes) {
                    TLSProtocol.SessionBytes sessionBytes = (TLSProtocol.SessionBytes)A1;
                    return (B1)sessionBytes.bytes();
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(TLSProtocol.SslTlsInbound x1) {
                TLSProtocol.SslTlsInbound sslTlsInbound = x1;
                return sslTlsInbound instanceof TLSProtocol.SessionBytes;
            }
        })));
        defaultBacklog = 100;
    }

    @Override
    public Extension apply(ClassicActorSystemProvider system) {
        return ExtensionId.apply$((ExtensionId)this, system);
    }

    @Override
    public final int hashCode() {
        return ExtensionId.hashCode$(this);
    }

    @Override
    public final boolean equals(Object other) {
        return ExtensionId.equals$(this, other);
    }

    @Override
    public Tcp apply(ActorSystem system) {
        return (Tcp)ExtensionId.apply$((ExtensionId)this, system);
    }

    @Override
    public Tcp get(ActorSystem system) {
        return (Tcp)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public Tcp get(ClassicActorSystemProvider system) {
        return (Tcp)ExtensionId.get$((ExtensionId)this, system);
    }

    public Tcp$ lookup() {
        return this;
    }

    @Override
    public Tcp createExtension(ExtendedActorSystem system) {
        return new Tcp(system);
    }

    public BidiFlow<ByteString, TLSProtocol.SendBytes, TLSProtocol.SslTlsInbound, ByteString, NotUsed> akka$stream$scaladsl$Tcp$$tlsWrapping() {
        return akka$stream$scaladsl$Tcp$$tlsWrapping;
    }

    public int defaultBacklog() {
        return defaultBacklog;
    }

    private Tcp$() {
    }
}

