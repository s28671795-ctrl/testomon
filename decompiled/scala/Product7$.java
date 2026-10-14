/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product7;
import scala.Some;

public final class Product7$ {
    public static final Product7$ MODULE$ = new Product7$();

    public <T1, T2, T3, T4, T5, T6, T7> Option<Product7<T1, T2, T3, T4, T5, T6, T7>> unapply(Product7<T1, T2, T3, T4, T5, T6, T7> x) {
        return new Some<Product7<T1, T2, T3, T4, T5, T6, T7>>(x);
    }

    private Product7$() {
    }
}

