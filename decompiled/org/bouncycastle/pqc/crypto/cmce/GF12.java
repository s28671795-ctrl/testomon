/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.cmce;

import org.bouncycastle.math.raw.Interleave;
import org.bouncycastle.pqc.crypto.cmce.GF;

final class GF12
extends GF {
    GF12() {
    }

    @Override
    protected void gf_mul_poly(int n, int[] nArray, short[] sArray, short[] sArray2, short[] sArray3, int[] nArray2) {
        short s2;
        short s3;
        int n2;
        nArray2[0] = this.gf_mul_ext(sArray2[0], sArray3[0]);
        for (n2 = 1; n2 < n; ++n2) {
            nArray2[n2 + n2 - 1] = 0;
            s3 = sArray2[n2];
            s2 = sArray3[n2];
            for (int i = 0; i < n2; ++i) {
                int n3 = n2 + i;
                nArray2[n3] = nArray2[n3] ^ this.gf_mul_ext_par(s3, sArray3[i], sArray2[i], s2);
            }
            nArray2[n2 + n2] = this.gf_mul_ext(s3, s2);
        }
        for (n2 = (n - 1) * 2; n2 >= n; --n2) {
            s3 = nArray2[n2];
            for (s2 = 0; s2 < nArray.length - 1; ++s2) {
                int n4 = n2 - n + nArray[s2];
                nArray2[n4] = nArray2[n4] ^ s3;
            }
            int n5 = n2 - n;
            nArray2[n5] = nArray2[n5] ^ s3 << 1;
        }
        for (n2 = 0; n2 < n; ++n2) {
            sArray[n2] = this.gf_reduce(nArray2[n2]);
        }
    }

    @Override
    protected void gf_sqr_poly(int n, int[] nArray, short[] sArray, short[] sArray2, int[] nArray2) {
        int n2;
        nArray2[0] = this.gf_sq_ext(sArray2[0]);
        for (n2 = 1; n2 < n; ++n2) {
            nArray2[n2 + n2 - 1] = 0;
            nArray2[n2 + n2] = this.gf_sq_ext(sArray2[n2]);
        }
        for (n2 = (n - 1) * 2; n2 >= n; --n2) {
            int n3 = nArray2[n2];
            for (int i = 0; i < nArray.length - 1; ++i) {
                int n4 = n2 - n + nArray[i];
                nArray2[n4] = nArray2[n4] ^ n3;
            }
            int n5 = n2 - n;
            nArray2[n5] = nArray2[n5] ^ n3 << 1;
        }
        for (n2 = 0; n2 < n; ++n2) {
            sArray[n2] = this.gf_reduce(nArray2[n2]);
        }
    }

    @Override
    protected short gf_frac(short s2, short s3) {
        return this.gf_mul(this.gf_inv(s2), s3);
    }

    @Override
    protected short gf_inv(short s2) {
        short s3 = s2;
        s3 = this.gf_sq(s3);
        short s4 = this.gf_mul(s3, s2);
        s3 = this.gf_sq(s4);
        s3 = this.gf_sq(s3);
        short s5 = this.gf_mul(s3, s4);
        s3 = this.gf_sq(s5);
        s3 = this.gf_sq(s3);
        s3 = this.gf_sq(s3);
        s3 = this.gf_sq(s3);
        s3 = this.gf_mul(s3, s5);
        s3 = this.gf_sq(s3);
        s3 = this.gf_sq(s3);
        s3 = this.gf_mul(s3, s4);
        s3 = this.gf_sq(s3);
        s3 = this.gf_mul(s3, s2);
        return this.gf_sq(s3);
    }

    @Override
    protected short gf_mul(short s2, short s3) {
        short s4 = s2;
        short s5 = s3;
        int n = s4 * (s5 & 1);
        for (int i = 1; i < 12; ++i) {
            n ^= s4 * (s5 & 1 << i);
        }
        return this.gf_reduce(n);
    }

    @Override
    protected int gf_mul_ext(short s2, short s3) {
        short s4 = s2;
        short s5 = s3;
        int n = s4 * (s5 & 1);
        for (int i = 1; i < 12; ++i) {
            n ^= s4 * (s5 & 1 << i);
        }
        return n;
    }

    private int gf_mul_ext_par(short s2, short s3, short s4, short s5) {
        short s6 = s2;
        short s7 = s3;
        short s8 = s4;
        short s9 = s5;
        int n = s6 * (s7 & 1);
        int n2 = s8 * (s9 & 1);
        for (int i = 1; i < 12; ++i) {
            n ^= s6 * (s7 & 1 << i);
            n2 ^= s8 * (s9 & 1 << i);
        }
        return n ^ n2;
    }

    @Override
    protected short gf_reduce(int n) {
        int n2 = n & 0xFFF;
        int n3 = n >>> 12;
        int n4 = (n & 0x1FF000) >>> 9;
        int n5 = (n & 0xE00000) >>> 18;
        int n6 = n >>> 21;
        return (short)(n2 ^ n3 ^ n4 ^ n5 ^ n6);
    }

    @Override
    protected short gf_sq(short s2) {
        int n = Interleave.expand16to32(s2);
        return this.gf_reduce(n);
    }

    @Override
    protected int gf_sq_ext(short s2) {
        return Interleave.expand16to32(s2);
    }
}

