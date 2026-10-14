/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function1;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Map;
import scala.collection.MapOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015caB\u0004\t!\u0003\r\t!\u0004\u0005\u0006\u0017\u0002!\t\u0001\u0014\u0005\u0006!\u0002!\t%\u0015\u0005\u0006A\u0002!\t%\u0019\u0005\u0006]\u0002!\te\u001c\u0005\u0006s\u0002!\tE\u001f\u0005\b\u0003\u001f\u0001A\u0011IA\t\u0005U\u0019FO]5di>\u0003H/[7ju\u0016$W*\u00199PaNT!!\u0003\u0006\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\f\u0003\u0015\u00198-\u00197b\u0007\u0001)RAD\r$M\u0001\u001bB\u0001A\b\u0014\u0005B\u0011\u0001#E\u0007\u0002\u0015%\u0011!C\u0003\u0002\u0007\u0003:L(+\u001a4\u0011\rQ)rCI\u0013@\u001b\u0005A\u0011B\u0001\f\t\u0005\u0019i\u0015\r](qgB\u0011\u0001$\u0007\u0007\u0001\t\u0015Q\u0002A1\u0001\u001c\u0005\u0005Y\u0015C\u0001\u000f !\t\u0001R$\u0003\u0002\u001f\u0015\t9aj\u001c;iS:<\u0007C\u0001\t!\u0013\t\t#BA\u0002B]f\u0004\"\u0001G\u0012\u0005\r\u0011\u0002AQ1\u0001\u001c\u0005\u00051\u0006C\u0001\r'\t\u00199\u0003\u0001\"b\u0001Q\t\u00111iQ\u000b\u0004Smj\u0014C\u0001\u000f+a\rYs&\u000f\t\u0006)1r\u0013\u0007O\u0005\u0003[!\u00111\"\u0013;fe\u0006\u0014G.Z(qgB\u0011\u0001d\f\u0003\na\u0019\n\t\u0011!A\u0003\u0002m\u00111a\u0018\u00132!\t\u0011TG\u0004\u0002\u0015g%\u0011A\u0007C\u0001\ba\u0006\u001c7.Y4f\u0013\t1tGA\u0005B]f\u001cuN\\:ue*\u0011A\u0007\u0003\t\u00031e\"\u0011B\u000f\u0014\u0002\u0002\u0003\u0005)\u0011A\u000e\u0003\u0007}##\u0007B\u0003=M\t\u00071D\u0001\u0003`I\u0011\nD!\u0002 '\u0005\u0004Y\"\u0001B0%II\u0002\"\u0001\u0007!\u0005\r\u0005\u0003AQ1\u0001\u001c\u0005\u0005\u0019\u0005#\u0002\u000bD\u000b\"{\u0014B\u0001#\t\u0005i\u0019FO]5di>\u0003H/[7ju\u0016$\u0017\n^3sC\ndWm\u00149t!\u0011\u0001bi\u0006\u0012\n\u0005\u001dS!A\u0002+va2,'\u0007\u0005\u0002\u0015\u0013&\u0011!\n\u0003\u0002\t\u0013R,'/\u00192mK\u00061A%\u001b8ji\u0012\"\u0012!\u0014\t\u0003!9K!a\u0014\u0006\u0003\tUs\u0017\u000e^\u0001\u0004[\u0006\u0004Xc\u0001*V1R\u00111K\u0017\t\u00051\u0019\"v\u000b\u0005\u0002\u0019+\u0012)aK\u0001b\u00017\t\u00111J\r\t\u00031a#Q!\u0017\u0002C\u0002m\u0011!A\u0016\u001a\t\u000bm\u0013\u0001\u0019\u0001/\u0002\u0003\u0019\u0004B\u0001E/F?&\u0011aL\u0003\u0002\n\rVt7\r^5p]F\u0002B\u0001\u0005$U/\u00069a\r\\1u\u001b\u0006\u0004Xc\u00012fOR\u00111\r\u001b\t\u00051\u0019\"g\r\u0005\u0002\u0019K\u0012)ak\u0001b\u00017A\u0011\u0001d\u001a\u0003\u00063\u000e\u0011\ra\u0007\u0005\u00067\u000e\u0001\r!\u001b\t\u0005!u+%\u000eE\u0002\u0015W6L!\u0001\u001c\u0005\u0003\u0019%#XM]1cY\u0016|enY3\u0011\tA1EMZ\u0001\u0007G>t7-\u0019;\u0016\u0005A\u001cHCA9v!\u0011Abe\u0006:\u0011\u0005a\u0019H!B-\u0005\u0005\u0004!\u0018C\u0001\u0012 \u0011\u00151H\u00011\u0001x\u0003\u0019\u0019XO\u001a4jqB\u0019Ac\u001b=\u0011\tA1uC]\u0001\bG>dG.Z2u+\u0011Yh0!\u0001\u0015\u0007q\f\u0019\u0001\u0005\u0003\u0019Mu|\bC\u0001\r\u007f\t\u00151VA1\u0001\u001c!\rA\u0012\u0011\u0001\u0003\u00063\u0016\u0011\ra\u0007\u0005\b\u0003\u000b)\u0001\u0019AA\u0004\u0003\t\u0001h\r\u0005\u0004\u0011\u0003\u0013)\u0015QB\u0005\u0004\u0003\u0017Q!a\u0004)beRL\u0017\r\u001c$v]\u000e$\u0018n\u001c8\u0011\tA1Up`\u0001\u0006IAdWo]\u000b\u0005\u0003'\tI\u0002\u0006\u0005\u0002\u0016\u0005u\u00111EA\u0014!\u0015AbeFA\f!\rA\u0012\u0011\u0004\u0003\u0007\u000371!\u0019\u0001;\u0003\u0005Y\u000b\u0004bBA\u0010\r\u0001\u0007\u0011\u0011E\u0001\u0006K2,W.\r\t\u0006!\u0019;\u0012q\u0003\u0005\b\u0003K1\u0001\u0019AA\u0011\u0003\u0015)G.Z73\u0011\u001d\tIC\u0002a\u0001\u0003W\tQ!\u001a7f[N\u0004R\u0001EA\u0017\u0003CI1!a\f\u000b\u0005)a$/\u001a9fCR,GM\u0010\u0015\f\r\u0005M\u0012\u0011HA\u001e\u0003\u007f\t\t\u0005E\u0002\u0011\u0003kI1!a\u000e\u000b\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\t\ti$A#Vg\u0016\u00043f\u000b\u0011xSRD\u0007%\u00198!Kb\u0004H.[2ji\u0002\u001aw\u000e\u001c7fGRLwN\u001c\u0011be\u001e,X.\u001a8uA%t7\u000f^3bI\u0002zg\rI\u0016!o&$\b\u000e\t<be\u0006\u0014xm]\u0001\u0006g&t7-Z\u0011\u0003\u0003\u0007\naA\r\u00182g9\u0002\u0004")
public interface StrictOptimizedMapOps<K, V, CC extends IterableOps<?, Object, ?>, C>
extends MapOps<K, V, CC, C>,
StrictOptimizedIterableOps<Tuple2<K, V>, Iterable, C> {
    public static /* synthetic */ IterableOps map$(StrictOptimizedMapOps $this, Function1 f) {
        return $this.map(f);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public <K2, V2> CC map(Function1<Tuple2<K, V>, Tuple2<K2, V2>> f) {
        Builder strictOptimizedMap_b = this.mapFactory().newBuilder();
        Iterator strictOptimizedMap_it = this.iterator();
        while (strictOptimizedMap_it.hasNext()) {
            void strictOptimizedMap_$plus$eq_elem;
            Tuple2<K2, V2> tuple2 = f.apply((Tuple2<K, V>)strictOptimizedMap_it.next());
            if (strictOptimizedMap_b == null) {
                throw null;
            }
            strictOptimizedMap_b.addOne(strictOptimizedMap_$plus$eq_elem);
            tuple2 = null;
        }
        return (CC)((IterableOps)strictOptimizedMap_b.result());
    }

    public static /* synthetic */ IterableOps flatMap$(StrictOptimizedMapOps $this, Function1 f) {
        return $this.flatMap(f);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public <K2, V2> CC flatMap(Function1<Tuple2<K, V>, IterableOnce<Tuple2<K2, V2>>> f) {
        Builder strictOptimizedFlatMap_b = this.mapFactory().newBuilder();
        Iterator strictOptimizedFlatMap_it = this.iterator();
        while (strictOptimizedFlatMap_it.hasNext()) {
            void strictOptimizedFlatMap_$plus$plus$eq_xs;
            IterableOnce<Tuple2<K2, V2>> iterableOnce = f.apply((Tuple2<K, V>)strictOptimizedFlatMap_it.next());
            if (strictOptimizedFlatMap_b == null) {
                throw null;
            }
            strictOptimizedFlatMap_b.addAll(strictOptimizedFlatMap_$plus$plus$eq_xs);
            iterableOnce = null;
        }
        return (CC)((IterableOps)strictOptimizedFlatMap_b.result());
    }

    public static /* synthetic */ IterableOps concat$(StrictOptimizedMapOps $this, IterableOnce suffix) {
        return $this.concat(suffix);
    }

    @Override
    default public <V2> CC concat(IterableOnce<Tuple2<K, V2>> suffix) {
        Builder strictOptimizedConcat_b = this.mapFactory().newBuilder();
        if (strictOptimizedConcat_b == null) {
            throw null;
        }
        strictOptimizedConcat_b.addAll(this);
        strictOptimizedConcat_b.addAll(suffix);
        return (CC)((IterableOps)strictOptimizedConcat_b.result());
    }

    public static /* synthetic */ IterableOps collect$(StrictOptimizedMapOps $this, PartialFunction pf) {
        return $this.collect(pf);
    }

    @Override
    default public <K2, V2> CC collect(PartialFunction<Tuple2<K, V>, Tuple2<K2, V2>> pf) {
        Builder strictOptimizedCollect_b = this.mapFactory().newBuilder();
        Object strictOptimizedCollect_marker = Statics.pfMarker;
        Iterator strictOptimizedCollect_it = this.iterator();
        while (strictOptimizedCollect_it.hasNext()) {
            Object strictOptimizedCollect_elem = strictOptimizedCollect_it.next();
            Object strictOptimizedCollect_v = pf.applyOrElse(strictOptimizedCollect_elem, arg_0 -> StrictOptimizedIterableOps.$anonfun$strictOptimizedCollect$1(strictOptimizedCollect_marker, arg_0));
            if (strictOptimizedCollect_marker == strictOptimizedCollect_v) continue;
            if (strictOptimizedCollect_b == null) {
                throw null;
            }
            strictOptimizedCollect_b.addOne(strictOptimizedCollect_v);
        }
        return (CC)((IterableOps)strictOptimizedCollect_b.result());
    }

    public static /* synthetic */ IterableOps $plus$(StrictOptimizedMapOps $this, Tuple2 elem1, Tuple2 elem2, Seq elems) {
        return $this.$plus(elem1, elem2, elems);
    }

    @Override
    default public <V1> CC $plus(Tuple2<K, V1> elem1, Tuple2<K, V1> elem2, Seq<Tuple2<K, V1>> elems) {
        Object m4 = ((Map)this.$plus(elem1)).$plus(elem2);
        if (elems.isEmpty()) {
            return m4;
        }
        return (CC)((IterableOps)m4.concat(elems));
    }

    public static void $init$(StrictOptimizedMapOps $this) {
    }
}

