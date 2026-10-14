/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.MaterializationContext;
import akka.stream.SourceShape;
import akka.stream.impl.SourceModule;
import akka.stream.impl.VirtualProcessor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q3Qa\u0002\u0005\u0003\u00199A\u0001b\u000b\u0001\u0003\u0006\u0004%\t\u0001\f\u0005\tc\u0001\u0011\t\u0011)A\u0005[!I!\u0007\u0001B\u0001B\u0003%1G\u000e\u0005\u0006o\u0001!\t\u0001\u000f\u0005\u0006y\u0001!\t%\u0010\u0005\u0006\u0013\u0002!\tE\u0013\u0002\u0011'V\u00147o\u0019:jE\u0016\u00148k\\;sG\u0016T!!\u0003\u0006\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u00171\taa\u001d;sK\u0006l'\"A\u0007\u0002\t\u0005\\7.Y\u000b\u0003\u001fY\u0019\"\u0001\u0001\t\u0011\tE\u0011BcI\u0007\u0002\u0011%\u00111\u0003\u0003\u0002\r'>,(oY3N_\u0012,H.\u001a\t\u0003+Ya\u0001\u0001B\u0003\u0018\u0001\t\u0007\u0011DA\u0002PkR\u001c\u0001!\u0005\u0002\u001bAA\u00111DH\u0007\u00029)\tQ$A\u0003tG\u0006d\u0017-\u0003\u0002 9\t9aj\u001c;iS:<\u0007CA\u000e\"\u0013\t\u0011CDA\u0002B]f\u00042\u0001J\u0015\u0015\u001b\u0005)#B\u0001\u0014(\u0003=\u0011X-Y2uSZ,7\u000f\u001e:fC6\u001c(\"\u0001\u0015\u0002\u0007=\u0014x-\u0003\u0002+K\tQ1+\u001e2tGJL'-\u001a:\u0002\u0015\u0005$HO]5ckR,7/F\u0001.!\tqs&D\u0001\u000b\u0013\t\u0001$B\u0001\u0006BiR\u0014\u0018NY;uKN\f1\"\u0019;ue&\u0014W\u000f^3tA\u0005)1\u000f[1qKB\u0019a\u0006\u000e\u000b\n\u0005UR!aC*pkJ\u001cWm\u00155ba\u0016L!A\r\n\u0002\rqJg.\u001b;?)\rI$h\u000f\t\u0004#\u0001!\u0002\"B\u0016\u0005\u0001\u0004i\u0003\"\u0002\u001a\u0005\u0001\u0004\u0019\u0014AB2sK\u0006$X\r\u0006\u0002?\tB!1dP!$\u0013\t\u0001ED\u0001\u0004UkBdWM\r\t\u0004I\t#\u0012BA\"&\u0005%\u0001VO\u00197jg\",'\u000fC\u0003F\u000b\u0001\u0007a)A\u0004d_:$X\r\u001f;\u0011\u00059:\u0015B\u0001%\u000b\u0005Yi\u0015\r^3sS\u0006d\u0017N_1uS>t7i\u001c8uKb$\u0018AD<ji\"\fE\u000f\u001e:jEV$Xm\u001d\u000b\u0003!-CQ\u0001\u0014\u0004A\u00025\nA!\u0019;ue\"\u0012\u0001A\u0014\t\u0003\u001fJk\u0011\u0001\u0015\u0006\u0003#2\t!\"\u00198o_R\fG/[8o\u0013\t\u0019\u0006KA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class SubscriberSource<Out>
extends SourceModule<Out, Subscriber<Out>> {
    private final Attributes attributes;

    @Override
    public Attributes attributes() {
        return this.attributes;
    }

    @Override
    public Tuple2<Publisher<Out>, Subscriber<Out>> create(MaterializationContext context) {
        VirtualProcessor processor = new VirtualProcessor();
        return new Tuple2<Publisher<Out>, Subscriber<Out>>(processor, processor);
    }

    @Override
    public SourceModule<Out, Subscriber<Out>> withAttributes(Attributes attr) {
        return new SubscriberSource(attr, this.amendShape(attr));
    }

    public SubscriberSource(Attributes attributes, SourceShape<Out> shape) {
        this.attributes = attributes;
        super(shape);
    }
}

