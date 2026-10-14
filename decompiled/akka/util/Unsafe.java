/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@InternalApi
public final class Unsafe {
    public static final sun.misc.Unsafe instance;
    private static final long stringValueFieldOffset;
    private static final boolean isJavaVersion9Plus;
    private static final int copyUSAsciiStrToBytesAlgorithm;

    static boolean isIsJavaVersion9Plus() {
        int[] nArray = Arrays.stream(System.getProperty("java.specification.version").split("\\.")).mapToInt(Integer::parseInt).toArray();
        int n = nArray[0] == 1 ? nArray[1] : nArray[0];
        return n > 8;
    }

    static boolean testUSAsciiStrToBytesAlgorithm0(String string) {
        try {
            byte[] byArray = new byte[string.length()];
            byte[] byArray2 = string.getBytes(StandardCharsets.US_ASCII);
            System.arraycopy(byArray2, 0, byArray, 0, string.length());
            String string2 = Unsafe.copyUSAciiBytesToStr(string.length(), byArray);
            return string.equals(string2);
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    static boolean testUSAsciiStrToBytesAlgorithm1(String string) {
        try {
            byte[] byArray = new byte[string.length()];
            byte[] byArray2 = (byte[])instance.getObject(string, stringValueFieldOffset);
            System.arraycopy(byArray2, 0, byArray, 0, string.length());
            String string2 = Unsafe.copyUSAciiBytesToStr(string.length(), byArray);
            return string.equals(string2);
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    static boolean testUSAsciiStrToBytesAlgorithm2(String string) {
        try {
            byte[] byArray = new byte[string.length()];
            char[] cArray = (char[])instance.getObject(string, stringValueFieldOffset);
            int n = 0;
            while (n < string.length()) {
                byArray[n] = (byte)cArray[n++];
            }
            String string2 = Unsafe.copyUSAciiBytesToStr(string.length(), byArray);
            return string.equals(string2);
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    private static String copyUSAciiBytesToStr(int n, byte[] byArray) {
        char[] cArray = new char[n];
        for (int i = 0; i < n; ++i) {
            cArray[i] = (char)byArray[i];
        }
        return String.valueOf(cArray, 0, n);
    }

    public static void copyUSAsciiStrToBytes(String string, byte[] byArray) {
        if (copyUSAsciiStrToBytesAlgorithm == 1) {
            byte[] byArray2 = (byte[])instance.getObject(string, stringValueFieldOffset);
            System.arraycopy(byArray2, 0, byArray, 0, string.length());
        } else if (copyUSAsciiStrToBytesAlgorithm == 2) {
            char[] cArray = (char[])instance.getObject(string, stringValueFieldOffset);
            int n = 0;
            while (n < string.length()) {
                byArray[n] = (byte)cArray[n++];
            }
        } else {
            byte[] byArray3 = string.getBytes(StandardCharsets.US_ASCII);
            System.arraycopy(byArray3, 0, byArray, 0, string.length());
        }
    }

    public static int fastHash(String string) {
        long l = 391408L;
        long l2 = 601258L;
        int n = 0;
        if (copyUSAsciiStrToBytesAlgorithm == 1) {
            byte[] byArray = (byte[])instance.getObject(string, stringValueFieldOffset);
            while (n < string.length()) {
                long l3;
                long l4 = l ^ (long)byArray[n++];
                l = l3 = l2;
                l4 ^= l4 << 23;
                l3 ^= l3 >>> 26;
                l4 ^= l4 >>> 17;
                l2 = l4 ^ l3;
            }
        } else if (copyUSAsciiStrToBytesAlgorithm == 2) {
            char[] cArray = (char[])instance.getObject(string, stringValueFieldOffset);
            while (n < string.length()) {
                long l5;
                long l6 = l ^ (long)cArray[n++];
                l = l5 = l2;
                l6 ^= l6 << 23;
                l5 ^= l5 >>> 26;
                l6 ^= l6 >>> 17;
                l2 = l6 ^ l5;
            }
        } else {
            byte[] byArray = string.getBytes(StandardCharsets.US_ASCII);
            while (n < string.length()) {
                long l7;
                long l8 = l ^ (long)byArray[n++];
                l = l7 = l2;
                l8 ^= l8 << 23;
                l7 ^= l7 >>> 26;
                l8 ^= l8 >>> 17;
                l2 = l8 ^ l7;
            }
        }
        return (int)(l + l2);
    }

    static {
        try {
            long l;
            sun.misc.Unsafe unsafe = null;
            for (Field field2 : sun.misc.Unsafe.class.getDeclaredFields()) {
                if (field2.getType() != sun.misc.Unsafe.class) continue;
                field2.setAccessible(true);
                unsafe = (sun.misc.Unsafe)field2.get(null);
                break;
            }
            if (unsafe == null) {
                throw new IllegalStateException("Can't find instance of sun.misc.Unsafe");
            }
            instance = unsafe;
            try {
                l = instance.objectFieldOffset(String.class.getDeclaredField("value"));
            }
            catch (NoSuchFieldException noSuchFieldException) {
                l = -1L;
            }
            stringValueFieldOffset = l;
            isJavaVersion9Plus = Unsafe.isIsJavaVersion9Plus();
            if (stringValueFieldOffset > -1L) {
                String string = "abc";
                copyUSAsciiStrToBytesAlgorithm = isJavaVersion9Plus && Unsafe.testUSAsciiStrToBytesAlgorithm1(string) ? 1 : (Unsafe.testUSAsciiStrToBytesAlgorithm2(string) ? 2 : 0);
            } else {
                copyUSAsciiStrToBytesAlgorithm = 0;
            }
        }
        catch (Throwable throwable) {
            throw new ExceptionInInitializerError(throwable);
        }
    }
}

