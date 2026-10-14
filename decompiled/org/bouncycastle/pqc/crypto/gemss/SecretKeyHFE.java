/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.gemss;

import org.bouncycastle.pqc.crypto.gemss.GeMSSEngine;
import org.bouncycastle.pqc.crypto.gemss.Pointer;

class SecretKeyHFE {
    complete_sparse_monic_gf2nx F_struct = new complete_sparse_monic_gf2nx();
    public Pointer F_HFEv;
    public Pointer S;
    public Pointer T;
    public Pointer sk_uncomp;

    public SecretKeyHFE(GeMSSEngine geMSSEngine) {
        this.F_struct.L = new int[geMSSEngine.NB_COEFS_HFEPOLY];
    }

    static class complete_sparse_monic_gf2nx {
        public Pointer poly;
        public int[] L;
    }
}

