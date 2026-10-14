/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.IgnoreComplete;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class IgnoreComplete$
extends IgnoreComplete
implements Product,
Serializable {
    public static final IgnoreComplete$ MODULE$ = new IgnoreComplete$();

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
        return "IgnoreComplete";
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
        return x$1 instanceof IgnoreComplete$;
    }

    public int hashCode() {
        return 1682468171;
    }

    public String toString() {
        return "IgnoreComplete";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(IgnoreComplete$.class);
    }

    private IgnoreComplete$() {
    }
}

