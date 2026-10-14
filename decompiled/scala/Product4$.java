/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product4;
import scala.Some;

public final class Product4$ {
    public static final Product4$ MODULE$ = new Product4$();

    public <T1, T2, T3, T4> Option<Product4<T1, T2, T3, T4>> unapply(Product4<T1, T2, T3, T4> x) {
        return new Some<Product4<T1, T2, T3, T4>>(x);
    }

    private Product4$() {
    }
}

