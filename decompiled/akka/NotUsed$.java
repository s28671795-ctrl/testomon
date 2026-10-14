/*
 * Decompiled with CFR 0.152.
 */
package akka;

import akka.NotUsed;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class NotUsed$
extends NotUsed
implements Product,
Serializable {
    public static final NotUsed$ MODULE$ = new NotUsed$();

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
    public NotUsed getInstance() {
        return this;
    }

    @Override
    public NotUsed notUsed() {
        return this;
    }

    @Override
    public String productPrefix() {
        return "NotUsed";
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
        return x$1 instanceof NotUsed$;
    }

    public int hashCode() {
        return -501546736;
    }

    public String toString() {
        return "NotUsed";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(NotUsed$.class);
    }

    private NotUsed$() {
    }
}

