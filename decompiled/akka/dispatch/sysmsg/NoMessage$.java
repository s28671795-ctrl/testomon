/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.dispatch.sysmsg.SystemMessage;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class NoMessage$
implements SystemMessage,
Product {
    public static final NoMessage$ MODULE$ = new NoMessage$();
    private static final long serialVersionUID = 1L;
    private static transient SystemMessage next;

    static {
        SystemMessage.$init$(MODULE$);
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
    public void unlink() {
        SystemMessage.unlink$(this);
    }

    @Override
    public boolean unlinked() {
        return SystemMessage.unlinked$(this);
    }

    @Override
    public SystemMessage next() {
        return next;
    }

    @Override
    public void next_$eq(SystemMessage x$1) {
        next = x$1;
    }

    @Override
    public String productPrefix() {
        return "NoMessage";
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
        return x$1 instanceof NoMessage$;
    }

    public int hashCode() {
        return -744497434;
    }

    public String toString() {
        return "NoMessage";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(NoMessage$.class);
    }

    private NoMessage$() {
    }
}

