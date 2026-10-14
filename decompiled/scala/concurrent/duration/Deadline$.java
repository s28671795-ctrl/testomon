/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent.duration;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.concurrent.duration.Deadline;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.ModuleSerializationProxy;

public final class Deadline$
implements Serializable {
    public static final Deadline$ MODULE$ = new Deadline$();

    public Deadline now() {
        TimeUnit apply_unit = TimeUnit.NANOSECONDS;
        long apply_length = System.nanoTime();
        Object var3_1 = null;
        FiniteDuration apply_time = new FiniteDuration(apply_length, apply_unit);
        return new Deadline(apply_time);
    }

    public Deadline apply(FiniteDuration time) {
        return new Deadline(time);
    }

    public Option<FiniteDuration> unapply(Deadline x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<FiniteDuration>(x$0.time());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Deadline$.class);
    }

    private Deadline$() {
    }
}

