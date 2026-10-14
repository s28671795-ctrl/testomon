/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function1;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Set;
import scala.collection.SortedSet;
import scala.collection.SortedSetOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedSetOps;
import scala.collection.mutable.Builder;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dba\u0002\u0004\b!\u0003\r\t\u0001\u0004\u0005\u0006m\u0001!\ta\u000e\u0005\u0006w\u0001!\t\u0005\u0010\u0005\u00067\u0002!\t\u0005\u0018\u0005\u0006U\u0002!\te\u001b\u0005\b\u0003\u0013\u0001A\u0011IA\u0006\u0005m\u0019FO]5di>\u0003H/[7ju\u0016$7k\u001c:uK\u0012\u001cV\r^(qg*\u0011\u0001\"C\u0001\u000bG>dG.Z2uS>t'\"\u0001\u0006\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U!Q\u0002\u0007\u0012.'\u0011\u0001aB\u0005\u0019\u0011\u0005=\u0001R\"A\u0005\n\u0005EI!AB!osJ+g\rE\u0003\u0014)Y\tC&D\u0001\b\u0013\t)rA\u0001\u0007T_J$X\rZ*fi>\u00038\u000f\u0005\u0002\u001811\u0001A!B\r\u0001\u0005\u0004Q\"!A!\u0012\u0005mq\u0002CA\b\u001d\u0013\ti\u0012BA\u0004O_RD\u0017N\\4\u0011\u0005=y\u0012B\u0001\u0011\n\u0005\r\te.\u001f\t\u0003/\t\"aa\t\u0001\u0005\u0006\u0004!#AA\"D+\t)#&\u0005\u0002\u001cMA\u00191cJ\u0015\n\u0005!:!!C*peR,GmU3u!\t9\"\u0006B\u0003,E\t\u0007!DA\u0001Y!\t9R\u0006\u0002\u0004/\u0001\u0011\u0015\ra\f\u0002\u0002\u0007F\u00111D\u0005\t\u0006'E22\u0007L\u0005\u0003e\u001d\u0011Qc\u0015;sS\u000e$x\n\u001d;j[&TX\rZ*fi>\u00038\u000f\u0005\u0002\u0014i%\u0011Qg\u0002\u0002\u0004'\u0016$\u0018A\u0002\u0013j]&$H\u0005F\u00019!\ty\u0011(\u0003\u0002;\u0013\t!QK\\5u\u0003\ri\u0017\r]\u000b\u0003{\u0005#\"A\u0010,\u0015\u0005}\u001a\u0005cA\f#\u0001B\u0011q#\u0011\u0003\u0006\u0005\n\u0011\rA\u0007\u0002\u0002\u0005\")AI\u0001a\u0002\u000b\u0006\u0011QM\u001e\t\u0004\r&\u0003eBA\bH\u0013\tA\u0015\"A\u0004qC\u000e\\\u0017mZ3\n\u0005)[%\u0001C(sI\u0016\u0014\u0018N\\4\u000b\u0005!K\u0001\u0006B\"N'R\u0003\"AT)\u000e\u0003=S!\u0001U\u0005\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002S\u001f\n\u0001\u0012.\u001c9mS\u000eLGOT8u\r>,h\u000eZ\u0001\u0004[N<\u0017%A+\u0002\u007f:{\u0007%[7qY&\u001c\u0017\u000e\u001e\u0011Pe\u0012,'/\u001b8h7\u0012Z()`/!M>,h\u000e\u001a\u0011u_\u0002\u0012W/\u001b7eA\u0005\u00043k\u001c:uK\u0012\u001cV\r^.%w\nkXL\f\u0011Z_V\u0004S.Y=!o\u0006tG\u000f\t;pAU\u00048-Y:uAQ|\u0007%\u0019\u0011TKR\\Fe_!~;\u00022\u0017N]:uA\tL\beY1mY&tw\r\t1v]N|'\u000f^3eA:BQa\u0016\u0002A\u0002a\u000b\u0011A\u001a\t\u0005\u001fe3\u0002)\u0003\u0002[\u0013\tIa)\u001e8di&|g.M\u0001\bM2\fG/T1q+\ti\u0016\r\u0006\u0002_KR\u0011qL\u0019\t\u0004/\t\u0002\u0007CA\fb\t\u0015\u00115A1\u0001\u001b\u0011\u0015!5\u0001q\u0001d!\r1\u0015\n\u0019\u0015\u0005E6\u001bF\u000bC\u0003X\u0007\u0001\u0007a\r\u0005\u0003\u00103Z9\u0007cA\niA&\u0011\u0011n\u0002\u0002\r\u0013R,'/\u00192mK>s7-Z\u0001\u0004u&\u0004XC\u00017|)\ri\u00171\u0001\u000b\u0003]r\u00042a\u0006\u0012p!\u0011y\u0001O\u001d>\n\u0005EL!A\u0002+va2,'G\u000b\u0002\u0017g.\nA\u000f\u0005\u0002vq6\taO\u0003\u0002x\u001f\u0006IQO\\2iK\u000e\\W\rZ\u0005\u0003sZ\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f!\t92\u0010B\u0003C\t\t\u0007!\u0004C\u0003E\t\u0001\u000fQ\u0010E\u0002G\u0013>DC\u0001`'T\u007f\u0006\u0012\u0011\u0011A\u0001\u0002\u00109{\u0007%[7qY&\u001c\u0017\u000e\u001e\u0011Pe\u0012,'/\u001b8h7\u0012Z()`/!M>,h\u000e\u001a\u0011u_\u0002\u0012W/\u001b7eA\u0005\u00043k\u001c:uK\u0012\u001cV\r^.)Im\fU\u0010\f\u0011%w\nk\u0018&\u0018\u0018!3>,\b%\\1zA]\fg\u000e\u001e\u0011u_\u0002*\boY1ti\u0002\"x\u000eI1!'\u0016$8\fJ>B{v\u0003c-\u001b:ti\u0002\u0012\u0017\u0010I2bY2Lgn\u001a\u0011ak:\u001cxN\u001d;fI\u0002t\u0003bBA\u0003\t\u0001\u0007\u0011qA\u0001\u0005i\"\fG\u000fE\u0002\u0014Qj\fqaY8mY\u0016\u001cG/\u0006\u0003\u0002\u000e\u0005UA\u0003BA\b\u0003;!B!!\u0005\u0002\u0018A!qCIA\n!\r9\u0012Q\u0003\u0003\u0006\u0005\u0016\u0011\rA\u0007\u0005\u0007\t\u0016\u0001\u001d!!\u0007\u0011\t\u0019K\u00151\u0003\u0015\u0006\u0003/i5\u000b\u0016\u0005\b\u0003?)\u0001\u0019AA\u0011\u0003\t\u0001h\r\u0005\u0004\u0010\u0003G1\u00121C\u0005\u0004\u0003KI!a\u0004)beRL\u0017\r\u001c$v]\u000e$\u0018n\u001c8")
public interface StrictOptimizedSortedSetOps<A, CC extends SortedSet<Object>, C extends SortedSetOps<A, CC, C>>
extends SortedSetOps<A, CC, C>,
StrictOptimizedSetOps<A, Set, C> {
    public static /* synthetic */ SortedSet map$(StrictOptimizedSortedSetOps $this, Function1 f, Ordering ev) {
        return $this.map(f, ev);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public <B> CC map(Function1<A, B> f, Ordering<B> ev) {
        Builder strictOptimizedMap_b = this.sortedIterableFactory().newBuilder(ev);
        Iterator strictOptimizedMap_it = this.iterator();
        while (strictOptimizedMap_it.hasNext()) {
            void strictOptimizedMap_$plus$eq_elem;
            B b = f.apply(strictOptimizedMap_it.next());
            if (strictOptimizedMap_b == null) {
                throw null;
            }
            strictOptimizedMap_b.addOne(strictOptimizedMap_$plus$eq_elem);
            b = null;
        }
        return (CC)((SortedSet)strictOptimizedMap_b.result());
    }

    public static /* synthetic */ SortedSet flatMap$(StrictOptimizedSortedSetOps $this, Function1 f, Ordering ev) {
        return $this.flatMap(f, ev);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public <B> CC flatMap(Function1<A, IterableOnce<B>> f, Ordering<B> ev) {
        Builder strictOptimizedFlatMap_b = this.sortedIterableFactory().newBuilder(ev);
        Iterator strictOptimizedFlatMap_it = this.iterator();
        while (strictOptimizedFlatMap_it.hasNext()) {
            void strictOptimizedFlatMap_$plus$plus$eq_xs;
            IterableOnce<B> iterableOnce = f.apply(strictOptimizedFlatMap_it.next());
            if (strictOptimizedFlatMap_b == null) {
                throw null;
            }
            strictOptimizedFlatMap_b.addAll(strictOptimizedFlatMap_$plus$plus$eq_xs);
            iterableOnce = null;
        }
        return (CC)((SortedSet)strictOptimizedFlatMap_b.result());
    }

    public static /* synthetic */ SortedSet zip$(StrictOptimizedSortedSetOps $this, IterableOnce that, Ordering ev) {
        return $this.zip(that, ev);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public <B> CC zip(IterableOnce<B> that, Ordering<Tuple2<A, B>> ev) {
        Builder strictOptimizedZip_b = this.sortedIterableFactory().newBuilder(ev);
        Iterator strictOptimizedZip_it1 = this.iterator();
        Iterator<B> strictOptimizedZip_it2 = that.iterator();
        while (strictOptimizedZip_it1.hasNext() && strictOptimizedZip_it2.hasNext()) {
            void strictOptimizedZip_$plus$eq_elem;
            Tuple2 tuple2 = new Tuple2(strictOptimizedZip_it1.next(), strictOptimizedZip_it2.next());
            if (strictOptimizedZip_b == null) {
                throw null;
            }
            strictOptimizedZip_b.addOne(strictOptimizedZip_$plus$eq_elem);
            tuple2 = null;
        }
        return (CC)((SortedSet)strictOptimizedZip_b.result());
    }

    public static /* synthetic */ SortedSet collect$(StrictOptimizedSortedSetOps $this, PartialFunction pf, Ordering ev) {
        return $this.collect(pf, ev);
    }

    @Override
    default public <B> CC collect(PartialFunction<A, B> pf, Ordering<B> ev) {
        Builder strictOptimizedCollect_b = this.sortedIterableFactory().newBuilder(ev);
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
        return (CC)((SortedSet)strictOptimizedCollect_b.result());
    }

    public static void $init$(StrictOptimizedSortedSetOps $this) {
    }
}

