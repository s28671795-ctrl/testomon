/*
 * Decompiled with CFR 0.152.
 */
package org.h2.compress;

import org.h2.compress.Compressor;

public class CompressNo
implements Compressor {
    @Override
    public int getAlgorithm() {
        return 0;
    }

    @Override
    public void setOptions(String string) {
    }

    @Override
    public int compress(byte[] byArray, int n, int n2, byte[] byArray2, int n3) {
        System.arraycopy(byArray, n, byArray2, n3, n2);
        return n3 + n2;
    }

    @Override
    public void expand(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4) {
        System.arraycopy(byArray, n, byArray2, n3, n4);
    }
}

