/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.FrequencySketch$;
import akka.util.FrequencySketch$Bits$;
import akka.util.FrequencySketchUtil$;
import java.io.Serializable;
import scala.Array$;
import scala.Function0;
import scala.Predef$;
import scala.collection.SetOps;
import scala.collection.immutable.Seq;
import scala.math.Ordering$Int$;
import scala.math.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.RichInt$;
import scala.runtime.ScalaRunTime$;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\t\rrAB\u001d;\u0011\u0003adH\u0002\u0004Au!\u0005A(\u0011\u0005\u0006\u0011\u0006!\tA\u0013\u0005\u0006\u0017\u0006!\t\u0001\u0014\u0005\n\u0003\u001f\f\u0011\u0013!C\u0001\u0003#D\u0011\"!;\u0002#\u0003%\t!a;\t\u0013\u0005M\u0018!%A\u0005\u0002\u0005U\b\"CA}\u0003E\u0005I\u0011AA~\r\u001da\u0016\u0001%A\u0012\"uCQa\u0018\u0005\u0007\u0002\u0001<a!a@\u0002\u0011\u0003\u0011h!\u0002/\u0002\u0011\u0003\u0001\b\"\u0002%\f\t\u0003\t\bb\u0002;\f\u0005\u0004%)!\u001e\u0005\u0007q.\u0001\u000bQ\u0002<\t\u000fe\\!\u0019!C\u0002u\"1Qp\u0003Q\u0001\nm4Aa\\\u0006\u0003}\"I\u0011qC\t\u0003\u0002\u0003\u0006Ia\u0015\u0005\u0007\u0011F!\t!!\u0007\t\r}\u000bB\u0011IA\u000f\u000f\u001d\u0011\t!\u0001E\u0001\u0005\u00071qA!\u0002\u0002\u0011\u0003\u00119\u0001\u0003\u0004I-\u0011\u0005!\u0011\u0002\u0005\b\u0005\u00171B\u0011\u0001B\u0007\u0011\u001d\u0011\u0019B\u0006C\u0001\u0005+AqA!\u0007\u0017\t\u0003\u0011YBB\u0003Au\ta\u0004\u000b\u0003\u0005S7\t\u0005\t\u0015!\u0003T\u0011!16D!A!\u0002\u0013\u0019\u0006\u0002C,\u001c\u0005\u0003\u0005\u000b\u0011B*\t\u0011a[\"\u0011!Q\u0001\nMC\u0001\"W\u000e\u0003\u0002\u0003\u0006IA\u0017\u0005\u0007\u0011n!\t!!\n\t\u0013\u0005M2D1A\u0005\u000e\u0005U\u0002\u0002CA\u001e7\u0001\u0006i!a\u000e\t\u000f\u0005u2\u0004)A\u0005'\"9\u0011qH\u000e!\u0002\u0013\u0019\u0006bBA!7\u0001\u0006Ia\u0015\u0005\b\u0003\u0007Z\u0002\u0015!\u0003T\u0011\u001d\t)e\u0007Q\u0001\nMCq!a\u0012\u001cA\u0003%1\u000bC\u0004\u0002Jm\u0001\u000b\u0011B*\t\u0011\u0005-3\u0004)A\u0005\u0003\u001bB\u0001\"a\u0015\u001cA\u0003%\u0011Q\n\u0005\t\u0003+Z\u0002\u0015!\u0003\u0002N!A\u0011qK\u000e!\u0002\u0013\tI\u0006\u0003\u0005\u0002bm\u0001\u000b\u0011BA2\u0011\u001d\t)g\u0007Q!\nMCq!a\u001a\u001c\t\u0003\tI\u0007C\u0004\u0002lm!\t!!\u001c\t\u000f\u0005E4\u0004\"\u0001\u0002t!9\u0011QP\u000e\u0005\n\u0005}\u0004bBAB7\u0011%\u0011Q\u0011\u0005\b\u0003\u001b[B\u0011BAH\u0011\u001d\tYj\u0007C\u0005\u0003;Cq!a(\u001c\t\u0003\t\t+A\bGe\u0016\fX/\u001a8dsN[W\r^2i\u0015\tYD(\u0001\u0003vi&d'\"A\u001f\u0002\t\u0005\\7.\u0019\t\u0003\u007f\u0005i\u0011A\u000f\u0002\u0010\rJ,\u0017/^3oGf\u001c6.\u001a;dQN\u0011\u0011A\u0011\t\u0003\u0007\u001ak\u0011\u0001\u0012\u0006\u0002\u000b\u0006)1oY1mC&\u0011q\t\u0012\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012AP\u0001\u0006CB\u0004H._\u000b\u0004\u001b\u0006MFc\u0003(\u0002:\u0006u\u0016\u0011YAf\u0003\u001b$2aTA[!\u0011y4$!-\u0016\u0007E\u000b\u0019c\u0005\u0002\u001c\u0005\u0006)A-\u001a9uQB\u00111\tV\u0005\u0003+\u0012\u00131!\u00138u\u0003\u00159\u0018\u000e\u001a;i\u0003-\u0019w.\u001e8uKJ\u0014\u0015\u000e^:\u0002\u0013I,7/\u001a;TSj,\u0017A\u00025bg\",'\u000f\u0005\u0003\\\u0011\u0005\u0005bBA \u0001\u0005\u0019A\u0015m\u001d5feV\u0011a,Z\n\u0003\u0011\t\u000bA\u0001[1tQR\u00111+\u0019\u0005\u0006E&\u0001\raY\u0001\u0006m\u0006dW/\u001a\t\u0003I\u0016d\u0001\u0001B\u0003g\u0011\t\u0007qMA\u0001B#\tA7\u000e\u0005\u0002DS&\u0011!\u000e\u0012\u0002\b\u001d>$\b.\u001b8h!\t\u0019E.\u0003\u0002n\t\n\u0019\u0011I\\=*\u0005!\t\"\u0001D*ue&tw\rS1tQ\u0016\u00148CA\u0006C)\u0005\u0011\bCA:\f\u001b\u0005\t\u0011a\u0003#fM\u0006,H\u000e^*fK\u0012,\u0012A^\b\u0002ov!\u0001\u0002\u0005\"&\u00031!UMZ1vYR\u001cV-\u001a3!\u00031\u0019FO]5oO\"\u000b7\u000f[3s+\u0005Y\bC\u0001?\u0012\u001b\u0005Y\u0011!D*ue&tw\rS1tQ\u0016\u0014\beE\u0002\u0012\u0005~\u0004Ba\u001d\u0005\u0002\u0002A!\u00111AA\t\u001d\u0011\t)!!\u0004\u0011\u0007\u0005\u001dA)\u0004\u0002\u0002\n)\u0019\u00111B%\u0002\rq\u0012xn\u001c;?\u0013\r\ty\u0001R\u0001\u0007!J,G-\u001a4\n\t\u0005M\u0011Q\u0003\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005=A)\u0001\u0003tK\u0016$GcA>\u0002\u001c!1\u0011qC\nA\u0002M#2aUA\u0010\u0011\u0019\u0011G\u00031\u0001\u0002\u0002A\u0019A-a\t\u0005\u000b\u0019\\\"\u0019A4\u0015\u0019\u0005\u001d\u0012\u0011FA\u0016\u0003[\ty#!\r\u0011\t}Z\u0012\u0011\u0005\u0005\u0006%\u0006\u0002\ra\u0015\u0005\u0006-\u0006\u0002\ra\u0015\u0005\u0006/\u0006\u0002\ra\u0015\u0005\u00061\u0006\u0002\ra\u0015\u0005\u00063\u0006\u0002\rAW\u0001\t'2|GOQ5ugV\u0011\u0011qG\b\u0003\u0003si\u0012\u0001Q\u0001\n'2|GOQ5ug\u0002\nAbY8v]R,'oV5ei\"\fQa\u001d7piN\f\u0001B]8x/&$G\u000f[\u0001\u000bG>dW/\u001c8NCN\\\u0017!C:m_R\u001c\u0006.\u001b4u\u0003!\u0019Hn\u001c;NCN\\\u0017\u0001D2pk:$XM]*iS\u001a$\u0018aC2pk:$XM]'bg.\u00042aQA(\u0013\r\t\t\u0006\u0012\u0002\u0005\u0019>tw-A\u0004pI\u0012l\u0015m]6\u0002\u0013I,7/\u001a;NCN\\\u0017AB7biJL\u0007\u0010E\u0003D\u00037\ny&C\u0002\u0002^\u0011\u0013Q!\u0011:sCf\u0004RaQA.\u0003\u001b\n\u0001B]8x'&TXm\u001d\t\u0005\u0007\u0006m3+A\u0006va\u0012\fG/\u001a3TSj,\u0017\u0001B:ju\u0016,\u0012aU\u0001\nMJ,\u0017/^3oGf$2aUA8\u0011\u0019\u0011'\u00071\u0001\u0002\"\u0005I\u0011N\\2sK6,g\u000e\u001e\u000b\u0005\u0003k\nY\bE\u0002D\u0003oJ1!!\u001fE\u0005\u0011)f.\u001b;\t\r\t\u001c\u0004\u0019AA\u0011\u0003\u0019\u0011X\r[1tQR\u00191+!!\t\u000b}#\u0004\u0019A*\u0002\u0015\u001d,GoQ8v]R,'\u000fF\u0003T\u0003\u000f\u000bY\t\u0003\u0004\u0002\nV\u0002\raU\u0001\u0004e><\b\"B06\u0001\u0004\u0019\u0016\u0001E5oGJ,W.\u001a8u\u0007>,h\u000e^3s)\u0019\t\t*a&\u0002\u001aB\u00191)a%\n\u0007\u0005UEIA\u0004C_>dW-\u00198\t\r\u0005%e\u00071\u0001T\u0011\u0015yf\u00071\u0001T\u0003\u0015\u0011Xm]3u)\t\t)(A\u0007u_\u0012+'-^4TiJLgnZ\u000b\u0003\u0003\u0003A3aGAS!\u0011\t9+!,\u000e\u0005\u0005%&bAAVy\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005=\u0016\u0011\u0016\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000eE\u0002e\u0003g#QAZ\u0002C\u0002\u001dDa!W\u0002A\u0004\u0005]\u0006\u0003B:\t\u0003cCa!a/\u0004\u0001\u0004\u0019\u0016\u0001C2ba\u0006\u001c\u0017\u000e^=\t\u0011\u0005}6\u0001%AA\u0002M\u000bqb^5ei\"lU\u000f\u001c;ja2LWM\u001d\u0005\n\u0003\u0007\u001c\u0001\u0013!a\u0001\u0003\u000b\fqB]3tKRlU\u000f\u001c;ja2LWM\u001d\t\u0004\u0007\u0006\u001d\u0017bAAe\t\n1Ai\\;cY\u0016DqAU\u0002\u0011\u0002\u0003\u00071\u000bC\u0004X\u0007A\u0005\t\u0019A*\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uII*B!a5\u0002hV\u0011\u0011Q\u001b\u0016\u0004'\u0006]7FAAm!\u0011\tY.a9\u000e\u0005\u0005u'\u0002BAp\u0003C\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005-F)\u0003\u0003\u0002f\u0006u'!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0012)a\r\u0002b\u0001O\u0006y\u0011\r\u001d9ms\u0012\"WMZ1vYR$3'\u0006\u0003\u0002n\u0006EXCAAxU\u0011\t)-a6\u0005\u000b\u0019,!\u0019A4\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIQ*B!a5\u0002x\u0012)aM\u0002b\u0001O\u0006y\u0011\r\u001d9ms\u0012\"WMZ1vYR$S'\u0006\u0003\u0002T\u0006uH!\u00024\b\u0005\u00049\u0017A\u0002%bg\",'/\u0001\u0003CSR\u001c\bCA:\u0017\u0005\u0011\u0011\u0015\u000e^:\u0014\u0005Y\u0011EC\u0001B\u0002\u00031I7\u000fU8xKJ|e\rV<p)\u0011\t\tJa\u0004\t\r\tE\u0001\u00041\u0001T\u0003\u0005I\u0017A\u00059po\u0016\u0014xJ\u001a+x_\u0016C\bo\u001c8f]R$2a\u0015B\f\u0011\u0019\u0011\t\"\u0007a\u0001'\u0006\t2-Z5mS:<\u0007k\\<fe>3Gk^8\u0015\u0007M\u0013i\u0002\u0003\u0004\u0003\u0012i\u0001\ra\u0015\u0015\u0004\u0003\u0005\u0015\u0006f\u0001\u0001\u0002&\u0002")
@InternalApi
public final class FrequencySketch<A> {
    private final int depth;
    private final int resetSize;
    private final Hasher<A> hasher;
    private final int counterWidth;
    private final int slots;
    private final int rowWidth;
    private final int columnMask;
    private final int slotShift;
    private final int slotMask;
    private final int counterShift;
    private final long counterMask;
    private final long oddMask;
    private final long resetMask;
    private final long[][] matrix;
    private final int[] rowSizes;
    private int updatedSize;

    public static <A> int apply$default$5() {
        return FrequencySketch$.MODULE$.apply$default$5();
    }

    public static <A> int apply$default$4() {
        return FrequencySketch$.MODULE$.apply$default$4();
    }

    public static <A> double apply$default$3() {
        return FrequencySketch$.MODULE$.apply$default$3();
    }

    public static <A> int apply$default$2() {
        return FrequencySketch$.MODULE$.apply$default$2();
    }

    public static <A> FrequencySketch<A> apply(int capacity, int widthMultiplier, double resetMultiplier, int depth, int counterBits, Hasher<A> hasher) {
        return FrequencySketch$.MODULE$.apply(capacity, widthMultiplier, resetMultiplier, depth, counterBits, hasher);
    }

    private final int SlotBits() {
        return 64;
    }

    public int size() {
        return this.updatedSize;
    }

    public int frequency(A value) {
        int hash1 = this.hasher.hash(value);
        int hash2 = this.rehash(hash1);
        int minCount = Integer.MAX_VALUE;
        for (int row = 0; row < this.depth; ++row) {
            int hash = hash1 + row * hash2;
            minCount = Math.min(minCount, this.getCounter(row, hash));
        }
        return minCount;
    }

    public void increment(A value) {
        int hash1 = this.hasher.hash(value);
        int hash2 = this.rehash(hash1);
        boolean updated = false;
        for (int row = 0; row < this.depth; ++row) {
            int hash = hash1 + row * hash2;
            updated |= this.incrementCounter(row, hash);
        }
        if (updated) {
            ++this.updatedSize;
            if (this.updatedSize == this.resetSize) {
                this.reset();
                return;
            }
            return;
        }
    }

    private int rehash(int hash) {
        return MurmurHash3$.MODULE$.finalizeHash(MurmurHash3$.MODULE$.mixLast(hash, hash), 2);
    }

    private int getCounter(int row, int hash) {
        int column = (hash & this.columnMask) >>> this.slotShift;
        int slot = (hash & this.slotMask) << this.counterShift;
        return (int)(this.matrix[row][column] >>> slot & this.counterMask);
    }

    private boolean incrementCounter(int row, int hash) {
        int column = (hash & this.columnMask) >>> this.slotShift;
        int slot = (hash & this.slotMask) << this.counterShift;
        long mask = this.counterMask << slot;
        if ((this.matrix[row][column] & mask) != mask) {
            long[] lArray = this.matrix[row];
            lArray[column] = lArray[column] + (1L << slot);
            this.rowSizes[row] = this.rowSizes[row] + 1;
            return true;
        }
        return false;
    }

    private void reset() {
        for (int row = 0; row < this.depth; ++row) {
            int odd = 0;
            for (int column = 0; column < this.rowWidth; ++column) {
                odd += Long.bitCount(this.matrix[row][column] & this.oddMask);
                this.matrix[row][column] = this.matrix[row][column] >>> 1 & this.resetMask;
            }
            this.rowSizes[row] = this.rowSizes[row] - odd >>> 1;
        }
        this.updatedSize = BoxesRunTime.unboxToInt(Predef$.MODULE$.wrapIntArray(this.rowSizes).max(Ordering$Int$.MODULE$));
    }

    public String toDebugString() {
        return FrequencySketchUtil$.MODULE$.debugString(this.matrix, this.rowWidth, this.slots, this.counterWidth, this.counterMask);
    }

    public FrequencySketch(int depth, int width, int counterBits, int resetSize, Hasher<A> hasher) {
        this.depth = depth;
        this.resetSize = resetSize;
        this.hasher = hasher;
        Predef$.MODULE$.require(FrequencySketch$Bits$.MODULE$.isPowerOfTwo(width), (Function0<Object>)(Function0<String> & Serializable)() -> "width must be a power of two");
        Predef$.MODULE$.require(((SetOps)Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.wrapIntArray(new int[]{2, 4, 8, 16, 32, 64}))).apply(BoxesRunTime.boxToInteger(counterBits)), (Function0<Object>)(Function0<String> & Serializable)() -> "counterBits must be 2, 4, 8, 16, 32, or 64 bits");
        this.counterWidth = counterBits;
        this.slots = 64 / this.counterWidth;
        this.rowWidth = package$.MODULE$.max(1, width / this.slots);
        this.columnMask = width - 1;
        this.slotShift = FrequencySketch$Bits$.MODULE$.powerOfTwoExponent(this.slots);
        this.slotMask = this.slots - 1;
        this.counterShift = FrequencySketch$Bits$.MODULE$.powerOfTwoExponent(this.counterWidth);
        this.counterMask = counterBits == 64 ? Long.MAX_VALUE : (1L << this.counterWidth) - 1L;
        this.oddMask = BoxesRunTime.unboxToLong(RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), this.slots).foldLeft(BoxesRunTime.boxToLong(1L), (mask, count) -> mask | 1L << count * $this.counterWidth));
        long counterResetMask = this.counterMask >> 1;
        this.resetMask = BoxesRunTime.unboxToLong(RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), this.slots).foldLeft(BoxesRunTime.boxToLong(counterResetMask), (mask, count) -> mask | counterResetMask << count * $this.counterWidth));
        this.matrix = (long[][])Array$.MODULE$.fill(depth, (Function0<long[]> & Serializable)() -> (long[])Array$.MODULE$.ofDim($this.rowWidth, ClassTag$.MODULE$.Long()), ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(Long.TYPE)));
        this.rowSizes = (int[])Array$.MODULE$.ofDim(depth, ClassTag$.MODULE$.Int());
        this.updatedSize = 0;
    }

    public static interface Hasher<A> {
        public int hash(A var1);
    }
}

