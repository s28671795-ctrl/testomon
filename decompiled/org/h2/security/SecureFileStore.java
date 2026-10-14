/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security;

import org.h2.security.BlockCipher;
import org.h2.security.CipherFactory;
import org.h2.security.SHA256;
import org.h2.store.DataHandler;
import org.h2.store.FileStore;
import org.h2.util.Bits;
import org.h2.util.MathUtils;

public class SecureFileStore
extends FileStore {
    private byte[] key;
    private final BlockCipher cipher;
    private final BlockCipher cipherForInitVector;
    private byte[] buffer = new byte[4];
    private long pos;
    private final byte[] bufferForInitVector;
    private final int keyIterations;

    public SecureFileStore(DataHandler dataHandler, String string, String string2, String string3, byte[] byArray, int n) {
        super(dataHandler, string, string2);
        this.key = byArray;
        this.cipher = CipherFactory.getBlockCipher(string3);
        this.cipherForInitVector = CipherFactory.getBlockCipher(string3);
        this.keyIterations = n;
        this.bufferForInitVector = new byte[16];
    }

    @Override
    protected byte[] generateSalt() {
        return MathUtils.secureRandomBytes(16);
    }

    @Override
    protected void initKey(byte[] byArray) {
        this.key = SHA256.getHashWithSalt(this.key, byArray);
        for (int i = 0; i < this.keyIterations; ++i) {
            this.key = SHA256.getHash(this.key, true);
        }
        this.cipher.setKey(this.key);
        this.key = SHA256.getHash(this.key, true);
        this.cipherForInitVector.setKey(this.key);
    }

    @Override
    protected void writeDirect(byte[] byArray, int n, int n2) {
        super.write(byArray, n, n2);
        this.pos += (long)n2;
    }

    @Override
    public void write(byte[] byArray, int n, int n2) {
        if (this.buffer.length < byArray.length) {
            this.buffer = new byte[n2];
        }
        System.arraycopy(byArray, n, this.buffer, 0, n2);
        this.xorInitVector(this.buffer, 0, n2, this.pos);
        this.cipher.encrypt(this.buffer, 0, n2);
        super.write(this.buffer, 0, n2);
        this.pos += (long)n2;
    }

    @Override
    public void readFullyDirect(byte[] byArray, int n, int n2) {
        super.readFully(byArray, n, n2);
        this.pos += (long)n2;
    }

    @Override
    public void readFully(byte[] byArray, int n, int n2) {
        super.readFully(byArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            if (byArray[i] == 0) continue;
            this.cipher.decrypt(byArray, n, n2);
            this.xorInitVector(byArray, n, n2, this.pos);
            break;
        }
        this.pos += (long)n2;
    }

    @Override
    public void seek(long l) {
        this.pos = l;
        super.seek(l);
    }

    private void xorInitVector(byte[] byArray, int n, int n2, long l) {
        byte[] byArray2 = this.bufferForInitVector;
        while (n2 > 0) {
            int n3;
            for (n3 = 0; n3 < 16; n3 += 8) {
                Bits.writeLong(byArray2, n3, l + (long)n3 >>> 3);
            }
            this.cipherForInitVector.encrypt(byArray2, 0, 16);
            for (n3 = 0; n3 < 16; ++n3) {
                int n4 = n + n3;
                byArray[n4] = (byte)(byArray[n4] ^ byArray2[n3]);
            }
            l += 16L;
            n += 16;
            n2 -= 16;
        }
    }
}

