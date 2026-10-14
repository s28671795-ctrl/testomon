/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;
import scala.util.Success;

public final class Success$
implements Serializable {
    public static final Success$ MODULE$ = new Success$();

    public final String toString() {
        return "Success";
    }

    public <T> Success<T> apply(T value) {
        return new Success<T>(value);
    }

    public <T> Option<T> unapply(Success<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<T>(x$0.value());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Success$.class);
    }

    private Success$() {
    }
}

