/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.pattern.FutureRef;
import akka.pattern.PromiseRef;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00013Qa\u0002\u0005\u0001\u00151A\u0001b\n\u0001\u0003\u0006\u0004%\t\u0001\u000b\u0005\t_\u0001\u0011\t\u0011)A\u0005S!A\u0001\u0007\u0001BC\u0002\u0013\u0005\u0011\u0007\u0003\u00059\u0001\t\u0005\t\u0015!\u00033\u0011\u0015I\u0004\u0001\"\u0001;\u0011\u0015q\u0004\u0001\"\u0001@\u00059\u0001&o\\7jg\u0016\u0014VMZ%na2T!!\u0003\u0006\u0002\u000fA\fG\u000f^3s]*\t1\"\u0001\u0003bW.\fWCA\u0007\u001b'\u0011\u0001a\u0002\u0006\u0013\u0011\u0005=\u0011R\"\u0001\t\u000b\u0003E\tQa]2bY\u0006L!a\u0005\t\u0003\r\u0005s\u0017PU3g!\r)b\u0003G\u0007\u0002\u0011%\u0011q\u0003\u0003\u0002\u000b!J|W.[:f%\u00164\u0007CA\r\u001b\u0019\u0001!Qa\u0007\u0001C\u0002u\u0011\u0011\u0001V\u0002\u0001#\tq\u0012\u0005\u0005\u0002\u0010?%\u0011\u0001\u0005\u0005\u0002\b\u001d>$\b.\u001b8h!\ty!%\u0003\u0002$!\t\u0019\u0011I\\=\u0011\u0007U)\u0003$\u0003\u0002'\u0011\tIa)\u001e;ve\u0016\u0014VMZ\u0001\u0004e\u00164W#A\u0015\u0011\u0005)jS\"A\u0016\u000b\u00051R\u0011!B1di>\u0014\u0018B\u0001\u0018,\u0005!\t5\r^8s%\u00164\u0017\u0001\u0002:fM\u0002\nq\u0001\u001d:p[&\u001cX-F\u00013!\r\u0019d\u0007G\u0007\u0002i)\u0011Q\u0007E\u0001\u000bG>t7-\u001e:sK:$\u0018BA\u001c5\u0005\u001d\u0001&o\\7jg\u0016\f\u0001\u0002\u001d:p[&\u001cX\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007mbT\bE\u0002\u0016\u0001aAQaJ\u0003A\u0002%BQ\u0001M\u0003A\u0002I\n1\u0002^8GkR,(/\u001a*fMV\tA\u0005")
public class PromiseRefImpl<T>
implements PromiseRef<T>,
FutureRef<T> {
    private final ActorRef ref;
    private final Promise<T> promise;

    @Override
    public final Future<T> future() {
        return PromiseRef.future$(this);
    }

    @Override
    public ActorRef ref() {
        return this.ref;
    }

    @Override
    public Promise<T> promise() {
        return this.promise;
    }

    @Override
    public FutureRef<T> toFutureRef() {
        return this;
    }

    public PromiseRefImpl(ActorRef ref, Promise<T> promise) {
        this.ref = ref;
        this.promise = promise;
        PromiseRef.$init$(this);
    }
}

