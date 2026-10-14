/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.falcon;

import org.bouncycastle.pqc.crypto.falcon.FalconFPR;
import org.bouncycastle.pqc.crypto.falcon.FalconRNG;

class SamplerCtx {
    FalconFPR sigma_min = new FalconFPR(0.0);
    FalconRNG p = new FalconRNG();

    SamplerCtx() {
    }
}

