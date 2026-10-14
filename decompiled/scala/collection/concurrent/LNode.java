/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.StringOps$;
import scala.collection.concurrent.MainNode;
import scala.collection.concurrent.TNode;
import scala.collection.concurrent.TrieMap;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.math.Equiv;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ObjectRef;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005q4Q!\u0004\b\u0003!QA\u0001B\u000b\u0001\u0003\u0006\u0004%\ta\u000b\u0005\tk\u0001\u0011\t\u0011)A\u0005Y!Aa\u0007\u0001B\u0001B\u0003%q\u0007C\u0003?\u0001\u0011\u0005q\bC\u0003?\u0001\u0011\u00051\tC\u0003?\u0001\u0011\u0005\u0011\nC\u0003T\u0001\u0011\u0005A\u000bC\u0003X\u0001\u0011\u0005\u0001\fC\u0003`\u0001\u0011\u0005\u0001\rC\u0003f\u0001\u0011\u0005a\rC\u0003o\u0001\u0011\u0005q\u000eC\u0003q\u0001\u0011\u0005\u0011OA\u0003M\u001d>$WM\u0003\u0002\u0010!\u0005Q1m\u001c8dkJ\u0014XM\u001c;\u000b\u0005E\u0011\u0012AC2pY2,7\r^5p]*\t1#A\u0003tG\u0006d\u0017-F\u0002\u00169!\u001a\"\u0001\u0001\f\u0011\t]A\"dJ\u0007\u0002\u001d%\u0011\u0011D\u0004\u0002\t\u001b\u0006LgNT8eKB\u00111\u0004\b\u0007\u0001\t\u0015i\u0002A1\u0001 \u0005\u0005Y5\u0001A\t\u0003A\u0011\u0002\"!\t\u0012\u000e\u0003II!a\t\n\u0003\u000f9{G\u000f[5oOB\u0011\u0011%J\u0005\u0003MI\u00111!\u00118z!\tY\u0002\u0006B\u0003*\u0001\t\u0007qDA\u0001W\u0003\u001d)g\u000e\u001e:jKN,\u0012\u0001\f\t\u0004[A\u0012T\"\u0001\u0018\u000b\u0005=\u0002\u0012!C5n[V$\u0018M\u00197f\u0013\t\tdF\u0001\u0003MSN$\b\u0003B\u001145\u001dJ!\u0001\u000e\n\u0003\rQ+\b\u000f\\33\u0003!)g\u000e\u001e:jKN\u0004\u0013!B3rk&4\bc\u0001\u001d<59\u0011\u0011%O\u0005\u0003uI\tq\u0001]1dW\u0006<W-\u0003\u0002={\t)Q)];jm*\u0011!HE\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007\u0001\u000b%\t\u0005\u0003\u0018\u0001i9\u0003\"\u0002\u0016\u0005\u0001\u0004a\u0003\"\u0002\u001c\u0005\u0001\u00049D\u0003\u0002!E\r\"CQ!R\u0003A\u0002i\t\u0011a\u001b\u0005\u0006\u000f\u0016\u0001\raJ\u0001\u0002m\")a'\u0002a\u0001oQ1\u0001I\u0013'O!JCQa\u0013\u0004A\u0002i\t!a[\u0019\t\u000b53\u0001\u0019A\u0014\u0002\u0005Y\f\u0004\"B(\u0007\u0001\u0004Q\u0012AA63\u0011\u0015\tf\u00011\u0001(\u0003\t1(\u0007C\u00037\r\u0001\u0007q'\u0001\u0005j]N,'\u000f^3e)\r\u0001UK\u0016\u0005\u0006\u000b\u001e\u0001\rA\u0007\u0005\u0006\u000f\u001e\u0001\raJ\u0001\be\u0016lwN^3e)\r1\u0012L\u0017\u0005\u0006\u000b\"\u0001\rA\u0007\u0005\u00067\"\u0001\r\u0001X\u0001\u0003GR\u0004BaF/\u001bO%\u0011aL\u0004\u0002\b)JLW-T1q\u0003\r9W\r\u001e\u000b\u0003C\u0012\u00042!\t2(\u0013\t\u0019'C\u0001\u0004PaRLwN\u001c\u0005\u0006\u000b&\u0001\rAG\u0001\u000bG\u0006\u001c\u0007.\u001a3TSj,GCA4k!\t\t\u0003.\u0003\u0002j%\t\u0019\u0011J\u001c;\t\u000bmS\u0001\u0019A6\u0011\u0005\u0005b\u0017BA7\u0013\u0005\u0019\te.\u001f*fM\u0006I1N\\8x]NK'0\u001a\u000b\u0002O\u000611\u000f\u001e:j]\u001e$\"A\u001d>\u0011\u0005MDX\"\u0001;\u000b\u0005U4\u0018\u0001\u00027b]\u001eT\u0011a^\u0001\u0005U\u00064\u0018-\u0003\u0002zi\n11\u000b\u001e:j]\u001eDQa\u001f\u0007A\u0002\u001d\f1\u0001\\3w\u0001")
public final class LNode<K, V>
extends MainNode<K, V> {
    private final List<Tuple2<K, V>> entries;
    private final Equiv<K> equiv;

    public List<Tuple2<K, V>> entries() {
        return this.entries;
    }

    public LNode<K, V> inserted(K k, V v) {
        Tuple2 tuple2;
        List list;
        Object object = k;
        Nil$ nil$ = Nil$.MODULE$;
        List remove$1_elems = this.entries();
        while (true) {
            $colon$colon<Tuple2> remove$1_acc;
            if (remove$1_elems.isEmpty()) {
                list = remove$1_acc;
                break;
            }
            if (this.equiv.equiv(((Tuple2)remove$1_elems.head())._1(), k)) {
                object = ((Tuple2)remove$1_elems.head())._1();
                list = ((List)remove$1_elems.tail()).$colon$colon$colon(remove$1_acc);
                break;
            }
            tuple2 = (Tuple2)remove$1_elems.head();
            remove$1_acc = new $colon$colon<Tuple2>(tuple2, remove$1_acc);
            remove$1_elems = (List)remove$1_elems.tail();
        }
        Object var5_5 = null;
        nil$ = null;
        tuple2 = null;
        void e = list;
        Tuple2<K, V> tuple22 = new Tuple2<K, V>(object, v);
        if (e == null) {
            throw null;
        }
        return new LNode<K, V>(new $colon$colon<Tuple2<K, V>>(tuple22, e), this.equiv);
    }

    /*
     * WARNING - void declaration
     */
    public MainNode<K, V> removed(K k, TrieMap<K, V> ct) {
        void var6_24;
        void var5_23;
        Object var15_10;
        Object var14_9;
        Object var23_18;
        Object var22_17;
        Object var21_16;
        Object var20_15;
        Object var19_14;
        Object var18_13;
        Object var17_12;
        Object var16_11;
        List list;
        List list2 = this.entries();
        if (list2 == null) {
            throw null;
        }
        List filterNot_this = list2;
        boolean filterNot_filterCommon_isFlipped = true;
        List filterNot_filterCommon_noneIn$1_l = filterNot_this;
        while (true) {
            if (filterNot_filterCommon_noneIn$1_l.isEmpty()) {
                list = Nil$.MODULE$;
                break;
            }
            Object filterNot_filterCommon_noneIn$1_h = filterNot_filterCommon_noneIn$1_l.head();
            List filterNot_filterCommon_noneIn$1_t = (List)filterNot_filterCommon_noneIn$1_l.tail();
            Tuple2 tuple2 = (Tuple2)filterNot_filterCommon_noneIn$1_h;
            if (this.equiv.equiv(tuple2._1(), k) != filterNot_filterCommon_isFlipped) {
                block13: {
                    List filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_next;
                    List filterNot_filterCommon_noneIn$1_allIn$1_remaining = filterNot_filterCommon_noneIn$1_t;
                    while (true) {
                        if (filterNot_filterCommon_noneIn$1_allIn$1_remaining.isEmpty()) {
                            list = filterNot_filterCommon_noneIn$1_l;
                            break block13;
                        }
                        Object filterNot_filterCommon_noneIn$1_allIn$1_x = filterNot_filterCommon_noneIn$1_allIn$1_remaining.head();
                        tuple2 = (Tuple2)filterNot_filterCommon_noneIn$1_allIn$1_x;
                        if (this.equiv.equiv(tuple2._1(), k) == filterNot_filterCommon_isFlipped) break;
                        filterNot_filterCommon_noneIn$1_allIn$1_remaining = (List)filterNot_filterCommon_noneIn$1_allIn$1_remaining.tail();
                    }
                    $colon$colon<Nothing$> filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_newHead = new $colon$colon<Nothing$>((Nothing$)filterNot_filterCommon_noneIn$1_l.head(), Nil$.MODULE$);
                    $colon$colon<Nothing$> filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast = filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_newHead;
                    for (List filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess = (List)filterNot_filterCommon_noneIn$1_l.tail(); filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess != filterNot_filterCommon_noneIn$1_allIn$1_remaining; filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess = (List)filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess.tail()) {
                        $colon$colon<Nothing$> filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_newElem = new $colon$colon<Nothing$>((Nothing$)filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess.head(), Nil$.MODULE$);
                        filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast.next_$eq(filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_newElem);
                        filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast = filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_newElem;
                    }
                    List filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy = filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_next = (List)filterNot_filterCommon_noneIn$1_allIn$1_remaining.tail();
                    while (!filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_next.isEmpty()) {
                        Object filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_head = filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_next.head();
                        tuple2 = (Tuple2)filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_head;
                        if (this.equiv.equiv(tuple2._1(), k) != filterNot_filterCommon_isFlipped) {
                            filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_next = (List)filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_next.tail();
                            continue;
                        }
                        while (filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy != filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_next) {
                            $colon$colon<Nothing$> filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_newElem = new $colon$colon<Nothing$>((Nothing$)filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy.head(), Nil$.MODULE$);
                            filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast.next_$eq(filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_newElem);
                            filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast = filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_newElem;
                            filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy = (List)filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy.tail();
                        }
                        filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy = (List)filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_next.tail();
                        filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_next = (List)filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_next.tail();
                    }
                    if (!filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy.isEmpty()) {
                        filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast.next_$eq(filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy);
                    }
                    list = filterNot_filterCommon_noneIn$1_allIn$1_partialFill$1_newHead;
                    var16_11 = null;
                    var17_12 = null;
                    var18_13 = null;
                    var19_14 = null;
                    var20_15 = null;
                    var21_16 = null;
                    var22_17 = null;
                    var23_18 = null;
                }
                var14_9 = null;
                var15_10 = null;
                var16_11 = null;
                var17_12 = null;
                var18_13 = null;
                var19_14 = null;
                var20_15 = null;
                var21_16 = null;
                var22_17 = null;
                var23_18 = null;
                break;
            }
            filterNot_filterCommon_noneIn$1_l = filterNot_filterCommon_noneIn$1_t;
        }
        Object var11_5 = null;
        Object var12_7 = null;
        Object var13_8 = null;
        var14_9 = null;
        var15_10 = null;
        var16_11 = null;
        var17_12 = null;
        var18_13 = null;
        var19_14 = null;
        var20_15 = null;
        var21_16 = null;
        var22_17 = null;
        var23_18 = null;
        List filterNot_filterCommon_result = list;
        Statics.releaseFence();
        Object var10_19 = null;
        Object var8_3 = null;
        List updmap = filterNot_filterCommon_result;
        int $greater$extension_size = 1;
        if (updmap.sizeCompare($greater$extension_size) > 0) {
            return new LNode<K, V>(updmap, this.equiv);
        }
        Tuple2 tuple2 = (Tuple2)updmap.iterator().next();
        if (tuple2 == null) {
            throw new MatchError((Object)null);
        }
        Object k2 = tuple2._1();
        Object v = tuple2._2();
        return new TNode<void, void>(var5_23, var6_24, ct.computeHash(var5_23));
    }

    public Option<V> get(K k) {
        Option option;
        block4: {
            List list = this.entries();
            if (list == null) {
                throw null;
            }
            List find_these = list;
            while (!find_these.isEmpty()) {
                Tuple2 tuple2 = (Tuple2)find_these.head();
                if (this.equiv.equiv(tuple2._1(), k)) {
                    option = new Some(find_these.head());
                    break block4;
                }
                find_these = (List)find_these.tail();
            }
            option = None$.MODULE$;
        }
        Object var3_2 = null;
        None$ map_this = option;
        if (map_this.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some(((Tuple2)((Option)map_this).get())._2());
    }

    @Override
    public int cachedSize(Object ct) {
        List<Tuple2<K, V>> list = this.entries();
        if (list == null) {
            throw null;
        }
        return list.length();
    }

    @Override
    public int knownSize() {
        return -1;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public String string(int lev) {
        void mkString_end;
        void mkString_sep;
        Object[] objectArray = new Object[1];
        List<Tuple2<K, V>> list = this.entries();
        String string = ", ";
        if (list == null) {
            throw null;
        }
        List<Tuple2<K, V>> mkString_this = list;
        String string2 = "";
        String mkString_start = "";
        Object var4_5 = null;
        string2 = null;
        Object var2_3 = null;
        string = null;
        objectArray[0] = IterableOnceOps.mkString$(mkString_this, mkString_start, (String)mkString_sep, (String)mkString_end);
        return new StringBuilder(0).append(StringOps$.MODULE$.$times$extension(" ", lev)).append(StringOps$.MODULE$.format$extension("LNode(%s)", ScalaRunTime$.MODULE$.genericWrapArray(objectArray))).toString();
    }

    private final List remove$1(List elems, List acc, Object k$3, ObjectRef k0$1) {
        while (!elems.isEmpty()) {
            if (this.equiv.equiv(((Tuple2)elems.head())._1(), k$3)) {
                k0$1.elem = ((Tuple2)elems.head())._1();
                return ((List)elems.tail()).$colon$colon$colon(acc);
            }
            Tuple2 tuple2 = (Tuple2)elems.head();
            if (acc == null) {
                throw null;
            }
            acc = new $colon$colon<Tuple2>(tuple2, acc);
            elems = (List)elems.tail();
        }
        return acc;
    }

    public static final /* synthetic */ Object $anonfun$get$2(Tuple2 x$2) {
        return x$2._2();
    }

    public LNode(List<Tuple2<K, V>> entries, Equiv<K> equiv) {
        this.entries = entries;
        this.equiv = equiv;
    }

    public LNode(K k, V v, Equiv<K> equiv) {
        Tuple2<K, V> tuple2 = new Tuple2<K, V>(k, v);
        Nil$ $colon$colon_this = Nil$.MODULE$;
        Object var5_5 = null;
        this(new $colon$colon<Nothing$>((Nothing$)((Object)tuple2), $colon$colon_this), equiv);
    }

    public LNode(K k1, V v1, K k2, V v2, Equiv<K> equiv) {
        $colon$colon<Nothing$> $colon$colon;
        if (equiv.equiv(k1, k2)) {
            Tuple2<K, V> tuple2 = new Tuple2<K, V>(k2, v2);
            Nil$ $colon$colon_this = Nil$.MODULE$;
            $colon$colon = new $colon$colon<Nothing$>((Nothing$)((Object)tuple2), $colon$colon_this);
            Object var9_7 = null;
        } else {
            Tuple2<K, V> tuple2 = new Tuple2<K, V>(k1, v1);
            Tuple2<K, V> tuple22 = new Tuple2<K, V>(k2, v2);
            Nil$ $colon$colon_this = Nil$.MODULE$;
            Object var10_10 = null;
            $colon$colon<Nothing$> $colon$colon_this2 = new $colon$colon<Nothing$>((Nothing$)((Object)tuple22), $colon$colon_this);
            $colon$colon = new $colon$colon<Nothing$>((Nothing$)((Object)tuple2), (List<Nothing$>)$colon$colon_this2);
            Object var11_11 = null;
        }
        this($colon$colon, equiv);
    }

    public static final /* synthetic */ Object $anonfun$removed$1$adapted(LNode $this, Object k$4, Tuple2 entry) {
        return BoxesRunTime.boxToBoolean($this.equiv.equiv(entry._1(), k$4));
    }

    public static final /* synthetic */ Object $anonfun$get$1$adapted(LNode $this, Object k$5, Tuple2 entry) {
        return BoxesRunTime.boxToBoolean($this.equiv.equiv(entry._1(), k$5));
    }
}

