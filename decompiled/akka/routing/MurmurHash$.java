/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import java.io.Serializable;
import scala.Function1;
import scala.collection.IterableOnce;
import scala.collection.IterableOnce$;
import scala.collection.IterableOnceExtensionMethods$;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class MurmurHash$ {
    public static final MurmurHash$ MODULE$ = new MurmurHash$();
    private static final int visibleMagic = -1759636613;
    private static final int akka$routing$MurmurHash$$hiddenMagicA = -1789642873;
    private static final int akka$routing$MurmurHash$$hiddenMagicB = 718793509;
    private static final int visibleMixer = 1390208809;
    private static final int hiddenMixerA = 2071795100;
    private static final int hiddenMixerB = 1808688022;
    private static final int finalMixer1 = -2048144789;
    private static final int finalMixer2 = -1028477387;
    private static final int seedString = -137723950;
    private static final int akka$routing$MurmurHash$$seedArray = 1007110753;
    private static final int[] storedMagicA = (int[])package$.MODULE$.Iterator().iterate(BoxesRunTime.boxToInteger(MODULE$.akka$routing$MurmurHash$$hiddenMagicA()), magicA -> MODULE$.nextMagicA(magicA)).take(23).toArray(ClassTag$.MODULE$.Int());
    private static final int[] storedMagicB = (int[])package$.MODULE$.Iterator().iterate(BoxesRunTime.boxToInteger(MODULE$.akka$routing$MurmurHash$$hiddenMagicB()), magicB -> MODULE$.nextMagicB(magicB)).take(23).toArray(ClassTag$.MODULE$.Int());

    private final int visibleMagic() {
        return visibleMagic;
    }

    public final int akka$routing$MurmurHash$$hiddenMagicA() {
        return akka$routing$MurmurHash$$hiddenMagicA;
    }

    public final int akka$routing$MurmurHash$$hiddenMagicB() {
        return akka$routing$MurmurHash$$hiddenMagicB;
    }

    private final int visibleMixer() {
        return visibleMixer;
    }

    private final int hiddenMixerA() {
        return hiddenMixerA;
    }

    private final int hiddenMixerB() {
        return hiddenMixerB;
    }

    private final int finalMixer1() {
        return finalMixer1;
    }

    private final int finalMixer2() {
        return finalMixer2;
    }

    private final int seedString() {
        return seedString;
    }

    public final int akka$routing$MurmurHash$$seedArray() {
        return akka$routing$MurmurHash$$seedArray;
    }

    private int[] storedMagicA() {
        return storedMagicA;
    }

    private int[] storedMagicB() {
        return storedMagicB;
    }

    public int startHash(int seed) {
        return seed ^ this.visibleMagic();
    }

    public int startMagicA() {
        return this.akka$routing$MurmurHash$$hiddenMagicA();
    }

    public int startMagicB() {
        return this.akka$routing$MurmurHash$$hiddenMagicB();
    }

    public int extendHash(int hash, int value, int magicA, int magicB) {
        return (hash ^ Integer.rotateLeft(value * magicA, 11) * magicB) * 3 + this.visibleMixer();
    }

    public int nextMagicA(int magicA) {
        return magicA * 5 + this.hiddenMixerA();
    }

    public int nextMagicB(int magicB) {
        return magicB * 5 + this.hiddenMixerB();
    }

    public int finalizeHash(int hash) {
        int i = hash ^ hash >>> 16;
        i *= this.finalMixer1();
        i ^= i >>> 13;
        i *= this.finalMixer2();
        i ^= i >>> 16;
        return i;
    }

    public <T> int arrayHash(Object a) {
        int h2 = this.startHash(ScalaRunTime$.MODULE$.array_length(a) * this.akka$routing$MurmurHash$$seedArray());
        int c = this.akka$routing$MurmurHash$$hiddenMagicA();
        int k = this.akka$routing$MurmurHash$$hiddenMagicB();
        for (int j = 0; j < ScalaRunTime$.MODULE$.array_length(a); ++j) {
            h2 = this.extendHash(h2, Statics.anyHash(ScalaRunTime$.MODULE$.array_apply(a, j)), c, k);
            c = this.nextMagicA(c);
            k = this.nextMagicB(k);
        }
        return this.finalizeHash(h2);
    }

    public int stringHash(String s2) {
        int h2;
        block1: {
            h2 = this.startHash(s2.length() * this.seedString());
            int c = this.akka$routing$MurmurHash$$hiddenMagicA();
            int k = this.akka$routing$MurmurHash$$hiddenMagicB();
            int j = 0;
            while (j + 1 < s2.length()) {
                int i = (s2.charAt(j) << 16) + s2.charAt(j + 1);
                h2 = this.extendHash(h2, i, c, k);
                c = this.nextMagicA(c);
                k = this.nextMagicB(k);
                j += 2;
            }
            if (j >= s2.length()) break block1;
            h2 = this.extendHash(h2, s2.charAt(j), c, k);
        }
        return this.finalizeHash(h2);
    }

    public <T> int symmetricHash(IterableOnce<T> xs, int seed) {
        IntRef a = IntRef.create(0);
        IntRef b = IntRef.create(0);
        IntRef n = IntRef.create(0);
        IntRef c = IntRef.create(1);
        IterableOnceExtensionMethods$.MODULE$.foreach$extension(IterableOnce$.MODULE$.iterableOnceExtensionMethods(xs), (Function1<Object, Object> & Serializable)i -> {
            MurmurHash$.$anonfun$symmetricHash$1(a, b, c, n, i);
            return BoxedUnit.UNIT;
        });
        int h2 = this.startHash(seed * n.elem);
        h2 = this.extendHash(h2, a.elem, this.storedMagicA()[0], this.storedMagicB()[0]);
        h2 = this.extendHash(h2, b.elem, this.storedMagicA()[1], this.storedMagicB()[1]);
        h2 = this.extendHash(h2, c.elem, this.storedMagicA()[2], this.storedMagicB()[2]);
        return this.finalizeHash(h2);
    }

    public int arrayHash$mZc$sp(boolean[] a) {
        int h2 = this.startHash(a.length * this.akka$routing$MurmurHash$$seedArray());
        int c = this.akka$routing$MurmurHash$$hiddenMagicA();
        int k = this.akka$routing$MurmurHash$$hiddenMagicB();
        for (int j = 0; j < a.length; ++j) {
            h2 = this.extendHash(h2, a[j] ? 1231 : 1237, c, k);
            c = this.nextMagicA(c);
            k = this.nextMagicB(k);
        }
        return this.finalizeHash(h2);
    }

    public int arrayHash$mBc$sp(byte[] a) {
        int h2 = this.startHash(a.length * this.akka$routing$MurmurHash$$seedArray());
        int c = this.akka$routing$MurmurHash$$hiddenMagicA();
        int k = this.akka$routing$MurmurHash$$hiddenMagicB();
        for (int j = 0; j < a.length; ++j) {
            h2 = this.extendHash(h2, a[j], c, k);
            c = this.nextMagicA(c);
            k = this.nextMagicB(k);
        }
        return this.finalizeHash(h2);
    }

    public int arrayHash$mCc$sp(char[] a) {
        int h2 = this.startHash(a.length * this.akka$routing$MurmurHash$$seedArray());
        int c = this.akka$routing$MurmurHash$$hiddenMagicA();
        int k = this.akka$routing$MurmurHash$$hiddenMagicB();
        for (int j = 0; j < a.length; ++j) {
            h2 = this.extendHash(h2, a[j], c, k);
            c = this.nextMagicA(c);
            k = this.nextMagicB(k);
        }
        return this.finalizeHash(h2);
    }

    public int arrayHash$mDc$sp(double[] a) {
        int h2 = this.startHash(a.length * this.akka$routing$MurmurHash$$seedArray());
        int c = this.akka$routing$MurmurHash$$hiddenMagicA();
        int k = this.akka$routing$MurmurHash$$hiddenMagicB();
        for (int j = 0; j < a.length; ++j) {
            h2 = this.extendHash(h2, Statics.doubleHash(a[j]), c, k);
            c = this.nextMagicA(c);
            k = this.nextMagicB(k);
        }
        return this.finalizeHash(h2);
    }

    public int arrayHash$mFc$sp(float[] a) {
        int h2 = this.startHash(a.length * this.akka$routing$MurmurHash$$seedArray());
        int c = this.akka$routing$MurmurHash$$hiddenMagicA();
        int k = this.akka$routing$MurmurHash$$hiddenMagicB();
        for (int j = 0; j < a.length; ++j) {
            h2 = this.extendHash(h2, Statics.floatHash(a[j]), c, k);
            c = this.nextMagicA(c);
            k = this.nextMagicB(k);
        }
        return this.finalizeHash(h2);
    }

    public int arrayHash$mIc$sp(int[] a) {
        int h2 = this.startHash(a.length * this.akka$routing$MurmurHash$$seedArray());
        int c = this.akka$routing$MurmurHash$$hiddenMagicA();
        int k = this.akka$routing$MurmurHash$$hiddenMagicB();
        for (int j = 0; j < a.length; ++j) {
            h2 = this.extendHash(h2, a[j], c, k);
            c = this.nextMagicA(c);
            k = this.nextMagicB(k);
        }
        return this.finalizeHash(h2);
    }

    public int arrayHash$mJc$sp(long[] a) {
        int h2 = this.startHash(a.length * this.akka$routing$MurmurHash$$seedArray());
        int c = this.akka$routing$MurmurHash$$hiddenMagicA();
        int k = this.akka$routing$MurmurHash$$hiddenMagicB();
        for (int j = 0; j < a.length; ++j) {
            h2 = this.extendHash(h2, Statics.longHash(a[j]), c, k);
            c = this.nextMagicA(c);
            k = this.nextMagicB(k);
        }
        return this.finalizeHash(h2);
    }

    public int arrayHash$mSc$sp(short[] a) {
        int h2 = this.startHash(a.length * this.akka$routing$MurmurHash$$seedArray());
        int c = this.akka$routing$MurmurHash$$hiddenMagicA();
        int k = this.akka$routing$MurmurHash$$hiddenMagicB();
        for (int j = 0; j < a.length; ++j) {
            h2 = this.extendHash(h2, a[j], c, k);
            c = this.nextMagicA(c);
            k = this.nextMagicB(k);
        }
        return this.finalizeHash(h2);
    }

    public int arrayHash$mVc$sp(BoxedUnit[] a) {
        int h2 = this.startHash(a.length * this.akka$routing$MurmurHash$$seedArray());
        int c = this.akka$routing$MurmurHash$$hiddenMagicA();
        int k = this.akka$routing$MurmurHash$$hiddenMagicB();
        for (int j = 0; j < a.length; ++j) {
            h2 = this.extendHash(h2, 0, c, k);
            c = this.nextMagicA(c);
            k = this.nextMagicB(k);
        }
        return this.finalizeHash(h2);
    }

    public static final /* synthetic */ void $anonfun$symmetricHash$1(IntRef a$1, IntRef b$1, IntRef c$1, IntRef n$1, Object i) {
        int h2 = Statics.anyHash(i);
        a$1.elem += h2;
        b$1.elem ^= h2;
        if (h2 != 0) {
            c$1.elem *= h2;
        }
        ++n$1.elem;
    }

    private MurmurHash$() {
    }
}

