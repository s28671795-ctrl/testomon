/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import java.util.concurrent.Flow;
import org.reactivestreams.Subscription;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005!3QAB\u0004\u0003\u00175A\u0001B\t\u0001\u0003\u0006\u0004%\t\u0001\n\u0005\tY\u0001\u0011\t\u0011)A\u0005K!)Q\u0006\u0001C\u0001]!)!\u0007\u0001C!g!)!\b\u0001C!w\ty\"k]*vEN\u001c'/\u001b9uS>tGk\u001c&bm\u00064En\\<BI\u0006\u0004H/\u001a:\u000b\u0005!I\u0011\u0001B5na2T!AC\u0006\u0002\rM$(/Z1n\u0015\u0005a\u0011\u0001B1lW\u0006\u001c2\u0001\u0001\b\u0017!\tyA#D\u0001\u0011\u0015\t\t\"#\u0001\u0003mC:<'\"A\n\u0002\t)\fg/Y\u0005\u0003+A\u0011aa\u00142kK\u000e$\bCA\f \u001d\tAR$D\u0001\u001a\u0015\tQ2$\u0001\u0006d_:\u001cWO\u001d:f]RT!\u0001\b\n\u0002\tU$\u0018\u000e\\\u0005\u0003=e\tAA\u00127po&\u0011\u0001%\t\u0002\r'V\u00147o\u0019:jaRLwN\u001c\u0006\u0003=e\t\u0001\u0002Z3mK\u001e\fG/Z\u0002\u0001+\u0005)\u0003C\u0001\u0014,\u001b\u00059#B\u0001\u0015*\u0003=\u0011X-Y2uSZ,7\u000f\u001e:fC6\u001c(\"\u0001\u0016\u0002\u0007=\u0014x-\u0003\u0002!O\u0005IA-\u001a7fO\u0006$X\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005=\n\u0004C\u0001\u0019\u0001\u001b\u00059\u0001\"\u0002\u0012\u0004\u0001\u0004)\u0013AB2b]\u000e,G\u000eF\u00015!\t)\u0004(D\u00017\u0015\u00059\u0014!B:dC2\f\u0017BA\u001d7\u0005\u0011)f.\u001b;\u0002\u000fI,\u0017/^3tiR\u0011A\u0007\u0010\u0005\u0006{\u0015\u0001\rAP\u0001\u0002]B\u0011QgP\u0005\u0003\u0001Z\u0012A\u0001T8oO\"\u0012\u0001A\u0011\t\u0003\u0007\u001ak\u0011\u0001\u0012\u0006\u0003\u000b.\t!\"\u00198o_R\fG/[8o\u0013\t9EIA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class RsSubscriptionToJavaFlowAdapter
implements Flow.Subscription {
    private final Subscription delegate;

    public Subscription delegate() {
        return this.delegate;
    }

    @Override
    public void cancel() {
        this.delegate().cancel();
    }

    @Override
    public void request(long n) {
        this.delegate().request(n);
    }

    public RsSubscriptionToJavaFlowAdapter(Subscription delegate) {
        this.delegate = delegate;
    }
}

