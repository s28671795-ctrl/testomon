/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.MaterializationContext;
import akka.stream.SinkShape;
import akka.stream.impl.ActorProcessor;
import akka.stream.impl.ExposedPublisher;
import akka.stream.impl.FanoutProcessorImpl$;
import akka.stream.impl.SinkModule;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q3Qa\u0002\u0005\u0003\u00199A\u0001b\u000b\u0001\u0003\u0006\u0004%\t\u0001\f\u0005\tc\u0001\u0011\t\u0011)A\u0005[!I!\u0007\u0001B\u0001B\u0003%1G\u000e\u0005\u0006o\u0001!\t\u0001\u000f\u0005\u0006y\u0001!\t%\u0010\u0005\u0006\u0013\u0002!\tE\u0013\u0002\u0014\r\u0006tw.\u001e;Qk\nd\u0017n\u001d5feNKgn\u001b\u0006\u0003\u0013)\tA![7qY*\u00111\u0002D\u0001\u0007gR\u0014X-Y7\u000b\u00035\tA!Y6lCV\u0011qBF\n\u0003\u0001A\u0001B!\u0005\n\u0015G5\t\u0001\"\u0003\u0002\u0014\u0011\tQ1+\u001b8l\u001b>$W\u000f\\3\u0011\u0005U1B\u0002\u0001\u0003\u0006/\u0001\u0011\r!\u0007\u0002\u0003\u0013:\u001c\u0001!\u0005\u0002\u001bAA\u00111DH\u0007\u00029)\tQ$A\u0003tG\u0006d\u0017-\u0003\u0002 9\t9aj\u001c;iS:<\u0007CA\u000e\"\u0013\t\u0011CDA\u0002B]f\u00042\u0001J\u0015\u0015\u001b\u0005)#B\u0001\u0014(\u0003=\u0011X-Y2uSZ,7\u000f\u001e:fC6\u001c(\"\u0001\u0015\u0002\u0007=\u0014x-\u0003\u0002+K\tI\u0001+\u001e2mSNDWM]\u0001\u000bCR$(/\u001b2vi\u0016\u001cX#A\u0017\u0011\u00059zS\"\u0001\u0006\n\u0005AR!AC!uiJL'-\u001e;fg\u0006Y\u0011\r\u001e;sS\n,H/Z:!\u0003\u0015\u0019\b.\u00199f!\rqC\u0007F\u0005\u0003k)\u0011\u0011bU5oWNC\u0017\r]3\n\u0005I\u0012\u0012A\u0002\u001fj]&$h\bF\u0002:um\u00022!\u0005\u0001\u0015\u0011\u0015YC\u00011\u0001.\u0011\u0015\u0011D\u00011\u00014\u0003\u0019\u0019'/Z1uKR\u0011a\b\u0012\t\u00057}\n5%\u0003\u0002A9\t1A+\u001e9mKJ\u00022\u0001\n\"\u0015\u0013\t\u0019UE\u0001\u0006Tk\n\u001c8M]5cKJDQ!R\u0003A\u0002\u0019\u000bqaY8oi\u0016DH\u000f\u0005\u0002/\u000f&\u0011\u0001J\u0003\u0002\u0017\u001b\u0006$XM]5bY&T\u0018\r^5p]\u000e{g\u000e^3yi\u0006qq/\u001b;i\u0003R$(/\u001b2vi\u0016\u001cHC\u0001\tL\u0011\u0015ae\u00011\u0001.\u0003\u0011\tG\u000f\u001e:)\u0005\u0001q\u0005CA(S\u001b\u0005\u0001&BA)\r\u0003)\tgN\\8uCRLwN\\\u0005\u0003'B\u00131\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public final class FanoutPublisherSink<In>
extends SinkModule<In, Publisher<In>> {
    private final Attributes attributes;

    @Override
    public Attributes attributes() {
        return this.attributes;
    }

    @Override
    public Tuple2<Subscriber<In>, Publisher<In>> create(MaterializationContext context) {
        ActorRef impl = context.materializer().actorOf(context, FanoutProcessorImpl$.MODULE$.props(context.effectiveAttributes()));
        ActorProcessor fanoutProcessor = new ActorProcessor(impl);
        ExposedPublisher x$1 = new ExposedPublisher(fanoutProcessor);
        ActorRef x$2 = impl.$bang$default$2(x$1);
        impl.$bang(x$1, x$2);
        return new Tuple2<Subscriber<In>, Publisher<In>>(fanoutProcessor, fanoutProcessor);
    }

    @Override
    public SinkModule<In, Publisher<In>> withAttributes(Attributes attr) {
        return new FanoutPublisherSink(attr, this.amendShape(attr));
    }

    public FanoutPublisherSink(Attributes attributes, SinkShape<In> shape) {
        this.attributes = attributes;
        super(shape);
    }
}

