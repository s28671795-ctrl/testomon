/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.falcon;

import org.bouncycastle.pqc.crypto.falcon.FPREngine;
import org.bouncycastle.pqc.crypto.falcon.FalconCommon;
import org.bouncycastle.pqc.crypto.falcon.FalconFFT;
import org.bouncycastle.pqc.crypto.falcon.FalconFPR;
import org.bouncycastle.pqc.crypto.falcon.SHAKE256;
import org.bouncycastle.pqc.crypto.falcon.SamplerCtx;
import org.bouncycastle.pqc.crypto.falcon.SamplerZ;

class FalconSign {
    FPREngine fpr = new FPREngine();
    FalconFFT fft = new FalconFFT();
    FalconCommon common = new FalconCommon();

    FalconSign() {
    }

    private static int MKN(int n) {
        return 1 << n;
    }

    int ffLDL_treesize(int n) {
        return n + 1 << n;
    }

    void ffLDL_fft_inner(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, FalconFPR[] falconFPRArray3, int n3, int n4, FalconFPR[] falconFPRArray4, int n5) {
        int n6 = FalconSign.MKN(n4);
        if (n6 == 1) {
            falconFPRArray[n + 0] = falconFPRArray2[n2 + 0];
            return;
        }
        int n7 = n6 >> 1;
        this.fft.poly_LDLmv_fft(falconFPRArray4, n5, falconFPRArray, n, falconFPRArray2, n2, falconFPRArray3, n3, falconFPRArray2, n2, n4);
        this.fft.poly_split_fft(falconFPRArray3, n3, falconFPRArray3, n3 + n7, falconFPRArray2, n2, n4);
        this.fft.poly_split_fft(falconFPRArray2, n2, falconFPRArray2, n2 + n7, falconFPRArray4, n5, n4);
        this.ffLDL_fft_inner(falconFPRArray, n + n6, falconFPRArray3, n3, falconFPRArray3, n3 + n7, n4 - 1, falconFPRArray4, n5);
        this.ffLDL_fft_inner(falconFPRArray, n + n6 + this.ffLDL_treesize(n4 - 1), falconFPRArray2, n2, falconFPRArray2, n2 + n7, n4 - 1, falconFPRArray4, n5);
    }

    void ffLDL_fft(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, FalconFPR[] falconFPRArray3, int n3, FalconFPR[] falconFPRArray4, int n4, int n5, FalconFPR[] falconFPRArray5, int n6) {
        int n7 = FalconSign.MKN(n5);
        if (n7 == 1) {
            falconFPRArray[n + 0] = falconFPRArray2[n2 + 0];
            return;
        }
        int n8 = n7 >> 1;
        int n9 = n6;
        int n10 = n6 + n7;
        System.arraycopy(falconFPRArray2, n2, falconFPRArray5, n9, n7);
        this.fft.poly_LDLmv_fft(falconFPRArray5, n10, falconFPRArray, n, falconFPRArray2, n2, falconFPRArray3, n3, falconFPRArray4, n4, n5);
        this.fft.poly_split_fft(falconFPRArray5, n6 += n7 << 1, falconFPRArray5, n6 + n8, falconFPRArray5, n9, n5);
        this.fft.poly_split_fft(falconFPRArray5, n9, falconFPRArray5, n9 + n8, falconFPRArray5, n10, n5);
        System.arraycopy(falconFPRArray5, n6, falconFPRArray5, n10, n7);
        this.ffLDL_fft_inner(falconFPRArray, n + n7, falconFPRArray5, n10, falconFPRArray5, n10 + n8, n5 - 1, falconFPRArray5, n6);
        this.ffLDL_fft_inner(falconFPRArray, n + n7 + this.ffLDL_treesize(n5 - 1), falconFPRArray5, n9, falconFPRArray5, n9 + n8, n5 - 1, falconFPRArray5, n6);
    }

    void ffLDL_binary_normalize(FalconFPR[] falconFPRArray, int n, int n2, int n3) {
        int n4 = FalconSign.MKN(n3);
        if (n4 == 1) {
            falconFPRArray[n + 0] = this.fpr.fpr_mul(this.fpr.fpr_sqrt(falconFPRArray[n + 0]), this.fpr.fpr_inv_sigma[n2]);
        } else {
            this.ffLDL_binary_normalize(falconFPRArray, n + n4, n2, n3 - 1);
            this.ffLDL_binary_normalize(falconFPRArray, n + n4 + this.ffLDL_treesize(n3 - 1), n2, n3 - 1);
        }
    }

    void smallints_to_fpr(FalconFPR[] falconFPRArray, int n, byte[] byArray, int n2, int n3) {
        int n4 = FalconSign.MKN(n3);
        for (int i = 0; i < n4; ++i) {
            falconFPRArray[n + i] = this.fpr.fpr_of(byArray[n2 + i]);
        }
    }

    int skoff_b00(int n) {
        return 0;
    }

    int skoff_b01(int n) {
        return FalconSign.MKN(n);
    }

    int skoff_b10(int n) {
        return 2 * FalconSign.MKN(n);
    }

    int skoff_b11(int n) {
        return 3 * FalconSign.MKN(n);
    }

    int skoff_tree(int n) {
        return 4 * FalconSign.MKN(n);
    }

    void expand_privkey(FalconFPR[] falconFPRArray, int n, byte[] byArray, int n2, byte[] byArray2, int n3, byte[] byArray3, int n4, byte[] byArray4, int n5, int n6, FalconFPR[] falconFPRArray2, int n7) {
        int n8 = FalconSign.MKN(n6);
        int n9 = n + this.skoff_b00(n6);
        int n10 = n + this.skoff_b01(n6);
        int n11 = n + this.skoff_b10(n6);
        int n12 = n + this.skoff_b11(n6);
        int n13 = n + this.skoff_tree(n6);
        int n14 = n10;
        int n15 = n9;
        int n16 = n12;
        int n17 = n11;
        this.smallints_to_fpr(falconFPRArray, n14, byArray, n2, n6);
        this.smallints_to_fpr(falconFPRArray, n15, byArray2, n3, n6);
        this.smallints_to_fpr(falconFPRArray, n16, byArray3, n4, n6);
        this.smallints_to_fpr(falconFPRArray, n17, byArray4, n5, n6);
        this.fft.FFT(falconFPRArray, n14, n6);
        this.fft.FFT(falconFPRArray, n15, n6);
        this.fft.FFT(falconFPRArray, n16, n6);
        this.fft.FFT(falconFPRArray, n17, n6);
        this.fft.poly_neg(falconFPRArray, n14, n6);
        this.fft.poly_neg(falconFPRArray, n16, n6);
        int n18 = n7;
        int n19 = n18 + n8;
        int n20 = n19 + n8;
        int n21 = n20 + n8;
        System.arraycopy(falconFPRArray, n9, falconFPRArray2, n18, n8);
        this.fft.poly_mulselfadj_fft(falconFPRArray2, n18, n6);
        System.arraycopy(falconFPRArray, n10, falconFPRArray2, n21, n8);
        this.fft.poly_mulselfadj_fft(falconFPRArray2, n21, n6);
        this.fft.poly_add(falconFPRArray2, n18, falconFPRArray2, n21, n6);
        System.arraycopy(falconFPRArray, n9, falconFPRArray2, n19, n8);
        this.fft.poly_muladj_fft(falconFPRArray2, n19, falconFPRArray, n11, n6);
        System.arraycopy(falconFPRArray, n10, falconFPRArray2, n21, n8);
        this.fft.poly_muladj_fft(falconFPRArray2, n21, falconFPRArray, n12, n6);
        this.fft.poly_add(falconFPRArray2, n19, falconFPRArray2, n21, n6);
        System.arraycopy(falconFPRArray, n11, falconFPRArray2, n20, n8);
        this.fft.poly_mulselfadj_fft(falconFPRArray2, n20, n6);
        System.arraycopy(falconFPRArray, n12, falconFPRArray2, n21, n8);
        this.fft.poly_mulselfadj_fft(falconFPRArray2, n21, n6);
        this.fft.poly_add(falconFPRArray2, n20, falconFPRArray2, n21, n6);
        this.ffLDL_fft(falconFPRArray, n13, falconFPRArray2, n18, falconFPRArray2, n19, falconFPRArray2, n20, n6, falconFPRArray2, n21);
        this.ffLDL_binary_normalize(falconFPRArray, n13, n6, n6);
    }

    void ffSampling_fft_dyntree(SamplerZ samplerZ, SamplerCtx samplerCtx, FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, FalconFPR[] falconFPRArray3, int n3, FalconFPR[] falconFPRArray4, int n4, FalconFPR[] falconFPRArray5, int n5, int n6, int n7, FalconFPR[] falconFPRArray6, int n8) {
        if (n7 == 0) {
            FalconFPR falconFPR = falconFPRArray3[n3 + 0];
            falconFPR = this.fpr.fpr_mul(this.fpr.fpr_sqrt(falconFPR), this.fpr.fpr_inv_sigma[n6]);
            falconFPRArray[n + 0] = this.fpr.fpr_of(samplerZ.sample(samplerCtx, falconFPRArray[n + 0], falconFPR));
            falconFPRArray2[n2 + 0] = this.fpr.fpr_of(samplerZ.sample(samplerCtx, falconFPRArray2[n2 + 0], falconFPR));
            return;
        }
        int n9 = 1 << n7;
        int n10 = n9 >> 1;
        this.fft.poly_LDL_fft(falconFPRArray3, n3, falconFPRArray4, n4, falconFPRArray5, n5, n7);
        this.fft.poly_split_fft(falconFPRArray6, n8, falconFPRArray6, n8 + n10, falconFPRArray3, n3, n7);
        System.arraycopy(falconFPRArray6, n8, falconFPRArray3, n3, n9);
        this.fft.poly_split_fft(falconFPRArray6, n8, falconFPRArray6, n8 + n10, falconFPRArray5, n5, n7);
        System.arraycopy(falconFPRArray6, n8, falconFPRArray5, n5, n9);
        System.arraycopy(falconFPRArray4, n4, falconFPRArray6, n8, n9);
        System.arraycopy(falconFPRArray3, n3, falconFPRArray4, n4, n10);
        System.arraycopy(falconFPRArray5, n5, falconFPRArray4, n4 + n10, n10);
        int n11 = n8 + n9;
        this.fft.poly_split_fft(falconFPRArray6, n11, falconFPRArray6, n11 + n10, falconFPRArray2, n2, n7);
        this.ffSampling_fft_dyntree(samplerZ, samplerCtx, falconFPRArray6, n11, falconFPRArray6, n11 + n10, falconFPRArray5, n5, falconFPRArray5, n5 + n10, falconFPRArray4, n4 + n10, n6, n7 - 1, falconFPRArray6, n11 + n9);
        this.fft.poly_merge_fft(falconFPRArray6, n8 + (n9 << 1), falconFPRArray6, n11, falconFPRArray6, n11 + n10, n7);
        System.arraycopy(falconFPRArray2, n2, falconFPRArray6, n11, n9);
        this.fft.poly_sub(falconFPRArray6, n11, falconFPRArray6, n8 + (n9 << 1), n7);
        System.arraycopy(falconFPRArray6, n8 + (n9 << 1), falconFPRArray2, n2, n9);
        this.fft.poly_mul_fft(falconFPRArray6, n8, falconFPRArray6, n11, n7);
        this.fft.poly_add(falconFPRArray, n, falconFPRArray6, n8, n7);
        int n12 = n8;
        this.fft.poly_split_fft(falconFPRArray6, n12, falconFPRArray6, n12 + n10, falconFPRArray, n, n7);
        this.ffSampling_fft_dyntree(samplerZ, samplerCtx, falconFPRArray6, n12, falconFPRArray6, n12 + n10, falconFPRArray3, n3, falconFPRArray3, n3 + n10, falconFPRArray4, n4, n6, n7 - 1, falconFPRArray6, n12 + n9);
        this.fft.poly_merge_fft(falconFPRArray, n, falconFPRArray6, n12, falconFPRArray6, n12 + n10, n7);
    }

    void ffSampling_fft(SamplerZ samplerZ, SamplerCtx samplerCtx, FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, FalconFPR[] falconFPRArray3, int n3, FalconFPR[] falconFPRArray4, int n4, FalconFPR[] falconFPRArray5, int n5, int n6, FalconFPR[] falconFPRArray6, int n7) {
        if (n6 == 2) {
            FalconFPR falconFPR;
            FalconFPR falconFPR2;
            int n8 = n3 + 4;
            int n9 = n3 + 8;
            FalconFPR falconFPR3 = falconFPRArray5[n5 + 0];
            FalconFPR falconFPR4 = falconFPRArray5[n5 + 2];
            FalconFPR falconFPR5 = falconFPRArray5[n5 + 1];
            FalconFPR falconFPR6 = falconFPRArray5[n5 + 3];
            FalconFPR falconFPR7 = this.fpr.fpr_add(falconFPR3, falconFPR5);
            FalconFPR falconFPR8 = this.fpr.fpr_add(falconFPR4, falconFPR6);
            FalconFPR falconFPR9 = this.fpr.fpr_half(falconFPR7);
            FalconFPR falconFPR10 = this.fpr.fpr_half(falconFPR8);
            falconFPR7 = this.fpr.fpr_sub(falconFPR3, falconFPR5);
            falconFPR8 = this.fpr.fpr_sub(falconFPR4, falconFPR6);
            FalconFPR falconFPR11 = this.fpr.fpr_mul(this.fpr.fpr_add(falconFPR7, falconFPR8), this.fpr.fpr_invsqrt8);
            FalconFPR falconFPR12 = this.fpr.fpr_mul(this.fpr.fpr_sub(falconFPR8, falconFPR7), this.fpr.fpr_invsqrt8);
            FalconFPR falconFPR13 = falconFPR11;
            FalconFPR falconFPR14 = falconFPR12;
            FalconFPR falconFPR15 = falconFPRArray3[n9 + 3];
            falconFPR11 = this.fpr.fpr_of(samplerZ.sample(samplerCtx, falconFPR13, falconFPR15));
            falconFPR12 = this.fpr.fpr_of(samplerZ.sample(samplerCtx, falconFPR14, falconFPR15));
            falconFPR3 = this.fpr.fpr_sub(falconFPR13, falconFPR11);
            falconFPR4 = this.fpr.fpr_sub(falconFPR14, falconFPR12);
            falconFPR5 = falconFPRArray3[n9 + 0];
            falconFPR6 = falconFPRArray3[n9 + 1];
            falconFPR7 = this.fpr.fpr_sub(this.fpr.fpr_mul(falconFPR3, falconFPR5), this.fpr.fpr_mul(falconFPR4, falconFPR6));
            falconFPR8 = this.fpr.fpr_add(this.fpr.fpr_mul(falconFPR3, falconFPR6), this.fpr.fpr_mul(falconFPR4, falconFPR5));
            falconFPR13 = this.fpr.fpr_add(falconFPR7, falconFPR9);
            falconFPR14 = this.fpr.fpr_add(falconFPR8, falconFPR10);
            falconFPR15 = falconFPRArray3[n9 + 2];
            falconFPR9 = this.fpr.fpr_of(samplerZ.sample(samplerCtx, falconFPR13, falconFPR15));
            falconFPR10 = this.fpr.fpr_of(samplerZ.sample(samplerCtx, falconFPR14, falconFPR15));
            falconFPR3 = falconFPR9;
            falconFPR4 = falconFPR10;
            falconFPR5 = falconFPR11;
            falconFPR6 = falconFPR12;
            falconFPR7 = this.fpr.fpr_mul(this.fpr.fpr_sub(falconFPR5, falconFPR6), this.fpr.fpr_invsqrt2);
            falconFPR8 = this.fpr.fpr_mul(this.fpr.fpr_add(falconFPR5, falconFPR6), this.fpr.fpr_invsqrt2);
            falconFPRArray2[n2 + 0] = falconFPR9 = this.fpr.fpr_add(falconFPR3, falconFPR7);
            falconFPRArray2[n2 + 2] = falconFPR11 = this.fpr.fpr_add(falconFPR4, falconFPR8);
            falconFPRArray2[n2 + 1] = falconFPR10 = this.fpr.fpr_sub(falconFPR3, falconFPR7);
            falconFPRArray2[n2 + 3] = falconFPR12 = this.fpr.fpr_sub(falconFPR4, falconFPR8);
            falconFPR9 = this.fpr.fpr_sub(falconFPRArray5[n5 + 0], falconFPR9);
            falconFPR10 = this.fpr.fpr_sub(falconFPRArray5[n5 + 1], falconFPR10);
            falconFPR11 = this.fpr.fpr_sub(falconFPRArray5[n5 + 2], falconFPR11);
            falconFPR12 = this.fpr.fpr_sub(falconFPRArray5[n5 + 3], falconFPR12);
            falconFPR3 = falconFPR9;
            falconFPR4 = falconFPR11;
            falconFPR5 = falconFPRArray3[n3 + 0];
            falconFPR6 = falconFPRArray3[n3 + 2];
            falconFPR9 = this.fpr.fpr_sub(this.fpr.fpr_mul(falconFPR3, falconFPR5), this.fpr.fpr_mul(falconFPR4, falconFPR6));
            falconFPR11 = this.fpr.fpr_add(this.fpr.fpr_mul(falconFPR3, falconFPR6), this.fpr.fpr_mul(falconFPR4, falconFPR5));
            falconFPR3 = falconFPR10;
            falconFPR4 = falconFPR12;
            falconFPR5 = falconFPRArray3[n3 + 1];
            falconFPR6 = falconFPRArray3[n3 + 3];
            falconFPR10 = this.fpr.fpr_sub(this.fpr.fpr_mul(falconFPR3, falconFPR5), this.fpr.fpr_mul(falconFPR4, falconFPR6));
            falconFPR12 = this.fpr.fpr_add(this.fpr.fpr_mul(falconFPR3, falconFPR6), this.fpr.fpr_mul(falconFPR4, falconFPR5));
            falconFPR9 = this.fpr.fpr_add(falconFPR9, falconFPRArray4[n4 + 0]);
            falconFPR10 = this.fpr.fpr_add(falconFPR10, falconFPRArray4[n4 + 1]);
            falconFPR11 = this.fpr.fpr_add(falconFPR11, falconFPRArray4[n4 + 2]);
            falconFPR12 = this.fpr.fpr_add(falconFPR12, falconFPRArray4[n4 + 3]);
            falconFPR3 = falconFPR9;
            falconFPR4 = falconFPR11;
            falconFPR5 = falconFPR10;
            falconFPR6 = falconFPR12;
            falconFPR7 = this.fpr.fpr_add(falconFPR3, falconFPR5);
            falconFPR8 = this.fpr.fpr_add(falconFPR4, falconFPR6);
            falconFPR9 = this.fpr.fpr_half(falconFPR7);
            falconFPR10 = this.fpr.fpr_half(falconFPR8);
            falconFPR7 = this.fpr.fpr_sub(falconFPR3, falconFPR5);
            falconFPR8 = this.fpr.fpr_sub(falconFPR4, falconFPR6);
            falconFPR11 = this.fpr.fpr_mul(this.fpr.fpr_add(falconFPR7, falconFPR8), this.fpr.fpr_invsqrt8);
            falconFPR12 = this.fpr.fpr_mul(this.fpr.fpr_sub(falconFPR8, falconFPR7), this.fpr.fpr_invsqrt8);
            falconFPR13 = falconFPR11;
            falconFPR14 = falconFPR12;
            falconFPR15 = falconFPRArray3[n8 + 3];
            falconFPR11 = falconFPR2 = this.fpr.fpr_of(samplerZ.sample(samplerCtx, falconFPR13, falconFPR15));
            falconFPR12 = falconFPR = this.fpr.fpr_of(samplerZ.sample(samplerCtx, falconFPR14, falconFPR15));
            falconFPR3 = this.fpr.fpr_sub(falconFPR13, falconFPR2);
            falconFPR4 = this.fpr.fpr_sub(falconFPR14, falconFPR);
            falconFPR5 = falconFPRArray3[n8 + 0];
            falconFPR6 = falconFPRArray3[n8 + 1];
            falconFPR7 = this.fpr.fpr_sub(this.fpr.fpr_mul(falconFPR3, falconFPR5), this.fpr.fpr_mul(falconFPR4, falconFPR6));
            falconFPR8 = this.fpr.fpr_add(this.fpr.fpr_mul(falconFPR3, falconFPR6), this.fpr.fpr_mul(falconFPR4, falconFPR5));
            falconFPR13 = this.fpr.fpr_add(falconFPR7, falconFPR9);
            falconFPR14 = this.fpr.fpr_add(falconFPR8, falconFPR10);
            falconFPR15 = falconFPRArray3[n8 + 2];
            falconFPR9 = this.fpr.fpr_of(samplerZ.sample(samplerCtx, falconFPR13, falconFPR15));
            falconFPR10 = this.fpr.fpr_of(samplerZ.sample(samplerCtx, falconFPR14, falconFPR15));
            falconFPR3 = falconFPR9;
            falconFPR4 = falconFPR10;
            falconFPR5 = falconFPR11;
            falconFPR6 = falconFPR12;
            falconFPR7 = this.fpr.fpr_mul(this.fpr.fpr_sub(falconFPR5, falconFPR6), this.fpr.fpr_invsqrt2);
            falconFPR8 = this.fpr.fpr_mul(this.fpr.fpr_add(falconFPR5, falconFPR6), this.fpr.fpr_invsqrt2);
            falconFPRArray[n + 0] = this.fpr.fpr_add(falconFPR3, falconFPR7);
            falconFPRArray[n + 2] = this.fpr.fpr_add(falconFPR4, falconFPR8);
            falconFPRArray[n + 1] = this.fpr.fpr_sub(falconFPR3, falconFPR7);
            falconFPRArray[n + 3] = this.fpr.fpr_sub(falconFPR4, falconFPR8);
            return;
        }
        if (n6 == 1) {
            FalconFPR falconFPR;
            FalconFPR falconFPR16;
            FalconFPR falconFPR17 = falconFPRArray5[n5 + 0];
            FalconFPR falconFPR18 = falconFPRArray5[n5 + 1];
            FalconFPR falconFPR19 = falconFPRArray3[n3 + 3];
            falconFPRArray2[n2 + 0] = falconFPR16 = this.fpr.fpr_of(samplerZ.sample(samplerCtx, falconFPR17, falconFPR19));
            falconFPRArray2[n2 + 1] = falconFPR = this.fpr.fpr_of(samplerZ.sample(samplerCtx, falconFPR18, falconFPR19));
            FalconFPR falconFPR20 = this.fpr.fpr_sub(falconFPR17, falconFPR16);
            FalconFPR falconFPR21 = this.fpr.fpr_sub(falconFPR18, falconFPR);
            FalconFPR falconFPR22 = falconFPRArray3[n3 + 0];
            FalconFPR falconFPR23 = falconFPRArray3[n3 + 1];
            FalconFPR falconFPR24 = this.fpr.fpr_sub(this.fpr.fpr_mul(falconFPR20, falconFPR22), this.fpr.fpr_mul(falconFPR21, falconFPR23));
            FalconFPR falconFPR25 = this.fpr.fpr_add(this.fpr.fpr_mul(falconFPR20, falconFPR23), this.fpr.fpr_mul(falconFPR21, falconFPR22));
            falconFPR17 = this.fpr.fpr_add(falconFPR24, falconFPRArray4[n4 + 0]);
            falconFPR18 = this.fpr.fpr_add(falconFPR25, falconFPRArray4[n4 + 1]);
            falconFPR19 = falconFPRArray3[n3 + 2];
            falconFPRArray[n + 0] = this.fpr.fpr_of(samplerZ.sample(samplerCtx, falconFPR17, falconFPR19));
            falconFPRArray[n + 1] = this.fpr.fpr_of(samplerZ.sample(samplerCtx, falconFPR18, falconFPR19));
            return;
        }
        int n10 = 1 << n6;
        int n11 = n10 >> 1;
        int n12 = n3 + n10;
        int n13 = n3 + n10 + this.ffLDL_treesize(n6 - 1);
        this.fft.poly_split_fft(falconFPRArray2, n2, falconFPRArray2, n2 + n11, falconFPRArray5, n5, n6);
        this.ffSampling_fft(samplerZ, samplerCtx, falconFPRArray6, n7, falconFPRArray6, n7 + n11, falconFPRArray3, n13, falconFPRArray2, n2, falconFPRArray2, n2 + n11, n6 - 1, falconFPRArray6, n7 + n10);
        this.fft.poly_merge_fft(falconFPRArray2, n2, falconFPRArray6, n7, falconFPRArray6, n7 + n11, n6);
        System.arraycopy(falconFPRArray5, n5, falconFPRArray6, n7, n10);
        this.fft.poly_sub(falconFPRArray6, n7, falconFPRArray2, n2, n6);
        this.fft.poly_mul_fft(falconFPRArray6, n7, falconFPRArray3, n3, n6);
        this.fft.poly_add(falconFPRArray6, n7, falconFPRArray4, n4, n6);
        this.fft.poly_split_fft(falconFPRArray, n, falconFPRArray, n + n11, falconFPRArray6, n7, n6);
        this.ffSampling_fft(samplerZ, samplerCtx, falconFPRArray6, n7, falconFPRArray6, n7 + n11, falconFPRArray3, n12, falconFPRArray, n, falconFPRArray, n + n11, n6 - 1, falconFPRArray6, n7 + n10);
        this.fft.poly_merge_fft(falconFPRArray, n, falconFPRArray6, n7, falconFPRArray6, n7 + n11, n6);
    }

    int do_sign_tree(SamplerZ samplerZ, SamplerCtx samplerCtx, short[] sArray, int n, FalconFPR[] falconFPRArray, int n2, short[] sArray2, int n3, int n4, FalconFPR[] falconFPRArray2, int n5) {
        int n6;
        int n7 = FalconSign.MKN(n4);
        int n8 = n5;
        int n9 = n8 + n7;
        int n10 = n2 + this.skoff_b00(n4);
        int n11 = n2 + this.skoff_b01(n4);
        int n12 = n2 + this.skoff_b10(n4);
        int n13 = n2 + this.skoff_b11(n4);
        int n14 = n2 + this.skoff_tree(n4);
        for (n6 = 0; n6 < n7; ++n6) {
            falconFPRArray2[n8 + n6] = this.fpr.fpr_of(sArray2[n3 + n6]);
        }
        this.fft.FFT(falconFPRArray2, n8, n4);
        FalconFPR falconFPR = this.fpr.fpr_inverse_of_q;
        System.arraycopy(falconFPRArray2, n8, falconFPRArray2, n9, n7);
        this.fft.poly_mul_fft(falconFPRArray2, n9, falconFPRArray, n11, n4);
        this.fft.poly_mulconst(falconFPRArray2, n9, this.fpr.fpr_neg(falconFPR), n4);
        this.fft.poly_mul_fft(falconFPRArray2, n8, falconFPRArray, n13, n4);
        this.fft.poly_mulconst(falconFPRArray2, n8, falconFPR, n4);
        int n15 = n9 + n7;
        int n16 = n15 + n7;
        this.ffSampling_fft(samplerZ, samplerCtx, falconFPRArray2, n15, falconFPRArray2, n16, falconFPRArray, n14, falconFPRArray2, n8, falconFPRArray2, n9, n4, falconFPRArray2, n16 + n7);
        System.arraycopy(falconFPRArray2, n15, falconFPRArray2, n8, n7);
        System.arraycopy(falconFPRArray2, n16, falconFPRArray2, n9, n7);
        this.fft.poly_mul_fft(falconFPRArray2, n15, falconFPRArray, n10, n4);
        this.fft.poly_mul_fft(falconFPRArray2, n16, falconFPRArray, n12, n4);
        this.fft.poly_add(falconFPRArray2, n15, falconFPRArray2, n16, n4);
        System.arraycopy(falconFPRArray2, n8, falconFPRArray2, n16, n7);
        this.fft.poly_mul_fft(falconFPRArray2, n16, falconFPRArray, n11, n4);
        System.arraycopy(falconFPRArray2, n15, falconFPRArray2, n8, n7);
        this.fft.poly_mul_fft(falconFPRArray2, n9, falconFPRArray, n13, n4);
        this.fft.poly_add(falconFPRArray2, n9, falconFPRArray2, n16, n4);
        this.fft.iFFT(falconFPRArray2, n8, n4);
        this.fft.iFFT(falconFPRArray2, n9, n4);
        short[] sArray3 = new short[n7];
        int n17 = 0;
        int n18 = 0;
        for (n6 = 0; n6 < n7; ++n6) {
            int n19 = (sArray2[n3 + n6] & 0xFFFF) - (int)this.fpr.fpr_rint(falconFPRArray2[n8 + n6]);
            n18 |= (n17 += n19 * n19);
            sArray3[n6] = (short)n19;
        }
        n17 |= -(n18 >>> 31);
        short[] sArray4 = new short[n7];
        for (n6 = 0; n6 < n7; ++n6) {
            sArray4[n6] = (short)(-this.fpr.fpr_rint(falconFPRArray2[n9 + n6]));
        }
        if (this.common.is_short_half(n17, sArray4, 0, n4) != 0) {
            System.arraycopy(sArray4, 0, sArray, n, n7);
            System.arraycopy(sArray3, 0, falconFPRArray2, n5, n7);
            return 1;
        }
        return 0;
    }

    int do_sign_dyn(SamplerZ samplerZ, SamplerCtx samplerCtx, short[] sArray, int n, byte[] byArray, int n2, byte[] byArray2, int n3, byte[] byArray3, int n4, byte[] byArray4, int n5, short[] sArray2, int n6, int n7, FalconFPR[] falconFPRArray, int n8) {
        int n9;
        int n10 = FalconSign.MKN(n7);
        int n11 = n8;
        int n12 = n11 + n10;
        int n13 = n12 + n10;
        int n14 = n13 + n10;
        this.smallints_to_fpr(falconFPRArray, n12, byArray, n2, n7);
        this.smallints_to_fpr(falconFPRArray, n11, byArray2, n3, n7);
        this.smallints_to_fpr(falconFPRArray, n14, byArray3, n4, n7);
        this.smallints_to_fpr(falconFPRArray, n13, byArray4, n5, n7);
        this.fft.FFT(falconFPRArray, n12, n7);
        this.fft.FFT(falconFPRArray, n11, n7);
        this.fft.FFT(falconFPRArray, n14, n7);
        this.fft.FFT(falconFPRArray, n13, n7);
        this.fft.poly_neg(falconFPRArray, n12, n7);
        this.fft.poly_neg(falconFPRArray, n14, n7);
        int n15 = n14 + n10;
        int n16 = n15 + n10;
        System.arraycopy(falconFPRArray, n12, falconFPRArray, n15, n10);
        this.fft.poly_mulselfadj_fft(falconFPRArray, n15, n7);
        System.arraycopy(falconFPRArray, n11, falconFPRArray, n16, n10);
        this.fft.poly_muladj_fft(falconFPRArray, n16, falconFPRArray, n13, n7);
        this.fft.poly_mulselfadj_fft(falconFPRArray, n11, n7);
        this.fft.poly_add(falconFPRArray, n11, falconFPRArray, n15, n7);
        System.arraycopy(falconFPRArray, n12, falconFPRArray, n15, n10);
        this.fft.poly_muladj_fft(falconFPRArray, n12, falconFPRArray, n14, n7);
        this.fft.poly_add(falconFPRArray, n12, falconFPRArray, n16, n7);
        this.fft.poly_mulselfadj_fft(falconFPRArray, n13, n7);
        System.arraycopy(falconFPRArray, n14, falconFPRArray, n16, n10);
        this.fft.poly_mulselfadj_fft(falconFPRArray, n16, n7);
        this.fft.poly_add(falconFPRArray, n13, falconFPRArray, n16, n7);
        int n17 = n11;
        int n18 = n12;
        int n19 = n13;
        n12 = n15;
        n15 = n12 + n10;
        n16 = n15 + n10;
        for (n9 = 0; n9 < n10; ++n9) {
            falconFPRArray[n15 + n9] = this.fpr.fpr_of(sArray2[n6 + n9]);
        }
        this.fft.FFT(falconFPRArray, n15, n7);
        FalconFPR falconFPR = this.fpr.fpr_inverse_of_q;
        System.arraycopy(falconFPRArray, n15, falconFPRArray, n16, n10);
        this.fft.poly_mul_fft(falconFPRArray, n16, falconFPRArray, n12, n7);
        this.fft.poly_mulconst(falconFPRArray, n16, this.fpr.fpr_neg(falconFPR), n7);
        this.fft.poly_mul_fft(falconFPRArray, n15, falconFPRArray, n14, n7);
        this.fft.poly_mulconst(falconFPRArray, n15, falconFPR, n7);
        System.arraycopy(falconFPRArray, n15, falconFPRArray, n14, 2 * n10);
        n15 = n19 + n10;
        n16 = n15 + n10;
        this.ffSampling_fft_dyntree(samplerZ, samplerCtx, falconFPRArray, n15, falconFPRArray, n16, falconFPRArray, n17, falconFPRArray, n18, falconFPRArray, n19, n7, n7, falconFPRArray, n16 + n10);
        n11 = n8;
        n12 = n11 + n10;
        n13 = n12 + n10;
        n14 = n13 + n10;
        System.arraycopy(falconFPRArray, n15, falconFPRArray, n14 + n10, n10 * 2);
        n15 = n14 + n10;
        n16 = n15 + n10;
        this.smallints_to_fpr(falconFPRArray, n12, byArray, n2, n7);
        this.smallints_to_fpr(falconFPRArray, n11, byArray2, n3, n7);
        this.smallints_to_fpr(falconFPRArray, n14, byArray3, n4, n7);
        this.smallints_to_fpr(falconFPRArray, n13, byArray4, n5, n7);
        this.fft.FFT(falconFPRArray, n12, n7);
        this.fft.FFT(falconFPRArray, n11, n7);
        this.fft.FFT(falconFPRArray, n14, n7);
        this.fft.FFT(falconFPRArray, n13, n7);
        this.fft.poly_neg(falconFPRArray, n12, n7);
        this.fft.poly_neg(falconFPRArray, n14, n7);
        int n20 = n16 + n10;
        int n21 = n20 + n10;
        System.arraycopy(falconFPRArray, n15, falconFPRArray, n20, n10);
        System.arraycopy(falconFPRArray, n16, falconFPRArray, n21, n10);
        this.fft.poly_mul_fft(falconFPRArray, n20, falconFPRArray, n11, n7);
        this.fft.poly_mul_fft(falconFPRArray, n21, falconFPRArray, n13, n7);
        this.fft.poly_add(falconFPRArray, n20, falconFPRArray, n21, n7);
        System.arraycopy(falconFPRArray, n15, falconFPRArray, n21, n10);
        this.fft.poly_mul_fft(falconFPRArray, n21, falconFPRArray, n12, n7);
        System.arraycopy(falconFPRArray, n20, falconFPRArray, n15, n10);
        this.fft.poly_mul_fft(falconFPRArray, n16, falconFPRArray, n14, n7);
        this.fft.poly_add(falconFPRArray, n16, falconFPRArray, n21, n7);
        this.fft.iFFT(falconFPRArray, n15, n7);
        this.fft.iFFT(falconFPRArray, n16, n7);
        short[] sArray3 = new short[n10];
        int n22 = 0;
        int n23 = 0;
        for (n9 = 0; n9 < n10; ++n9) {
            int n24 = (sArray2[n6 + n9] & 0xFFFF) - (int)this.fpr.fpr_rint(falconFPRArray[n15 + n9]);
            n23 |= (n22 += n24 * n24);
            sArray3[n9] = (short)n24;
        }
        n22 |= -(n23 >>> 31);
        short[] sArray4 = new short[n10];
        for (n9 = 0; n9 < n10; ++n9) {
            sArray4[n9] = (short)(-this.fpr.fpr_rint(falconFPRArray[n16 + n9]));
        }
        if (this.common.is_short_half(n22, sArray4, 0, n7) != 0) {
            System.arraycopy(sArray4, 0, sArray, n, n10);
            return 1;
        }
        return 0;
    }

    void sign_tree(short[] sArray, int n, SHAKE256 sHAKE256, FalconFPR[] falconFPRArray, int n2, short[] sArray2, int n3, int n4, FalconFPR[] falconFPRArray2, int n5) {
        SamplerCtx samplerCtx;
        SamplerCtx samplerCtx2;
        SamplerZ samplerZ;
        int n6 = n5;
        do {
            samplerCtx = new SamplerCtx();
            samplerZ = new SamplerZ();
            samplerCtx.sigma_min = this.fpr.fpr_sigma_min[n4];
            samplerCtx.p.prng_init(sHAKE256);
        } while (this.do_sign_tree(samplerZ, samplerCtx2 = samplerCtx, sArray, n, falconFPRArray, n2, sArray2, n3, n4, falconFPRArray2, n6) == 0);
    }

    void sign_dyn(short[] sArray, int n, SHAKE256 sHAKE256, byte[] byArray, int n2, byte[] byArray2, int n3, byte[] byArray3, int n4, byte[] byArray4, int n5, short[] sArray2, int n6, int n7, FalconFPR[] falconFPRArray, int n8) {
        SamplerCtx samplerCtx;
        SamplerCtx samplerCtx2;
        SamplerZ samplerZ;
        int n9 = n8;
        do {
            samplerCtx = new SamplerCtx();
            samplerZ = new SamplerZ();
            samplerCtx.sigma_min = this.fpr.fpr_sigma_min[n7];
            samplerCtx.p.prng_init(sHAKE256);
        } while (this.do_sign_dyn(samplerZ, samplerCtx2 = samplerCtx, sArray, n, byArray, n2, byArray2, n3, byArray3, n4, byArray4, n5, sArray2, n6, n7, falconFPRArray, n9) == 0);
    }
}

