/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.euclid;

import java.math.BigInteger;

public class BigIntEuclidean {
    public BigInteger x;
    public BigInteger y;
    public BigInteger gcd;

    private BigIntEuclidean() {
    }

    public static BigIntEuclidean calculate(BigInteger bigInteger, BigInteger bigInteger2) {
        Object object;
        BigInteger bigInteger3 = BigInteger.ZERO;
        BigInteger bigInteger4 = BigInteger.ONE;
        BigInteger bigInteger5 = BigInteger.ONE;
        BigInteger bigInteger6 = BigInteger.ZERO;
        while (!bigInteger2.equals(BigInteger.ZERO)) {
            object = bigInteger.divideAndRemainder(bigInteger2);
            BigInteger bigInteger7 = object[0];
            BigInteger bigInteger8 = bigInteger;
            bigInteger = bigInteger2;
            bigInteger2 = object[1];
            bigInteger8 = bigInteger3;
            bigInteger3 = bigInteger4.subtract(bigInteger7.multiply(bigInteger3));
            bigInteger4 = bigInteger8;
            bigInteger8 = bigInteger5;
            bigInteger5 = bigInteger6.subtract(bigInteger7.multiply(bigInteger5));
            bigInteger6 = bigInteger8;
        }
        object = new BigIntEuclidean();
        object.x = bigInteger4;
        object.y = bigInteger6;
        object.gcd = bigInteger;
        return object;
    }
}

