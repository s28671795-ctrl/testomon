/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.JavaFlowAndRsConverters$Implicits$;
import akka.stream.impl.JavaFlowAndRsConverters$Implicits$RsSubscriptionConverter$;
import java.util.concurrent.Flow;
import org.reactivestreams.Subscriber;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)4Q\u0001C\u0005\u0003\u001b=A\u0001\u0002\u000e\u0001\u0003\u0006\u0004%\t!\u000e\u0005\t{\u0001\u0011\t\u0011)A\u0005m!)a\b\u0001C\u0001\u007f!)1\t\u0001C!\t\")a\u000b\u0001C!/\")\u0001\f\u0001C!3\")A\f\u0001C!;\ni\"k]*vEN\u001c'/\u001b2feR{'*\u0019<b\r2|w/\u00113baR,'O\u0003\u0002\u000b\u0017\u0005!\u0011.\u001c9m\u0015\taQ\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u001d\u0005!\u0011m[6b+\t\u0001reE\u0002\u0001#e\u0001\"AE\f\u000e\u0003MQ!\u0001F\u000b\u0002\t1\fgn\u001a\u0006\u0002-\u0005!!.\u0019<b\u0013\tA2C\u0001\u0004PE*,7\r\u001e\t\u00045\t*cBA\u000e!\u001b\u0005a\"BA\u000f\u001f\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0003?U\tA!\u001e;jY&\u0011\u0011\u0005H\u0001\u0005\r2|w/\u0003\u0002$I\tQ1+\u001e2tGJL'-\u001a:\u000b\u0005\u0005b\u0002C\u0001\u0014(\u0019\u0001!Q\u0001\u000b\u0001C\u0002)\u0012\u0011\u0001V\u0002\u0001#\tY\u0013\u0007\u0005\u0002-_5\tQFC\u0001/\u0003\u0015\u00198-\u00197b\u0013\t\u0001TFA\u0004O_RD\u0017N\\4\u0011\u00051\u0012\u0014BA\u001a.\u0005\r\te._\u0001\tI\u0016dWmZ1uKV\ta\u0007E\u00028y\u0015j\u0011\u0001\u000f\u0006\u0003si\nqB]3bGRLg/Z:ue\u0016\fWn\u001d\u0006\u0002w\u0005\u0019qN]4\n\u0005\rB\u0014!\u00033fY\u0016<\u0017\r^3!\u0003\u0019a\u0014N\\5u}Q\u0011\u0001I\u0011\t\u0004\u0003\u0002)S\"A\u0005\t\u000bQ\u001a\u0001\u0019\u0001\u001c\u0002\u000f=tWI\u001d:peR\u0011Q\t\u0013\t\u0003Y\u0019K!aR\u0017\u0003\tUs\u0017\u000e\u001e\u0005\u0006\u0013\u0012\u0001\rAS\u0001\u0002iB\u00111j\u0015\b\u0003\u0019Fs!!\u0014)\u000e\u00039S!aT\u0015\u0002\rq\u0012xn\u001c;?\u0013\u0005q\u0013B\u0001*.\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001V+\u0003\u0013QC'o\\<bE2,'B\u0001*.\u0003)ygnQ8na2,G/\u001a\u000b\u0002\u000b\u00061qN\u001c(fqR$\"!\u0012.\t\u000bm3\u0001\u0019A\u0013\u0002\t\u0015dW-\\\u0001\f_:\u001cVOY:de&\u0014W\r\u0006\u0002F=\")ql\u0002a\u0001A\u0006\t1\u000f\u0005\u0002\u001bC&\u0011!\r\n\u0002\r'V\u00147o\u0019:jaRLwN\u001c\u0015\u0003\u0001\u0011\u0004\"!\u001a5\u000e\u0003\u0019T!aZ\u0007\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002jM\nY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public final class RsSubscriberToJavaFlowAdapter<T>
implements Flow.Subscriber<T> {
    private final Subscriber<T> delegate;

    public Subscriber<T> delegate() {
        return this.delegate;
    }

    @Override
    public void onError(Throwable t2) {
        this.delegate().onError(t2);
    }

    @Override
    public void onComplete() {
        this.delegate().onComplete();
    }

    @Override
    public void onNext(T elem) {
        this.delegate().onNext(elem);
    }

    @Override
    public void onSubscribe(Flow.Subscription s2) {
        this.delegate().onSubscribe(JavaFlowAndRsConverters$Implicits$RsSubscriptionConverter$.MODULE$.asRs$extension(JavaFlowAndRsConverters$Implicits$.MODULE$.RsSubscriptionConverter(s2)));
    }

    public RsSubscriberToJavaFlowAdapter(Subscriber<T> delegate) {
        this.delegate = delegate;
    }
}

