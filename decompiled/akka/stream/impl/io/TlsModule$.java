/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.actor.ActorSystem;
import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.BidiShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.Shape;
import akka.stream.TLSClosing;
import akka.stream.TLSProtocol;
import akka.stream.impl.io.TlsModule;
import akka.util.ByteString;
import java.io.Serializable;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple9;
import scala.runtime.BoxedUnit;
import scala.runtime.ModuleSerializationProxy;
import scala.util.Try;

@InternalApi
public final class TlsModule$
implements Serializable {
    public static final TlsModule$ MODULE$ = new TlsModule$();

    public TlsModule apply(Attributes attributes, Function1<ActorSystem, SSLEngine> createSSLEngine, Function2<ActorSystem, SSLSession, Try<BoxedUnit>> verifySession, TLSClosing closing2) {
        String name = attributes.nameOrDefault("StreamTls()");
        Inlet<ByteString> cipherIn = Inlet$.MODULE$.apply(new StringBuilder(9).append(name).append(".cipherIn").toString());
        Outlet<ByteString> cipherOut = Outlet$.MODULE$.apply(new StringBuilder(10).append(name).append(".cipherOut").toString());
        Inlet<TLSProtocol.SslTlsOutbound> plainIn = Inlet$.MODULE$.apply(new StringBuilder(12).append(name).append(".transportIn").toString());
        Outlet<TLSProtocol.SslTlsInbound> plainOut = Outlet$.MODULE$.apply(new StringBuilder(13).append(name).append(".transportOut").toString());
        BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound> shape = new BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound>(plainIn, cipherOut, cipherIn, plainOut);
        return new TlsModule(plainIn, plainOut, cipherIn, cipherOut, shape, attributes, createSSLEngine, verifySession, closing2);
    }

    public TlsModule apply(Inlet<TLSProtocol.SslTlsOutbound> plainIn, Outlet<TLSProtocol.SslTlsInbound> plainOut, Inlet<ByteString> cipherIn, Outlet<ByteString> cipherOut, BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound> shape, Attributes attributes, Function1<ActorSystem, SSLEngine> createSSLEngine, Function2<ActorSystem, SSLSession, Try<BoxedUnit>> verifySession, TLSClosing closing2) {
        return new TlsModule(plainIn, plainOut, cipherIn, cipherOut, shape, attributes, createSSLEngine, verifySession, closing2);
    }

    public Option<Tuple9<Inlet<TLSProtocol.SslTlsOutbound>, Outlet<TLSProtocol.SslTlsInbound>, Inlet<ByteString>, Outlet<ByteString>, BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound>, Attributes, Function1<ActorSystem, SSLEngine>, Function2<ActorSystem, SSLSession, Try<BoxedUnit>>, TLSClosing>> unapply(TlsModule x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple9<Inlet<TLSProtocol.SslTlsOutbound>, Outlet<TLSProtocol.SslTlsInbound>, Inlet<ByteString>, Outlet<ByteString>, BidiShape<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound>, Attributes, Function1<ActorSystem, SSLEngine>, Function2<ActorSystem, SSLSession, Try<BoxedUnit>>, TLSClosing>>(new Tuple9<Inlet<TLSProtocol.SslTlsOutbound>, Outlet<TLSProtocol.SslTlsInbound>, Inlet<ByteString>, Outlet<ByteString>, Shape, Attributes, Function1<ActorSystem, SSLEngine>, Function2<ActorSystem, SSLSession, Try<BoxedUnit>>, TLSClosing>(x$0.plainIn(), x$0.plainOut(), x$0.cipherIn(), x$0.cipherOut(), x$0.shape(), x$0.attributes(), x$0.createSSLEngine(), x$0.verifySession(), x$0.closing()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TlsModule$.class);
    }

    private TlsModule$() {
    }
}

