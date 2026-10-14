/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent.impl;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import scala.concurrent.ExecutionContext$parasitic$;
import scala.concurrent.impl.Promise;
import scala.runtime.NonLocalReturnControl;
import scala.runtime.Nothing$;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.ControlThrowable;

public final class Promise$ {
    public static final Promise$ MODULE$ = new Promise$();
    public static final Promise.Transformation<Nothing$, Nothing$> scala$concurrent$impl$Promise$$Noop = new Promise.Transformation(0, null, ExecutionContext$parasitic$.MODULE$);

    public final <T> Try<T> scala$concurrent$impl$Promise$$resolve(Try<T> value) {
        if (Objects.requireNonNull(value) instanceof Success) {
            return value;
        }
        Throwable t2 = ((Failure)value).exception();
        if (t2 instanceof ControlThrowable || t2 instanceof InterruptedException || t2 instanceof Error) {
            if (t2 instanceof NonLocalReturnControl) {
                return new Success(((NonLocalReturnControl)t2).value());
            }
            return new Failure(new ExecutionException("Boxed Exception", t2));
        }
        return value;
    }

    public final int Xform_noop() {
        return 0;
    }

    public final int Xform_map() {
        return 1;
    }

    public final int Xform_flatMap() {
        return 2;
    }

    public final int Xform_transform() {
        return 3;
    }

    public final int Xform_transformWith() {
        return 4;
    }

    public final int Xform_foreach() {
        return 5;
    }

    public final int Xform_onComplete() {
        return 6;
    }

    public final int Xform_recover() {
        return 7;
    }

    public final int Xform_recoverWith() {
        return 8;
    }

    public final int Xform_filter() {
        return 9;
    }

    public final int Xform_collect() {
        return 10;
    }

    private Promise$() {
    }
}

