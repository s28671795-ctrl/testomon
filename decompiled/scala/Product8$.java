/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product8;
import scala.Some;

public final class Product8$ {
    public static final Product8$ MODULE$ = new Product8$();

    public <T1, T2, T3, T4, T5, T6, T7, T8> Option<Product8<T1, T2, T3, T4, T5, T6, T7, T8>> unapply(Product8<T1, T2, T3, T4, T5, T6, T7, T8> x) {
        return new Some<Product8<T1, T2, T3, T4, T5, T6, T7, T8>>(x);
    }

    private Product8$() {
    }
}

