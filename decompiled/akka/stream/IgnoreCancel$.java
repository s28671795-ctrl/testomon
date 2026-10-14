/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.IgnoreCancel;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class IgnoreCancel$
extends IgnoreCancel
implements Product,
Serializable {
    public static final IgnoreCancel$ MODULE$ = new IgnoreCancel$();

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
        return "IgnoreCancel";
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
        return x$1 instanceof IgnoreCancel$;
    }

    public int hashCode() {
        return -203340020;
    }

    public String toString() {
        return "IgnoreCancel";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(IgnoreCancel$.class);
    }

    private IgnoreCancel$() {
    }
}

