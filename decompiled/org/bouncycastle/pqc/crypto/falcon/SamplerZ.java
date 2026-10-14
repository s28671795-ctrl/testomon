/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.falcon;

import org.bouncycastle.pqc.crypto.falcon.FPREngine;
import org.bouncycastle.pqc.crypto.falcon.FalconFPR;
import org.bouncycastle.pqc.crypto.falcon.FalconRNG;
import org.bouncycastle.pqc.crypto.falcon.SamplerCtx;

class SamplerZ {
    FPREngine fpr = new FPREngine();

    SamplerZ() {
    }

    int sample(SamplerCtx samplerCtx, FalconFPR falconFPR, FalconFPR falconFPR2) {
        return this.sampler(samplerCtx, falconFPR, falconFPR2);
    }

    int gaussian0_sampler(FalconRNG falconRNG) {
        int[] nArray = new int[]{10745844, 3068844, 3741698, 5559083, 1580863, 8248194, 2260429, 13669192, 2736639, 708981, 4421575, 10046180, 169348, 7122675, 4136815, 30538, 13063405, 7650655, 4132, 14505003, 7826148, 417, 16768101, 11363290, 31, 8444042, 8086568, 1, 12844466, 265321, 0, 1232676, 13644283, 0, 38047, 9111839, 0, 870, 6138264, 0, 14, 12545723, 0, 0, 3104126, 0, 0, 28824, 0, 0, 198, 0, 0, 1};
        long l = falconRNG.prng_get_u64();
        int n = falconRNG.prng_get_u8() & 0xFF;
        int n2 = (int)l & 0xFFFFFF;
        int n3 = (int)(l >>> 24) & 0xFFFFFF;
        int n4 = (int)(l >>> 48) | n << 16;
        int n5 = 0;
        for (int i = 0; i < nArray.length; i += 3) {
            int n6 = nArray[i + 2];
            int n7 = nArray[i + 1];
            int n8 = nArray[i + 0];
            int n9 = n2 - n6 >>> 31;
            n9 = n3 - n7 - n9 >>> 31;
            n9 = n4 - n8 - n9 >>> 31;
            n5 += n9;
        }
        return n5;
    }

    int BerExp(FalconRNG falconRNG, FalconFPR falconFPR, FalconFPR falconFPR2) {
        int n;
        int n2 = (int)this.fpr.fpr_trunc(this.fpr.fpr_mul(falconFPR, this.fpr.fpr_inv_log2));
        FalconFPR falconFPR3 = this.fpr.fpr_sub(falconFPR, this.fpr.fpr_mul(this.fpr.fpr_of(n2), this.fpr.fpr_log2));
        int n3 = n2;
        n3 ^= (n3 ^ 0x3F) & -(63 - n3 >>> 31);
        n2 = n3;
        long l = (this.fpr.fpr_expm_p63(falconFPR3, falconFPR2) << 1) - 1L >>> n2;
        int n4 = 64;
        while ((n = (falconRNG.prng_get_u8() & 0xFF) - ((int)(l >>> (n4 -= 8)) & 0xFF)) == 0 && n4 > 0) {
        }
        return n >>> 31;
    }

    int sampler(SamplerCtx samplerCtx, FalconFPR falconFPR, FalconFPR falconFPR2) {
        int n;
        int n2;
        FalconFPR falconFPR3;
        SamplerCtx samplerCtx2 = samplerCtx;
        int n3 = (int)this.fpr.fpr_floor(falconFPR);
        FalconFPR falconFPR4 = this.fpr.fpr_sub(falconFPR, this.fpr.fpr_of(n3));
        FalconFPR falconFPR5 = this.fpr.fpr_half(this.fpr.fpr_sqr(falconFPR2));
        FalconFPR falconFPR6 = this.fpr.fpr_mul(falconFPR2, samplerCtx2.sigma_min);
        do {
            n2 = this.gaussian0_sampler(samplerCtx2.p);
            int n4 = samplerCtx2.p.prng_get_u8() & 0xFF & 1;
            n = n4 + ((n4 << 1) - 1) * n2;
            falconFPR3 = this.fpr.fpr_mul(this.fpr.fpr_sqr(this.fpr.fpr_sub(this.fpr.fpr_of(n), falconFPR4)), falconFPR5);
        } while (this.BerExp(samplerCtx2.p, falconFPR3 = this.fpr.fpr_sub(falconFPR3, this.fpr.fpr_mul(this.fpr.fpr_of(n2 * n2), this.fpr.fpr_inv_2sqrsigma0)), falconFPR6) == 0);
        return n3 + n;
    }
}

