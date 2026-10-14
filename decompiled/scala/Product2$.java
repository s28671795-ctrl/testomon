/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product2;
import scala.Some;

public final class Product2$ {
    public static final Product2$ MODULE$ = new Product2$();

    public <T1, T2> Option<Product2<T1, T2>> unapply(Product2<T1, T2> x) {
        return new Some<Product2<T1, T2>>(x);
    }

    private Product2$() {
    }
}

