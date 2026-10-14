/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.FastFrequencySketch$;
import akka.util.FrequencySketch$Bits$;
import akka.util.FrequencySketchUtil$;
import java.io.Serializable;
import scala.Array$;
import scala.Function0;
import scala.Predef$;
import scala.math.Ordering$Int$;
import scala.math.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005\rxA\u0002\u00180\u0011\u0003\t4G\u0002\u00046_!\u0005\u0011G\u000e\u0005\u0006{\u0005!\ta\u0010\u0005\u0006\u0001\u0006!\t!\u0011\u0005\n\u0003w\u000b\u0011\u0013!C\u0001\u0003{C\u0011\"!6\u0002#\u0003%\t!a6\u0007\u000bUz#!\r#\t\u0011\u00193!\u0011!Q\u0001\n\u001dC\u0001B\u0013\u0004\u0003\u0002\u0003\u0006Ia\u0012\u0005\u0006{\u0019!\ta\u0013\u0005\b5\u001a\u0011\r\u0011\"\u0004\\\u0011\u0019qf\u0001)A\u00079\"9qL\u0002b\u0001\n\u001bY\u0006B\u00021\u0007A\u00035A\fC\u0004b\r\t\u0007IQ\u00022\t\r\u00154\u0001\u0015!\u0004d\u0011\u001d1gA1A\u0005\u000e\u001dDaA\u001b\u0004!\u0002\u001bA\u0007bB6\u0007\u0005\u0004%i\u0001\u001c\u0005\u0007_\u001a\u0001\u000bQB7\t\u000fA4!\u0019!C\u0007c\"1AO\u0002Q\u0001\u000eIDq!\u001e\u0004C\u0002\u00135a\u000f\u0003\u0004z\r\u0001\u0006ia\u001e\u0005\bu\u001a\u0011\r\u0011\"\u0004|\u0011\u0019qh\u0001)A\u0007y\"AqP\u0002b\u0001\n\u001b\t\t\u0001\u0003\u0005\u0002\b\u0019\u0001\u000bQBA\u0002\u0011%\tIA\u0002b\u0001\n\u001b\tY\u0001\u0003\u0005\u0002\u0012\u0019\u0001\u000bQBA\u0007\u0011%\t\u0019B\u0002b\u0001\n\u001b\t)\u0002\u0003\u0005\u0002\u001c\u0019\u0001\u000bQBA\f\u0011\u001d\tiB\u0002Q\u0001\n\u001dCq!a\b\u0007A\u0003%q\t\u0003\u0005\u0002\"\u0019\u0001\u000b\u0011BA\u0012\u0011!\t\tD\u0002Q\u0001\n\u0005M\u0002bBA\u001b\r\u0001\u0006Ka\u0012\u0005\b\u0003o1A\u0011AA\u001d\u0011\u001d\tYD\u0002C\u0001\u0003{Aq!a\u0011\u0007\t\u0003\t)\u0005C\u0004\u0002P\u0019!I!!\u0015\t\u000f\u0005]c\u0001\"\u0003\u0002Z!9\u0011\u0011\r\u0004\u0005\n\u0005\r\u0004bBA6\r\u0011%\u0011Q\u000e\u0005\b\u0003s2A\u0011BA>\u0011\u001d\tiH\u0002C\u0001\u0003\u007f\n1CR1ti\u001a\u0013X-];f]\u000eL8k[3uG\"T!\u0001M\u0019\u0002\tU$\u0018\u000e\u001c\u0006\u0002e\u0005!\u0011m[6b!\t!\u0014!D\u00010\u0005M1\u0015m\u001d;Ge\u0016\fX/\u001a8dsN[W\r^2i'\t\tq\u0007\u0005\u00029w5\t\u0011HC\u0001;\u0003\u0015\u00198-\u00197b\u0013\ta\u0014H\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\t1'A\u0003baBd\u00170F\u0002C\u0003O#raQAU\u0003[\u000b\t\f\u0005\u00035\r\u0005\u0015VCA#P'\t1q'A\u0003xS\u0012$\b\u000e\u0005\u00029\u0011&\u0011\u0011*\u000f\u0002\u0004\u0013:$\u0018!\u0003:fg\u0016$8+\u001b>f)\ra\u0005,\u0017\t\u0004i\u0019i\u0005C\u0001(P\u0019\u0001!Q\u0001\u0015\u0004C\u0002E\u0013\u0011!Q\t\u0003%V\u0003\"\u0001O*\n\u0005QK$a\u0002(pi\"Lgn\u001a\t\u0003qYK!aV\u001d\u0003\u0007\u0005s\u0017\u0010C\u0003G\u0013\u0001\u0007q\tC\u0003K\u0013\u0001\u0007q)A\u0003EKB$\b.F\u0001]\u001f\u0005iV$\u0001\u0003\u0002\r\u0011+\u0007\u000f\u001e5!\u0003%\u0019Fn\u001c;TQ&4G/\u0001\u0006TY>$8\u000b[5gi\u0002\n\u0001b\u00157pi6\u000b7o[\u000b\u0002G>\tA-H\u0001\u0010\u0003%\u0019Fn\u001c;NCN\\\u0007%\u0001\u0007D_VtG/\u001a:TQ&4G/F\u0001i\u001f\u0005IW$\u0001\u0002\u0002\u001b\r{WO\u001c;feNC\u0017N\u001a;!\u0003-\u0019u.\u001e8uKJl\u0015m]6\u0016\u00035|\u0011A\u001c\u0010\u0002\u001f\u0005a1i\\;oi\u0016\u0014X*Y:lA\u00059q\n\u001a3NCN\\W#\u0001:\u0010\u0003Mt\u0002\"E\t\u0012#E\t\u0012#E\u0001\t\u001f\u0012$W*Y:lA\u0005I!+Z:fi6\u000b7o[\u000b\u0002o>\t\u0001P\b\u0005xo^<xo^<x\u0003)\u0011Vm]3u\u001b\u0006\u001c8\u000eI\u0001\u0006'\u0016,G\rM\u000b\u0002y>\tQP\b\u0005DL#gvss\u0019(\u0003\u0019\u0019V-\u001a31A\u0005)1+Z3ecU\u0011\u00111A\b\u0003\u0003\u000bq\u0002\u0002.J7`zF*\u007f]\u0001\u0007'\u0016,G-\r\u0011\u0002\u000bM+W\r\u001a\u001a\u0016\u0005\u00055qBAA\b=!Q\u001a][\u001e0!\u0003{\u0015AB*fK\u0012\u0014\u0004%A\u0003TK\u0016$7'\u0006\u0002\u0002\u0018=\u0011\u0011\u0011\u0004\u0010\t\u0017LhJ\u001d\"\u0012$K\u000511+Z3eg\u0001\n\u0001B]8x/&$G\u000f[\u0001\nS:$W\r_'bg.\fa!\\1ue&D\b#\u0002\u001d\u0002&\u0005%\u0012bAA\u0014s\t)\u0011I\u001d:bsB)\u0001(!\n\u0002,A\u0019\u0001(!\f\n\u0007\u0005=\u0012H\u0001\u0003M_:<\u0017\u0001\u0003:poNK'0Z:\u0011\ta\n)cR\u0001\fkB$\u0017\r^3e'&TX-\u0001\u0003tSj,W#A$\u0002\u0013\u0019\u0014X-];f]\u000eLHcA$\u0002@!1\u0011\u0011\t\u0014A\u00025\u000bQA^1mk\u0016\f\u0011\"\u001b8de\u0016lWM\u001c;\u0015\t\u0005\u001d\u0013Q\n\t\u0004q\u0005%\u0013bAA&s\t!QK\\5u\u0011\u0019\t\te\na\u0001\u001b\u00061!/\u001a5bg\"$2aRA*\u0011\u0019\t)\u0006\u000ba\u0001\u000f\u0006!\u0001.Y:i\u0003\u0015Ig\u000eZ3y)\u00159\u00151LA/\u0011\u0019\t)&\u000ba\u0001\u000f\"9\u0011qL\u0015A\u0002\u0005-\u0012\u0001B:fK\u0012\f!bZ3u\u0007>,h\u000e^3s)\u00159\u0015QMA5\u0011\u0019\t9G\u000ba\u0001\u000f\u0006\u0019!o\\<\t\r\u0005]#\u00061\u0001H\u0003AIgn\u0019:f[\u0016tGoQ8v]R,'\u000f\u0006\u0004\u0002p\u0005U\u0014q\u000f\t\u0004q\u0005E\u0014bAA:s\t9!i\\8mK\u0006t\u0007BBA4W\u0001\u0007q\t\u0003\u0004\u0002X-\u0002\raR\u0001\u0006e\u0016\u001cX\r\u001e\u000b\u0003\u0003\u000f\nQ\u0002^8EK\n,xm\u0015;sS:<WCAAA!\u0011\t\u0019)!%\u000f\t\u0005\u0015\u0015Q\u0012\t\u0004\u0003\u000fKTBAAE\u0015\r\tYIP\u0001\u0007yI|w\u000e\u001e \n\u0007\u0005=\u0015(\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003'\u000b)J\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003\u001fK\u0004f\u0001\u0004\u0002\u001aB!\u00111TAQ\u001b\t\tiJC\u0002\u0002 F\n!\"\u00198o_R\fG/[8o\u0013\u0011\t\u0019+!(\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b\t\u0004\u001d\u0006\u001dF!\u0002)\u0004\u0005\u0004\t\u0006BBAV\u0007\u0001\u0007q)\u0001\u0005dCB\f7-\u001b;z\u0011!\tyk\u0001I\u0001\u0002\u00049\u0015aD<jIRDW*\u001e7uSBd\u0017.\u001a:\t\u0013\u0005M6\u0001%AA\u0002\u0005U\u0016a\u0004:fg\u0016$X*\u001e7uSBd\u0017.\u001a:\u0011\u0007a\n9,C\u0002\u0002:f\u0012a\u0001R8vE2,\u0017aD1qa2LH\u0005Z3gCVdG\u000f\n\u001a\u0016\t\u0005}\u00161[\u000b\u0003\u0003\u0003T3aRAbW\t\t)\r\u0005\u0003\u0002H\u0006=WBAAe\u0015\u0011\tY-!4\u0002\u0013Ut7\r[3dW\u0016$'bAAPs%!\u0011\u0011[Ae\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006!\u0012\u0011\r!U\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%gU!\u0011\u0011\\Ao+\t\tYN\u000b\u0003\u00026\u0006\rG!\u0002)\u0006\u0005\u0004\t\u0006fA\u0001\u0002\u001a\"\u001a\u0001!!'")
@InternalApi
public final class FastFrequencySketch<A> {
    private final int resetSize;
    private final int rowWidth;
    private final int indexMask;
    private final long[][] matrix;
    private final int[] rowSizes;
    private int updatedSize;

    public static <A> double apply$default$3() {
        return FastFrequencySketch$.MODULE$.apply$default$3();
    }

    public static <A> int apply$default$2() {
        return FastFrequencySketch$.MODULE$.apply$default$2();
    }

    public static <A> FastFrequencySketch<A> apply(int capacity, int widthMultiplier, double resetMultiplier) {
        return FastFrequencySketch$.MODULE$.apply(capacity, widthMultiplier, resetMultiplier);
    }

    private final int Depth() {
        return 4;
    }

    private final int SlotShift() {
        return 4;
    }

    private final int SlotMask() {
        return 15;
    }

    private final int CounterShift() {
        return 2;
    }

    private final long CounterMask() {
        return 15L;
    }

    private final long OddMask() {
        return 0x1111111111111111L;
    }

    private final long ResetMask() {
        return 0x7777777777777777L;
    }

    private final long Seed0() {
        return -4348849565147123417L;
    }

    private final long Seed1() {
        return -5435081209227447693L;
    }

    private final long Seed2() {
        return -7286425919675154353L;
    }

    private final long Seed3() {
        return -3750763034362895579L;
    }

    public int size() {
        return this.updatedSize;
    }

    public int frequency(A value) {
        int hash = this.rehash(value.hashCode());
        int minCount = this.getCounter(0, this.index(hash, -4348849565147123417L));
        minCount = Math.min(minCount, this.getCounter(1, this.index(hash, -5435081209227447693L)));
        minCount = Math.min(minCount, this.getCounter(2, this.index(hash, -7286425919675154353L)));
        minCount = Math.min(minCount, this.getCounter(3, this.index(hash, -3750763034362895579L)));
        return minCount;
    }

    public void increment(A value) {
        int hash = this.rehash(value.hashCode());
        boolean updated = this.incrementCounter(0, this.index(hash, -4348849565147123417L));
        updated |= this.incrementCounter(1, this.index(hash, -5435081209227447693L));
        updated |= this.incrementCounter(2, this.index(hash, -7286425919675154353L));
        if (updated |= this.incrementCounter(3, this.index(hash, -3750763034362895579L))) {
            ++this.updatedSize;
            if (this.updatedSize == this.resetSize) {
                this.reset();
                return;
            }
            return;
        }
    }

    private int rehash(int hash) {
        int x = hash;
        x = (x >>> 15 ^ x) * -781669715;
        x = (x >>> 15 ^ x) * -1351469673;
        return x >>> 15 ^ x;
    }

    private int index(int hash, long seed) {
        long x = ((long)hash + seed) * seed;
        return (int)(x + (x >>> 32)) & this.indexMask;
    }

    private int getCounter(int row, int index) {
        int column = index >>> 4;
        int slot = (index & 0xF) << 2;
        return (int)(this.matrix[row][column] >>> slot & 0xFL);
    }

    private boolean incrementCounter(int row, int index) {
        int column = index >>> 4;
        int slot = (index & 0xF) << 2;
        long mask = 15L << slot;
        if ((this.matrix[row][column] & mask) != mask) {
            long[] lArray = this.matrix[row];
            lArray[column] = lArray[column] + (1L << slot);
            this.rowSizes[row] = this.rowSizes[row] + 1;
            return true;
        }
        return false;
    }

    private void reset() {
        for (int row = 0; row < 4; ++row) {
            int odd = 0;
            for (int column = 0; column < this.rowWidth; ++column) {
                odd += Long.bitCount(this.matrix[row][column] & 0x1111111111111111L);
                this.matrix[row][column] = this.matrix[row][column] >>> 1 & 0x7777777777777777L;
            }
            this.rowSizes[row] = this.rowSizes[row] - odd >>> 1;
        }
        this.updatedSize = BoxesRunTime.unboxToInt(Predef$.MODULE$.wrapIntArray(this.rowSizes).max(Ordering$Int$.MODULE$));
    }

    public String toDebugString() {
        return FrequencySketchUtil$.MODULE$.debugString(this.matrix, this.rowWidth, 16, 4, 15L);
    }

    public FastFrequencySketch(int width, int resetSize) {
        this.resetSize = resetSize;
        Predef$.MODULE$.require(FrequencySketch$Bits$.MODULE$.isPowerOfTwo(width), (Function0<Object>)(Function0<String> & Serializable)() -> "width must be a power of two");
        this.rowWidth = package$.MODULE$.max(1, width >>> 4);
        this.indexMask = width - 1;
        this.matrix = (long[][])Array$.MODULE$.fill(4, (Function0<long[]> & Serializable)() -> (long[])Array$.MODULE$.ofDim($this.rowWidth, ClassTag$.MODULE$.Long()), ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(Long.TYPE)));
        this.rowSizes = (int[])Array$.MODULE$.ofDim(4, ClassTag$.MODULE$.Int());
        this.updatedSize = 0;
    }
}

