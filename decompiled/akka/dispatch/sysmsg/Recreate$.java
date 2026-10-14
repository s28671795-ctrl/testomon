/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.dispatch.sysmsg.Recreate;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class Recreate$
extends AbstractFunction1<Throwable, Recreate>
implements Serializable {
    public static final Recreate$ MODULE$ = new Recreate$();

    @Override
    public final String toString() {
        return "Recreate";
    }

    @Override
    public Recreate apply(Throwable cause) {
        return new Recreate(cause);
    }

    public Option<Throwable> unapply(Recreate x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Throwable>(x$0.cause());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Recreate$.class);
    }

    private Recreate$() {
    }
}

