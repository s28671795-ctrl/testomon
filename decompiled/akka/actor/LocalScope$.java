/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.LocalScope;
import akka.actor.Scope;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class LocalScope$
extends LocalScope
implements Product,
Serializable {
    public static final LocalScope$ MODULE$ = new LocalScope$();
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
    public LocalScope$ getInstance() {
        return this;
    }

    @Override
    public Scope withFallback(Scope other) {
        return this;
    }

    @Override
    public String productPrefix() {
        return "LocalScope";
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
        return x$1 instanceof LocalScope$;
    }

    public int hashCode() {
        return -1009371703;
    }

    public String toString() {
        return "LocalScope";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(LocalScope$.class);
    }

    private LocalScope$() {
    }
}

