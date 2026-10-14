/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.Transform;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

public final class Transform$
extends AbstractFunction1<Function1<Nothing$, Object>, Transform>
implements Serializable {
    public static final Transform$ MODULE$ = new Transform$();

    @Override
    public final String toString() {
        return "Transform";
    }

    @Override
    public Transform apply(Function1<Nothing$, Object> mapper2) {
        return new Transform(mapper2);
    }

    public Option<Function1<Nothing$, Object>> unapply(Transform x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Function1<Nothing$, Object>>(x$0.mapper());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Transform$.class);
    }

    private Transform$() {
    }
}

