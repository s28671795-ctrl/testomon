/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.generic;

import java.io.Serializable;
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
import scala.collection.StringView;
import scala.collection.View;
import scala.collection.WithFilter;
import scala.collection.generic.IsIterable;
import scala.collection.generic.IsIterableOnce;
import scala.collection.generic.IsSeq$;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Range;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.mutable.ArrayBuilder;
import scala.collection.mutable.ArraySeq;
import scala.collection.mutable.ArraySeq$;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Map;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dgaB\t\u0013!\u0003\r\t!\u0007\u0005\u0006]\u0001!\ta\f\u0005\bg\u0001\u0011\r\u0011\"\u00115\u0011\u0015\u0011\u0006A\"\u0001T\u000f\u00151&\u0003#\u0001X\r\u0015\t\"\u0003#\u0001Y\u0011\u0015IV\u0001\"\u0001[\u0011\u001dYVA1A\u0005\nqCa!Y\u0003!\u0002\u0013i\u0006\"\u00022\u0006\t\u0007\u0019\u0007\"\u0002=\u0006\t\u0007I\b\"CA\u000f\u000b\t\u0007I1AA\u0010\u0011!\t9%\u0002Q\u0001\n\u0005\u0005\u0002\"CA%\u000b\t\u0007I1AA&\u0011!\ty&\u0002Q\u0001\n\u00055\u0003bBA1\u000b\u0011\r\u00111\r\u0005\b\u0003\u0017+A1AAG\u0005\u0015I5oU3r\u0015\t\u0019B#A\u0004hK:,'/[2\u000b\u0005U1\u0012AC2pY2,7\r^5p]*\tq#A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005i)3c\u0001\u0001\u001c?A\u0011A$H\u0007\u0002-%\u0011aD\u0006\u0002\u0007\u0003:L(+\u001a4\u0011\u0007\u0001\n3%D\u0001\u0013\u0013\t\u0011#C\u0001\u0006Jg&#XM]1cY\u0016\u0004\"\u0001J\u0013\r\u0001\u0011)a\u0005\u0001b\u0001O\t!!+\u001a9s#\tA3\u0006\u0005\u0002\u001dS%\u0011!F\u0006\u0002\b\u001d>$\b.\u001b8h!\taB&\u0003\u0002.-\t\u0019\u0011I\\=\u0002\r\u0011Jg.\u001b;%)\u0005\u0001\u0004C\u0001\u000f2\u0013\t\u0011dC\u0001\u0003V]&$\u0018AC2p]Z,'o]5p]V\tQ\u0007\u0005\u0003\u001dm\rB\u0014BA\u001c\u0017\u0005%1UO\\2uS>t\u0017\u0007E\u0003:uq\u0012U)D\u0001\u0015\u0013\tYDC\u0001\u0004TKF|\u0005o\u001d\t\u0003{yj\u0011\u0001A\u0005\u0003\u007f\u0001\u0013\u0011!Q\u0005\u0003\u0003J\u0011a\"S:Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW\r\u0005\u0002:\u0007&\u0011A\t\u0006\u0002\t\u0013R,'/\u00192mKB\u0011QHR\u0005\u0003\u000f\u0006\u0012\u0011a\u0011\u0015\u0007\u0005%cUj\u0014)\u0011\u0005qQ\u0015BA&\u0017\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\u0005q\u0015AK\u0014d_:4XM]:j_:<\u0003%[:!]><\b%\u0019\u0011nKRDw\u000e\u001a\u0011oC6,G\rI\u0014baBd\u0017pJ\u0001\u0006g&t7-Z\u0011\u0002#\u00061!GL\u00194]A\nQ!\u00199qYf$\"\u0001\u000f+\t\u000bU\u001b\u0001\u0019A\u0012\u0002\t\r|G\u000e\\\u0001\u0006\u0013N\u001cV-\u001d\t\u0003A\u0015\u0019\"!B\u000e\u0002\rqJg.\u001b;?)\u00059\u0016AD:fc>\u00038/S:TKF4\u0016\r\\\u000b\u0002;B\u0019\u0001\u0005\u00010\u0011\u0007ez6&\u0003\u0002a)\t\u00191+Z9\u0002\u001fM,\u0017o\u00149t\u0013N\u001cV-\u001d,bY\u0002\n1b]3r\u001fB\u001c\u0018j]*fcV\u0019AM\u001b;\u0016\u0003\u0015\u0014\"A\u001a5\u0007\t\u001d,\u0001!\u001a\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\t\u0004A\u0001I\u0007c\u0001\u0013kg\u0012)1.\u0003b\u0001Y\n\u00191i\u0011\u0019\u0016\u00055\u0004\u0018C\u0001\u0015o!\u0015I$h\u001c\"s!\t!\u0003\u000fB\u0003rU\n\u0007qEA\u0001Y!\r!#n\u001c\t\u0003IQ$Q!^\u0005C\u0002\u001d\u0012!!\u0011\u0019\u0006\t}2\u0007a]\u0003\u0005\u000f\u001a\u0004\u0011.\u0001\u0007tKF4\u0016.Z<JgN+\u0017/\u0006\u0003{\u007f\u0006EQ#A>\u0013\u0005qlh\u0001B4\u0006\u0001m\u00042\u0001\t\u0001\u007f!\u0011!s0a\u0004\u0005\r-T!\u0019AA\u0001+\u0011\t\u0019!!\u0004\u0012\u0007!\n)\u0001E\u0003:\u0003\u000f\tY!C\u0002\u0002\nQ\u0011qaU3r-&,w\u000fE\u0002%\u0003\u001b!Q!]@C\u0002\u001d\u00022\u0001JA\t\t\u0015)(B1\u0001(\u000b\u0015yD\u0010AA\b\u000b\u00159E\u0010AA\f!\u0015I\u0014\u0011DA\b\u0013\r\tY\u0002\u0006\u0002\u0005-&,w/A\u0006tiJLgnZ%t'\u0016\fXCAA\u0011%\u0011\t\u0019#!\n\u0007\u000b\u001d,\u0001!!\t\u0011\t\u0001\u0002\u0011q\u0005\t\u0005\u0003S\t9D\u0004\u0003\u0002,\u0005M\u0002cAA\u0017-5\u0011\u0011q\u0006\u0006\u0004\u0003cA\u0012A\u0002\u001fs_>$h(C\u0002\u00026Y\ta\u0001\u0015:fI\u00164\u0017\u0002BA\u001d\u0003w\u0011aa\u0015;sS:<'bAA\u001b-\u00151q(a\t!\u0003\u007f\u00012\u0001HA!\u0013\r\t\u0019E\u0006\u0002\u0005\u0007\"\f'/\u0002\u0004H\u0003G\u0001\u0013qE\u0001\rgR\u0014\u0018N\\4JgN+\u0017\u000fI\u0001\u0010gR\u0014\u0018N\\4WS\u0016<\u0018j]*fcV\u0011\u0011Q\n\n\u0005\u0003\u001f\n\tFB\u0003h\u000b\u0001\ti\u0005\u0005\u0003!\u0001\u0005M\u0003cA\u001d\u0002V%\u0019\u0011q\u000b\u000b\u0003\u0015M#(/\u001b8h-&,w/\u0002\u0004@\u0003\u001f\u0002\u0013qH\u0003\u0007\u000f\u0006=\u0003%!\u0018\u0011\u000be\nI\"a\u0010\u0002!M$(/\u001b8h-&,w/S:TKF\u0004\u0013AC1se\u0006L\u0018j]*fcV!\u0011QMA;)\u0011\t9'a\u001f\u0013\t\u0005%\u00141\u000e\u0004\u0006O\u0016\u0001\u0011q\r\t\u0005A\u0001\ti\u0007E\u0003\u001d\u0003_\n\u0019(C\u0002\u0002rY\u0011Q!\u0011:sCf\u00042\u0001JA;\t\u0015)xB1\u0001(\u000b\u0019y\u0014\u0011\u000e\u0001\u0002t\u00151q)!\u001b\u0001\u0003[B\u0011\"! \u0010\u0003\u0003\u0005\u001d!a \u0002\u0015\u00154\u0018\u000eZ3oG\u0016$\u0013\u0007\u0005\u0004\u0002\u0002\u0006\u001d\u00151O\u0007\u0003\u0003\u0007S1!!\"\u0017\u0003\u001d\u0011XM\u001a7fGRLA!!#\u0002\u0004\nA1\t\\1tgR\u000bw-\u0001\u0006sC:<W-S:TKF,B!a$\u0002\u001aV\u0011\u0011\u0011\u0013\n\u0005\u0003'\u000b)JB\u0003h\u000b\u0001\t\t\n\u0005\u0003!\u0001\u0005]\u0005c\u0001\u0013\u0002\u001a\u00129\u00111\u0014\tC\u0002\u0005u%AA\"1#\rA\u0013q\u0014\t\u0005\u0003C\u000bYK\u0004\u0003\u0002$\u0006\u001df\u0002BA\u0017\u0003KK\u0011aF\u0005\u0004\u0003S3\u0012a\u00029bG.\fw-Z\u0005\u0005\u0003[\u000byKA\u0003SC:<WMC\u0002\u0002*Z)aaPAJ\u0001\u0005M\u0006c\u0001\u000f\u00026&\u0019\u0011q\u0017\f\u0003\u0007%sG/\u0002\u0004H\u0003'\u0003\u00111\u0018\t\u0007\u0003{\u000b\u0019-a-\u000e\u0005\u0005}&bAAa)\u0005I\u0011.\\7vi\u0006\u0014G.Z\u0005\u0005\u0003\u000b\fyL\u0001\u0006J]\u0012,\u00070\u001a3TKF\u0004")
public interface IsSeq<Repr>
extends IsIterable<Repr> {
    public static <C0 extends Range> IsSeq<C0> rangeIsSeq() {
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

    public static <A0> IsSeq<Object> arrayIsSeq(ClassTag<A0> evidence$1) {
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
                return new SeqOps<A0, ArraySeq, Object>(this, a){
                    private final /* synthetic */ $anon$6 $outer;
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

                    public IterableFactory<ArraySeq> companion() {
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

                    public WithFilter<A0, ArraySeq> withFilter(Function1<A0, Object> p) {
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

                    public <K$, B> scala.collection.immutable.Map<K$, ArraySeq<B>> groupMap(Function1<A0, K$> key, Function1<A0, B> f) {
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

                    public <A1, A2> Tuple2<ArraySeq<A1>, ArraySeq<A2>> partitionMap(Function1<A0, Either<A1, A2>> f) {
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

                    public <A1, A2> Tuple2<ArraySeq<A1>, ArraySeq<A2>> unzip(Function1<A0, Tuple2<A1, A2>> asPair) {
                        return IterableOps.unzip$(this, asPair);
                    }

                    public <A1, A2, A3> Tuple3<ArraySeq<A1>, ArraySeq<A2>, ArraySeq<A3>> unzip3(Function1<A0, Tuple3<A1, A2, A3>> asTriple) {
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

                    public IterableFactory<ArraySeq> iterableFactory() {
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

    public static IsSeq<StringView> stringViewIsSeq() {
        return IsSeq$.MODULE$.stringViewIsSeq();
    }

    public static IsSeq<String> stringIsSeq() {
        return IsSeq$.MODULE$.stringIsSeq();
    }

    public static <CC0 extends SeqView<Object>, A0> IsSeq<CC0> seqViewIsSeq() {
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

    public static <CC0 extends SeqOps<Object, Iterable, CC0>, A0> IsSeq<CC0> seqOpsIsSeq() {
        return IsSeq$.MODULE$.seqOpsIsSeq();
    }

    public void scala$collection$generic$IsSeq$_setter_$conversion_$eq(Function1<Repr, SeqOps<Object, Iterable, Object>> var1);

    @Override
    public Function1<Repr, SeqOps<Object, Iterable, Object>> conversion();

    public SeqOps<Object, Iterable, Object> apply(Repr var1);

    public static void $init$(IsSeq $this) {
        $this.scala$collection$generic$IsSeq$_setter_$conversion_$eq((Function1<Object, SeqOps> & Serializable)x$1 -> $this.apply(x$1));
    }
}

