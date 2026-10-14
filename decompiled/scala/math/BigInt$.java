/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import java.io.Serializable;
import java.math.BigInteger;
import scala.math.BigInt;
import scala.runtime.ModuleSerializationProxy;
import scala.util.Random;

public final class BigInt$
implements Serializable {
    public static final BigInt$ MODULE$ = new BigInt$();
    private static final BigInteger scala$math$BigInt$$longMinValueBigInteger = BigInteger.valueOf(Long.MIN_VALUE);
    private static final BigInt longMinValue = new BigInt(MODULE$.scala$math$BigInt$$longMinValueBigInteger(), Long.MIN_VALUE);
    private static final int minCached = -1024;
    private static final int maxCached = 1024;
    private static final BigInt[] cache = new BigInt[maxCached - minCached + 1];
    private static final BigInteger scala$math$BigInt$$minusOne = BigInteger.valueOf(-1L);

    public BigInteger scala$math$BigInt$$longMinValueBigInteger() {
        return scala$math$BigInt$$longMinValueBigInteger;
    }

    private BigInt longMinValue() {
        return longMinValue;
    }

    private BigInt getCached(int i) {
        int offset = i - minCached;
        BigInt n = cache[offset];
        if (n == null) {
            BigInt$.cache[offset] = n = new BigInt(null, i);
        }
        return n;
    }

    public BigInteger scala$math$BigInt$$minusOne() {
        return scala$math$BigInt$$minusOne;
    }

    public BigInt apply(int i) {
        if (minCached <= i && i <= maxCached) {
            return this.getCached(i);
        }
        return this.apply((long)i);
    }

    public BigInt apply(long l) {
        if ((long)minCached <= l && l <= (long)maxCached) {
            return this.getCached((int)l);
        }
        if (l == Long.MIN_VALUE) {
            return this.longMinValue();
        }
        return new BigInt(null, l);
    }

    public BigInt apply(byte[] x) {
        return this.apply(new BigInteger(x));
    }

    public BigInt apply(int signum, byte[] magnitude) {
        return this.apply(new BigInteger(signum, magnitude));
    }

    public BigInt apply(int bitlength, int certainty, Random rnd) {
        return this.apply(new BigInteger(bitlength, certainty, rnd.self()));
    }

    public BigInt apply(int numbits, Random rnd) {
        return this.apply(new BigInteger(numbits, rnd.self()));
    }

    public BigInt apply(String x) {
        return this.apply(new BigInteger(x));
    }

    public BigInt apply(String x, int radix) {
        return this.apply(new BigInteger(x, radix));
    }

    public BigInt apply(BigInteger x) {
        if (x.bitLength() <= 63) {
            long l = x.longValue();
            if ((long)minCached <= l && l <= (long)maxCached) {
                return this.getCached((int)l);
            }
            return new BigInt(x, l);
        }
        return new BigInt(x, Long.MIN_VALUE);
    }

    public BigInt probablePrime(int bitLength, Random rnd) {
        return this.apply(BigInteger.probablePrime(bitLength, rnd.self()));
    }

    public BigInt int2bigInt(int i) {
        return this.apply(i);
    }

    public BigInt long2bigInt(long l) {
        return this.apply(l);
    }

    public BigInt javaBigInteger2bigInt(BigInteger x) {
        return this.apply(x);
    }

    public long scala$math$BigInt$$longGcd(long a, long b) {
        long minDeltaOrZero;
        if (a == 0L) {
            return b;
        }
        if (b == 0L) {
            return a;
        }
        int aTwos = Long.numberOfTrailingZeros(a);
        long a1 = a >> aTwos;
        int bTwos = Long.numberOfTrailingZeros(b);
        for (long b1 = b >> bTwos; a1 != b1; b1 += minDeltaOrZero, a1 >>= Long.numberOfTrailingZeros(a1)) {
            long delta = a1 - b1;
            minDeltaOrZero = delta & delta >> 63;
            a1 = delta - minDeltaOrZero - minDeltaOrZero;
        }
        return a1 << Math.min(aTwos, bTwos);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(BigInt$.class);
    }

    private BigInt$() {
    }
}

