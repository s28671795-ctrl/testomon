/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.affinity;

import akka.annotation.ApiMayChange;
import akka.annotation.InternalApi;
import akka.dispatch.affinity.RejectionHandler;
import akka.dispatch.affinity.RejectionHandlerFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r3Q\u0001B\u0003\u0003\u0013-AQ!\u0007\u0001\u0005\u0002mAQ!\b\u0001\u0005ByAQA\u000e\u0001\u0005B]\u0012q\u0004\u00165s_^|en\u0014<fe\u001adwn\u001e*fU\u0016\u001cG/[8o\u0011\u0006tG\r\\3s\u0015\t1q!\u0001\u0005bM\u001aLg.\u001b;z\u0015\tA\u0011\"\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005Q\u0011\u0001B1lW\u0006\u001cB\u0001\u0001\u0007\u0013-A\u0011Q\u0002E\u0007\u0002\u001d)\tq\"A\u0003tG\u0006d\u0017-\u0003\u0002\u0012\u001d\t1\u0011I\\=SK\u001a\u0004\"a\u0005\u000b\u000e\u0003\u0015I!!F\u0003\u0003/I+'.Z2uS>t\u0007*\u00198eY\u0016\u0014h)Y2u_JL\bCA\n\u0018\u0013\tARA\u0001\tSK*,7\r^5p]\"\u000bg\u000e\u001a7fe\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001\u001d!\t\u0019\u0002!\u0001\u0004sK*,7\r\u001e\u000b\u0004?\tb\u0003CA\u0007!\u0013\t\tcB\u0001\u0003V]&$\b\"B\u0012\u0003\u0001\u0004!\u0013aB2p[6\fg\u000e\u001a\t\u0003K)j\u0011A\n\u0006\u0003O!\nA\u0001\\1oO*\t\u0011&\u0001\u0003kCZ\f\u0017BA\u0016'\u0005!\u0011VO\u001c8bE2,\u0007\"B\u0017\u0003\u0001\u0004q\u0013aB:feZL7-\u001a\t\u0003_Qj\u0011\u0001\r\u0006\u0003cI\n!bY8oGV\u0014(/\u001a8u\u0015\t\u0019\u0004&\u0001\u0003vi&d\u0017BA\u001b1\u0005=)\u00050Z2vi>\u00148+\u001a:wS\u000e,\u0017AB2sK\u0006$X\rF\u0001\u0017Q\t\u0001\u0011\b\u0005\u0002;{5\t1H\u0003\u0002=\u0013\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005yZ$\u0001D!qS6\u000b\u0017p\u00115b]\u001e,\u0007F\u0001\u0001A!\tQ\u0014)\u0003\u0002Cw\tY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
@ApiMayChange
public final class ThrowOnOverflowRejectionHandler
implements RejectionHandlerFactory,
RejectionHandler {
    @Override
    public void reject(Runnable command, ExecutorService service2) {
        throw new RejectedExecutionException(new StringBuilder(20).append("Task ").append(command).append(" rejected from ").append(service2).toString());
    }

    @Override
    public RejectionHandler create() {
        return this;
    }
}

