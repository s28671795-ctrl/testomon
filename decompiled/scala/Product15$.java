/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product15;
import scala.Some;

public final class Product15$ {
    public static final Product15$ MODULE$ = new Product15$();

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Option<Product15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> unapply(Product15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> x) {
        return new Some<Product15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>(x);
    }

    private Product15$() {
    }
}

