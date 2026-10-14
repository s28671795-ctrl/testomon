/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.$less$colon$less$;
import scala.Console$;
import scala.Function0;
import scala.Function1;
import scala.LowPriorityImplicits;
import scala.NotImplementedError;
import scala.Predef;
import scala.Tuple2;
import scala.Tuple2$;
import scala.Tuple3;
import scala.collection.IndexedSeq;
import scala.collection.StringOps$;
import scala.collection.immutable.Map$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set$;
import scala.reflect.Manifest;
import scala.reflect.Manifest$;
import scala.reflect.NoManifest$;
import scala.reflect.OptManifest;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class Predef$
extends LowPriorityImplicits {
    public static final Predef$ MODULE$ = new Predef$();
    private static final Map$ Map = Map$.MODULE$;
    private static final Set$ Set = Set$.MODULE$;
    private static final Tuple2$ $minus$greater = Tuple2$.MODULE$;
    private static final Manifest$ Manifest = Manifest$.MODULE$;
    private static final NoManifest$ NoManifest = NoManifest$.MODULE$;

    public <T> Class<T> classOf() {
        return null;
    }

    public <T> T valueOf(T vt) {
        return vt;
    }

    public Map$ Map() {
        return Map;
    }

    public Set$ Set() {
        return Set;
    }

    public Tuple2$ $minus$greater() {
        return $minus$greater;
    }

    public Manifest$ Manifest() {
        return Manifest;
    }

    public NoManifest$ NoManifest() {
        return NoManifest;
    }

    public <T> Manifest<T> manifest(Manifest<T> m4) {
        return m4;
    }

    public <T> OptManifest<T> optManifest(OptManifest<T> m4) {
        return m4;
    }

    public <A> A identity(A x) {
        return x;
    }

    public <T> T implicitly(T e) {
        return e;
    }

    public <T> T locally(T x) {
        return x;
    }

    public void assert(boolean assertion) {
        if (!assertion) {
            throw new AssertionError((Object)"assertion failed");
        }
    }

    public final void assert(boolean assertion, Function0<Object> message) {
        if (!assertion) {
            throw new AssertionError((Object)new StringBuilder(18).append("assertion failed: ").append(message.apply()).toString());
        }
    }

    public void assume(boolean assumption) {
        if (!assumption) {
            throw new AssertionError((Object)"assumption failed");
        }
    }

    public final void assume(boolean assumption, Function0<Object> message) {
        if (!assumption) {
            throw new AssertionError((Object)new StringBuilder(19).append("assumption failed: ").append(message.apply()).toString());
        }
    }

    public void require(boolean requirement) {
        if (!requirement) {
            throw new IllegalArgumentException("requirement failed");
        }
    }

    public final void require(boolean requirement, Function0<Object> message) {
        if (!requirement) {
            throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append(message.apply()).toString());
        }
    }

    public Nothing$ $qmark$qmark$qmark() {
        throw new NotImplementedError();
    }

    public final <A> A ArrowAssoc(A self) {
        return self;
    }

    public final <A> A Ensuring(A self) {
        return self;
    }

    public final <A> A StringFormat(A self) {
        return self;
    }

    public final <A> A any2stringadd(A self) {
        return self;
    }

    public Predef.SeqCharSequence SeqCharSequence(IndexedSeq<Object> sequenceOfChars) {
        return new Predef.SeqCharSequence(sequenceOfChars);
    }

    public Predef.ArrayCharSequence ArrayCharSequence(char[] arrayOfChars) {
        return new Predef.ArrayCharSequence(arrayOfChars);
    }

    public String augmentString(String x) {
        return x;
    }

    public void print(Object x) {
        Console$.MODULE$.print(x);
    }

    public void println() {
        Console$.MODULE$.println();
    }

    public void println(Object x) {
        Console$.MODULE$.println(x);
    }

    public void printf(String text, Seq<Object> xs) {
        Console$.MODULE$.print(StringOps$.MODULE$.format$extension(text, xs));
    }

    public <T1, T2> Tuple2<T1, T2> tuple2ToZippedOps(Tuple2<T1, T2> x) {
        return x;
    }

    public <T1, T2, T3> Tuple3<T1, T2, T3> tuple3ToZippedOps(Tuple3<T1, T2, T3> x) {
        return x;
    }

    public <T> Object genericArrayOps(Object xs) {
        return xs;
    }

    public boolean[] booleanArrayOps(boolean[] xs) {
        return xs;
    }

    public byte[] byteArrayOps(byte[] xs) {
        return xs;
    }

    public char[] charArrayOps(char[] xs) {
        return xs;
    }

    public double[] doubleArrayOps(double[] xs) {
        return xs;
    }

    public float[] floatArrayOps(float[] xs) {
        return xs;
    }

    public int[] intArrayOps(int[] xs) {
        return xs;
    }

    public long[] longArrayOps(long[] xs) {
        return xs;
    }

    public <T> T[] refArrayOps(T[] xs) {
        return xs;
    }

    public short[] shortArrayOps(short[] xs) {
        return xs;
    }

    public BoxedUnit[] unitArrayOps(BoxedUnit[] xs) {
        return xs;
    }

    public Byte byte2Byte(byte x) {
        return x;
    }

    public Short short2Short(short x) {
        return x;
    }

    public Character char2Character(char x) {
        return Character.valueOf(x);
    }

    public Integer int2Integer(int x) {
        return x;
    }

    public Long long2Long(long x) {
        return x;
    }

    public Float float2Float(float x) {
        return Float.valueOf(x);
    }

    public Double double2Double(double x) {
        return x;
    }

    public Boolean boolean2Boolean(boolean x) {
        return x;
    }

    public byte Byte2byte(Byte x) {
        return BoxesRunTime.unboxToByte(x);
    }

    public short Short2short(Short x) {
        return BoxesRunTime.unboxToShort(x);
    }

    public char Character2char(Character x) {
        return BoxesRunTime.unboxToChar(x);
    }

    public int Integer2int(Integer x) {
        return BoxesRunTime.unboxToInt(x);
    }

    public long Long2long(Long x) {
        return BoxesRunTime.unboxToLong(x);
    }

    public float Float2float(Float x) {
        return BoxesRunTime.unboxToFloat(x);
    }

    public double Double2double(Double x) {
        return BoxesRunTime.unboxToDouble(x);
    }

    public boolean Boolean2boolean(Boolean x) {
        return BoxesRunTime.unboxToBoolean(x);
    }

    public <A> Function1<A, A> $conforms() {
        return $less$colon$less$.MODULE$.refl();
    }

    private Predef$() {
    }
}

