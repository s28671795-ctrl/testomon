/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.util.Arrays;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.AbstractIterator;
import scala.collection.BitSet;
import scala.collection.IntStepper;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Set;
import scala.collection.SortedSet;
import scala.collection.SortedSetOps;
import scala.collection.SpecificIterableFactory;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.View;
import scala.collection.convert.impl.BitSetStepper$;
import scala.collection.immutable.Range;
import scala.math.Ordering;
import scala.math.Ordering$Int$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\t\u0015fa\u0002\u00180!\u0003\r\t\u0001\u000e\u0005\u0006'\u0002!\t\u0001\u0016\u0005\u00061\u00021\t!\u0017\u0005\u0006;\u00021\tA\u0018\u0005\u0006E\u0002!)a\u0019\u0005\u0007W\u00021\tb\f7\t\r5\u0004a\u0011C\u0018o\u0011\u0019!\bA\"\u00050k\")1\u0010\u0001C\u0001y\"9\u0011Q\u0001\u0001\u0005\u0002\u0005\u001d\u0001bBA\b\u0001\u0011\u0005\u0011\u0011\u0003\u0005\b\u0003/\u0001A\u0011IA\r\u0011\u0019\t)\u0007\u0001C!Y\"9\u0011q\r\u0001\u0005B\u0005%\u0004bBA6\u0001\u0001&I\u0001\u001c\u0005\b\u0003k\u0002\u0001\u0015\"\u0003m\u0011\u001d\tI\b\u0001C!\u0003wBq!!$\u0001\t\u0003\ny\tC\u0004\u0002\u001c\u0002!\t%!(\t\u000f\u0005E\u0006\u0001\"\u0001\u00024\"9\u0011Q\u0017\u0001\u0005\u0002\u0005]\u0006bBAd\u0001\u0011\u0005\u0013\u0011\u001a\u0005\b\u0003+\u0004A\u0011IAl\u0011!\tY\u000e\u0001I\u0005\u0002\u0005u\u0007bBAq\u0001\u0011\u0005\u00111\u001d\u0005\b\u0003O\u0004AQAAu\u0011\u001d\ty\u000f\u0001C\u0001\u0003cDq!a>\u0001\t\u0003\tI\u0010C\u0004\u0002\u0000\u0002!\tA!\u0001\t\u000f\t5\u0001\u0001\"\u0011\u0003\u0010!q!Q\u0004\u0001\u0011\u0002\u0007\u0005\t\u0011\"\u0003\u0003 \t5\u0002B\u0004B\u0018\u0001A\u0005\u0019\u0011!A\u0005\n\tE\"Q\b\u0005\u000f\u0005\u007f\u0001\u0001\u0013aA\u0001\u0002\u0013%!\u0011\tB$\u00119\u0011i\u0005\u0001I\u0001\u0004\u0003\u0005I\u0011\u0002B(\u0005'BaB!\u0016\u0001!\u0003\r\t\u0011!C\u0005\u0005/\u0012YfB\u0004\u0003^=B\tAa\u0018\u0007\r9z\u0003\u0012\u0001B1\u0011\u001d\u0011\u0019\u0007\nC\u0001\u0005KB!Ba\u001a%\u0005\u0004%)a\fB5\u0011!\u0011y\u0007\nQ\u0001\u000e\t-\u0004B\u0003B9I\t\u0007IQA\u0018\u0003t!A!\u0011\u0010\u0013!\u0002\u001b\u0011)\b\u0003\u0006\u0003|\u0011\u0012\r\u0011\"\u00020\u0005{B\u0001Ba!%A\u00035!q\u0010\u0005\t\u0005\u000b#C\u0011A\u0018\u0003\b\"A!\u0011\u0013\u0013\u0005\u0002=\u0012\u0019JA\u0005CSR\u001cV\r^(qg*\u0011\u0001'M\u0001\u000bG>dG.Z2uS>t'\"\u0001\u001a\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011QGR\n\u0004\u0001YR\u0004CA\u001c9\u001b\u0005\t\u0014BA\u001d2\u0005\u0019\te.\u001f*fMB)1\b\u0010 B\t6\tq&\u0003\u0002>_\ta1k\u001c:uK\u0012\u001cV\r^(qgB\u0011qgP\u0005\u0003\u0001F\u00121!\u00138u!\tY$)\u0003\u0002D_\tI1k\u001c:uK\u0012\u001cV\r\u001e\t\u0003\u000b\u001ac\u0001\u0001\u0002\u0004H\u0001\u0011\u0015\r\u0001\u0013\u0002\u0002\u0007F\u0011\u0011\n\u0014\t\u0003o)K!aS\u0019\u0003\u000f9{G\u000f[5oOJ\u0019Qj\u0014*\u0007\t9\u0003\u0001\u0001\u0014\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\t\u0003wAK!!U\u0018\u0003\r\tKGoU3u!\rY\u0004\u0001R\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003U\u0003\"a\u000e,\n\u0005]\u000b$\u0001B+oSR\fQBY5u'\u0016$h)Y2u_JLX#\u0001.\u0011\tmZf\bR\u0005\u00039>\u0012qc\u00159fG&4\u0017nY%uKJ\f'\r\\3GC\u000e$xN]=\u0002\u0011Ut7o\u001c:uK\u0012,\u0012a\u0018\t\u0004w\u0001t\u0014BA10\u0005\r\u0019V\r^\u0001\t_J$WM]5oOV\tA\rE\u0002fQzr!a\u000e4\n\u0005\u001d\f\u0014a\u00029bG.\fw-Z\u0005\u0003S*\u0014\u0001b\u0014:eKJLgn\u001a\u0006\u0003OF\naA\\<pe\u0012\u001cX#\u0001 \u0002\t]|'\u000f\u001a\u000b\u0003_J\u0004\"a\u000e9\n\u0005E\f$\u0001\u0002'p]\u001eDQa\u001d\u0004A\u0002y\n1!\u001b3y\u0003E1'o\\7CSRl\u0015m]6O_\u000e{\u0007/\u001f\u000b\u0003\tZDQa^\u0004A\u0002a\fQ!\u001a7f[N\u00042aN=p\u0013\tQ\u0018GA\u0003BeJ\f\u00170\u0001\u0005d_:$\u0018-\u001b8t)\ri\u0018\u0011\u0001\t\u0003oyL!a`\u0019\u0003\u000f\t{w\u000e\\3b]\"1\u00111\u0001\u0005A\u0002y\nA!\u001a7f[\u0006A\u0011\u000e^3sCR|'/\u0006\u0002\u0002\nA!1(a\u0003?\u0013\r\tia\f\u0002\t\u0013R,'/\u0019;pe\u0006a\u0011\u000e^3sCR|'O\u0012:p[R!\u0011\u0011BA\n\u0011\u0019\t)B\u0003a\u0001}\u0005)1\u000f^1si\u000691\u000f^3qa\u0016\u0014X\u0003BA\u000e\u0003G!B!!\b\u0002\\I1\u0011qDA\u0011\u0003\u007f1QA\u0014\u0001\u0001\u0003;\u00012!RA\u0012\t\u001d\t)c\u0003b\u0001\u0003O\u0011\u0011aU\t\u0004\u0013\u0006%\u0002\u0007BA\u0016\u0003g\u0001RaOA\u0017\u0003cI1!a\f0\u0005\u001d\u0019F/\u001a9qKJ\u00042!RA\u001a\t1\t)$a\t\u0002\u0002\u0003\u0005)\u0011AA\u001c\u0005\ryF%M\t\u0004\u0013\u0006e\u0002cA\u001c\u0002<%\u0019\u0011QH\u0019\u0003\u0007\u0005s\u0017\u0010\u0005\u0003\u0002B\u0005Uc\u0002BA\"\u0003#rA!!\u0012\u0002P9!\u0011qIA'\u001b\t\tIEC\u0002\u0002LM\na\u0001\u0010:p_Rt\u0014\"\u0001\u001a\n\u0005A\n\u0014bAA*_\u000591\u000b^3qa\u0016\u0014\u0018\u0002BA,\u00033\u0012a\"\u00124gS\u000eLWM\u001c;Ta2LGOC\u0002\u0002T=Bq!!\u0018\f\u0001\b\ty&A\u0003tQ\u0006\u0004X\r\u0005\u0004<\u0003Cr\u0014\u0011E\u0005\u0004\u0003Gz#\u0001D*uKB\u0004XM]*iCB,\u0017\u0001B:ju\u0016\fq![:F[B$\u00180F\u0001~\u0003-\u0019X.\u00197mKN$\u0018J\u001c;)\u00079\ty\u0007E\u00028\u0003cJ1!a\u001d2\u0005\u0019Ig\u000e\\5oK\u0006QA.\u0019:hKN$\u0018J\u001c;)\u0007=\ty'A\u0002nCb,B!! \u0002\bR\u0019a(a \t\u000f\u0005\u0005\u0005\u0003q\u0001\u0002\u0004\u0006\u0019qN\u001d3\u0011\t\u0015D\u0017Q\u0011\t\u0004\u000b\u0006\u001dEaBAE!\t\u0007\u00111\u0012\u0002\u0002\u0005F\u0019a(!\u000f\u0002\u00075Lg.\u0006\u0003\u0002\u0012\u0006eEc\u0001 \u0002\u0014\"9\u0011\u0011Q\tA\u0004\u0005U\u0005\u0003B3i\u0003/\u00032!RAM\t\u001d\tI)\u0005b\u0001\u0003\u0017\u000bqAZ8sK\u0006\u001c\u0007.\u0006\u0003\u0002 \u00065FcA+\u0002\"\"9\u00111\u0015\nA\u0002\u0005\u0015\u0016!\u00014\u0011\r]\n9KPAV\u0013\r\tI+\r\u0002\n\rVt7\r^5p]F\u00022!RAW\t\u001d\tyK\u0005b\u0001\u0003o\u0011\u0011!V\u0001\ni>\u0014\u0015\u000e^'bg.,\u0012\u0001_\u0001\ne\u0006tw-Z%na2$R\u0001RA]\u0003\u0007Dq!a/\u0015\u0001\u0004\ti,\u0001\u0003ge>l\u0007\u0003B\u001c\u0002@zJ1!!12\u0005\u0019y\u0005\u000f^5p]\"9\u0011Q\u0019\u000bA\u0002\u0005u\u0016!B;oi&d\u0017AB2p]\u000e\fG\u000fF\u0002E\u0003\u0017Dq!!4\u0016\u0001\u0004\ty-A\u0003pi\",'\u000f\u0005\u0003<\u0003#t\u0014bAAj_\ta\u0011\n^3sC\ndWm\u00148dK\u0006I\u0011N\u001c;feN,7\r\u001e\u000b\u0004\t\u0006e\u0007BBAg-\u0001\u0007q,\u0001\u0003eS\u001a4Gc\u0001#\u0002`\"1\u0011QZ\fA\u0002}\u000b1\u0001_8s)\r!\u0015Q\u001d\u0005\u0007\u0003\u001bD\u0002\u0019A(\u0002\u0007\u0011*\b\u000fF\u0002E\u0003WDa!!4\u001a\u0001\u0004y\u0005fA\r\u0002p\u0005\u0019Q.\u00199\u0015\u0007\u0011\u000b\u0019\u0010C\u0004\u0002$j\u0001\r!!>\u0011\u000b]\n9K\u0010 \u0002\u000f\u0019d\u0017\r^'baR\u0019A)a?\t\u000f\u0005\r6\u00041\u0001\u0002~B1q'a*?\u0003\u001f\fqaY8mY\u0016\u001cG\u000fF\u0002E\u0005\u0007AqA!\u0002\u001d\u0001\u0004\u00119!\u0001\u0002qMB)qG!\u0003?}%\u0019!1B\u0019\u0003\u001fA\u000b'\u000f^5bY\u001a+hn\u0019;j_:\f\u0011\u0002]1si&$\u0018n\u001c8\u0015\t\tE!q\u0003\t\u0006o\tMA\tR\u0005\u0004\u0005+\t$A\u0002+va2,'\u0007C\u0004\u0003\u001au\u0001\rAa\u0007\u0002\u0003A\u0004RaNAT}u\f\u0011b];qKJ$S.\u0019=\u0016\t\t\u0005\"1\u0006\u000b\u0004}\t\r\u0002bBAA=\u0001\u000f!Q\u0005\t\u0006\u0005OA'\u0011\u0006\b\u0004\u0003\u000b2\u0007cA#\u0003,\u00119\u0011\u0011\u0012\u0010C\u0002\u0005-\u0015bAA=y\u0005I1/\u001e9fe\u0012j\u0017N\\\u000b\u0005\u0005g\u0011Y\u0004F\u0002?\u0005kAq!!! \u0001\b\u00119\u0004E\u0003\u0003(!\u0014I\u0004E\u0002F\u0005w!q!!# \u0005\u0004\tY)C\u0002\u0002\u000er\nAb];qKJ$3m\u001c8dCR$2\u0001\u0012B\"\u0011\u001d\u0011)\u0005\ta\u0001\u0003\u001f\fA\u0001\u001e5bi&!\u0011q\u0019B%\u0013\r\u0011Ye\f\u0002\u0007'\u0016$x\n]:\u0002\u001fM,\b/\u001a:%S:$XM]:fGR$2\u0001\u0012B)\u0011\u0019\u0011)%\ta\u0001?&!\u0011Q\u001bB%\u0003)\u0019X\u000f]3sI\u0011LgM\u001a\u000b\u0004\t\ne\u0003B\u0002B#E\u0001\u0007q,\u0003\u0003\u0002\\\n%\u0013!\u0003\"jiN+Go\u00149t!\tYDe\u0005\u0002%m\u00051A(\u001b8jiz\"\"Aa\u0018\u0002\u000b1{wm\u0016'\u0016\u0005\t-tB\u0001B7;\u00051\u0011A\u0002'pO^c\u0005%\u0001\u0006X_J$G*\u001a8hi\",\"A!\u001e\u0010\u0005\t]T$\u0001!\u0002\u0017]{'\u000f\u001a'f]\u001e$\b\u000eI\u0001\b\u001b\u0006D8+\u001b>f+\t\u0011yh\u0004\u0002\u0003\u0002v!!\u0001\u0001\u0001\u0001\u0003!i\u0015\r_*ju\u0016\u0004\u0013aC;qI\u0006$X-\u0011:sCf$r\u0001\u001fBE\u0005\u0017\u0013i\tC\u0003xY\u0001\u0007\u0001\u0010C\u0003tY\u0001\u0007a\b\u0003\u0004\u0003\u00102\u0002\ra\\\u0001\u0002o\u0006!2m\\7qkR,wk\u001c:e\r>\u0014h)\u001b7uKJ$\u0012b\u001cBK\u00053\u0013iJ!)\t\u000f\t]U\u00061\u0001\u0003\u001c\u0005!\u0001O]3e\u0011\u0019\u0011Y*\fa\u0001{\u0006I\u0011n\u001d$mSB\u0004X\r\u001a\u0005\u0007\u0005?k\u0003\u0019A8\u0002\u000f=dGmV8sI\"1!1U\u0017A\u0002y\n\u0011b^8sI&sG-\u001a=")
public interface BitSetOps<C extends BitSet & BitSetOps<C>>
extends SortedSetOps<Object, SortedSet, C> {
    public /* synthetic */ int scala$collection$BitSetOps$$super$max(Ordering var1);

    public /* synthetic */ int scala$collection$BitSetOps$$super$min(Ordering var1);

    public /* synthetic */ BitSet scala$collection$BitSetOps$$super$concat(IterableOnce var1);

    public /* synthetic */ BitSet scala$collection$BitSetOps$$super$intersect(Set var1);

    public /* synthetic */ BitSet scala$collection$BitSetOps$$super$diff(Set var1);

    public SpecificIterableFactory<Object, C> bitSetFactory();

    @Override
    public Set<Object> unsorted();

    public static /* synthetic */ Ordering ordering$(BitSetOps $this) {
        return $this.ordering();
    }

    @Override
    default public Ordering<Object> ordering() {
        return Ordering$Int$.MODULE$;
    }

    public int nwords();

    public long word(int var1);

    public C fromBitMaskNoCopy(long[] var1);

    public static /* synthetic */ boolean contains$(BitSetOps $this, int elem) {
        return $this.contains(elem);
    }

    @Override
    default public boolean contains(int elem) {
        return 0 <= elem && (this.word(elem >> 6) & 1L << elem) != 0L;
    }

    public static /* synthetic */ Iterator iterator$(BitSetOps $this) {
        return $this.iterator();
    }

    @Override
    default public Iterator<Object> iterator() {
        return this.iteratorFrom(0);
    }

    public static /* synthetic */ Iterator iteratorFrom$(BitSetOps $this, int start2) {
        return $this.iteratorFrom(start2);
    }

    @Override
    default public Iterator<Object> iteratorFrom(int start2) {
        return new AbstractIterator<Object>(this, start2){
            private int currentPos;
            private long currentWord;
            private final /* synthetic */ BitSetOps $outer;

            public final boolean hasNext() {
                while (this.currentWord == 0L) {
                    if (this.currentPos + 1 >= this.$outer.nwords()) {
                        return false;
                    }
                    ++this.currentPos;
                    this.currentWord = this.$outer.word(this.currentPos);
                }
                return true;
            }

            public final int next() {
                if (this.hasNext()) {
                    int bitPos = Long.numberOfTrailingZeros(this.currentWord);
                    this.currentWord &= this.currentWord - 1L;
                    return (this.currentPos << 6) + bitPos;
                }
                return BoxesRunTime.unboxToInt(Iterator$.scala$collection$Iterator$$_empty.next());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.currentPos = start$1 > 0 ? start$1 >> 6 : 0;
                this.currentWord = start$1 > 0 ? $outer.word(this.currentPos) & -1L << (start$1 & 0x3F) : $outer.word(0);
            }
        };
    }

    public static /* synthetic */ Stepper stepper$(BitSetOps $this, StepperShape shape) {
        return $this.stepper(shape);
    }

    @Override
    default public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
        IntStepper st = BitSetStepper$.MODULE$.from(this);
        if (shape.shape() == StepperShape$.MODULE$.IntShape()) {
            return (S)st;
        }
        if (!(shape.shape() == StepperShape$.MODULE$.ReferenceShape())) {
            throw new AssertionError((Object)new StringBuilder(18).append("assertion failed: ").append((Object)BitSetOps.$anonfun$stepper$1(shape)).toString());
        }
        return (S)new Stepper.EfficientSplit(st){};
    }

    public static /* synthetic */ int size$(BitSetOps $this) {
        return $this.size();
    }

    @Override
    default public int size() {
        int s2 = 0;
        int i = this.nwords();
        while (i > 0) {
            s2 += Long.bitCount(this.word(--i));
        }
        return s2;
    }

    public static /* synthetic */ boolean isEmpty$(BitSetOps $this) {
        return $this.isEmpty();
    }

    @Override
    default public boolean isEmpty() {
        int n = 0;
        int until$extension_end = this.nwords();
        return new Range.Exclusive(n, until$extension_end, 1).forall(i -> this.word(i) == 0L);
    }

    private int smallestInt() {
        int thisnwords = this.nwords();
        for (int i = 0; i < thisnwords; ++i) {
            long currentWord = this.word(i);
            if (currentWord == 0L) continue;
            return Long.numberOfTrailingZeros(currentWord) + i * 64;
        }
        throw new UnsupportedOperationException("empty.smallestInt");
    }

    private int largestInt() {
        for (int i = this.nwords() - 1; i >= 0; --i) {
            long currentWord = this.word(i);
            if (currentWord == 0L) continue;
            return (i + 1) * 64 - Long.numberOfLeadingZeros(currentWord) - 1;
        }
        throw new UnsupportedOperationException("empty.largestInt");
    }

    public static /* synthetic */ int max$(BitSetOps $this, Ordering ord) {
        return $this.max(ord);
    }

    @Override
    default public <B> int max(Ordering<B> ord) {
        if (Ordering$Int$.MODULE$ == ord) {
            for (int largestInt_i = this.nwords() - 1; largestInt_i >= 0; --largestInt_i) {
                long largestInt_currentWord = this.word(largestInt_i);
                if (largestInt_currentWord == 0L) continue;
                return (largestInt_i + 1) * 64 - Long.numberOfLeadingZeros(largestInt_currentWord) - 1;
            }
            throw new UnsupportedOperationException("empty.largestInt");
        }
        if (Ordering.CachedReverse.isReverseOf$(Ordering$Int$.MODULE$, ord)) {
            int smallestInt_thisnwords = this.nwords();
            for (int smallestInt_i = 0; smallestInt_i < smallestInt_thisnwords; ++smallestInt_i) {
                long smallestInt_currentWord = this.word(smallestInt_i);
                if (smallestInt_currentWord == 0L) continue;
                return Long.numberOfTrailingZeros(smallestInt_currentWord) + smallestInt_i * 64;
            }
            throw new UnsupportedOperationException("empty.smallestInt");
        }
        return this.scala$collection$BitSetOps$$super$max(ord);
    }

    public static /* synthetic */ int min$(BitSetOps $this, Ordering ord) {
        return $this.min(ord);
    }

    @Override
    default public <B> int min(Ordering<B> ord) {
        if (Ordering$Int$.MODULE$ == ord) {
            int smallestInt_thisnwords = this.nwords();
            for (int smallestInt_i = 0; smallestInt_i < smallestInt_thisnwords; ++smallestInt_i) {
                long smallestInt_currentWord = this.word(smallestInt_i);
                if (smallestInt_currentWord == 0L) continue;
                return Long.numberOfTrailingZeros(smallestInt_currentWord) + smallestInt_i * 64;
            }
            throw new UnsupportedOperationException("empty.smallestInt");
        }
        if (Ordering.CachedReverse.isReverseOf$(Ordering$Int$.MODULE$, ord)) {
            for (int largestInt_i = this.nwords() - 1; largestInt_i >= 0; --largestInt_i) {
                long largestInt_currentWord = this.word(largestInt_i);
                if (largestInt_currentWord == 0L) continue;
                return (largestInt_i + 1) * 64 - Long.numberOfLeadingZeros(largestInt_currentWord) - 1;
            }
            throw new UnsupportedOperationException("empty.largestInt");
        }
        return this.scala$collection$BitSetOps$$super$min(ord);
    }

    public static /* synthetic */ void foreach$(BitSetOps $this, Function1 f) {
        $this.foreach(f);
    }

    @Override
    default public <U> void foreach(Function1<Object, U> f) {
        for (int i = 0; i < this.nwords(); ++i) {
            long w = this.word(i);
            int j = i * 64;
            while (w != 0L) {
                if ((w & 1L) == 1L) {
                    f.apply(j);
                }
                w >>>= 1;
                ++j;
            }
        }
    }

    public static /* synthetic */ long[] toBitMask$(BitSetOps $this) {
        return $this.toBitMask();
    }

    default public long[] toBitMask() {
        long[] a = new long[this.nwords()];
        int i = a.length;
        while (i > 0) {
            a[--i] = this.word(i);
        }
        return a;
    }

    public static /* synthetic */ BitSet rangeImpl$(BitSetOps $this, Option from, Option until) {
        return $this.rangeImpl(from, until);
    }

    @Override
    default public C rangeImpl(Option<Object> from, Option<Object> until) {
        long[] a = ((BitSetOps)this.coll()).toBitMask();
        int len = a.length;
        if (from.isDefined()) {
            int f = BoxesRunTime.unboxToInt(from.get());
            int w = f >> 6;
            int b = f & 0x3F;
            if (w >= 0) {
                Arrays.fill(a, 0, Math.min(w, len), 0L);
                if (b > 0 && w < len) {
                    a[w] = a[w] & ((1L << b) - 1L ^ 0xFFFFFFFFFFFFFFFFL);
                }
            }
        }
        if (until.isDefined()) {
            int u = BoxesRunTime.unboxToInt(until.get());
            int w = u >> 6;
            int b = u & 0x3F;
            if (w < len) {
                Arrays.fill(a, Math.max(w + 1, 0), len, 0L);
                if (w >= 0) {
                    a[w] = a[w] & (1L << b) - 1L;
                }
            }
        }
        return ((BitSetOps)this.coll()).fromBitMaskNoCopy(a);
    }

    public static /* synthetic */ BitSet concat$(BitSetOps $this, IterableOnce other) {
        return $this.concat(other);
    }

    @Override
    default public C concat(IterableOnce<Object> other) {
        if (other instanceof BitSet) {
            BitSet bitSet = (BitSet)other;
            int n = ((BitSetOps)this.coll()).nwords();
            int max$extension_that = bitSet.nwords();
            int len = Math.max(n, max$extension_that);
            long[] words = new long[len];
            int n2 = 0;
            Range.Exclusive foreach$mVc$sp_this = new Range.Exclusive(n2, len, 1);
            if (!foreach$mVc$sp_this.isEmpty()) {
                int foreach$mVc$sp_i = foreach$mVc$sp_this.start();
                while (true) {
                    words[foreach$mVc$sp_i] = this.word(foreach$mVc$sp_i) | bitSet.word(foreach$mVc$sp_i);
                    if (foreach$mVc$sp_i == foreach$mVc$sp_this.scala$collection$immutable$Range$$lastElement) break;
                    foreach$mVc$sp_i += foreach$mVc$sp_this.step();
                }
            }
            Object var7_8 = null;
            return this.fromBitMaskNoCopy(words);
        }
        return (C)this.scala$collection$BitSetOps$$super$concat(other);
    }

    public static /* synthetic */ BitSet intersect$(BitSetOps $this, Set other) {
        return $this.intersect(other);
    }

    @Override
    default public C intersect(Set<Object> other) {
        if (other instanceof BitSet) {
            BitSet bitSet = (BitSet)other;
            int n = ((BitSetOps)this.coll()).nwords();
            int min$extension_that = bitSet.nwords();
            int len = Math.min(n, min$extension_that);
            long[] words = new long[len];
            int n2 = 0;
            Range.Exclusive foreach$mVc$sp_this = new Range.Exclusive(n2, len, 1);
            if (!foreach$mVc$sp_this.isEmpty()) {
                int foreach$mVc$sp_i = foreach$mVc$sp_this.start();
                while (true) {
                    words[foreach$mVc$sp_i] = this.word(foreach$mVc$sp_i) & bitSet.word(foreach$mVc$sp_i);
                    if (foreach$mVc$sp_i == foreach$mVc$sp_this.scala$collection$immutable$Range$$lastElement) break;
                    foreach$mVc$sp_i += foreach$mVc$sp_this.step();
                }
            }
            Object var7_8 = null;
            return this.fromBitMaskNoCopy(words);
        }
        return (C)this.scala$collection$BitSetOps$$super$intersect(other);
    }

    public static /* synthetic */ BitSet diff$(BitSetOps $this, Set other) {
        return $this.diff(other);
    }

    @Override
    default public C diff(Set<Object> other) {
        if (other instanceof BitSet) {
            BitSet bitSet = (BitSet)other;
            int len = ((BitSetOps)this.coll()).nwords();
            long[] words = new long[len];
            int n = 0;
            Range.Exclusive foreach$mVc$sp_this = new Range.Exclusive(n, len, 1);
            if (!foreach$mVc$sp_this.isEmpty()) {
                int foreach$mVc$sp_i = foreach$mVc$sp_this.start();
                while (true) {
                    words[foreach$mVc$sp_i] = this.word(foreach$mVc$sp_i) & (bitSet.word(foreach$mVc$sp_i) ^ 0xFFFFFFFFFFFFFFFFL);
                    if (foreach$mVc$sp_i == foreach$mVc$sp_this.scala$collection$immutable$Range$$lastElement) break;
                    foreach$mVc$sp_i += foreach$mVc$sp_this.step();
                }
            }
            Object var6_6 = null;
            return this.fromBitMaskNoCopy(words);
        }
        return (C)this.scala$collection$BitSetOps$$super$diff(other);
    }

    public static /* synthetic */ BitSet xor$(BitSetOps $this, BitSet other) {
        return $this.xor(other);
    }

    default public C xor(BitSet other) {
        int n = ((BitSetOps)this.coll()).nwords();
        int max$extension_that = other.nwords();
        int len = Math.max(n, max$extension_that);
        long[] words = new long[len];
        int n2 = 0;
        Range.Exclusive foreach$mVc$sp_this = new Range.Exclusive(n2, len, 1);
        if (!foreach$mVc$sp_this.isEmpty()) {
            int foreach$mVc$sp_i = foreach$mVc$sp_this.start();
            while (true) {
                words[foreach$mVc$sp_i] = ((BitSetOps)this.coll()).word(foreach$mVc$sp_i) ^ other.word(foreach$mVc$sp_i);
                if (foreach$mVc$sp_i == foreach$mVc$sp_this.scala$collection$immutable$Range$$lastElement) break;
                foreach$mVc$sp_i += foreach$mVc$sp_this.step();
            }
        }
        Object var6_7 = null;
        return ((BitSetOps)this.coll()).fromBitMaskNoCopy(words);
    }

    public static /* synthetic */ BitSet $up$(BitSetOps $this, BitSet other) {
        return $this.$up(other);
    }

    default public C $up(BitSet other) {
        return this.xor(other);
    }

    public static /* synthetic */ BitSet map$(BitSetOps $this, Function1 f) {
        return $this.map(f);
    }

    @Override
    default public C map(Function1<Object, Object> f) {
        return (C)((BitSet)this.fromSpecific(new View.Map<Object, Object>(this, f)));
    }

    public static /* synthetic */ BitSet flatMap$(BitSetOps $this, Function1 f) {
        return $this.flatMap(f);
    }

    @Override
    default public C flatMap(Function1<Object, IterableOnce<Object>> f) {
        return (C)((BitSet)this.fromSpecific(new View.FlatMap<Object, Object>(this, f)));
    }

    public static /* synthetic */ BitSet collect$(BitSetOps $this, PartialFunction pf) {
        return $this.collect(pf);
    }

    @Override
    default public C collect(PartialFunction<Object, Object> pf) {
        return (C)((BitSet)this.fromSpecific(SortedSetOps.collect$(this, pf, Ordering$Int$.MODULE$)));
    }

    public static /* synthetic */ Tuple2 partition$(BitSetOps $this, Function1 p) {
        return $this.partition(p);
    }

    @Override
    default public Tuple2<C, C> partition(Function1<Object, Object> p) {
        BitSet left = (BitSet)this.filter(p);
        return new Tuple2(left, this.diff(left));
    }

    public static /* synthetic */ String $anonfun$stepper$1(StepperShape shape$1) {
        return new StringBuilder(25).append("unexpected StepperShape: ").append(shape$1).toString();
    }

    public static void $init$(BitSetOps $this) {
    }
}

