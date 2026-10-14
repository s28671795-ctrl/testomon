/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.generic;

import java.io.Serializable;
import scala.Function1;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.IndexedSeq$;
import scala.collection.immutable.Range;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntegralProxy;
import scala.runtime.RichLong;

@ScalaSignature(bytes="\u0006\u0005\u0005-tAB\u0012%\u0011\u00031#F\u0002\u0004-I!\u0005a%\f\u0005\u0006e\u0005!\t\u0001\u000e\u0004\bk\u0005\u0001\n1!\u00017\u0011\u001594\u0001\"\u00019\u000b\u0011)4\u0001\u0001\u001f\t\u000by\u001aA\u0011A \t\u000b!\u001bA\u0011A%\t\u000b1\u001bA\u0011A'\t\u000bQ\u001bA\u0011A+\t\u000be\u001bA\u0011\u0001.\t\u000b}\u001bA\u0011\u00011\t\u000b\t\u001cA\u0011A2\t\u000b1\u001cA\u0011A7\t\u000fq\u001c\u0011\u0013!C\u0001{\"9\u0011\u0011C\u0002\u0005\u0002\u0005MqaBA\f\u0003!\u0005\u0011\u0011\u0004\u0004\u0007k\u0005A\t!!\b\t\rI\nB\u0011AA\u0011\r%\t\u0019#\u0001I\u0001\u0004\u0003\t)\u0003C\u00038'\u0011\u0005\u0001(\u0002\u0004\u0002$M\u0001\u0011q\u0005\u0005\u0007}M!\t!a\u000b\t\r!\u001bB\u0011AA\u001b\u0011\u0019a5\u0003\"\u0001\u0002<!1Ak\u0005C\u0001\u0003\u0007Ba!W\n\u0005\u0002\u0005%\u0003BB0\u0014\t\u0003\ty\u0005\u0003\u0004c'\u0011\u0005\u00111\u000b\u0005\u0007YN!\t!a\u0016\t\u000fq\u001c\u0012\u0013!C\u0001{\"9\u0011\u0011C\n\u0005\u0002\u0005usaBA1\u0003!\u0005\u00111\r\u0004\b\u0003G\t\u0001\u0012AA3\u0011\u0019\u0011\u0014\u0005\"\u0001\u0002j\u0005i!)\u001b;Pa\u0016\u0014\u0018\r^5p]NT!!\n\u0014\u0002\u000f\u001d,g.\u001a:jG*\u0011q\u0005K\u0001\u000bG>dG.Z2uS>t'\"A\u0015\u0002\u000bM\u001c\u0017\r\\1\u0011\u0005-\nQ\"\u0001\u0013\u0003\u001b\tKGo\u00149fe\u0006$\u0018n\u001c8t'\t\ta\u0006\u0005\u00020a5\t\u0001&\u0003\u00022Q\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002U\t\u0019\u0011J\u001c;\u0014\u0005\rq\u0013A\u0002\u0013j]&$H\u0005F\u0001:!\ty#(\u0003\u0002<Q\t!QK\\5u!\tyS(\u0003\u00026Q\u0005!!0\u001a:p)\r\u00015i\u0012\t\u0003_\u0005K!A\u0011\u0015\u0003\u000f\t{w\u000e\\3b]\")AI\u0002a\u0001\u000b\u0006\t\u0011\u000e\u0005\u0002G\u000b5\t1\u0001C\u0003I\r\u0001\u0007Q)\u0001\u0003nCN\\Gc\u0001\u001fK\u0017\")Ai\u0002a\u0001\u000b\")\u0001j\u0002a\u0001\u000b\u0006A\u0001.Y:NCR\u001c\u0007\u000e\u0006\u0003A\u001dB\u0013\u0006\"B(\t\u0001\u0004)\u0015aA6fs\")\u0011\u000b\u0003a\u0001\u000b\u00061\u0001O]3gSbDQa\u0015\u0005A\u0002\u0015\u000b\u0011!\\\u0001\u0010k:\u001c\u0018n\u001a8fI\u000e{W\u000e]1sKR\u0019\u0001IV,\t\u000b\u0011K\u0001\u0019A#\t\u000baK\u0001\u0019A#\u0002\u0003)\fqa\u001d5peR,'\u000fF\u0002A7vCQ\u0001\u0018\u0006A\u0002\u0015\u000b!!\\\u0019\t\u000byS\u0001\u0019A#\u0002\u00055\u0014\u0014AC2p[BdW-\\3oiR\u0011A(\u0019\u0005\u0006\t.\u0001\r!R\u0001\u0005E&$8\u000f\u0006\u0002eUB\u0019Q\r\u001b!\u000e\u0003\u0019T!a\u001a\u0014\u0002\u0013%lW.\u001e;bE2,\u0017BA5g\u0005)Ie\u000eZ3yK\u0012\u001cV-\u001d\u0005\u0006W2\u0001\r!R\u0001\u0004]Vl\u0017!\u00032jiN#(/\u001b8h)\rq\u0017P\u001f\t\u0003_Zt!\u0001\u001d;\u0011\u0005EDS\"\u0001:\u000b\u0005M\u001c\u0014A\u0002\u001fs_>$h(\u0003\u0002vQ\u00051\u0001K]3eK\u001aL!a\u001e=\u0003\rM#(/\u001b8h\u0015\t)\b\u0006C\u0003l\u001b\u0001\u0007Q\tC\u0004|\u001bA\u0005\t\u0019\u00018\u0002\u0007M,\u0007/A\ncSR\u001cFO]5oO\u0012\"WMZ1vYR$#'F\u0001\u007fU\tqwp\u000b\u0002\u0002\u0002A!\u00111AA\u0007\u001b\t\t)A\u0003\u0003\u0002\b\u0005%\u0011!C;oG\",7m[3e\u0015\r\tY\u0001K\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\b\u0003\u000b\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00035A\u0017n\u001a5fgR|e.\u001a\"jiR\u0019A(!\u0006\t\u000ba{\u0001\u0019A#\u0002\u0007%sG\u000fE\u0002\u0002\u001cEi\u0011!A\n\u0005#9\ny\u0002E\u0002\u0002\u001c\r!\"!!\u0007\u0003\t1{gnZ\n\u0003'9\u00022aLA\u0015\u0013\r\t\u0019\u0003\u000b\u000b\u0006\u0001\u00065\u00121\u0007\u0005\u0007\tZ\u0001\r!a\f\u0011\u0007\u0005ER#D\u0001\u0014\u0011\u0019Ae\u00031\u0001\u00020Q1\u0011qEA\u001c\u0003sAa\u0001R\fA\u0002\u0005=\u0002B\u0002%\u0018\u0001\u0004\ty\u0003F\u0004A\u0003{\ty$!\u0011\t\r=C\u0002\u0019AA\u0018\u0011\u0019\t\u0006\u00041\u0001\u00020!11\u000b\u0007a\u0001\u0003_!R\u0001QA#\u0003\u000fBa\u0001R\rA\u0002\u0005=\u0002B\u0002-\u001a\u0001\u0004\ty\u0003F\u0003A\u0003\u0017\ni\u0005\u0003\u0004]5\u0001\u0007\u0011q\u0006\u0005\u0007=j\u0001\r!a\f\u0015\t\u0005\u001d\u0012\u0011\u000b\u0005\u0007\tn\u0001\r!a\f\u0015\u0007\u0011\f)\u0006\u0003\u0004l9\u0001\u0007\u0011q\u0006\u000b\u0006]\u0006e\u00131\f\u0005\u0007Wv\u0001\r!a\f\t\u000fml\u0002\u0013!a\u0001]R!\u0011qEA0\u0011\u0019Av\u00041\u0001\u00020\u0005!Aj\u001c8h!\r\tY\"I\n\u0005C9\n9\u0007E\u0002\u0002\u001cM!\"!a\u0019")
public final class BitOperations {

    public static interface Int {
        public static /* synthetic */ boolean zero$(Int $this, int i, int mask) {
            return $this.zero(i, mask);
        }

        default public boolean zero(int i, int mask) {
            return (i & mask) == 0;
        }

        public static /* synthetic */ int mask$(Int $this, int i, int mask) {
            return $this.mask(i, mask);
        }

        default public int mask(int i, int mask) {
            return i & (this.complement(mask - 1) ^ mask);
        }

        public static /* synthetic */ boolean hasMatch$(Int $this, int key, int prefix, int m4) {
            return $this.hasMatch(key, prefix, m4);
        }

        default public boolean hasMatch(int key, int prefix, int m4) {
            return this.mask(key, m4) == prefix;
        }

        public static /* synthetic */ boolean unsignedCompare$(Int $this, int i, int j) {
            return $this.unsignedCompare(i, j);
        }

        default public boolean unsignedCompare(int i, int j) {
            return i < j ^ i < 0 ^ j < 0;
        }

        public static /* synthetic */ boolean shorter$(Int $this, int m1, int m22) {
            return $this.shorter(m1, m22);
        }

        default public boolean shorter(int m1, int m22) {
            return this.unsignedCompare(m22, m1);
        }

        public static /* synthetic */ int complement$(Int $this, int i) {
            return $this.complement(i);
        }

        default public int complement(int i) {
            return 0xFFFFFFFF ^ i;
        }

        public static /* synthetic */ IndexedSeq bits$(Int $this, int num) {
            return $this.bits(num);
        }

        /*
         * WARNING - void declaration
         */
        default public IndexedSeq<Object> bits(int num) {
            int n = 31;
            int to$extension_end = 0;
            Range range = new Range.Inclusive(n, to$extension_end, 1).by(-1);
            if (range == null) {
                throw null;
            }
            Range map_this = range;
            map_this.scala$collection$immutable$Range$$validateMaxLength();
            Builder strictOptimizedMap_b = IndexedSeq$.MODULE$.newBuilder();
            Iterator strictOptimizedMap_it = map_this.iterator();
            while (strictOptimizedMap_it.hasNext()) {
                void strictOptimizedMap_$plus$eq_elem;
                int n2 = BoxesRunTime.unboxToInt(strictOptimizedMap_it.next());
                Boolean bl = BoxesRunTime.boxToBoolean(Int.$anonfun$bits$1(num, n2));
                if (strictOptimizedMap_b == null) {
                    throw null;
                }
                strictOptimizedMap_b.addOne(strictOptimizedMap_$plus$eq_elem);
                bl = null;
            }
            return (IndexedSeq)strictOptimizedMap_b.result();
        }

        public static /* synthetic */ String bitString$(Int $this, int num, String sep) {
            return $this.bitString(num, sep);
        }

        default public String bitString(int num, String sep) {
            IterableOnceOps iterableOnceOps = (IterableOnceOps)this.bits(num).map((Function1<Object, String> & Serializable)b -> Int.$anonfun$bitString$1(BoxesRunTime.unboxToBoolean(b)));
            if (iterableOnceOps == null) {
                throw null;
            }
            return iterableOnceOps.mkString("", sep, "");
        }

        public static /* synthetic */ String bitString$default$2$(Int $this) {
            return $this.bitString$default$2();
        }

        default public String bitString$default$2() {
            return "";
        }

        public static /* synthetic */ int highestOneBit$(Int $this, int j) {
            return $this.highestOneBit(j);
        }

        default public int highestOneBit(int j) {
            return Integer.highestOneBit(j);
        }

        public static /* synthetic */ boolean $anonfun$bits$1(int num$1, int i) {
            return (num$1 >>> i & 1) != 0;
        }

        public static /* synthetic */ String $anonfun$bitString$1(boolean b) {
            if (b) {
                return "1";
            }
            return "0";
        }

        public static void $init$(Int $this) {
        }
    }

    public static interface Long {
        public static /* synthetic */ boolean zero$(Long $this, long i, long mask) {
            return $this.zero(i, mask);
        }

        default public boolean zero(long i, long mask) {
            return (i & mask) == 0L;
        }

        public static /* synthetic */ long mask$(Long $this, long i, long mask) {
            return $this.mask(i, mask);
        }

        default public long mask(long i, long mask) {
            return i & (this.complement(mask - 1L) ^ mask);
        }

        public static /* synthetic */ boolean hasMatch$(Long $this, long key, long prefix, long m4) {
            return $this.hasMatch(key, prefix, m4);
        }

        default public boolean hasMatch(long key, long prefix, long m4) {
            return this.mask(key, m4) == prefix;
        }

        public static /* synthetic */ boolean unsignedCompare$(Long $this, long i, long j) {
            return $this.unsignedCompare(i, j);
        }

        default public boolean unsignedCompare(long i, long j) {
            return i < j ^ i < 0L ^ j < 0L;
        }

        public static /* synthetic */ boolean shorter$(Long $this, long m1, long m22) {
            return $this.shorter(m1, m22);
        }

        default public boolean shorter(long m1, long m22) {
            return this.unsignedCompare(m22, m1);
        }

        public static /* synthetic */ long complement$(Long $this, long i) {
            return $this.complement(i);
        }

        default public long complement(long i) {
            return 0xFFFFFFFFFFFFFFFFL ^ i;
        }

        public static /* synthetic */ IndexedSeq bits$(Long $this, long num) {
            return $this.bits(num);
        }

        default public IndexedSeq<Object> bits(long num) {
            return (IndexedSeq)IntegralProxy.to$(new RichLong(63L), 0L).by(-1L).map(i -> (num >>> (int)i & 1L) != 0L);
        }

        public static /* synthetic */ String bitString$(Long $this, long num, String sep) {
            return $this.bitString(num, sep);
        }

        default public String bitString(long num, String sep) {
            IterableOnceOps iterableOnceOps = (IterableOnceOps)this.bits(num).map((Function1<Object, String> & Serializable)b -> Long.$anonfun$bitString$2(BoxesRunTime.unboxToBoolean(b)));
            if (iterableOnceOps == null) {
                throw null;
            }
            return iterableOnceOps.mkString("", sep, "");
        }

        public static /* synthetic */ String bitString$default$2$(Long $this) {
            return $this.bitString$default$2();
        }

        default public String bitString$default$2() {
            return "";
        }

        public static /* synthetic */ long highestOneBit$(Long $this, long j) {
            return $this.highestOneBit(j);
        }

        default public long highestOneBit(long j) {
            return java.lang.Long.highestOneBit(j);
        }

        public static /* synthetic */ String $anonfun$bitString$2(boolean b) {
            if (b) {
                return "1";
            }
            return "0";
        }

        public static void $init$(Long $this) {
        }
    }
}

