/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.UninitializedFieldError;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class UninitializedFieldError$
extends AbstractFunction1<String, UninitializedFieldError>
implements Serializable {
    public static final UninitializedFieldError$ MODULE$ = new UninitializedFieldError$();

    @Override
    public final String toString() {
        return "UninitializedFieldError";
    }

    @Override
    public UninitializedFieldError apply(String msg) {
        return new UninitializedFieldError(msg);
    }

    public Option<String> unapply(UninitializedFieldError x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<String>(x$0.msg());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(UninitializedFieldError$.class);
    }

    private UninitializedFieldError$() {
    }
}

