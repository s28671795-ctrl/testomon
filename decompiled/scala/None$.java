/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.util.NoSuchElementException;
import scala.Option;
import scala.Product;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;
import scala.runtime.Statics;

public final class None$
extends Option<Nothing$> {
    public static final None$ MODULE$ = new None$();
    private static final long serialVersionUID = 5066590221178148012L;

    @Override
    public Nothing$ get() {
        throw new NoSuchElementException("None.get");
    }

    @Override
    public String productPrefix() {
        return "None";
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
        return x$1 instanceof None$;
    }

    public int hashCode() {
        return 2433880;
    }

    public String toString() {
        return "None";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(None$.class);
    }

    private None$() {
    }
}

