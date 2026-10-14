/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.io.Inet;
import akka.io.Tcp;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Materializer;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.io.OutgoingConnectionStage$;
import akka.stream.impl.io.TcpConnectionStage;
import akka.stream.scaladsl.Tcp;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.util.ByteString;
import java.io.Serializable;
import java.net.InetSocketAddress;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.immutable.Iterable;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005}f!B\r\u001b\u0001y\u0011\u0003\u0002C#\u0001\u0005\u0003\u0005\u000b\u0011B$\t\u00115\u0003!\u0011!Q\u0001\n9C\u0001B\u0016\u0001\u0003\u0002\u0003\u0006Ia\u0016\u0005\t7\u0002\u0011\t\u0011)A\u00059\"A1\u000f\u0001B\u0001B\u0003%A\u000f\u0003\u0005x\u0001\t\u0005\t\u0015!\u0003y\u0011\u0015q\b\u0001\"\u0001\u0000\u0011%\t\t\u0002\u0001b\u0001\n\u0003\t\u0019\u0002\u0003\u0005\u0002\u001c\u0001\u0001\u000b\u0011BA\u000b\u0011%\ti\u0002\u0001b\u0001\n\u0003\ty\u0002\u0003\u0005\u0002(\u0001\u0001\u000b\u0011BA\u0011\u0011\u001d\tI\u0003\u0001C!\u0003WA\u0011\"a\r\u0001\u0005\u0004%\t!!\u000e\t\u000f\u0005]\u0002\u0001)A\u0005S!9\u0011\u0011\b\u0001\u0005B\u0005m\u0002bBA\u001d\u0001\u0011\u0005\u00131\f\u0005\b\u0003W\u0002A\u0011IA7\u000f)\tIIGA\u0001\u0012\u0003q\u00121\u0012\u0004\n3i\t\t\u0011#\u0001\u001f\u0003\u001bCaA`\n\u0005\u0002\u0005U\u0005\"CAL'E\u0005I\u0011AAM\u0011%\tikEI\u0001\n\u0003\ty\u000bC\u0005\u00024N\t\n\u0011\"\u0001\u00026\"I\u0011\u0011X\n\u0012\u0002\u0013\u0005\u00111\u0018\u0002\u0018\u001fV$xm\\5oO\u000e{gN\\3di&|gn\u0015;bO\u0016T!a\u0007\u000f\u0002\u0005%|'BA\u000f\u001f\u0003\u0011IW\u000e\u001d7\u000b\u0005}\u0001\u0013AB:ue\u0016\fWNC\u0001\"\u0003\u0011\t7n[1\u0014\u0005\u0001\u0019\u0003\u0003\u0002\u0013(SMj\u0011!\n\u0006\u0003My\tQa\u001d;bO\u0016L!\u0001K\u0013\u0003?\u001d\u0013\u0018\r\u001d5Ti\u0006<WmV5uQ6\u000bG/\u001a:jC2L'0\u001a3WC2,X\r\u0005\u0003+W5jS\"\u0001\u0010\n\u00051r\"!\u0003$m_^\u001c\u0006.\u00199f!\tq\u0013'D\u00010\u0015\t\u0001\u0004%\u0001\u0003vi&d\u0017B\u0001\u001a0\u0005)\u0011\u0015\u0010^3TiJLgn\u001a\t\u0004ieZT\"A\u001b\u000b\u0005Y:\u0014AC2p]\u000e,(O]3oi*\t\u0001(A\u0003tG\u0006d\u0017-\u0003\u0002;k\t1a)\u001e;ve\u0016\u0004\"\u0001\u0010\"\u000f\u0005u\u0002U\"\u0001 \u000b\u0005}r\u0012\u0001C:dC2\fGm\u001d7\n\u0005\u0005s\u0014a\u0001+da&\u00111\t\u0012\u0002\u0013\u001fV$xm\\5oO\u000e{gN\\3di&|gN\u0003\u0002B}\u00059Q.\u00198bO\u0016\u00148\u0001\u0001\t\u0003\u0011.k\u0011!\u0013\u0006\u0003\u0015\u0002\nQ!Y2u_JL!\u0001T%\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\fQB]3n_R,\u0017\t\u001a3sKN\u001c\bCA(U\u001b\u0005\u0001&BA)S\u0003\rqW\r\u001e\u0006\u0002'\u0006!!.\u0019<b\u0013\t)\u0006KA\tJ]\u0016$8k\\2lKR\fE\r\u001a:fgN\fA\u0002\\8dC2\fE\r\u001a:fgN\u00042\u0001W-O\u001b\u00059\u0014B\u0001.8\u0005\u0019y\u0005\u000f^5p]\u00069q\u000e\u001d;j_:\u001c\bcA/cI6\taL\u0003\u0002`A\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003C^\n!bY8mY\u0016\u001cG/[8o\u0013\t\u0019gL\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\t)\u0007O\u0004\u0002g[:\u0011q\r\u001c\b\u0003Q.l\u0011!\u001b\u0006\u0003U\u001a\u000ba\u0001\u0010:p_Rt\u0014\"A\u0011\n\u0005m\u0001\u0013B\u00018p\u0003\u0011Ie.\u001a;\u000b\u0005m\u0001\u0013BA9s\u00051\u0019vnY6fi>\u0003H/[8o\u0015\tqw.A\u0005iC247\t\\8tKB\u0011\u0001,^\u0005\u0003m^\u0012qAQ8pY\u0016\fg.\u0001\bd_:tWm\u0019;US6,w.\u001e;\u0011\u0005edX\"\u0001>\u000b\u0005m,\u0014\u0001\u00033ve\u0006$\u0018n\u001c8\n\u0005uT(\u0001\u0003#ve\u0006$\u0018n\u001c8\u0002\rqJg.\u001b;?)9\t\t!!\u0002\u0002\b\u0005%\u00111BA\u0007\u0003\u001f\u00012!a\u0001\u0001\u001b\u0005Q\u0002\"B#\b\u0001\u00049\u0005\"B'\b\u0001\u0004q\u0005b\u0002,\b!\u0003\u0005\ra\u0016\u0005\b7\u001e\u0001\n\u00111\u0001]\u0011\u001d\u0019x\u0001%AA\u0002QDqa^\u0004\u0011\u0002\u0003\u0007\u00010A\u0004csR,7/\u00138\u0016\u0005\u0005U\u0001\u0003\u0002\u0016\u0002\u00185J1!!\u0007\u001f\u0005\u0015Ie\u000e\\3u\u0003!\u0011\u0017\u0010^3t\u0013:\u0004\u0013\u0001\u00032zi\u0016\u001cx*\u001e;\u0016\u0005\u0005\u0005\u0002\u0003\u0002\u0016\u0002$5J1!!\n\u001f\u0005\u0019yU\u000f\u001e7fi\u0006I!-\u001f;fg>+H\u000fI\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cXCAA\u0017!\rQ\u0013qF\u0005\u0004\u0003cq\"AC!uiJL'-\u001e;fg\u0006)1\u000f[1qKV\t\u0011&\u0001\u0004tQ\u0006\u0004X\rI\u0001 GJ,\u0017\r^3M_\u001eL7-\u00118e\u001b\u0006$XM]5bY&TX\r\u001a,bYV,G\u0003BA\u001f\u0003/\u0002r\u0001WA \u0003\u0007\nI%C\u0002\u0002B]\u0012a\u0001V;qY\u0016\u0014\u0004c\u0001\u0013\u0002F%\u0019\u0011qI\u0013\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000e\u0004B\u0001N\u001d\u0002LA\u0019\u0011Q\n\"\u000f\u0007\u0005=\u0003I\u0004\u0003\u0002R\u0005UcbA4\u0002T%\u0011q\u0004I\u0005\u0003\u007fyAq!!\u0017\u0010\u0001\u0004\ti#A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7\u000f\u0006\u0004\u0002^\u0005}\u0013\u0011\r\t\u00071\u0006}\u00121I\u001a\t\u000f\u0005e\u0003\u00031\u0001\u0002.!9\u00111\r\tA\u0002\u0005\u0015\u0014!E3bO\u0016\u0014X*\u0019;fe&\fG.\u001b>feB\u0019!&a\u001a\n\u0007\u0005%dD\u0001\u0007NCR,'/[1mSj,'/\u0001\u0005u_N#(/\u001b8h)\t\ty\u0007\u0005\u0003\u0002r\u0005]TBAA:\u0015\r\t)HU\u0001\u0005Y\u0006tw-\u0003\u0003\u0002z\u0005M$AB*ue&tw\rK\u0002\u0001\u0003{\u0002B!a \u0002\u00066\u0011\u0011\u0011\u0011\u0006\u0004\u0003\u0007\u0003\u0013AC1o]>$\u0018\r^5p]&!\u0011qQAA\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002/=+HoZ8j]\u001e\u001cuN\u001c8fGRLwN\\*uC\u001e,\u0007cAA\u0002'M\u00191#a$\u0011\u0007a\u000b\t*C\u0002\u0002\u0014^\u0012a!\u00118z%\u00164GCAAF\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%gU\u0011\u00111\u0014\u0016\u0004/\u0006u5FAAP!\u0011\t\t+!+\u000e\u0005\u0005\r&\u0002BAS\u0003O\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005\ru'\u0003\u0003\u0002,\u0006\r&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIQ*\"!!-+\u0007q\u000bi*A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%N\u000b\u0003\u0003oS3\u0001^AO\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%mU\u0011\u0011Q\u0018\u0016\u0004q\u0006u\u0005")
@InternalApi
public class OutgoingConnectionStage
extends GraphStageWithMaterializedValue<FlowShape<ByteString, ByteString>, Future<Tcp.OutgoingConnection>> {
    private final ActorRef manager;
    private final InetSocketAddress remoteAddress;
    private final Option<InetSocketAddress> localAddress;
    private final Iterable<Inet.SocketOption> options;
    private final boolean halfClose;
    private final Duration connectTimeout;
    private final Inlet<ByteString> bytesIn;
    private final Outlet<ByteString> bytesOut;
    private final FlowShape<ByteString, ByteString> shape;

    public static Duration $lessinit$greater$default$6() {
        return OutgoingConnectionStage$.MODULE$.$lessinit$greater$default$6();
    }

    public static boolean $lessinit$greater$default$5() {
        return OutgoingConnectionStage$.MODULE$.$lessinit$greater$default$5();
    }

    public static Iterable<Inet.SocketOption> $lessinit$greater$default$4() {
        return OutgoingConnectionStage$.MODULE$.$lessinit$greater$default$4();
    }

    public static Option<InetSocketAddress> $lessinit$greater$default$3() {
        return OutgoingConnectionStage$.MODULE$.$lessinit$greater$default$3();
    }

    public Inlet<ByteString> bytesIn() {
        return this.bytesIn;
    }

    public Outlet<ByteString> bytesOut() {
        return this.bytesOut;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("OutgoingConnection");
    }

    @Override
    public FlowShape<ByteString, ByteString> shape() {
        return this.shape;
    }

    @Override
    public Tuple2<GraphStageLogic, Future<Tcp.OutgoingConnection>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        throw new UnsupportedOperationException("Not used");
    }

    @Override
    public Tuple2<GraphStageLogic, Future<Tcp.OutgoingConnection>> createLogicAndMaterializedValue(Attributes inheritedAttributes, Materializer eagerMaterializer) {
        Option option;
        Duration duration = this.connectTimeout;
        if (duration instanceof FiniteDuration) {
            FiniteDuration finiteDuration = (FiniteDuration)duration;
            option = new Some<FiniteDuration>(finiteDuration);
        } else {
            option = None$.MODULE$;
        }
        None$ connTimeout = option;
        Promise<InetSocketAddress> localAddressPromise = Promise$.MODULE$.apply();
        TcpConnectionStage.TcpStreamLogic logic = new TcpConnectionStage.TcpStreamLogic((FlowShape<ByteString, ByteString>)this.shape(), new TcpConnectionStage.Outbound(this.manager, new Tcp.Connect(this.remoteAddress, this.localAddress, this.options, connTimeout, true), localAddressPromise, this.halfClose), inheritedAttributes, this.remoteAddress, eagerMaterializer);
        return new Tuple2<GraphStageLogic, Future<Tcp.OutgoingConnection>>(logic, localAddressPromise.future().map((Function1<InetSocketAddress, Tcp.OutgoingConnection> & Serializable)x$4 -> new Tcp.OutgoingConnection($this.remoteAddress, (InetSocketAddress)x$4), ExecutionContexts$.MODULE$.parasitic()));
    }

    public String toString() {
        return new StringBuilder(8).append("TCP-to(").append(this.remoteAddress).append(")").toString();
    }

    public OutgoingConnectionStage(ActorRef manager, InetSocketAddress remoteAddress, Option<InetSocketAddress> localAddress, Iterable<Inet.SocketOption> options, boolean halfClose, Duration connectTimeout) {
        this.manager = manager;
        this.remoteAddress = remoteAddress;
        this.localAddress = localAddress;
        this.options = options;
        this.halfClose = halfClose;
        this.connectTimeout = connectTimeout;
        this.bytesIn = Inlet$.MODULE$.apply("OutgoingTCP.in");
        this.bytesOut = Outlet$.MODULE$.apply("OutgoingTCP.out");
        this.shape = new FlowShape<ByteString, ByteString>(this.bytesIn(), this.bytesOut());
    }
}

