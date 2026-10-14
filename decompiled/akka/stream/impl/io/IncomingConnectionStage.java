/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.NotUsed$;
import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Materializer;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.io.TcpConnectionStage;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.util.ByteString;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;
import scala.Function0;
import scala.Tuple2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005}b!\u0002\n\u0014\u0001eY\u0002\u0002\u0003\u0017\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0018\t\u0011Q\u0002!\u0011!Q\u0001\nUB\u0001\"\u0010\u0001\u0003\u0002\u0003\u0006IA\u0010\u0005\t\t\u0002\u0011\t\u0011)A\u0005\u000b\")1\n\u0001C\u0001\u0019\"91\u000b\u0001b\u0001\n\u0013!\u0006B\u00020\u0001A\u0003%Q\u000bC\u0004`\u0001\t\u0007I\u0011\u00011\t\r\u0011\u0004\u0001\u0015!\u0003b\u0011\u001d)\u0007A1A\u0005\u0002\u0019DaA\u001b\u0001!\u0002\u00139\u0007\"B6\u0001\t\u0003b\u0007b\u00029\u0001\u0005\u0004%\t!\u001d\u0005\u0007e\u0002\u0001\u000b\u0011\u0002\u0012\t\u000bM\u0004A\u0011\t;\t\u000bi\u0004A\u0011I>\t\u000f\u0005\u0005\u0002\u0001\"\u0011\u0002$\t9\u0012J\\2p[&twmQ8o]\u0016\u001cG/[8o'R\fw-\u001a\u0006\u0003)U\t!![8\u000b\u0005Y9\u0012\u0001B5na2T!\u0001G\r\u0002\rM$(/Z1n\u0015\u0005Q\u0012\u0001B1lW\u0006\u001c\"\u0001\u0001\u000f\u0011\u0007u\u0001#%D\u0001\u001f\u0015\tyr#A\u0003ti\u0006<W-\u0003\u0002\"=\tQqI]1qQN#\u0018mZ3\u0011\t\r\"cEJ\u0007\u0002/%\u0011Qe\u0006\u0002\n\r2|wo\u00155ba\u0016\u0004\"a\n\u0016\u000e\u0003!R!!K\r\u0002\tU$\u0018\u000e\\\u0005\u0003W!\u0012!BQ=uKN#(/\u001b8h\u0003)\u0019wN\u001c8fGRLwN\\\u0002\u0001!\ty#'D\u00011\u0015\t\t\u0014$A\u0003bGR|'/\u0003\u00024a\tA\u0011i\u0019;peJ+g-A\u0007sK6|G/Z!eIJ,7o\u001d\t\u0003mmj\u0011a\u000e\u0006\u0003qe\n1A\\3u\u0015\u0005Q\u0014\u0001\u00026bm\u0006L!\u0001P\u001c\u0003#%sW\r^*pG.,G/\u00113ee\u0016\u001c8/A\u0005iC247\t\\8tKB\u0011qHQ\u0007\u0002\u0001*\t\u0011)A\u0003tG\u0006d\u0017-\u0003\u0002D\u0001\n9!i\\8mK\u0006t\u0017\u0001\u0005:fO&\u001cH/\u001a:DC2d'-Y2l!\ryd\tS\u0005\u0003\u000f\u0002\u0013\u0011BR;oGRLwN\u001c\u0019\u0011\u0005}J\u0015B\u0001&A\u0005\u0011)f.\u001b;\u0002\rqJg.\u001b;?)\u0015iu\nU)S!\tq\u0005!D\u0001\u0014\u0011\u0015aS\u00011\u0001/\u0011\u0015!T\u00011\u00016\u0011\u0015iT\u00011\u0001?\u0011\u0015!U\u00011\u0001F\u00039A\u0017m\u001d\"fK:\u001c%/Z1uK\u0012,\u0012!\u0016\t\u0003-rk\u0011a\u0016\u0006\u00031f\u000ba!\u0019;p[&\u001c'B\u0001.\\\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0003SeJ!!X,\u0003\u001b\u0005#x.\\5d\u0005>|G.Z1o\u0003=A\u0017m\u001d\"fK:\u001c%/Z1uK\u0012\u0004\u0013a\u00022zi\u0016\u001c\u0018J\\\u000b\u0002CB\u00191E\u0019\u0014\n\u0005\r<\"!B%oY\u0016$\u0018\u0001\u00032zi\u0016\u001c\u0018J\u001c\u0011\u0002\u0011\tLH/Z:PkR,\u0012a\u001a\t\u0004G!4\u0013BA5\u0018\u0005\u0019yU\u000f\u001e7fi\u0006I!-\u001f;fg>+H\u000fI\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#A7\u0011\u0005\rr\u0017BA8\u0018\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0002E\u000511\u000f[1qK\u0002\n1b\u0019:fCR,Gj\\4jGR\u0011Q\u000f\u001f\t\u0003;YL!a\u001e\u0010\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDQ!_\bA\u00025\f1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\fqd\u0019:fCR,Gj\\4jG\u0006sG-T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f)\u0015a\u0018QCA\f!\u0015yTp`A\u0007\u0013\tq\bI\u0001\u0004UkBdWM\r\t\u0005\u0003\u0003\t9AD\u0002O\u0003\u0007I1!!\u0002\u0014\u0003I!6\r]\"p]:,7\r^5p]N#\u0018mZ3\n\t\u0005%\u00111\u0002\u0002\u000f)\u000e\u00048\u000b\u001e:fC6dunZ5d\u0015\r\t)a\u0005\b\u0005\u0003\u001f\t\t\"D\u0001\u001a\u0013\r\t\u0019\"G\u0001\b\u001d>$Xk]3e\u0011\u0015I\b\u00031\u0001n\u0011\u001d\tI\u0002\u0005a\u0001\u00037\t\u0011#Z1hKJl\u0015\r^3sS\u0006d\u0017N_3s!\r\u0019\u0013QD\u0005\u0004\u0003?9\"\u0001D'bi\u0016\u0014\u0018.\u00197ju\u0016\u0014\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005\u0015\u0002\u0003BA\u0014\u0003[i!!!\u000b\u000b\u0007\u0005-\u0012(\u0001\u0003mC:<\u0017\u0002BA\u0018\u0003S\u0011aa\u0015;sS:<\u0007f\u0001\u0001\u00024A!\u0011QGA\u001e\u001b\t\t9DC\u0002\u0002:e\t!\"\u00198o_R\fG/[8o\u0013\u0011\ti$a\u000e\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public class IncomingConnectionStage
extends GraphStage<FlowShape<ByteString, ByteString>> {
    private final ActorRef connection;
    private final InetSocketAddress remoteAddress;
    private final boolean halfClose;
    private final Function0<BoxedUnit> registerCallback;
    private final AtomicBoolean hasBeenCreated;
    private final Inlet<ByteString> bytesIn;
    private final Outlet<ByteString> bytesOut;
    private final FlowShape<ByteString, ByteString> shape;

    private AtomicBoolean hasBeenCreated() {
        return this.hasBeenCreated;
    }

    public Inlet<ByteString> bytesIn() {
        return this.bytesIn;
    }

    public Outlet<ByteString> bytesOut() {
        return this.bytesOut;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("IncomingConnection");
    }

    @Override
    public FlowShape<ByteString, ByteString> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        throw new UnsupportedOperationException("Not used");
    }

    @Override
    public Tuple2<TcpConnectionStage.TcpStreamLogic, NotUsed$> createLogicAndMaterializedValue(Attributes inheritedAttributes, Materializer eagerMaterializer) {
        if (this.hasBeenCreated().get()) {
            throw new IllegalStateException("Cannot materialize an incoming connection Flow twice.");
        }
        this.hasBeenCreated().set(true);
        return new Tuple2<TcpConnectionStage.TcpStreamLogic, NotUsed$>(new TcpConnectionStage.TcpStreamLogic((FlowShape<ByteString, ByteString>)this.shape(), new TcpConnectionStage.Inbound(this.connection, this.halfClose, this.registerCallback), inheritedAttributes, this.remoteAddress, eagerMaterializer), NotUsed$.MODULE$);
    }

    public String toString() {
        return new StringBuilder(10).append("TCP-from(").append(this.remoteAddress).append(")").toString();
    }

    public IncomingConnectionStage(ActorRef connection, InetSocketAddress remoteAddress, boolean halfClose, Function0<BoxedUnit> registerCallback) {
        this.connection = connection;
        this.remoteAddress = remoteAddress;
        this.halfClose = halfClose;
        this.registerCallback = registerCallback;
        this.hasBeenCreated = new AtomicBoolean(false);
        this.bytesIn = Inlet$.MODULE$.apply("IncomingTCP.in");
        this.bytesOut = Outlet$.MODULE$.apply("IncomingTCP.out");
        this.shape = new FlowShape<ByteString, ByteString>(this.bytesIn(), this.bytesOut());
    }
}

