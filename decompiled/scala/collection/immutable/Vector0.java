/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterable;
import scala.collection.Factory;
import scala.collection.IndexedSeqView;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.LazyZip2;
import scala.collection.Searching;
import scala.collection.Seq;
import scala.collection.SeqFactory;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.WithFilter;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Range;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector0$;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\u0005%s!\u0002\n\u0014\u0011\u0013Qb!\u0002\u000f\u0014\u0011\u0013i\u0002\"B\u0013\u0002\t\u00031\u0003\"B\u0014\u0002\t\u0003A\u0003\"\u0002\u0018\u0002\t\u0003z\u0003\"B \u0002\t\u0003\u0002\u0005\"\u0002$\u0002\t\u0003:\u0005\"B'\u0002\t\u0003r\u0005\"\u0002-\u0002\t\u0003J\u0006\"B.\u0002\t\u0003J\u0006B\u0002/\u0002A\u0013EQ\f\u0003\u0004c\u0003\u0011E1c\u0019\u0005\u0007I\u0006!\tbE3\t\rM\fA\u0011C\nu\u0011\u00151\u0018\u0001\"\u0011x\u0011\u0019i\u0018\u0001)C)}\"A\u0011qC\u0001!\n#\nI\u0002\u0003\u0005\u0002,\u0005\u0001K\u0011KA\u0017\u0003\u001d1Vm\u0019;peBR!\u0001F\u000b\u0002\u0013%lW.\u001e;bE2,'B\u0001\f\u0018\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u00021\u0005)1oY1mC\u000e\u0001\u0001CA\u000e\u0002\u001b\u0005\u0019\"a\u0002,fGR|'\u000fM\n\u0003\u0003y\u00012aG\u0010\"\u0013\t\u00013CA\u0005CS\u001e4Vm\u0019;peB\u0011!eI\u0007\u0002/%\u0011Ae\u0006\u0002\b\u001d>$\b.\u001b8h\u0003\u0019a\u0014N\\5u}Q\t!$A\u0003baBd\u0017\u0010\u0006\u0002\"S!)!f\u0001a\u0001W\u0005)\u0011N\u001c3fqB\u0011!\u0005L\u0005\u0003[]\u00111!\u00138u\u0003\u001d)\b\u000fZ1uK\u0012,\"\u0001\r\u001c\u0015\u0007EbT\bE\u0002\u001ceQJ!aM\n\u0003\rY+7\r^8s!\t)d\u0007\u0004\u0001\u0005\u000b]\"!\u0019\u0001\u001d\u0003\u0003\t\u000b\"!I\u001d\u0011\u0005\tR\u0014BA\u001e\u0018\u0005\r\te.\u001f\u0005\u0006U\u0011\u0001\ra\u000b\u0005\u0006}\u0011\u0001\r\u0001N\u0001\u0005K2,W.\u0001\u0005baB,g\u000eZ3e+\t\tE\t\u0006\u0002C\u000bB\u00191DM\"\u0011\u0005U\"E!B\u001c\u0006\u0005\u0004A\u0004\"\u0002 \u0006\u0001\u0004\u0019\u0015!\u00039sKB,g\u000eZ3e+\tA5\n\u0006\u0002J\u0019B\u00191D\r&\u0011\u0005UZE!B\u001c\u0007\u0005\u0004A\u0004\"\u0002 \u0007\u0001\u0004Q\u0015aA7baV\u0011qJ\u0015\u000b\u0003!N\u00032a\u0007\u001aR!\t)$\u000bB\u00038\u000f\t\u0007\u0001\bC\u0003U\u000f\u0001\u0007Q+A\u0001g!\u0011\u0011c+I)\n\u0005];\"!\u0003$v]\u000e$\u0018n\u001c82\u0003\u0011!\u0018-\u001b7\u0016\u0003i\u00032a\u0007\u001a\"\u0003\u0011Ig.\u001b;\u0002\rMd\u0017nY31)\rQf\f\u0019\u0005\u0006?*\u0001\raK\u0001\u0003Y>DQ!\u0019\u0006A\u0002-\n!\u0001[5\u0002!Y,7\r^8s'2L7-Z\"pk:$X#A\u0016\u0002\u0017Y,7\r^8s'2L7-\u001a\u000b\u0003MF\u0004$aZ6\u0011\u0007\tB'.\u0003\u0002j/\t)\u0011I\u001d:bsB\u0011Qg\u001b\u0003\nY2\t\t\u0011!A\u0003\u00025\u0014Aa\u0018\u00132cE\u0011\u0011E\u001c\t\u0003E=L!\u0001]\f\u0003\r\u0005s\u0017PU3g\u0011\u0015\u0011H\u00021\u0001,\u0003\rIG\r_\u0001\u0018m\u0016\u001cGo\u001c:TY&\u001cW\r\u0015:fM&DH*\u001a8hi\"$\"aK;\t\u000bIl\u0001\u0019A\u0016\u0002\r\u0015\fX/\u00197t)\tA8\u0010\u0005\u0002#s&\u0011!p\u0006\u0002\b\u0005>|G.Z1o\u0011\u0015ah\u00021\u0001:\u0003\u0005y\u0017!\u00049sKB,g\u000eZ3e\u00032d\u0007'F\u0002\u0000\u0003\u000b!b!!\u0001\u0002\b\u0005M\u0001\u0003B\u000e3\u0003\u0007\u00012!NA\u0003\t\u00159tB1\u00019\u0011\u001d\tIa\u0004a\u0001\u0003\u0017\ta\u0001\u001d:fM&D\bCBA\u0007\u0003\u001f\t\u0019!D\u0001\u0016\u0013\r\t\t\"\u0006\u0002\r\u0013R,'/\u00192mK>s7-\u001a\u0005\u0007\u0003+y\u0001\u0019A\u0016\u0002\u0003-\fA\"\u00199qK:$W\rZ!mYB*B!a\u0007\u0002\"Q1\u0011QDA\u0012\u0003S\u0001Ba\u0007\u001a\u0002 A\u0019Q'!\t\u0005\u000b]\u0002\"\u0019\u0001\u001d\t\u000f\u0005\u0015\u0002\u00031\u0001\u0002(\u000511/\u001e4gSb\u0004b!!\u0004\u0002\u0010\u0005}\u0001BBA\u000b!\u0001\u00071&\u0001\u0003j_>\u0014G\u0003BA\u0018\u0003\u000f\u0002B!!\r\u0002B9!\u00111GA\u001f\u001d\u0011\t)$a\u000f\u000e\u0005\u0005]\"bAA\u001d3\u00051AH]8pizJ\u0011\u0001G\u0005\u0004\u0003\u007f9\u0012a\u00029bG.\fw-Z\u0005\u0005\u0003\u0007\n)EA\rJ]\u0012,\u0007pT;u\u001f\u001a\u0014u.\u001e8eg\u0016C8-\u001a9uS>t'bAA /!)!&\u0005a\u0001W\u0001")
public final class Vector0 {
    public static boolean equals(Object o) {
        return Vector0$.MODULE$.equals(o);
    }

    public static Vector<Nothing$> init() {
        return Vector0$.MODULE$.init();
    }

    public static Vector<Nothing$> tail() {
        return Vector0$.MODULE$.tail();
    }

    public static <B> Vector<B> map(Function1<Nothing$, B> f) {
        return Vector0$.MODULE$.map((Function1)f);
    }

    public static <B> Vector<B> prepended(B elem) {
        return Vector0$.MODULE$.prepended((Object)elem);
    }

    public static <B> Vector<B> appended(B elem) {
        return Vector0$.MODULE$.appended((Object)elem);
    }

    public static <B> Vector<B> updated(int index, B elem) {
        return Vector0$.MODULE$.updated(index, (Object)elem);
    }

    public static Nothing$ apply(int index) {
        return Vector0$.MODULE$.apply(index);
    }

    public static Vector<Nothing$> slice(int from, int until) {
        return Vector0$.MODULE$.slice(from, until);
    }

    public static <U> void foreach(Function1<Nothing$, U> f) {
        Vector0$.MODULE$.foreach(f);
    }

    public static Object last() {
        return Vector0$.MODULE$.last();
    }

    public static Object head() {
        return Vector0$.MODULE$.head();
    }

    public static <S extends Stepper<?>> S stepper(StepperShape<Nothing$, S> shape) {
        return Vector0$.MODULE$.stepper(shape);
    }

    public static Vector<Nothing$> toVector() {
        return Vector0$.MODULE$.toVector();
    }

    public static <B> int copyToArray(Object xs, int start2, int len) {
        return Vector0$.MODULE$.copyToArray(xs, start2, len);
    }

    public static Vector<Nothing$> dropRight(int n) {
        return Vector0$.MODULE$.dropRight(n);
    }

    public static Vector<Nothing$> takeRight(int n) {
        return Vector0$.MODULE$.takeRight(n);
    }

    public static Vector<Nothing$> drop(int n) {
        return Vector0$.MODULE$.drop(n);
    }

    public static Vector<Nothing$> take(int n) {
        return Vector0$.MODULE$.take(n);
    }

    public static String className() {
        return Vector0$.MODULE$.className();
    }

    public static <B> Vector<B> appendedAll(IterableOnce<B> suffix) {
        return Vector0$.MODULE$.appendedAll((IterableOnce)suffix);
    }

    public static <B> Vector<B> prependedAll(IterableOnce<B> prefix) {
        return Vector0$.MODULE$.prependedAll((IterableOnce)prefix);
    }

    public static Iterator<Nothing$> iterator() {
        return Vector0$.MODULE$.iterator();
    }

    public static int length() {
        return Vector0$.MODULE$.length();
    }

    public static SeqFactory<Vector> iterableFactory() {
        return Vector0$.MODULE$.iterableFactory();
    }

    public static Object sorted(Ordering ord) {
        return Vector0$.MODULE$.sorted(ord);
    }

    public static Object patch(int from, IterableOnce other, int replaced) {
        return Vector0$.MODULE$.patch(from, other, replaced);
    }

    public static Object distinctBy(Function1 f) {
        return Vector0$.MODULE$.distinctBy(f);
    }

    public static Object intersect(Seq that) {
        return Vector0$.MODULE$.intersect(that);
    }

    public static Object diff(Seq that) {
        return Vector0$.MODULE$.diff(that);
    }

    public static Object padTo(int len, Object elem) {
        return Vector0$.MODULE$.padTo(len, elem);
    }

    public static Object tapEach(Function1 f) {
        return Vector0$.MODULE$.tapEach(f);
    }

    public static <A1, A2> Tuple2<Vector<A1>, Vector<A2>> partitionMap(Function1<Nothing$, Either<A1, A2>> f) {
        return Vector0$.MODULE$.partitionMap(f);
    }

    public static Object filterNot(Function1 pred) {
        return Vector0$.MODULE$.filterNot(pred);
    }

    public static Object filter(Function1 pred) {
        return Vector0$.MODULE$.filter(pred);
    }

    public static Object scanLeft(Object z, Function2 op) {
        return Vector0$.MODULE$.scanLeft(z, op);
    }

    public static Object zipWithIndex() {
        return Vector0$.MODULE$.zipWithIndex();
    }

    public static Object zip(IterableOnce that) {
        return Vector0$.MODULE$.zip(that);
    }

    public static Object flatten(Function1 toIterableOnce) {
        return Vector0$.MODULE$.flatten(toIterableOnce);
    }

    public static Object collect(PartialFunction pf) {
        return Vector0$.MODULE$.collect(pf);
    }

    public static Object flatMap(Function1 f) {
        return Vector0$.MODULE$.flatMap(f);
    }

    public static <A1, A2, A3> Tuple3<Vector<A1>, Vector<A2>, Vector<A3>> unzip3(Function1<Nothing$, Tuple3<A1, A2, A3>> asTriple) {
        return Vector0$.MODULE$.unzip3(asTriple);
    }

    public static <A1, A2> Tuple2<Vector<A1>, Vector<A2>> unzip(Function1<Nothing$, Tuple2<A1, A2>> asPair) {
        return Vector0$.MODULE$.unzip(asPair);
    }

    public static Tuple2<Vector<Nothing$>, Vector<Nothing$>> span(Function1<Nothing$, Object> p) {
        return Vector0$.MODULE$.span(p);
    }

    public static Tuple2<Vector<Nothing$>, Vector<Nothing$>> partition(Function1<Nothing$, Object> p) {
        return Vector0$.MODULE$.partition(p);
    }

    public static <B> boolean sameElements(IterableOnce<B> o) {
        return Vector0$.MODULE$.sameElements(o);
    }

    public static boolean canEqual(Object that) {
        return Vector0$.MODULE$.canEqual(that);
    }

    public static IndexedSeq<Nothing$> toIndexedSeq() {
        return Vector0$.MODULE$.toIndexedSeq();
    }

    public static <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
        return Vector0$.MODULE$.search(elem, from, to, ord);
    }

    public static <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
        return Vector0$.MODULE$.search(elem, ord);
    }

    public static int lengthCompare(Iterable<?> that) {
        return Vector0$.MODULE$.lengthCompare(that);
    }

    public static int knownSize() {
        return Vector0$.MODULE$.knownSize();
    }

    public static int lengthCompare(int len) {
        return Vector0$.MODULE$.lengthCompare(len);
    }

    public static Option<Nothing$> headOption() {
        return Vector0$.MODULE$.headOption();
    }

    public static Object reverse() {
        return Vector0$.MODULE$.reverse();
    }

    public static IndexedSeqView<Nothing$> view(int from, int until) {
        return Vector0$.MODULE$.view(from, until);
    }

    public static IndexedSeqView<Nothing$> view() {
        return Vector0$.MODULE$.view();
    }

    public static <B$> B$ foldRight(B$ z, Function2<Nothing$, B$, B$> op) {
        return Vector0$.MODULE$.foldRight(z, op);
    }

    public static Iterator<Nothing$> reverseIterator() {
        return Vector0$.MODULE$.reverseIterator();
    }

    public static scala.collection.immutable.Seq toSeq() {
        return Vector0$.MODULE$.toSeq();
    }

    public static String toString() {
        return Vector0$.MODULE$.toString();
    }

    public static int hashCode() {
        return Vector0$.MODULE$.hashCode();
    }

    public static <B$> boolean corresponds(Seq<B$> that, Function2<Nothing$, B$, Object> p) {
        return Vector0$.MODULE$.corresponds(that, p);
    }

    public static boolean isEmpty() {
        return Vector0$.MODULE$.isEmpty();
    }

    public static IterableOps lengthIs() {
        return Vector0$.MODULE$.lengthIs();
    }

    public static int sizeCompare(Iterable<?> that) {
        return Vector0$.MODULE$.sizeCompare(that);
    }

    public static int sizeCompare(int otherSize) {
        return Vector0$.MODULE$.sizeCompare(otherSize);
    }

    public static Range indices() {
        return Vector0$.MODULE$.indices();
    }

    public static Object sortBy(Function1 f, Ordering ord) {
        return Vector0$.MODULE$.sortBy(f, ord);
    }

    public static Object sortWith(Function2 lt) {
        return Vector0$.MODULE$.sortWith(lt);
    }

    public static Iterator<Vector<Nothing$>> combinations(int n) {
        return Vector0$.MODULE$.combinations(n);
    }

    public static Iterator<Vector<Nothing$>> permutations() {
        return Vector0$.MODULE$.permutations();
    }

    public static Object reverseMap(Function1 f) {
        return Vector0$.MODULE$.reverseMap(f);
    }

    public static <A1> boolean contains(A1 elem) {
        return Vector0$.MODULE$.contains(elem);
    }

    public static <B> boolean containsSlice(Seq<B> that) {
        return Vector0$.MODULE$.containsSlice(that);
    }

    public static Option<Nothing$> findLast(Function1<Nothing$, Object> p) {
        return Vector0$.MODULE$.findLast(p);
    }

    public static <B> int lastIndexOfSlice(Seq<B> that) {
        return Vector0$.MODULE$.lastIndexOfSlice(that);
    }

    public static <B> int lastIndexOfSlice(Seq<B> that, int end) {
        return Vector0$.MODULE$.lastIndexOfSlice(that, end);
    }

    public static <B> int indexOfSlice(Seq<B> that) {
        return Vector0$.MODULE$.indexOfSlice(that);
    }

    public static <B> int indexOfSlice(Seq<B> that, int from) {
        return Vector0$.MODULE$.indexOfSlice(that, from);
    }

    public static int lastIndexWhere(Function1<Nothing$, Object> p) {
        return Vector0$.MODULE$.lastIndexWhere(p);
    }

    public static int lastIndexWhere(Function1<Nothing$, Object> p, int end) {
        return Vector0$.MODULE$.lastIndexWhere(p, end);
    }

    public static <B> int lastIndexOf$default$2() {
        return Vector0$.MODULE$.lastIndexOf$default$2();
    }

    public static <B> int lastIndexOf(B elem, int end) {
        return Vector0$.MODULE$.lastIndexOf(elem, end);
    }

    public static <B> int indexOf(B elem) {
        return Vector0$.MODULE$.indexOf(elem);
    }

    public static <B> int indexOf(B elem, int from) {
        return Vector0$.MODULE$.indexOf(elem, from);
    }

    public static int indexWhere(Function1<Nothing$, Object> p) {
        return Vector0$.MODULE$.indexWhere(p);
    }

    public static int indexWhere(Function1<Nothing$, Object> p, int from) {
        return Vector0$.MODULE$.indexWhere(p, from);
    }

    public static int prefixLength(Function1<Nothing$, Object> p) {
        return Vector0$.MODULE$.prefixLength(p);
    }

    public static int segmentLength(Function1<Nothing$, Object> p, int from) {
        return Vector0$.MODULE$.segmentLength(p, from);
    }

    public static int segmentLength(Function1<Nothing$, Object> p) {
        return Vector0$.MODULE$.segmentLength(p);
    }

    public static boolean isDefinedAt(int idx) {
        return Vector0$.MODULE$.isDefinedAt(idx);
    }

    public static <B> boolean endsWith(Iterable<B> that) {
        return Vector0$.MODULE$.endsWith(that);
    }

    public static <B> int startsWith$default$2() {
        return Vector0$.MODULE$.startsWith$default$2();
    }

    public static <B> boolean startsWith(IterableOnce<B> that, int offset) {
        return Vector0$.MODULE$.startsWith(that, offset);
    }

    public static Object distinct() {
        return Vector0$.MODULE$.distinct();
    }

    public static int size() {
        return Vector0$.MODULE$.size();
    }

    public static Object union(Seq that) {
        return Vector0$.MODULE$.union(that);
    }

    public static Object concat(IterableOnce suffix) {
        return Vector0$.MODULE$.concat(suffix);
    }

    public static Object $colon$plus$plus(IterableOnce suffix) {
        return Vector0$.MODULE$.$colon$plus$plus(suffix);
    }

    public static Object $plus$plus$colon(IterableOnce prefix) {
        return Vector0$.MODULE$.$plus$plus$colon(prefix);
    }

    public static Object $colon$plus(Object elem) {
        return Vector0$.MODULE$.$colon$plus(elem);
    }

    public static Object $plus$colon(Object elem) {
        return Vector0$.MODULE$.$plus$colon(elem);
    }

    public static <U> Function1<Object, Object> runWith(Function1<Nothing$, U> action) {
        return Vector0$.MODULE$.runWith(action);
    }

    public static Object applyOrElse(Object x, Function1 function1) {
        return Vector0$.MODULE$.applyOrElse(x, function1);
    }

    public static Function1<Object, Option<Nothing$>> lift() {
        return Vector0$.MODULE$.lift();
    }

    public static <R$> PartialFunction<R$, Nothing$> compose(PartialFunction<R$, Object> k) {
        return Vector0$.MODULE$.compose(k);
    }

    public static <C$> PartialFunction<Object, C$> andThen(PartialFunction<Nothing$, C$> k) {
        return Vector0$.MODULE$.andThen(k);
    }

    public static <C$> PartialFunction<Object, C$> andThen(Function1<Nothing$, C$> k) {
        return Vector0$.MODULE$.andThen(k);
    }

    public static <A1, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
        return Vector0$.MODULE$.orElse(that);
    }

    public static PartialFunction elementWise() {
        return Vector0$.MODULE$.elementWise();
    }

    public static Option unapply(Object a) {
        return Vector0$.MODULE$.unapply(a);
    }

    public static <A$> Function1<A$, Nothing$> compose(Function1<A$, Object> g2) {
        return Vector0$.MODULE$.compose(g2);
    }

    public static <B> LazyZip2<Nothing$, B, Vector0$> lazyZip(Iterable<B> that) {
        return Vector0$.MODULE$.lazyZip(that);
    }

    public static Iterable seq() {
        return Vector0$.MODULE$.seq();
    }

    public static Iterable toIterable() {
        return Vector0$.MODULE$.toIterable();
    }

    public static IterableOps empty() {
        return Vector0$.MODULE$.empty();
    }

    public static Iterator<Vector<Nothing$>> inits() {
        return Vector0$.MODULE$.inits();
    }

    public static Iterator<Vector<Nothing$>> tails() {
        return Vector0$.MODULE$.tails();
    }

    public static Object zipAll(Iterable that, Object thisElem, Object thatElem) {
        return Vector0$.MODULE$.zipAll(that, thisElem, thatElem);
    }

    public static Object $plus$plus(IterableOnce suffix) {
        return Vector0$.MODULE$.$plus$plus(suffix);
    }

    public static Object scanRight(Object z, Function2 op) {
        return Vector0$.MODULE$.scanRight(z, op);
    }

    public static Object scan(Object z, Function2 op) {
        return Vector0$.MODULE$.scan(z, op);
    }

    public static <K$, B> Map<K$, B> groupMapReduce(Function1<Nothing$, K$> key, Function1<Nothing$, B> f, Function2<B, B, B> reduce) {
        return Vector0$.MODULE$.groupMapReduce(key, f, reduce);
    }

    public static <K$, B> Map<K$, Vector<B>> groupMap(Function1<Nothing$, K$> key, Function1<Nothing$, B> f) {
        return Vector0$.MODULE$.groupMap(key, f);
    }

    public static <K$> Map<K$, Vector<Nothing$>> groupBy(Function1<Nothing$, K$> f) {
        return Vector0$.MODULE$.groupBy(f);
    }

    public static Iterator<Vector<Nothing$>> sliding(int size, int step) {
        return Vector0$.MODULE$.sliding(size, step);
    }

    public static Iterator<Vector<Nothing$>> sliding(int size) {
        return Vector0$.MODULE$.sliding(size);
    }

    public static Iterator<Vector<Nothing$>> grouped(int size) {
        return Vector0$.MODULE$.grouped(size);
    }

    public static Object dropWhile(Function1 p) {
        return Vector0$.MODULE$.dropWhile(p);
    }

    public static Object takeWhile(Function1 p) {
        return Vector0$.MODULE$.takeWhile(p);
    }

    public static Tuple2<Vector<Nothing$>, Vector<Nothing$>> splitAt(int n) {
        return Vector0$.MODULE$.splitAt(n);
    }

    public static WithFilter<Nothing$, ?> withFilter(Function1<Nothing$, Object> p) {
        return Vector0$.MODULE$.withFilter(p);
    }

    public static Object transpose(Function1 asIterable) {
        return Vector0$.MODULE$.transpose(asIterable);
    }

    public static IterableOps sizeIs() {
        return Vector0$.MODULE$.sizeIs();
    }

    public static Option<Nothing$> lastOption() {
        return Vector0$.MODULE$.lastOption();
    }

    public static IterableFactory<?> companion() {
        return Vector0$.MODULE$.companion();
    }

    public static Object repr() {
        return Vector0$.MODULE$.repr();
    }

    public static boolean isTraversableAgain() {
        return Vector0$.MODULE$.isTraversableAgain();
    }

    public static Iterable<Nothing$> toTraversable() {
        return Vector0$.MODULE$.toTraversable();
    }

    public static Object toArray(ClassTag evidence$2) {
        return Vector0$.MODULE$.toArray(evidence$2);
    }

    public static <B> Buffer<B> toBuffer() {
        return Vector0$.MODULE$.toBuffer();
    }

    public static Stream<Nothing$> toStream() {
        return Vector0$.MODULE$.toStream();
    }

    public static <B> Set<B> toSet() {
        return Vector0$.MODULE$.toSet();
    }

    public static <K$, V$> Map<K$, V$> toMap($less$colon$less<Nothing$, Tuple2<K$, V$>> ev) {
        return Vector0$.MODULE$.toMap(ev);
    }

    public static List<Nothing$> toList() {
        return Vector0$.MODULE$.toList();
    }

    public static Iterator<Nothing$> toIterator() {
        return Vector0$.MODULE$.toIterator();
    }

    public static <C1> C1 to(Factory<Nothing$, C1> factory2) {
        return Vector0$.MODULE$.to(factory2);
    }

    public static StringBuilder addString(StringBuilder b) {
        return Vector0$.MODULE$.addString(b);
    }

    public static StringBuilder addString(StringBuilder b, String sep) {
        return Vector0$.MODULE$.addString(b, sep);
    }

    public static StringBuilder addString(StringBuilder b, String start2, String sep, String end) {
        return Vector0$.MODULE$.addString(b, start2, sep, end);
    }

    public static String mkString() {
        return Vector0$.MODULE$.mkString();
    }

    public static String mkString(String sep) {
        return Vector0$.MODULE$.mkString(sep);
    }

    public static String mkString(String start2, String sep, String end) {
        return Vector0$.MODULE$.mkString(start2, sep, end);
    }

    public static <B> boolean corresponds(IterableOnce<B> that, Function2<Nothing$, B, Object> p) {
        return ((AbstractIterable)Vector0$.MODULE$).corresponds(that, p);
    }

    public static <B> B aggregate(Function0<B> z, Function2<B, Nothing$, B> seqop, Function2<B, B, B> combop) {
        return Vector0$.MODULE$.aggregate(z, seqop, combop);
    }

    public static <B> Option<B> collectFirst(PartialFunction<Nothing$, B> pf) {
        return Vector0$.MODULE$.collectFirst(pf);
    }

    public static <B> Option<Nothing$> minByOption(Function1<Nothing$, B> f, Ordering<B> cmp) {
        return Vector0$.MODULE$.minByOption(f, cmp);
    }

    public static Object minBy(Function1 f, Ordering cmp) {
        return Vector0$.MODULE$.minBy(f, cmp);
    }

    public static <B> Option<Nothing$> maxByOption(Function1<Nothing$, B> f, Ordering<B> cmp) {
        return Vector0$.MODULE$.maxByOption(f, cmp);
    }

    public static Object maxBy(Function1 f, Ordering cmp) {
        return Vector0$.MODULE$.maxBy(f, cmp);
    }

    public static <B> Option<Nothing$> maxOption(Ordering<B> ord) {
        return Vector0$.MODULE$.maxOption(ord);
    }

    public static Object max(Ordering ord) {
        return Vector0$.MODULE$.max(ord);
    }

    public static <B> Option<Nothing$> minOption(Ordering<B> ord) {
        return Vector0$.MODULE$.minOption(ord);
    }

    public static Object min(Ordering ord) {
        return Vector0$.MODULE$.min(ord);
    }

    public static <B> B product(Numeric<B> num) {
        return Vector0$.MODULE$.product(num);
    }

    public static <B> B sum(Numeric<B> num) {
        return Vector0$.MODULE$.sum(num);
    }

    public static <B> int copyToArray(Object xs, int start2) {
        return Vector0$.MODULE$.copyToArray(xs, start2);
    }

    public static <B> int copyToArray(Object xs) {
        return Vector0$.MODULE$.copyToArray(xs);
    }

    public static <B> void copyToBuffer(Buffer<B> dest) {
        Vector0$.MODULE$.copyToBuffer(dest);
    }

    public static boolean nonEmpty() {
        return Vector0$.MODULE$.nonEmpty();
    }

    public static <B> Option<B> reduceRightOption(Function2<Nothing$, B, B> op) {
        return Vector0$.MODULE$.reduceRightOption(op);
    }

    public static <B> Option<B> reduceLeftOption(Function2<B, Nothing$, B> op) {
        return Vector0$.MODULE$.reduceLeftOption(op);
    }

    public static <B> B reduceRight(Function2<Nothing$, B, B> op) {
        return Vector0$.MODULE$.reduceRight(op);
    }

    public static <B> B reduceLeft(Function2<B, Nothing$, B> op) {
        return Vector0$.MODULE$.reduceLeft(op);
    }

    public static <B> Option<B> reduceOption(Function2<B, B, B> op) {
        return Vector0$.MODULE$.reduceOption(op);
    }

    public static <B> B reduce(Function2<B, B, B> op) {
        return Vector0$.MODULE$.reduce(op);
    }

    public static <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
        return Vector0$.MODULE$.fold(z, op);
    }

    public static <B> B $colon$bslash(B z, Function2<Nothing$, B, B> op) {
        return Vector0$.MODULE$.$colon$bslash(z, op);
    }

    public static <B> B $div$colon(B z, Function2<B, Nothing$, B> op) {
        return Vector0$.MODULE$.$div$colon(z, op);
    }

    public static <B> B foldLeft(B z, Function2<B, Nothing$, B> op) {
        return Vector0$.MODULE$.foldLeft(z, op);
    }

    public static Option<Nothing$> find(Function1<Nothing$, Object> p) {
        return Vector0$.MODULE$.find(p);
    }

    public static int count(Function1<Nothing$, Object> p) {
        return Vector0$.MODULE$.count(p);
    }

    public static boolean exists(Function1<Nothing$, Object> p) {
        return Vector0$.MODULE$.exists(p);
    }

    public static boolean forall(Function1<Nothing$, Object> p) {
        return Vector0$.MODULE$.forall(p);
    }

    public static boolean hasDefiniteSize() {
        return Vector0$.MODULE$.hasDefiniteSize();
    }
}

