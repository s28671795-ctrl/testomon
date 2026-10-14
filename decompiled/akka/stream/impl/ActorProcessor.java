/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.stream.impl.ActorProcessor$;
import akka.stream.impl.ActorPublisher;
import akka.stream.impl.ActorSubscriberMessage;
import akka.stream.impl.ActorSubscriberMessage$OnComplete$;
import akka.stream.impl.ReactiveStreamsCompliance$;
import org.reactivestreams.Processor;
import org.reactivestreams.Subscription;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y<aa\u0003\u0007\t\u0002A\u0011bA\u0002\u000b\r\u0011\u0003\u0001R\u0003C\u0003\u001d\u0003\u0011\u0005a\u0004C\u0003 \u0003\u0011\u0005\u0001EB\u0003\u0015\u0019\u0001\u00012\u0005C\u0005\u000e\t\t\u0005\t\u0015!\u0003?\t\")A\u0004\u0002C\u0001\u000b\")\u0001\n\u0002C!\u0013\")!\u000b\u0002C!'\")!\r\u0002C!G\")A\r\u0002C!K\u0006q\u0011i\u0019;peB\u0013xnY3tg>\u0014(BA\u0007\u000f\u0003\u0011IW\u000e\u001d7\u000b\u0005=\u0001\u0012AB:ue\u0016\fWNC\u0001\u0012\u0003\u0011\t7n[1\u0011\u0005M\tQ\"\u0001\u0007\u0003\u001d\u0005\u001bGo\u001c:Qe>\u001cWm]:peN\u0011\u0011A\u0006\t\u0003/ii\u0011\u0001\u0007\u0006\u00023\u0005)1oY1mC&\u00111\u0004\u0007\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012AE\u0001\u0006CB\u0004H._\u000b\u0004CA\u0014HC\u0001\u0012t!\u0011\u0019Ba\\9\u0016\u0007\u0011b$fE\u0002\u0005KM\u00022a\u0005\u0014)\u0013\t9CB\u0001\bBGR|'\u000fU;cY&\u001c\b.\u001a:\u0011\u0005%RC\u0002\u0001\u0003\u0006W\u0011\u0011\r\u0001\f\u0002\u0002\u001fF\u0011Q\u0006\r\t\u0003/9J!a\f\r\u0003\u000f9{G\u000f[5oOB\u0011q#M\u0005\u0003ea\u00111!\u00118z!\u0011!\u0014h\u000f\u0015\u000e\u0003UR!AN\u001c\u0002\u001fI,\u0017m\u0019;jm\u0016\u001cHO]3b[NT\u0011\u0001O\u0001\u0004_J<\u0017B\u0001\u001e6\u0005%\u0001&o\\2fgN|'\u000f\u0005\u0002*y\u0011)Q\b\u0002b\u0001Y\t\t\u0011\n\u0005\u0002@\u00056\t\u0001I\u0003\u0002B!\u0005)\u0011m\u0019;pe&\u00111\t\u0011\u0002\t\u0003\u000e$xN\u001d*fM&\u0011QB\n\u000b\u0003\r\u001e\u0003Ba\u0005\u0003<Q!)QB\u0002a\u0001}\u0005YqN\\*vEN\u001c'/\u001b2f)\tQU\n\u0005\u0002\u0018\u0017&\u0011A\n\u0007\u0002\u0005+:LG\u000fC\u0003O\u000f\u0001\u0007q*A\u0001t!\t!\u0004+\u0003\u0002Rk\ta1+\u001e2tGJL\u0007\u000f^5p]\u00069qN\\#se>\u0014HC\u0001&U\u0011\u0015)\u0006\u00021\u0001W\u0003\u0005!\bCA,`\u001d\tAVL\u0004\u0002Z96\t!L\u0003\u0002\\;\u00051AH]8pizJ\u0011!G\u0005\u0003=b\tq\u0001]1dW\u0006<W-\u0003\u0002aC\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0003=b\t!b\u001c8D_6\u0004H.\u001a;f)\u0005Q\u0015AB8o\u001d\u0016DH\u000f\u0006\u0002KM\")qM\u0003a\u0001w\u0005!Q\r\\3nQ\t!\u0011\u000e\u0005\u0002k[6\t1N\u0003\u0002m!\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u00059\\'aC%oi\u0016\u0014h.\u00197Ba&\u0004\"!\u000b9\u0005\u000bu\u001a!\u0019\u0001\u0017\u0011\u0005%\u0012H!B\u0016\u0004\u0005\u0004a\u0003\"B\u0007\u0004\u0001\u0004q\u0004FA\u0001jQ\t\u0001\u0011\u000e")
@InternalApi
public class ActorProcessor<I, O>
extends ActorPublisher<O>
implements Processor<I, O> {
    public static <I, O> ActorProcessor<I, O> apply(ActorRef impl) {
        return ActorProcessor$.MODULE$.apply(impl);
    }

    @Override
    public void onSubscribe(Subscription s2) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullSubscription(s2);
        ActorSubscriberMessage.OnSubscribe x$1 = new ActorSubscriberMessage.OnSubscribe(s2);
        ActorRef x$2 = super.impl().$bang$default$2(x$1);
        super.impl().$bang(x$1, x$2);
    }

    @Override
    public void onError(Throwable t2) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullException(t2);
        ActorSubscriberMessage.OnError x$1 = new ActorSubscriberMessage.OnError(t2);
        ActorRef x$2 = super.impl().$bang$default$2(x$1);
        super.impl().$bang(x$1, x$2);
    }

    @Override
    public void onComplete() {
        ActorSubscriberMessage$OnComplete$ x$1 = ActorSubscriberMessage$OnComplete$.MODULE$;
        ActorRef x$2 = super.impl().$bang$default$2(x$1);
        super.impl().$bang(x$1, x$2);
    }

    @Override
    public void onNext(I elem) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(elem);
        ActorSubscriberMessage.OnNext x$1 = new ActorSubscriberMessage.OnNext(elem);
        ActorRef x$2 = super.impl().$bang$default$2(x$1);
        super.impl().$bang(x$1, x$2);
    }

    public ActorProcessor(ActorRef impl) {
        super(impl);
    }
}

