/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.JavaFlowAndRsConverters$Implicits$;
import akka.stream.impl.JavaFlowAndRsConverters$Implicits$RsSubscriberConverter$;
import akka.stream.impl.JavaFlowAndRsConverters$Implicits$RsSubscriptionConverter$;
import java.util.concurrent.Flow;
import org.reactivestreams.Processor;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i4Q!\u0003\u0006\u0003\u001dAA\u0001\u0002\u000f\u0001\u0003\u0006\u0004%\t!\u000f\u0005\t\u0003\u0002\u0011\t\u0011)A\u0005u!)!\t\u0001C\u0001\u0007\")q\t\u0001C!\u0011\")!\f\u0001C!7\")A\f\u0001C!;\")\u0001\r\u0001C!C\")q\r\u0001C!Q\na\"k\u001d)s_\u000e,7o]8s)>T\u0015M^1GY><\u0018\tZ1qi\u0016\u0014(BA\u0006\r\u0003\u0011IW\u000e\u001d7\u000b\u00055q\u0011AB:ue\u0016\fWNC\u0001\u0010\u0003\u0011\t7n[1\u0016\u0007EAcgE\u0002\u0001%i\u0001\"a\u0005\r\u000e\u0003QQ!!\u0006\f\u0002\t1\fgn\u001a\u0006\u0002/\u0005!!.\u0019<b\u0013\tIBC\u0001\u0004PE*,7\r\u001e\t\u00057\r2SG\u0004\u0002\u001dC5\tQD\u0003\u0002\u001f?\u0005Q1m\u001c8dkJ\u0014XM\u001c;\u000b\u0005\u00012\u0012\u0001B;uS2L!AI\u000f\u0002\t\u0019cwn^\u0005\u0003I\u0015\u0012\u0011\u0002\u0015:pG\u0016\u001c8o\u001c:\u000b\u0005\tj\u0002CA\u0014)\u0019\u0001!Q!\u000b\u0001C\u0002-\u0012\u0011\u0001V\u0002\u0001#\ta#\u0007\u0005\u0002.a5\taFC\u00010\u0003\u0015\u00198-\u00197b\u0013\t\tdFA\u0004O_RD\u0017N\\4\u0011\u00055\u001a\u0014B\u0001\u001b/\u0005\r\te.\u001f\t\u0003OY\"Qa\u000e\u0001C\u0002-\u0012\u0011AU\u0001\tI\u0016dWmZ1uKV\t!\b\u0005\u0003<\u0001\u001a*T\"\u0001\u001f\u000b\u0005ur\u0014a\u0004:fC\u000e$\u0018N^3tiJ,\u0017-\\:\u000b\u0003}\n1a\u001c:h\u0013\t!C(A\u0005eK2,w-\u0019;fA\u00051A(\u001b8jiz\"\"\u0001\u0012$\u0011\t\u0015\u0003a%N\u0007\u0002\u0015!)\u0001h\u0001a\u0001u\u00059qN\\#se>\u0014HCA%M!\ti#*\u0003\u0002L]\t!QK\\5u\u0011\u0015iE\u00011\u0001O\u0003\u0005!\bCA(X\u001d\t\u0001VK\u0004\u0002R)6\t!K\u0003\u0002TU\u00051AH]8pizJ\u0011aL\u0005\u0003-:\nq\u0001]1dW\u0006<W-\u0003\u0002Y3\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0003-:\n!b\u001c8D_6\u0004H.\u001a;f)\u0005I\u0015AB8o\u001d\u0016DH\u000f\u0006\u0002J=\")qL\u0002a\u0001M\u0005!Q\r\\3n\u0003-ygnU;cg\u000e\u0014\u0018NY3\u0015\u0005%\u0013\u0007\"B2\b\u0001\u0004!\u0017!A:\u0011\u0005m)\u0017B\u00014&\u00051\u0019VOY:de&\u0004H/[8o\u0003%\u0019XOY:de&\u0014W\r\u0006\u0002JS\")!\u000e\u0003a\u0001W\u0006q!.\u0019<b'V\u00147o\u0019:jE\u0016\u0014\bG\u00017q!\rYRn\\\u0005\u0003]\u0016\u0012!bU;cg\u000e\u0014\u0018NY3s!\t9\u0003\u000fB\u0005rS\u0006\u0005\t\u0011!B\u0001e\n\u0019q\fJ\u001a\u0012\u0005U\u0012\u0004F\u0001\u0001u!\t)\b0D\u0001w\u0015\t9h\"\u0001\u0006b]:|G/\u0019;j_:L!!\u001f<\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public final class RsProcessorToJavaFlowAdapter<T, R>
implements Flow.Processor<T, R> {
    private final Processor<T, R> delegate;

    public Processor<T, R> delegate() {
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

    @Override
    public void subscribe(Flow.Subscriber<? super R> javaSubscriber) {
        this.delegate().subscribe(JavaFlowAndRsConverters$Implicits$RsSubscriberConverter$.MODULE$.asRs$extension(JavaFlowAndRsConverters$Implicits$.MODULE$.RsSubscriberConverter(javaSubscriber)));
    }

    public RsProcessorToJavaFlowAdapter(Processor<T, R> delegate) {
        this.delegate = delegate;
    }
}

