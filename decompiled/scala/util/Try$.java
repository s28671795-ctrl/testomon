/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import java.io.Serializable;
import scala.Function0;
import scala.Option;
import scala.runtime.ModuleSerializationProxy;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NonFatal$;

public final class Try$
implements Serializable {
    public static final Try$ MODULE$ = new Try$();

    public <T> Try<T> apply(Function0<T> r) {
        try {
            return new Success<T>(r.apply());
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            if (throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty()) {
                Throwable e = option.get();
                return new Failure(e);
            }
            throw throwable;
        }
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Try$.class);
    }

    private Try$() {
    }
}

