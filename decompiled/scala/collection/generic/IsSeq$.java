/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.generic;

import java.lang.reflect.Array;
import scala.$less$colon$less;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.ArrayOps$;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Searching;
import scala.collection.Seq;
import scala.collection.SeqOps;
import scala.collection.SeqView;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StringOps;
import scala.collection.StringView;
import scala.collection.View;
import scala.collection.WithFilter;
import scala.collection.generic.IsIterable;
import scala.collection.generic.IsIterableOnce;
import scala.collection.generic.IsSeq;
import scala.collection.generic.IsSeq$;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Range;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.immutable.WrappedString;
import scala.collection.mutable.ArrayBuilder;
import scala.collection.mutable.ArraySeq$;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Map;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Either;

public final class IsSeq$ {
    public static final IsSeq$ MODULE$ = new IsSeq$();
    private static final IsSeq<Seq<Object>> seqOpsIsSeqVal = new IsSeq<Seq<Object>>(){
        private Function1<Seq<Object>, SeqOps<Object, Iterable, Object>> conversion;

        public Function1<Seq<Object>, SeqOps<Object, Iterable, Object>> conversion() {
            return this.conversion;
        }

        public void scala$collection$generic$IsSeq$_setter_$conversion_$eq(Function1<Seq<Object>, SeqOps<Object, Iterable, Object>> x$1) {
            this.conversion = x$1;
        }

        public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<Seq<Object>, IterableOps<Object, Iterable, Object>> x$1) {
        }

        public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<Seq<Object>, IterableOnce<Object>> x$1) {
        }

        public SeqOps<Object, Iterable, Object> apply(Seq<Object> coll) {
            return coll;
        }
        {
            IsIterableOnce.$init$(this);
            IsIterable.$init$(this);
            IsSeq.$init$(this);
            Statics.releaseFence();
        }
    };
    private static final IsSeq<String> stringIsSeq = new IsSeq<String>(){
        private Function1<String, SeqOps<Object, Iterable, String>> conversion;

        public Function1<String, SeqOps<Object, Iterable, String>> conversion() {
            return this.conversion;
        }

        public void scala$collection$generic$IsSeq$_setter_$conversion_$eq(Function1<String, SeqOps<Object, Iterable, String>> x$1) {
            this.conversion = x$1;
        }

        public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<String, IterableOps<Object, Iterable, String>> x$1) {
        }

        public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<String, IterableOnce<Object>> x$1) {
        }

        public SeqOps<Object, IndexedSeq, String> apply(String s2) {
            return new SeqOps<Object, ArraySeq, String>(null, s2){
                private final String s$1;

                public /* synthetic */ Object scala$collection$SeqOps$$super$concat(IterableOnce suffix) {
                    return IterableOps.concat$(this, suffix);
                }

                public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(int otherSize) {
                    return IterableOps.sizeCompare$((IterableOps)this, otherSize);
                }

                public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(Iterable that) {
                    return IterableOps.sizeCompare$((IterableOps)this, that);
                }

                public SeqView<Object> view() {
                    return SeqOps.view$(this);
                }

                public Object prepended(Object elem) {
                    return SeqOps.prepended$(this, elem);
                }

                public final Object $plus$colon(Object elem) {
                    return SeqOps.$plus$colon$(this, elem);
                }

                public Object appended(Object elem) {
                    return SeqOps.appended$(this, elem);
                }

                public final Object $colon$plus(Object elem) {
                    return SeqOps.$colon$plus$(this, elem);
                }

                public Object prependedAll(IterableOnce prefix) {
                    return SeqOps.prependedAll$(this, prefix);
                }

                public final Object $plus$plus$colon(IterableOnce prefix) {
                    return SeqOps.$plus$plus$colon$(this, prefix);
                }

                public Object appendedAll(IterableOnce suffix) {
                    return SeqOps.appendedAll$(this, suffix);
                }

                public final Object $colon$plus$plus(IterableOnce suffix) {
                    return SeqOps.$colon$plus$plus$(this, suffix);
                }

                public final Object concat(IterableOnce suffix) {
                    return SeqOps.concat$(this, suffix);
                }

                public final Object union(Seq that) {
                    return SeqOps.union$(this, that);
                }

                public final int size() {
                    return SeqOps.size$(this);
                }

                public Object distinct() {
                    return SeqOps.distinct$(this);
                }

                public Object distinctBy(Function1 f) {
                    return SeqOps.distinctBy$(this, f);
                }

                public Object reverse() {
                    return SeqOps.reverse$(this);
                }

                public Iterator<Object> reverseIterator() {
                    return SeqOps.reverseIterator$(this);
                }

                public <B> boolean startsWith(IterableOnce<B> that, int offset) {
                    return SeqOps.startsWith$(this, that, offset);
                }

                public <B> int startsWith$default$2() {
                    return SeqOps.startsWith$default$2$(this);
                }

                public <B> boolean endsWith(Iterable<B> that) {
                    return SeqOps.endsWith$(this, that);
                }

                public boolean isDefinedAt(int idx) {
                    return SeqOps.isDefinedAt$(this, idx);
                }

                public Object padTo(int len, Object elem) {
                    return SeqOps.padTo$(this, len, elem);
                }

                public final int segmentLength(Function1<Object, Object> p) {
                    return SeqOps.segmentLength$(this, p);
                }

                public int segmentLength(Function1<Object, Object> p, int from) {
                    return SeqOps.segmentLength$(this, p, from);
                }

                public final int prefixLength(Function1<Object, Object> p) {
                    return SeqOps.prefixLength$(this, p);
                }

                public int indexWhere(Function1<Object, Object> p, int from) {
                    return SeqOps.indexWhere$(this, p, from);
                }

                public int indexWhere(Function1<Object, Object> p) {
                    return SeqOps.indexWhere$(this, p);
                }

                public <B> int indexOf(B elem, int from) {
                    return SeqOps.indexOf$(this, elem, from);
                }

                public <B> int indexOf(B elem) {
                    return SeqOps.indexOf$(this, elem);
                }

                public <B> int lastIndexOf(B elem, int end) {
                    return SeqOps.lastIndexOf$(this, elem, end);
                }

                public <B> int lastIndexOf$default$2() {
                    return SeqOps.lastIndexOf$default$2$(this);
                }

                public int lastIndexWhere(Function1<Object, Object> p, int end) {
                    return SeqOps.lastIndexWhere$(this, p, end);
                }

                public int lastIndexWhere(Function1<Object, Object> p) {
                    return SeqOps.lastIndexWhere$(this, p);
                }

                public <B> int indexOfSlice(Seq<B> that, int from) {
                    return SeqOps.indexOfSlice$(this, that, from);
                }

                public <B> int indexOfSlice(Seq<B> that) {
                    return SeqOps.indexOfSlice$(this, that);
                }

                public <B> int lastIndexOfSlice(Seq<B> that, int end) {
                    return SeqOps.lastIndexOfSlice$(this, that, end);
                }

                public <B> int lastIndexOfSlice(Seq<B> that) {
                    return SeqOps.lastIndexOfSlice$(this, that);
                }

                public Option<Object> findLast(Function1<Object, Object> p) {
                    return SeqOps.findLast$(this, p);
                }

                public <B> boolean containsSlice(Seq<B> that) {
                    return SeqOps.containsSlice$(this, that);
                }

                public <A1> boolean contains(A1 elem) {
                    return SeqOps.contains$(this, elem);
                }

                public Object reverseMap(Function1 f) {
                    return SeqOps.reverseMap$(this, f);
                }

                public Iterator<String> permutations() {
                    return SeqOps.permutations$(this);
                }

                public Iterator<String> combinations(int n) {
                    return SeqOps.combinations$(this, n);
                }

                public Object sorted(Ordering ord) {
                    return SeqOps.sorted$(this, ord);
                }

                public Object sortWith(Function2 lt) {
                    return SeqOps.sortWith$(this, lt);
                }

                public Object sortBy(Function1 f, Ordering ord) {
                    return SeqOps.sortBy$(this, f, ord);
                }

                public Range indices() {
                    return SeqOps.indices$(this);
                }

                public final int sizeCompare(int otherSize) {
                    return SeqOps.sizeCompare$((SeqOps)this, otherSize);
                }

                public int lengthCompare(int len) {
                    return SeqOps.lengthCompare$((SeqOps)this, len);
                }

                public final int sizeCompare(Iterable<?> that) {
                    return SeqOps.sizeCompare$((SeqOps)this, that);
                }

                public int lengthCompare(Iterable<?> that) {
                    return SeqOps.lengthCompare$((SeqOps)this, that);
                }

                public final IterableOps lengthIs() {
                    return SeqOps.lengthIs$(this);
                }

                public boolean isEmpty() {
                    return SeqOps.isEmpty$(this);
                }

                public <B> boolean sameElements(IterableOnce<B> that) {
                    return SeqOps.sameElements$(this, that);
                }

                public <B$> boolean corresponds(Seq<B$> that, Function2<Object, B$, Object> p) {
                    return SeqOps.corresponds$(this, that, p);
                }

                public Object diff(Seq that) {
                    return SeqOps.diff$(this, that);
                }

                public Object intersect(Seq that) {
                    return SeqOps.intersect$(this, that);
                }

                public Object patch(int from, IterableOnce other, int replaced) {
                    return SeqOps.patch$(this, from, other, replaced);
                }

                public Object updated(int index, Object elem) {
                    return SeqOps.updated$(this, index, elem);
                }

                public <B> Map<B, Object> occCounts(Seq<B> sq) {
                    return SeqOps.occCounts$(this, sq);
                }

                public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
                    return SeqOps.search$(this, elem, ord);
                }

                public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
                    return SeqOps.search$(this, elem, from, to, ord);
                }

                public final Iterable<Object> toTraversable() {
                    return IterableOps.toTraversable$(this);
                }

                public boolean isTraversableAgain() {
                    return IterableOps.isTraversableAgain$(this);
                }

                public final Object repr() {
                    return IterableOps.repr$(this);
                }

                public IterableFactory<ArraySeq> companion() {
                    return IterableOps.companion$(this);
                }

                public Object head() {
                    return IterableOps.head$(this);
                }

                public Option<Object> headOption() {
                    return IterableOps.headOption$(this);
                }

                public Object last() {
                    return IterableOps.last$(this);
                }

                public Option<Object> lastOption() {
                    return IterableOps.lastOption$(this);
                }

                public final IterableOps sizeIs() {
                    return IterableOps.sizeIs$(this);
                }

                public View<Object> view(int from, int until) {
                    return IterableOps.view$(this, from, until);
                }

                public Object transpose(Function1 asIterable) {
                    return IterableOps.transpose$(this, asIterable);
                }

                public Object filter(Function1 pred) {
                    return IterableOps.filter$(this, pred);
                }

                public Object filterNot(Function1 pred) {
                    return IterableOps.filterNot$(this, pred);
                }

                public WithFilter<Object, ArraySeq> withFilter(Function1<Object, Object> p) {
                    return IterableOps.withFilter$(this, p);
                }

                public Tuple2<String, String> partition(Function1<Object, Object> p) {
                    return IterableOps.partition$(this, p);
                }

                public Tuple2<String, String> splitAt(int n) {
                    return IterableOps.splitAt$(this, n);
                }

                public Object take(int n) {
                    return IterableOps.take$(this, n);
                }

                public Object takeRight(int n) {
                    return IterableOps.takeRight$(this, n);
                }

                public Object takeWhile(Function1 p) {
                    return IterableOps.takeWhile$(this, p);
                }

                public Tuple2<String, String> span(Function1<Object, Object> p) {
                    return IterableOps.span$(this, p);
                }

                public Object drop(int n) {
                    return IterableOps.drop$(this, n);
                }

                public Object dropRight(int n) {
                    return IterableOps.dropRight$(this, n);
                }

                public Object dropWhile(Function1 p) {
                    return IterableOps.dropWhile$(this, p);
                }

                public Iterator<String> grouped(int size) {
                    return IterableOps.grouped$(this, size);
                }

                public Iterator<String> sliding(int size) {
                    return IterableOps.sliding$(this, size);
                }

                public Iterator<String> sliding(int size, int step) {
                    return IterableOps.sliding$(this, size, step);
                }

                public Object tail() {
                    return IterableOps.tail$(this);
                }

                public Object init() {
                    return IterableOps.init$(this);
                }

                public Object slice(int from, int until) {
                    return IterableOps.slice$(this, from, until);
                }

                public <K$> scala.collection.immutable.Map<K$, String> groupBy(Function1<Object, K$> f) {
                    return IterableOps.groupBy$(this, f);
                }

                public <K$, B> scala.collection.immutable.Map<K$, ArraySeq<B>> groupMap(Function1<Object, K$> key, Function1<Object, B> f) {
                    return IterableOps.groupMap$(this, key, f);
                }

                public <K$, B> scala.collection.immutable.Map<K$, B> groupMapReduce(Function1<Object, K$> key, Function1<Object, B> f, Function2<B, B, B> reduce) {
                    return IterableOps.groupMapReduce$(this, key, f, reduce);
                }

                public Object scan(Object z, Function2 op) {
                    return IterableOps.scan$(this, z, op);
                }

                public Object scanLeft(Object z, Function2 op) {
                    return IterableOps.scanLeft$(this, z, op);
                }

                public Object scanRight(Object z, Function2 op) {
                    return IterableOps.scanRight$(this, z, op);
                }

                public Object map(Function1 f) {
                    return IterableOps.map$(this, f);
                }

                public Object flatMap(Function1 f) {
                    return IterableOps.flatMap$(this, f);
                }

                public Object flatten(Function1 asIterable) {
                    return IterableOps.flatten$(this, asIterable);
                }

                public Object collect(PartialFunction pf) {
                    return IterableOps.collect$(this, pf);
                }

                public <A1, A2> Tuple2<ArraySeq<A1>, ArraySeq<A2>> partitionMap(Function1<Object, Either<A1, A2>> f) {
                    return IterableOps.partitionMap$(this, f);
                }

                public final Object $plus$plus(IterableOnce suffix) {
                    return IterableOps.$plus$plus$(this, suffix);
                }

                public Object zip(IterableOnce that) {
                    return IterableOps.zip$(this, that);
                }

                public Object zipWithIndex() {
                    return IterableOps.zipWithIndex$(this);
                }

                public Object zipAll(Iterable that, Object thisElem, Object thatElem) {
                    return IterableOps.zipAll$(this, that, thisElem, thatElem);
                }

                public <A1, A2> Tuple2<ArraySeq<A1>, ArraySeq<A2>> unzip(Function1<Object, Tuple2<A1, A2>> asPair) {
                    return IterableOps.unzip$(this, asPair);
                }

                public <A1, A2, A3> Tuple3<ArraySeq<A1>, ArraySeq<A2>, ArraySeq<A3>> unzip3(Function1<Object, Tuple3<A1, A2, A3>> asTriple) {
                    return IterableOps.unzip3$(this, asTriple);
                }

                public Iterator<String> tails() {
                    return IterableOps.tails$(this);
                }

                public Iterator<String> inits() {
                    return IterableOps.inits$(this);
                }

                public Object tapEach(Function1 f) {
                    return IterableOps.tapEach$(this, f);
                }

                public boolean hasDefiniteSize() {
                    return IterableOnceOps.hasDefiniteSize$(this);
                }

                public <U> void foreach(Function1<Object, U> f) {
                    IterableOnceOps.foreach$(this, f);
                }

                public boolean forall(Function1<Object, Object> p) {
                    return IterableOnceOps.forall$(this, p);
                }

                public boolean exists(Function1<Object, Object> p) {
                    return IterableOnceOps.exists$(this, p);
                }

                public int count(Function1<Object, Object> p) {
                    return IterableOnceOps.count$(this, p);
                }

                public Option<Object> find(Function1<Object, Object> p) {
                    return IterableOnceOps.find$(this, p);
                }

                public <B> B foldLeft(B z, Function2<B, Object, B> op) {
                    return (B)IterableOnceOps.foldLeft$(this, z, op);
                }

                public <B> B foldRight(B z, Function2<Object, B, B> op) {
                    return (B)IterableOnceOps.foldRight$(this, z, op);
                }

                public final <B> B $div$colon(B z, Function2<B, Object, B> op) {
                    return (B)IterableOnceOps.$div$colon$(this, z, op);
                }

                public final <B> B $colon$bslash(B z, Function2<Object, B, B> op) {
                    return (B)IterableOnceOps.$colon$bslash$(this, z, op);
                }

                public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
                    return (A1)IterableOnceOps.fold$(this, z, op);
                }

                public <B> B reduce(Function2<B, B, B> op) {
                    return (B)IterableOnceOps.reduce$(this, op);
                }

                public <B> Option<B> reduceOption(Function2<B, B, B> op) {
                    return IterableOnceOps.reduceOption$(this, op);
                }

                public <B> B reduceLeft(Function2<B, Object, B> op) {
                    return (B)IterableOnceOps.reduceLeft$(this, op);
                }

                public <B> B reduceRight(Function2<Object, B, B> op) {
                    return (B)IterableOnceOps.reduceRight$(this, op);
                }

                public <B> Option<B> reduceLeftOption(Function2<B, Object, B> op) {
                    return IterableOnceOps.reduceLeftOption$(this, op);
                }

                public <B> Option<B> reduceRightOption(Function2<Object, B, B> op) {
                    return IterableOnceOps.reduceRightOption$(this, op);
                }

                public boolean nonEmpty() {
                    return IterableOnceOps.nonEmpty$(this);
                }

                public final <B> void copyToBuffer(Buffer<B> dest) {
                    IterableOnceOps.copyToBuffer$(this, dest);
                }

                public <B> int copyToArray(Object xs) {
                    return IterableOnceOps.copyToArray$(this, xs);
                }

                public <B> int copyToArray(Object xs, int start2) {
                    return IterableOnceOps.copyToArray$(this, xs, start2);
                }

                public <B> int copyToArray(Object xs, int start2, int len) {
                    return IterableOnceOps.copyToArray$(this, xs, start2, len);
                }

                public <B> B sum(Numeric<B> num) {
                    return (B)IterableOnceOps.sum$(this, num);
                }

                public <B> B product(Numeric<B> num) {
                    return (B)IterableOnceOps.product$(this, num);
                }

                public Object min(Ordering ord) {
                    return IterableOnceOps.min$(this, ord);
                }

                public <B> Option<Object> minOption(Ordering<B> ord) {
                    return IterableOnceOps.minOption$(this, ord);
                }

                public Object max(Ordering ord) {
                    return IterableOnceOps.max$(this, ord);
                }

                public <B> Option<Object> maxOption(Ordering<B> ord) {
                    return IterableOnceOps.maxOption$(this, ord);
                }

                public Object maxBy(Function1 f, Ordering cmp) {
                    return IterableOnceOps.maxBy$(this, f, cmp);
                }

                public <B> Option<Object> maxByOption(Function1<Object, B> f, Ordering<B> cmp) {
                    return IterableOnceOps.maxByOption$(this, f, cmp);
                }

                public Object minBy(Function1 f, Ordering cmp) {
                    return IterableOnceOps.minBy$(this, f, cmp);
                }

                public <B> Option<Object> minByOption(Function1<Object, B> f, Ordering<B> cmp) {
                    return IterableOnceOps.minByOption$(this, f, cmp);
                }

                public <B> Option<B> collectFirst(PartialFunction<Object, B> pf) {
                    return IterableOnceOps.collectFirst$(this, pf);
                }

                public <B> B aggregate(Function0<B> z, Function2<B, Object, B> seqop, Function2<B, B, B> combop) {
                    return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
                }

                public <B> boolean corresponds(IterableOnce<B> that, Function2<Object, B, Object> p) {
                    return IterableOnceOps.corresponds$(this, that, p);
                }

                public final String mkString(String start2, String sep, String end) {
                    return IterableOnceOps.mkString$(this, start2, sep, end);
                }

                public final String mkString(String sep) {
                    return IterableOnceOps.mkString$(this, sep);
                }

                public final String mkString() {
                    return IterableOnceOps.mkString$(this);
                }

                public StringBuilder addString(StringBuilder b, String start2, String sep, String end) {
                    return IterableOnceOps.addString$(this, b, start2, sep, end);
                }

                public final StringBuilder addString(StringBuilder b, String sep) {
                    return IterableOnceOps.addString$(this, b, sep);
                }

                public final StringBuilder addString(StringBuilder b) {
                    return IterableOnceOps.addString$(this, b);
                }

                public <C1> C1 to(Factory<Object, C1> factory2) {
                    return (C1)IterableOnceOps.to$(this, factory2);
                }

                public final Iterator<Object> toIterator() {
                    return IterableOnceOps.toIterator$(this);
                }

                public List<Object> toList() {
                    return IterableOnceOps.toList$(this);
                }

                public Vector<Object> toVector() {
                    return IterableOnceOps.toVector$(this);
                }

                public <K$, V$> scala.collection.immutable.Map<K$, V$> toMap($less$colon$less<Object, Tuple2<K$, V$>> ev) {
                    return IterableOnceOps.toMap$(this, ev);
                }

                public <B> Set<B> toSet() {
                    return IterableOnceOps.toSet$(this);
                }

                public scala.collection.immutable.Seq<Object> toSeq() {
                    return IterableOnceOps.toSeq$(this);
                }

                public IndexedSeq<Object> toIndexedSeq() {
                    return IterableOnceOps.toIndexedSeq$(this);
                }

                public final Stream<Object> toStream() {
                    return IterableOnceOps.toStream$(this);
                }

                public final <B> Buffer<B> toBuffer() {
                    return IterableOnceOps.toBuffer$(this);
                }

                public <B> Object toArray(ClassTag<B> evidence$2) {
                    return IterableOnceOps.toArray$(this, evidence$2);
                }

                public Iterable<Object> reversed() {
                    return IterableOnceOps.reversed$(this);
                }

                public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
                    return (S)IterableOnce.stepper$(this, shape);
                }

                public int knownSize() {
                    return IterableOnce.knownSize$(this);
                }

                public int length() {
                    return this.s$1.length();
                }

                public char apply(int i) {
                    return this.s$1.charAt(i);
                }

                public Iterable<Object> toIterable() {
                    return new WrappedString(this.s$1);
                }

                public String coll() {
                    return this.s$1;
                }

                public String fromSpecific(IterableOnce<Object> coll) {
                    Iterator<Object> iterator2 = coll.iterator();
                    if (iterator2 == null) {
                        throw null;
                    }
                    Iterator<Object> mkString_this = iterator2;
                    String mkString_mkString_sep = "";
                    return mkString_this.mkString("", mkString_mkString_sep, "");
                }

                public IterableFactory<ArraySeq> iterableFactory() {
                    return scala.collection.immutable.ArraySeq$.MODULE$.untagged();
                }

                public String empty() {
                    return "";
                }

                public Builder<Object, String> newSpecificBuilder() {
                    return new StringBuilder();
                }

                public Iterator<Object> iterator() {
                    Object var1_1;
                    String augmentString_x = this.s$1;
                    void v0 = var1_1;
                    var1_1 = null;
                    void iterator$extension_$this = v0;
                    return new StringOps.StringIterator((String)iterator$extension_$this);
                }
                {
                    this.s$1 = s$1;
                }
            };
        }
        {
            IsIterableOnce.$init$(this);
            IsIterable.$init$(this);
            IsSeq.$init$(this);
            Statics.releaseFence();
        }
    };
    private static final IsSeq<StringView> stringViewIsSeq = new IsSeq<StringView>(){
        private Function1<StringView, SeqOps<Object, Iterable, View<Object>>> conversion;

        public Function1<StringView, SeqOps<Object, Iterable, View<Object>>> conversion() {
            return this.conversion;
        }

        public void scala$collection$generic$IsSeq$_setter_$conversion_$eq(Function1<StringView, SeqOps<Object, Iterable, View<Object>>> x$1) {
            this.conversion = x$1;
        }

        public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<StringView, IterableOps<Object, Iterable, View<Object>>> x$1) {
        }

        public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<StringView, IterableOnce<Object>> x$1) {
        }

        public SeqOps<Object, View, View<Object>> apply(StringView coll) {
            return coll;
        }
        {
            IsIterableOnce.$init$(this);
            IsIterable.$init$(this);
            IsSeq.$init$(this);
            Statics.releaseFence();
        }
    };

    private IsSeq<Seq<Object>> seqOpsIsSeqVal() {
        return seqOpsIsSeqVal;
    }

    public <CC0 extends SeqOps<Object, Iterable, CC0>, A0> IsSeq<CC0> seqOpsIsSeq() {
        return this.seqOpsIsSeqVal();
    }

    public <CC0 extends SeqView<Object>, A0> IsSeq<CC0> seqViewIsSeq() {
        return new IsSeq<CC0>(){
            private Function1<CC0, SeqOps<A0, Iterable, View<A0>>> conversion;

            public Function1<CC0, SeqOps<A0, Iterable, View<A0>>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsSeq$_setter_$conversion_$eq(Function1<CC0, SeqOps<A0, Iterable, View<A0>>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<CC0, IterableOps<A0, Iterable, View<A0>>> x$1) {
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<CC0, IterableOnce<A0>> x$1) {
            }

            public SeqOps<A0, View, View<A0>> apply(CC0 coll) {
                return coll;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                IsSeq.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public IsSeq<String> stringIsSeq() {
        return stringIsSeq;
    }

    public IsSeq<StringView> stringViewIsSeq() {
        return stringViewIsSeq;
    }

    public <A0> IsSeq<Object> arrayIsSeq(ClassTag<A0> evidence$1) {
        return new IsSeq<Object>(evidence$1){
            private Function1<Object, SeqOps<A0, Iterable, Object>> conversion;
            public final ClassTag evidence$1$1;

            public Function1<Object, SeqOps<A0, Iterable, Object>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsSeq$_setter_$conversion_$eq(Function1<Object, SeqOps<A0, Iterable, Object>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<Object, IterableOps<A0, Iterable, Object>> x$1) {
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<Object, IterableOnce<A0>> x$1) {
            }

            public SeqOps<A0, Seq, Object> apply(Object a) {
                return new SeqOps<A0, scala.collection.mutable.ArraySeq, Object>(this, a){
                    private final /* synthetic */ anon.6 $outer;
                    private final Object a$1;

                    public /* synthetic */ Object scala$collection$SeqOps$$super$concat(IterableOnce suffix) {
                        return IterableOps.concat$(this, suffix);
                    }

                    public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(int otherSize) {
                        return IterableOps.sizeCompare$((IterableOps)this, otherSize);
                    }

                    public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(Iterable that) {
                        return IterableOps.sizeCompare$((IterableOps)this, that);
                    }

                    public SeqView<A0> view() {
                        return SeqOps.view$(this);
                    }

                    public Object prepended(Object elem) {
                        return SeqOps.prepended$(this, elem);
                    }

                    public final Object $plus$colon(Object elem) {
                        return SeqOps.$plus$colon$(this, elem);
                    }

                    public Object appended(Object elem) {
                        return SeqOps.appended$(this, elem);
                    }

                    public final Object $colon$plus(Object elem) {
                        return SeqOps.$colon$plus$(this, elem);
                    }

                    public Object prependedAll(IterableOnce prefix) {
                        return SeqOps.prependedAll$(this, prefix);
                    }

                    public final Object $plus$plus$colon(IterableOnce prefix) {
                        return SeqOps.$plus$plus$colon$(this, prefix);
                    }

                    public Object appendedAll(IterableOnce suffix) {
                        return SeqOps.appendedAll$(this, suffix);
                    }

                    public final Object $colon$plus$plus(IterableOnce suffix) {
                        return SeqOps.$colon$plus$plus$(this, suffix);
                    }

                    public final Object concat(IterableOnce suffix) {
                        return SeqOps.concat$(this, suffix);
                    }

                    public final Object union(Seq that) {
                        return SeqOps.union$(this, that);
                    }

                    public final int size() {
                        return SeqOps.size$(this);
                    }

                    public Object distinct() {
                        return SeqOps.distinct$(this);
                    }

                    public <B> Object distinctBy(Function1<A0, B> f) {
                        return SeqOps.distinctBy$(this, f);
                    }

                    public Object reverse() {
                        return SeqOps.reverse$(this);
                    }

                    public Iterator<A0> reverseIterator() {
                        return SeqOps.reverseIterator$(this);
                    }

                    public <B> boolean startsWith(IterableOnce<B> that, int offset) {
                        return SeqOps.startsWith$(this, that, offset);
                    }

                    public <B> int startsWith$default$2() {
                        return SeqOps.startsWith$default$2$(this);
                    }

                    public <B> boolean endsWith(Iterable<B> that) {
                        return SeqOps.endsWith$(this, that);
                    }

                    public boolean isDefinedAt(int idx) {
                        return SeqOps.isDefinedAt$(this, idx);
                    }

                    public Object padTo(int len, Object elem) {
                        return SeqOps.padTo$(this, len, elem);
                    }

                    public final int segmentLength(Function1<A0, Object> p) {
                        return SeqOps.segmentLength$(this, p);
                    }

                    public int segmentLength(Function1<A0, Object> p, int from) {
                        return SeqOps.segmentLength$(this, p, from);
                    }

                    public final int prefixLength(Function1<A0, Object> p) {
                        return SeqOps.prefixLength$(this, p);
                    }

                    public int indexWhere(Function1<A0, Object> p, int from) {
                        return SeqOps.indexWhere$(this, p, from);
                    }

                    public int indexWhere(Function1<A0, Object> p) {
                        return SeqOps.indexWhere$(this, p);
                    }

                    public <B> int indexOf(B elem, int from) {
                        return SeqOps.indexOf$(this, elem, from);
                    }

                    public <B> int indexOf(B elem) {
                        return SeqOps.indexOf$(this, elem);
                    }

                    public <B> int lastIndexOf(B elem, int end) {
                        return SeqOps.lastIndexOf$(this, elem, end);
                    }

                    public <B> int lastIndexOf$default$2() {
                        return SeqOps.lastIndexOf$default$2$(this);
                    }

                    public int lastIndexWhere(Function1<A0, Object> p, int end) {
                        return SeqOps.lastIndexWhere$(this, p, end);
                    }

                    public int lastIndexWhere(Function1<A0, Object> p) {
                        return SeqOps.lastIndexWhere$(this, p);
                    }

                    public <B> int indexOfSlice(Seq<B> that, int from) {
                        return SeqOps.indexOfSlice$(this, that, from);
                    }

                    public <B> int indexOfSlice(Seq<B> that) {
                        return SeqOps.indexOfSlice$(this, that);
                    }

                    public <B> int lastIndexOfSlice(Seq<B> that, int end) {
                        return SeqOps.lastIndexOfSlice$(this, that, end);
                    }

                    public <B> int lastIndexOfSlice(Seq<B> that) {
                        return SeqOps.lastIndexOfSlice$(this, that);
                    }

                    public Option<A0> findLast(Function1<A0, Object> p) {
                        return SeqOps.findLast$(this, p);
                    }

                    public <B> boolean containsSlice(Seq<B> that) {
                        return SeqOps.containsSlice$(this, that);
                    }

                    public <A1> boolean contains(A1 elem) {
                        return SeqOps.contains$(this, elem);
                    }

                    public Object reverseMap(Function1 f) {
                        return SeqOps.reverseMap$(this, f);
                    }

                    public Iterator<Object> permutations() {
                        return SeqOps.permutations$(this);
                    }

                    public Iterator<Object> combinations(int n) {
                        return SeqOps.combinations$(this, n);
                    }

                    public <B> Object sorted(Ordering<B> ord) {
                        return SeqOps.sorted$(this, ord);
                    }

                    public Object sortWith(Function2<A0, A0, Object> lt) {
                        return SeqOps.sortWith$(this, lt);
                    }

                    public <B> Object sortBy(Function1<A0, B> f, Ordering<B> ord) {
                        return SeqOps.sortBy$(this, f, ord);
                    }

                    public Range indices() {
                        return SeqOps.indices$(this);
                    }

                    public final int sizeCompare(int otherSize) {
                        return SeqOps.sizeCompare$((SeqOps)this, otherSize);
                    }

                    public int lengthCompare(int len) {
                        return SeqOps.lengthCompare$((SeqOps)this, len);
                    }

                    public final int sizeCompare(Iterable<?> that) {
                        return SeqOps.sizeCompare$((SeqOps)this, that);
                    }

                    public int lengthCompare(Iterable<?> that) {
                        return SeqOps.lengthCompare$((SeqOps)this, that);
                    }

                    public final IterableOps lengthIs() {
                        return SeqOps.lengthIs$(this);
                    }

                    public boolean isEmpty() {
                        return SeqOps.isEmpty$(this);
                    }

                    public <B> boolean sameElements(IterableOnce<B> that) {
                        return SeqOps.sameElements$(this, that);
                    }

                    public <B$> boolean corresponds(Seq<B$> that, Function2<A0, B$, Object> p) {
                        return SeqOps.corresponds$(this, that, p);
                    }

                    public <B> Object diff(Seq<B> that) {
                        return SeqOps.diff$(this, that);
                    }

                    public <B> Object intersect(Seq<B> that) {
                        return SeqOps.intersect$(this, that);
                    }

                    public Object patch(int from, IterableOnce other, int replaced) {
                        return SeqOps.patch$(this, from, other, replaced);
                    }

                    public Object updated(int index, Object elem) {
                        return SeqOps.updated$(this, index, elem);
                    }

                    public <B> Map<B, Object> occCounts(Seq<B> sq) {
                        return SeqOps.occCounts$(this, sq);
                    }

                    public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
                        return SeqOps.search$(this, elem, ord);
                    }

                    public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
                        return SeqOps.search$(this, elem, from, to, ord);
                    }

                    public final Iterable<A0> toTraversable() {
                        return IterableOps.toTraversable$(this);
                    }

                    public boolean isTraversableAgain() {
                        return IterableOps.isTraversableAgain$(this);
                    }

                    public final Object repr() {
                        return IterableOps.repr$(this);
                    }

                    public IterableFactory<scala.collection.mutable.ArraySeq> companion() {
                        return IterableOps.companion$(this);
                    }

                    public A0 head() {
                        return (A0)IterableOps.head$(this);
                    }

                    public Option<A0> headOption() {
                        return IterableOps.headOption$(this);
                    }

                    public A0 last() {
                        return (A0)IterableOps.last$(this);
                    }

                    public Option<A0> lastOption() {
                        return IterableOps.lastOption$(this);
                    }

                    public final IterableOps sizeIs() {
                        return IterableOps.sizeIs$(this);
                    }

                    public View<A0> view(int from, int until) {
                        return IterableOps.view$(this, from, until);
                    }

                    public Object transpose(Function1 asIterable) {
                        return IterableOps.transpose$(this, asIterable);
                    }

                    public Object filter(Function1<A0, Object> pred) {
                        return IterableOps.filter$(this, pred);
                    }

                    public Object filterNot(Function1<A0, Object> pred) {
                        return IterableOps.filterNot$(this, pred);
                    }

                    public WithFilter<A0, scala.collection.mutable.ArraySeq> withFilter(Function1<A0, Object> p) {
                        return IterableOps.withFilter$(this, p);
                    }

                    public Tuple2<Object, Object> partition(Function1<A0, Object> p) {
                        return IterableOps.partition$(this, p);
                    }

                    public Tuple2<Object, Object> splitAt(int n) {
                        return IterableOps.splitAt$(this, n);
                    }

                    public Object take(int n) {
                        return IterableOps.take$(this, n);
                    }

                    public Object takeRight(int n) {
                        return IterableOps.takeRight$(this, n);
                    }

                    public Object takeWhile(Function1<A0, Object> p) {
                        return IterableOps.takeWhile$(this, p);
                    }

                    public Tuple2<Object, Object> span(Function1<A0, Object> p) {
                        return IterableOps.span$(this, p);
                    }

                    public Object drop(int n) {
                        return IterableOps.drop$(this, n);
                    }

                    public Object dropRight(int n) {
                        return IterableOps.dropRight$(this, n);
                    }

                    public Object dropWhile(Function1<A0, Object> p) {
                        return IterableOps.dropWhile$(this, p);
                    }

                    public Iterator<Object> grouped(int size) {
                        return IterableOps.grouped$(this, size);
                    }

                    public Iterator<Object> sliding(int size) {
                        return IterableOps.sliding$(this, size);
                    }

                    public Iterator<Object> sliding(int size, int step) {
                        return IterableOps.sliding$(this, size, step);
                    }

                    public Object tail() {
                        return IterableOps.tail$(this);
                    }

                    public Object init() {
                        return IterableOps.init$(this);
                    }

                    public Object slice(int from, int until) {
                        return IterableOps.slice$(this, from, until);
                    }

                    public <K$> scala.collection.immutable.Map<K$, Object> groupBy(Function1<A0, K$> f) {
                        return IterableOps.groupBy$(this, f);
                    }

                    public <K$, B> scala.collection.immutable.Map<K$, scala.collection.mutable.ArraySeq<B>> groupMap(Function1<A0, K$> key, Function1<A0, B> f) {
                        return IterableOps.groupMap$(this, key, f);
                    }

                    public <K$, B> scala.collection.immutable.Map<K$, B> groupMapReduce(Function1<A0, K$> key, Function1<A0, B> f, Function2<B, B, B> reduce) {
                        return IterableOps.groupMapReduce$(this, key, f, reduce);
                    }

                    public Object scan(Object z, Function2 op) {
                        return IterableOps.scan$(this, z, op);
                    }

                    public Object scanLeft(Object z, Function2 op) {
                        return IterableOps.scanLeft$(this, z, op);
                    }

                    public Object scanRight(Object z, Function2 op) {
                        return IterableOps.scanRight$(this, z, op);
                    }

                    public Object map(Function1 f) {
                        return IterableOps.map$(this, f);
                    }

                    public Object flatMap(Function1 f) {
                        return IterableOps.flatMap$(this, f);
                    }

                    public Object flatten(Function1 asIterable) {
                        return IterableOps.flatten$(this, asIterable);
                    }

                    public Object collect(PartialFunction pf) {
                        return IterableOps.collect$(this, pf);
                    }

                    public <A1, A2> Tuple2<scala.collection.mutable.ArraySeq<A1>, scala.collection.mutable.ArraySeq<A2>> partitionMap(Function1<A0, Either<A1, A2>> f) {
                        return IterableOps.partitionMap$(this, f);
                    }

                    public final Object $plus$plus(IterableOnce suffix) {
                        return IterableOps.$plus$plus$(this, suffix);
                    }

                    public Object zip(IterableOnce that) {
                        return IterableOps.zip$(this, that);
                    }

                    public Object zipWithIndex() {
                        return IterableOps.zipWithIndex$(this);
                    }

                    public Object zipAll(Iterable that, Object thisElem, Object thatElem) {
                        return IterableOps.zipAll$(this, that, thisElem, thatElem);
                    }

                    public <A1, A2> Tuple2<scala.collection.mutable.ArraySeq<A1>, scala.collection.mutable.ArraySeq<A2>> unzip(Function1<A0, Tuple2<A1, A2>> asPair) {
                        return IterableOps.unzip$(this, asPair);
                    }

                    public <A1, A2, A3> Tuple3<scala.collection.mutable.ArraySeq<A1>, scala.collection.mutable.ArraySeq<A2>, scala.collection.mutable.ArraySeq<A3>> unzip3(Function1<A0, Tuple3<A1, A2, A3>> asTriple) {
                        return IterableOps.unzip3$(this, asTriple);
                    }

                    public Iterator<Object> tails() {
                        return IterableOps.tails$(this);
                    }

                    public Iterator<Object> inits() {
                        return IterableOps.inits$(this);
                    }

                    public <U> Object tapEach(Function1<A0, U> f) {
                        return IterableOps.tapEach$(this, f);
                    }

                    public boolean hasDefiniteSize() {
                        return IterableOnceOps.hasDefiniteSize$(this);
                    }

                    public <U> void foreach(Function1<A0, U> f) {
                        IterableOnceOps.foreach$(this, f);
                    }

                    public boolean forall(Function1<A0, Object> p) {
                        return IterableOnceOps.forall$(this, p);
                    }

                    public boolean exists(Function1<A0, Object> p) {
                        return IterableOnceOps.exists$(this, p);
                    }

                    public int count(Function1<A0, Object> p) {
                        return IterableOnceOps.count$(this, p);
                    }

                    public Option<A0> find(Function1<A0, Object> p) {
                        return IterableOnceOps.find$(this, p);
                    }

                    public <B> B foldLeft(B z, Function2<B, A0, B> op) {
                        return (B)IterableOnceOps.foldLeft$(this, z, op);
                    }

                    public <B> B foldRight(B z, Function2<A0, B, B> op) {
                        return (B)IterableOnceOps.foldRight$(this, z, op);
                    }

                    public final <B> B $div$colon(B z, Function2<B, A0, B> op) {
                        return (B)IterableOnceOps.$div$colon$(this, z, op);
                    }

                    public final <B> B $colon$bslash(B z, Function2<A0, B, B> op) {
                        return (B)IterableOnceOps.$colon$bslash$(this, z, op);
                    }

                    public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
                        return (A1)IterableOnceOps.fold$(this, z, op);
                    }

                    public <B> B reduce(Function2<B, B, B> op) {
                        return (B)IterableOnceOps.reduce$(this, op);
                    }

                    public <B> Option<B> reduceOption(Function2<B, B, B> op) {
                        return IterableOnceOps.reduceOption$(this, op);
                    }

                    public <B> B reduceLeft(Function2<B, A0, B> op) {
                        return (B)IterableOnceOps.reduceLeft$(this, op);
                    }

                    public <B> B reduceRight(Function2<A0, B, B> op) {
                        return (B)IterableOnceOps.reduceRight$(this, op);
                    }

                    public <B> Option<B> reduceLeftOption(Function2<B, A0, B> op) {
                        return IterableOnceOps.reduceLeftOption$(this, op);
                    }

                    public <B> Option<B> reduceRightOption(Function2<A0, B, B> op) {
                        return IterableOnceOps.reduceRightOption$(this, op);
                    }

                    public boolean nonEmpty() {
                        return IterableOnceOps.nonEmpty$(this);
                    }

                    public final <B> void copyToBuffer(Buffer<B> dest) {
                        IterableOnceOps.copyToBuffer$(this, dest);
                    }

                    public <B> int copyToArray(Object xs) {
                        return IterableOnceOps.copyToArray$(this, xs);
                    }

                    public <B> int copyToArray(Object xs, int start2) {
                        return IterableOnceOps.copyToArray$(this, xs, start2);
                    }

                    public <B> int copyToArray(Object xs, int start2, int len) {
                        return IterableOnceOps.copyToArray$(this, xs, start2, len);
                    }

                    public <B> B sum(Numeric<B> num) {
                        return (B)IterableOnceOps.sum$(this, num);
                    }

                    public <B> B product(Numeric<B> num) {
                        return (B)IterableOnceOps.product$(this, num);
                    }

                    public <B> A0 min(Ordering<B> ord) {
                        return (A0)IterableOnceOps.min$(this, ord);
                    }

                    public <B> Option<A0> minOption(Ordering<B> ord) {
                        return IterableOnceOps.minOption$(this, ord);
                    }

                    public <B> A0 max(Ordering<B> ord) {
                        return (A0)IterableOnceOps.max$(this, ord);
                    }

                    public <B> Option<A0> maxOption(Ordering<B> ord) {
                        return IterableOnceOps.maxOption$(this, ord);
                    }

                    public <B> A0 maxBy(Function1<A0, B> f, Ordering<B> cmp) {
                        return (A0)IterableOnceOps.maxBy$(this, f, cmp);
                    }

                    public <B> Option<A0> maxByOption(Function1<A0, B> f, Ordering<B> cmp) {
                        return IterableOnceOps.maxByOption$(this, f, cmp);
                    }

                    public <B> A0 minBy(Function1<A0, B> f, Ordering<B> cmp) {
                        return (A0)IterableOnceOps.minBy$(this, f, cmp);
                    }

                    public <B> Option<A0> minByOption(Function1<A0, B> f, Ordering<B> cmp) {
                        return IterableOnceOps.minByOption$(this, f, cmp);
                    }

                    public <B> Option<B> collectFirst(PartialFunction<A0, B> pf) {
                        return IterableOnceOps.collectFirst$(this, pf);
                    }

                    public <B> B aggregate(Function0<B> z, Function2<B, A0, B> seqop, Function2<B, B, B> combop) {
                        return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
                    }

                    public <B> boolean corresponds(IterableOnce<B> that, Function2<A0, B, Object> p) {
                        return IterableOnceOps.corresponds$(this, that, p);
                    }

                    public final String mkString(String start2, String sep, String end) {
                        return IterableOnceOps.mkString$(this, start2, sep, end);
                    }

                    public final String mkString(String sep) {
                        return IterableOnceOps.mkString$(this, sep);
                    }

                    public final String mkString() {
                        return IterableOnceOps.mkString$(this);
                    }

                    public StringBuilder addString(StringBuilder b, String start2, String sep, String end) {
                        return IterableOnceOps.addString$(this, b, start2, sep, end);
                    }

                    public final StringBuilder addString(StringBuilder b, String sep) {
                        return IterableOnceOps.addString$(this, b, sep);
                    }

                    public final StringBuilder addString(StringBuilder b) {
                        return IterableOnceOps.addString$(this, b);
                    }

                    public <C1> C1 to(Factory<A0, C1> factory2) {
                        return (C1)IterableOnceOps.to$(this, factory2);
                    }

                    public final Iterator<A0> toIterator() {
                        return IterableOnceOps.toIterator$(this);
                    }

                    public List<A0> toList() {
                        return IterableOnceOps.toList$(this);
                    }

                    public Vector<A0> toVector() {
                        return IterableOnceOps.toVector$(this);
                    }

                    public <K$, V$> scala.collection.immutable.Map<K$, V$> toMap($less$colon$less<A0, Tuple2<K$, V$>> ev) {
                        return IterableOnceOps.toMap$(this, ev);
                    }

                    public <B> Set<B> toSet() {
                        return IterableOnceOps.toSet$(this);
                    }

                    public scala.collection.immutable.Seq<A0> toSeq() {
                        return IterableOnceOps.toSeq$(this);
                    }

                    public IndexedSeq<A0> toIndexedSeq() {
                        return IterableOnceOps.toIndexedSeq$(this);
                    }

                    public final Stream<A0> toStream() {
                        return IterableOnceOps.toStream$(this);
                    }

                    public final <B> Buffer<B> toBuffer() {
                        return IterableOnceOps.toBuffer$(this);
                    }

                    public <B> Object toArray(ClassTag<B> evidence$2) {
                        return IterableOnceOps.toArray$(this, evidence$2);
                    }

                    public Iterable<A0> reversed() {
                        return IterableOnceOps.reversed$(this);
                    }

                    public <S extends Stepper<?>> S stepper(StepperShape<A0, S> shape) {
                        return (S)IterableOnce.stepper$(this, shape);
                    }

                    public int knownSize() {
                        return IterableOnce.knownSize$(this);
                    }

                    public A0 apply(int i) {
                        return (A0)ScalaRunTime$.MODULE$.array_apply(this.a$1, i);
                    }

                    public int length() {
                        return Array.getLength(this.a$1);
                    }

                    public Iterable<A0> toIterable() {
                        return ArraySeq$.MODULE$.make(this.a$1);
                    }

                    public Object coll() {
                        return this.a$1;
                    }

                    public Object fromSpecific(IterableOnce<A0> coll) {
                        return Array$.MODULE$.from(coll, this.$outer.evidence$1$1);
                    }

                    public IterableFactory<scala.collection.mutable.ArraySeq> iterableFactory() {
                        return ArraySeq$.MODULE$.untagged();
                    }

                    public Object empty() {
                        return this.$outer.evidence$1$1.newArray(0);
                    }

                    public Builder<A0, Object> newSpecificBuilder() {
                        ClassTag newBuilder_t = this.$outer.evidence$1$1;
                        Class<?> clazz = newBuilder_t.runtimeClass();
                        Class<Byte> clazz2 = Byte.TYPE;
                        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
                            return new ArrayBuilder.ofByte();
                        }
                        Class<Short> clazz3 = Short.TYPE;
                        if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                            return new ArrayBuilder.ofShort();
                        }
                        Class<Character> clazz4 = Character.TYPE;
                        if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                            return new ArrayBuilder.ofChar();
                        }
                        Class<Integer> clazz5 = Integer.TYPE;
                        if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                            return new ArrayBuilder.ofInt();
                        }
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            return new ArrayBuilder.ofLong();
                        }
                        Class<Float> clazz7 = Float.TYPE;
                        if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                            return new ArrayBuilder.ofFloat();
                        }
                        Class<Double> clazz8 = Double.TYPE;
                        if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                            return new ArrayBuilder.ofDouble();
                        }
                        Class<Boolean> clazz9 = Boolean.TYPE;
                        if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                            return new ArrayBuilder.ofBoolean();
                        }
                        Class<Void> clazz10 = Void.TYPE;
                        if (!(clazz10 != null ? !clazz10.equals(clazz) : clazz != null)) {
                            return new ArrayBuilder.ofUnit();
                        }
                        return new ArrayBuilder.ofRef<T>(newBuilder_t);
                    }

                    public Iterator<A0> iterator() {
                        return ArrayOps$.MODULE$.iterator$extension(this.a$1);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        this.a$1 = a$1;
                    }
                };
            }
            {
                this.evidence$1$1 = evidence$1$1;
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                IsSeq.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public <C0 extends Range> IsSeq<C0> rangeIsSeq() {
        return new IsSeq<C0>(){
            private Function1<C0, SeqOps<Object, Iterable, IndexedSeq<Object>>> conversion;

            public Function1<C0, SeqOps<Object, Iterable, IndexedSeq<Object>>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsSeq$_setter_$conversion_$eq(Function1<C0, SeqOps<Object, Iterable, IndexedSeq<Object>>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<C0, IterableOps<Object, Iterable, IndexedSeq<Object>>> x$1) {
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<C0, IterableOnce<Object>> x$1) {
            }

            public SeqOps<Object, Seq, IndexedSeq<Object>> apply(C0 coll) {
                return coll;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                IsSeq.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    private IsSeq$() {
    }
}

