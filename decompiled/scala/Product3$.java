/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product3;
import scala.Some;

public final class Product3$ {
    public static final Product3$ MODULE$ = new Product3$();

    public <T1, T2, T3> Option<Product3<T1, T2, T3>> unapply(Product3<T1, T2, T3> x) {
        return new Some<Product3<T1, T2, T3>>(x);
    }

    private Product3$() {
    }
}

