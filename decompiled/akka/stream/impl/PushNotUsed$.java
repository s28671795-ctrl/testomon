/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.MaterializedValueOp;
import akka.stream.impl.Traversal;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@InternalApi
public final class PushNotUsed$
implements MaterializedValueOp,
Product,
Serializable {
    public static final PushNotUsed$ MODULE$ = new PushNotUsed$();

    static {
        Traversal.$init$(MODULE$);
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
    public Traversal concat(Traversal that) {
        return Traversal.concat$(this, that);
    }

    @Override
    public Traversal rewireFirstTo(int relativeOffset) {
        return Traversal.rewireFirstTo$(this, relativeOffset);
    }

    @Override
    public String productPrefix() {
        return "PushNotUsed";
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
        return x$1 instanceof PushNotUsed$;
    }

    public int hashCode() {
        return -581195626;
    }

    public String toString() {
        return "PushNotUsed";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(PushNotUsed$.class);
    }

    private PushNotUsed$() {
    }
}

