/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.pattern.AutoReset;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class AutoReset$
extends AbstractFunction1<FiniteDuration, AutoReset>
implements Serializable {
    public static final AutoReset$ MODULE$ = new AutoReset$();

    @Override
    public final String toString() {
        return "AutoReset";
    }

    @Override
    public AutoReset apply(FiniteDuration resetBackoff) {
        return new AutoReset(resetBackoff);
    }

    public Option<FiniteDuration> unapply(AutoReset x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<FiniteDuration>(x$0.resetBackoff());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AutoReset$.class);
    }

    private AutoReset$() {
    }
}

