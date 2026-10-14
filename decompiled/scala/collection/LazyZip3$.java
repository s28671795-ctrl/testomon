/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.AbstractView;
import scala.collection.Iterator;
import scala.collection.LazyZip3;
import scala.collection.View;

public final class LazyZip3$ {
    public static final LazyZip3$ MODULE$ = new LazyZip3$();

    public <El1, El2, El3> View<Tuple3<El1, El2, El3>> lazyZip3ToIterable(LazyZip3<El1, El2, El3, ?> zipped3) {
        if (zipped3 == null) {
            throw null;
        }
        return new AbstractView<Tuple3<El1, El2, El3>>(zipped3){
            private final /* synthetic */ LazyZip3 $outer;

            public AbstractIterator<Tuple3<El1, El2, El3>> iterator() {
                return new AbstractIterator<Tuple3<El1, El2, El3>>(this){
                    private final Iterator<El1> elems1;
                    private final Iterator<El2> elems2;
                    private final Iterator<El3> elems3;

                    public boolean hasNext() {
                        return this.elems1.hasNext() && this.elems2.hasNext() && this.elems3.hasNext();
                    }

                    public Tuple3<El1, El2, El3> next() {
                        return new Tuple3<El1, El2, El3>(this.elems1.next(), this.elems2.next(), this.elems3.next());
                    }
                    {
                        this.elems1 = $outer.scala$collection$LazyZip3$$anon$$$outer().scala$collection$LazyZip3$$coll1.iterator();
                        this.elems2 = $outer.scala$collection$LazyZip3$$anon$$$outer().scala$collection$LazyZip3$$coll2.iterator();
                        this.elems3 = $outer.scala$collection$LazyZip3$$anon$$$outer().scala$collection$LazyZip3$$coll3.iterator();
                    }
                };
            }

            public int knownSize() {
                return this.$outer.scala$collection$LazyZip3$$zipKnownSize();
            }

            public boolean isEmpty() {
                return this.$outer.scala$collection$LazyZip3$$coll1.isEmpty() || this.$outer.scala$collection$LazyZip3$$coll2.isEmpty() || this.$outer.scala$collection$LazyZip3$$coll3.isEmpty();
            }

            public /* synthetic */ LazyZip3 scala$collection$LazyZip3$$anon$$$outer() {
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

    private LazyZip3$() {
    }
}

