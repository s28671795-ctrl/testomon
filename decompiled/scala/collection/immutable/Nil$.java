/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.util.NoSuchElementException;
import scala.Function1;
import scala.None$;
import scala.Product;
import scala.Tuple2;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.immutable.List;
import scala.runtime.Nothing$;
import scala.runtime.Statics;

public final class Nil$
extends List<Nothing$>
implements Product {
    public static final Nil$ MODULE$ = new Nil$();
    private static final transient Tuple2<Nil$, Nil$> EmptyUnzip = new Tuple2<Nil$, Nil$>(MODULE$, MODULE$);

    @Override
    public String productElementName(int n) {
        return Product.productElementName$(this, n);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Nothing$ head() {
        throw new NoSuchElementException("head of empty list");
    }

    public None$ headOption() {
        return None$.MODULE$;
    }

    @Override
    public Nothing$ tail() {
        throw new UnsupportedOperationException("tail of empty list");
    }

    @Override
    public Nothing$ last() {
        throw new NoSuchElementException("last of empty list");
    }

    @Override
    public Nothing$ init() {
        throw new UnsupportedOperationException("init of empty list");
    }

    @Override
    public int knownSize() {
        return 0;
    }

    @Override
    public Iterator<Nothing$> iterator() {
        return Iterator$.scala$collection$Iterator$$_empty;
    }

    @Override
    public <A1, A2> Tuple2<List<A1>, List<A2>> unzip(Function1<Nothing$, Tuple2<A1, A2>> asPair) {
        return EmptyUnzip;
    }

    @Override
    public String productPrefix() {
        return "Nil";
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

    private Nil$() {
    }
}

