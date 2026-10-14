/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function1;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder$;
import scala.collection.mutable.Growable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005%4q!\u0003\u0006\u0011\u0002\u0007\u0005\u0011\u0003C\u0003'\u0001\u0011\u0005q\u0005C\u0003,\u0001\u0019\u0005q\u0005C\u0003-\u0001\u0019\u0005Q\u0006C\u00032\u0001\u0011\u0005!\u0007C\u00032\u0001\u0011\u0015\u0001\bC\u0004F\u0001E\u0005IQ\u0001$\t\u000bE\u0003AQ\u0001*\t\u000bu\u0003A\u0011\u00010\u0003\u000f\t+\u0018\u000e\u001c3fe*\u00111\u0002D\u0001\b[V$\u0018M\u00197f\u0015\tia\"\u0001\u0006d_2dWm\u0019;j_:T\u0011aD\u0001\u0006g\u000e\fG.Y\u0002\u0001+\r\u0011RdL\n\u0004\u0001M9\u0002C\u0001\u000b\u0016\u001b\u0005q\u0011B\u0001\f\u000f\u0005\u0019\te.\u001f*fMB\u0019\u0001$G\u000e\u000e\u0003)I!A\u0007\u0006\u0003\u0011\u001d\u0013xn^1cY\u0016\u0004\"\u0001H\u000f\r\u0001\u00111a\u0004\u0001EC\u0002}\u0011\u0011!Q\t\u0003A\r\u0002\"\u0001F\u0011\n\u0005\tr!a\u0002(pi\"Lgn\u001a\t\u0003)\u0011J!!\n\b\u0003\u0007\u0005s\u00170\u0001\u0004%S:LG\u000f\n\u000b\u0002QA\u0011A#K\u0005\u0003U9\u0011A!\u00168ji\u0006)1\r\\3be\u00061!/Z:vYR$\u0012A\f\t\u00039=\"a\u0001\r\u0001\u0005\u0006\u0004y\"A\u0001+p\u0003!\u0019\u0018N_3IS:$HC\u0001\u00154\u0011\u0015!D\u00011\u00016\u0003\u0011\u0019\u0018N_3\u0011\u0005Q1\u0014BA\u001c\u000f\u0005\rIe\u000e\u001e\u000b\u0004Qe\u001a\u0005\"\u0002\u001e\u0006\u0001\u0004Y\u0014\u0001B2pY2\u0004$\u0001P!\u0011\u0007ur\u0004)D\u0001\r\u0013\tyDB\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW\r\u0005\u0002\u001d\u0003\u0012I!)OA\u0001\u0002\u0003\u0015\ta\b\u0002\u0004?\u0012\n\u0004b\u0002#\u0006!\u0003\u0005\r!N\u0001\u0006I\u0016dG/Y\u0001\u0013g&TX\rS5oi\u0012\"WMZ1vYR$#'F\u0001HU\t)\u0004jK\u0001J!\tQu*D\u0001L\u0015\taU*A\u0005v]\u000eDWmY6fI*\u0011aJD\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001)L\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u0010g&TX\rS5oi\n{WO\u001c3fIR\u0019\u0001f\u0015+\t\u000bQ:\u0001\u0019A\u001b\t\u000bU;\u0001\u0019\u0001,\u0002\u0019\t|WO\u001c3j]\u001e\u001cu\u000e\u001c71\u0005][\u0006cA\u001fY5&\u0011\u0011\f\u0004\u0002\t\u0013R,'/\u00192mKB\u0011Ad\u0017\u0003\n9R\u000b\t\u0011!A\u0003\u0002}\u00111a\u0018\u00133\u0003%i\u0017\r\u001d*fgVdG/\u0006\u0002`ER\u0011\u0001\r\u001a\t\u00051\u0001Y\u0012\r\u0005\u0002\u001dE\u0012)1\r\u0003b\u0001?\t)a*Z<U_\")Q\r\u0003a\u0001M\u0006\ta\r\u0005\u0003\u0015O:\n\u0017B\u00015\u000f\u0005%1UO\\2uS>t\u0017\u0007")
public interface Builder<A, To>
extends Growable<A> {
    @Override
    public void clear();

    public To result();

    public static /* synthetic */ void sizeHint$(Builder $this, int size) {
        $this.sizeHint(size);
    }

    default public void sizeHint(int size) {
    }

    public static /* synthetic */ void sizeHint$(Builder $this, IterableOnce coll, int delta) {
        $this.sizeHint(coll, delta);
    }

    default public void sizeHint(IterableOnce<?> coll, int delta) {
        int s2 = coll.knownSize();
        if (s2 != -1) {
            this.sizeHint(s2 + delta);
            return;
        }
    }

    public static /* synthetic */ int sizeHint$default$2$(Builder $this) {
        return $this.sizeHint$default$2();
    }

    default public int sizeHint$default$2() {
        return 0;
    }

    public static /* synthetic */ void sizeHintBounded$(Builder $this, int size, Iterable boundingColl) {
        $this.sizeHintBounded(size, boundingColl);
    }

    default public void sizeHintBounded(int size, Iterable<?> boundingColl) {
        int s2 = boundingColl.knownSize();
        if (s2 != -1) {
            this.sizeHint(Math.min(s2, size));
            return;
        }
    }

    public static /* synthetic */ Builder mapResult$(Builder $this, Function1 f) {
        return $this.mapResult(f);
    }

    default public <NewTo> Builder<A, NewTo> mapResult(Function1<To, NewTo> f) {
        return new Builder<A, NewTo>(this, f){
            private final /* synthetic */ Builder $outer;
            private final Function1 f$1;

            public final void sizeHint(IterableOnce<?> coll, int delta) {
                Builder.sizeHint$(this, coll, delta);
            }

            public final int sizeHint$default$2() {
                return Builder.sizeHint$default$2$(this);
            }

            public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
                Builder.sizeHintBounded$(this, size, boundingColl);
            }

            public <NewTo> Builder<A, NewTo> mapResult(Function1<NewTo, NewTo> f) {
                return Builder.mapResult$(this, f);
            }

            public final Growable<A> $plus$eq(A elem) {
                return Growable.$plus$eq$(this, elem);
            }

            public final Growable<A> $plus$eq(A elem1, A elem2, Seq<A> elems) {
                return Growable.$plus$eq$(this, elem1, elem2, elems);
            }

            public final Growable<A> $plus$plus$eq(IterableOnce<A> xs) {
                return Growable.$plus$plus$eq$(this, xs);
            }

            public $anon$1 addOne(A x) {
                Builder builder = this.$outer;
                if (builder == null) {
                    throw null;
                }
                builder.addOne(x);
                return this;
            }

            public void clear() {
                this.$outer.clear();
            }

            public $anon$1 addAll(IterableOnce<A> xs) {
                Builder builder = this.$outer;
                if (builder == null) {
                    throw null;
                }
                builder.addAll(xs);
                return this;
            }

            public void sizeHint(int size) {
                this.$outer.sizeHint(size);
            }

            public NewTo result() {
                return (NewTo)this.f$1.apply(this.$outer.result());
            }

            public int knownSize() {
                return this.$outer.knownSize();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.f$1 = f$1;
            }
        };
    }

    public static void $init$(Builder $this) {
    }
}

