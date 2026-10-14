/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.LinearSeqOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.View;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.AbstractSet;
import scala.collection.immutable.List;
import scala.collection.immutable.ListSet$;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.SetOps;
import scala.collection.immutable.StrictOptimizedSetOps;
import scala.collection.mutable.Builder;
import scala.collection.mutable.ImmutableBuilder;
import scala.math.Integral;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005h\u0001B\u0015+!EBQA\u0015\u0001\u0005\u0002MCa\u0001\u0016\u0001!\n#*\u0006\"B1\u0001\t\u0003\u0012\u0007\"\u00024\u0001\t\u0003\u0012\u0007\"B4\u0001\t\u0003B\u0007\"\u00027\u0001\t\u0003i\u0007\"\u00029\u0001\t\u0003\t\b\"B:\u0001\t\u0003!\b\"\u0002<\u0001\t\u00039\b\"B8\u0001\t#Y\b\"\u0002?\u0001\t#i\b\"\u0002@\u0001\t\u0003zhABA\u0004\u0001!\tI\u0001\u0003\u0005p\u001b\t\u0015\r\u0011\"\u0015|\u0011%\tY!\u0004B\u0001B\u0003%q\u0007\u0003\u0004S\u001b\u0011\u0005\u0011Q\u0002\u0005\u0006C6!\tE\u0019\u0005\u0006M6!\tE\u0019\u0005\t\u0003+i\u0001\u0015\"\u0003\u0002\u0018!)q-\u0004C!Q\"1A.\u0004C!\u0003_A\u0001\"!\u000e\u000eA\u0013%\u0011q\u0007\u0005\u0007a6!\t%a\u0010\t\rMlA\u0011IA\"\u0011!\t9%\u0004Q\u0005\n\u0005%\u0003\"\u0002?\u000e\t#j\bBBA/\u001b\u0011\u00053\u0010\u0003\u0004\u0002`5!\t%`\u0004\b\u0003;T\u0003\u0012AA8\r\u0019I#\u0006#\u0001\u0002f!1!K\bC\u0001\u0003[Bq!!\u001d\u001f\t\u0003\t\u0019hB\u0004\u0002\nzAI!a#\u0007\u000f\u0005\rd\u0004#\u0003\u0002Z\"1!K\tC\u0001\u00037DQA\u001a\u0012\u0005B\tD\u0001\"a$\u001f\t\u0003a\u0013\u0011\u0013\u0005\b\u0003+sB\u0011AAL\u0011\u001d\t\tK\bC\u0001\u0003GC\u0011\"!/\u001f\u0003\u0003%I!a/\u0003\u000f1K7\u000f^*fi*\u00111\u0006L\u0001\nS6lW\u000f^1cY\u0016T!!\f\u0018\u0002\u0015\r|G\u000e\\3di&|gNC\u00010\u0003\u0015\u00198-\u00197b\u0007\u0001)\"AM\u001d\u0014\u000b\u0001\u00194\t\u0013'\u0011\u0007Q*t'D\u0001+\u0013\t1$FA\u0006BEN$(/Y2u'\u0016$\bC\u0001\u001d:\u0019\u0001!QA\u000f\u0001C\u0002m\u0012\u0011!Q\t\u0003y\u0001\u0003\"!\u0010 \u000e\u00039J!a\u0010\u0018\u0003\u000f9{G\u000f[5oOB\u0011Q(Q\u0005\u0003\u0005:\u00121!\u00118z!\u0015!Di\u000e$H\u0013\t)%FA\u000bTiJL7\r^(qi&l\u0017N_3e'\u0016$x\n]:\u0011\u0005Q\u0002\u0001c\u0001\u001b\u0001oA!\u0011JS\u001cG\u001b\u0005a\u0013BA&-\u0005]IE/\u001a:bE2,g)Y2u_JLH)\u001a4bk2$8\u000f\u0005\u0002N!6\taJ\u0003\u0002PY\u00059q-\u001a8fe&\u001c\u0017BA)O\u0005M!UMZ1vYR\u001cVM]5bY&T\u0018M\u00197f\u0003\u0019a\u0014N\\5u}Q\tq)A\u0005dY\u0006\u001c8OT1nKV\ta\u000b\u0005\u0002X=:\u0011\u0001\f\u0018\t\u00033:j\u0011A\u0017\u0006\u00037B\na\u0001\u0010:p_Rt\u0014BA//\u0003\u0019\u0001&/\u001a3fM&\u0011q\f\u0019\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005us\u0013\u0001B:ju\u0016,\u0012a\u0019\t\u0003{\u0011L!!\u001a\u0018\u0003\u0007%sG/A\u0005l]><hnU5{K\u00069\u0011n]#naRLX#A5\u0011\u0005uR\u0017BA6/\u0005\u001d\u0011un\u001c7fC:\f\u0001bY8oi\u0006Lgn\u001d\u000b\u0003S:DQa\u001c\u0004A\u0002]\nA!\u001a7f[\u0006!\u0011N\\2m)\t9%\u000fC\u0003p\u000f\u0001\u0007q'\u0001\u0003fq\u000edGCA$v\u0011\u0015y\u0007\u00021\u00018\u0003!IG/\u001a:bi>\u0014X#\u0001=\u0011\u0007%Kx'\u0003\u0002{Y\tA\u0011\n^3sCR|'/F\u00018\u0003\u0011qW\r\u001f;\u0016\u0003\u001d\u000bq\"\u001b;fe\u0006\u0014G.\u001a$bGR|'/_\u000b\u0003\u0003\u0003\u0001B!SA\u0002\r&\u0019\u0011Q\u0001\u0017\u0003\u001f%#XM]1cY\u00164\u0015m\u0019;pef\u0014AAT8eKN\u0011QbR\u0001\u0006K2,W\u000e\t\u000b\u0005\u0003\u001f\t\u0019\u0002E\u0002\u0002\u00125i\u0011\u0001\u0001\u0005\u0006_B\u0001\raN\u0001\rg&TX-\u00138uKJt\u0017\r\u001c\u000b\u0006G\u0006e\u0011Q\u0004\u0005\u0007\u00037\u0019\u0002\u0019A$\u0002\u00039Da!a\b\u0014\u0001\u0004\u0019\u0017aA1dG\"\u001a1#a\t\u0011\t\u0005\u0015\u00121F\u0007\u0003\u0003OQ1!!\u000b/\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003[\t9CA\u0004uC&d'/Z2\u0015\u0007%\f\t\u0004\u0003\u0004\u00024U\u0001\raN\u0001\u0002K\u0006\u00012m\u001c8uC&t7/\u00138uKJt\u0017\r\u001c\u000b\u0006S\u0006e\u00121\b\u0005\u0007\u000371\u0002\u0019A$\t\r\u0005Mb\u00031\u00018Q\r1\u00121\u0005\u000b\u0004\u000f\u0006\u0005\u0003BBA\u001a/\u0001\u0007q\u0007F\u0002H\u0003\u000bBa!a\r\u0019\u0001\u00049\u0014A\u0004:f[>4X-\u00138uKJt\u0017\r\u001c\u000b\b\u000f\u0006-\u0013qJA*\u0011\u0019\ti%\u0007a\u0001o\u0005\t1\u000e\u0003\u0004\u0002Re\u0001\raR\u0001\u0004GV\u0014\bbBA\u00103\u0001\u0007\u0011Q\u000b\t\u0005i\u0005]s)C\u0002\u0002Z)\u0012A\u0001T5ti\"\u001a\u0011$a\t\u0002\t1\f7\u000f^\u0001\u0005S:LG/K\u0002\u0001E5\u0011A\"R7qifd\u0015n\u001d;TKR\u001cRAHA4\u0003\u0003\u00012!PA5\u0013\r\tYG\f\u0002\u0007\u0003:L(+\u001a4\u0015\u0005\u0005=\u0004C\u0001\u001b\u001f\u0003\u00111'o\\7\u0016\t\u0005U\u00141\u0010\u000b\u0005\u0003o\ny\b\u0005\u00035\u0001\u0005e\u0004c\u0001\u001d\u0002|\u00111\u0011Q\u0010\u0011C\u0002m\u0012\u0011!\u0012\u0005\b\u0003\u0003\u0003\u0003\u0019AAB\u0003\tIG\u000fE\u0003J\u0003\u000b\u000bI(C\u0002\u0002\b2\u0012A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\fA\"R7qifd\u0015n\u001d;TKR\u00042!!$#\u001b\u0005q\u0012!D3naRL\u0018J\\:uC:\u001cW-\u0006\u0002\u0002\u0014B\u0019A\u0007\u0001!\u0002\u000b\u0015l\u0007\u000f^=\u0016\t\u0005e\u0015qT\u000b\u0003\u00037\u0003B\u0001\u000e\u0001\u0002\u001eB\u0019\u0001(a(\u0005\u000bi2#\u0019A\u001e\u0002\u00159,wOQ;jY\u0012,'/\u0006\u0003\u0002&\u0006UVCAAT!!\tI+a,\u00024\u0006]VBAAV\u0015\r\ti\u000bL\u0001\b[V$\u0018M\u00197f\u0013\u0011\t\t,a+\u0003\u000f\t+\u0018\u000e\u001c3feB\u0019\u0001(!.\u0005\u000bi:#\u0019A\u001e\u0011\tQ\u0002\u00111W\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003{\u0003B!a0\u0002J6\u0011\u0011\u0011\u0019\u0006\u0005\u0003\u0007\f)-\u0001\u0003mC:<'BAAd\u0003\u0011Q\u0017M^1\n\t\u0005-\u0017\u0011\u0019\u0002\u0007\u001f\nTWm\u0019;)\u000fy\ty-!6\u0002XB\u0019Q(!5\n\u0007\u0005MgF\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t1aE\u0002#\u0003'#\"!a#\u0002\u000f1K7\u000f^*fi\":Q$a4\u0002V\u0006]\u0007")
public class ListSet<A>
extends AbstractSet<A>
implements StrictOptimizedSetOps<A, ListSet, ListSet<A>>,
DefaultSerializable {
    public static <A> Builder<A, ListSet<A>> newBuilder() {
        return new ImmutableBuilder<A, ListSet<A>>(){

            public $anon$1 addOne(A elem) {
                SetOps setOps = (SetOps)this.elems();
                if (setOps == null) {
                    throw null;
                }
                this.elems_$eq(setOps.incl(elem));
                return this;
            }
        };
    }

    public static <E> ListSet<E> from(IterableOnce<E> it) {
        return ListSet$.MODULE$.from((IterableOnce)it);
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        ListSet$ tabulate_this = ListSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$7$adapted(tabulate_this, n2, n3, n4, n5, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        ListSet$ tabulate_this = ListSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$5$adapted(tabulate_this, n2, n3, n4, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        ListSet$ tabulate_this = ListSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$3$adapted(tabulate_this, n2, n3, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        ListSet$ tabulate_this = ListSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$1$adapted(tabulate_this, n2, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n, Function1 f) {
        ListSet$ tabulate_this = ListSet$.MODULE$;
        View.Tabulate from_source = new View.Tabulate(n, f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        ListSet$ fill_this = ListSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$4(fill_this, n2, n3, n4, n5, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        ListSet$ fill_this = ListSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$3(fill_this, n2, n3, n4, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        ListSet$ fill_this = ListSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$2(fill_this, n2, n3, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        ListSet$ fill_this = ListSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$1(fill_this, n2, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n, Function0 elem) {
        ListSet$ fill_this = ListSet$.MODULE$;
        View.Fill from_source = new View.Fill(n, elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(ListSet$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(ListSet$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        ListSet$ unfold_this = ListSet$.MODULE$;
        View.Unfold from_source = new View.Unfold(init2, f);
        return unfold_this.from((IterableOnce)from_source);
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        ListSet$ iterate_this = ListSet$.MODULE$;
        View.Iterate<Object> from_source = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from((IterableOnce)from_source);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public SetOps concat(IterableOnce that) {
        return StrictOptimizedSetOps.concat$(this, that);
    }

    @Override
    public Tuple2<ListSet<A>, ListSet<A>> partition(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<ListSet<A>, ListSet<A>> span(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<ListSet<A1>, ListSet<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<ListSet<A1>, ListSet<A2>, ListSet<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<A, IterableOnce<B>> f) {
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
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<A, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<A, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<A, B>, C2> b) {
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
    public <A1, A2> Tuple2<ListSet<A1>, ListSet<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
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
    public String className() {
        return "ListSet";
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int knownSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean contains(A elem) {
        return false;
    }

    @Override
    public ListSet<A> incl(A elem) {
        return new Node(this, elem);
    }

    @Override
    public ListSet<A> excl(A elem) {
        return this;
    }

    @Override
    public Iterator<A> iterator() {
        ListSet<A> curr = this;
        List res = Nil$.MODULE$;
        while (!curr.isEmpty()) {
            A a = curr.elem();
            res = res.$colon$colon(a);
            curr = curr.next();
        }
        return ((List)res).iterator();
    }

    public A elem() {
        throw new NoSuchElementException("elem of empty set");
    }

    public ListSet<A> next() {
        throw new NoSuchElementException("next of empty set");
    }

    @Override
    public IterableFactory<ListSet> iterableFactory() {
        return ListSet$.MODULE$;
    }

    public class Node
    extends ListSet<A> {
        private final A elem;
        public final /* synthetic */ ListSet $outer;

        @Override
        public A elem() {
            return this.elem;
        }

        @Override
        public int size() {
            return this.sizeInternal(this, 0);
        }

        @Override
        public int knownSize() {
            return -1;
        }

        private int sizeInternal(ListSet<A> n, int acc) {
            while (!n.isEmpty()) {
                ++acc;
                n = n.next();
            }
            return acc;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(A e) {
            return this.containsInternal(this, e);
        }

        private boolean containsInternal(ListSet<A> n, A e) {
            while (!n.isEmpty()) {
                if (!BoxesRunTime.equals(n.elem(), e)) {
                    n = n.next();
                    continue;
                }
                return true;
            }
            return false;
        }

        @Override
        public ListSet<A> incl(A e) {
            if (this.contains(e)) {
                return this;
            }
            return new Node(this, e);
        }

        @Override
        public ListSet<A> excl(A e) {
            return this.removeInternal(e, this, Nil$.MODULE$);
        }

        /*
         * WARNING - void declaration
         */
        private ListSet<A> removeInternal(A k, ListSet<A> cur, List<ListSet<A>> acc) {
            while (!cur.isEmpty()) {
                if (BoxesRunTime.equals(k, cur.elem())) {
                    void foldLeft_op;
                    void foldLeft_z;
                    Function2<ListSet, ListSet, Node> & Serializable intersect = (Function2<ListSet, ListSet, Node> & Serializable)(t2, h2) -> new Node((ListSet)t2, h2.elem());
                    ListSet listSet = cur.next();
                    if (acc == null) {
                        throw null;
                    }
                    return (ListSet)LinearSeqOps.foldLeft$(acc, (Object)foldLeft_z, (Function2)foldLeft_op);
                }
                if (acc == null) {
                    throw null;
                }
                acc = new $colon$colon(cur, acc);
                cur = cur.next();
            }
            return acc.last();
        }

        @Override
        public ListSet<A> next() {
            return this.scala$collection$immutable$ListSet$Node$$$outer();
        }

        @Override
        public A last() {
            return this.elem();
        }

        @Override
        public ListSet<A> init() {
            return this.next();
        }

        public /* synthetic */ ListSet scala$collection$immutable$ListSet$Node$$$outer() {
            return this.$outer;
        }

        public Node(ListSet $outer, A elem) {
            this.elem = elem;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }
}

