/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.math.ScalaNumber;

public final class BoxesRunTime {
    private static final int CHAR = 0;
    private static final int INT = 3;
    private static final int LONG = 4;
    private static final int FLOAT = 5;
    private static final int DOUBLE = 6;
    private static final int OTHER = 7;

    private static int typeCode(Object a) {
        if (a instanceof Integer) {
            return 3;
        }
        if (a instanceof Double) {
            return 6;
        }
        if (a instanceof Long) {
            return 4;
        }
        if (a instanceof Character) {
            return 0;
        }
        if (a instanceof Float) {
            return 5;
        }
        if (a instanceof Byte || a instanceof Short) {
            return 3;
        }
        return 7;
    }

    public static Boolean boxToBoolean(boolean b) {
        return b;
    }

    public static Character boxToCharacter(char c) {
        return Character.valueOf(c);
    }

    public static Byte boxToByte(byte b) {
        return b;
    }

    public static Short boxToShort(short s2) {
        return s2;
    }

    public static Integer boxToInteger(int i) {
        return i;
    }

    public static Long boxToLong(long l) {
        return l;
    }

    public static Float boxToFloat(float f) {
        return Float.valueOf(f);
    }

    public static Double boxToDouble(double d) {
        return d;
    }

    public static boolean unboxToBoolean(Object b) {
        return b == null ? false : (Boolean)b;
    }

    public static char unboxToChar(Object c) {
        return c == null ? (char)'\u0000' : ((Character)c).charValue();
    }

    public static byte unboxToByte(Object b) {
        return b == null ? (byte)0 : (Byte)b;
    }

    public static short unboxToShort(Object s2) {
        return s2 == null ? (short)0 : (Short)s2;
    }

    public static int unboxToInt(Object i) {
        return i == null ? 0 : (Integer)i;
    }

    public static long unboxToLong(Object l) {
        return l == null ? 0L : (Long)l;
    }

    public static float unboxToFloat(Object f) {
        return f == null ? 0.0f : ((Float)f).floatValue();
    }

    public static double unboxToDouble(Object d) {
        return d == null ? 0.0 : (Double)d;
    }

    public static boolean equals(Object x, Object y) {
        if (x == y) {
            return true;
        }
        return BoxesRunTime.equals2(x, y);
    }

    public static boolean equals2(Object x, Object y) {
        if (x instanceof Number) {
            return BoxesRunTime.equalsNumObject((Number)x, y);
        }
        if (x instanceof Character) {
            return BoxesRunTime.equalsCharObject((Character)x, y);
        }
        if (x == null) {
            return y == null;
        }
        return x.equals(y);
    }

    public static boolean equalsNumObject(Number xn, Object y) {
        if (y instanceof Number) {
            return BoxesRunTime.equalsNumNum(xn, (Number)y);
        }
        if (y instanceof Character) {
            return BoxesRunTime.equalsNumChar(xn, (Character)y);
        }
        if (xn == null) {
            return y == null;
        }
        return xn.equals(y);
    }

    public static boolean equalsNumNum(Number xn, Number yn) {
        int xcode = BoxesRunTime.typeCode(xn);
        int ycode = BoxesRunTime.typeCode(yn);
        switch (ycode > xcode ? ycode : xcode) {
            case 3: {
                return xn.intValue() == yn.intValue();
            }
            case 4: {
                return xn.longValue() == yn.longValue();
            }
            case 5: {
                return xn.floatValue() == yn.floatValue();
            }
            case 6: {
                return xn.doubleValue() == yn.doubleValue();
            }
        }
        if (yn instanceof ScalaNumber && !(xn instanceof ScalaNumber)) {
            return yn.equals(xn);
        }
        if (xn == null) {
            return yn == null;
        }
        return xn.equals(yn);
    }

    public static boolean equalsCharObject(Character xc, Object y) {
        if (y instanceof Character) {
            return xc.charValue() == ((Character)y).charValue();
        }
        if (y instanceof Number) {
            return BoxesRunTime.equalsNumChar((Number)y, xc);
        }
        if (xc == null) {
            return y == null;
        }
        return xc.equals(y);
    }

    public static boolean equalsNumChar(Number xn, Character yc) {
        if (yc == null) {
            return xn == null;
        }
        char ch = yc.charValue();
        switch (BoxesRunTime.typeCode(xn)) {
            case 3: {
                return xn.intValue() == ch;
            }
            case 4: {
                return xn.longValue() == (long)ch;
            }
            case 5: {
                return xn.floatValue() == (float)ch;
            }
            case 6: {
                return xn.doubleValue() == (double)ch;
            }
        }
        return xn.equals(yc);
    }

    private static int unboxCharOrInt(Object arg1, int code) {
        if (code == 0) {
            return ((Character)arg1).charValue();
        }
        return ((Number)arg1).intValue();
    }

    private static long unboxCharOrLong(Object arg1, int code) {
        if (code == 0) {
            return ((Character)arg1).charValue();
        }
        return ((Number)arg1).longValue();
    }

    private static float unboxCharOrFloat(Object arg1, int code) {
        if (code == 0) {
            return ((Character)arg1).charValue();
        }
        return ((Number)arg1).floatValue();
    }

    private static double unboxCharOrDouble(Object arg1, int code) {
        if (code == 0) {
            return ((Character)arg1).charValue();
        }
        return ((Number)arg1).doubleValue();
    }

    public static Object add(Object arg1, Object arg2) throws NoSuchMethodException {
        int code2;
        int maxcode;
        int code1 = BoxesRunTime.typeCode(arg1);
        int n = maxcode = code1 < (code2 = BoxesRunTime.typeCode(arg2)) ? code2 : code1;
        if (maxcode <= 3) {
            return BoxesRunTime.boxToInteger(BoxesRunTime.unboxCharOrInt(arg1, code1) + BoxesRunTime.unboxCharOrInt(arg2, code2));
        }
        if (maxcode <= 4) {
            return BoxesRunTime.boxToLong(BoxesRunTime.unboxCharOrLong(arg1, code1) + BoxesRunTime.unboxCharOrLong(arg2, code2));
        }
        if (maxcode <= 5) {
            return BoxesRunTime.boxToFloat(BoxesRunTime.unboxCharOrFloat(arg1, code1) + BoxesRunTime.unboxCharOrFloat(arg2, code2));
        }
        if (maxcode <= 6) {
            return BoxesRunTime.boxToDouble(BoxesRunTime.unboxCharOrDouble(arg1, code1) + BoxesRunTime.unboxCharOrDouble(arg2, code2));
        }
        throw new NoSuchMethodException();
    }

    public static Object subtract(Object arg1, Object arg2) throws NoSuchMethodException {
        int code2;
        int maxcode;
        int code1 = BoxesRunTime.typeCode(arg1);
        int n = maxcode = code1 < (code2 = BoxesRunTime.typeCode(arg2)) ? code2 : code1;
        if (maxcode <= 3) {
            return BoxesRunTime.boxToInteger(BoxesRunTime.unboxCharOrInt(arg1, code1) - BoxesRunTime.unboxCharOrInt(arg2, code2));
        }
        if (maxcode <= 4) {
            return BoxesRunTime.boxToLong(BoxesRunTime.unboxCharOrLong(arg1, code1) - BoxesRunTime.unboxCharOrLong(arg2, code2));
        }
        if (maxcode <= 5) {
            return BoxesRunTime.boxToFloat(BoxesRunTime.unboxCharOrFloat(arg1, code1) - BoxesRunTime.unboxCharOrFloat(arg2, code2));
        }
        if (maxcode <= 6) {
            return BoxesRunTime.boxToDouble(BoxesRunTime.unboxCharOrDouble(arg1, code1) - BoxesRunTime.unboxCharOrDouble(arg2, code2));
        }
        throw new NoSuchMethodException();
    }

    public static Object multiply(Object arg1, Object arg2) throws NoSuchMethodException {
        int code2;
        int maxcode;
        int code1 = BoxesRunTime.typeCode(arg1);
        int n = maxcode = code1 < (code2 = BoxesRunTime.typeCode(arg2)) ? code2 : code1;
        if (maxcode <= 3) {
            return BoxesRunTime.boxToInteger(BoxesRunTime.unboxCharOrInt(arg1, code1) * BoxesRunTime.unboxCharOrInt(arg2, code2));
        }
        if (maxcode <= 4) {
            return BoxesRunTime.boxToLong(BoxesRunTime.unboxCharOrLong(arg1, code1) * BoxesRunTime.unboxCharOrLong(arg2, code2));
        }
        if (maxcode <= 5) {
            return BoxesRunTime.boxToFloat(BoxesRunTime.unboxCharOrFloat(arg1, code1) * BoxesRunTime.unboxCharOrFloat(arg2, code2));
        }
        if (maxcode <= 6) {
            return BoxesRunTime.boxToDouble(BoxesRunTime.unboxCharOrDouble(arg1, code1) * BoxesRunTime.unboxCharOrDouble(arg2, code2));
        }
        throw new NoSuchMethodException();
    }

    public static Object divide(Object arg1, Object arg2) throws NoSuchMethodException {
        int code2;
        int maxcode;
        int code1 = BoxesRunTime.typeCode(arg1);
        int n = maxcode = code1 < (code2 = BoxesRunTime.typeCode(arg2)) ? code2 : code1;
        if (maxcode <= 3) {
            return BoxesRunTime.boxToInteger(BoxesRunTime.unboxCharOrInt(arg1, code1) / BoxesRunTime.unboxCharOrInt(arg2, code2));
        }
        if (maxcode <= 4) {
            return BoxesRunTime.boxToLong(BoxesRunTime.unboxCharOrLong(arg1, code1) / BoxesRunTime.unboxCharOrLong(arg2, code2));
        }
        if (maxcode <= 5) {
            return BoxesRunTime.boxToFloat(BoxesRunTime.unboxCharOrFloat(arg1, code1) / BoxesRunTime.unboxCharOrFloat(arg2, code2));
        }
        if (maxcode <= 6) {
            return BoxesRunTime.boxToDouble(BoxesRunTime.unboxCharOrDouble(arg1, code1) / BoxesRunTime.unboxCharOrDouble(arg2, code2));
        }
        throw new NoSuchMethodException();
    }

    public static Object takeModulo(Object arg1, Object arg2) throws NoSuchMethodException {
        int code2;
        int maxcode;
        int code1 = BoxesRunTime.typeCode(arg1);
        int n = maxcode = code1 < (code2 = BoxesRunTime.typeCode(arg2)) ? code2 : code1;
        if (maxcode <= 3) {
            return BoxesRunTime.boxToInteger(BoxesRunTime.unboxCharOrInt(arg1, code1) % BoxesRunTime.unboxCharOrInt(arg2, code2));
        }
        if (maxcode <= 4) {
            return BoxesRunTime.boxToLong(BoxesRunTime.unboxCharOrLong(arg1, code1) % BoxesRunTime.unboxCharOrLong(arg2, code2));
        }
        if (maxcode <= 5) {
            return BoxesRunTime.boxToFloat(BoxesRunTime.unboxCharOrFloat(arg1, code1) % BoxesRunTime.unboxCharOrFloat(arg2, code2));
        }
        if (maxcode <= 6) {
            return BoxesRunTime.boxToDouble(BoxesRunTime.unboxCharOrDouble(arg1, code1) % BoxesRunTime.unboxCharOrDouble(arg2, code2));
        }
        throw new NoSuchMethodException();
    }

    public static Object shiftSignedRight(Object arg1, Object arg2) throws NoSuchMethodException {
        int code1 = BoxesRunTime.typeCode(arg1);
        int code2 = BoxesRunTime.typeCode(arg2);
        if (code1 <= 3) {
            int val1 = BoxesRunTime.unboxCharOrInt(arg1, code1);
            if (code2 <= 3) {
                int val2 = BoxesRunTime.unboxCharOrInt(arg2, code2);
                return BoxesRunTime.boxToInteger(val1 >> val2);
            }
            if (code2 <= 4) {
                long val2 = BoxesRunTime.unboxCharOrLong(arg2, code2);
                return BoxesRunTime.boxToInteger(val1 >> (int)val2);
            }
        }
        if (code1 <= 4) {
            long val1 = BoxesRunTime.unboxCharOrLong(arg1, code1);
            if (code2 <= 3) {
                int val2 = BoxesRunTime.unboxCharOrInt(arg2, code2);
                return BoxesRunTime.boxToLong(val1 >> val2);
            }
            if (code2 <= 4) {
                long val2 = BoxesRunTime.unboxCharOrLong(arg2, code2);
                return BoxesRunTime.boxToLong(val1 >> (int)val2);
            }
        }
        throw new NoSuchMethodException();
    }

    public static Object shiftSignedLeft(Object arg1, Object arg2) throws NoSuchMethodException {
        int code1 = BoxesRunTime.typeCode(arg1);
        int code2 = BoxesRunTime.typeCode(arg2);
        if (code1 <= 3) {
            int val1 = BoxesRunTime.unboxCharOrInt(arg1, code1);
            if (code2 <= 3) {
                int val2 = BoxesRunTime.unboxCharOrInt(arg2, code2);
                return BoxesRunTime.boxToInteger(val1 << val2);
            }
            if (code2 <= 4) {
                long val2 = BoxesRunTime.unboxCharOrLong(arg2, code2);
                return BoxesRunTime.boxToInteger(val1 << (int)val2);
            }
        }
        if (code1 <= 4) {
            long val1 = BoxesRunTime.unboxCharOrLong(arg1, code1);
            if (code2 <= 3) {
                int val2 = BoxesRunTime.unboxCharOrInt(arg2, code2);
                return BoxesRunTime.boxToLong(val1 << val2);
            }
            if (code2 <= 4) {
                long val2 = BoxesRunTime.unboxCharOrLong(arg2, code2);
                return BoxesRunTime.boxToLong(val1 << (int)val2);
            }
        }
        throw new NoSuchMethodException();
    }

    public static Object shiftLogicalRight(Object arg1, Object arg2) throws NoSuchMethodException {
        int code1 = BoxesRunTime.typeCode(arg1);
        int code2 = BoxesRunTime.typeCode(arg2);
        if (code1 <= 3) {
            int val1 = BoxesRunTime.unboxCharOrInt(arg1, code1);
            if (code2 <= 3) {
                int val2 = BoxesRunTime.unboxCharOrInt(arg2, code2);
                return BoxesRunTime.boxToInteger(val1 >>> val2);
            }
            if (code2 <= 4) {
                long val2 = BoxesRunTime.unboxCharOrLong(arg2, code2);
                return BoxesRunTime.boxToInteger(val1 >>> (int)val2);
            }
        }
        if (code1 <= 4) {
            long val1 = BoxesRunTime.unboxCharOrLong(arg1, code1);
            if (code2 <= 3) {
                int val2 = BoxesRunTime.unboxCharOrInt(arg2, code2);
                return BoxesRunTime.boxToLong(val1 >>> val2);
            }
            if (code2 <= 4) {
                long val2 = BoxesRunTime.unboxCharOrLong(arg2, code2);
                return BoxesRunTime.boxToLong(val1 >>> (int)val2);
            }
        }
        throw new NoSuchMethodException();
    }

    public static Object negate(Object arg) throws NoSuchMethodException {
        int code = BoxesRunTime.typeCode(arg);
        if (code <= 3) {
            int val = BoxesRunTime.unboxCharOrInt(arg, code);
            return BoxesRunTime.boxToInteger(-val);
        }
        if (code <= 4) {
            long val = BoxesRunTime.unboxCharOrLong(arg, code);
            return BoxesRunTime.boxToLong(-val);
        }
        if (code <= 5) {
            float val = BoxesRunTime.unboxCharOrFloat(arg, code);
            return BoxesRunTime.boxToFloat(-val);
        }
        if (code <= 6) {
            double val = BoxesRunTime.unboxCharOrDouble(arg, code);
            return BoxesRunTime.boxToDouble(-val);
        }
        throw new NoSuchMethodException();
    }

    public static Object positive(Object arg) throws NoSuchMethodException {
        int code = BoxesRunTime.typeCode(arg);
        if (code <= 3) {
            return BoxesRunTime.boxToInteger(BoxesRunTime.unboxCharOrInt(arg, code));
        }
        if (code <= 4) {
            return BoxesRunTime.boxToLong(BoxesRunTime.unboxCharOrLong(arg, code));
        }
        if (code <= 5) {
            return BoxesRunTime.boxToFloat(BoxesRunTime.unboxCharOrFloat(arg, code));
        }
        if (code <= 6) {
            return BoxesRunTime.boxToDouble(BoxesRunTime.unboxCharOrDouble(arg, code));
        }
        throw new NoSuchMethodException();
    }

    public static Object takeAnd(Object arg1, Object arg2) throws NoSuchMethodException {
        int code2;
        int maxcode;
        if (arg1 instanceof Boolean || arg2 instanceof Boolean) {
            if (arg1 instanceof Boolean && arg2 instanceof Boolean) {
                return BoxesRunTime.boxToBoolean((Boolean)arg1 & (Boolean)arg2);
            }
            throw new NoSuchMethodException();
        }
        int code1 = BoxesRunTime.typeCode(arg1);
        int n = maxcode = code1 < (code2 = BoxesRunTime.typeCode(arg2)) ? code2 : code1;
        if (maxcode <= 3) {
            return BoxesRunTime.boxToInteger(BoxesRunTime.unboxCharOrInt(arg1, code1) & BoxesRunTime.unboxCharOrInt(arg2, code2));
        }
        if (maxcode <= 4) {
            return BoxesRunTime.boxToLong(BoxesRunTime.unboxCharOrLong(arg1, code1) & BoxesRunTime.unboxCharOrLong(arg2, code2));
        }
        throw new NoSuchMethodException();
    }

    public static Object takeOr(Object arg1, Object arg2) throws NoSuchMethodException {
        int code2;
        int maxcode;
        if (arg1 instanceof Boolean || arg2 instanceof Boolean) {
            if (arg1 instanceof Boolean && arg2 instanceof Boolean) {
                return BoxesRunTime.boxToBoolean((Boolean)arg1 | (Boolean)arg2);
            }
            throw new NoSuchMethodException();
        }
        int code1 = BoxesRunTime.typeCode(arg1);
        int n = maxcode = code1 < (code2 = BoxesRunTime.typeCode(arg2)) ? code2 : code1;
        if (maxcode <= 3) {
            return BoxesRunTime.boxToInteger(BoxesRunTime.unboxCharOrInt(arg1, code1) | BoxesRunTime.unboxCharOrInt(arg2, code2));
        }
        if (maxcode <= 4) {
            return BoxesRunTime.boxToLong(BoxesRunTime.unboxCharOrLong(arg1, code1) | BoxesRunTime.unboxCharOrLong(arg2, code2));
        }
        throw new NoSuchMethodException();
    }

    public static Object takeXor(Object arg1, Object arg2) throws NoSuchMethodException {
        int code2;
        int maxcode;
        if (arg1 instanceof Boolean || arg2 instanceof Boolean) {
            if (arg1 instanceof Boolean && arg2 instanceof Boolean) {
                return BoxesRunTime.boxToBoolean((Boolean)arg1 ^ (Boolean)arg2);
            }
            throw new NoSuchMethodException();
        }
        int code1 = BoxesRunTime.typeCode(arg1);
        int n = maxcode = code1 < (code2 = BoxesRunTime.typeCode(arg2)) ? code2 : code1;
        if (maxcode <= 3) {
            return BoxesRunTime.boxToInteger(BoxesRunTime.unboxCharOrInt(arg1, code1) ^ BoxesRunTime.unboxCharOrInt(arg2, code2));
        }
        if (maxcode <= 4) {
            return BoxesRunTime.boxToLong(BoxesRunTime.unboxCharOrLong(arg1, code1) ^ BoxesRunTime.unboxCharOrLong(arg2, code2));
        }
        throw new NoSuchMethodException();
    }

    public static Object takeConditionalAnd(Object arg1, Object arg2) throws NoSuchMethodException {
        if (arg1 instanceof Boolean && arg2 instanceof Boolean) {
            return BoxesRunTime.boxToBoolean((Boolean)arg1 != false && (Boolean)arg2 != false);
        }
        throw new NoSuchMethodException();
    }

    public static Object takeConditionalOr(Object arg1, Object arg2) throws NoSuchMethodException {
        if (arg1 instanceof Boolean && arg2 instanceof Boolean) {
            return BoxesRunTime.boxToBoolean((Boolean)arg1 != false || (Boolean)arg2 != false);
        }
        throw new NoSuchMethodException();
    }

    public static Object complement(Object arg) throws NoSuchMethodException {
        int code = BoxesRunTime.typeCode(arg);
        if (code <= 3) {
            return BoxesRunTime.boxToInteger(~BoxesRunTime.unboxCharOrInt(arg, code));
        }
        if (code <= 4) {
            return BoxesRunTime.boxToLong(BoxesRunTime.unboxCharOrLong(arg, code) ^ 0xFFFFFFFFFFFFFFFFL);
        }
        throw new NoSuchMethodException();
    }

    public static Object takeNot(Object arg) throws NoSuchMethodException {
        if (arg instanceof Boolean) {
            return BoxesRunTime.boxToBoolean((Boolean)arg == false);
        }
        throw new NoSuchMethodException();
    }

    public static Object testEqual(Object arg1, Object arg2) throws NoSuchMethodException {
        return BoxesRunTime.boxToBoolean(arg1 == arg2);
    }

    public static Object testNotEqual(Object arg1, Object arg2) throws NoSuchMethodException {
        return BoxesRunTime.boxToBoolean(arg1 != arg2);
    }

    public static Object testLessThan(Object arg1, Object arg2) throws NoSuchMethodException {
        int code2;
        int maxcode;
        int code1 = BoxesRunTime.typeCode(arg1);
        int n = maxcode = code1 < (code2 = BoxesRunTime.typeCode(arg2)) ? code2 : code1;
        if (maxcode <= 3) {
            int val2;
            int val1 = BoxesRunTime.unboxCharOrInt(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 < (val2 = BoxesRunTime.unboxCharOrInt(arg2, code2)));
        }
        if (maxcode <= 4) {
            long val2;
            long val1 = BoxesRunTime.unboxCharOrLong(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 < (val2 = BoxesRunTime.unboxCharOrLong(arg2, code2)));
        }
        if (maxcode <= 5) {
            float val2;
            float val1 = BoxesRunTime.unboxCharOrFloat(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 < (val2 = BoxesRunTime.unboxCharOrFloat(arg2, code2)));
        }
        if (maxcode <= 6) {
            double val2;
            double val1 = BoxesRunTime.unboxCharOrDouble(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 < (val2 = BoxesRunTime.unboxCharOrDouble(arg2, code2)));
        }
        throw new NoSuchMethodException();
    }

    public static Object testLessOrEqualThan(Object arg1, Object arg2) throws NoSuchMethodException {
        int code2;
        int maxcode;
        int code1 = BoxesRunTime.typeCode(arg1);
        int n = maxcode = code1 < (code2 = BoxesRunTime.typeCode(arg2)) ? code2 : code1;
        if (maxcode <= 3) {
            int val2;
            int val1 = BoxesRunTime.unboxCharOrInt(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 <= (val2 = BoxesRunTime.unboxCharOrInt(arg2, code2)));
        }
        if (maxcode <= 4) {
            long val2;
            long val1 = BoxesRunTime.unboxCharOrLong(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 <= (val2 = BoxesRunTime.unboxCharOrLong(arg2, code2)));
        }
        if (maxcode <= 5) {
            float val2;
            float val1 = BoxesRunTime.unboxCharOrFloat(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 <= (val2 = BoxesRunTime.unboxCharOrFloat(arg2, code2)));
        }
        if (maxcode <= 6) {
            double val2;
            double val1 = BoxesRunTime.unboxCharOrDouble(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 <= (val2 = BoxesRunTime.unboxCharOrDouble(arg2, code2)));
        }
        throw new NoSuchMethodException();
    }

    public static Object testGreaterOrEqualThan(Object arg1, Object arg2) throws NoSuchMethodException {
        int code2;
        int maxcode;
        int code1 = BoxesRunTime.typeCode(arg1);
        int n = maxcode = code1 < (code2 = BoxesRunTime.typeCode(arg2)) ? code2 : code1;
        if (maxcode <= 3) {
            int val2;
            int val1 = BoxesRunTime.unboxCharOrInt(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 >= (val2 = BoxesRunTime.unboxCharOrInt(arg2, code2)));
        }
        if (maxcode <= 4) {
            long val2;
            long val1 = BoxesRunTime.unboxCharOrLong(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 >= (val2 = BoxesRunTime.unboxCharOrLong(arg2, code2)));
        }
        if (maxcode <= 5) {
            float val2;
            float val1 = BoxesRunTime.unboxCharOrFloat(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 >= (val2 = BoxesRunTime.unboxCharOrFloat(arg2, code2)));
        }
        if (maxcode <= 6) {
            double val2;
            double val1 = BoxesRunTime.unboxCharOrDouble(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 >= (val2 = BoxesRunTime.unboxCharOrDouble(arg2, code2)));
        }
        throw new NoSuchMethodException();
    }

    public static Object testGreaterThan(Object arg1, Object arg2) throws NoSuchMethodException {
        int code2;
        int maxcode;
        int code1 = BoxesRunTime.typeCode(arg1);
        int n = maxcode = code1 < (code2 = BoxesRunTime.typeCode(arg2)) ? code2 : code1;
        if (maxcode <= 3) {
            int val2;
            int val1 = BoxesRunTime.unboxCharOrInt(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 > (val2 = BoxesRunTime.unboxCharOrInt(arg2, code2)));
        }
        if (maxcode <= 4) {
            long val2;
            long val1 = BoxesRunTime.unboxCharOrLong(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 > (val2 = BoxesRunTime.unboxCharOrLong(arg2, code2)));
        }
        if (maxcode <= 5) {
            float val2;
            float val1 = BoxesRunTime.unboxCharOrFloat(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 > (val2 = BoxesRunTime.unboxCharOrFloat(arg2, code2)));
        }
        if (maxcode <= 6) {
            double val2;
            double val1 = BoxesRunTime.unboxCharOrDouble(arg1, code1);
            return BoxesRunTime.boxToBoolean(val1 > (val2 = BoxesRunTime.unboxCharOrDouble(arg2, code2)));
        }
        throw new NoSuchMethodException();
    }

    public static boolean isBoxedNumberOrBoolean(Object arg) {
        return arg instanceof Boolean || BoxesRunTime.isBoxedNumber(arg);
    }

    public static boolean isBoxedNumber(Object arg) {
        return arg instanceof Integer || arg instanceof Long || arg instanceof Double || arg instanceof Float || arg instanceof Short || arg instanceof Character || arg instanceof Byte;
    }

    public static Character toCharacter(Object arg) throws NoSuchMethodException {
        if (arg instanceof Integer) {
            return BoxesRunTime.boxToCharacter((char)BoxesRunTime.unboxToInt(arg));
        }
        if (arg instanceof Short) {
            return BoxesRunTime.boxToCharacter((char)BoxesRunTime.unboxToShort(arg));
        }
        if (arg instanceof Character) {
            return (Character)arg;
        }
        if (arg instanceof Long) {
            return BoxesRunTime.boxToCharacter((char)BoxesRunTime.unboxToLong(arg));
        }
        if (arg instanceof Byte) {
            return BoxesRunTime.boxToCharacter((char)BoxesRunTime.unboxToByte(arg));
        }
        if (arg instanceof Float) {
            return BoxesRunTime.boxToCharacter((char)BoxesRunTime.unboxToFloat(arg));
        }
        if (arg instanceof Double) {
            return BoxesRunTime.boxToCharacter((char)BoxesRunTime.unboxToDouble(arg));
        }
        throw new NoSuchMethodException();
    }

    public static Byte toByte(Object arg) throws NoSuchMethodException {
        if (arg instanceof Integer) {
            return BoxesRunTime.boxToByte((byte)BoxesRunTime.unboxToInt(arg));
        }
        if (arg instanceof Character) {
            return BoxesRunTime.boxToByte((byte)BoxesRunTime.unboxToChar(arg));
        }
        if (arg instanceof Byte) {
            return (Byte)arg;
        }
        if (arg instanceof Long) {
            return BoxesRunTime.boxToByte((byte)BoxesRunTime.unboxToLong(arg));
        }
        if (arg instanceof Short) {
            return BoxesRunTime.boxToByte((byte)BoxesRunTime.unboxToShort(arg));
        }
        if (arg instanceof Float) {
            return BoxesRunTime.boxToByte((byte)BoxesRunTime.unboxToFloat(arg));
        }
        if (arg instanceof Double) {
            return BoxesRunTime.boxToByte((byte)BoxesRunTime.unboxToDouble(arg));
        }
        throw new NoSuchMethodException();
    }

    public static Short toShort(Object arg) throws NoSuchMethodException {
        if (arg instanceof Integer) {
            return BoxesRunTime.boxToShort((short)BoxesRunTime.unboxToInt(arg));
        }
        if (arg instanceof Long) {
            return BoxesRunTime.boxToShort((short)BoxesRunTime.unboxToLong(arg));
        }
        if (arg instanceof Character) {
            return BoxesRunTime.boxToShort((short)BoxesRunTime.unboxToChar(arg));
        }
        if (arg instanceof Byte) {
            return BoxesRunTime.boxToShort(BoxesRunTime.unboxToByte(arg));
        }
        if (arg instanceof Short) {
            return (Short)arg;
        }
        if (arg instanceof Float) {
            return BoxesRunTime.boxToShort((short)BoxesRunTime.unboxToFloat(arg));
        }
        if (arg instanceof Double) {
            return BoxesRunTime.boxToShort((short)BoxesRunTime.unboxToDouble(arg));
        }
        throw new NoSuchMethodException();
    }

    public static Integer toInteger(Object arg) throws NoSuchMethodException {
        if (arg instanceof Integer) {
            return (Integer)arg;
        }
        if (arg instanceof Long) {
            return BoxesRunTime.boxToInteger((int)BoxesRunTime.unboxToLong(arg));
        }
        if (arg instanceof Double) {
            return BoxesRunTime.boxToInteger((int)BoxesRunTime.unboxToDouble(arg));
        }
        if (arg instanceof Float) {
            return BoxesRunTime.boxToInteger((int)BoxesRunTime.unboxToFloat(arg));
        }
        if (arg instanceof Character) {
            return BoxesRunTime.boxToInteger(BoxesRunTime.unboxToChar(arg));
        }
        if (arg instanceof Byte) {
            return BoxesRunTime.boxToInteger(BoxesRunTime.unboxToByte(arg));
        }
        if (arg instanceof Short) {
            return BoxesRunTime.boxToInteger(BoxesRunTime.unboxToShort(arg));
        }
        throw new NoSuchMethodException();
    }

    public static Long toLong(Object arg) throws NoSuchMethodException {
        if (arg instanceof Integer) {
            return BoxesRunTime.boxToLong(BoxesRunTime.unboxToInt(arg));
        }
        if (arg instanceof Double) {
            return BoxesRunTime.boxToLong((long)BoxesRunTime.unboxToDouble(arg));
        }
        if (arg instanceof Float) {
            return BoxesRunTime.boxToLong((long)BoxesRunTime.unboxToFloat(arg));
        }
        if (arg instanceof Long) {
            return (Long)arg;
        }
        if (arg instanceof Character) {
            return BoxesRunTime.boxToLong(BoxesRunTime.unboxToChar(arg));
        }
        if (arg instanceof Byte) {
            return BoxesRunTime.boxToLong(BoxesRunTime.unboxToByte(arg));
        }
        if (arg instanceof Short) {
            return BoxesRunTime.boxToLong(BoxesRunTime.unboxToShort(arg));
        }
        throw new NoSuchMethodException();
    }

    public static Float toFloat(Object arg) throws NoSuchMethodException {
        if (arg instanceof Integer) {
            return BoxesRunTime.boxToFloat(BoxesRunTime.unboxToInt(arg));
        }
        if (arg instanceof Long) {
            return BoxesRunTime.boxToFloat(BoxesRunTime.unboxToLong(arg));
        }
        if (arg instanceof Float) {
            return (Float)arg;
        }
        if (arg instanceof Double) {
            return BoxesRunTime.boxToFloat((float)BoxesRunTime.unboxToDouble(arg));
        }
        if (arg instanceof Character) {
            return BoxesRunTime.boxToFloat(BoxesRunTime.unboxToChar(arg));
        }
        if (arg instanceof Byte) {
            return BoxesRunTime.boxToFloat(BoxesRunTime.unboxToByte(arg));
        }
        if (arg instanceof Short) {
            return BoxesRunTime.boxToFloat(BoxesRunTime.unboxToShort(arg));
        }
        throw new NoSuchMethodException();
    }

    public static Double toDouble(Object arg) throws NoSuchMethodException {
        if (arg instanceof Integer) {
            return BoxesRunTime.boxToDouble(BoxesRunTime.unboxToInt(arg));
        }
        if (arg instanceof Float) {
            return BoxesRunTime.boxToDouble(BoxesRunTime.unboxToFloat(arg));
        }
        if (arg instanceof Double) {
            return (Double)arg;
        }
        if (arg instanceof Long) {
            return BoxesRunTime.boxToDouble(BoxesRunTime.unboxToLong(arg));
        }
        if (arg instanceof Character) {
            return BoxesRunTime.boxToDouble(BoxesRunTime.unboxToChar(arg));
        }
        if (arg instanceof Byte) {
            return BoxesRunTime.boxToDouble(BoxesRunTime.unboxToByte(arg));
        }
        if (arg instanceof Short) {
            return BoxesRunTime.boxToDouble(BoxesRunTime.unboxToShort(arg));
        }
        throw new NoSuchMethodException();
    }
}

