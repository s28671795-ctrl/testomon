/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import java.io.Serializable;
import java.math.MathContext;
import java.math.RoundingMode;
import scala.math.BigDecimal;
import scala.math.BigInt;
import scala.math.BigInt$;
import scala.runtime.ModuleSerializationProxy;

public final class BigDecimal$
implements Serializable {
    public static final BigDecimal$ MODULE$ = new BigDecimal$();
    private static BigDecimal[] cache;
    private static final int minCached;
    private static final int maxCached;
    private static final MathContext defaultMathContext;
    private static volatile boolean bitmap$0;

    static {
        minCached = -512;
        maxCached = 512;
        defaultMathContext = MathContext.DECIMAL128;
    }

    private final int maximumHashScale() {
        return 4934;
    }

    private final int hashCodeNotComputed() {
        return 1565550863;
    }

    private final double deci2binary() {
        return 3.3219280948873626;
    }

    public MathContext defaultMathContext() {
        return defaultMathContext;
    }

    private BigDecimal[] cache$lzycompute() {
        synchronized (this) {
            if (!bitmap$0) {
                cache = new BigDecimal[maxCached - minCached + 1];
                bitmap$0 = true;
            }
        }
        return cache;
    }

    private BigDecimal[] cache() {
        if (!bitmap$0) {
            return this.cache$lzycompute();
        }
        return cache;
    }

    public BigDecimal decimal(double d, MathContext mc) {
        return new BigDecimal(new java.math.BigDecimal(Double.toString(d), mc), mc);
    }

    public BigDecimal decimal(double d) {
        return this.decimal(d, this.defaultMathContext());
    }

    public BigDecimal decimal(float f, MathContext mc) {
        return new BigDecimal(new java.math.BigDecimal(Float.toString(f), mc), mc);
    }

    public BigDecimal decimal(float f) {
        return this.decimal(f, this.defaultMathContext());
    }

    public BigDecimal decimal(long l, MathContext mc) {
        return this.apply(l, mc);
    }

    public BigDecimal decimal(long l) {
        return this.apply(l);
    }

    public BigDecimal decimal(java.math.BigDecimal bd, MathContext mc) {
        return new BigDecimal(bd.round(mc), mc);
    }

    public BigDecimal binary(double d, MathContext mc) {
        return new BigDecimal(new java.math.BigDecimal(d, mc), mc);
    }

    public BigDecimal binary(double d) {
        return this.binary(d, this.defaultMathContext());
    }

    public BigDecimal exact(java.math.BigDecimal repr) {
        MathContext mc = repr.precision() <= this.defaultMathContext().getPrecision() ? this.defaultMathContext() : new MathContext(repr.precision(), RoundingMode.HALF_EVEN);
        return new BigDecimal(repr, mc);
    }

    public BigDecimal exact(double d) {
        return this.exact(new java.math.BigDecimal(d));
    }

    public BigDecimal exact(BigInt bi) {
        return this.exact(new java.math.BigDecimal(bi.bigInteger()));
    }

    public BigDecimal exact(long l) {
        return this.apply(l);
    }

    public BigDecimal exact(String s2) {
        return this.exact(new java.math.BigDecimal(s2));
    }

    public BigDecimal exact(char[] cs) {
        return this.exact(new java.math.BigDecimal(cs));
    }

    public BigDecimal valueOf(double d) {
        return this.apply(java.math.BigDecimal.valueOf(d));
    }

    public BigDecimal valueOf(long x) {
        return this.apply(x);
    }

    public BigDecimal apply(int i) {
        return this.apply(i, this.defaultMathContext());
    }

    public BigDecimal apply(int i, MathContext mc) {
        MathContext mathContext = mc;
        MathContext mathContext2 = this.defaultMathContext();
        if (!(mathContext != null ? !((Object)mathContext).equals(mathContext2) : mathContext2 != null)) {
            if (minCached <= i && i <= maxCached) {
                int offset = i - minCached;
                BigDecimal n = this.cache()[offset];
                if (n == null) {
                    this.cache()[offset] = n = new BigDecimal(java.math.BigDecimal.valueOf(i), mc);
                }
                return n;
            }
        }
        return this.apply((long)i, mc);
    }

    public BigDecimal apply(long l) {
        if ((long)minCached <= l && l <= (long)maxCached) {
            return this.apply((int)l);
        }
        return new BigDecimal(java.math.BigDecimal.valueOf(l), this.defaultMathContext());
    }

    public BigDecimal apply(long l, MathContext mc) {
        return new BigDecimal(new java.math.BigDecimal(l, mc), mc);
    }

    public BigDecimal apply(long unscaledVal, int scale) {
        return this.apply(BigInt$.MODULE$.apply(unscaledVal), scale);
    }

    public BigDecimal apply(long unscaledVal, int scale, MathContext mc) {
        return this.apply(BigInt$.MODULE$.apply(unscaledVal), scale, mc);
    }

    public BigDecimal apply(double d) {
        return this.decimal(d, this.defaultMathContext());
    }

    public BigDecimal apply(double d, MathContext mc) {
        return this.decimal(d, mc);
    }

    public BigDecimal apply(char[] x) {
        return this.exact(x);
    }

    public BigDecimal apply(char[] x, MathContext mc) {
        return new BigDecimal(new java.math.BigDecimal(x, mc), mc);
    }

    public BigDecimal apply(String x) {
        return this.exact(x);
    }

    public BigDecimal apply(String x, MathContext mc) {
        return new BigDecimal(new java.math.BigDecimal(x, mc), mc);
    }

    public BigDecimal apply(BigInt x) {
        return this.exact(x);
    }

    public BigDecimal apply(BigInt x, MathContext mc) {
        return new BigDecimal(new java.math.BigDecimal(x.bigInteger(), mc), mc);
    }

    public BigDecimal apply(BigInt unscaledVal, int scale) {
        return this.exact(new java.math.BigDecimal(unscaledVal.bigInteger(), scale));
    }

    public BigDecimal apply(BigInt unscaledVal, int scale, MathContext mc) {
        return new BigDecimal(new java.math.BigDecimal(unscaledVal.bigInteger(), scale, mc), mc);
    }

    public BigDecimal apply(java.math.BigDecimal bd) {
        return new BigDecimal(bd, this.defaultMathContext());
    }

    public BigDecimal int2bigDecimal(int i) {
        return this.apply(i);
    }

    public BigDecimal long2bigDecimal(long l) {
        return this.apply(l);
    }

    public BigDecimal double2bigDecimal(double d) {
        return this.decimal(d);
    }

    public BigDecimal javaBigDecimal2bigDecimal(java.math.BigDecimal x) {
        if (x == null) {
            return null;
        }
        return this.apply(x);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(BigDecimal$.class);
    }

    private BigDecimal$() {
    }
}

