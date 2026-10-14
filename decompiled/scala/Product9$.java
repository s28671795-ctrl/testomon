/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product9;
import scala.Some;

public final class Product9$ {
    public static final Product9$ MODULE$ = new Product9$();

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Option<Product9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> unapply(Product9<T1, T2, T3, T4, T5, T6, T7, T8, T9> x) {
        return new Some<Product9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>(x);
    }

    private Product9$() {
    }
}

