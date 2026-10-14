/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.euclid;

public class IntEuclidean {
    public int x;
    public int y;
    public int gcd;

    private IntEuclidean() {
    }

    public static IntEuclidean calculate(int n, int n2) {
        int n3 = 0;
        int n4 = 1;
        int n5 = 1;
        int n6 = 0;
        while (n2 != 0) {
            int n7 = n / n2;
            int n8 = n;
            n = n2;
            n2 = n8 % n2;
            n8 = n3;
            n3 = n4 - n7 * n3;
            n4 = n8;
            n8 = n5;
            n5 = n6 - n7 * n5;
            n6 = n8;
        }
        IntEuclidean intEuclidean = new IntEuclidean();
        intEuclidean.x = n4;
        intEuclidean.y = n6;
        intEuclidean.gcd = n;
        return intEuclidean;
    }
}

