/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Tuple2;
import scala.collection.AbstractIterator;
import scala.collection.AbstractView;
import scala.collection.Iterator;
import scala.collection.LazyZip2;
import scala.collection.View;

public final class LazyZip2$ {
    public static final LazyZip2$ MODULE$ = new LazyZip2$();

    public <El1, El2> View<Tuple2<El1, El2>> lazyZip2ToIterable(LazyZip2<El1, El2, ?> zipped2) {
        if (zipped2 == null) {
            throw null;
        }
        return new AbstractView<Tuple2<El1, El2>>(zipped2){
            private final /* synthetic */ LazyZip2 $outer;

            public AbstractIterator<Tuple2<El1, El2>> iterator() {
                return new AbstractIterator<Tuple2<El1, El2>>(this){
                    private final Iterator<El1> elems1;
                    private final Iterator<El2> elems2;

                    public boolean hasNext() {
                        return this.elems1.hasNext() && this.elems2.hasNext();
                    }

                    public Tuple2<El1, El2> next() {
                        return new Tuple2<El1, El2>(this.elems1.next(), this.elems2.next());
                    }
                    {
                        this.elems1 = $outer.scala$collection$LazyZip2$$anon$$$outer().scala$collection$LazyZip2$$coll1.iterator();
                        this.elems2 = $outer.scala$collection$LazyZip2$$anon$$$outer().scala$collection$LazyZip2$$coll2.iterator();
                    }
                };
            }

            public int knownSize() {
                return this.$outer.scala$collection$LazyZip2$$zipKnownSize();
            }

            public boolean isEmpty() {
                return this.$outer.scala$collection$LazyZip2$$coll1.isEmpty() || this.$outer.scala$collection$LazyZip2$$coll2.isEmpty();
            }

            public /* synthetic */ LazyZip2 scala$collection$LazyZip2$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    private LazyZip2$() {
    }
}

