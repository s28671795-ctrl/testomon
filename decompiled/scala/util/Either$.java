/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import java.io.Serializable;
import scala.Function0;
import scala.runtime.ModuleSerializationProxy;
import scala.util.Either;
import scala.util.Left;
import scala.util.Right;

public final class Either$
implements Serializable {
    public static final Either$ MODULE$ = new Either$();

    public <A, B> Either<A, B> cond(boolean test, Function0<B> right, Function0<A> left) {
        if (test) {
            return new Right(right.apply());
        }
        return new Left(left.apply());
    }

    public <A> Either<A, A> MergeableEither(Either<A, A> x) {
        return x;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Either$.class);
    }

    private Either$() {
    }
}

