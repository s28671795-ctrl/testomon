/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.NotUsed;
import akka.NotUsed$;
import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.MaterializationContext;
import akka.stream.SinkShape;
import akka.stream.impl.CancellingSubscriber;
import akka.stream.impl.SinkModule;
import org.reactivestreams.Subscriber;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000513Qa\u0002\u0005\u0003\u00199A\u0001\"\b\u0001\u0003\u0006\u0004%\ta\b\u0005\tI\u0001\u0011\t\u0011)A\u0005A!IQ\u0005\u0001B\u0001B\u0003%a%\u000b\u0005\u0006U\u0001!\ta\u000b\u0005\u0006_\u0001!\t\u0005\r\u0005\u0006\u0003\u0002!\tE\u0011\u0002\u000b\u0007\u0006t7-\u001a7TS:\\'BA\u0005\u000b\u0003\u0011IW\u000e\u001d7\u000b\u0005-a\u0011AB:ue\u0016\fWNC\u0001\u000e\u0003\u0011\t7n[1\u0014\u0005\u0001y\u0001\u0003\u0002\t\u0012'ei\u0011\u0001C\u0005\u0003%!\u0011!bU5oW6{G-\u001e7f!\t!r#D\u0001\u0016\u0015\u00051\u0012!B:dC2\f\u0017B\u0001\r\u0016\u0005\r\te.\u001f\t\u00035mi\u0011\u0001D\u0005\u000391\u0011qAT8u+N,G-\u0001\u0006biR\u0014\u0018NY;uKN\u001c\u0001!F\u0001!!\t\t#%D\u0001\u000b\u0013\t\u0019#B\u0001\u0006BiR\u0014\u0018NY;uKN\f1\"\u0019;ue&\u0014W\u000f^3tA\u0005)1\u000f[1qKB\u0019\u0011eJ\n\n\u0005!R!!C*j].\u001c\u0006.\u00199f\u0013\t)\u0013#\u0001\u0004=S:LGO\u0010\u000b\u0004Y5r\u0003C\u0001\t\u0001\u0011\u0015iB\u00011\u0001!\u0011\u0015)C\u00011\u0001'\u0003\u0019\u0019'/Z1uKR\u0011\u0011\u0007\u0010\t\u0005)I\"\u0014$\u0003\u00024+\t1A+\u001e9mKJ\u00022!\u000e\u001e\u0014\u001b\u00051$BA\u001c9\u0003=\u0011X-Y2uSZ,7\u000f\u001e:fC6\u001c(\"A\u001d\u0002\u0007=\u0014x-\u0003\u0002<m\tQ1+\u001e2tGJL'-\u001a:\t\u000bu*\u0001\u0019\u0001 \u0002\u000f\r|g\u000e^3yiB\u0011\u0011eP\u0005\u0003\u0001*\u0011a#T1uKJL\u0017\r\\5{CRLwN\\\"p]R,\u0007\u0010^\u0001\u000fo&$\b.\u0011;ue&\u0014W\u000f^3t)\ty1\tC\u0003E\r\u0001\u0007\u0001%\u0001\u0003biR\u0014\bF\u0001\u0001G!\t9%*D\u0001I\u0015\tIE\"\u0001\u0006b]:|G/\u0019;j_:L!a\u0013%\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public final class CancelSink
extends SinkModule<Object, NotUsed> {
    private final Attributes attributes;

    @Override
    public Attributes attributes() {
        return this.attributes;
    }

    @Override
    public Tuple2<Subscriber<Object>, NotUsed> create(MaterializationContext context) {
        return new Tuple2<Subscriber<Object>, NotUsed>(new CancellingSubscriber(), NotUsed$.MODULE$);
    }

    @Override
    public SinkModule<Object, NotUsed> withAttributes(Attributes attr) {
        return new CancelSink(attr, this.amendShape(attr));
    }

    public CancelSink(Attributes attributes, SinkShape<Object> shape) {
        this.attributes = attributes;
        super(shape);
    }
}

