/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.JavaFlowAndRsConverters$Implicits$;
import akka.stream.impl.JavaFlowAndRsConverters$Implicits$FlowSubscriberConverter$;
import akka.stream.impl.JavaFlowAndRsConverters$Implicits$FlowSubscriptionConverter$;
import java.util.concurrent.Flow;
import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i4Q!\u0003\u0006\u0003\u001dAA\u0001\u0002\u000e\u0001\u0003\u0006\u0004%\t!\u000e\u0005\t\u0003\u0002\u0011\t\u0011)A\u0005m!)!\t\u0001C\u0001\u0007\")q\t\u0001C!\u0011\")!\f\u0001C!7\")A\f\u0001C!;\")\u0001\r\u0001C!C\")q\r\u0001C!Q\na\"*\u0019<b\r2|w\u000f\u0015:pG\u0016\u001c8o\u001c:U_J\u001b\u0018\tZ1qi\u0016\u0014(BA\u0006\r\u0003\u0011IW\u000e\u001d7\u000b\u00055q\u0011AB:ue\u0016\fWNC\u0001\u0010\u0003\u0011\t7n[1\u0016\u0007E!#gE\u0002\u0001%i\u0001\"a\u0005\r\u000e\u0003QQ!!\u0006\f\u0002\t1\fgn\u001a\u0006\u0002/\u0005!!.\u0019<b\u0013\tIBC\u0001\u0004PE*,7\r\u001e\t\u00057\u0001\u0012\u0013'D\u0001\u001d\u0015\tib$A\bsK\u0006\u001cG/\u001b<fgR\u0014X-Y7t\u0015\u0005y\u0012aA8sO&\u0011\u0011\u0005\b\u0002\n!J|7-Z:t_J\u0004\"a\t\u0013\r\u0001\u0011)Q\u0005\u0001b\u0001O\t\tAk\u0001\u0001\u0012\u0005!r\u0003CA\u0015-\u001b\u0005Q#\"A\u0016\u0002\u000bM\u001c\u0017\r\\1\n\u00055R#a\u0002(pi\"Lgn\u001a\t\u0003S=J!\u0001\r\u0016\u0003\u0007\u0005s\u0017\u0010\u0005\u0002$e\u0011)1\u0007\u0001b\u0001O\t\t!+\u0001\u0005eK2,w-\u0019;f+\u00051\u0004\u0003B\u001c@EEr!\u0001O\u001f\u000e\u0003eR!AO\u001e\u0002\u0015\r|gnY;se\u0016tGO\u0003\u0002=-\u0005!Q\u000f^5m\u0013\tq\u0014(\u0001\u0003GY><\u0018BA\u0011A\u0015\tq\u0014(A\u0005eK2,w-\u0019;fA\u00051A(\u001b8jiz\"\"\u0001\u0012$\u0011\t\u0015\u0003!%M\u0007\u0002\u0015!)Ag\u0001a\u0001m\u00059qN\\#se>\u0014HCA%M!\tI#*\u0003\u0002LU\t!QK\\5u\u0011\u0015iE\u00011\u0001O\u0003\u0005!\bCA(X\u001d\t\u0001VK\u0004\u0002R)6\t!K\u0003\u0002TM\u00051AH]8pizJ\u0011aK\u0005\u0003-*\nq\u0001]1dW\u0006<W-\u0003\u0002Y3\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0003-*\n!b\u001c8D_6\u0004H.\u001a;f)\u0005I\u0015AB8o\u001d\u0016DH\u000f\u0006\u0002J=\")qL\u0002a\u0001E\u0005!Q\r\\3n\u0003-ygnU;cg\u000e\u0014\u0018NY3\u0015\u0005%\u0013\u0007\"B2\b\u0001\u0004!\u0017!A:\u0011\u0005m)\u0017B\u00014\u001d\u00051\u0019VOY:de&\u0004H/[8o\u0003%\u0019XOY:de&\u0014W\r\u0006\u0002JS\")!\u000e\u0003a\u0001W\u0006a!o]*vEN\u001c'/\u001b2feB\u0012A\u000e\u001d\t\u000475|\u0017B\u00018\u001d\u0005)\u0019VOY:de&\u0014WM\u001d\t\u0003GA$\u0011\"]5\u0002\u0002\u0003\u0005)\u0011\u0001:\u0003\u0007}#C'\u0005\u00022]!\u0012\u0001\u0001\u001e\t\u0003kbl\u0011A\u001e\u0006\u0003o:\t!\"\u00198o_R\fG/[8o\u0013\tIhOA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class JavaFlowProcessorToRsAdapter<T, R>
implements Processor<T, R> {
    private final Flow.Processor<T, R> delegate;

    public Flow.Processor<T, R> delegate() {
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

    @Override
    public void subscribe(Subscriber<? super R> rsSubscriber) {
        this.delegate().subscribe(JavaFlowAndRsConverters$Implicits$FlowSubscriberConverter$.MODULE$.asJava$extension(JavaFlowAndRsConverters$Implicits$.MODULE$.FlowSubscriberConverter(rsSubscriber)));
    }

    public JavaFlowProcessorToRsAdapter(Flow.Processor<T, R> delegate) {
        this.delegate = delegate;
    }
}

