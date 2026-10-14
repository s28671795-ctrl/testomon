/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.MaterializationContext;
import akka.stream.SinkShape;
import akka.stream.StreamSubscriptionTimeoutTerminationMode;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$;
import akka.stream.impl.SinkModule;
import akka.stream.impl.VirtualPublisher;
import org.reactivestreams.Publisher;
import scala.MatchError;
import scala.Tuple2;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005Q3Qa\u0002\u0005\u0001\u00199A\u0001b\u000b\u0001\u0003\u0006\u0004%\t\u0001\f\u0005\tc\u0001\u0011\t\u0011)A\u0005[!I!\u0007\u0001B\u0001B\u0003%1G\u000e\u0005\u0006o\u0001!\t\u0001\u000f\u0005\u0006y\u0001!\t%\u0010\u0005\u0006\u0013\u0002!\tE\u0013\u0002\u000e!V\u0014G.[:iKJ\u001c\u0016N\\6\u000b\u0005%Q\u0011\u0001B5na2T!a\u0003\u0007\u0002\rM$(/Z1n\u0015\u0005i\u0011\u0001B1lW\u0006,\"a\u0004\f\u0014\u0005\u0001\u0001\u0002\u0003B\t\u0013)\rj\u0011\u0001C\u0005\u0003'!\u0011!bU5oW6{G-\u001e7f!\t)b\u0003\u0004\u0001\u0005\u000b]\u0001!\u0019A\r\u0003\u0005%s7\u0001A\t\u00035\u0001\u0002\"a\u0007\u0010\u000e\u0003qQ\u0011!H\u0001\u0006g\u000e\fG.Y\u0005\u0003?q\u0011qAT8uQ&tw\r\u0005\u0002\u001cC%\u0011!\u0005\b\u0002\u0004\u0003:L\bc\u0001\u0013*)5\tQE\u0003\u0002'O\u0005y!/Z1di&4Xm\u001d;sK\u0006l7OC\u0001)\u0003\ry'oZ\u0005\u0003U\u0015\u0012\u0011\u0002U;cY&\u001c\b.\u001a:\u0002\u0015\u0005$HO]5ckR,7/F\u0001.!\tqs&D\u0001\u000b\u0013\t\u0001$B\u0001\u0006BiR\u0014\u0018NY;uKN\f1\"\u0019;ue&\u0014W\u000f^3tA\u0005)1\u000f[1qKB\u0019a\u0006\u000e\u000b\n\u0005UR!!C*j].\u001c\u0006.\u00199f\u0013\t\u0011$#\u0001\u0004=S:LGO\u0010\u000b\u0004siZ\u0004cA\t\u0001)!)1\u0006\u0002a\u0001[!)!\u0007\u0002a\u0001g\u000511M]3bi\u0016$\"A\u0010#\u0011\tmy\u0014iI\u0005\u0003\u0001r\u0011a\u0001V;qY\u0016\u0014\u0004CA\u000eC\u0013\t\u0019ED\u0001\u0004B]f\u0014VM\u001a\u0005\u0006\u000b\u0016\u0001\rAR\u0001\bG>tG/\u001a=u!\tqs)\u0003\u0002I\u0015\t1R*\u0019;fe&\fG.\u001b>bi&|gnQ8oi\u0016DH/\u0001\bxSRD\u0017\t\u001e;sS\n,H/Z:\u0015\u0005AY\u0005\"\u0002'\u0007\u0001\u0004i\u0013\u0001B1uiJD#\u0001\u0001(\u0011\u0005=\u0013V\"\u0001)\u000b\u0005Ec\u0011AC1o]>$\u0018\r^5p]&\u00111\u000b\u0015\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
@InternalApi
public class PublisherSink<In>
extends SinkModule<In, Publisher<In>> {
    private final Attributes attributes;

    @Override
    public Attributes attributes() {
        return this.attributes;
    }

    @Override
    public Tuple2<Object, Publisher<In>> create(MaterializationContext context) {
        StreamSubscriptionTimeoutTerminationMode mode;
        VirtualPublisher proc = new VirtualPublisher();
        ActorAttributes.StreamSubscriptionTimeout streamSubscriptionTimeout = (ActorAttributes.StreamSubscriptionTimeout)context.effectiveAttributes().mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.StreamSubscriptionTimeout.class));
        if (streamSubscriptionTimeout == null) {
            throw new MatchError(streamSubscriptionTimeout);
        }
        FiniteDuration timeout2 = streamSubscriptionTimeout.timeout();
        StreamSubscriptionTimeoutTerminationMode mode2 = streamSubscriptionTimeout.mode();
        Tuple2<FiniteDuration, StreamSubscriptionTimeoutTerminationMode> tuple2 = new Tuple2<FiniteDuration, StreamSubscriptionTimeoutTerminationMode>(timeout2, mode2);
        FiniteDuration timeout3 = tuple2._1();
        StreamSubscriptionTimeoutTerminationMode streamSubscriptionTimeoutTerminationMode = mode = tuple2._2();
        StreamSubscriptionTimeoutTerminationMode streamSubscriptionTimeoutTerminationMode2 = StreamSubscriptionTimeoutTerminationMode$.MODULE$.noop();
        Object object = (streamSubscriptionTimeoutTerminationMode == null ? streamSubscriptionTimeoutTerminationMode2 != null : !streamSubscriptionTimeoutTerminationMode.equals(streamSubscriptionTimeoutTerminationMode2)) ? context.materializer().scheduleOnce(timeout3, () -> proc.onSubscriptionTimeout(context.materializer(), mode)) : BoxedUnit.UNIT;
        return new Tuple2<Object, Publisher<In>>(proc, proc);
    }

    @Override
    public SinkModule<In, Publisher<In>> withAttributes(Attributes attr) {
        return new PublisherSink(attr, this.amendShape(attr));
    }

    public PublisherSink(Attributes attributes, SinkShape<In> shape) {
        this.attributes = attributes;
        super(shape);
    }
}

