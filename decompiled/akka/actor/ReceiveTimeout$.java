/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ReceiveTimeout;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class ReceiveTimeout$
extends ReceiveTimeout
implements Product,
Serializable {
    public static final ReceiveTimeout$ MODULE$ = new ReceiveTimeout$();
    private static final long serialVersionUID = 1L;

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
    public ReceiveTimeout$ getInstance() {
        return this;
    }

    @Override
    public String productPrefix() {
        return "ReceiveTimeout";
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
        return x$1 instanceof ReceiveTimeout$;
    }

    public int hashCode() {
        return -988624610;
    }

    public String toString() {
        return "ReceiveTimeout";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ReceiveTimeout$.class);
    }

    private ReceiveTimeout$() {
    }
}

