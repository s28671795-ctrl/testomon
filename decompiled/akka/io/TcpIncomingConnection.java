/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.io.ChannelRegistration;
import akka.io.ChannelRegistry;
import akka.io.Inet;
import akka.io.TcpConnection;
import akka.io.TcpExt;
import java.io.Serializable;
import java.nio.channels.SocketChannel;
import scala.Function1;
import scala.PartialFunction;
import scala.collection.immutable.Iterable;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u000514Q!\u0003\u0006\u0001\u00159A\u0011b\u0005\u0001\u0003\u0002\u0003\u0006I!\u0006\r\t\u0013i\u0001!\u0011!Q\u0001\nm)\u0003\u0002C\u0014\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0015\t\u0011-\u0002!\u0011!Q\u0001\n1B\u0001B\r\u0001\u0003\u0002\u0003\u0006Ia\r\u0005\n#\u0002\u0011\t\u0011)A\u0005%ZCQ\u0001\u0017\u0001\u0005\u0002eCQ!\u0019\u0001\u0005\u0002\t\u0014Q\u0003V2q\u0013:\u001cw.\\5oO\u000e{gN\\3di&|gN\u0003\u0002\f\u0019\u0005\u0011\u0011n\u001c\u0006\u0002\u001b\u0005!\u0011m[6b'\t\u0001q\u0002\u0005\u0002\u0011#5\t!\"\u0003\u0002\u0013\u0015\tiAk\u00199D_:tWm\u0019;j_:\fAa\u0018;da\u000e\u0001\u0001C\u0001\t\u0017\u0013\t9\"B\u0001\u0004UGB,\u0005\u0010^\u0005\u00033E\t1\u0001^2q\u0003!y6\r[1o]\u0016d\u0007C\u0001\u000f$\u001b\u0005i\"B\u0001\u0010 \u0003!\u0019\u0007.\u00198oK2\u001c(B\u0001\u0011\"\u0003\rq\u0017n\u001c\u0006\u0002E\u0005!!.\u0019<b\u0013\t!SDA\u0007T_\u000e\\W\r^\"iC:tW\r\\\u0005\u0003ME\tqa\u00195b]:,G.\u0001\u0005sK\u001eL7\u000f\u001e:z!\t\u0001\u0012&\u0003\u0002+\u0015\ty1\t[1o]\u0016d'+Z4jgR\u0014\u00180A\u0006cS:$\u0007*\u00198eY\u0016\u0014\bCA\u00171\u001b\u0005q#BA\u0018\r\u0003\u0015\t7\r^8s\u0013\t\tdF\u0001\u0005BGR|'OU3g\u0003\u001dy\u0007\u000f^5p]N\u00042\u0001\u000e!D\u001d\t)TH\u0004\u00027w5\tqG\u0003\u00029s\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003i\nQa]2bY\u0006L!\u0001P\u001c\u0002\u0013%lW.\u001e;bE2,\u0017B\u0001 @\u0003\u001d\u0001\u0018mY6bO\u0016T!\u0001P\u001c\n\u0005\u0005\u0013%a\u0003+sCZ,'o]1cY\u0016T!AP \u0011\u0005\u0011seBA#M\u001d\t15J\u0004\u0002H\u00156\t\u0001J\u0003\u0002J)\u00051AH]8pizJ\u0011!D\u0005\u0003\u00171I!!\u0014\u0006\u0002\t%sW\r^\u0005\u0003\u001fB\u0013AbU8dW\u0016$x\n\u001d;j_:T!!\u0014\u0006\u0002\u001dI,\u0017\r\u001a+ie>$H\u000f\\5oOB\u00111\u000bV\u0007\u0002s%\u0011Q+\u000f\u0002\b\u0005>|G.Z1o\u0013\t9\u0016#\u0001\u0005qk2dWj\u001c3f\u0003\u0019a\u0014N\\5u}Q9!l\u0017/^=~\u0003\u0007C\u0001\t\u0001\u0011\u0015\u0019r\u00011\u0001\u0016\u0011\u0015Qr\u00011\u0001\u001c\u0011\u00159s\u00011\u0001)\u0011\u0015Ys\u00011\u0001-\u0011\u0015\u0011t\u00011\u00014\u0011\u0015\tv\u00011\u0001S\u0003\u001d\u0011XmY3jm\u0016,\u0012a\u0019\t\u0005'\u00124\u0017.\u0003\u0002fs\ty\u0001+\u0019:uS\u0006dg)\u001e8di&|g\u000e\u0005\u0002TO&\u0011\u0001.\u000f\u0002\u0004\u0003:L\bCA*k\u0013\tY\u0017H\u0001\u0003V]&$\b")
public class TcpIncomingConnection
extends TcpConnection {
    public final ActorRef akka$io$TcpIncomingConnection$$bindHandler;
    public final Iterable<Inet.SocketOption> akka$io$TcpIncomingConnection$$options;

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpIncomingConnection $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof ChannelRegistration) {
                    ChannelRegistration channelRegistration = (ChannelRegistration)A1;
                    this.$outer.completeConnect(channelRegistration, this.$outer.akka$io$TcpIncomingConnection$$bindHandler, this.$outer.akka$io$TcpIncomingConnection$$options);
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof ChannelRegistration;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public TcpIncomingConnection(TcpExt _tcp, SocketChannel _channel, ChannelRegistry registry, ActorRef bindHandler, Iterable<Inet.SocketOption> options, boolean readThrottling) {
        this.akka$io$TcpIncomingConnection$$bindHandler = bindHandler;
        this.akka$io$TcpIncomingConnection$$options = options;
        super(_tcp, _channel, readThrottling);
        this.signDeathPact(bindHandler);
        registry.register(this.channel(), 0, this.self());
    }
}

