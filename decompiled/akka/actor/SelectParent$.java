/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.SelectionPathElement;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class SelectParent$
implements SelectionPathElement,
Product,
Serializable {
    public static final SelectParent$ MODULE$ = new SelectParent$();
    private static final long serialVersionUID = 2L;

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

    public String toString() {
        return "..";
    }

    @Override
    public String productPrefix() {
        return "SelectParent";
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
        return x$1 instanceof SelectParent$;
    }

    public int hashCode() {
        return 2101727462;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SelectParent$.class);
    }

    private SelectParent$() {
    }
}

