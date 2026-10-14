/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.GetRoutees;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class GetRoutees$
extends GetRoutees
implements Product,
Serializable {
    public static final GetRoutees$ MODULE$ = new GetRoutees$();
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
    public GetRoutees$ getInstance() {
        return this;
    }

    @Override
    public String productPrefix() {
        return "GetRoutees";
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
        return x$1 instanceof GetRoutees$;
    }

    public int hashCode() {
        return 121006241;
    }

    public String toString() {
        return "GetRoutees";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(GetRoutees$.class);
    }

    private GetRoutees$() {
    }
}

