/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.lang.reflect.Array;
import scala.$less$colon$less;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.BufferedIterator;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001de\u0001\u0002\u0011\"\r!B\u0001\"\u0012\u0001\u0003\u0002\u0003\u0006IA\u0012\u0005\t\u0015\u0002\u0011\t\u0011)Q\u0005\u0017\"Aa\n\u0001B\u0001B\u0003%1\nC\u0003P\u0001\u0011\u0005\u0001\u000b\u0003\u0004V\u0001\u0001\u0006KA\u0016\u0005\nM\u0002\u0001\r\u0011!Q!\n\u001dD\u0011B\u001b\u0001A\u0002\u0003\u0005\u000b\u0015B6\t\u00139\u0004\u0001\u0019!A!B\u0013y\u0007\"\u0003:\u0001\u0001\u0004\u0005\t\u0015)\u0003t\u0011%1\b\u00011A\u0001B\u0003&q\u000f\u0003\u0004{\u0001\u0001\u0006Ka\u0013\u0005\rw\u0002!\t\u0011!B\u0001\u0002\u0003\u0006Ka\u0013\u0005\u0007y\u0002\u0001\u000b\u0015B&\t\u0019u\u0004A\u0011!A\u0003\u0002\u0003\u0005\u000b\u0015B&\t\ry\u0004\u0001\u0015)\u0003L\u0011\u0019y\b\u0001)Q\u0005\u0017\"9\u0011\u0011\u0001\u0001!B\u0013Y\u0005bBA\u0002\u0001\u0001\u0006Ka\u0013\u0005\b\u0003\u000b\u0001A\u0011IA\u0004\u0011\u001d\t\t\u0002\u0001C\u0001\u0003'Aq!!\b\u0001\t\u0003\ty\u0002\u0003\u0005\u0002\"\u0001\u0001K\u0011BA\u0012\u0011!\tY\u0003\u0001Q\u0005\n\u0005\r\u0002\u0002CA\u0017\u0001\u0001&I!a\f\t\u0011\u0005e\u0002\u0001)C\u0005\u0003wAq!!\u0011\u0001\t\u0003\n\u0019\u0005C\u0004\u0002J\u0001!\t%a\u0013\t\u000f\u0005=\u0003\u0001\"\u0011\u0002R!9\u00111\f\u0001\u0005B\u0005u\u0003bBA>\u0001\u0011\u0005\u0013Q\u0010\u0005\t\u0003\u007f\u0002A\u0011C\u0011\u0002\u0002\n\tb*Z<WK\u000e$xN]%uKJ\fGo\u001c:\u000b\u0005\t\u001a\u0013!C5n[V$\u0018M\u00197f\u0015\t!S%\u0001\u0006d_2dWm\u0019;j_:T\u0011AJ\u0001\u0006g\u000e\fG.Y\u0002\u0001+\tICg\u0005\u0003\u0001U9j\u0004CA\u0016-\u001b\u0005)\u0013BA\u0017&\u0005\u0019\te.\u001f*fMB\u0019q\u0006\r\u001a\u000e\u0003\rJ!!M\u0012\u0003\u0011%#XM]1u_J\u0004\"a\r\u001b\r\u0001\u0011)Q\u0007\u0001b\u0001m\t\t\u0011)\u0005\u00028uA\u00111\u0006O\u0005\u0003s\u0015\u0012qAT8uQ&tw\r\u0005\u0002,w%\u0011A(\n\u0002\u0004\u0003:L\bC\u0001 D\u001b\u0005y$B\u0001!B\u0003\u0011a\u0017M\\4\u000b\u0003\t\u000bAA[1wC&\u0011Ai\u0010\u0002\n\u00072|g.Z1cY\u0016\f\u0011A\u001e\t\u0004\u000f\"\u0013T\"A\u0011\n\u0005%\u000b#A\u0002,fGR|'/A\u0006u_R\fG\u000eT3oORD\u0007CA\u0016M\u0013\tiUEA\u0002J]R\f!b\u001d7jG\u0016\u001cu.\u001e8u\u0003\u0019a\u0014N\\5u}Q!\u0011KU*U!\r9\u0005A\r\u0005\u0006\u000b\u0012\u0001\rA\u0012\u0005\u0006\u0015\u0012\u0001\ra\u0013\u0005\u0006\u001d\u0012\u0001\raS\u0001\u0003CF\u0002\"aV2\u000f\u0005a\u000bgBA-a\u001d\tQvL\u0004\u0002\\=6\tAL\u0003\u0002^O\u00051AH]8pizJ\u0011AJ\u0005\u0003I\u0015J!AI\u0012\n\u0005\t\f\u0013\u0001\u0004,fGR|'/\u00138mS:,\u0017B\u00013f\u0005\u0011\t%O]\u0019\u000b\u0005\t\f\u0013AA13!\t9\u0006.\u0003\u0002jK\n!\u0011I\u001d:3\u0003\t\t7\u0007\u0005\u0002XY&\u0011Q.\u001a\u0002\u0005\u0003J\u00148'\u0001\u0002biA\u0011q\u000b]\u0005\u0003c\u0016\u0014A!\u0011:si\u0005\u0011\u0011-\u000e\t\u0003/RL!!^3\u0003\t\u0005\u0013(/N\u0001\u0003CZ\u0002\"a\u0016=\n\u0005e,'\u0001B!seZ\nQ!Y\u0019mK:\f\u0001g]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%S6lW\u000f^1cY\u0016$c*Z<WK\u000e$xN]%uKJ\fGo\u001c:%I%\f\u0014AB8mIB{7/\u0001\u001atG\u0006d\u0017\rJ2pY2,7\r^5p]\u0012JW.\\;uC\ndW\r\n(foZ+7\r^8s\u0013R,'/\u0019;pe\u0012\"C.\u001a82\u0003!\u0019H.[2f\u0013\u0012D\u0018\u0001C:mS\u000e,G)[7\u0002\u0015Md\u0017nY3Ti\u0006\u0014H/\u0001\u0005tY&\u001cW-\u00128e\u0003%Ygn\\<o'&TX-F\u0001LQ\r\u0019\u00121\u0002\t\u0004W\u00055\u0011bAA\bK\t1\u0011N\u001c7j]\u0016\fq\u0001[1t\u001d\u0016DH/\u0006\u0002\u0002\u0016A\u00191&a\u0006\n\u0007\u0005eQEA\u0004C_>dW-\u00198)\u0007Q\tY!\u0001\u0003oKb$H#\u0001\u001a\u0002\u0019\u0005$g/\u00198dKNc\u0017nY3\u0015\u0005\u0005\u0015\u0002cA\u0016\u0002(%\u0019\u0011\u0011F\u0013\u0003\tUs\u0017\u000e^\u0001\bC\u00124\u0018M\\2f\u0003!\tGM^1oG\u0016\fECBA\u0013\u0003c\t)\u0004\u0003\u0004\u00024a\u0001\raS\u0001\u0003S>Da!a\u000e\u0019\u0001\u0004Y\u0015a\u0001=pe\u0006!1/\u001a;B)\u0019\t)#!\u0010\u0002@!1\u00111G\rA\u0002-Ca!a\u000e\u001a\u0001\u0004Y\u0015\u0001\u00023s_B$2ALA#\u0011\u0019\t9E\u0007a\u0001\u0017\u0006\ta.\u0001\u0003uC.,Gc\u0001\u0018\u0002N!1\u0011qI\u000eA\u0002-\u000bQa\u001d7jG\u0016$RALA*\u0003/Ba!!\u0016\u001d\u0001\u0004Y\u0015\u0001\u00024s_6Da!!\u0017\u001d\u0001\u0004Y\u0015!B;oi&d\u0017aC2paf$v.\u0011:sCf,B!a\u0018\u0002nQ91*!\u0019\u0002t\u0005]\u0004bBA2;\u0001\u0007\u0011QM\u0001\u0003qN\u0004RaKA4\u0003WJ1!!\u001b&\u0005\u0015\t%O]1z!\r\u0019\u0014Q\u000e\u0003\b\u0003_j\"\u0019AA9\u0005\u0005\u0011\u0015C\u0001\u001a;\u0011\u0019\t)(\ba\u0001\u0017\u0006)1\u000f^1si\"1\u0011\u0011P\u000fA\u0002-\u000b1\u0001\\3o\u0003!!xNV3di>\u0014X#\u0001$\u0002\u000bM\u0004H.\u001b;\u0015\u0007E\u000b\u0019\t\u0003\u0004\u0002\u0006~\u0001\raS\u0001\u0003CR\u0004")
public final class NewVectorIterator<A>
implements Iterator<A>,
Cloneable {
    private final Vector<A> v;
    private int totalLength;
    private final int sliceCount;
    private Object[] a1;
    private Object[][] a2;
    private Object[][][] a3;
    private Object[][][][] a4;
    private Object[][][][][] a5;
    private Object[][][][][][] a6;
    private int a1len;
    public int scala$collection$immutable$NewVectorIterator$$i1;
    private int oldPos;
    public int scala$collection$immutable$NewVectorIterator$$len1;
    private int sliceIdx;
    private int sliceDim;
    private int sliceStart;
    private int sliceEnd;

    @Override
    public final boolean hasDefiniteSize() {
        return Iterator.hasDefiniteSize$(this);
    }

    @Override
    public final Iterator<A> iterator() {
        return Iterator.iterator$(this);
    }

    @Override
    public Option<A> nextOption() {
        return Iterator.nextOption$(this);
    }

    @Override
    public boolean contains(Object elem) {
        return Iterator.contains$(this, elem);
    }

    @Override
    public BufferedIterator<A> buffered() {
        return Iterator.buffered$(this);
    }

    @Override
    public <B> Iterator<B> padTo(int len, B elem) {
        return Iterator.padTo$(this, len, elem);
    }

    @Override
    public Tuple2<Iterator<A>, Iterator<A>> partition(Function1<A, Object> p) {
        return Iterator.partition$(this, p);
    }

    @Override
    public <B> Iterator.GroupedIterator<B> grouped(int size) {
        return Iterator.grouped$(this, size);
    }

    @Override
    public <B> Iterator.GroupedIterator<B> sliding(int size, int step) {
        return Iterator.sliding$(this, size, step);
    }

    @Override
    public <B> int sliding$default$2() {
        return Iterator.sliding$default$2$(this);
    }

    @Override
    public <B> Iterator<B> scanLeft(B z, Function2<B, A, B> op) {
        return Iterator.scanLeft$(this, z, op);
    }

    @Override
    public <B> Iterator<B> scanRight(B z, Function2<A, B, B> op) {
        return Iterator.scanRight$(this, z, op);
    }

    @Override
    public int indexWhere(Function1<A, Object> p, int from) {
        return Iterator.indexWhere$(this, p, from);
    }

    @Override
    public int indexWhere$default$2() {
        return Iterator.indexWhere$default$2$(this);
    }

    @Override
    public <B> int indexOf(B elem) {
        return Iterator.indexOf$(this, elem);
    }

    @Override
    public <B> int indexOf(B elem, int from) {
        return Iterator.indexOf$(this, elem, from);
    }

    @Override
    public final int length() {
        return Iterator.length$(this);
    }

    @Override
    public boolean isEmpty() {
        return Iterator.isEmpty$(this);
    }

    @Override
    public Iterator<A> filter(Function1<A, Object> p) {
        return Iterator.filter$(this, p);
    }

    @Override
    public Iterator<A> filterNot(Function1<A, Object> p) {
        return Iterator.filterNot$(this, p);
    }

    @Override
    public Iterator<A> filterImpl(Function1<A, Object> p, boolean isFlipped) {
        return Iterator.filterImpl$(this, p, isFlipped);
    }

    @Override
    public Iterator<A> withFilter(Function1<A, Object> p) {
        return Iterator.withFilter$(this, p);
    }

    @Override
    public <B> Iterator<B> collect(PartialFunction<A, B> pf) {
        return Iterator.collect$(this, pf);
    }

    @Override
    public Iterator<A> distinct() {
        return Iterator.distinct$(this);
    }

    @Override
    public <B> Iterator<A> distinctBy(Function1<A, B> f) {
        return Iterator.distinctBy$(this, f);
    }

    @Override
    public <B> Iterator<B> map(Function1<A, B> f) {
        return Iterator.map$(this, f);
    }

    @Override
    public <B> Iterator<B> flatMap(Function1<A, IterableOnce<B>> f) {
        return Iterator.flatMap$(this, f);
    }

    @Override
    public <B> Iterator<B> flatten(Function1<A, IterableOnce<B>> ev) {
        return Iterator.flatten$(this, ev);
    }

    @Override
    public <B> Iterator<B> concat(Function0<IterableOnce<B>> xs) {
        return Iterator.concat$(this, xs);
    }

    @Override
    public final <B> Iterator<B> $plus$plus(Function0<IterableOnce<B>> xs) {
        return Iterator.$plus$plus$(this, xs);
    }

    @Override
    public Iterator<A> takeWhile(Function1<A, Object> p) {
        return Iterator.takeWhile$(this, p);
    }

    @Override
    public Iterator<A> dropWhile(Function1<A, Object> p) {
        return Iterator.dropWhile$(this, p);
    }

    @Override
    public Tuple2<Iterator<A>, Iterator<A>> span(Function1<A, Object> p) {
        return Iterator.span$(this, p);
    }

    @Override
    public Iterator<A> sliceIterator(int from, int until) {
        return Iterator.sliceIterator$(this, from, until);
    }

    @Override
    public <B> Iterator<Tuple2<A, B>> zip(IterableOnce<B> that) {
        return Iterator.zip$(this, that);
    }

    @Override
    public <A1, B> Iterator<Tuple2<A1, B>> zipAll(IterableOnce<B> that, A1 thisElem, B thatElem) {
        return Iterator.zipAll$(this, that, thisElem, thatElem);
    }

    @Override
    public Iterator<Tuple2<A, Object>> zipWithIndex() {
        return Iterator.zipWithIndex$(this);
    }

    @Override
    public <B> boolean sameElements(IterableOnce<B> that) {
        return Iterator.sameElements$(this, that);
    }

    @Override
    public Tuple2<Iterator<A>, Iterator<A>> duplicate() {
        return Iterator.duplicate$(this);
    }

    @Override
    public <B> Iterator<B> patch(int from, Iterator<B> patchElems, int replaced) {
        return Iterator.patch$(this, from, patchElems, replaced);
    }

    @Override
    public <U> Iterator<A> tapEach(Function1<A, U> f) {
        return Iterator.tapEach$(this, f);
    }

    @Override
    public String toString() {
        return Iterator.toString$(this);
    }

    @Override
    public Iterator<A> seq() {
        return Iterator.seq$(this);
    }

    @Override
    public Tuple2<Iterator<A>, Iterator<A>> splitAt(int n) {
        return IterableOnceOps.splitAt$(this, n);
    }

    @Override
    public boolean isTraversableAgain() {
        return IterableOnceOps.isTraversableAgain$(this);
    }

    @Override
    public <U> void foreach(Function1<A, U> f) {
        IterableOnceOps.foreach$(this, f);
    }

    @Override
    public boolean forall(Function1<A, Object> p) {
        return IterableOnceOps.forall$(this, p);
    }

    @Override
    public boolean exists(Function1<A, Object> p) {
        return IterableOnceOps.exists$(this, p);
    }

    @Override
    public int count(Function1<A, Object> p) {
        return IterableOnceOps.count$(this, p);
    }

    @Override
    public Option<A> find(Function1<A, Object> p) {
        return IterableOnceOps.find$(this, p);
    }

    @Override
    public <B> B foldLeft(B z, Function2<B, A, B> op) {
        return (B)IterableOnceOps.foldLeft$(this, z, op);
    }

    @Override
    public <B> B foldRight(B z, Function2<A, B, B> op) {
        return (B)IterableOnceOps.foldRight$(this, z, op);
    }

    @Override
    public final <B> B $div$colon(B z, Function2<B, A, B> op) {
        return (B)IterableOnceOps.$div$colon$(this, z, op);
    }

    @Override
    public final <B> B $colon$bslash(B z, Function2<A, B, B> op) {
        return (B)IterableOnceOps.$colon$bslash$(this, z, op);
    }

    @Override
    public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
        return (A1)IterableOnceOps.fold$(this, z, op);
    }

    @Override
    public <B> B reduce(Function2<B, B, B> op) {
        return (B)IterableOnceOps.reduce$(this, op);
    }

    @Override
    public <B> Option<B> reduceOption(Function2<B, B, B> op) {
        return IterableOnceOps.reduceOption$(this, op);
    }

    @Override
    public <B> B reduceLeft(Function2<B, A, B> op) {
        return (B)IterableOnceOps.reduceLeft$(this, op);
    }

    @Override
    public <B> B reduceRight(Function2<A, B, B> op) {
        return (B)IterableOnceOps.reduceRight$(this, op);
    }

    @Override
    public <B> Option<B> reduceLeftOption(Function2<B, A, B> op) {
        return IterableOnceOps.reduceLeftOption$(this, op);
    }

    @Override
    public <B> Option<B> reduceRightOption(Function2<A, B, B> op) {
        return IterableOnceOps.reduceRightOption$(this, op);
    }

    @Override
    public boolean nonEmpty() {
        return IterableOnceOps.nonEmpty$(this);
    }

    @Override
    public int size() {
        return IterableOnceOps.size$(this);
    }

    @Override
    public final <B> void copyToBuffer(Buffer<B> dest) {
        IterableOnceOps.copyToBuffer$(this, dest);
    }

    @Override
    public <B> int copyToArray(Object xs) {
        return IterableOnceOps.copyToArray$(this, xs);
    }

    @Override
    public <B> int copyToArray(Object xs, int start2) {
        return IterableOnceOps.copyToArray$(this, xs, start2);
    }

    @Override
    public <B> B sum(Numeric<B> num) {
        return (B)IterableOnceOps.sum$(this, num);
    }

    @Override
    public <B> B product(Numeric<B> num) {
        return (B)IterableOnceOps.product$(this, num);
    }

    @Override
    public <B> A min(Ordering<B> ord) {
        return (A)IterableOnceOps.min$(this, ord);
    }

    @Override
    public <B> Option<A> minOption(Ordering<B> ord) {
        return IterableOnceOps.minOption$(this, ord);
    }

    @Override
    public <B> A max(Ordering<B> ord) {
        return (A)IterableOnceOps.max$(this, ord);
    }

    @Override
    public <B> Option<A> maxOption(Ordering<B> ord) {
        return IterableOnceOps.maxOption$(this, ord);
    }

    @Override
    public <B> A maxBy(Function1<A, B> f, Ordering<B> cmp) {
        return (A)IterableOnceOps.maxBy$(this, f, cmp);
    }

    @Override
    public <B> Option<A> maxByOption(Function1<A, B> f, Ordering<B> cmp) {
        return IterableOnceOps.maxByOption$(this, f, cmp);
    }

    @Override
    public <B> A minBy(Function1<A, B> f, Ordering<B> cmp) {
        return (A)IterableOnceOps.minBy$(this, f, cmp);
    }

    @Override
    public <B> Option<A> minByOption(Function1<A, B> f, Ordering<B> cmp) {
        return IterableOnceOps.minByOption$(this, f, cmp);
    }

    @Override
    public <B> Option<B> collectFirst(PartialFunction<A, B> pf) {
        return IterableOnceOps.collectFirst$(this, pf);
    }

    @Override
    public <B> B aggregate(Function0<B> z, Function2<B, A, B> seqop, Function2<B, B, B> combop) {
        return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
    }

    @Override
    public <B> boolean corresponds(IterableOnce<B> that, Function2<A, B, Object> p) {
        return IterableOnceOps.corresponds$(this, that, p);
    }

    @Override
    public final String mkString(String start2, String sep, String end) {
        return IterableOnceOps.mkString$(this, start2, sep, end);
    }

    @Override
    public final String mkString(String sep) {
        return IterableOnceOps.mkString$(this, sep);
    }

    @Override
    public final String mkString() {
        return IterableOnceOps.mkString$(this);
    }

    @Override
    public StringBuilder addString(StringBuilder b, String start2, String sep, String end) {
        return IterableOnceOps.addString$(this, b, start2, sep, end);
    }

    @Override
    public final StringBuilder addString(StringBuilder b, String sep) {
        return IterableOnceOps.addString$(this, b, sep);
    }

    @Override
    public final StringBuilder addString(StringBuilder b) {
        return IterableOnceOps.addString$(this, b);
    }

    @Override
    public <C1> C1 to(Factory<A, C1> factory2) {
        return (C1)IterableOnceOps.to$(this, factory2);
    }

    @Override
    public final Iterator<A> toIterator() {
        return IterableOnceOps.toIterator$(this);
    }

    @Override
    public List<A> toList() {
        return IterableOnceOps.toList$(this);
    }

    @Override
    public <K$, V$> Map<K$, V$> toMap($less$colon$less<A, Tuple2<K$, V$>> ev) {
        return IterableOnceOps.toMap$(this, ev);
    }

    @Override
    public <B> Set<B> toSet() {
        return IterableOnceOps.toSet$(this);
    }

    @Override
    public Seq<A> toSeq() {
        return IterableOnceOps.toSeq$(this);
    }

    @Override
    public IndexedSeq<A> toIndexedSeq() {
        return IterableOnceOps.toIndexedSeq$(this);
    }

    @Override
    public final Stream<A> toStream() {
        return IterableOnceOps.toStream$(this);
    }

    @Override
    public final <B> Buffer<B> toBuffer() {
        return IterableOnceOps.toBuffer$(this);
    }

    @Override
    public <B> Object toArray(ClassTag<B> evidence$2) {
        return IterableOnceOps.toArray$(this, evidence$2);
    }

    @Override
    public Iterable<A> reversed() {
        return IterableOnceOps.reversed$(this);
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
        return (S)IterableOnce.stepper$(this, shape);
    }

    @Override
    public int knownSize() {
        return this.scala$collection$immutable$NewVectorIterator$$len1 - this.scala$collection$immutable$NewVectorIterator$$i1;
    }

    @Override
    public boolean hasNext() {
        return this.scala$collection$immutable$NewVectorIterator$$len1 > this.scala$collection$immutable$NewVectorIterator$$i1;
    }

    @Override
    public A next() {
        if (this.scala$collection$immutable$NewVectorIterator$$i1 == this.a1len) {
            this.advance();
        }
        Object r = this.a1[this.scala$collection$immutable$NewVectorIterator$$i1];
        ++this.scala$collection$immutable$NewVectorIterator$$i1;
        return (A)r;
    }

    private void advanceSlice() {
        if (!(this.scala$collection$immutable$NewVectorIterator$$len1 > this.scala$collection$immutable$NewVectorIterator$$i1)) {
            Iterator$.scala$collection$Iterator$$_empty.next();
        }
        ++this.sliceIdx;
        Object[] slice = this.v.vectorSlice(this.sliceIdx);
        while (slice.length == 0) {
            ++this.sliceIdx;
            slice = this.v.vectorSlice(this.sliceIdx);
        }
        this.sliceStart = this.sliceEnd;
        int vectorSliceDim_idx = this.sliceIdx;
        int vectorSliceDim_c = this.sliceCount / 2;
        int n = this.sliceDim = vectorSliceDim_c + 1 - Math.abs(vectorSliceDim_idx - vectorSliceDim_c);
        switch (n) {
            case 1: {
                this.a1 = slice;
                break;
            }
            case 2: {
                this.a2 = (Object[][])slice;
                break;
            }
            case 3: {
                this.a3 = (Object[][][])slice;
                break;
            }
            case 4: {
                this.a4 = (Object[][][][])slice;
                break;
            }
            case 5: {
                this.a5 = (Object[][][][][])slice;
                break;
            }
            case 6: {
                this.a6 = (Object[][][][][][])slice;
                break;
            }
            default: {
                throw new MatchError(n);
            }
        }
        this.sliceEnd = this.sliceStart + slice.length * (1 << 5 * (this.sliceDim - 1));
        if (this.sliceEnd > this.totalLength) {
            this.sliceEnd = this.totalLength;
        }
        if (this.sliceDim > 1) {
            this.oldPos = (1 << 5 * this.sliceDim) - 1;
            return;
        }
    }

    private void advance() {
        int pos = this.scala$collection$immutable$NewVectorIterator$$i1 - this.scala$collection$immutable$NewVectorIterator$$len1 + this.totalLength;
        if (pos == this.sliceEnd) {
            this.advanceSlice();
        }
        if (this.sliceDim > 1) {
            int io = pos - this.sliceStart;
            int xor = this.oldPos ^ io;
            this.advanceA(io, xor);
            this.oldPos = io;
        }
        this.scala$collection$immutable$NewVectorIterator$$len1 -= this.scala$collection$immutable$NewVectorIterator$$i1;
        this.a1len = Math.min(this.a1.length, this.scala$collection$immutable$NewVectorIterator$$len1);
        this.scala$collection$immutable$NewVectorIterator$$i1 = 0;
    }

    private void advanceA(int io, int xor) {
        if (xor < 1024) {
            this.a1 = this.a2[io >>> 5 & 0x1F];
            return;
        }
        if (xor < 32768) {
            this.a2 = this.a3[io >>> 10 & 0x1F];
            this.a1 = this.a2[0];
            return;
        }
        if (xor < 0x100000) {
            this.a3 = this.a4[io >>> 15 & 0x1F];
            this.a2 = this.a3[0];
            this.a1 = this.a2[0];
            return;
        }
        if (xor < 0x2000000) {
            this.a4 = this.a5[io >>> 20 & 0x1F];
            this.a3 = this.a4[0];
            this.a2 = this.a3[0];
            this.a1 = this.a2[0];
            return;
        }
        this.a5 = this.a6[io >>> 25];
        this.a4 = this.a5[0];
        this.a3 = this.a4[0];
        this.a2 = this.a3[0];
        this.a1 = this.a2[0];
    }

    private void setA(int io, int xor) {
        if (xor < 1024) {
            this.a1 = this.a2[io >>> 5 & 0x1F];
            return;
        }
        if (xor < 32768) {
            this.a2 = this.a3[io >>> 10 & 0x1F];
            this.a1 = this.a2[io >>> 5 & 0x1F];
            return;
        }
        if (xor < 0x100000) {
            this.a3 = this.a4[io >>> 15 & 0x1F];
            this.a2 = this.a3[io >>> 10 & 0x1F];
            this.a1 = this.a2[io >>> 5 & 0x1F];
            return;
        }
        if (xor < 0x2000000) {
            this.a4 = this.a5[io >>> 20 & 0x1F];
            this.a3 = this.a4[io >>> 15 & 0x1F];
            this.a2 = this.a3[io >>> 10 & 0x1F];
            this.a1 = this.a2[io >>> 5 & 0x1F];
            return;
        }
        this.a5 = this.a6[io >>> 25];
        this.a4 = this.a5[io >>> 20 & 0x1F];
        this.a3 = this.a4[io >>> 15 & 0x1F];
        this.a2 = this.a3[io >>> 10 & 0x1F];
        this.a1 = this.a2[io >>> 5 & 0x1F];
    }

    @Override
    public Iterator<A> drop(int n) {
        if (n > 0) {
            int newpos = Math.min(this.scala$collection$immutable$NewVectorIterator$$i1 - this.scala$collection$immutable$NewVectorIterator$$len1 + this.totalLength + n, this.totalLength);
            if (newpos == this.totalLength) {
                this.scala$collection$immutable$NewVectorIterator$$i1 = 0;
                this.scala$collection$immutable$NewVectorIterator$$len1 = 0;
                this.a1len = 0;
            } else {
                while (newpos >= this.sliceEnd) {
                    this.advanceSlice();
                }
                int io = newpos - this.sliceStart;
                if (this.sliceDim > 1) {
                    int xor = this.oldPos ^ io;
                    this.setA(io, xor);
                    this.oldPos = io;
                }
                this.a1len = this.a1.length;
                this.scala$collection$immutable$NewVectorIterator$$i1 = io & 0x1F;
                this.scala$collection$immutable$NewVectorIterator$$len1 = this.scala$collection$immutable$NewVectorIterator$$i1 + (this.totalLength - newpos);
                if (this.a1len > this.scala$collection$immutable$NewVectorIterator$$len1) {
                    this.a1len = this.scala$collection$immutable$NewVectorIterator$$len1;
                }
            }
        }
        return this;
    }

    @Override
    public Iterator<A> take(int n) {
        if (n < this.scala$collection$immutable$NewVectorIterator$$len1 - this.scala$collection$immutable$NewVectorIterator$$i1) {
            int trunc = this.scala$collection$immutable$NewVectorIterator$$len1 - this.scala$collection$immutable$NewVectorIterator$$i1 - Math.max(0, n);
            this.totalLength -= trunc;
            this.scala$collection$immutable$NewVectorIterator$$len1 -= trunc;
            if (this.scala$collection$immutable$NewVectorIterator$$len1 < this.a1len) {
                this.a1len = this.scala$collection$immutable$NewVectorIterator$$len1;
            }
            if (this.totalLength < this.sliceEnd) {
                this.sliceEnd = this.totalLength;
            }
        }
        return this;
    }

    @Override
    public Iterator<A> slice(int from, int until) {
        int n;
        if (from > 0) {
            this.drop(from);
            n = until - from;
        } else {
            n = until;
        }
        int _until = n;
        return this.take(_until);
    }

    @Override
    public <B> int copyToArray(Object xs, int start2, int len) {
        int count;
        int xsLen = Array.getLength(xs);
        int elemsToCopyToArray_srcLen = this.scala$collection$immutable$NewVectorIterator$$len1 - this.scala$collection$immutable$NewVectorIterator$$i1;
        int total = Math.max(Math.min(Math.min(len, elemsToCopyToArray_srcLen), xsLen - start2), 0);
        boolean isBoxed = xs instanceof Object[];
        for (int copied = 0; copied < total; copied += count) {
            if (this.scala$collection$immutable$NewVectorIterator$$i1 == this.a1len) {
                this.advance();
            }
            count = Math.min(total - copied, this.a1.length - this.scala$collection$immutable$NewVectorIterator$$i1);
            if (isBoxed) {
                System.arraycopy(this.a1, this.scala$collection$immutable$NewVectorIterator$$i1, xs, start2 + copied, count);
            } else {
                Array$.MODULE$.copy(this.a1, this.scala$collection$immutable$NewVectorIterator$$i1, xs, start2 + copied, count);
            }
            this.scala$collection$immutable$NewVectorIterator$$i1 += count;
        }
        return total;
    }

    @Override
    public Vector<A> toVector() {
        return (Vector)this.v.slice(this.scala$collection$immutable$NewVectorIterator$$i1 - this.scala$collection$immutable$NewVectorIterator$$len1 + this.totalLength, this.totalLength);
    }

    public NewVectorIterator<A> split(int at) {
        NewVectorIterator it2 = (NewVectorIterator)this.clone();
        it2.take(at);
        this.drop(at);
        return it2;
    }

    public NewVectorIterator(Vector<A> v, int totalLength, int sliceCount) {
        this.v = v;
        this.totalLength = totalLength;
        this.sliceCount = sliceCount;
        this.a1 = v.prefix1();
        this.a1len = this.a1.length;
        this.scala$collection$immutable$NewVectorIterator$$i1 = 0;
        this.oldPos = 0;
        this.scala$collection$immutable$NewVectorIterator$$len1 = this.totalLength;
        this.sliceIdx = 0;
        this.sliceDim = 1;
        this.sliceStart = 0;
        this.sliceEnd = this.a1len;
    }
}

