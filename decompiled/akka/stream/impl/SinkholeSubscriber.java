/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.Done;
import akka.Done$;
import akka.annotation.InternalApi;
import akka.stream.impl.ReactiveStreamsCompliance$;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import scala.concurrent.Promise;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-4Q\u0001C\u0005\u0003\u001b=A\u0001\u0002\r\u0001\u0003\u0002\u0003\u0006I!\r\u0005\u0006w\u0001!\t\u0001\u0010\u0005\u0007\u0001\u0002\u0001\u000b\u0015B!\t\u000b\u0011\u0003A\u0011I#\t\u000b9\u0003A\u0011I(\t\u000by\u0003A\u0011I0\t\u000b\u0001\u0004A\u0011I1\u0003%MKgn\u001b5pY\u0016\u001cVOY:de&\u0014WM\u001d\u0006\u0003\u0015-\tA![7qY*\u0011A\"D\u0001\u0007gR\u0014X-Y7\u000b\u00039\tA!Y6lCV\u0011\u0001cI\n\u0004\u0001EI\u0002C\u0001\n\u0018\u001b\u0005\u0019\"B\u0001\u000b\u0016\u0003\u0011a\u0017M\\4\u000b\u0003Y\tAA[1wC&\u0011\u0001d\u0005\u0002\u0007\u001f\nTWm\u0019;\u0011\u0007iy\u0012%D\u0001\u001c\u0015\taR$A\bsK\u0006\u001cG/\u001b<fgR\u0014X-Y7t\u0015\u0005q\u0012aA8sO&\u0011\u0001e\u0007\u0002\u000b'V\u00147o\u0019:jE\u0016\u0014\bC\u0001\u0012$\u0019\u0001!Q\u0001\n\u0001C\u0002\u0019\u0012\u0011\u0001V\u0002\u0001#\t9S\u0006\u0005\u0002)W5\t\u0011FC\u0001+\u0003\u0015\u00198-\u00197b\u0013\ta\u0013FA\u0004O_RD\u0017N\\4\u0011\u0005!r\u0013BA\u0018*\u0005\r\te._\u0001\ro\",gnQ8na2,G/\u001a\t\u0004eU:T\"A\u001a\u000b\u0005QJ\u0013AC2p]\u000e,(O]3oi&\u0011ag\r\u0002\b!J|W.[:f!\tA\u0014(D\u0001\u000e\u0013\tQTB\u0001\u0003E_:,\u0017A\u0002\u001fj]&$h\b\u0006\u0002>\u007fA\u0019a\bA\u0011\u000e\u0003%AQ\u0001\r\u0002A\u0002E\nqA];o]&tw\r\u0005\u0002)\u0005&\u00111)\u000b\u0002\b\u0005>|G.Z1o\u0003-ygnU;cg\u000e\u0014\u0018NY3\u0015\u0005\u0019K\u0005C\u0001\u0015H\u0013\tA\u0015F\u0001\u0003V]&$\b\"\u0002&\u0005\u0001\u0004Y\u0015aA:vEB\u0011!\u0004T\u0005\u0003\u001bn\u0011AbU;cg\u000e\u0014\u0018\u000e\u001d;j_:\fqa\u001c8FeJ|'\u000f\u0006\u0002G!\")\u0011+\u0002a\u0001%\u0006)1-Y;tKB\u00111k\u0017\b\u0003)fs!!\u0016-\u000e\u0003YS!aV\u0013\u0002\rq\u0012xn\u001c;?\u0013\u0005Q\u0013B\u0001.*\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001X/\u0003\u0013QC'o\\<bE2,'B\u0001.*\u0003)ygnQ8na2,G/\u001a\u000b\u0002\r\u00061qN\u001c(fqR$\"A\u00122\t\u000b\r<\u0001\u0019A\u0011\u0002\u000f\u0015dW-\\3oi\"\u0012\u0001!\u001a\t\u0003M&l\u0011a\u001a\u0006\u0003Q6\t!\"\u00198o_R\fG/[8o\u0013\tQwMA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class SinkholeSubscriber<T>
implements Subscriber<T> {
    private final Promise<Done> whenComplete;
    private boolean running;

    @Override
    public void onSubscribe(Subscription sub) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullSubscription(sub);
        if (this.running) {
            sub.cancel();
            return;
        }
        this.running = true;
        sub.request(Long.MAX_VALUE);
    }

    @Override
    public void onError(Throwable cause) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullException(cause);
        this.whenComplete.tryFailure(cause);
    }

    @Override
    public void onComplete() {
        this.whenComplete.trySuccess(Done$.MODULE$);
    }

    @Override
    public void onNext(T element) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(element);
    }

    public SinkholeSubscriber(Promise<Done> whenComplete) {
        this.whenComplete = whenComplete;
        this.running = false;
    }
}

