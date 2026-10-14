/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.AdjustPoolSize;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class AdjustPoolSize$
extends AbstractFunction1<Object, AdjustPoolSize>
implements Serializable {
    public static final AdjustPoolSize$ MODULE$ = new AdjustPoolSize$();

    @Override
    public final String toString() {
        return "AdjustPoolSize";
    }

    @Override
    public AdjustPoolSize apply(int change) {
        return new AdjustPoolSize(change);
    }

    public Option<Object> unapply(AdjustPoolSize x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Object>(BoxesRunTime.boxToInteger(x$0.change()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AdjustPoolSize$.class);
    }

    private AdjustPoolSize$() {
    }
}

