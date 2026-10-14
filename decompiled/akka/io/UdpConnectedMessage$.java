/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.io.Inet;
import akka.io.UdpConnected;
import akka.io.UdpConnected$Disconnect$;
import akka.io.UdpConnected$NoAck$;
import akka.io.UdpConnected$ResumeReading$;
import akka.io.UdpConnected$Send$;
import akka.io.UdpConnected$SuspendReading$;
import akka.util.ByteString;
import akka.util.ccompat.package$JavaConverters$;
import java.net.InetSocketAddress;
import scala.None$;
import scala.Some;
import scala.collection.IterableFactory$;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Iterable$;
import scala.collection.immutable.Nil$;

public final class UdpConnectedMessage$ {
    public static final UdpConnectedMessage$ MODULE$ = new UdpConnectedMessage$();

    public UdpConnected.Command connect(ActorRef handler, InetSocketAddress remoteAddress, InetSocketAddress localAddress, java.lang.Iterable<Inet.SocketOption> options) {
        return new UdpConnected.Connect(handler, remoteAddress, new Some<InetSocketAddress>(localAddress), this.fromJava(options));
    }

    public UdpConnected.Command connect(ActorRef handler, InetSocketAddress remoteAddress, java.lang.Iterable<Inet.SocketOption> options) {
        return new UdpConnected.Connect(handler, remoteAddress, None$.MODULE$, this.fromJava(options));
    }

    public UdpConnected.Command connect(ActorRef handler, InetSocketAddress remoteAddress) {
        return new UdpConnected.Connect(handler, remoteAddress, None$.MODULE$, Nil$.MODULE$);
    }

    public UdpConnected.Command send(ByteString data, Object ack) {
        return new UdpConnected.Send(data, ack);
    }

    public UdpConnected.Command send(ByteString data) {
        return UdpConnected$Send$.MODULE$.apply(data);
    }

    public UdpConnected.Command disconnect() {
        return UdpConnected$Disconnect$.MODULE$;
    }

    public UdpConnected.NoAck noAck(Object token) {
        return new UdpConnected.NoAck(token);
    }

    public UdpConnected.NoAck noAck() {
        return UdpConnected$NoAck$.MODULE$;
    }

    public UdpConnected.Command suspendReading() {
        return UdpConnected$SuspendReading$.MODULE$;
    }

    public UdpConnected.Command resumeReading() {
        return UdpConnected$ResumeReading$.MODULE$;
    }

    private <T> Iterable<T> fromJava(java.lang.Iterable<T> coll) {
        return package$JavaConverters$.MODULE$.IterableHasAsScala(coll).asScala().to(IterableFactory$.MODULE$.toFactory(Iterable$.MODULE$));
    }

    private UdpConnectedMessage$() {
    }
}

