/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005u3qAF\f\u0011\u0002\u0007\u0005A\u0004C\u0003\"\u0001\u0011\u0005!\u0005C\u0003'\u0001\u0019\u0005q\u0005C\u0003,\u0001\u0019\u0005A\u0006C\u00031\u0001\u0019\u0005\u0011\u0007C\u00036\u0001\u0019\u0005a\u0007C\u0003;\u0001\u0019\u00051\bC\u0003@\u0001\u0019\u0005\u0001\tC\u0003E\u0001\u0019\u0005Q\tC\u0003J\u0001\u0011\u0005!\nC\u0003O\u0001\u0011\u0005A\u0006C\u0003P\u0001\u0011\u0005\u0011\u0007C\u0003Q\u0001\u0011\u0005a\u0007C\u0003R\u0001\u0011\u00051\bC\u0003S\u0001\u0011\u0005\u0001\tC\u0003T\u0001\u0011\u0005Q\tC\u0003U\u0001\u0011\u0005q\u0005C\u0003V\u0001\u0011\u0005q\u0005C\u0003W\u0001\u0011\u0005q\u0005C\u0003X\u0001\u0011\u0005q\u0005C\u0003Y\u0001\u0011Ea\u0007C\u0003Z\u0001\u0011E!L\u0001\u000eTG\u0006d\u0017MT;nKJL7-\u00118z\u0007>tg/\u001a:tS>t7O\u0003\u0002\u00193\u0005!Q.\u0019;i\u0015\u0005Q\u0012!B:dC2\f7\u0001A\n\u0003\u0001u\u0001\"AH\u0010\u000e\u0003eI!\u0001I\r\u0003\u0007\u0005s\u00170\u0001\u0004%S:LG\u000f\n\u000b\u0002GA\u0011a\u0004J\u0005\u0003Ke\u0011A!\u00168ji\u00069\u0011n],i_2,W#\u0001\u0015\u0011\u0005yI\u0013B\u0001\u0016\u001a\u0005\u001d\u0011un\u001c7fC:\f\u0011BY=uKZ\u000bG.^3\u0016\u00035\u0002\"A\b\u0018\n\u0005=J\"\u0001\u0002\"zi\u0016\f!b\u001d5peR4\u0016\r\\;f+\u0005\u0011\u0004C\u0001\u00104\u0013\t!\u0014DA\u0003TQ>\u0014H/\u0001\u0005j]R4\u0016\r\\;f+\u00059\u0004C\u0001\u00109\u0013\tI\u0014DA\u0002J]R\f\u0011\u0002\\8oOZ\u000bG.^3\u0016\u0003q\u0002\"AH\u001f\n\u0005yJ\"\u0001\u0002'p]\u001e\f!B\u001a7pCR4\u0016\r\\;f+\u0005\t\u0005C\u0001\u0010C\u0013\t\u0019\u0015DA\u0003GY>\fG/A\u0006e_V\u0014G.\u001a,bYV,W#\u0001$\u0011\u0005y9\u0015B\u0001%\u001a\u0005\u0019!u.\u001e2mK\u00061Ao\\\"iCJ,\u0012a\u0013\t\u0003=1K!!T\r\u0003\t\rC\u0017M]\u0001\u0007i>\u0014\u0015\u0010^3\u0002\u000fQ|7\u000b[8si\u0006)Ao\\%oi\u00061Ao\u001c'p]\u001e\fq\u0001^8GY>\fG/\u0001\u0005u_\u0012{WO\u00197f\u0003-I7OV1mS\u0012\u0014\u0015\u0010^3\u0002\u0019%\u001ch+\u00197jINCwN\u001d;\u0002\u0015%\u001ch+\u00197jI&sG/A\u0006jgZ\u000bG.\u001b3DQ\u0006\u0014\u0018\u0001G;oS\u001aLW\r\u001a)sS6LG/\u001b<f\u0011\u0006\u001c\bnY8eK\u00061RO\\5gS\u0016$\u0007K]5nSRLg/Z#rk\u0006d7\u000f\u0006\u0002)7\")A,\u0006a\u0001;\u0005\t\u0001\u0010")
public interface ScalaNumericAnyConversions {
    public boolean isWhole();

    public byte byteValue();

    public short shortValue();

    public int intValue();

    public long longValue();

    public float floatValue();

    public double doubleValue();

    public static /* synthetic */ char toChar$(ScalaNumericAnyConversions $this) {
        return $this.toChar();
    }

    default public char toChar() {
        return (char)this.intValue();
    }

    public static /* synthetic */ byte toByte$(ScalaNumericAnyConversions $this) {
        return $this.toByte();
    }

    default public byte toByte() {
        return this.byteValue();
    }

    public static /* synthetic */ short toShort$(ScalaNumericAnyConversions $this) {
        return $this.toShort();
    }

    default public short toShort() {
        return this.shortValue();
    }

    public static /* synthetic */ int toInt$(ScalaNumericAnyConversions $this) {
        return $this.toInt();
    }

    default public int toInt() {
        return this.intValue();
    }

    public static /* synthetic */ long toLong$(ScalaNumericAnyConversions $this) {
        return $this.toLong();
    }

    default public long toLong() {
        return this.longValue();
    }

    public static /* synthetic */ float toFloat$(ScalaNumericAnyConversions $this) {
        return $this.toFloat();
    }

    default public float toFloat() {
        return this.floatValue();
    }

    public static /* synthetic */ double toDouble$(ScalaNumericAnyConversions $this) {
        return $this.toDouble();
    }

    default public double toDouble() {
        return this.doubleValue();
    }

    public static /* synthetic */ boolean isValidByte$(ScalaNumericAnyConversions $this) {
        return $this.isValidByte();
    }

    default public boolean isValidByte() {
        return this.isWhole() && this.toInt() == this.toByte();
    }

    public static /* synthetic */ boolean isValidShort$(ScalaNumericAnyConversions $this) {
        return $this.isValidShort();
    }

    default public boolean isValidShort() {
        return this.isWhole() && this.toInt() == this.toShort();
    }

    public static /* synthetic */ boolean isValidInt$(ScalaNumericAnyConversions $this) {
        return $this.isValidInt();
    }

    default public boolean isValidInt() {
        return this.isWhole() && this.toLong() == (long)this.toInt();
    }

    public static /* synthetic */ boolean isValidChar$(ScalaNumericAnyConversions $this) {
        return $this.isValidChar();
    }

    default public boolean isValidChar() {
        return this.isWhole() && this.toInt() >= 0 && this.toInt() <= 65535;
    }

    public static /* synthetic */ int unifiedPrimitiveHashcode$(ScalaNumericAnyConversions $this) {
        return $this.unifiedPrimitiveHashcode();
    }

    default public int unifiedPrimitiveHashcode() {
        long lv = this.toLong();
        if (lv >= Integer.MIN_VALUE && lv <= Integer.MAX_VALUE) {
            return (int)lv;
        }
        return Statics.longHash(lv);
    }

    public static /* synthetic */ boolean unifiedPrimitiveEquals$(ScalaNumericAnyConversions $this, Object x) {
        return $this.unifiedPrimitiveEquals(x);
    }

    default public boolean unifiedPrimitiveEquals(Object x) {
        if (x instanceof Character) {
            char c = BoxesRunTime.unboxToChar(x);
            return this.isValidChar() && this.toInt() == c;
        }
        if (x instanceof Byte) {
            byte by = BoxesRunTime.unboxToByte(x);
            return this.isValidByte() && this.toByte() == by;
        }
        if (x instanceof Short) {
            short s2 = BoxesRunTime.unboxToShort(x);
            return this.isValidShort() && this.toShort() == s2;
        }
        if (x instanceof Integer) {
            int n = BoxesRunTime.unboxToInt(x);
            return this.isValidInt() && this.toInt() == n;
        }
        if (x instanceof Long) {
            long l = BoxesRunTime.unboxToLong(x);
            return this.toLong() == l;
        }
        if (x instanceof Float) {
            float f = BoxesRunTime.unboxToFloat(x);
            return this.toFloat() == f;
        }
        if (x instanceof Double) {
            double d = BoxesRunTime.unboxToDouble(x);
            return this.toDouble() == d;
        }
        return false;
    }

    public static void $init$(ScalaNumericAnyConversions $this) {
    }
}

