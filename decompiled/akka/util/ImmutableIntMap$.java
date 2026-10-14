/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.ImmutableIntMap;
import scala.Array$;

@InternalApi
public final class ImmutableIntMap$ {
    public static final ImmutableIntMap$ MODULE$ = new ImmutableIntMap$();
    private static final ImmutableIntMap empty = new ImmutableIntMap(Array$.MODULE$.emptyIntArray(), 0);

    public final ImmutableIntMap empty() {
        return empty;
    }

    private ImmutableIntMap$() {
    }
}

