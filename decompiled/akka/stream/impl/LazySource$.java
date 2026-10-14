/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.LazySource;
import akka.stream.scaladsl.Source;
import scala.Function0;

@InternalApi
public final class LazySource$ {
    public static final LazySource$ MODULE$ = new LazySource$();

    public <T, M> LazySource<T, M> apply(Function0<Source<T, M>> sourceFactory) {
        return new LazySource<T, M>(sourceFactory);
    }

    private LazySource$() {
    }
}

