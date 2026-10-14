/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Tuple4;
import scala.collection.AbstractIterator;
import scala.collection.AbstractView;
import scala.collection.Iterator;
import scala.collection.LazyZip4;
import scala.collection.View;

public final class LazyZip4$ {
    public static final LazyZip4$ MODULE$ = new LazyZip4$();

    public <El1, El2, El3, El4> View<Tuple4<El1, El2, El3, El4>> lazyZip4ToIterable(LazyZip4<El1, El2, El3, El4, ?> zipped4) {
        if (zipped4 == null) {
            throw null;
        }
        return new AbstractView<Tuple4<El1, El2, El3, El4>>(zipped4){
            private final /* synthetic */ LazyZip4 $outer;

            public AbstractIterator<Tuple4<El1, El2, El3, El4>> iterator() {
                return new AbstractIterator<Tuple4<El1, El2, El3, El4>>(this){
                    private final Iterator<El1> elems1;
                    private final Iterator<El2> elems2;
                    private final Iterator<El3> elems3;
                    private final Iterator<El4> elems4;

                    public boolean hasNext() {
                        return this.elems1.hasNext() && this.elems2.hasNext() && this.elems3.hasNext() && this.elems4.hasNext();
                    }

                    public Tuple4<El1, El2, El3, El4> next() {
                        return new Tuple4<El1, El2, El3, El4>(this.elems1.next(), this.elems2.next(), this.elems3.next(), this.elems4.next());
                    }
                    {
                        this.elems1 = $outer.scala$collection$LazyZip4$$anon$$$outer().scala$collection$LazyZip4$$coll1.iterator();
                        this.elems2 = $outer.scala$collection$LazyZip4$$anon$$$outer().scala$collection$LazyZip4$$coll2.iterator();
                        this.elems3 = $outer.scala$collection$LazyZip4$$anon$$$outer().scala$collection$LazyZip4$$coll3.iterator();
                        this.elems4 = $outer.scala$collection$LazyZip4$$anon$$$outer().scala$collection$LazyZip4$$coll4.iterator();
                    }
                };
            }

            public int knownSize() {
                return this.$outer.scala$collection$LazyZip4$$zipKnownSize();
            }

            public boolean isEmpty() {
                return this.$outer.scala$collection$LazyZip4$$coll1.isEmpty() || this.$outer.scala$collection$LazyZip4$$coll2.isEmpty() || this.$outer.scala$collection$LazyZip4$$coll3.isEmpty() || this.$outer.scala$collection$LazyZip4$$coll4.isEmpty();
            }

            public /* synthetic */ LazyZip4 scala$collection$LazyZip4$$anon$$$outer() {
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

    private LazyZip4$() {
    }
}

