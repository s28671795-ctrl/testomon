/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.EagerClose;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class EagerClose$
extends EagerClose
implements Product,
Serializable {
    public static final EagerClose$ MODULE$ = new EagerClose$();

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
        return "EagerClose";
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
        return x$1 instanceof EagerClose$;
    }

    public int hashCode() {
        return 1989369792;
    }

    public String toString() {
        return "EagerClose";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(EagerClose$.class);
    }

    private EagerClose$() {
    }
}

