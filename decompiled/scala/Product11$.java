/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product11;
import scala.Some;

public final class Product11$ {
    public static final Product11$ MODULE$ = new Product11$();

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Option<Product11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> unapply(Product11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> x) {
        return new Some<Product11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>(x);
    }

    private Product11$() {
    }
}

