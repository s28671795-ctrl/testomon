/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security;

import org.h2.security.BlockCipher;
import org.h2.util.Bits;

public class Fog
implements BlockCipher {
    private int key;

    @Override
    public void encrypt(byte[] byArray, int n, int n2) {
        for (int i = n; i < n + n2; i += 16) {
            this.encryptBlock(byArray, byArray, i);
        }
    }

    @Override
    public void decrypt(byte[] byArray, int n, int n2) {
        for (int i = n; i < n + n2; i += 16) {
            this.decryptBlock(byArray, byArray, i);
        }
    }

    private void encryptBlock(byte[] byArray, byte[] byArray2, int n) {
        int n2 = Bits.readInt(byArray, n);
        int n3 = Bits.readInt(byArray, n + 4);
        int n4 = Bits.readInt(byArray, n + 8);
        int n5 = Bits.readInt(byArray, n + 12);
        int n6 = this.key;
        n2 = Integer.rotateLeft(n2 ^ n6, n3);
        n4 = Integer.rotateLeft(n4 ^ n6, n3);
        n3 = Integer.rotateLeft(n3 ^ n6, n2);
        n5 = Integer.rotateLeft(n5 ^ n6, n2);
        Bits.writeInt(byArray2, n, n2);
        Bits.writeInt(byArray2, n + 4, n3);
        Bits.writeInt(byArray2, n + 8, n4);
        Bits.writeInt(byArray2, n + 12, n5);
    }

    private void decryptBlock(byte[] byArray, byte[] byArray2, int n) {
        int n2 = Bits.readInt(byArray, n);
        int n3 = Bits.readInt(byArray, n + 4);
        int n4 = Bits.readInt(byArray, n + 8);
        int n5 = Bits.readInt(byArray, n + 12);
        int n6 = this.key;
        n3 = Integer.rotateRight(n3, n2) ^ n6;
        n5 = Integer.rotateRight(n5, n2) ^ n6;
        n2 = Integer.rotateRight(n2, n3) ^ n6;
        n4 = Integer.rotateRight(n4, n3) ^ n6;
        Bits.writeInt(byArray2, n, n2);
        Bits.writeInt(byArray2, n + 4, n3);
        Bits.writeInt(byArray2, n + 8, n4);
        Bits.writeInt(byArray2, n + 12, n5);
    }

    @Override
    public int getKeyLength() {
        return 16;
    }

    @Override
    public void setKey(byte[] byArray) {
        this.key = (int)Bits.readLong(byArray, 0);
    }
}

