/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.io.Inet;
import akka.io.Tcp;
import akka.io.Tcp$Abort$;
import akka.io.Tcp$Bind$;
import akka.io.Tcp$Close$;
import akka.io.Tcp$ConfirmedClose$;
import akka.io.Tcp$NoAck$;
import akka.io.Tcp$Register$;
import akka.io.Tcp$ResumeReading$;
import akka.io.Tcp$ResumeWriting$;
import akka.io.Tcp$SuspendReading$;
import akka.io.Tcp$Unbind$;
import akka.io.Tcp$Write$;
import akka.japi.Util$;
import akka.util.ByteString;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import scala.None$;
import scala.Option$;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Nil$;
import scala.concurrent.duration.FiniteDuration;

public final class TcpMessage$ {
    public static final TcpMessage$ MODULE$ = new TcpMessage$();

    public Tcp.Command connect(InetSocketAddress remoteAddress, InetSocketAddress localAddress, java.lang.Iterable<Inet.SocketOption> options, FiniteDuration timeout2, boolean pullMode) {
        return new Tcp.Connect(remoteAddress, Option$.MODULE$.apply(localAddress), this.fromJava(options), Option$.MODULE$.apply(timeout2), pullMode);
    }

    public Tcp.Command connect(InetSocketAddress remoteAddress, InetSocketAddress localAddress, java.lang.Iterable<Inet.SocketOption> options, Duration timeout2, boolean pullMode) {
        return this.connect(remoteAddress, localAddress, options, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2)), pullMode);
    }

    public Tcp.Command connect(InetSocketAddress remoteAddress) {
        return new Tcp.Connect(remoteAddress, None$.MODULE$, Nil$.MODULE$, None$.MODULE$, false);
    }

    public Tcp.Command bind(ActorRef handler, InetSocketAddress endpoint, int backlog, java.lang.Iterable<Inet.SocketOption> options, boolean pullMode) {
        return new Tcp.Bind(handler, endpoint, backlog, this.fromJava(options), pullMode);
    }

    public Tcp.Command bind(ActorRef handler, InetSocketAddress endpoint, int backlog) {
        return new Tcp.Bind(handler, endpoint, backlog, Nil$.MODULE$, Tcp$Bind$.MODULE$.apply$default$5());
    }

    public Tcp.Command register(ActorRef handler, boolean keepOpenOnPeerClosed, boolean useResumeWriting) {
        return new Tcp.Register(handler, keepOpenOnPeerClosed, useResumeWriting);
    }

    public Tcp.Command register(ActorRef handler) {
        return new Tcp.Register(handler, Tcp$Register$.MODULE$.apply$default$2(), Tcp$Register$.MODULE$.apply$default$3());
    }

    public Tcp.Command unbind() {
        return Tcp$Unbind$.MODULE$;
    }

    public Tcp.Command close() {
        return Tcp$Close$.MODULE$;
    }

    public Tcp.Command confirmedClose() {
        return Tcp$ConfirmedClose$.MODULE$;
    }

    public Tcp.Command abort() {
        return Tcp$Abort$.MODULE$;
    }

    public Tcp.NoAck noAck(Object token) {
        return new Tcp.NoAck(token);
    }

    public Tcp.NoAck noAck() {
        return Tcp$NoAck$.MODULE$;
    }

    public Tcp.Command write(ByteString data, Tcp.Event ack) {
        return new Tcp.Write(data, ack);
    }

    public Tcp.Command write(ByteString data) {
        return Tcp$Write$.MODULE$.apply(data);
    }

    public Tcp.Command writeFile(String filePath, long position, long count, Tcp.Event ack) {
        return new Tcp.WritePath(Paths.get(filePath, new String[0]), position, count, ack);
    }

    public Tcp.Command writePath(Path filePath, long position, long count, Tcp.Event ack) {
        return new Tcp.WritePath(filePath, position, count, ack);
    }

    public Tcp.Command resumeWriting() {
        return Tcp$ResumeWriting$.MODULE$;
    }

    public Tcp.Command suspendReading() {
        return Tcp$SuspendReading$.MODULE$;
    }

    public Tcp.Command resumeReading() {
        return Tcp$ResumeReading$.MODULE$;
    }

    public Tcp.Command resumeAccepting(int batchSize) {
        return new Tcp.ResumeAccepting(batchSize);
    }

    private <T> Iterable<T> fromJava(java.lang.Iterable<T> coll) {
        return Util$.MODULE$.immutableSeq(coll);
    }

    private TcpMessage$() {
    }
}

