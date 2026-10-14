/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.DeadLetterSuppression;
import akka.actor.NoSerializationVerificationNeeded;
import akka.annotation.InternalApi;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@InternalApi
public final class SubscribePending$
implements DeadLetterSuppression,
NoSerializationVerificationNeeded,
Product,
Serializable {
    public static final SubscribePending$ MODULE$ = new SubscribePending$();

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
    public String productPrefix() {
        return "SubscribePending";
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
        return x$1 instanceof SubscribePending$;
    }

    public int hashCode() {
        return 1100079373;
    }

    public String toString() {
        return "SubscribePending";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SubscribePending$.class);
    }

    private SubscribePending$() {
    }
}

