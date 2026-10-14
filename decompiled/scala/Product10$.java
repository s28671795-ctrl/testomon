/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product10;
import scala.Some;

public final class Product10$ {
    public static final Product10$ MODULE$ = new Product10$();

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Option<Product10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> unapply(Product10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> x) {
        return new Some<Product10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>(x);
    }

    private Product10$() {
    }
}

