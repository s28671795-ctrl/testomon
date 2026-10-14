/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A2Aa\u0001\u0003\u0001\u0013!)!\u0004\u0001C\u00017!)a\u0004\u0001C\u0001?\ta2+\u00198f%\u0016TWm\u0019;fI\u0016CXmY;uS>t\u0007*\u00198eY\u0016\u0014(BA\u0003\u0007\u0003!!\u0017n\u001d9bi\u000eD'\"A\u0004\u0002\t\u0005\\7.Y\u0002\u0001'\r\u0001!B\u0005\t\u0003\u0017Ai\u0011\u0001\u0004\u0006\u0003\u001b9\tA\u0001\\1oO*\tq\"\u0001\u0003kCZ\f\u0017BA\t\r\u0005\u0019y%M[3diB\u00111\u0003G\u0007\u0002))\u0011QCF\u0001\u000bG>t7-\u001e:sK:$(BA\f\u000f\u0003\u0011)H/\u001b7\n\u0005e!\"\u0001\u0007*fU\u0016\u001cG/\u001a3Fq\u0016\u001cW\u000f^5p]\"\u000bg\u000e\u001a7fe\u00061A(\u001b8jiz\"\u0012\u0001\b\t\u0003;\u0001i\u0011\u0001B\u0001\u0012e\u0016TWm\u0019;fI\u0016CXmY;uS>tGc\u0001\u0011'WA\u0011\u0011\u0005J\u0007\u0002E)\t1%A\u0003tG\u0006d\u0017-\u0003\u0002&E\t!QK\\5u\u0011\u00159#\u00011\u0001)\u0003!\u0011XO\u001c8bE2,\u0007CA\u0006*\u0013\tQCB\u0001\u0005Sk:t\u0017M\u00197f\u0011\u0015a#\u00011\u0001.\u0003I!\bN]3bIB{w\u000e\\#yK\u000e,Ho\u001c:\u0011\u0005Mq\u0013BA\u0018\u0015\u0005I!\u0006N]3bIB{w\u000e\\#yK\u000e,Ho\u001c:")
public class SaneRejectedExecutionHandler
implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable runnable2, ThreadPoolExecutor threadPoolExecutor) {
        if (threadPoolExecutor.isShutdown()) {
            throw new RejectedExecutionException("Shutdown");
        }
        runnable2.run();
    }
}

