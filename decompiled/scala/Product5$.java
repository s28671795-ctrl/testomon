/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product5;
import scala.Some;

public final class Product5$ {
    public static final Product5$ MODULE$ = new Product5$();

    public <T1, T2, T3, T4, T5> Option<Product5<T1, T2, T3, T4, T5>> unapply(Product5<T1, T2, T3, T4, T5> x) {
        return new Some<Product5<T1, T2, T3, T4, T5>>(x);
    }

    private Product5$() {
    }
}

