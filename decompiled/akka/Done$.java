/*
 * Decompiled with CFR 0.152.
 */
package akka;

import akka.Done;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class Done$
extends Done
implements Product {
    public static final Done$ MODULE$ = new Done$();

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
    public Done getInstance() {
        return this;
    }

    @Override
    public Done done() {
        return this;
    }

    @Override
    public String productPrefix() {
        return "Done";
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
        return x$1 instanceof Done$;
    }

    public int hashCode() {
        return 2135970;
    }

    public String toString() {
        return "Done";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Done$.class);
    }

    private Done$() {
    }
}

