/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.affinity;

import akka.annotation.ApiMayChange;
import akka.annotation.InternalApi;
import akka.dispatch.affinity.QueueSelector;
import akka.dispatch.affinity.QueueSelectorFactory;
import akka.util.Helpers$;
import akka.util.Helpers$Requiring$;
import akka.util.ImmutableIntMap;
import akka.util.ImmutableIntMap$;
import com.typesafe.config.Config;
import java.io.Serializable;
import scala.Function0;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u00113Q\u0001C\u0005\u0003\u001b=A\u0001B\u0007\u0001\u0003\u0006\u0004%\t\u0001\b\u0005\tM\u0001\u0011\t\u0011)A\u0005;!)q\u0005\u0001C\u0001Q!91\u0006\u0001b\u0001\n\u001ba\u0003BB\u0018\u0001A\u00035Q\u0006\u0003\u00041\u0001\u0001\u0006i!\r\u0005\u0006i\u0001!\t%\u000e\u0002\u001a\r\u0006L'\u000fR5tiJL'-\u001e;j_:D\u0015m\u001d5DC\u000eDWM\u0003\u0002\u000b\u0017\u0005A\u0011M\u001a4j]&$\u0018P\u0003\u0002\r\u001b\u0005AA-[:qCR\u001c\u0007NC\u0001\u000f\u0003\u0011\t7n[1\u0014\u0007\u0001\u0001b\u0003\u0005\u0002\u0012)5\t!CC\u0001\u0014\u0003\u0015\u00198-\u00197b\u0013\t)\"C\u0001\u0004B]f\u0014VM\u001a\t\u0003/ai\u0011!C\u0005\u00033%\u0011A#U;fk\u0016\u001cV\r\\3di>\u0014h)Y2u_JL\u0018AB2p]\u001aLwm\u0001\u0001\u0016\u0003u\u0001\"A\b\u0013\u000e\u0003}Q!A\u0007\u0011\u000b\u0005\u0005\u0012\u0013\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u0003\r\n1aY8n\u0013\t)sD\u0001\u0004D_:4\u0017nZ\u0001\bG>tg-[4!\u0003\u0019a\u0014N\\5u}Q\u0011\u0011F\u000b\t\u0003/\u0001AQAG\u0002A\u0002u\tA$T1y\r\u0006L'\u000fR5tiJL'-\u001e;j_:$\u0006N]3tQ>dG-F\u0001.\u001f\u0005qSD\u0001\u0005\u0001\u0003ui\u0015\r\u001f$bSJ$\u0015n\u001d;sS\n,H/[8o)\"\u0014Xm\u001d5pY\u0012\u0004\u0013!\u00074bSJ$\u0015n\u001d;sS\n,H/[8o)\"\u0014Xm\u001d5pY\u0012\u0004\"!\u0005\u001a\n\u0005M\u0012\"aA%oi\u000611M]3bi\u0016$\u0012A\u000e\t\u0003/]J!\u0001O\u0005\u0003\u001bE+X-^3TK2,7\r^8sQ\t\u0001!\b\u0005\u0002<}5\tAH\u0003\u0002>\u001b\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005}b$\u0001D!qS6\u000b\u0017p\u00115b]\u001e,\u0007F\u0001\u0001B!\tY$)\u0003\u0002Dy\tY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
@ApiMayChange
public final class FairDistributionHashCache
implements QueueSelectorFactory {
    private final Config config;
    public final int akka$dispatch$affinity$FairDistributionHashCache$$fairDistributionThreshold;

    public Config config() {
        return this.config;
    }

    private final int MaxFairDistributionThreshold() {
        return 2048;
    }

    @Override
    public QueueSelector create() {
        return new QueueSelector(this){
            private final /* synthetic */ FairDistributionHashCache $outer;

            public String toString() {
                return new StringBuilder(55).append("FairDistributionHashCache(fairDistributionThreshold = ").append(this.$outer.akka$dispatch$affinity$FairDistributionHashCache$$fairDistributionThreshold).append(")").toString();
            }

            private final int improve(int h2) {
                return Integer.MAX_VALUE & Integer.reverseBytes(h2 * -1640532531) * -1640532531;
            }

            public final int getQueue(Runnable command, int queues) {
                int runnableHash = command.hashCode();
                if (this.$outer.akka$dispatch$affinity$FairDistributionHashCache$$fairDistributionThreshold == 0) {
                    return this.improve(runnableHash) % queues;
                }
                return this.cacheLookup$1((ImmutableIntMap)this.get(), runnableHash, runnableHash, queues);
            }

            private final int cacheLookup$1(ImmutableIntMap prev, int hash, int runnableHash$1, int queues$1) {
                int existingIndex;
                while ((existingIndex = prev.get(runnableHash$1)) < 0) {
                    if (prev.size() > this.$outer.akka$dispatch$affinity$FairDistributionHashCache$$fairDistributionThreshold) {
                        return this.improve(hash) % queues$1;
                    }
                    int index = prev.size() % queues$1;
                    if (this.compareAndSet(prev, prev.updated(runnableHash$1, index))) {
                        return index;
                    }
                    prev = (ImmutableIntMap)this.get();
                }
                return existingIndex;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super(ImmutableIntMap$.MODULE$.empty());
            }
        };
    }

    public FairDistributionHashCache(Config config) {
        this.config = config;
        this.akka$dispatch$affinity$FairDistributionHashCache$$fairDistributionThreshold = BoxesRunTime.unboxToInt(Helpers$Requiring$.MODULE$.requiring$extension(Helpers$.MODULE$.Requiring(BoxesRunTime.boxToInteger(config.getInt("fair-work-distribution.threshold"))), thr -> 0 <= thr && thr <= 2048, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(69).append("fair-work-distribution.threshold must be between 0 and ").append(2048).toString()));
    }
}

