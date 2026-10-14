/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product16;
import scala.Some;

public final class Product16$ {
    public static final Product16$ MODULE$ = new Product16$();

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Option<Product16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> unapply(Product16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> x) {
        return new Some<Product16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>(x);
    }

    private Product16$() {
    }
}

