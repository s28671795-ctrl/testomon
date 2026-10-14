/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import org.bouncycastle.pqc.legacy.math.ntru.euclid.IntEuclidean;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.DenseTernaryPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.SparseTernaryPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.TernaryPolynomial;
import org.bouncycastle.util.Integers;

public class Util {
    private static volatile boolean IS_64_BITNESS_KNOWN;
    private static volatile boolean IS_64_BIT_JVM;

    public static int invert(int n, int n2) {
        if ((n %= n2) < 0) {
            n += n2;
        }
        return IntEuclidean.calculate((int)n, (int)n2).x;
    }

    public static int pow(int n, int n2, int n3) {
        int n4 = 1;
        for (int i = 0; i < n2; ++i) {
            n4 = n4 * n % n3;
        }
        return n4;
    }

    public static long pow(long l, int n, long l2) {
        long l3 = 1L;
        for (int i = 0; i < n; ++i) {
            l3 = l3 * l % l2;
        }
        return l3;
    }

    public static TernaryPolynomial generateRandomTernary(int n, int n2, int n3, boolean bl, SecureRandom secureRandom) {
        if (bl) {
            return SparseTernaryPolynomial.generateRandom(n, n2, n3, secureRandom);
        }
        return DenseTernaryPolynomial.generateRandom(n, n2, n3, secureRandom);
    }

    public static int[] generateRandomTernary(int n, int n2, int n3, SecureRandom secureRandom) {
        int n4;
        Integer n5 = Integers.valueOf(1);
        Integer n6 = Integers.valueOf(-1);
        Integer n7 = Integers.valueOf(0);
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (n4 = 0; n4 < n2; ++n4) {
            arrayList.add(n5);
        }
        for (n4 = 0; n4 < n3; ++n4) {
            arrayList.add(n6);
        }
        while (arrayList.size() < n) {
            arrayList.add(n7);
        }
        Collections.shuffle(arrayList, secureRandom);
        int[] nArray = new int[n];
        for (int i = 0; i < n; ++i) {
            nArray[i] = (Integer)arrayList.get(i);
        }
        return nArray;
    }

    public static boolean is64BitJVM() {
        if (!IS_64_BITNESS_KNOWN) {
            String string = System.getProperty("os.arch");
            String string2 = System.getProperty("sun.arch.data.model");
            IS_64_BIT_JVM = "amd64".equals(string) || "x86_64".equals(string) || "ppc64".equals(string) || "64".equals(string2);
            IS_64_BITNESS_KNOWN = true;
        }
        return IS_64_BIT_JVM;
    }

    public static byte[] readFullLength(InputStream inputStream2, int n) throws IOException {
        byte[] byArray = new byte[n];
        if (inputStream2.read(byArray) != byArray.length) {
            throw new IOException("Not enough bytes to read.");
        }
        return byArray;
    }
}

