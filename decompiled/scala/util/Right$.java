/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;
import scala.util.Right;

public final class Right$
implements Serializable {
    public static final Right$ MODULE$ = new Right$();

    public final String toString() {
        return "Right";
    }

    public <A, B> Right<A, B> apply(B value) {
        return new Right(value);
    }

    public <A, B> Option<B> unapply(Right<A, B> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<B>(x$0.value());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Right$.class);
    }

    private Right$() {
    }
}

