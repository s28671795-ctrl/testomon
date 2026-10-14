/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.DropWhile;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class DropWhile$
implements Serializable {
    public static final DropWhile$ MODULE$ = new DropWhile$();

    public final String toString() {
        return "DropWhile";
    }

    public <T> DropWhile<T> apply(Function1<T, Object> p) {
        return new DropWhile<T>(p);
    }

    public <T> Option<Function1<T, Object>> unapply(DropWhile<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Function1<T, Object>>(x$0.p());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DropWhile$.class);
    }

    private DropWhile$() {
    }
}

