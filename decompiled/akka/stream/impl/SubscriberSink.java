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
import akka.stream.impl.SinkModule;
import org.reactivestreams.Subscriber;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m3Q\u0001C\u0005\u0003\u001b=A\u0001\u0002\u000b\u0001\u0003\u0002\u0003\u0006I!\u000b\u0005\tc\u0001\u0011)\u0019!C\u0001e!Aq\u0007\u0001B\u0001B\u0003%1\u0007C\u00059\u0001\t\u0005\t\u0015!\u0003:y!)Q\b\u0001C\u0001}!)1\t\u0001C!\t\")\u0001\u000b\u0001C!#\nq1+\u001e2tGJL'-\u001a:TS:\\'B\u0001\u0006\f\u0003\u0011IW\u000e\u001d7\u000b\u00051i\u0011AB:ue\u0016\fWNC\u0001\u000f\u0003\u0011\t7n[1\u0016\u0005A92C\u0001\u0001\u0012!\u0011\u00112#\u0006\u0013\u000e\u0003%I!\u0001F\u0005\u0003\u0015MKgn['pIVdW\r\u0005\u0002\u0017/1\u0001A!\u0002\r\u0001\u0005\u0004Q\"AA%o\u0007\u0001\t\"aG\u0011\u0011\u0005qyR\"A\u000f\u000b\u0003y\tQa]2bY\u0006L!\u0001I\u000f\u0003\u000f9{G\u000f[5oOB\u0011ADI\u0005\u0003Gu\u00111!\u00118z!\t)c%D\u0001\u000e\u0013\t9SBA\u0004O_R,6/\u001a3\u0002\u0015M,(m]2sS\n,'\u000fE\u0002+_Ui\u0011a\u000b\u0006\u0003Y5\nqB]3bGRLg/Z:ue\u0016\fWn\u001d\u0006\u0002]\u0005\u0019qN]4\n\u0005AZ#AC*vEN\u001c'/\u001b2fe\u0006Q\u0011\r\u001e;sS\n,H/Z:\u0016\u0003M\u0002\"\u0001N\u001b\u000e\u0003-I!AN\u0006\u0003\u0015\u0005#HO]5ckR,7/A\u0006biR\u0014\u0018NY;uKN\u0004\u0013!B:iCB,\u0007c\u0001\u001b;+%\u00111h\u0003\u0002\n'&t7n\u00155ba\u0016L!\u0001O\n\u0002\rqJg.\u001b;?)\u0011y\u0004)\u0011\"\u0011\u0007I\u0001Q\u0003C\u0003)\u000b\u0001\u0007\u0011\u0006C\u00032\u000b\u0001\u00071\u0007C\u00039\u000b\u0001\u0007\u0011(\u0001\u0004de\u0016\fG/\u001a\u000b\u0003\u000b.\u0003B\u0001\b$*\u0011&\u0011q)\b\u0002\u0007)V\u0004H.\u001a\u001a\u000f\u0005\u0015J\u0015B\u0001&\u000e\u0003\u001dqu\u000e^+tK\u0012DQ\u0001\u0014\u0004A\u00025\u000bqaY8oi\u0016DH\u000f\u0005\u00025\u001d&\u0011qj\u0003\u0002\u0017\u001b\u0006$XM]5bY&T\u0018\r^5p]\u000e{g\u000e^3yi\u0006qq/\u001b;i\u0003R$(/\u001b2vi\u0016\u001cHCA\tS\u0011\u0015\u0019v\u00011\u00014\u0003\u0011\tG\u000f\u001e:)\u0005\u0001)\u0006C\u0001,Z\u001b\u00059&B\u0001-\u000e\u0003)\tgN\\8uCRLwN\\\u0005\u00035^\u00131\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public final class SubscriberSink<In>
extends SinkModule<In, NotUsed> {
    private final Subscriber<In> subscriber;
    private final Attributes attributes;

    @Override
    public Attributes attributes() {
        return this.attributes;
    }

    @Override
    public Tuple2<Subscriber<In>, NotUsed$> create(MaterializationContext context) {
        return new Tuple2<Subscriber<In>, NotUsed$>(this.subscriber, NotUsed$.MODULE$);
    }

    @Override
    public SinkModule<In, NotUsed> withAttributes(Attributes attr) {
        return new SubscriberSink<In>(this.subscriber, attr, this.amendShape(attr));
    }

    public SubscriberSink(Subscriber<In> subscriber, Attributes attributes, SinkShape<In> shape) {
        this.subscriber = subscriber;
        this.attributes = attributes;
        super(shape);
    }
}

