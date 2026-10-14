/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterable;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.LazyZip2;
import scala.collection.LinearSeq;
import scala.collection.Searching;
import scala.collection.Seq;
import scala.collection.SeqFactory;
import scala.collection.SeqView;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.View;
import scala.collection.WithFilter;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Range;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005m<Q\u0001E\t\t\u0002b1QAG\t\t\u0002nAQAM\u0001\u0005\u0002MBQ\u0001N\u0001\u0005BUBQAN\u0001\u0005B]BQaO\u0001\u0005BUBQ\u0001P\u0001\u0005BUBQ!P\u0001\u0005BUBQAP\u0001\u0005B}BQaQ\u0001\u0005B\u0011CQ!S\u0001\u0005B)CaAY\u0001!\u0002\u0013\u0019\u0007bB5\u0002\u0003\u0003%\tE\u001b\u0005\bg\u0006\t\t\u0011\"\u0001@\u0011\u001d!\u0018!!A\u0005\u0002UDq\u0001_\u0001\u0002\u0002\u0013\u0005\u00130A\u0002OS2T!AE\n\u0002\u0013%lW.\u001e;bE2,'B\u0001\u000b\u0016\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002-\u0005)1oY1mC\u000e\u0001\u0001CA\r\u0002\u001b\u0005\t\"a\u0001(jYN!\u0011\u0001H\u0012'!\rIRdH\u0005\u0003=E\u0011A\u0001T5tiB\u0011\u0001%I\u0007\u0002+%\u0011!%\u0006\u0002\b\u001d>$\b.\u001b8h!\t\u0001C%\u0003\u0002&+\t9\u0001K]8ek\u000e$\bCA\u00140\u001d\tASF\u0004\u0002*Y5\t!F\u0003\u0002,/\u00051AH]8pizJ\u0011AF\u0005\u0003]U\tq\u0001]1dW\u0006<W-\u0003\u00021c\ta1+\u001a:jC2L'0\u00192mK*\u0011a&F\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003a\tA\u0001[3bIV\tq$\u0001\u0006iK\u0006$w\n\u001d;j_:,\u0012\u0001\u000f\b\u0003AeJ!AO\u000b\u0002\t9{g.Z\u0001\u0005i\u0006LG.\u0001\u0003mCN$\u0018\u0001B5oSR\f\u0011b\u001b8po:\u001c\u0016N_3\u0016\u0003\u0001\u0003\"\u0001I!\n\u0005\t+\"aA%oi\u0006A\u0011\u000e^3sCR|'/F\u0001F!\r1uiH\u0007\u0002'%\u0011\u0001j\u0005\u0002\t\u0013R,'/\u0019;pe\u0006)QO\u001c>jaV\u00191J\u0015.\u0015\u00051c\u0006\u0003\u0002\u0011N\u001fbK!AT\u000b\u0003\rQ+\b\u000f\\33!\rIR\u0004\u0015\t\u0003#Jc\u0001\u0001B\u0003T\u0015\t\u0007AK\u0001\u0002BcE\u0011q$\u0016\t\u0003AYK!aV\u000b\u0003\u0007\u0005s\u0017\u0010E\u0002\u001a;e\u0003\"!\u0015.\u0005\u000bmS!\u0019\u0001+\u0003\u0005\u0005\u0013\u0004\"B/\u000b\u0001\bq\u0016AB1t!\u0006L'\u000f\u0005\u0003!?~\t\u0017B\u00011\u0016\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0003!\u001bBK\u0016AC#naRLXK\u001c>jaB!\u0001%\u00143e\u001d\tI\u0002\u0001\u000b\u0002\fMB\u0011\u0001eZ\u0005\u0003QV\u0011\u0011\u0002\u001e:b]NLWM\u001c;\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005Y\u0007C\u00017r\u001b\u0005i'B\u00018p\u0003\u0011a\u0017M\\4\u000b\u0003A\fAA[1wC&\u0011!/\u001c\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011QK\u001e\u0005\bo:\t\t\u00111\u0001A\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\t!\u0010E\u0002G\u000fV\u0003")
public final class Nil {
    public static Iterator<Object> productIterator() {
        return Nil$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return Nil$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return Nil$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return Nil$.MODULE$.productPrefix();
    }

    public static <A1, A2> Tuple2<List<A1>, List<A2>> unzip(Function1<Nothing$, Tuple2<A1, A2>> asPair) {
        return Nil$.MODULE$.unzip(asPair);
    }

    public static Iterator<Nothing$> iterator() {
        return Nil$.MODULE$.iterator();
    }

    public static int knownSize() {
        return Nil$.MODULE$.knownSize();
    }

    public static Nothing$ init() {
        return Nil$.MODULE$.init();
    }

    public static Nothing$ last() {
        return Nil$.MODULE$.last();
    }

    public static Nothing$ tail() {
        return Nil$.MODULE$.tail();
    }

    public static None$ headOption() {
        return Nil$.MODULE$.headOption();
    }

    public static Nothing$ head() {
        return Nil$.MODULE$.head();
    }

    public static Iterator<String> productElementNames() {
        return Nil$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return Nil$.MODULE$.productElementName(n);
    }

    public static boolean equals(Object o) {
        return Nil$.MODULE$.equals(o);
    }

    public static List<Nothing$> toList() {
        return Nil$.MODULE$.toList();
    }

    public static Tuple2<List<Nothing$>, List<Nothing$>> partition(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.partition(p);
    }

    public static List<Nothing$> filterNot(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.filterNot(p);
    }

    public static List<Nothing$> filter(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.filter(p);
    }

    public static <B> List<B> mapConserve(Function1<Nothing$, B> f) {
        return Nil$.MODULE$.mapConserve(f);
    }

    public static <B> boolean corresponds(Seq<B> that, Function2<Nothing$, B, Object> p) {
        return Nil$.MODULE$.corresponds(that, p);
    }

    public static Option<Nothing$> find(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.find(p);
    }

    public static <A1> boolean contains(A1 elem) {
        return Nil$.MODULE$.contains(elem);
    }

    public static boolean exists(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.exists(p);
    }

    public static boolean forall(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.forall(p);
    }

    public static int lengthCompare(int len) {
        return Nil$.MODULE$.lengthCompare(len);
    }

    public static int length() {
        return Nil$.MODULE$.length();
    }

    public static <B> B foldRight(B z, Function2<Nothing$, B, B> op) {
        return Nil$.MODULE$.foldRight(z, op);
    }

    public static List<Nothing$> reverse() {
        return Nil$.MODULE$.reverse();
    }

    public static <U> void foreach(Function1<Nothing$, U> f) {
        Nil$.MODULE$.foreach(f);
    }

    public static Tuple2<List<Nothing$>, List<Nothing$>> span(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.span(p);
    }

    public static List<Nothing$> takeWhile(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.takeWhile(p);
    }

    public static <B> List<B> flatMap(Function1<Nothing$, IterableOnce<B>> f) {
        return Nil$.MODULE$.flatMap(f);
    }

    public static <B> List<B> collect(PartialFunction<Nothing$, B> pf) {
        return Nil$.MODULE$.collect((PartialFunction)pf);
    }

    public static <B> List<B> map(Function1<Nothing$, B> f) {
        return Nil$.MODULE$.map(f);
    }

    public static <B> List<B> updated(int index, B elem) {
        return Nil$.MODULE$.updated(index, (Object)elem);
    }

    public static Tuple2<List<Nothing$>, List<Nothing$>> splitAt(int n) {
        return Nil$.MODULE$.splitAt(n);
    }

    public static List<Nothing$> takeRight(int n) {
        return Nil$.MODULE$.takeRight(n);
    }

    public static List<Nothing$> slice(int from, int until) {
        return Nil$.MODULE$.slice(from, until);
    }

    public static List<Nothing$> take(int n) {
        return Nil$.MODULE$.take(n);
    }

    public static <B> List<B> appendedAll(IterableOnce<B> suffix) {
        return Nil$.MODULE$.appendedAll((IterableOnce)suffix);
    }

    public static <B> List<B> prependedAll(IterableOnce<B> prefix) {
        return Nil$.MODULE$.prependedAll((IterableOnce)prefix);
    }

    public static <B> List<B> prepended(B elem) {
        return Nil$.MODULE$.prepended((Object)elem);
    }

    public static boolean isEmpty() {
        return Nil$.MODULE$.isEmpty();
    }

    public static <B> List<B> reverse_$colon$colon$colon(List<B> prefix) {
        return Nil$.MODULE$.reverse_$colon$colon$colon(prefix);
    }

    public static <B> List<B> $colon$colon$colon(List<B> prefix) {
        return Nil$.MODULE$.$colon$colon$colon(prefix);
    }

    public static <B> List<B> $colon$colon(B elem) {
        return Nil$.MODULE$.$colon$colon(elem);
    }

    public static SeqFactory<List> iterableFactory() {
        return Nil$.MODULE$.iterableFactory();
    }

    public static Object sorted(Ordering ord) {
        return Nil$.MODULE$.sorted(ord);
    }

    public static Object patch(int from, IterableOnce other, int replaced) {
        return Nil$.MODULE$.patch(from, other, replaced);
    }

    public static Object distinctBy(Function1 f) {
        return Nil$.MODULE$.distinctBy(f);
    }

    public static LinearSeq dropWhile(Function1 p) {
        return Nil$.MODULE$.dropWhile(p);
    }

    public static LinearSeq drop(int n) {
        return Nil$.MODULE$.drop(n);
    }

    public static Object intersect(Seq that) {
        return Nil$.MODULE$.intersect(that);
    }

    public static Object diff(Seq that) {
        return Nil$.MODULE$.diff(that);
    }

    public static Object padTo(int len, Object elem) {
        return Nil$.MODULE$.padTo(len, elem);
    }

    public static Object appended(Object elem) {
        return Nil$.MODULE$.appended(elem);
    }

    public static Object dropRight(int n) {
        return Nil$.MODULE$.dropRight(n);
    }

    public static Object tapEach(Function1 f) {
        return Nil$.MODULE$.tapEach(f);
    }

    public static <A1, A2> Tuple2<List<A1>, List<A2>> partitionMap(Function1<Nothing$, Either<A1, A2>> f) {
        return Nil$.MODULE$.partitionMap(f);
    }

    public static Object scanLeft(Object z, Function2 op) {
        return Nil$.MODULE$.scanLeft(z, op);
    }

    public static Object zipWithIndex() {
        return Nil$.MODULE$.zipWithIndex();
    }

    public static Object zip(IterableOnce that) {
        return Nil$.MODULE$.zip(that);
    }

    public static Object flatten(Function1 toIterableOnce) {
        return Nil$.MODULE$.flatten(toIterableOnce);
    }

    public static <A1, A2, A3> Tuple3<List<A1>, List<A2>, List<A3>> unzip3(Function1<Nothing$, Tuple3<A1, A2, A3>> asTriple) {
        return Nil$.MODULE$.unzip3(asTriple);
    }

    public static Iterator<List<Nothing$>> tails() {
        return Nil$.MODULE$.tails();
    }

    public static Option<Nothing$> findLast(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.findLast(p);
    }

    public static int lastIndexWhere(Function1<Nothing$, Object> p, int end) {
        return Nil$.MODULE$.lastIndexWhere(p, end);
    }

    public static int indexWhere(Function1<Nothing$, Object> p, int from) {
        return Nil$.MODULE$.indexWhere(p, from);
    }

    public static int segmentLength(Function1<Nothing$, Object> p, int from) {
        return Nil$.MODULE$.segmentLength(p, from);
    }

    public static <B> boolean sameElements(IterableOnce<B> that) {
        return Nil$.MODULE$.sameElements(that);
    }

    public static <B> B foldLeft(B z, Function2<B, Nothing$, B> op) {
        return Nil$.MODULE$.foldLeft(z, op);
    }

    public static Object apply(int n) throws IndexOutOfBoundsException {
        return Nil$.MODULE$.apply(n);
    }

    public static boolean isDefinedAt(int x) {
        return Nil$.MODULE$.isDefinedAt(x);
    }

    public static int lengthCompare(Iterable<?> that) {
        return Nil$.MODULE$.lengthCompare(that);
    }

    public static scala.collection.immutable.Seq toSeq() {
        return Nil$.MODULE$.toSeq();
    }

    public static String toString() {
        return Nil$.MODULE$.toString();
    }

    public static int hashCode() {
        return Nil$.MODULE$.hashCode();
    }

    public static boolean canEqual(Object that) {
        return Nil$.MODULE$.canEqual(that);
    }

    public static <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
        return Nil$.MODULE$.search(elem, from, to, ord);
    }

    public static <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
        return Nil$.MODULE$.search(elem, ord);
    }

    public static IterableOps lengthIs() {
        return Nil$.MODULE$.lengthIs();
    }

    public static int sizeCompare(Iterable<?> that) {
        return Nil$.MODULE$.sizeCompare(that);
    }

    public static int sizeCompare(int otherSize) {
        return Nil$.MODULE$.sizeCompare(otherSize);
    }

    public static Range indices() {
        return Nil$.MODULE$.indices();
    }

    public static Object sortBy(Function1 f, Ordering ord) {
        return Nil$.MODULE$.sortBy(f, ord);
    }

    public static Object sortWith(Function2 lt) {
        return Nil$.MODULE$.sortWith(lt);
    }

    public static Iterator<List<Nothing$>> combinations(int n) {
        return Nil$.MODULE$.combinations(n);
    }

    public static Iterator<List<Nothing$>> permutations() {
        return Nil$.MODULE$.permutations();
    }

    public static Object reverseMap(Function1 f) {
        return Nil$.MODULE$.reverseMap(f);
    }

    public static <B> boolean containsSlice(Seq<B> that) {
        return Nil$.MODULE$.containsSlice(that);
    }

    public static <B> int lastIndexOfSlice(Seq<B> that) {
        return Nil$.MODULE$.lastIndexOfSlice(that);
    }

    public static <B> int lastIndexOfSlice(Seq<B> that, int end) {
        return Nil$.MODULE$.lastIndexOfSlice(that, end);
    }

    public static <B> int indexOfSlice(Seq<B> that) {
        return Nil$.MODULE$.indexOfSlice(that);
    }

    public static <B> int indexOfSlice(Seq<B> that, int from) {
        return Nil$.MODULE$.indexOfSlice(that, from);
    }

    public static int lastIndexWhere(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.lastIndexWhere(p);
    }

    public static <B> int lastIndexOf$default$2() {
        return Nil$.MODULE$.lastIndexOf$default$2();
    }

    public static <B> int lastIndexOf(B elem, int end) {
        return Nil$.MODULE$.lastIndexOf(elem, end);
    }

    public static <B> int indexOf(B elem) {
        return Nil$.MODULE$.indexOf(elem);
    }

    public static <B> int indexOf(B elem, int from) {
        return Nil$.MODULE$.indexOf(elem, from);
    }

    public static int indexWhere(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.indexWhere(p);
    }

    public static int prefixLength(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.prefixLength(p);
    }

    public static int segmentLength(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.segmentLength(p);
    }

    public static <B> boolean endsWith(Iterable<B> that) {
        return Nil$.MODULE$.endsWith(that);
    }

    public static <B> int startsWith$default$2() {
        return Nil$.MODULE$.startsWith$default$2();
    }

    public static <B> boolean startsWith(IterableOnce<B> that, int offset) {
        return Nil$.MODULE$.startsWith(that, offset);
    }

    public static Iterator<Nothing$> reverseIterator() {
        return Nil$.MODULE$.reverseIterator();
    }

    public static Object distinct() {
        return Nil$.MODULE$.distinct();
    }

    public static int size() {
        return Nil$.MODULE$.size();
    }

    public static Object union(Seq that) {
        return Nil$.MODULE$.union(that);
    }

    public static Object concat(IterableOnce suffix) {
        return Nil$.MODULE$.concat(suffix);
    }

    public static Object $colon$plus$plus(IterableOnce suffix) {
        return Nil$.MODULE$.$colon$plus$plus(suffix);
    }

    public static Object $plus$plus$colon(IterableOnce prefix) {
        return Nil$.MODULE$.$plus$plus$colon(prefix);
    }

    public static Object $colon$plus(Object elem) {
        return Nil$.MODULE$.$colon$plus(elem);
    }

    public static Object $plus$colon(Object elem) {
        return Nil$.MODULE$.$plus$colon(elem);
    }

    public static SeqView<Nothing$> view() {
        return Nil$.MODULE$.view();
    }

    public static <U> Function1<Object, Object> runWith(Function1<Nothing$, U> action) {
        return Nil$.MODULE$.runWith(action);
    }

    public static Object applyOrElse(Object x, Function1 function1) {
        return Nil$.MODULE$.applyOrElse(x, function1);
    }

    public static Function1<Object, Option<Nothing$>> lift() {
        return Nil$.MODULE$.lift();
    }

    public static <R$> PartialFunction<R$, Nothing$> compose(PartialFunction<R$, Object> k) {
        return Nil$.MODULE$.compose(k);
    }

    public static <C$> PartialFunction<Object, C$> andThen(PartialFunction<Nothing$, C$> k) {
        return Nil$.MODULE$.andThen(k);
    }

    public static <C$> PartialFunction<Object, C$> andThen(Function1<Nothing$, C$> k) {
        return Nil$.MODULE$.andThen(k);
    }

    public static <A1, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
        return Nil$.MODULE$.orElse(that);
    }

    public static PartialFunction elementWise() {
        return Nil$.MODULE$.elementWise();
    }

    public static Option unapply(Object a) {
        return Nil$.MODULE$.unapply(a);
    }

    public static <A$> Function1<A$, Nothing$> compose(Function1<A$, Object> g2) {
        return Nil$.MODULE$.compose(g2);
    }

    public static <B> LazyZip2<Nothing$, B, Nil$> lazyZip(Iterable<B> that) {
        return Nil$.MODULE$.lazyZip(that);
    }

    public static Iterable seq() {
        return Nil$.MODULE$.seq();
    }

    public static Iterable toIterable() {
        return Nil$.MODULE$.toIterable();
    }

    public static IterableOps empty() {
        return Nil$.MODULE$.empty();
    }

    public static Iterator<List<Nothing$>> inits() {
        return Nil$.MODULE$.inits();
    }

    public static Object zipAll(Iterable that, Object thisElem, Object thatElem) {
        return Nil$.MODULE$.zipAll(that, thisElem, thatElem);
    }

    public static Object $plus$plus(IterableOnce suffix) {
        return Nil$.MODULE$.$plus$plus(suffix);
    }

    public static Object scanRight(Object z, Function2 op) {
        return Nil$.MODULE$.scanRight(z, op);
    }

    public static Object scan(Object z, Function2 op) {
        return Nil$.MODULE$.scan(z, op);
    }

    public static <K$, B> Map<K$, B> groupMapReduce(Function1<Nothing$, K$> key, Function1<Nothing$, B> f, Function2<B, B, B> reduce) {
        return Nil$.MODULE$.groupMapReduce(key, f, reduce);
    }

    public static <K$, B> Map<K$, List<B>> groupMap(Function1<Nothing$, K$> key, Function1<Nothing$, B> f) {
        return Nil$.MODULE$.groupMap(key, f);
    }

    public static <K$> Map<K$, List<Nothing$>> groupBy(Function1<Nothing$, K$> f) {
        return Nil$.MODULE$.groupBy(f);
    }

    public static Iterator<List<Nothing$>> sliding(int size, int step) {
        return Nil$.MODULE$.sliding(size, step);
    }

    public static Iterator<List<Nothing$>> sliding(int size) {
        return Nil$.MODULE$.sliding(size);
    }

    public static Iterator<List<Nothing$>> grouped(int size) {
        return Nil$.MODULE$.grouped(size);
    }

    public static WithFilter<Nothing$, ?> withFilter(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.withFilter(p);
    }

    public static Object transpose(Function1 asIterable) {
        return Nil$.MODULE$.transpose(asIterable);
    }

    public static View<Nothing$> view(int from, int until) {
        return Nil$.MODULE$.view(from, until);
    }

    public static IterableOps sizeIs() {
        return Nil$.MODULE$.sizeIs();
    }

    public static Option<Nothing$> lastOption() {
        return Nil$.MODULE$.lastOption();
    }

    public static IterableFactory<?> companion() {
        return Nil$.MODULE$.companion();
    }

    public static Object repr() {
        return Nil$.MODULE$.repr();
    }

    public static boolean isTraversableAgain() {
        return Nil$.MODULE$.isTraversableAgain();
    }

    public static Iterable<Nothing$> toTraversable() {
        return Nil$.MODULE$.toTraversable();
    }

    public static Object toArray(ClassTag evidence$2) {
        return Nil$.MODULE$.toArray(evidence$2);
    }

    public static <B> Buffer<B> toBuffer() {
        return Nil$.MODULE$.toBuffer();
    }

    public static Stream<Nothing$> toStream() {
        return Nil$.MODULE$.toStream();
    }

    public static IndexedSeq<Nothing$> toIndexedSeq() {
        return Nil$.MODULE$.toIndexedSeq();
    }

    public static <B> Set<B> toSet() {
        return Nil$.MODULE$.toSet();
    }

    public static <K$, V$> Map<K$, V$> toMap($less$colon$less<Nothing$, Tuple2<K$, V$>> ev) {
        return Nil$.MODULE$.toMap(ev);
    }

    public static Vector<Nothing$> toVector() {
        return Nil$.MODULE$.toVector();
    }

    public static Iterator<Nothing$> toIterator() {
        return Nil$.MODULE$.toIterator();
    }

    public static <C1> C1 to(Factory<Nothing$, C1> factory2) {
        return Nil$.MODULE$.to(factory2);
    }

    public static StringBuilder addString(StringBuilder b) {
        return Nil$.MODULE$.addString(b);
    }

    public static StringBuilder addString(StringBuilder b, String sep) {
        return Nil$.MODULE$.addString(b, sep);
    }

    public static StringBuilder addString(StringBuilder b, String start2, String sep, String end) {
        return Nil$.MODULE$.addString(b, start2, sep, end);
    }

    public static String mkString() {
        return Nil$.MODULE$.mkString();
    }

    public static String mkString(String sep) {
        return Nil$.MODULE$.mkString(sep);
    }

    public static String mkString(String start2, String sep, String end) {
        return Nil$.MODULE$.mkString(start2, sep, end);
    }

    public static <B> boolean corresponds(IterableOnce<B> that, Function2<Nothing$, B, Object> p) {
        return ((AbstractIterable)Nil$.MODULE$).corresponds(that, p);
    }

    public static <B> B aggregate(Function0<B> z, Function2<B, Nothing$, B> seqop, Function2<B, B, B> combop) {
        return Nil$.MODULE$.aggregate(z, seqop, combop);
    }

    public static <B> Option<B> collectFirst(PartialFunction<Nothing$, B> pf) {
        return Nil$.MODULE$.collectFirst(pf);
    }

    public static <B> Option<Nothing$> minByOption(Function1<Nothing$, B> f, Ordering<B> cmp) {
        return Nil$.MODULE$.minByOption(f, cmp);
    }

    public static Object minBy(Function1 f, Ordering cmp) {
        return Nil$.MODULE$.minBy(f, cmp);
    }

    public static <B> Option<Nothing$> maxByOption(Function1<Nothing$, B> f, Ordering<B> cmp) {
        return Nil$.MODULE$.maxByOption(f, cmp);
    }

    public static Object maxBy(Function1 f, Ordering cmp) {
        return Nil$.MODULE$.maxBy(f, cmp);
    }

    public static <B> Option<Nothing$> maxOption(Ordering<B> ord) {
        return Nil$.MODULE$.maxOption(ord);
    }

    public static Object max(Ordering ord) {
        return Nil$.MODULE$.max(ord);
    }

    public static <B> Option<Nothing$> minOption(Ordering<B> ord) {
        return Nil$.MODULE$.minOption(ord);
    }

    public static Object min(Ordering ord) {
        return Nil$.MODULE$.min(ord);
    }

    public static <B> B product(Numeric<B> num) {
        return Nil$.MODULE$.product(num);
    }

    public static <B> B sum(Numeric<B> num) {
        return Nil$.MODULE$.sum(num);
    }

    public static <B> int copyToArray(Object xs, int start2, int len) {
        return Nil$.MODULE$.copyToArray(xs, start2, len);
    }

    public static <B> int copyToArray(Object xs, int start2) {
        return Nil$.MODULE$.copyToArray(xs, start2);
    }

    public static <B> int copyToArray(Object xs) {
        return Nil$.MODULE$.copyToArray(xs);
    }

    public static <B> void copyToBuffer(Buffer<B> dest) {
        Nil$.MODULE$.copyToBuffer(dest);
    }

    public static boolean nonEmpty() {
        return Nil$.MODULE$.nonEmpty();
    }

    public static <B> Option<B> reduceRightOption(Function2<Nothing$, B, B> op) {
        return Nil$.MODULE$.reduceRightOption(op);
    }

    public static <B> Option<B> reduceLeftOption(Function2<B, Nothing$, B> op) {
        return Nil$.MODULE$.reduceLeftOption(op);
    }

    public static <B> B reduceRight(Function2<Nothing$, B, B> op) {
        return Nil$.MODULE$.reduceRight(op);
    }

    public static <B> B reduceLeft(Function2<B, Nothing$, B> op) {
        return Nil$.MODULE$.reduceLeft(op);
    }

    public static <B> Option<B> reduceOption(Function2<B, B, B> op) {
        return Nil$.MODULE$.reduceOption(op);
    }

    public static <B> B reduce(Function2<B, B, B> op) {
        return Nil$.MODULE$.reduce(op);
    }

    public static <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
        return Nil$.MODULE$.fold(z, op);
    }

    public static <B> B $colon$bslash(B z, Function2<Nothing$, B, B> op) {
        return Nil$.MODULE$.$colon$bslash(z, op);
    }

    public static <B> B $div$colon(B z, Function2<B, Nothing$, B> op) {
        return Nil$.MODULE$.$div$colon(z, op);
    }

    public static int count(Function1<Nothing$, Object> p) {
        return Nil$.MODULE$.count(p);
    }

    public static boolean hasDefiniteSize() {
        return Nil$.MODULE$.hasDefiniteSize();
    }

    public static <S extends Stepper<?>> S stepper(StepperShape<Nothing$, S> shape) {
        return Nil$.MODULE$.stepper(shape);
    }
}

