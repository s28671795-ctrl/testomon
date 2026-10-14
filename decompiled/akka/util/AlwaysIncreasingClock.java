/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.WallClock;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongUnaryOperator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-2Qa\u0001\u0003\u0003\r!AQ\u0001\u0007\u0001\u0005\u0002iAQ\u0001\b\u0001\u0005Bu\u0011Q#\u00117xCf\u001c\u0018J\\2sK\u0006\u001c\u0018N\\4DY>\u001c7N\u0003\u0002\u0006\r\u0005!Q\u000f^5m\u0015\u00059\u0011\u0001B1lW\u0006\u001c2\u0001A\u0005\u0015!\tQ!#D\u0001\f\u0015\taQ\"\u0001\u0004bi>l\u0017n\u0019\u0006\u0003\u001d=\t!bY8oGV\u0014(/\u001a8u\u0015\t)\u0001CC\u0001\u0012\u0003\u0011Q\u0017M^1\n\u0005MY!AC!u_6L7\rT8oOB\u0011QCF\u0007\u0002\t%\u0011q\u0003\u0002\u0002\n/\u0006dGn\u00117pG.\fa\u0001P5oSRt4\u0001\u0001\u000b\u00027A\u0011Q\u0003A\u0001\u0012GV\u0014(/\u001a8u)&lW-T5mY&\u001cH#\u0001\u0010\u0011\u0005}\u0011S\"\u0001\u0011\u000b\u0003\u0005\nQa]2bY\u0006L!a\t\u0011\u0003\t1{gn\u001a\u0015\u0003\u0001\u0015\u0002\"AJ\u0015\u000e\u0003\u001dR!\u0001\u000b\u0004\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002+O\tY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public final class AlwaysIncreasingClock
extends AtomicLong
implements WallClock {
    @Override
    public long currentTimeMillis() {
        long currentSystemTime = System.currentTimeMillis();
        return this.updateAndGet(new LongUnaryOperator(null, currentSystemTime){
            private final long currentSystemTime$1;

            public LongUnaryOperator compose(LongUnaryOperator x$1) {
                return LongUnaryOperator.super.compose(x$1);
            }

            public LongUnaryOperator andThen(LongUnaryOperator x$1) {
                return LongUnaryOperator.super.andThen(x$1);
            }

            public long applyAsLong(long time) {
                if (this.currentSystemTime$1 <= time) {
                    return time + 1L;
                }
                return this.currentSystemTime$1;
            }
            {
                this.currentSystemTime$1 = currentSystemTime$1;
            }
        });
    }
}

