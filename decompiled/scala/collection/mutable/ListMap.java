/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.MapFactory;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedMapOps;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.AbstractMap;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Iterable;
import scala.collection.mutable.ListMap$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.util.Either;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ScalaSignature(bytes="\u0006\u0005\u0005Uf\u0001\u0002\f\u0018\u0001yAQA\u0014\u0001\u0005\u0002=CQ\u0001\u0015\u0001\u0005BECa!\u0016\u0001!B\u00131\u0006B\u0002/\u0001A\u0003&Q\fC\u0003a\u0001\u0011\u0005\u0011\rC\u0003h\u0001\u0011\u0005\u0001\u000eC\u0003m\u0001\u0011\u0015S\u000eC\u0003r\u0001\u0011\u0015#\u000fC\u0003u\u0001\u0011%Q\u000fC\u0004\u0002\u0006\u0001!)%a\u0002\t\u000f\u0005=\u0001\u0001\"\u0012\u0002\u0012!9\u00111\u0003\u0001\u0005B\u0005E\u0001bBA\u000b\u0001\u0011\u0005\u0013q\u0003\u0005\t\u0003?\u0001\u0001\u0015\"\u0015\u0002\"\u001d9\u0011qI\f\t\u0002\u0005%cA\u0002\f\u0018\u0011\u0003\tY\u0005\u0003\u0004O!\u0011\u0005\u00111\u000b\u0005\b\u0003+\u0002B\u0011AA,\u0011\u001d\t)\u0007\u0005C\u0001\u0003OBq!!!\u0011\t\u0003\t\u0019\tC\u0005\u0002\u001aB\t\t\u0011\"\u0003\u0002\u001c\n9A*[:u\u001b\u0006\u0004(B\u0001\r\u001a\u0003\u001diW\u000f^1cY\u0016T!AG\u000e\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\u001d\u0003\u0015\u00198-\u00197b\u0007\u0001)2a\b\u00142'\u001d\u0001\u0001e\r\u001dC\u000b\"\u0003B!\t\u0012%a5\tq#\u0003\u0002$/\tY\u0011IY:ue\u0006\u001cG/T1q!\t)c\u0005\u0004\u0001\u0005\u000b\u001d\u0002!\u0019\u0001\u0015\u0003\u0003-\u000b\"!K\u0017\u0011\u0005)ZS\"A\u000e\n\u00051Z\"a\u0002(pi\"Lgn\u001a\t\u0003U9J!aL\u000e\u0003\u0007\u0005s\u0017\u0010\u0005\u0002&c\u0011)!\u0007\u0001b\u0001Q\t\ta\u000b\u0005\u0004\"i\u0011\u0002dgN\u0005\u0003k]\u0011a!T1q\u001fB\u001c\bCA\u0011\u0001!\u0011\t\u0003\u0001\n\u0019\u0011\u000beRDhP\u001c\u000e\u0003eI!aO\r\u00035M#(/[2u\u001fB$\u0018.\\5{K\u0012LE/\u001a:bE2,w\n]:\u0011\t)jD\u0005M\u0005\u0003}m\u0011a\u0001V;qY\u0016\u0014\u0004CA\u0011A\u0013\t\tuC\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\u0019I4\t\n\u00197o%\u0011A)\u0007\u0002\u0016'R\u0014\u0018n\u0019;PaRLW.\u001b>fI6\u000b\u0007o\u00149t!\u0019Id\t\n\u00197\u007f%\u0011q)\u0007\u0002\u0013\u001b\u0006\u0004h)Y2u_JLH)\u001a4bk2$8\u000f\u0005\u0002J\u00196\t!J\u0003\u0002L3\u00059q-\u001a8fe&\u001c\u0017BA'K\u0005M!UMZ1vYR\u001cVM]5bY&T\u0018M\u00197f\u0003\u0019a\u0014N\\5u}Q\tq'\u0001\u0006nCB4\u0015m\u0019;pef,\u0012A\u0015\t\u0004sM3\u0014B\u0001+\u001a\u0005)i\u0015\r\u001d$bGR|'/_\u0001\u0006K2,Wn\u001d\t\u0004/jcT\"\u0001-\u000b\u0005eK\u0012!C5n[V$\u0018M\u00197f\u0013\tY\u0006L\u0001\u0003MSN$\u0018aA:juB\u0011!FX\u0005\u0003?n\u00111!\u00138u\u0003\r9W\r\u001e\u000b\u0003E\u0016\u00042AK21\u0013\t!7D\u0001\u0004PaRLwN\u001c\u0005\u0006M\u0016\u0001\r\u0001J\u0001\u0004W\u0016L\u0018\u0001C5uKJ\fGo\u001c:\u0016\u0003%\u00042!\u000f6=\u0013\tY\u0017D\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003\u0019\tG\rZ(oKR\u0011an\\\u0007\u0002\u0001!)\u0001o\u0002a\u0001y\u0005\u00111N^\u0001\fgV\u0014GO]1di>sW\r\u0006\u0002og\")a\r\u0003a\u0001I\u00051!/Z7pm\u0016$BA^<ysB!!&\u0010,%\u0011\u00151\u0017\u00021\u0001%\u0011\u0015)\u0016\u00021\u0001W\u0011\u0015Q\u0018\u00021\u0001W\u0003\r\t7m\u0019\u0015\u0003\u0013q\u00042!`A\u0001\u001b\u0005q(BA@\u001c\u0003)\tgN\\8uCRLwN\\\u0005\u0004\u0003\u0007q(a\u0002;bS2\u0014XmY\u0001\u0006G2,\u0017M\u001d\u000b\u0003\u0003\u0013\u00012AKA\u0006\u0013\r\tia\u0007\u0002\u0005+:LG/\u0001\u0003tSj,W#A/\u0002\u0013-twn\u001e8TSj,\u0017aB5t\u000b6\u0004H/_\u000b\u0003\u00033\u00012AKA\u000e\u0013\r\tib\u0007\u0002\b\u0005>|G.Z1o\u00031\u0019HO]5oOB\u0013XMZ5y+\t\t\u0019\u0003\u0005\u0003\u0002&\u0005=RBAA\u0014\u0015\u0011\tI#a\u000b\u0002\t1\fgn\u001a\u0006\u0003\u0003[\tAA[1wC&!\u0011\u0011GA\u0014\u0005\u0019\u0019FO]5oO\"Z\u0001!!\u000e\u0002<\u0005u\u0012\u0011IA\"!\rQ\u0013qG\u0005\u0004\u0003sY\"A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017EAA \u0003\u0015+6/\u001a\u0011b]\u0002JW.\\;uC\ndWM\f'jgRl\u0015\r\u001d\u0011bgNLwM\\3eAQ|\u0007%\u0019\u0011wCJ\u0004\u0013N\\:uK\u0006$\u0007e\u001c4![V$\u0018M\u00197f]1K7\u000f^'ba\u0006)1/\u001b8dK\u0006\u0012\u0011QI\u0001\u0007e9\n4G\f\u0019\u0002\u000f1K7\u000f^'baB\u0011\u0011\u0005E\n\u0005!\u00055#\u000bE\u0002+\u0003\u001fJ1!!\u0015\u001c\u0005\u0019\te.\u001f*fMR\u0011\u0011\u0011J\u0001\u0006K6\u0004H/_\u000b\u0007\u00033\ny&a\u0019\u0016\u0005\u0005m\u0003CB\u0011\u0001\u0003;\n\t\u0007E\u0002&\u0003?\"Qa\n\nC\u0002!\u00022!JA2\t\u0015\u0011$C1\u0001)\u0003\u00111'o\\7\u0016\r\u0005%\u0014qNA:)\u0011\tY'!\u001e\u0011\r\u0005\u0002\u0011QNA9!\r)\u0013q\u000e\u0003\u0006OM\u0011\r\u0001\u000b\t\u0004K\u0005MD!\u0002\u001a\u0014\u0005\u0004A\u0003bBA<'\u0001\u0007\u0011\u0011P\u0001\u0003SR\u0004R!OA>\u0003\u007fJ1!! \u001a\u00051IE/\u001a:bE2,wJ\\2f!\u0019QS(!\u001c\u0002r\u0005Qa.Z<Ck&dG-\u001a:\u0016\r\u0005\u0015\u0015\u0011SAK+\t\t9\tE\u0004\"\u0003\u0013\u000bi)a&\n\u0007\u0005-uCA\u0004Ck&dG-\u001a:\u0011\r)j\u0014qRAJ!\r)\u0013\u0011\u0013\u0003\u0006OQ\u0011\r\u0001\u000b\t\u0004K\u0005UE!\u0002\u001a\u0015\u0005\u0004A\u0003CB\u0011\u0001\u0003\u001f\u000b\u0019*\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002\u001eB!\u0011QEAP\u0013\u0011\t\t+a\n\u0003\r=\u0013'.Z2uQ-\u0001\u0012QGA\u001e\u0003{\t\t%a\u0011)\u000fA\t9+!,\u00020B\u0019!&!+\n\u0007\u0005-6D\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t1\u0001K\u0006\u0010\u0003k\tY$!\u0010\u0002B\u0005\r\u0003fB\b\u0002(\u00065\u0016q\u0016")
public class ListMap<K, V>
extends AbstractMap<K, V>
implements StrictOptimizedMapOps<K, V, ListMap, ListMap<K, V>>,
DefaultSerializable {
    private List<Tuple2<K, V>> elems = Nil$.MODULE$;
    private int siz = 0;

    public static <K, V> Builder<Tuple2<K, V>, ListMap<K, V>> newBuilder() {
        return ListMap$.MODULE$.newBuilder();
    }

    public static <K, V> ListMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        return ListMap$.MODULE$.from((IterableOnce)it);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public IterableOps map(Function1 f) {
        return StrictOptimizedMapOps.map$(this, f);
    }

    @Override
    public IterableOps flatMap(Function1 f) {
        return StrictOptimizedMapOps.flatMap$(this, f);
    }

    @Override
    public IterableOps concat(IterableOnce suffix) {
        return StrictOptimizedMapOps.concat$(this, suffix);
    }

    @Override
    public IterableOps collect(PartialFunction pf) {
        return StrictOptimizedMapOps.collect$(this, pf);
    }

    @Override
    public IterableOps $plus(Tuple2 elem1, Tuple2 elem2, Seq elems) {
        return StrictOptimizedMapOps.$plus$(this, elem1, elem2, elems);
    }

    @Override
    public Tuple2<ListMap<K, V>, ListMap<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<ListMap<K, V>, ListMap<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> unzip(Function1<Tuple2<K, V>, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<Iterable<A1>, Iterable<A2>, Iterable<A3>> unzip3(Function1<Tuple2<K, V>, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<Tuple2<K, V>, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<Tuple2<K, V>, IterableOnce<B>> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatMap$(this, b, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedConcat(IterableOnce<B> that, Builder<B, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedConcat$(this, that, b);
    }

    @Override
    public Object collect(PartialFunction pf) {
        return StrictOptimizedIterableOps.collect$(this, pf);
    }

    @Override
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<Tuple2<K, V>, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<Tuple2<K, V>, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<Tuple2<K, V>, B>, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedZip$(this, that, b);
    }

    @Override
    public Object zipWithIndex() {
        return StrictOptimizedIterableOps.zipWithIndex$(this);
    }

    @Override
    public Object scanLeft(Object z, Function2 op) {
        return StrictOptimizedIterableOps.scanLeft$(this, z, op);
    }

    @Override
    public Object filter(Function1 pred) {
        return StrictOptimizedIterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public Object filterImpl(Function1 pred, boolean isFlipped) {
        return StrictOptimizedIterableOps.filterImpl$(this, pred, isFlipped);
    }

    @Override
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public Object takeRight(int n) {
        return StrictOptimizedIterableOps.takeRight$(this, n);
    }

    @Override
    public Object dropRight(int n) {
        return StrictOptimizedIterableOps.dropRight$(this, n);
    }

    @Override
    public MapFactory<ListMap> mapFactory() {
        return ListMap$.MODULE$;
    }

    @Override
    public Option<V> get(K key) {
        Option option;
        block4: {
            List list = this.elems;
            if (list == null) {
                throw null;
            }
            List find_these = list;
            while (!find_these.isEmpty()) {
                Tuple2 tuple2 = (Tuple2)find_these.head();
                if (ListMap.$anonfun$get$1(key, tuple2)) {
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
    public Iterator<Tuple2<K, V>> iterator() {
        return this.elems.iterator();
    }

    /*
     * WARNING - void declaration
     */
    public final ListMap<K, V> addOne(Tuple2<K, V> kv) {
        void var3_3;
        void var4_4;
        Tuple2<List<Tuple2<K, V>>, K> tuple2 = this.remove(kv._1(), this.elems, Nil$.MODULE$);
        if (tuple2 == null) {
            throw new MatchError((Object)null);
        }
        List<Tuple2<K, V>> e = tuple2._1();
        K key0 = tuple2._2();
        V v = kv._2();
        Tuple2<void, V> tuple22 = new Tuple2<void, V>(var4_4, v);
        if (var3_3 == null) {
            throw null;
        }
        this.elems = new $colon$colon<Tuple2<void, V>>(tuple22, var3_3);
        ++this.siz;
        return this;
    }

    public final ListMap<K, V> subtractOne(K key) {
        this.elems = this.remove(key, this.elems, Nil$.MODULE$)._1();
        return this;
    }

    private Tuple2<List<Tuple2<K, V>>, K> remove(K key, List<Tuple2<K, V>> elems, List<Tuple2<K, V>> acc) {
        while (!elems.isEmpty()) {
            if (BoxesRunTime.equals(((Tuple2)elems.head())._1(), key)) {
                --this.siz;
                return new Tuple2(((List)elems.tail()).$colon$colon$colon(acc), ((Tuple2)elems.head())._1());
            }
            Tuple2 tuple2 = (Tuple2)elems.head();
            if (acc == null) {
                throw null;
            }
            acc = new $colon$colon<Tuple2<K, V>>(tuple2, acc);
            elems = (List)elems.tail();
        }
        return new Tuple2<List<Tuple2<K, V>>, K>(acc, key);
    }

    @Override
    public final void clear() {
        this.elems = Nil$.MODULE$;
        this.siz = 0;
    }

    @Override
    public final int size() {
        return this.siz;
    }

    @Override
    public int knownSize() {
        return this.size();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public String stringPrefix() {
        return "ListMap";
    }

    public static final /* synthetic */ boolean $anonfun$get$1(Object key$1, Tuple2 x$1) {
        return BoxesRunTime.equals(x$1._1(), key$1);
    }

    public static final /* synthetic */ Object $anonfun$get$2(Tuple2 x$2) {
        return x$2._2();
    }

    public static final /* synthetic */ Object $anonfun$get$1$adapted(Object key$1, Tuple2 x$1) {
        return BoxesRunTime.boxToBoolean(ListMap.$anonfun$get$1(key$1, x$1));
    }
}

