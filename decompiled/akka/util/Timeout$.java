/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import akka.util.Timeout;
import java.io.Serializable;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.ModuleSerializationProxy;

public final class Timeout$
implements Serializable {
    public static final Timeout$ MODULE$ = new Timeout$();
    private static final Timeout zero = new Timeout(Duration$.MODULE$.Zero());

    public Timeout zero() {
        return zero;
    }

    public Timeout apply(long length, TimeUnit unit) {
        return new Timeout(length, unit);
    }

    public Timeout create(Duration duration) {
        return new Timeout(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(duration)));
    }

    public Timeout durationToTimeout(FiniteDuration duration) {
        return new Timeout(duration);
    }

    public Timeout apply(FiniteDuration duration) {
        return new Timeout(duration);
    }

    public Option<FiniteDuration> unapply(Timeout x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<FiniteDuration>(x$0.duration());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Timeout$.class);
    }

    private Timeout$() {
    }
}

