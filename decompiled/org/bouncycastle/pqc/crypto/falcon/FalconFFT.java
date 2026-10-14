/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.falcon;

import org.bouncycastle.pqc.crypto.falcon.ComplexNumberWrapper;
import org.bouncycastle.pqc.crypto.falcon.FPREngine;
import org.bouncycastle.pqc.crypto.falcon.FalconFPR;

class FalconFFT {
    FPREngine fpr = new FPREngine();

    FalconFFT() {
    }

    ComplexNumberWrapper FPC_ADD(FalconFPR falconFPR, FalconFPR falconFPR2, FalconFPR falconFPR3, FalconFPR falconFPR4) {
        FalconFPR falconFPR5 = this.fpr.fpr_add(falconFPR, falconFPR3);
        FalconFPR falconFPR6 = this.fpr.fpr_add(falconFPR2, falconFPR4);
        return new ComplexNumberWrapper(falconFPR5, falconFPR6);
    }

    ComplexNumberWrapper FPC_SUB(FalconFPR falconFPR, FalconFPR falconFPR2, FalconFPR falconFPR3, FalconFPR falconFPR4) {
        FalconFPR falconFPR5 = this.fpr.fpr_sub(falconFPR, falconFPR3);
        FalconFPR falconFPR6 = this.fpr.fpr_sub(falconFPR2, falconFPR4);
        return new ComplexNumberWrapper(falconFPR5, falconFPR6);
    }

    ComplexNumberWrapper FPC_MUL(FalconFPR falconFPR, FalconFPR falconFPR2, FalconFPR falconFPR3, FalconFPR falconFPR4) {
        FalconFPR falconFPR5 = falconFPR;
        FalconFPR falconFPR6 = falconFPR2;
        FalconFPR falconFPR7 = falconFPR3;
        FalconFPR falconFPR8 = falconFPR4;
        FalconFPR falconFPR9 = this.fpr.fpr_sub(this.fpr.fpr_mul(falconFPR5, falconFPR7), this.fpr.fpr_mul(falconFPR6, falconFPR8));
        FalconFPR falconFPR10 = this.fpr.fpr_add(this.fpr.fpr_mul(falconFPR5, falconFPR8), this.fpr.fpr_mul(falconFPR6, falconFPR7));
        return new ComplexNumberWrapper(falconFPR9, falconFPR10);
    }

    ComplexNumberWrapper FPC_SQR(FalconFPR falconFPR, FalconFPR falconFPR2) {
        FalconFPR falconFPR3 = falconFPR;
        FalconFPR falconFPR4 = falconFPR2;
        FalconFPR falconFPR5 = this.fpr.fpr_sub(this.fpr.fpr_sqr(falconFPR3), this.fpr.fpr_sqr(falconFPR4));
        FalconFPR falconFPR6 = this.fpr.fpr_double(this.fpr.fpr_mul(falconFPR3, falconFPR4));
        return new ComplexNumberWrapper(falconFPR5, falconFPR6);
    }

    ComplexNumberWrapper FPC_INV(FalconFPR falconFPR, FalconFPR falconFPR2) {
        FalconFPR falconFPR3 = falconFPR;
        FalconFPR falconFPR4 = falconFPR2;
        FalconFPR falconFPR5 = this.fpr.fpr_add(this.fpr.fpr_sqr(falconFPR3), this.fpr.fpr_sqr(falconFPR4));
        falconFPR5 = this.fpr.fpr_inv(falconFPR5);
        FalconFPR falconFPR6 = this.fpr.fpr_mul(falconFPR3, falconFPR5);
        FalconFPR falconFPR7 = this.fpr.fpr_mul(this.fpr.fpr_neg(falconFPR4), falconFPR5);
        return new ComplexNumberWrapper(falconFPR6, falconFPR7);
    }

    ComplexNumberWrapper FPC_DIV(FalconFPR falconFPR, FalconFPR falconFPR2, FalconFPR falconFPR3, FalconFPR falconFPR4) {
        FalconFPR falconFPR5 = falconFPR;
        FalconFPR falconFPR6 = falconFPR2;
        FalconFPR falconFPR7 = falconFPR3;
        FalconFPR falconFPR8 = falconFPR4;
        FalconFPR falconFPR9 = this.fpr.fpr_add(this.fpr.fpr_sqr(falconFPR7), this.fpr.fpr_sqr(falconFPR8));
        falconFPR9 = this.fpr.fpr_inv(falconFPR9);
        falconFPR7 = this.fpr.fpr_mul(falconFPR7, falconFPR9);
        falconFPR8 = this.fpr.fpr_mul(this.fpr.fpr_neg(falconFPR8), falconFPR9);
        FalconFPR falconFPR10 = this.fpr.fpr_sub(this.fpr.fpr_mul(falconFPR5, falconFPR7), this.fpr.fpr_mul(falconFPR6, falconFPR8));
        FalconFPR falconFPR11 = this.fpr.fpr_add(this.fpr.fpr_mul(falconFPR5, falconFPR8), this.fpr.fpr_mul(falconFPR6, falconFPR7));
        return new ComplexNumberWrapper(falconFPR10, falconFPR11);
    }

    void FFT(FalconFPR[] falconFPRArray, int n, int n2) {
        int n3;
        int n4 = 1 << n2;
        int n5 = n3 = n4 >> 1;
        int n6 = 1;
        int n7 = 2;
        while (n6 < n2) {
            int n8 = n5 >> 1;
            int n9 = n7 >> 1;
            int n10 = 0;
            int n11 = 0;
            while (n10 < n9) {
                int n12 = n11 + n8;
                FalconFPR falconFPR = this.fpr.fpr_gm_tab[(n7 + n10 << 1) + 0];
                FalconFPR falconFPR2 = this.fpr.fpr_gm_tab[(n7 + n10 << 1) + 1];
                for (int i = n11; i < n12; ++i) {
                    FalconFPR falconFPR3 = falconFPRArray[n + i];
                    FalconFPR falconFPR4 = falconFPRArray[n + i + n3];
                    FalconFPR falconFPR5 = falconFPRArray[n + i + n8];
                    FalconFPR falconFPR6 = falconFPRArray[n + i + n8 + n3];
                    ComplexNumberWrapper complexNumberWrapper = this.FPC_MUL(falconFPR5, falconFPR6, falconFPR, falconFPR2);
                    falconFPR5 = complexNumberWrapper.re;
                    falconFPR6 = complexNumberWrapper.im;
                    complexNumberWrapper = this.FPC_ADD(falconFPR3, falconFPR4, falconFPR5, falconFPR6);
                    falconFPRArray[n + i] = complexNumberWrapper.re;
                    falconFPRArray[n + i + n3] = complexNumberWrapper.im;
                    complexNumberWrapper = this.FPC_SUB(falconFPR3, falconFPR4, falconFPR5, falconFPR6);
                    falconFPRArray[n + i + n8] = complexNumberWrapper.re;
                    falconFPRArray[n + i + n8 + n3] = complexNumberWrapper.im;
                }
                ++n10;
                n11 += n5;
            }
            n5 = n8;
            ++n6;
            n7 <<= 1;
        }
    }

    void iFFT(FalconFPR[] falconFPRArray, int n, int n2) {
        int n3;
        int n4 = 1 << n2;
        int n5 = 1;
        int n6 = n4;
        int n7 = n4 >> 1;
        for (n3 = n2; n3 > 1; --n3) {
            int n8 = n6 >> 1;
            int n9 = n5 << 1;
            int n10 = 0;
            for (int i = 0; i < n7; i += n9) {
                int n11 = i + n5;
                FalconFPR falconFPR = this.fpr.fpr_gm_tab[(n8 + n10 << 1) + 0];
                FalconFPR falconFPR2 = this.fpr.fpr_neg(this.fpr.fpr_gm_tab[(n8 + n10 << 1) + 1]);
                for (int j = i; j < n11; ++j) {
                    FalconFPR falconFPR3 = falconFPRArray[n + j];
                    FalconFPR falconFPR4 = falconFPRArray[n + j + n7];
                    FalconFPR falconFPR5 = falconFPRArray[n + j + n5];
                    FalconFPR falconFPR6 = falconFPRArray[n + j + n5 + n7];
                    ComplexNumberWrapper complexNumberWrapper = this.FPC_ADD(falconFPR3, falconFPR4, falconFPR5, falconFPR6);
                    falconFPRArray[n + j] = complexNumberWrapper.re;
                    falconFPRArray[n + j + n7] = complexNumberWrapper.im;
                    complexNumberWrapper = this.FPC_SUB(falconFPR3, falconFPR4, falconFPR5, falconFPR6);
                    falconFPR3 = complexNumberWrapper.re;
                    falconFPR4 = complexNumberWrapper.im;
                    complexNumberWrapper = this.FPC_MUL(falconFPR3, falconFPR4, falconFPR, falconFPR2);
                    falconFPRArray[n + j + n5] = complexNumberWrapper.re;
                    falconFPRArray[n + j + n5 + n7] = complexNumberWrapper.im;
                }
                ++n10;
            }
            n5 = n9;
            n6 = n8;
        }
        if (n2 > 0) {
            FalconFPR falconFPR = this.fpr.fpr_p2_tab[n2];
            for (n3 = 0; n3 < n4; ++n3) {
                falconFPRArray[n + n3] = this.fpr.fpr_mul(falconFPRArray[n + n3], falconFPR);
            }
        }
    }

    void poly_add(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, int n3) {
        int n4 = 1 << n3;
        for (int i = 0; i < n4; ++i) {
            falconFPRArray[n + i] = this.fpr.fpr_add(falconFPRArray[n + i], falconFPRArray2[n2 + i]);
        }
    }

    void poly_sub(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, int n3) {
        int n4 = 1 << n3;
        for (int i = 0; i < n4; ++i) {
            falconFPRArray[n + i] = this.fpr.fpr_sub(falconFPRArray[n + i], falconFPRArray2[n2 + i]);
        }
    }

    void poly_neg(FalconFPR[] falconFPRArray, int n, int n2) {
        int n3 = 1 << n2;
        for (int i = 0; i < n3; ++i) {
            falconFPRArray[n + i] = this.fpr.fpr_neg(falconFPRArray[n + i]);
        }
    }

    void poly_adj_fft(FalconFPR[] falconFPRArray, int n, int n2) {
        int n3 = 1 << n2;
        for (int i = n3 >> 1; i < n3; ++i) {
            falconFPRArray[n + i] = this.fpr.fpr_neg(falconFPRArray[n + i]);
        }
    }

    void poly_mul_fft(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, int n3) {
        int n4 = 1 << n3;
        int n5 = n4 >> 1;
        for (int i = 0; i < n5; ++i) {
            FalconFPR falconFPR = falconFPRArray[n + i];
            FalconFPR falconFPR2 = falconFPRArray[n + i + n5];
            FalconFPR falconFPR3 = falconFPRArray2[n2 + i];
            FalconFPR falconFPR4 = falconFPRArray2[n2 + i + n5];
            ComplexNumberWrapper complexNumberWrapper = this.FPC_MUL(falconFPR, falconFPR2, falconFPR3, falconFPR4);
            falconFPRArray[n + i] = complexNumberWrapper.re;
            falconFPRArray[n + i + n5] = complexNumberWrapper.im;
        }
    }

    void poly_muladj_fft(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, int n3) {
        int n4 = 1 << n3;
        int n5 = n4 >> 1;
        for (int i = 0; i < n5; ++i) {
            FalconFPR falconFPR = falconFPRArray[n + i];
            FalconFPR falconFPR2 = falconFPRArray[n + i + n5];
            FalconFPR falconFPR3 = falconFPRArray2[n2 + i];
            FalconFPR falconFPR4 = this.fpr.fpr_neg(falconFPRArray2[n2 + i + n5]);
            ComplexNumberWrapper complexNumberWrapper = this.FPC_MUL(falconFPR, falconFPR2, falconFPR3, falconFPR4);
            falconFPRArray[n + i] = complexNumberWrapper.re;
            falconFPRArray[n + i + n5] = complexNumberWrapper.im;
        }
    }

    void poly_mulselfadj_fft(FalconFPR[] falconFPRArray, int n, int n2) {
        int n3 = 1 << n2;
        int n4 = n3 >> 1;
        for (int i = 0; i < n4; ++i) {
            FalconFPR falconFPR = falconFPRArray[n + i];
            FalconFPR falconFPR2 = falconFPRArray[n + i + n4];
            falconFPRArray[n + i] = this.fpr.fpr_add(this.fpr.fpr_sqr(falconFPR), this.fpr.fpr_sqr(falconFPR2));
            falconFPRArray[n + i + n4] = this.fpr.fpr_zero;
        }
    }

    void poly_mulconst(FalconFPR[] falconFPRArray, int n, FalconFPR falconFPR, int n2) {
        int n3 = 1 << n2;
        for (int i = 0; i < n3; ++i) {
            falconFPRArray[n + i] = this.fpr.fpr_mul(falconFPRArray[n + i], falconFPR);
        }
    }

    void poly_div_fft(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, int n3) {
        int n4 = 1 << n3;
        int n5 = n4 >> 1;
        for (int i = 0; i < n5; ++i) {
            FalconFPR falconFPR = falconFPRArray[n + i];
            FalconFPR falconFPR2 = falconFPRArray[n + i + n5];
            FalconFPR falconFPR3 = falconFPRArray2[n2 + i];
            FalconFPR falconFPR4 = falconFPRArray2[n2 + i + n5];
            ComplexNumberWrapper complexNumberWrapper = this.FPC_DIV(falconFPR, falconFPR2, falconFPR3, falconFPR4);
            falconFPRArray[n + i] = complexNumberWrapper.re;
            falconFPRArray[n + i + n5] = complexNumberWrapper.im;
        }
    }

    void poly_invnorm2_fft(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, FalconFPR[] falconFPRArray3, int n3, int n4) {
        int n5 = 1 << n4;
        int n6 = n5 >> 1;
        for (int i = 0; i < n6; ++i) {
            FalconFPR falconFPR = falconFPRArray2[n2 + i];
            FalconFPR falconFPR2 = falconFPRArray2[n2 + i + n6];
            FalconFPR falconFPR3 = falconFPRArray3[n3 + i];
            FalconFPR falconFPR4 = falconFPRArray3[n3 + i + n6];
            falconFPRArray[n + i] = this.fpr.fpr_inv(this.fpr.fpr_add(this.fpr.fpr_add(this.fpr.fpr_sqr(falconFPR), this.fpr.fpr_sqr(falconFPR2)), this.fpr.fpr_add(this.fpr.fpr_sqr(falconFPR3), this.fpr.fpr_sqr(falconFPR4))));
        }
    }

    void poly_add_muladj_fft(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, FalconFPR[] falconFPRArray3, int n3, FalconFPR[] falconFPRArray4, int n4, FalconFPR[] falconFPRArray5, int n5, int n6) {
        int n7 = 1 << n6;
        int n8 = n7 >> 1;
        for (int i = 0; i < n8; ++i) {
            FalconFPR falconFPR = falconFPRArray2[n2 + i];
            FalconFPR falconFPR2 = falconFPRArray2[n2 + i + n8];
            FalconFPR falconFPR3 = falconFPRArray3[n3 + i];
            FalconFPR falconFPR4 = falconFPRArray3[n3 + i + n8];
            FalconFPR falconFPR5 = falconFPRArray4[n4 + i];
            FalconFPR falconFPR6 = falconFPRArray4[n4 + i + n8];
            FalconFPR falconFPR7 = falconFPRArray5[n5 + i];
            FalconFPR falconFPR8 = falconFPRArray5[n5 + i + n8];
            ComplexNumberWrapper complexNumberWrapper = this.FPC_MUL(falconFPR, falconFPR2, falconFPR5, this.fpr.fpr_neg(falconFPR6));
            FalconFPR falconFPR9 = complexNumberWrapper.re;
            FalconFPR falconFPR10 = complexNumberWrapper.im;
            complexNumberWrapper = this.FPC_MUL(falconFPR3, falconFPR4, falconFPR7, this.fpr.fpr_neg(falconFPR8));
            FalconFPR falconFPR11 = complexNumberWrapper.re;
            FalconFPR falconFPR12 = complexNumberWrapper.im;
            falconFPRArray[n + i] = this.fpr.fpr_add(falconFPR9, falconFPR11);
            falconFPRArray[n + i + n8] = this.fpr.fpr_add(falconFPR10, falconFPR12);
        }
    }

    void poly_mul_autoadj_fft(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, int n3) {
        int n4 = 1 << n3;
        int n5 = n4 >> 1;
        for (int i = 0; i < n5; ++i) {
            falconFPRArray[n + i] = this.fpr.fpr_mul(falconFPRArray[n + i], falconFPRArray2[n2 + i]);
            falconFPRArray[n + i + n5] = this.fpr.fpr_mul(falconFPRArray[n + i + n5], falconFPRArray2[n2 + i]);
        }
    }

    void poly_div_autoadj_fft(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, int n3) {
        int n4 = 1 << n3;
        int n5 = n4 >> 1;
        for (int i = 0; i < n5; ++i) {
            FalconFPR falconFPR = this.fpr.fpr_inv(falconFPRArray2[n2 + i]);
            falconFPRArray[n + i] = this.fpr.fpr_mul(falconFPRArray[n + i], falconFPR);
            falconFPRArray[n + i + n5] = this.fpr.fpr_mul(falconFPRArray[n + i + n5], falconFPR);
        }
    }

    void poly_LDL_fft(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, FalconFPR[] falconFPRArray3, int n3, int n4) {
        int n5 = 1 << n4;
        int n6 = n5 >> 1;
        for (int i = 0; i < n6; ++i) {
            FalconFPR falconFPR = falconFPRArray[n + i];
            FalconFPR falconFPR2 = falconFPRArray[n + i + n6];
            FalconFPR falconFPR3 = falconFPRArray2[n2 + i];
            FalconFPR falconFPR4 = falconFPRArray2[n2 + i + n6];
            FalconFPR falconFPR5 = falconFPRArray3[n3 + i];
            FalconFPR falconFPR6 = falconFPRArray3[n3 + i + n6];
            ComplexNumberWrapper complexNumberWrapper = this.FPC_DIV(falconFPR3, falconFPR4, falconFPR, falconFPR2);
            FalconFPR falconFPR7 = complexNumberWrapper.re;
            FalconFPR falconFPR8 = complexNumberWrapper.im;
            complexNumberWrapper = this.FPC_MUL(falconFPR7, falconFPR8, falconFPR3, this.fpr.fpr_neg(falconFPR4));
            falconFPR3 = complexNumberWrapper.re;
            falconFPR4 = complexNumberWrapper.im;
            complexNumberWrapper = this.FPC_SUB(falconFPR5, falconFPR6, falconFPR3, falconFPR4);
            falconFPRArray3[n3 + i] = complexNumberWrapper.re;
            falconFPRArray3[n3 + i + n6] = complexNumberWrapper.im;
            falconFPRArray2[n2 + i] = falconFPR7;
            falconFPRArray2[n2 + i + n6] = this.fpr.fpr_neg(falconFPR8);
        }
    }

    void poly_LDLmv_fft(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, FalconFPR[] falconFPRArray3, int n3, FalconFPR[] falconFPRArray4, int n4, FalconFPR[] falconFPRArray5, int n5, int n6) {
        int n7 = 1 << n6;
        int n8 = n7 >> 1;
        for (int i = 0; i < n8; ++i) {
            FalconFPR falconFPR = falconFPRArray3[n3 + i];
            FalconFPR falconFPR2 = falconFPRArray3[n3 + i + n8];
            FalconFPR falconFPR3 = falconFPRArray4[n4 + i];
            FalconFPR falconFPR4 = falconFPRArray4[n4 + i + n8];
            FalconFPR falconFPR5 = falconFPRArray5[n5 + i];
            FalconFPR falconFPR6 = falconFPRArray5[n5 + i + n8];
            ComplexNumberWrapper complexNumberWrapper = this.FPC_DIV(falconFPR3, falconFPR4, falconFPR, falconFPR2);
            FalconFPR falconFPR7 = complexNumberWrapper.re;
            FalconFPR falconFPR8 = complexNumberWrapper.im;
            complexNumberWrapper = this.FPC_MUL(falconFPR7, falconFPR8, falconFPR3, this.fpr.fpr_neg(falconFPR4));
            falconFPR3 = complexNumberWrapper.re;
            falconFPR4 = complexNumberWrapper.im;
            complexNumberWrapper = this.FPC_SUB(falconFPR5, falconFPR6, falconFPR3, falconFPR4);
            falconFPRArray[n + i] = complexNumberWrapper.re;
            falconFPRArray[n + i + n8] = complexNumberWrapper.im;
            falconFPRArray2[n2 + i] = falconFPR7;
            falconFPRArray2[n2 + i + n8] = this.fpr.fpr_neg(falconFPR8);
        }
    }

    void poly_split_fft(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, FalconFPR[] falconFPRArray3, int n3, int n4) {
        int n5 = 1 << n4;
        int n6 = n5 >> 1;
        int n7 = n6 >> 1;
        falconFPRArray[n + 0] = falconFPRArray3[n3 + 0];
        falconFPRArray2[n2 + 0] = falconFPRArray3[n3 + n6];
        for (int i = 0; i < n7; ++i) {
            FalconFPR falconFPR = falconFPRArray3[n3 + (i << 1) + 0];
            FalconFPR falconFPR2 = falconFPRArray3[n3 + (i << 1) + 0 + n6];
            FalconFPR falconFPR3 = falconFPRArray3[n3 + (i << 1) + 1];
            FalconFPR falconFPR4 = falconFPRArray3[n3 + (i << 1) + 1 + n6];
            ComplexNumberWrapper complexNumberWrapper = this.FPC_ADD(falconFPR, falconFPR2, falconFPR3, falconFPR4);
            FalconFPR falconFPR5 = complexNumberWrapper.re;
            FalconFPR falconFPR6 = complexNumberWrapper.im;
            falconFPRArray[n + i] = this.fpr.fpr_half(falconFPR5);
            falconFPRArray[n + i + n7] = this.fpr.fpr_half(falconFPR6);
            complexNumberWrapper = this.FPC_SUB(falconFPR, falconFPR2, falconFPR3, falconFPR4);
            falconFPR5 = complexNumberWrapper.re;
            falconFPR6 = complexNumberWrapper.im;
            complexNumberWrapper = this.FPC_MUL(falconFPR5, falconFPR6, this.fpr.fpr_gm_tab[(i + n6 << 1) + 0], this.fpr.fpr_neg(this.fpr.fpr_gm_tab[(i + n6 << 1) + 1]));
            falconFPR5 = complexNumberWrapper.re;
            falconFPR6 = complexNumberWrapper.im;
            falconFPRArray2[n2 + i] = this.fpr.fpr_half(falconFPR5);
            falconFPRArray2[n2 + i + n7] = this.fpr.fpr_half(falconFPR6);
        }
    }

    void poly_merge_fft(FalconFPR[] falconFPRArray, int n, FalconFPR[] falconFPRArray2, int n2, FalconFPR[] falconFPRArray3, int n3, int n4) {
        int n5 = 1 << n4;
        int n6 = n5 >> 1;
        int n7 = n6 >> 1;
        falconFPRArray[n + 0] = falconFPRArray2[n2 + 0];
        falconFPRArray[n + n6] = falconFPRArray3[n3 + 0];
        for (int i = 0; i < n7; ++i) {
            FalconFPR falconFPR = falconFPRArray2[n2 + i];
            FalconFPR falconFPR2 = falconFPRArray2[n2 + i + n7];
            ComplexNumberWrapper complexNumberWrapper = this.FPC_MUL(falconFPRArray3[n3 + i], falconFPRArray3[n3 + i + n7], this.fpr.fpr_gm_tab[(i + n6 << 1) + 0], this.fpr.fpr_gm_tab[(i + n6 << 1) + 1]);
            FalconFPR falconFPR3 = complexNumberWrapper.re;
            FalconFPR falconFPR4 = complexNumberWrapper.im;
            complexNumberWrapper = this.FPC_ADD(falconFPR, falconFPR2, falconFPR3, falconFPR4);
            FalconFPR falconFPR5 = complexNumberWrapper.re;
            FalconFPR falconFPR6 = complexNumberWrapper.im;
            falconFPRArray[n + (i << 1) + 0] = falconFPR5;
            falconFPRArray[n + (i << 1) + 0 + n6] = falconFPR6;
            complexNumberWrapper = this.FPC_SUB(falconFPR, falconFPR2, falconFPR3, falconFPR4);
            falconFPR5 = complexNumberWrapper.re;
            falconFPR6 = complexNumberWrapper.im;
            falconFPRArray[n + (i << 1) + 1] = falconFPR5;
            falconFPRArray[n + (i << 1) + 1 + n6] = falconFPR6;
        }
    }
}

