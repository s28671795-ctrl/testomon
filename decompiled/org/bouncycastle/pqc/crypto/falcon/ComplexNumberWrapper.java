/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.falcon;

import org.bouncycastle.pqc.crypto.falcon.FalconFPR;

class ComplexNumberWrapper {
    FalconFPR re;
    FalconFPR im;

    ComplexNumberWrapper(FalconFPR falconFPR, FalconFPR falconFPR2) {
        this.re = falconFPR;
        this.im = falconFPR2;
    }
}

