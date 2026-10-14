/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.crystals.kyber;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.SICBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

abstract class Symmetric {
    final int xofBlockBytes;

    abstract void hash_h(byte[] var1, byte[] var2, int var3);

    abstract void hash_g(byte[] var1, byte[] var2);

    abstract void xofAbsorb(byte[] var1, byte var2, byte var3);

    abstract void xofSqueezeBlocks(byte[] var1, int var2, int var3);

    abstract void prf(byte[] var1, byte[] var2, byte var3);

    abstract void kdf(byte[] var1, byte[] var2);

    Symmetric(int n) {
        this.xofBlockBytes = n;
    }

    @Deprecated
    static class AesSymmetric
    extends Symmetric {
        private final SHA256Digest sha256Digest = new SHA256Digest();
        private final SHA512Digest sha512Digest = new SHA512Digest();
        private final StreamCipher cipher = SICBlockCipher.newInstance((BlockCipher)AESEngine.newInstance());

        AesSymmetric() {
            super(64);
        }

        private void doDigest(ExtendedDigest extendedDigest, byte[] byArray, byte[] byArray2, int n) {
            extendedDigest.update(byArray2, 0, byArray2.length);
            extendedDigest.doFinal(byArray, n);
        }

        private void aes128(byte[] byArray, int n, int n2) {
            byte[] byArray2 = new byte[n2];
            this.cipher.processBytes(byArray2, 0, n2, byArray, n);
        }

        @Override
        void hash_h(byte[] byArray, byte[] byArray2, int n) {
            this.doDigest(this.sha256Digest, byArray, byArray2, n);
        }

        @Override
        void hash_g(byte[] byArray, byte[] byArray2) {
            this.doDigest(this.sha512Digest, byArray, byArray2, 0);
        }

        @Override
        void xofAbsorb(byte[] byArray, byte by, byte by2) {
            byte[] byArray2 = new byte[12];
            byArray2[0] = by;
            byArray2[1] = by2;
            ParametersWithIV parametersWithIV = new ParametersWithIV(new KeyParameter(byArray, 0, 32), byArray2);
            this.cipher.init(true, parametersWithIV);
        }

        @Override
        void xofSqueezeBlocks(byte[] byArray, int n, int n2) {
            this.aes128(byArray, n, n2);
        }

        @Override
        void prf(byte[] byArray, byte[] byArray2, byte by) {
            byte[] byArray3 = new byte[12];
            byArray3[0] = by;
            ParametersWithIV parametersWithIV = new ParametersWithIV(new KeyParameter(byArray2, 0, 32), byArray3);
            this.cipher.init(true, parametersWithIV);
            this.aes128(byArray, 0, byArray.length);
        }

        @Override
        void kdf(byte[] byArray, byte[] byArray2) {
            byte[] byArray3 = new byte[32];
            this.doDigest(this.sha256Digest, byArray3, byArray2, 0);
            System.arraycopy(byArray3, 0, byArray, 0, byArray.length);
        }
    }

    static class ShakeSymmetric
    extends Symmetric {
        private final SHAKEDigest xof = new SHAKEDigest(128);
        private final SHA3Digest sha3Digest512;
        private final SHA3Digest sha3Digest256;
        private final SHAKEDigest shakeDigest = new SHAKEDigest(256);

        ShakeSymmetric() {
            super(168);
            this.sha3Digest256 = new SHA3Digest(256);
            this.sha3Digest512 = new SHA3Digest(512);
        }

        @Override
        void hash_h(byte[] byArray, byte[] byArray2, int n) {
            this.sha3Digest256.update(byArray2, 0, byArray2.length);
            this.sha3Digest256.doFinal(byArray, n);
        }

        @Override
        void hash_g(byte[] byArray, byte[] byArray2) {
            this.sha3Digest512.update(byArray2, 0, byArray2.length);
            this.sha3Digest512.doFinal(byArray, 0);
        }

        @Override
        void xofAbsorb(byte[] byArray, byte by, byte by2) {
            this.xof.reset();
            byte[] byArray2 = new byte[byArray.length + 2];
            System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
            byArray2[byArray.length] = by;
            byArray2[byArray.length + 1] = by2;
            this.xof.update(byArray2, 0, byArray.length + 2);
        }

        @Override
        void xofSqueezeBlocks(byte[] byArray, int n, int n2) {
            this.xof.doOutput(byArray, n, n2);
        }

        @Override
        void prf(byte[] byArray, byte[] byArray2, byte by) {
            byte[] byArray3 = new byte[byArray2.length + 1];
            System.arraycopy(byArray2, 0, byArray3, 0, byArray2.length);
            byArray3[byArray2.length] = by;
            this.shakeDigest.update(byArray3, 0, byArray3.length);
            this.shakeDigest.doFinal(byArray, 0, byArray.length);
        }

        @Override
        void kdf(byte[] byArray, byte[] byArray2) {
            this.shakeDigest.update(byArray2, 0, byArray2.length);
            this.shakeDigest.doFinal(byArray, 0, byArray.length);
        }
    }
}

