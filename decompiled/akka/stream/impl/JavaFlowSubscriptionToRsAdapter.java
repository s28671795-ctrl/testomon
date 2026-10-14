/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import java.util.concurrent.Flow;
import org.reactivestreams.Subscription;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005!3QAB\u0004\u0003\u00175A\u0001B\b\u0001\u0003\u0006\u0004%\t\u0001\t\u0005\tY\u0001\u0011\t\u0011)A\u0005C!)Q\u0006\u0001C\u0001]!)!\u0007\u0001C!g!)!\b\u0001C!w\ty\"*\u0019<b\r2|woU;cg\u000e\u0014\u0018\u000e\u001d;j_:$vNU:BI\u0006\u0004H/\u001a:\u000b\u0005!I\u0011\u0001B5na2T!AC\u0006\u0002\rM$(/Z1n\u0015\u0005a\u0011\u0001B1lW\u0006\u001c2\u0001\u0001\b\u0017!\tyA#D\u0001\u0011\u0015\t\t\"#\u0001\u0003mC:<'\"A\n\u0002\t)\fg/Y\u0005\u0003+A\u0011aa\u00142kK\u000e$\bCA\f\u001d\u001b\u0005A\"BA\r\u001b\u0003=\u0011X-Y2uSZ,7\u000f\u001e:fC6\u001c(\"A\u000e\u0002\u0007=\u0014x-\u0003\u0002\u001e1\ta1+\u001e2tGJL\u0007\u000f^5p]\u0006AA-\u001a7fO\u0006$Xm\u0001\u0001\u0016\u0003\u0005\u0002\"A\t\u0016\u000f\u0005\rBS\"\u0001\u0013\u000b\u0005\u00152\u0013AC2p]\u000e,(O]3oi*\u0011qEE\u0001\u0005kRLG.\u0003\u0002*I\u0005!a\t\\8x\u0013\ti2F\u0003\u0002*I\u0005IA-\u001a7fO\u0006$X\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005=\n\u0004C\u0001\u0019\u0001\u001b\u00059\u0001\"\u0002\u0010\u0004\u0001\u0004\t\u0013AB2b]\u000e,G\u000eF\u00015!\t)\u0004(D\u00017\u0015\u00059\u0014!B:dC2\f\u0017BA\u001d7\u0005\u0011)f.\u001b;\u0002\u000fI,\u0017/^3tiR\u0011A\u0007\u0010\u0005\u0006{\u0015\u0001\rAP\u0001\u0002]B\u0011QgP\u0005\u0003\u0001Z\u0012A\u0001T8oO\"\u0012\u0001A\u0011\t\u0003\u0007\u001ak\u0011\u0001\u0012\u0006\u0003\u000b.\t!\"\u00198o_R\fG/[8o\u0013\t9EIA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class JavaFlowSubscriptionToRsAdapter
implements Subscription {
    private final Flow.Subscription delegate;

    public Flow.Subscription delegate() {
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

    public JavaFlowSubscriptionToRsAdapter(Flow.Subscription delegate) {
        this.delegate = delegate;
    }
}

