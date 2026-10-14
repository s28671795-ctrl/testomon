/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.TypedMultiMap;
import scala.Predef$;
import scala.runtime.Nothing$;

public final class TypedMultiMap$ {
    public static final TypedMultiMap$ MODULE$ = new TypedMultiMap$();
    private static final TypedMultiMap<Nothing$, Nothing$> _empty = new TypedMultiMap(Predef$.MODULE$.Map().empty());

    private TypedMultiMap<Nothing$, Nothing$> _empty() {
        return _empty;
    }

    public <T, K> TypedMultiMap<T, K> empty() {
        return this._empty();
    }

    private TypedMultiMap$() {
    }
}

