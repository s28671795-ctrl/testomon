/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product14;
import scala.Some;

public final class Product14$ {
    public static final Product14$ MODULE$ = new Product14$();

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Option<Product14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> unapply(Product14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> x) {
        return new Some<Product14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>(x);
    }

    private Product14$() {
    }
}

