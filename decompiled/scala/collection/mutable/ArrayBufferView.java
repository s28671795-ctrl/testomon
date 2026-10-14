/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.collection.AbstractIndexedSeqView;
import scala.collection.IndexedSeqOps;
import scala.collection.IndexedSeqView;
import scala.collection.Iterator;
import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.CheckedIndexedSeqView;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005-h\u0001\u0002\r\u001a\u0005\u0001B\u0001B\r\u0001\u0003\u0002\u0003\u0006Ia\r\u0005\to\u0001\u0011\t\u0011)A\u0005q!1a\b\u0001C\u00013}BQA\u0010\u0001\u0005\u0002\rCQ!\u0012\u0001\u0005\u0002aCQ\u0001\u0018\u0001\u0005\u0002uCa!\u0014\u0001\u0005\u0002\u0005U\u0001\u0002CA\f\u0001\u0001&\t&!\u0007\t\u000f\u0005%\u0002\u0001\"\u0011\u0002,!9\u00111\u0007\u0001\u0005B\u0005-\u0002bBA\u001b\u0001\u0011\u0005\u0013q\u0007\u0005\b\u0003\u001b\u0002A\u0011IA(\u0011\u001d\tY\u0006\u0001C!\u0003;Bq!a\u0019\u0001\t\u0003\n)\u0007C\u0004\u0002j\u0001!\t%a\u001b\t\u000f\u0005=\u0004\u0001\"\u0011\u0002r!9\u0011Q\u000f\u0001\u0005B\u0005]\u0004bBAF\u0001\u0011\u0005\u0013Q\u0012\u0005\b\u0003\u001f\u0003A\u0011IAI\u0011\u001d\tY\n\u0001C!\u0003;Cq!a+\u0001\t\u0003\ni\u000bC\u0004\u0002J\u0002!\t%a3\t\u000f\u0005e\u0007\u0001\"\u0011\u0002\\\ny\u0011I\u001d:bs\n+hMZ3s-&,wO\u0003\u0002\u001b7\u00059Q.\u001e;bE2,'B\u0001\u000f\u001e\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002=\u0005)1oY1mC\u000e\u0001QCA\u0011)'\t\u0001!\u0005E\u0002$I\u0019j\u0011aG\u0005\u0003Km\u0011a#\u00112tiJ\f7\r^%oI\u0016DX\rZ*fcZKWm\u001e\t\u0003O!b\u0001\u0001B\u0003*\u0001\t\u0007!FA\u0001B#\tYs\u0006\u0005\u0002-[5\tQ$\u0003\u0002/;\t9aj\u001c;iS:<\u0007C\u0001\u00171\u0013\t\tTDA\u0002B]f\f!\"\u001e8eKJd\u00170\u001b8h!\r!TGJ\u0007\u00023%\u0011a'\u0007\u0002\f\u0003J\u0014\u0018-\u001f\"vM\u001a,'/A\u0007nkR\fG/[8o\u0007>,h\u000e\u001e\t\u0004YeZ\u0014B\u0001\u001e\u001e\u0005%1UO\\2uS>t\u0007\u0007\u0005\u0002-y%\u0011Q(\b\u0002\u0004\u0013:$\u0018A\u0002\u001fj]&$h\bF\u0002A\u0003\n\u00032\u0001\u000e\u0001'\u0011\u0015\u00114\u00011\u00014\u0011\u001594\u00011\u00019)\r\u0001E\t\u0014\u0005\u0006\u000b\u0012\u0001\rAR\u0001\u0006CJ\u0014\u0018-\u001f\t\u0004Y\u001dK\u0015B\u0001%\u001e\u0005\u0015\t%O]1z!\ta#*\u0003\u0002L;\t1\u0011I\\=SK\u001aDQ!\u0014\u0003A\u0002m\na\u0001\\3oORD\u0007F\u0002\u0003P%N+f\u000b\u0005\u0002-!&\u0011\u0011+\b\u0002\u000bI\u0016\u0004(/Z2bi\u0016$\u0017aB7fgN\fw-Z\u0011\u0002)\u0006Qd.\u001a<fe\u0002Jg\u000e^3oI\u0016$\u0007\u0005^8!E\u0016\u0004\u0003/\u001e2mS\u000e\\\u0004eY1mY\u0002\n%O]1z\u0005V4g-\u001a:$m&,w\u000fI5ogR,\u0017\rZ\u0001\u0006g&t7-Z\u0011\u0002/\u00061!GL\u00194]]*\u0012A\u0012\u0015\u0007\u000b=\u0013&,\u0016,\"\u0003m\u000b1D\\3wKJ\u0004\u0013N\u001c;f]\u0012,G\r\t;pA\t,\u0007\u0005];cY&\u001c\u0017!B1qa2LHC\u0001\u0014_\u0011\u0015yf\u00011\u0001<\u0003\u0005q\u0007f\u0001\u0004bWB\u0019AF\u00193\n\u0005\rl\"A\u0002;ie><8\u000f\u0005\u0002fQ:\u0011AFZ\u0005\u0003Ov\tq\u0001]1dW\u0006<W-\u0003\u0002jU\nI\u0012J\u001c3fq>+Ho\u00144C_VtGm]#yG\u0016\u0004H/[8o\u0015\t9W$M\u0003\u001fY^\f\u0019\u0002\u0005\u0002ni:\u0011aN\u001d\t\u0003_vi\u0011\u0001\u001d\u0006\u0003c~\ta\u0001\u0010:p_Rt\u0014BA:\u001e\u0003\u0019\u0001&/\u001a3fM&\u0011QO\u001e\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005Ml\u0012GB\u0012yy\u0006%Q0\u0006\u0002zuV\tA\u000e\u0002\u0004|?\t\u0007\u0011\u0011\u0001\u0002\u0002)&\u0011QP`\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u0019\u000b\u0005}l\u0012A\u0002;ie><8/E\u0002,\u0003\u0007\u00012!ZA\u0003\u0013\r\t9A\u001b\u0002\n)\"\u0014xn^1cY\u0016\f\u0004bIA\u0006\u0003\u001b\tya \b\u0004Y\u00055\u0011BA@\u001ec\u0015\u0011C&HA\t\u0005\u0015\u00198-\u00197bc\t1C-F\u0001<\u0003%\u0019G.Y:t\u001d\u0006lW-\u0006\u0002\u0002\u001cA!\u0011QDA\u0014\u001b\t\tyB\u0003\u0003\u0002\"\u0005\r\u0012\u0001\u00027b]\u001eT!!!\n\u0002\t)\fg/Y\u0005\u0004k\u0006}\u0011\u0001C5uKJ\fGo\u001c:\u0016\u0005\u00055\u0002\u0003B\u0012\u00020\u0019J1!!\r\u001c\u0005!IE/\u001a:bi>\u0014\u0018a\u0004:fm\u0016\u00148/Z%uKJ\fGo\u001c:\u0002\u0011\u0005\u0004\b/\u001a8eK\u0012,B!!\u000f\u0002DQ!\u00111HA%!\u0015\u0019\u0013QHA!\u0013\r\tyd\u0007\u0002\u000f\u0013:$W\r_3e'\u0016\fh+[3x!\r9\u00131\t\u0003\b\u0003\u000bZ!\u0019AA$\u0005\u0005\u0011\u0015C\u0001\u00140\u0011\u001d\tYe\u0003a\u0001\u0003\u0003\nA!\u001a7f[\u0006I\u0001O]3qK:$W\rZ\u000b\u0005\u0003#\n9\u0006\u0006\u0003\u0002T\u0005e\u0003#B\u0012\u0002>\u0005U\u0003cA\u0014\u0002X\u00119\u0011Q\t\u0007C\u0002\u0005\u001d\u0003bBA&\u0019\u0001\u0007\u0011QK\u0001\u0005i\u0006\\W\r\u0006\u0003\u0002`\u0005\u0005\u0004\u0003B\u0012\u0002>\u0019BQaX\u0007A\u0002m\n\u0011\u0002^1lKJKw\r\u001b;\u0015\t\u0005}\u0013q\r\u0005\u0006?:\u0001\raO\u0001\u0005IJ|\u0007\u000f\u0006\u0003\u0002`\u00055\u0004\"B0\u0010\u0001\u0004Y\u0014!\u00033s_B\u0014\u0016n\u001a5u)\u0011\ty&a\u001d\t\u000b}\u0003\u0002\u0019A\u001e\u0002\u00075\f\u0007/\u0006\u0003\u0002z\u0005}D\u0003BA>\u0003\u0003\u0003RaIA\u001f\u0003{\u00022aJA@\t\u0019\t)%\u0005b\u0001U!9\u00111Q\tA\u0002\u0005\u0015\u0015!\u00014\u0011\r1\n9IJA?\u0013\r\tI)\b\u0002\n\rVt7\r^5p]F\nqA]3wKJ\u001cX-\u0006\u0002\u0002`\u0005)1\u000f\\5dKR1\u0011qLAJ\u0003/Ca!!&\u0014\u0001\u0004Y\u0014\u0001\u00024s_6Da!!'\u0014\u0001\u0004Y\u0014!B;oi&d\u0017a\u0002;ba\u0016\u000b7\r[\u000b\u0005\u0003?\u000b9\u000b\u0006\u0003\u0002`\u0005\u0005\u0006bBAB)\u0001\u0007\u00111\u0015\t\u0007Y\u0005\u001de%!*\u0011\u0007\u001d\n9\u000b\u0002\u0004\u0002*R\u0011\rA\u000b\u0002\u0002+\u000611m\u001c8dCR,B!a,\u00026R!\u0011\u0011WA\\!\u0015\u0019\u0013QHAZ!\r9\u0013Q\u0017\u0003\b\u0003\u000b*\"\u0019AA$\u0011\u001d\tI,\u0006a\u0001\u0003w\u000baa];gM&D\bCBA_\u0003\u0007\f\u0019LD\u0002$\u0003\u007fK1!!1\u001c\u00039Ie\u000eZ3yK\u0012\u001cV-\u001d,jK^LA!!2\u0002H\n\t2k\\7f\u0013:$W\r_3e'\u0016\fx\n]:\u000b\u0007\u0005\u00057$A\u0006baB,g\u000eZ3e\u00032dW\u0003BAg\u0003'$B!a4\u0002VB)1%!\u0010\u0002RB\u0019q%a5\u0005\u000f\u0005\u0015cC1\u0001\u0002H!9\u0011\u0011\u0018\fA\u0002\u0005]\u0007CBA_\u0003\u0007\f\t.\u0001\u0007qe\u0016\u0004XM\u001c3fI\u0006cG.\u0006\u0003\u0002^\u0006\rH\u0003BAp\u0003K\u0004RaIA\u001f\u0003C\u00042aJAr\t\u001d\t)e\u0006b\u0001\u0003\u000fBq!a:\u0018\u0001\u0004\tI/\u0001\u0004qe\u00164\u0017\u000e\u001f\t\u0007\u0003{\u000b\u0019-!9")
public final class ArrayBufferView<A>
extends AbstractIndexedSeqView<A> {
    private final ArrayBuffer<A> underlying;
    private final Function0<Object> mutationCount;

    public Object[] array() {
        return (Object[])this.underlying.toArray(ClassTag$.MODULE$.Any());
    }

    @Override
    public A apply(int n) throws IndexOutOfBoundsException {
        return this.underlying.apply(n);
    }

    @Override
    public int length() {
        return this.underlying.length();
    }

    @Override
    public String className() {
        return "ArrayBufferView";
    }

    @Override
    public Iterator<A> iterator() {
        return new CheckedIndexedSeqView.CheckedIterator(this, this.mutationCount);
    }

    @Override
    public Iterator<A> reverseIterator() {
        return new CheckedIndexedSeqView.CheckedReverseIterator(this, this.mutationCount);
    }

    @Override
    public <B> IndexedSeqView<B> appended(B elem) {
        return new CheckedIndexedSeqView.Appended<B>(this, elem, this.mutationCount);
    }

    @Override
    public <B> IndexedSeqView<B> prepended(B elem) {
        return new CheckedIndexedSeqView.Prepended<B>(elem, this, this.mutationCount);
    }

    @Override
    public IndexedSeqView<A> take(int n) {
        return new CheckedIndexedSeqView.Take(this, n, this.mutationCount);
    }

    @Override
    public IndexedSeqView<A> takeRight(int n) {
        return new CheckedIndexedSeqView.TakeRight(this, n, this.mutationCount);
    }

    @Override
    public IndexedSeqView<A> drop(int n) {
        return new CheckedIndexedSeqView.Drop(this, n, this.mutationCount);
    }

    @Override
    public IndexedSeqView<A> dropRight(int n) {
        return new CheckedIndexedSeqView.DropRight(this, n, this.mutationCount);
    }

    @Override
    public <B> IndexedSeqView<B> map(Function1<A, B> f) {
        return new CheckedIndexedSeqView.Map<A, B>(this, f, this.mutationCount);
    }

    @Override
    public IndexedSeqView<A> reverse() {
        return new CheckedIndexedSeqView.Reverse(this, this.mutationCount);
    }

    @Override
    public IndexedSeqView<A> slice(int from, int until) {
        return new CheckedIndexedSeqView.Slice(this, from, until, this.mutationCount);
    }

    @Override
    public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
        return new CheckedIndexedSeqView.Map(this, (Function1<Object, Object> & Serializable)a -> {
            f.apply(a);
            return a;
        }, this.mutationCount);
    }

    @Override
    public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
        return new CheckedIndexedSeqView.Concat<B>(this, suffix, this.mutationCount);
    }

    @Override
    public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
        return new CheckedIndexedSeqView.Concat<B>(this, suffix, this.mutationCount);
    }

    @Override
    public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
        return new CheckedIndexedSeqView.Concat<B>(prefix, this, this.mutationCount);
    }

    public ArrayBufferView(ArrayBuffer<A> underlying, Function0<Object> mutationCount) {
        this.underlying = underlying;
        this.mutationCount = mutationCount;
    }

    public ArrayBufferView(Object[] array, int length) {
        this(new ArrayBuffer<A>(array, length){
            {
                this.array_$eq(_array$1);
                this.size0_$eq(_length$1);
            }
        }, (Function0<Object>)((Object)new Serializable(){
            private static final long serialVersionUID = 0L;

            public final int apply() {
                return 0;
            }

            public int apply$mcI$sp() {
                return 0;
            }
        }));
    }
}

