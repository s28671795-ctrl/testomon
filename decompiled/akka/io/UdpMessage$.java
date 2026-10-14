/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.io.Inet;
import akka.io.Udp;
import akka.io.Udp$NoAck$;
import akka.io.Udp$ResumeReading$;
import akka.io.Udp$Send$;
import akka.io.Udp$SimpleSender$;
import akka.io.Udp$SuspendReading$;
import akka.io.Udp$Unbind$;
import akka.util.ByteString;
import akka.util.ccompat.package$JavaConverters$;
import java.net.InetSocketAddress;
import scala.collection.IterableFactory$;
import scala.collection.immutable.IndexedSeq$;
import scala.collection.immutable.Nil$;

public final class UdpMessage$ {
    public static final UdpMessage$ MODULE$ = new UdpMessage$();

    public Udp.NoAck noAck(Object token) {
        return new Udp.NoAck(token);
    }

    public Udp.NoAck noAck() {
        return Udp$NoAck$.MODULE$;
    }

    public Udp.Command send(ByteString payload, InetSocketAddress target, Udp.Event ack) {
        return new Udp.Send(payload, target, ack);
    }

    public Udp.Command send(ByteString payload, InetSocketAddress target) {
        return Udp$Send$.MODULE$.apply(payload, target);
    }

    public Udp.Command bind(ActorRef handler, InetSocketAddress endpoint, Iterable<Inet.SocketOption> options) {
        return new Udp.Bind(handler, endpoint, package$JavaConverters$.MODULE$.IterableHasAsScala(options).asScala().to(IterableFactory$.MODULE$.toFactory(IndexedSeq$.MODULE$)));
    }

    public Udp.Command bind(ActorRef handler, InetSocketAddress endpoint) {
        return new Udp.Bind(handler, endpoint, Nil$.MODULE$);
    }

    public Udp.Command unbind() {
        return Udp$Unbind$.MODULE$;
    }

    public Udp.Command simpleSender(Iterable<Inet.SocketOption> options) {
        return new Udp.SimpleSender(package$JavaConverters$.MODULE$.IterableHasAsScala(options).asScala().to(IterableFactory$.MODULE$.toFactory(IndexedSeq$.MODULE$)));
    }

    public Udp.Command simpleSender() {
        return Udp$SimpleSender$.MODULE$;
    }

    public Udp.Command suspendReading() {
        return Udp$SuspendReading$.MODULE$;
    }

    public Udp.Command resumeReading() {
        return Udp$ResumeReading$.MODULE$;
    }

    private UdpMessage$() {
    }
}

