/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;
import scala.Function1;
import scala.Function3;
import scala.MatchError;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.BuildFrom;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.mutable.Builder;
import scala.runtime.BoxesRunTime;
import scala.runtime.Tuple3Zipped;
import scala.util.hashing.MurmurHash3$;

public final class Tuple3Zipped$ {
    public static final Tuple3Zipped$ MODULE$ = new Tuple3Zipped$();

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>, El3, It3 extends Iterable<El3>> It1 coll1$extension(Tuple3<It1, It2, It3> $this) {
        return (It1)((Iterable)$this._1());
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>, El3, It3 extends Iterable<El3>> It2 coll2$extension(Tuple3<It1, It2, It3> $this) {
        return (It2)((Iterable)$this._2());
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>, El3, It3 extends Iterable<El3>> It3 coll3$extension(Tuple3<It1, It2, It3> $this) {
        return (It3)((Iterable)$this._3());
    }

    /*
     * WARNING - void declaration
     */
    public final <B, To, El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>, El3, It3 extends Iterable<El3>> To map$extension(Tuple3<It1, It2, It3> $this, Function3<El1, El2, El3, B> f, BuildFrom<It1, B, To> bf) {
        Builder<B, To> b = bf.newBuilder((Iterable)$this._1());
        Iterator elems1 = ((Iterable)$this._1()).iterator();
        Iterator elems2 = ((Iterable)$this._2()).iterator();
        Iterator elems3 = ((Iterable)$this._3()).iterator();
        while (elems1.hasNext() && elems2.hasNext() && elems3.hasNext()) {
            void $plus$eq_elem;
            B b2 = f.apply(elems1.next(), elems2.next(), elems3.next());
            if (b == null) {
                throw null;
            }
            b.addOne($plus$eq_elem);
            b2 = null;
        }
        return b.result();
    }

    /*
     * WARNING - void declaration
     */
    public final <B, To, El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>, El3, It3 extends Iterable<El3>> To flatMap$extension(Tuple3<It1, It2, It3> $this, Function3<El1, El2, El3, IterableOnce<B>> f, BuildFrom<It1, B, To> bf) {
        Builder<B, To> b = bf.newBuilder((Iterable)$this._1());
        Iterator elems1 = ((Iterable)$this._1()).iterator();
        Iterator elems2 = ((Iterable)$this._2()).iterator();
        Iterator elems3 = ((Iterable)$this._3()).iterator();
        while (elems1.hasNext() && elems2.hasNext() && elems3.hasNext()) {
            void $plus$plus$eq_xs;
            IterableOnce<B> iterableOnce = f.apply(elems1.next(), elems2.next(), elems3.next());
            if (b == null) {
                throw null;
            }
            b.addAll((IterableOnce<B>)$plus$plus$eq_xs);
            iterableOnce = null;
        }
        return b.result();
    }

    public final <To1, To2, To3, El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>, El3, It3 extends Iterable<El3>> Tuple3<To1, To2, To3> filter$extension(Tuple3<It1, It2, It3> $this, Function3<El1, El2, El3, Object> f, BuildFrom<It1, El1, To1> bf1, BuildFrom<It2, El2, To2> bf2, BuildFrom<It3, El3, To3> bf3) {
        Builder<El1, To1> b1 = bf1.newBuilder((Iterable)$this._1());
        Builder<El2, To2> b2 = bf2.newBuilder((Iterable)$this._2());
        Builder<El3, To3> b3 = bf3.newBuilder((Iterable)$this._3());
        Iterator elems1 = ((Iterable)$this._1()).iterator();
        Iterator elems2 = ((Iterable)$this._2()).iterator();
        Iterator elems3 = ((Iterable)$this._3()).iterator();
        while (elems1.hasNext() && elems2.hasNext() && elems3.hasNext()) {
            Object el3;
            Object el2;
            Object el1 = elems1.next();
            if (!BoxesRunTime.unboxToBoolean(f.apply(el1, el2 = elems2.next(), el3 = elems3.next()))) continue;
            if (b1 == null) {
                throw null;
            }
            b1.addOne(el1);
            if (b2 == null) {
                throw null;
            }
            b2.addOne(el2);
            if (b3 == null) {
                throw null;
            }
            b3.addOne(el3);
        }
        return new Tuple3<To1, To2, To3>(b1.result(), b2.result(), b3.result());
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>, El3, It3 extends Iterable<El3>> boolean exists$extension(Tuple3<It1, It2, It3> $this, Function3<El1, El2, El3, Object> p) {
        Iterator elems1 = ((Iterable)$this._1()).iterator();
        Iterator elems2 = ((Iterable)$this._2()).iterator();
        Iterator elems3 = ((Iterable)$this._3()).iterator();
        while (elems1.hasNext() && elems2.hasNext() && elems3.hasNext()) {
            if (!BoxesRunTime.unboxToBoolean(p.apply(elems1.next(), elems2.next(), elems3.next()))) continue;
            return true;
        }
        return false;
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>, El3, It3 extends Iterable<El3>> boolean forall$extension(Tuple3<It1, It2, It3> $this, Function3<El1, El2, El3, Object> p) {
        boolean bl;
        block1: {
            Iterator exists$extension_elems1 = ((Iterable)$this._1()).iterator();
            Iterator exists$extension_elems2 = ((Iterable)$this._2()).iterator();
            Iterator exists$extension_elems3 = ((Iterable)$this._3()).iterator();
            while (exists$extension_elems1.hasNext() && exists$extension_elems2.hasNext() && exists$extension_elems3.hasNext()) {
                Object a = exists$extension_elems3.next();
                Object a2 = exists$extension_elems2.next();
                Object a3 = exists$extension_elems1.next();
                if (!(!BoxesRunTime.unboxToBoolean(p.apply(a3, a2, a)))) continue;
                bl = true;
                break block1;
            }
            bl = false;
        }
        Object var3_3 = null;
        Object var4_4 = null;
        Object var5_5 = null;
        return !bl;
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>, El3, It3 extends Iterable<El3>> Iterator<Tuple3<El1, El2, El3>> iterator$extension(Tuple3<It1, It2, It3> $this) {
        return ((Iterable)$this._1()).iterator().zip(((Iterable)$this._2()).iterator()).zip(((Iterable)$this._3()).iterator()).map((Function1<Tuple2, Tuple3> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Tuple2 tuple2 = (Tuple2)x0$1._1();
                Object c = x0$1._2();
                if (tuple2 != null) {
                    Object a = tuple2._1();
                    Object b = tuple2._2();
                    return new Tuple3(a, b, c);
                }
            }
            throw new MatchError(x0$1);
        });
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>, El3, It3 extends Iterable<El3>> boolean isEmpty$extension(Tuple3<It1, It2, It3> $this) {
        return ((Iterable)$this._1()).isEmpty() || ((Iterable)$this._2()).isEmpty() || ((Iterable)$this._3()).isEmpty();
    }

    public final <U, El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>, El3, It3 extends Iterable<El3>> void foreach$extension(Tuple3<It1, It2, It3> $this, Function3<El1, El2, El3, U> f) {
        Iterator elems1 = ((Iterable)$this._1()).iterator();
        Iterator elems2 = ((Iterable)$this._2()).iterator();
        Iterator elems3 = ((Iterable)$this._3()).iterator();
        while (elems1.hasNext() && elems2.hasNext() && elems3.hasNext()) {
            f.apply(elems1.next(), elems2.next(), elems3.next());
        }
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>, El3, It3 extends Iterable<El3>> String toString$extension(Tuple3<It1, It2, It3> $this) {
        return new StringBuilder(13).append("(").append((Iterable)$this._1()).append(", ").append((Iterable)$this._2()).append(", ").append((Iterable)$this._3()).append(").zipped").toString();
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>, El3, It3 extends Iterable<El3>> int hashCode$extension(Tuple3<It1, It2, It3> $this) {
        if ($this == null) {
            throw null;
        }
        return MurmurHash3$.MODULE$.productHash($this);
    }

    public final <El1, It1 extends Iterable<El1>, El2, It2 extends Iterable<El2>, El3, It3 extends Iterable<El3>> boolean equals$extension(Tuple3<It1, It2, It3> $this, Object x$1) {
        if (x$1 instanceof Tuple3Zipped) {
            Tuple3 tuple3 = x$1 == null ? null : ((Tuple3Zipped)x$1).scala$runtime$Tuple3Zipped$$colls();
            Tuple3<It1, It2, It3> tuple32 = $this;
            if (!(tuple32 != null ? !((Object)tuple32).equals(tuple3) : tuple3 != null)) {
                return true;
            }
        }
        return false;
    }

    public static final /* synthetic */ boolean $anonfun$forall$1(Function3 p$1, Object x, Object y, Object z) {
        return !BoxesRunTime.unboxToBoolean(p$1.apply(x, y, z));
    }

    private Tuple3Zipped$() {
    }

    public static final /* synthetic */ Object $anonfun$forall$1$adapted(Function3 p$1, Object x, Object y, Object z) {
        return BoxesRunTime.boxToBoolean(Tuple3Zipped$.$anonfun$forall$1(p$1, x, y, z));
    }
}

