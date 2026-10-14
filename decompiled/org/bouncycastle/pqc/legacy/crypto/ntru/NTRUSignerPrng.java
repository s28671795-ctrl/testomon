/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.crypto.ntru;

import java.nio.ByteBuffer;
import org.bouncycastle.crypto.Digest;

public class NTRUSignerPrng {
    private int counter = 0;
    private byte[] seed;
    private Digest hashAlg;

    NTRUSignerPrng(byte[] byArray, Digest digest) {
        this.seed = byArray;
        this.hashAlg = digest;
    }

    byte[] nextBytes(int n) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(n);
        while (byteBuffer.hasRemaining()) {
            ByteBuffer byteBuffer2 = ByteBuffer.allocate(this.seed.length + 4);
            byteBuffer2.put(this.seed);
            byteBuffer2.putInt(this.counter);
            byte[] byArray = byteBuffer2.array();
            byte[] byArray2 = new byte[this.hashAlg.getDigestSize()];
            this.hashAlg.update(byArray, 0, byArray.length);
            this.hashAlg.doFinal(byArray2, 0);
            if (byteBuffer.remaining() < byArray2.length) {
                byteBuffer.put(byArray2, 0, byteBuffer.remaining());
            } else {
                byteBuffer.put(byArray2);
            }
            ++this.counter;
        }
        return byteBuffer.array();
    }
}

