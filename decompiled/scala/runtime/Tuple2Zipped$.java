/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function2;
import scala.Tuple2;
import scala.collection.BuildFrom;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.mutable.Builder;
import scala.runtime.BoxesRunTime;
import scala.runtime.Tuple2Zipped;

public final class Tuple2Zipped$ {
    public static final Tuple2Zipped$ MODULE$ = new Tuple2Zipped$();

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>> It1 coll1$extension(Tuple2<It1, It2> $this) {
        return (It1)((Iterable)$this._1());
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>> It2 coll2$extension(Tuple2<It1, It2> $this) {
        return (It2)((Iterable)$this._2());
    }

    public final <B, To, El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>> To map$extension(Tuple2<It1, It2> $this, Function2<El1, El2, B> f, BuildFrom<It1, B, To> bf) {
        Builder<B, To> b = bf.newBuilder((Iterable)$this._1());
        b.sizeHint((Iterable)$this._1(), 0);
        Iterator elems1 = ((Iterable)$this._1()).iterator();
        Iterator elems2 = ((Iterable)$this._2()).iterator();
        while (elems1.hasNext() && elems2.hasNext()) {
            B $plus$eq_elem = f.apply(elems1.next(), elems2.next());
            b.addOne($plus$eq_elem);
            Object var7_7 = null;
        }
        return b.result();
    }

    /*
     * WARNING - void declaration
     */
    public final <B, To, El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>> To flatMap$extension(Tuple2<It1, It2> $this, Function2<El1, El2, IterableOnce<B>> f, BuildFrom<It1, B, To> bf) {
        Builder<B, To> b = bf.newBuilder((Iterable)$this._1());
        Iterator elems1 = ((Iterable)$this._1()).iterator();
        Iterator elems2 = ((Iterable)$this._2()).iterator();
        while (elems1.hasNext() && elems2.hasNext()) {
            void $plus$plus$eq_xs;
            IterableOnce<B> iterableOnce = f.apply(elems1.next(), elems2.next());
            if (b == null) {
                throw null;
            }
            b.addAll((IterableOnce<B>)$plus$plus$eq_xs);
            iterableOnce = null;
        }
        return b.result();
    }

    public final <To1, To2, El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>> Tuple2<To1, To2> filter$extension(Tuple2<It1, It2> $this, Function2<El1, El2, Object> f, BuildFrom<It1, El1, To1> bf1, BuildFrom<It2, El2, To2> bf2) {
        Builder<El1, To1> b1 = bf1.newBuilder((Iterable)$this._1());
        Builder<El2, To2> b2 = bf2.newBuilder((Iterable)$this._2());
        Iterator elems1 = ((Iterable)$this._1()).iterator();
        Iterator elems2 = ((Iterable)$this._2()).iterator();
        while (elems1.hasNext() && elems2.hasNext()) {
            Object el2;
            Object el1 = elems1.next();
            if (!BoxesRunTime.unboxToBoolean(f.apply(el1, el2 = elems2.next()))) continue;
            if (b1 == null) {
                throw null;
            }
            b1.addOne(el1);
            if (b2 == null) {
                throw null;
            }
            b2.addOne(el2);
        }
        return new Tuple2<To1, To2>(b1.result(), b2.result());
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>> boolean exists$extension(Tuple2<It1, It2> $this, Function2<El1, El2, Object> p) {
        Iterator elems1 = ((Iterable)$this._1()).iterator();
        Iterator elems2 = ((Iterable)$this._2()).iterator();
        while (elems1.hasNext() && elems2.hasNext()) {
            if (!BoxesRunTime.unboxToBoolean(p.apply(elems1.next(), elems2.next()))) continue;
            return true;
        }
        return false;
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>> boolean forall$extension(Tuple2<It1, It2> $this, Function2<El1, El2, Object> p) {
        boolean bl;
        block1: {
            Iterator exists$extension_elems1 = ((Iterable)$this._1()).iterator();
            Iterator exists$extension_elems2 = ((Iterable)$this._2()).iterator();
            while (exists$extension_elems1.hasNext() && exists$extension_elems2.hasNext()) {
                Object a = exists$extension_elems2.next();
                Object a2 = exists$extension_elems1.next();
                if (!(!BoxesRunTime.unboxToBoolean(p.apply(a2, a)))) continue;
                bl = true;
                break block1;
            }
            bl = false;
        }
        Object var3_3 = null;
        Object var4_4 = null;
        return !bl;
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>> Iterator<Tuple2<El1, El2>> iterator$extension(Tuple2<It1, It2> $this) {
        return ((Iterable)$this._1()).iterator().zip(((Iterable)$this._2()).iterator());
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>> boolean isEmpty$extension(Tuple2<It1, It2> $this) {
        return ((Iterable)$this._1()).isEmpty() || ((Iterable)$this._2()).isEmpty();
    }

    public final <U, El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>> void foreach$extension(Tuple2<It1, It2> $this, Function2<El1, El2, U> f) {
        Iterator elems1 = ((Iterable)$this._1()).iterator();
        Iterator elems2 = ((Iterable)$this._2()).iterator();
        while (elems1.hasNext() && elems2.hasNext()) {
            f.apply(elems1.next(), elems2.next());
        }
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>> String toString$extension(Tuple2<It1, It2> $this) {
        return new StringBuilder(11).append("(").append((Iterable)$this._1()).append(", ").append((Iterable)$this._2()).append(").zipped").toString();
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>> int hashCode$extension(Tuple2<It1, It2> $this) {
        return $this.hashCode();
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>> boolean equals$extension(Tuple2<It1, It2> $this, Object x$1) {
        if (x$1 instanceof Tuple2Zipped) {
            Tuple2 tuple2 = x$1 == null ? null : ((Tuple2Zipped)x$1).scala$runtime$Tuple2Zipped$$colls();
            Tuple2<It1, It2> tuple22 = $this;
            if (!(tuple22 != null ? !((Object)tuple22).equals(tuple2) : tuple2 != null)) {
                return true;
            }
        }
        return false;
    }

    public static final /* synthetic */ boolean $anonfun$forall$1(Function2 p$1, Object x, Object y) {
        return !BoxesRunTime.unboxToBoolean(p$1.apply(x, y));
    }

    private Tuple2Zipped$() {
    }

    public static final /* synthetic */ Object $anonfun$forall$1$adapted(Function2 p$1, Object x, Object y) {
        return BoxesRunTime.boxToBoolean(Tuple2Zipped$.$anonfun$forall$1(p$1, x, y));
    }
}

