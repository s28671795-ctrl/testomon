/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import java.io.Serializable;
import scala.Product;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Statics;

public final class TrieMapSerializationEnd$
implements Product,
Serializable {
    public static final TrieMapSerializationEnd$ MODULE$ = new TrieMapSerializationEnd$();
    private static final long serialVersionUID = 3L;

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
        return "TrieMapSerializationEnd";
    }

    @Override
    public int productArity() {
        return 0;
    }

    @Override
    public Object productElement(int x$1) {
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return new AbstractIterator<T>(this){
            private int c;
            private final int cmax;
            private final Product x$2;

            public boolean hasNext() {
                return this.c < this.cmax;
            }

            public T next() {
                Object result = this.x$2.productElement(this.c);
                ++this.c;
                return (T)result;
            }
            {
                this.x$2 = x$2;
                this.c = 0;
                this.cmax = x$2.productArity();
            }
        };
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof TrieMapSerializationEnd$;
    }

    public int hashCode() {
        return 289833389;
    }

    public String toString() {
        return "TrieMapSerializationEnd";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TrieMapSerializationEnd$.class);
    }

    private TrieMapSerializationEnd$() {
    }
}

