/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product6;
import scala.Some;

public final class Product6$ {
    public static final Product6$ MODULE$ = new Product6$();

    public <T1, T2, T3, T4, T5, T6> Option<Product6<T1, T2, T3, T4, T5, T6>> unapply(Product6<T1, T2, T3, T4, T5, T6> x) {
        return new Some<Product6<T1, T2, T3, T4, T5, T6>>(x);
    }

    private Product6$() {
    }
}

