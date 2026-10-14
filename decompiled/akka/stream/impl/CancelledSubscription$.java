/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import java.io.Serializable;
import org.reactivestreams.Subscription;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@InternalApi
public final class CancelledSubscription$
implements Subscription,
Product,
Serializable {
    public static final CancelledSubscription$ MODULE$ = new CancelledSubscription$();

    static {
        Product.$init$(MODULE$);
    }

    @Override
    public String productElementName(int n) {
        return Product.productElementName$(this, n);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public void request(long elements) {
    }

    @Override
    public void cancel() {
    }

    @Override
    public String productPrefix() {
        return "CancelledSubscription";
    }

    @Override
    public int productArity() {
        return 0;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof CancelledSubscription$;
    }

    public int hashCode() {
        return -1091914514;
    }

    public String toString() {
        return "CancelledSubscription";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(CancelledSubscription$.class);
    }

    private CancelledSubscription$() {
    }
}

