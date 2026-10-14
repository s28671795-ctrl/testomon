/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.Clock;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005=2Q\u0001B\u0003\u0003\u000f%AQ\u0001\u0006\u0001\u0005\u0002YAQ\u0001\u0007\u0001\u0005BeAQ!\b\u0001\u0005By\u0011\u0011BT1o_\u000ecwnY6\u000b\u0005\u00199\u0011\u0001B;uS2T\u0011\u0001C\u0001\u0005C.\\\u0017mE\u0002\u0001\u0015A\u0001\"a\u0003\b\u000e\u00031Q\u0011!D\u0001\u0006g\u000e\fG.Y\u0005\u0003\u001f1\u0011a!\u00118z%\u00164\u0007CA\t\u0013\u001b\u0005)\u0011BA\n\u0006\u0005\u0015\u0019En\\2l\u0003\u0019a\u0014N\\5u}\r\u0001A#A\f\u0011\u0005E\u0001\u0011aC2veJ,g\u000e\u001e+j[\u0016$\u0012A\u0007\t\u0003\u0017mI!\u0001\b\u0007\u0003\t1{gnZ\u0001\fK\u0006\u0014H.[3s)&lW\r\u0006\u0002\u001b?!)\u0001e\u0001a\u0001C\u0005AA-\u001e:bi&|g\u000e\u0005\u0002#M5\t1E\u0003\u0002!I)\u0011Q\u0005D\u0001\u000bG>t7-\u001e:sK:$\u0018BA\u0014$\u000591\u0015N\\5uK\u0012+(/\u0019;j_:D#\u0001A\u0015\u0011\u0005)jS\"A\u0016\u000b\u00051:\u0011AC1o]>$\u0018\r^5p]&\u0011af\u000b\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
@InternalApi
public final class NanoClock
implements Clock {
    @Override
    public long currentTime() {
        return System.nanoTime();
    }

    @Override
    public long earlierTime(FiniteDuration duration) {
        return this.currentTime() - duration.toNanos();
    }
}

