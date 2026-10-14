/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

import scala.Function1;
import scala.Function2;
import scala.Product;
import scala.Tuple2;
import scala.collection.IndexedSeq;
import scala.collection.IterableOnce;
import scala.collection.Map;
import scala.collection.Seq;
import scala.collection.Set;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;
import scala.util.hashing.Hashing;
import scala.util.hashing.MurmurHash3;
import scala.util.hashing.MurmurHash3$ArrayHashing$mcB$sp;
import scala.util.hashing.MurmurHash3$ArrayHashing$mcC$sp;
import scala.util.hashing.MurmurHash3$ArrayHashing$mcD$sp;
import scala.util.hashing.MurmurHash3$ArrayHashing$mcF$sp;
import scala.util.hashing.MurmurHash3$ArrayHashing$mcI$sp;
import scala.util.hashing.MurmurHash3$ArrayHashing$mcJ$sp;
import scala.util.hashing.MurmurHash3$ArrayHashing$mcS$sp;
import scala.util.hashing.MurmurHash3$ArrayHashing$mcV$sp;
import scala.util.hashing.MurmurHash3$ArrayHashing$mcZ$sp;

public final class MurmurHash3$
extends MurmurHash3 {
    public static final MurmurHash3$ MODULE$ = new MurmurHash3$();
    private static final int seqSeed = "Seq".hashCode();
    private static final int mapSeed = "Map".hashCode();
    private static final int setSeed = "Set".hashCode();
    private static final int emptyMapHash = MODULE$.unorderedHash(Nil$.MODULE$, MODULE$.mapSeed());

    @Override
    public final int arraySeed() {
        return 1007110753;
    }

    @Override
    public final int stringSeed() {
        return -137723950;
    }

    @Override
    public final int productSeed() {
        return -889275714;
    }

    @Override
    public final int symmetricSeed() {
        return -1248659538;
    }

    @Override
    public final int traversableSeed() {
        return -415593707;
    }

    @Override
    public final int seqSeed() {
        return seqSeed;
    }

    @Override
    public final int mapSeed() {
        return mapSeed;
    }

    @Override
    public final int setSeed() {
        return setSeed;
    }

    public <T> int arrayHash(Object a) {
        return this.arrayHash(a, 1007110753);
    }

    public int bytesHash(byte[] data) {
        return this.bytesHash(data, 1007110753);
    }

    public int orderedHash(IterableOnce<Object> xs) {
        return this.orderedHash(xs, -1248659538);
    }

    public int productHash(Product x) {
        return this.productHash(x, -889275714, false);
    }

    public int stringHash(String x) {
        return this.stringHash(x, -137723950);
    }

    public int unorderedHash(IterableOnce<Object> xs) {
        return this.unorderedHash(xs, -415593707);
    }

    public int rangeHash(int start2, int step, int last) {
        return this.rangeHash(start2, step, last, this.seqSeed());
    }

    public <T> int arraySeqHash(Object a) {
        return this.arrayHash(a, this.seqSeed());
    }

    public int tuple2Hash(Object x, Object y) {
        return this.tuple2Hash(Statics.anyHash(x), Statics.anyHash(y), -889275714);
    }

    @Override
    public int seqHash(Seq<?> xs) {
        if (xs instanceof IndexedSeq) {
            IndexedSeq indexedSeq = (IndexedSeq)xs;
            return this.indexedSeqHash(indexedSeq, this.seqSeed());
        }
        if (xs instanceof List) {
            List list = (List)xs;
            return this.listHash(list, this.seqSeed());
        }
        return this.orderedHash(xs, this.seqSeed());
    }

    @Override
    public int mapHash(Map<?, ?> xs) {
        if (xs.isEmpty()) {
            return this.emptyMapHash();
        }
        public class Scala_util_hashing_MurmurHash3$accum$1
        implements Function2<Object, Object, BoxedUnit> {
            private int a;
            private int b;
            private int n;
            private int c;

            public boolean apply$mcZDD$sp(double v1, double v2) {
                return Function2.apply$mcZDD$sp$(this, v1, v2);
            }

            public double apply$mcDDD$sp(double v1, double v2) {
                return Function2.apply$mcDDD$sp$(this, v1, v2);
            }

            public float apply$mcFDD$sp(double v1, double v2) {
                return Function2.apply$mcFDD$sp$(this, v1, v2);
            }

            public int apply$mcIDD$sp(double v1, double v2) {
                return Function2.apply$mcIDD$sp$(this, v1, v2);
            }

            public long apply$mcJDD$sp(double v1, double v2) {
                return Function2.apply$mcJDD$sp$(this, v1, v2);
            }

            public void apply$mcVDD$sp(double v1, double v2) {
                Function2.apply$mcVDD$sp$(this, v1, v2);
            }

            public boolean apply$mcZDI$sp(double v1, int v2) {
                return Function2.apply$mcZDI$sp$(this, v1, v2);
            }

            public double apply$mcDDI$sp(double v1, int v2) {
                return Function2.apply$mcDDI$sp$(this, v1, v2);
            }

            public float apply$mcFDI$sp(double v1, int v2) {
                return Function2.apply$mcFDI$sp$(this, v1, v2);
            }

            public int apply$mcIDI$sp(double v1, int v2) {
                return Function2.apply$mcIDI$sp$(this, v1, v2);
            }

            public long apply$mcJDI$sp(double v1, int v2) {
                return Function2.apply$mcJDI$sp$(this, v1, v2);
            }

            public void apply$mcVDI$sp(double v1, int v2) {
                Function2.apply$mcVDI$sp$(this, v1, v2);
            }

            public boolean apply$mcZDJ$sp(double v1, long v2) {
                return Function2.apply$mcZDJ$sp$(this, v1, v2);
            }

            public double apply$mcDDJ$sp(double v1, long v2) {
                return Function2.apply$mcDDJ$sp$(this, v1, v2);
            }

            public float apply$mcFDJ$sp(double v1, long v2) {
                return Function2.apply$mcFDJ$sp$(this, v1, v2);
            }

            public int apply$mcIDJ$sp(double v1, long v2) {
                return Function2.apply$mcIDJ$sp$(this, v1, v2);
            }

            public long apply$mcJDJ$sp(double v1, long v2) {
                return Function2.apply$mcJDJ$sp$(this, v1, v2);
            }

            public void apply$mcVDJ$sp(double v1, long v2) {
                Function2.apply$mcVDJ$sp$(this, v1, v2);
            }

            public boolean apply$mcZID$sp(int v1, double v2) {
                return Function2.apply$mcZID$sp$(this, v1, v2);
            }

            public double apply$mcDID$sp(int v1, double v2) {
                return Function2.apply$mcDID$sp$(this, v1, v2);
            }

            public float apply$mcFID$sp(int v1, double v2) {
                return Function2.apply$mcFID$sp$(this, v1, v2);
            }

            public int apply$mcIID$sp(int v1, double v2) {
                return Function2.apply$mcIID$sp$(this, v1, v2);
            }

            public long apply$mcJID$sp(int v1, double v2) {
                return Function2.apply$mcJID$sp$(this, v1, v2);
            }

            public void apply$mcVID$sp(int v1, double v2) {
                Function2.apply$mcVID$sp$(this, v1, v2);
            }

            public boolean apply$mcZII$sp(int v1, int v2) {
                return Function2.apply$mcZII$sp$(this, v1, v2);
            }

            public double apply$mcDII$sp(int v1, int v2) {
                return Function2.apply$mcDII$sp$(this, v1, v2);
            }

            public float apply$mcFII$sp(int v1, int v2) {
                return Function2.apply$mcFII$sp$(this, v1, v2);
            }

            public int apply$mcIII$sp(int v1, int v2) {
                return Function2.apply$mcIII$sp$(this, v1, v2);
            }

            public long apply$mcJII$sp(int v1, int v2) {
                return Function2.apply$mcJII$sp$(this, v1, v2);
            }

            public void apply$mcVII$sp(int v1, int v2) {
                Function2.apply$mcVII$sp$(this, v1, v2);
            }

            public boolean apply$mcZIJ$sp(int v1, long v2) {
                return Function2.apply$mcZIJ$sp$(this, v1, v2);
            }

            public double apply$mcDIJ$sp(int v1, long v2) {
                return Function2.apply$mcDIJ$sp$(this, v1, v2);
            }

            public float apply$mcFIJ$sp(int v1, long v2) {
                return Function2.apply$mcFIJ$sp$(this, v1, v2);
            }

            public int apply$mcIIJ$sp(int v1, long v2) {
                return Function2.apply$mcIIJ$sp$(this, v1, v2);
            }

            public long apply$mcJIJ$sp(int v1, long v2) {
                return Function2.apply$mcJIJ$sp$(this, v1, v2);
            }

            public void apply$mcVIJ$sp(int v1, long v2) {
                Function2.apply$mcVIJ$sp$(this, v1, v2);
            }

            public boolean apply$mcZJD$sp(long v1, double v2) {
                return Function2.apply$mcZJD$sp$(this, v1, v2);
            }

            public double apply$mcDJD$sp(long v1, double v2) {
                return Function2.apply$mcDJD$sp$(this, v1, v2);
            }

            public float apply$mcFJD$sp(long v1, double v2) {
                return Function2.apply$mcFJD$sp$(this, v1, v2);
            }

            public int apply$mcIJD$sp(long v1, double v2) {
                return Function2.apply$mcIJD$sp$(this, v1, v2);
            }

            public long apply$mcJJD$sp(long v1, double v2) {
                return Function2.apply$mcJJD$sp$(this, v1, v2);
            }

            public void apply$mcVJD$sp(long v1, double v2) {
                Function2.apply$mcVJD$sp$(this, v1, v2);
            }

            public boolean apply$mcZJI$sp(long v1, int v2) {
                return Function2.apply$mcZJI$sp$(this, v1, v2);
            }

            public double apply$mcDJI$sp(long v1, int v2) {
                return Function2.apply$mcDJI$sp$(this, v1, v2);
            }

            public float apply$mcFJI$sp(long v1, int v2) {
                return Function2.apply$mcFJI$sp$(this, v1, v2);
            }

            public int apply$mcIJI$sp(long v1, int v2) {
                return Function2.apply$mcIJI$sp$(this, v1, v2);
            }

            public long apply$mcJJI$sp(long v1, int v2) {
                return Function2.apply$mcJJI$sp$(this, v1, v2);
            }

            public void apply$mcVJI$sp(long v1, int v2) {
                Function2.apply$mcVJI$sp$(this, v1, v2);
            }

            public boolean apply$mcZJJ$sp(long v1, long v2) {
                return Function2.apply$mcZJJ$sp$(this, v1, v2);
            }

            public double apply$mcDJJ$sp(long v1, long v2) {
                return Function2.apply$mcDJJ$sp$(this, v1, v2);
            }

            public float apply$mcFJJ$sp(long v1, long v2) {
                return Function2.apply$mcFJJ$sp$(this, v1, v2);
            }

            public int apply$mcIJJ$sp(long v1, long v2) {
                return Function2.apply$mcIJJ$sp$(this, v1, v2);
            }

            public long apply$mcJJJ$sp(long v1, long v2) {
                return Function2.apply$mcJJJ$sp$(this, v1, v2);
            }

            public void apply$mcVJJ$sp(long v1, long v2) {
                Function2.apply$mcVJJ$sp$(this, v1, v2);
            }

            public Function1<Object, Function1<Object, BoxedUnit>> curried() {
                return Function2.curried$(this);
            }

            public Function1<Tuple2<Object, Object>, BoxedUnit> tupled() {
                return Function2.tupled$(this);
            }

            public String toString() {
                return Function2.toString$(this);
            }

            public int a() {
                return this.a;
            }

            public void a_$eq(int x$1) {
                this.a = x$1;
            }

            public int b() {
                return this.b;
            }

            public void b_$eq(int x$1) {
                this.b = x$1;
            }

            public int n() {
                return this.n;
            }

            public void n_$eq(int x$1) {
                this.n = x$1;
            }

            public int c() {
                return this.c;
            }

            public void c_$eq(int x$1) {
                this.c = x$1;
            }

            public void apply(Object k, Object v) {
                int h2 = MurmurHash3$.MODULE$.tuple2Hash(k, v);
                this.a_$eq(this.a() + h2);
                this.b_$eq(this.b() ^ h2);
                this.c_$eq(this.c() * (h2 | 1));
                this.n_$eq(this.n() + 1);
            }

            public Scala_util_hashing_MurmurHash3$accum$1() {
                this.a = 0;
                this.b = 0;
                this.n = 0;
                this.c = 1;
            }
        }
        Scala_util_hashing_MurmurHash3$accum$1 accum2 = new Scala_util_hashing_MurmurHash3$accum$1();
        int h2 = this.mapSeed();
        xs.foreachEntry(accum2);
        h2 = this.mix(h2, accum2.a());
        h2 = this.mix(h2, accum2.b());
        h2 = this.mixLast(h2, accum2.c());
        int finalizeHash_length = accum2.n();
        return this.scala$util$hashing$MurmurHash3$$avalanche(h2 ^ finalizeHash_length);
    }

    public int emptyMapHash() {
        return emptyMapHash;
    }

    @Override
    public int setHash(Set<?> xs) {
        return this.unorderedHash(xs, this.setSeed());
    }

    @Override
    public <T> MurmurHash3.ArrayHashing<T> arrayHashing() {
        return new MurmurHash3.ArrayHashing();
    }

    @Override
    public Hashing<byte[]> bytesHashing() {
        return new Hashing<byte[]>(){

            public int hash(byte[] data) {
                return MurmurHash3$.MODULE$.bytesHash(data, 1007110753);
            }
        };
    }

    @Override
    public Hashing<IterableOnce<Object>> orderedHashing() {
        return new Hashing<IterableOnce<Object>>(){

            public int hash(IterableOnce<Object> xs) {
                return MurmurHash3$.MODULE$.orderedHash(xs, -1248659538);
            }
        };
    }

    @Override
    public Hashing<Product> productHashing() {
        return new Hashing<Product>(){

            public int hash(Product x) {
                return MurmurHash3$.MODULE$.productHash(x);
            }
        };
    }

    @Override
    public Hashing<String> stringHashing() {
        return new Hashing<String>(){

            public int hash(String x) {
                return MurmurHash3$.MODULE$.stringHash(x, -137723950);
            }
        };
    }

    @Override
    public Hashing<IterableOnce<Object>> unorderedHashing() {
        return new Hashing<IterableOnce<Object>>(){

            public int hash(IterableOnce<Object> xs) {
                return MurmurHash3$.MODULE$.unorderedHash(xs, -415593707);
            }
        };
    }

    public int arrayHash$mZc$sp(boolean[] a) {
        return this.arrayHash$mZc$sp(a, 1007110753);
    }

    public int arrayHash$mBc$sp(byte[] a) {
        return this.arrayHash$mBc$sp(a, 1007110753);
    }

    public int arrayHash$mCc$sp(char[] a) {
        return this.arrayHash$mCc$sp(a, 1007110753);
    }

    public int arrayHash$mDc$sp(double[] a) {
        return this.arrayHash$mDc$sp(a, 1007110753);
    }

    public int arrayHash$mFc$sp(float[] a) {
        return this.arrayHash$mFc$sp(a, 1007110753);
    }

    public int arrayHash$mIc$sp(int[] a) {
        return this.arrayHash$mIc$sp(a, 1007110753);
    }

    public int arrayHash$mJc$sp(long[] a) {
        return this.arrayHash$mJc$sp(a, 1007110753);
    }

    public int arrayHash$mSc$sp(short[] a) {
        return this.arrayHash$mSc$sp(a, 1007110753);
    }

    public int arrayHash$mVc$sp(BoxedUnit[] a) {
        return this.arrayHash$mVc$sp(a, 1007110753);
    }

    public int arraySeqHash$mZc$sp(boolean[] a) {
        return this.arrayHash$mZc$sp(a, this.seqSeed());
    }

    public int arraySeqHash$mBc$sp(byte[] a) {
        return this.arrayHash$mBc$sp(a, this.seqSeed());
    }

    public int arraySeqHash$mCc$sp(char[] a) {
        return this.arrayHash$mCc$sp(a, this.seqSeed());
    }

    public int arraySeqHash$mDc$sp(double[] a) {
        return this.arrayHash$mDc$sp(a, this.seqSeed());
    }

    public int arraySeqHash$mFc$sp(float[] a) {
        return this.arrayHash$mFc$sp(a, this.seqSeed());
    }

    public int arraySeqHash$mIc$sp(int[] a) {
        return this.arrayHash$mIc$sp(a, this.seqSeed());
    }

    public int arraySeqHash$mJc$sp(long[] a) {
        return this.arrayHash$mJc$sp(a, this.seqSeed());
    }

    public int arraySeqHash$mSc$sp(short[] a) {
        return this.arrayHash$mSc$sp(a, this.seqSeed());
    }

    public int arraySeqHash$mVc$sp(BoxedUnit[] a) {
        return this.arrayHash$mVc$sp(a, this.seqSeed());
    }

    public MurmurHash3.ArrayHashing<Object> arrayHashing$mZc$sp() {
        return new MurmurHash3$ArrayHashing$mcZ$sp();
    }

    public MurmurHash3.ArrayHashing<Object> arrayHashing$mBc$sp() {
        return new MurmurHash3$ArrayHashing$mcB$sp();
    }

    public MurmurHash3.ArrayHashing<Object> arrayHashing$mCc$sp() {
        return new MurmurHash3$ArrayHashing$mcC$sp();
    }

    public MurmurHash3.ArrayHashing<Object> arrayHashing$mDc$sp() {
        return new MurmurHash3$ArrayHashing$mcD$sp();
    }

    public MurmurHash3.ArrayHashing<Object> arrayHashing$mFc$sp() {
        return new MurmurHash3$ArrayHashing$mcF$sp();
    }

    public MurmurHash3.ArrayHashing<Object> arrayHashing$mIc$sp() {
        return new MurmurHash3$ArrayHashing$mcI$sp();
    }

    public MurmurHash3.ArrayHashing<Object> arrayHashing$mJc$sp() {
        return new MurmurHash3$ArrayHashing$mcJ$sp();
    }

    public MurmurHash3.ArrayHashing<Object> arrayHashing$mSc$sp() {
        return new MurmurHash3$ArrayHashing$mcS$sp();
    }

    public MurmurHash3.ArrayHashing<BoxedUnit> arrayHashing$mVc$sp() {
        return new MurmurHash3$ArrayHashing$mcV$sp();
    }

    private MurmurHash3$() {
    }
}

