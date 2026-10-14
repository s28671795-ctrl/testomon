/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product12;
import scala.Some;

public final class Product12$ {
    public static final Product12$ MODULE$ = new Product12$();

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Option<Product12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> unapply(Product12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> x) {
        return new Some<Product12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>(x);
    }

    private Product12$() {
    }
}

