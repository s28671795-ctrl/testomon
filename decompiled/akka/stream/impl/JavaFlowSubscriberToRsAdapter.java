/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.JavaFlowAndRsConverters$Implicits$;
import akka.stream.impl.JavaFlowAndRsConverters$Implicits$FlowSubscriptionConverter$;
import java.util.concurrent.Flow;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)4Q\u0001C\u0005\u0003\u001b=A\u0001\u0002\r\u0001\u0003\u0006\u0004%\t!\r\u0005\t{\u0001\u0011\t\u0011)A\u0005e!)a\b\u0001C\u0001\u007f!)1\t\u0001C!\t\")a\u000b\u0001C!/\")\u0001\f\u0001C!3\")A\f\u0001C!;\ni\"*\u0019<b\r2|woU;cg\u000e\u0014\u0018NY3s)>\u00146/\u00113baR,'O\u0003\u0002\u000b\u0017\u0005!\u0011.\u001c9m\u0015\taQ\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u001d\u0005!\u0011m[6b+\t\u00012eE\u0002\u0001#e\u0001\"AE\f\u000e\u0003MQ!\u0001F\u000b\u0002\t1\fgn\u001a\u0006\u0002-\u0005!!.\u0019<b\u0013\tA2C\u0001\u0004PE*,7\r\u001e\t\u00045}\tS\"A\u000e\u000b\u0005qi\u0012a\u0004:fC\u000e$\u0018N^3tiJ,\u0017-\\:\u000b\u0003y\t1a\u001c:h\u0013\t\u00013D\u0001\u0006Tk\n\u001c8M]5cKJ\u0004\"AI\u0012\r\u0001\u0011)A\u0005\u0001b\u0001M\t\tAk\u0001\u0001\u0012\u0005\u001dj\u0003C\u0001\u0015,\u001b\u0005I#\"\u0001\u0016\u0002\u000bM\u001c\u0017\r\\1\n\u00051J#a\u0002(pi\"Lgn\u001a\t\u0003Q9J!aL\u0015\u0003\u0007\u0005s\u00170\u0001\u0005eK2,w-\u0019;f+\u0005\u0011\u0004cA\u001a<C9\u0011A'O\u0007\u0002k)\u0011agN\u0001\u000bG>t7-\u001e:sK:$(B\u0001\u001d\u0016\u0003\u0011)H/\u001b7\n\u0005i*\u0014\u0001\u0002$m_^L!\u0001\t\u001f\u000b\u0005i*\u0014!\u00033fY\u0016<\u0017\r^3!\u0003\u0019a\u0014N\\5u}Q\u0011\u0001I\u0011\t\u0004\u0003\u0002\tS\"A\u0005\t\u000bA\u001a\u0001\u0019\u0001\u001a\u0002\u000f=tWI\u001d:peR\u0011Q\t\u0013\t\u0003Q\u0019K!aR\u0015\u0003\tUs\u0017\u000e\u001e\u0005\u0006\u0013\u0012\u0001\rAS\u0001\u0002iB\u00111j\u0015\b\u0003\u0019Fs!!\u0014)\u000e\u00039S!aT\u0013\u0002\rq\u0012xn\u001c;?\u0013\u0005Q\u0013B\u0001**\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001V+\u0003\u0013QC'o\\<bE2,'B\u0001**\u0003)ygnQ8na2,G/\u001a\u000b\u0002\u000b\u00061qN\u001c(fqR$\"!\u0012.\t\u000bm3\u0001\u0019A\u0011\u0002\t\u0015dW-\\\u0001\f_:\u001cVOY:de&\u0014W\r\u0006\u0002F=\")ql\u0002a\u0001A\u0006\t1\u000f\u0005\u0002\u001bC&\u0011!m\u0007\u0002\r'V\u00147o\u0019:jaRLwN\u001c\u0015\u0003\u0001\u0011\u0004\"!\u001a5\u000e\u0003\u0019T!aZ\u0007\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002jM\nY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public final class JavaFlowSubscriberToRsAdapter<T>
implements Subscriber<T> {
    private final Flow.Subscriber<T> delegate;

    public Flow.Subscriber<T> delegate() {
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
    public void onSubscribe(Subscription s2) {
        this.delegate().onSubscribe(JavaFlowAndRsConverters$Implicits$FlowSubscriptionConverter$.MODULE$.asJava$extension(JavaFlowAndRsConverters$Implicits$.MODULE$.FlowSubscriptionConverter(s2)));
    }

    public JavaFlowSubscriberToRsAdapter(Flow.Subscriber<T> delegate) {
        this.delegate = delegate;
    }
}

