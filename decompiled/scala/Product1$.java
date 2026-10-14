/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product1;
import scala.Some;

public final class Product1$ {
    public static final Product1$ MODULE$ = new Product1$();

    public <T1> Option<Product1<T1>> unapply(Product1<T1> x) {
        return new Some<Product1<T1>>(x);
    }

    private Product1$() {
    }
}

