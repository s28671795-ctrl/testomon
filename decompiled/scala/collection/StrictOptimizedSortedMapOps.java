/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function1;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Map;
import scala.collection.SortedMapOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedMapOps;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005udaB\u0004\t!\u0003\r\t!\u0004\u0005\u0006\u0007\u0002!\t\u0001\u0012\u0005\u0006\u0011\u0002!\t%\u0013\u0005\u0006i\u0002!\t%\u001e\u0005\b\u0003\u001b\u0001A\u0011IA\b\u0011\u001d\t\u0019\u0003\u0001C!\u0003KAq!a\u0012\u0001\t\u0003\nIEA\u000eTiJL7\r^(qi&l\u0017N_3e'>\u0014H/\u001a3NCB|\u0005o\u001d\u0006\u0003\u0013)\t!bY8mY\u0016\u001cG/[8o\u0015\u0005Y\u0011!B:dC2\f7\u0001A\u000b\u0006\u001de\u0019c\u0005P\n\u0005\u0001=\u0019r\b\u0005\u0002\u0011#5\t!\"\u0003\u0002\u0013\u0015\t1\u0011I\\=SK\u001a\u0004b\u0001F\u000b\u0018E\u0015ZT\"\u0001\u0005\n\u0005YA!\u0001D*peR,G-T1q\u001fB\u001c\bC\u0001\r\u001a\u0019\u0001!QA\u0007\u0001C\u0002m\u0011\u0011aS\t\u00039}\u0001\"\u0001E\u000f\n\u0005yQ!a\u0002(pi\"Lgn\u001a\t\u0003!\u0001J!!\t\u0006\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u0019G\u00111A\u0005\u0001CC\u0002m\u0011\u0011A\u0016\t\u00031\u0019\"aa\n\u0001\u0005\u0006\u0004A#AA\"D+\rI\u0013\u0007N\t\u00039)\u00122aK\u00177\r\u0011a\u0003\u0001\u0001\u0016\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0011\tQq\u0003gM\u0005\u0003_!\u00111!T1q!\tA\u0012\u0007B\u00033M\t\u00071DA\u0001Y!\tAB\u0007B\u00036M\t\u00071DA\u0001Za\t9\u0014\b\u0005\u0004\u0015+A\u001aT\u0005\u000f\t\u00031e\"\u0011B\u000f\u0014\u0002\u0002\u0003\u0005)\u0011A\u000e\u0003\u0007}#\u0013\u0007\u0005\u0002\u0019y\u00111Q\b\u0001CC\u0002y\u0012\u0011aQ\t\u00039M\u0001b\u0001\u0006!\u0018E\t[\u0014BA!\t\u0005U\u0019FO]5di>\u0003H/[7ju\u0016$W*\u00199PaN\u0004\"\u0001\u0006\u0018\u0002\r\u0011Jg.\u001b;%)\u0005)\u0005C\u0001\tG\u0013\t9%B\u0001\u0003V]&$\u0018aA7baV\u0019!JT)\u0015\u0005-[GC\u0001'T!\u0011Ab%\u0014)\u0011\u0005aqE!B(\u0003\u0005\u0004Y\"AA&3!\tA\u0012\u000bB\u0003S\u0005\t\u00071D\u0001\u0002We!)AK\u0001a\u0002+\u0006AqN\u001d3fe&tw\rE\u0002W=6s!a\u0016/\u000f\u0005a[V\"A-\u000b\u0005ic\u0011A\u0002\u001fs_>$h(C\u0001\f\u0013\ti&\"A\u0004qC\u000e\\\u0017mZ3\n\u0005}\u0003'\u0001C(sI\u0016\u0014\u0018N\\4\u000b\u0005uS\u0001\u0006B*cQ&\u0004\"a\u00194\u000e\u0003\u0011T!!\u001a\u0006\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002hI\n\u0001\u0012.\u001c9mS\u000eLGOT8u\r>,h\u000eZ\u0001\u0004[N<\u0017%\u00016\u0002\u0003;qu\u000eI5na2L7-\u001b;!\u001fJ$WM]5oOn#3p\u0013\u001a~;\u00022w.\u001e8eAQ|\u0007EY;jY\u0012\u0004\u0013\rI*peR,G-T1q7\u0012Z8JM?-A\u0011ZhKM?^]\u0001Jv.\u001e\u0011nCf\u0004s/\u00198uAQ|\u0007%\u001e9dCN$\b\u0005^8!C\u0002j\u0015\r].%w.kH\u0006\t\u0013|-vl\u0006EZ5sgR\u0004#-\u001f\u0011dC2d\u0017N\\4!AVt7o\u001c:uK\u0012\u0004g\u0006C\u0003m\u0005\u0001\u0007Q.A\u0001g!\u0011\u0001b\u000e]:\n\u0005=T!!\u0003$v]\u000e$\u0018n\u001c82!\u0011\u0001\u0012o\u0006\u0012\n\u0005IT!A\u0002+va2,'\u0007\u0005\u0003\u0011c6\u0003\u0016a\u00024mCRl\u0015\r]\u000b\u0004mjdHcA<\u0002\u0002Q\u0011\u00010 \t\u00051\u0019J8\u0010\u0005\u0002\u0019u\u0012)qj\u0001b\u00017A\u0011\u0001\u0004 \u0003\u0006%\u000e\u0011\ra\u0007\u0005\u0006)\u000e\u0001\u001dA \t\u0004-zK\b\u0006B?cQ&Da\u0001\\\u0002A\u0002\u0005\r\u0001#\u0002\toa\u0006\u0015\u0001#\u0002\u000b\u0002\b\u0005-\u0011bAA\u0005\u0011\ta\u0011\n^3sC\ndWm\u00148dKB!\u0001#]=|\u0003\u0019\u0019wN\\2biV!\u0011\u0011CA\f)\u0011\t\u0019\"a\u0007\u0011\u000ba1s#!\u0006\u0011\u0007a\t9\u0002\u0002\u0004S\t\t\u0007\u0011\u0011D\t\u0003E}Aq!!\b\u0005\u0001\u0004\ty\"\u0001\u0002ygB)A#a\u0002\u0002\"A)\u0001#]\f\u0002\u0016\u000591m\u001c7mK\u000e$XCBA\u0014\u0003_\t\u0019\u0004\u0006\u0003\u0002*\u0005mB\u0003BA\u0016\u0003k\u0001b\u0001\u0007\u0014\u0002.\u0005E\u0002c\u0001\r\u00020\u0011)q*\u0002b\u00017A\u0019\u0001$a\r\u0005\u000bI+!\u0019A\u000e\t\rQ+\u00019AA\u001c!\u00111f,!\f)\u000b\u0005U\"\r[5\t\u000f\u0005uR\u00011\u0001\u0002@\u0005\u0011\u0001O\u001a\t\u0007!\u0005\u0005\u0003/!\u0012\n\u0007\u0005\r#BA\bQCJ$\u0018.\u00197Gk:\u001cG/[8o!\u0019\u0001\u0012/!\f\u00022\u0005)A\u0005\u001d7vgV!\u00111JA))!\ti%!\u0016\u0002\\\u0005}\u0003#\u0002\r'/\u0005=\u0003c\u0001\r\u0002R\u00119\u00111\u000b\u0004C\u0002\u0005e!A\u0001,2\u0011\u001d\t9F\u0002a\u0001\u00033\nQ!\u001a7f[F\u0002R\u0001E9\u0018\u0003\u001fBq!!\u0018\u0007\u0001\u0004\tI&A\u0003fY\u0016l'\u0007C\u0004\u0002b\u0019\u0001\r!a\u0019\u0002\u000b\u0015dW-\\:\u0011\u000bA\t)'!\u0017\n\u0007\u0005\u001d$B\u0001\u0006=e\u0016\u0004X-\u0019;fIzB3BBA6\u0003c\n\u0019(a\u001e\u0002zA\u0019\u0001#!\u001c\n\u0007\u0005=$B\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-\t\u0002\u0002v\u0005)Uk]3!W-\u0002s/\u001b;iA\u0005t\u0007%\u001a=qY&\u001c\u0017\u000e\u001e\u0011d_2dWm\u0019;j_:\u0004\u0013M]4v[\u0016tG\u000fI5ogR,\u0017\r\u001a\u0011pM\u0002Z\u0003e^5uQ\u00022\u0018M]1sON\fQa]5oG\u0016\f#!a\u001f\u0002\rIr\u0013g\r\u00181\u0001")
public interface StrictOptimizedSortedMapOps<K, V, CC extends Map<Object, Object>, C extends SortedMapOps<K, V, CC, C>>
extends SortedMapOps<K, V, CC, C>,
StrictOptimizedMapOps<K, V, Map, C> {
    public static /* synthetic */ Map map$(StrictOptimizedSortedMapOps $this, Function1 f, Ordering ordering) {
        return $this.map(f, ordering);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public <K2, V2> CC map(Function1<Tuple2<K, V>, Tuple2<K2, V2>> f, Ordering<K2> ordering) {
        Builder strictOptimizedMap_b = this.sortedMapFactory().newBuilder(ordering);
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
        return (CC)((Map)strictOptimizedMap_b.result());
    }

    public static /* synthetic */ Map flatMap$(StrictOptimizedSortedMapOps $this, Function1 f, Ordering ordering) {
        return $this.flatMap(f, ordering);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public <K2, V2> CC flatMap(Function1<Tuple2<K, V>, IterableOnce<Tuple2<K2, V2>>> f, Ordering<K2> ordering) {
        Builder strictOptimizedFlatMap_b = this.sortedMapFactory().newBuilder(ordering);
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
        return (CC)((Map)strictOptimizedFlatMap_b.result());
    }

    public static /* synthetic */ Map concat$(StrictOptimizedSortedMapOps $this, IterableOnce xs) {
        return $this.concat(xs);
    }

    @Override
    default public <V2> CC concat(IterableOnce<Tuple2<K, V2>> xs) {
        Builder strictOptimizedConcat_b = this.sortedMapFactory().newBuilder(this.ordering());
        if (strictOptimizedConcat_b == null) {
            throw null;
        }
        strictOptimizedConcat_b.addAll(this);
        strictOptimizedConcat_b.addAll(xs);
        return (CC)((Map)strictOptimizedConcat_b.result());
    }

    public static /* synthetic */ Map collect$(StrictOptimizedSortedMapOps $this, PartialFunction pf, Ordering ordering) {
        return $this.collect(pf, ordering);
    }

    @Override
    default public <K2, V2> CC collect(PartialFunction<Tuple2<K, V>, Tuple2<K2, V2>> pf, Ordering<K2> ordering) {
        Builder strictOptimizedCollect_b = this.sortedMapFactory().newBuilder(ordering);
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
        return (CC)((Map)strictOptimizedCollect_b.result());
    }

    public static /* synthetic */ Map $plus$(StrictOptimizedSortedMapOps $this, Tuple2 elem1, Tuple2 elem2, Seq elems) {
        return $this.$plus(elem1, elem2, elems);
    }

    @Override
    default public <V1> CC $plus(Tuple2<K, V1> elem1, Tuple2<K, V1> elem2, Seq<Tuple2<K, V1>> elems) {
        Map m4 = (Map)this.$plus(elem1).$plus(elem2);
        if (elems.isEmpty()) {
            return (CC)m4;
        }
        return ((SortedMapOps)((Object)m4)).concat(elems);
    }

    public static void $init$(StrictOptimizedSortedMapOps $this) {
    }
}

