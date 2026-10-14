/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.actor.Scheduler;
import akka.annotation.InternalApi;
import akka.util.Clock;
import java.util.concurrent.atomic.AtomicLong;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;
import scala.math.package$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q4QAE\n\u0003+]A\u0001B\t\u0001\u0003\u0002\u0003\u0006I\u0001\n\u0005\tY\u0001\u0011\t\u0011)A\u0005[!A1\u0007\u0001B\u0001B\u0003%A\u0007C\u00039\u0001\u0011\u0005\u0011\bC\u0004?\u0001\t\u0007I\u0011B \t\r)\u0003\u0001\u0015!\u0003A\u0011\u001dY\u0005\u00011A\u0005\n1Cq\u0001\u0015\u0001A\u0002\u0013%\u0011\u000b\u0003\u0004X\u0001\u0001\u0006K!\u0014\u0005\b9\u0002\u0011\r\u0011\"\u0003M\u0011\u0019i\u0006\u0001)A\u0005\u001b\"9a\f\u0001a\u0001\n\u0003y\u0006bB2\u0001\u0001\u0004%\t\u0001\u001a\u0005\u0007M\u0002\u0001\u000b\u0015\u00021\t\u000b!\u0004A\u0011B5\t\u000bE\u0004A\u0011\t:\t\u000bM\u0004A\u0011\t;\u0003\u001dM\u001b\u0007.\u001a3vY\u0016$7\t\\8dW*\u0011A#F\u0001\u0005kRLGNC\u0001\u0017\u0003\u0011\t7n[1\u0014\u0007\u0001Ab\u0004\u0005\u0002\u001a95\t!DC\u0001\u001c\u0003\u0015\u00198-\u00197b\u0013\ti\"D\u0001\u0004B]f\u0014VM\u001a\t\u0003?\u0001j\u0011aE\u0005\u0003CM\u0011Qa\u00117pG.\fa\"\u001e9eCR,\u0017J\u001c;feZ\fGn\u0001\u0001\u0011\u0005\u0015RS\"\u0001\u0014\u000b\u0005\u001dB\u0013\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\u0005%R\u0012AC2p]\u000e,(O]3oi&\u00111F\n\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o\u0003%\u00198\r[3ek2,'\u000f\u0005\u0002/c5\tqF\u0003\u00021+\u0005)\u0011m\u0019;pe&\u0011!g\f\u0002\n'\u000eDW\rZ;mKJ\f\u0001#\u001a=fGV$\u0018n\u001c8D_:$X\r\u001f;\u0011\u0005U2T\"\u0001\u0015\n\u0005]B#\u0001E#yK\u000e,H/[8o\u0007>tG/\u001a=u\u0003\u0019a\u0014N\\5u}Q!!h\u000f\u001f>!\ty\u0002\u0001C\u0003#\t\u0001\u0007A\u0005C\u0003-\t\u0001\u0007Q\u0006C\u00034\t\u0001\u0007A'\u0001\u0003uS6,W#\u0001!\u0011\u0005\u0005CU\"\u0001\"\u000b\u0005\r#\u0015AB1u_6L7M\u0003\u0002*\u000b*\u0011AC\u0012\u0006\u0002\u000f\u0006!!.\u0019<b\u0013\tI%I\u0001\u0006Bi>l\u0017n\u0019'p]\u001e\fQ\u0001^5nK\u0002\n1\"\u001e9eCR,G\rV5nKV\tQ\n\u0005\u0002\u001a\u001d&\u0011qJ\u0007\u0002\u0005\u0019>tw-A\bva\u0012\fG/\u001a3US6,w\fJ3r)\t\u0011V\u000b\u0005\u0002\u001a'&\u0011AK\u0007\u0002\u0005+:LG\u000fC\u0004W\u0011\u0005\u0005\t\u0019A'\u0002\u0007a$\u0013'\u0001\u0007va\u0012\fG/\u001a3US6,\u0007\u0005\u000b\u0002\n3B\u0011\u0011DW\u0005\u00037j\u0011\u0001B^8mCRLG.Z\u0001\r[\u0006D\u0018J\\2sK6,g\u000e^\u0001\u000e[\u0006D\u0018J\\2sK6,g\u000e\u001e\u0011\u0002'5\f\u00070\u00138de\u0016lWM\u001c;SK\u0006\u001c\u0007.\u001a3\u0016\u0003\u0001\u0004\"!G1\n\u0005\tT\"a\u0002\"p_2,\u0017M\\\u0001\u0018[\u0006D\u0018J\\2sK6,g\u000e\u001e*fC\u000eDW\rZ0%KF$\"AU3\t\u000fYk\u0011\u0011!a\u0001A\u0006!R.\u0019=J]\u000e\u0014X-\\3oiJ+\u0017m\u00195fI\u0002B#AD-\u0002\rU\u0004H-\u0019;f)\u0005\u0011\u0006FA\bl!\taw.D\u0001n\u0015\tq'$\u0001\u0006b]:|G/\u0019;j_:L!\u0001]7\u0003\u000fQ\f\u0017\u000e\u001c:fG\u0006Y1-\u001e:sK:$H+[7f)\u0005i\u0015aC3be2LWM\u001d+j[\u0016$\"!T;\t\u000b\u001d\n\u0002\u0019\u0001\u0013)\u0005\u00019\bC\u0001={\u001b\u0005I(B\u00018\u0016\u0013\tY\u0018PA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class ScheduledClock
implements Clock {
    private final AtomicLong time = new AtomicLong(System.nanoTime());
    private volatile long updatedTime = this.time().get();
    private final long maxIncrement;
    private volatile boolean maxIncrementReached;

    private AtomicLong time() {
        return this.time;
    }

    private long updatedTime() {
        return this.updatedTime;
    }

    private void updatedTime_$eq(long x$1) {
        this.updatedTime = x$1;
    }

    private long maxIncrement() {
        return this.maxIncrement;
    }

    public boolean maxIncrementReached() {
        return this.maxIncrementReached;
    }

    public void maxIncrementReached_$eq(boolean x$1) {
        this.maxIncrementReached = x$1;
    }

    private void update() {
        long newTime;
        long current;
        do {
            current = this.time().get();
            long now = System.nanoTime();
            long l = newTime = now - current >= 0L ? now : current;
        } while (!this.time().compareAndSet(current, newTime));
        this.updatedTime_$eq(newTime);
        this.maxIncrementReached_$eq(false);
    }

    @Override
    public long currentTime() {
        long now;
        block1: {
            if (this.maxIncrementReached()) {
                return this.time().get();
            }
            now = this.time().incrementAndGet();
            if (now - this.updatedTime() < this.maxIncrement()) break block1;
            this.maxIncrementReached_$eq(true);
        }
        return now;
    }

    @Override
    public long earlierTime(FiniteDuration duration) {
        return this.time().get() - duration.toNanos();
    }

    public ScheduledClock(FiniteDuration updateInterval, Scheduler scheduler, ExecutionContext executionContext) {
        this.maxIncrement = package$.MODULE$.max(updateInterval.toNanos() - 100000L, 0L);
        this.maxIncrementReached = false;
        scheduler.scheduleWithFixedDelay(updateInterval, updateInterval, () -> this.update(), executionContext);
    }
}

