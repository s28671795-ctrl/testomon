/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.LowPriorityImplicits2;
import scala.collection.immutable.WrappedString;
import scala.collection.mutable.ArraySeq;
import scala.collection.mutable.ArraySeq$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005uhA\u0002\f\u0018\u0003\u00039\u0012\u0004C\u0003\u001f\u0001\u0011\u0005\u0001\u0005C\u0003#\u0001\u0011\r1\u0005C\u00034\u0001\u0011\rA\u0007C\u0003>\u0001\u0011\ra\bC\u0003H\u0001\u0011\r\u0001\nC\u0003S\u0001\u0011\r1\u000bC\u0003]\u0001\u0011\rQ\fC\u0003g\u0001\u0011\rq\rC\u0003q\u0001\u0011\r\u0011\u000fC\u0003{\u0001\u0011\r1\u0010C\u0004\u0002,\u0001!\u0019!!\f\t\u000f\u0005=\u0003\u0001b\u0001\u0002R!9\u0011Q\f\u0001\u0005\u0004\u0005}\u0003bBA6\u0001\u0011\r\u0011Q\u000e\u0005\b\u0003s\u0002A1AA>\u0011\u001d\t9\t\u0001C\u0002\u0003\u0013Cq!!&\u0001\t\u0007\t9\nC\u0004\u0002$\u0002!\u0019!!*\t\u000f\u0005E\u0006\u0001b\u0001\u00024\"9\u0011q\u0018\u0001\u0005\u0004\u0005\u0005\u0007bBAj\u0001\u0011\r\u0011Q\u001b\u0002\u0015\u0019><\bK]5pe&$\u00180S7qY&\u001c\u0017\u000e^:\u000b\u0003a\tQa]2bY\u0006\u001c\"\u0001\u0001\u000e\u0011\u0005maR\"A\f\n\u0005u9\"!\u0006'poB\u0013\u0018n\u001c:jifLU\u000e\u001d7jG&$8OM\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\t\u0011\u0005\u0005\u0002\u001c\u0001\u0005Y!-\u001f;f/J\f\u0007\u000f]3s)\t!#\u0006\u0005\u0002&Q5\taE\u0003\u0002(/\u00059!/\u001e8uS6,\u0017BA\u0015'\u0005!\u0011\u0016n\u00195CsR,\u0007\"B\u0016\u0003\u0001\u0004a\u0013!\u0001=\u0011\u0005mi\u0013B\u0001\u0018\u0018\u0005\u0011\u0011\u0015\u0010^3)\u0005\t\u0001\u0004CA\u000e2\u0013\t\u0011tC\u0001\u0004j]2Lg.Z\u0001\rg\"|'\u000f^,sCB\u0004XM\u001d\u000b\u0003ka\u0002\"!\n\u001c\n\u0005]2#!\u0003*jG\"\u001c\u0006n\u001c:u\u0011\u0015Y3\u00011\u0001:!\tY\"(\u0003\u0002</\t)1\u000b[8si\"\u00121\u0001M\u0001\u000bS:$xK]1qa\u0016\u0014HCA C!\t)\u0003)\u0003\u0002BM\t9!+[2i\u0013:$\b\"B\u0016\u0005\u0001\u0004\u0019\u0005CA\u000eE\u0013\t)uCA\u0002J]RD#\u0001\u0002\u0019\u0002\u0017\rD\u0017M],sCB\u0004XM\u001d\u000b\u0003\u00132\u0003\"!\n&\n\u0005-3#\u0001\u0003*jG\"\u001c\u0005.\u0019:\t\u000b5+\u0001\u0019\u0001(\u0002\u0003\r\u0004\"aG(\n\u0005A;\"\u0001B\"iCJD#!\u0002\u0019\u0002\u00171|gnZ,sCB\u0004XM\u001d\u000b\u0003)^\u0003\"!J+\n\u0005Y3#\u0001\u0003*jG\"duN\\4\t\u000b-2\u0001\u0019\u0001-\u0011\u0005mI\u0016B\u0001.\u0018\u0005\u0011auN\\4)\u0005\u0019\u0001\u0014\u0001\u00044m_\u0006$xK]1qa\u0016\u0014HC\u00010b!\t)s,\u0003\u0002aM\tI!+[2i\r2|\u0017\r\u001e\u0005\u0006W\u001d\u0001\rA\u0019\t\u00037\rL!\u0001Z\f\u0003\u000b\u0019cw.\u0019;)\u0005\u001d\u0001\u0014!\u00043pk\ndWm\u0016:baB,'\u000f\u0006\u0002iWB\u0011Q%[\u0005\u0003U\u001a\u0012!BU5dQ\u0012{WO\u00197f\u0011\u0015Y\u0003\u00021\u0001m!\tYR.\u0003\u0002o/\t1Ai\\;cY\u0016D#\u0001\u0003\u0019\u0002\u001d\t|w\u000e\\3b]^\u0013\u0018\r\u001d9feR\u0011!/\u001e\t\u0003KML!\u0001\u001e\u0014\u0003\u0017IK7\r\u001b\"p_2,\u0017M\u001c\u0005\u0006W%\u0001\rA\u001e\t\u00037]L!\u0001_\f\u0003\u000f\t{w\u000e\\3b]\"\u0012\u0011\u0002M\u0001\u0011O\u0016tWM]5d/J\f\u0007/\u0011:sCf,2\u0001`A\b)\ri\u0018\u0011\u0005\t\u0006}\u0006\u001d\u00111B\u0007\u0002\u007f*!\u0011\u0011AA\u0002\u0003\u001diW\u000f^1cY\u0016T1!!\u0002\u0018\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0004\u0003\u0013y(\u0001C!se\u0006L8+Z9\u0011\t\u00055\u0011q\u0002\u0007\u0001\t\u001d\t\tB\u0003b\u0001\u0003'\u0011\u0011\u0001V\t\u0005\u0003+\tY\u0002E\u0002\u001c\u0003/I1!!\u0007\u0018\u0005\u001dqu\u000e\u001e5j]\u001e\u00042aGA\u000f\u0013\r\tyb\u0006\u0002\u0004\u0003:L\bbBA\u0012\u0015\u0001\u0007\u0011QE\u0001\u0003qN\u0004RaGA\u0014\u0003\u0017I1!!\u000b\u0018\u0005\u0015\t%O]1z\u000319(/\u00199SK\u001a\f%O]1z+\u0011\ty#!\u0011\u0015\t\u0005E\u00121\n\t\u0007\u0003g\tI$a\u0010\u000f\u0007y\f)$C\u0002\u00028}\f\u0001\"\u0011:sCf\u001cV-]\u0005\u0005\u0003w\tiDA\u0003pMJ+gMC\u0002\u00028}\u0004B!!\u0004\u0002B\u00119\u0011\u0011C\u0006C\u0002\u0005\r\u0013\u0003BA\u000b\u0003\u000b\u00022aGA$\u0013\r\tIe\u0006\u0002\u0007\u0003:L(+\u001a4\t\u000f\u0005\r2\u00021\u0001\u0002NA)1$a\n\u0002@\u0005aqO]1q\u0013:$\u0018I\u001d:bsR!\u00111KA-!\u0011\t\u0019$!\u0016\n\t\u0005]\u0013Q\b\u0002\u0006_\u001aLe\u000e\u001e\u0005\b\u0003Ga\u0001\u0019AA.!\u0011Y\u0012qE\"\u0002\u001f]\u0014\u0018\r\u001d#pk\ndW-\u0011:sCf$B!!\u0019\u0002hA!\u00111GA2\u0013\u0011\t)'!\u0010\u0003\u0011=4Gi\\;cY\u0016Dq!a\t\u000e\u0001\u0004\tI\u0007\u0005\u0003\u001c\u0003Oa\u0017!D<sCBduN\\4BeJ\f\u0017\u0010\u0006\u0003\u0002p\u0005U\u0004\u0003BA\u001a\u0003cJA!a\u001d\u0002>\t1qN\u001a'p]\u001eDq!a\t\u000f\u0001\u0004\t9\b\u0005\u0003\u001c\u0003OA\u0016AD<sCB4En\\1u\u0003J\u0014\u0018-\u001f\u000b\u0005\u0003{\n\u0019\t\u0005\u0003\u00024\u0005}\u0014\u0002BAA\u0003{\u0011qa\u001c4GY>\fG\u000fC\u0004\u0002$=\u0001\r!!\"\u0011\tm\t9CY\u0001\u000eoJ\f\u0007o\u00115be\u0006\u0013(/Y=\u0015\t\u0005-\u0015\u0011\u0013\t\u0005\u0003g\ti)\u0003\u0003\u0002\u0010\u0006u\"AB8g\u0007\"\f'\u000fC\u0004\u0002$A\u0001\r!a%\u0011\tm\t9CT\u0001\u000eoJ\f\u0007OQ=uK\u0006\u0013(/Y=\u0015\t\u0005e\u0015q\u0014\t\u0005\u0003g\tY*\u0003\u0003\u0002\u001e\u0006u\"AB8g\u0005f$X\rC\u0004\u0002$E\u0001\r!!)\u0011\tm\t9\u0003L\u0001\u000foJ\f\u0007o\u00155peR\f%O]1z)\u0011\t9+!,\u0011\t\u0005M\u0012\u0011V\u0005\u0005\u0003W\u000biDA\u0004pMNCwN\u001d;\t\u000f\u0005\r\"\u00031\u0001\u00020B!1$a\n:\u0003A9(/\u00199C_>dW-\u00198BeJ\f\u0017\u0010\u0006\u0003\u00026\u0006m\u0006\u0003BA\u001a\u0003oKA!!/\u0002>\tIqN\u001a\"p_2,\u0017M\u001c\u0005\b\u0003G\u0019\u0002\u0019AA_!\u0011Y\u0012q\u0005<\u0002\u001b]\u0014\u0018\r]+oSR\f%O]1z)\u0011\t\u0019-!3\u0011\t\u0005M\u0012QY\u0005\u0005\u0003\u000f\fiD\u0001\u0004pMVs\u0017\u000e\u001e\u0005\b\u0003G!\u0002\u0019AAf!\u0015Y\u0012qEAg!\rY\u0012qZ\u0005\u0004\u0003#<\"\u0001B+oSR\f!b\u001e:baN#(/\u001b8h)\u0011\t9.a9\u0011\t\u0005e\u0017q\\\u0007\u0003\u00037TA!!8\u0002\u0004\u0005I\u0011.\\7vi\u0006\u0014G.Z\u0005\u0005\u0003C\fYNA\u0007Xe\u0006\u0004\b/\u001a3TiJLgn\u001a\u0005\b\u0003K,\u0002\u0019AAt\u0003\u0005\u0019\b\u0003BAu\u0003otA!a;\u0002tB\u0019\u0011Q^\f\u000e\u0005\u0005=(bAAy?\u00051AH]8pizJ1!!>\u0018\u0003\u0019\u0001&/\u001a3fM&!\u0011\u0011`A~\u0005\u0019\u0019FO]5oO*\u0019\u0011Q_\f")
public abstract class LowPriorityImplicits
extends LowPriorityImplicits2 {
    public byte byteWrapper(byte x) {
        return x;
    }

    public short shortWrapper(short x) {
        return x;
    }

    public int intWrapper(int x) {
        return x;
    }

    public char charWrapper(char c) {
        return c;
    }

    public long longWrapper(long x) {
        return x;
    }

    public float floatWrapper(float x) {
        return x;
    }

    public double doubleWrapper(double x) {
        return x;
    }

    public boolean booleanWrapper(boolean x) {
        return x;
    }

    public <T> ArraySeq<T> genericWrapArray(Object xs) {
        if (xs == null) {
            return null;
        }
        return ArraySeq$.MODULE$.make(xs);
    }

    public <T> ArraySeq.ofRef<T> wrapRefArray(T[] xs) {
        if (xs == null) {
            return null;
        }
        if (xs.length == 0) {
            return (ArraySeq.ofRef)ArraySeq$.MODULE$.empty(ClassTag$.MODULE$.AnyRef());
        }
        return new ArraySeq.ofRef<T>(xs);
    }

    public ArraySeq.ofInt wrapIntArray(int[] xs) {
        if (xs != null) {
            return new ArraySeq.ofInt(xs);
        }
        return null;
    }

    public ArraySeq.ofDouble wrapDoubleArray(double[] xs) {
        if (xs != null) {
            return new ArraySeq.ofDouble(xs);
        }
        return null;
    }

    public ArraySeq.ofLong wrapLongArray(long[] xs) {
        if (xs != null) {
            return new ArraySeq.ofLong(xs);
        }
        return null;
    }

    public ArraySeq.ofFloat wrapFloatArray(float[] xs) {
        if (xs != null) {
            return new ArraySeq.ofFloat(xs);
        }
        return null;
    }

    public ArraySeq.ofChar wrapCharArray(char[] xs) {
        if (xs != null) {
            return new ArraySeq.ofChar(xs);
        }
        return null;
    }

    public ArraySeq.ofByte wrapByteArray(byte[] xs) {
        if (xs != null) {
            return new ArraySeq.ofByte(xs);
        }
        return null;
    }

    public ArraySeq.ofShort wrapShortArray(short[] xs) {
        if (xs != null) {
            return new ArraySeq.ofShort(xs);
        }
        return null;
    }

    public ArraySeq.ofBoolean wrapBooleanArray(boolean[] xs) {
        if (xs != null) {
            return new ArraySeq.ofBoolean(xs);
        }
        return null;
    }

    public ArraySeq.ofUnit wrapUnitArray(BoxedUnit[] xs) {
        if (xs != null) {
            return new ArraySeq.ofUnit(xs);
        }
        return null;
    }

    public WrappedString wrapString(String s2) {
        if (s2 != null) {
            return new WrappedString(s2);
        }
        return null;
    }
}

