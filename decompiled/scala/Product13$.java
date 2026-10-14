/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product13;
import scala.Some;

public final class Product13$ {
    public static final Product13$ MODULE$ = new Product13$();

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Option<Product13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> unapply(Product13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> x) {
        return new Some<Product13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>(x);
    }

    private Product13$() {
    }
}

