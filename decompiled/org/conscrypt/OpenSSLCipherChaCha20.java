/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLCipher;
import org.conscrypt.ShortBufferWithoutStackTraceException;

public class OpenSSLCipherChaCha20
extends OpenSSLCipher {
    private static final int BLOCK_SIZE_BYTES = 64;
    private static final int NONCE_SIZE_BYTES = 12;
    private int currentBlockConsumedBytes = 0;
    private int blockCounter = 0;

    @Override
    void engineInitInternal(byte[] encodedKey, AlgorithmParameterSpec params, SecureRandom random) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (params instanceof IvParameterSpec) {
            IvParameterSpec ivParams = (IvParameterSpec)params;
            if (ivParams.getIV().length != 12) {
                throw new InvalidAlgorithmParameterException("IV must be 12 bytes long");
            }
            this.iv = ivParams.getIV();
        } else {
            if (!this.isEncrypting()) {
                throw new InvalidAlgorithmParameterException("IV must be specified when decrypting");
            }
            this.iv = new byte[12];
            if (random != null) {
                random.nextBytes(this.iv);
            } else {
                NativeCrypto.RAND_bytes(this.iv);
            }
        }
    }

    @Override
    int updateInternal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset, int maximumLen) throws ShortBufferException {
        if (inputLen > output.length - outputOffset) {
            throw new ShortBufferWithoutStackTraceException("Insufficient output space");
        }
        int inputLenRemaining = inputLen;
        if (this.currentBlockConsumedBytes > 0) {
            int len = Math.min(64 - this.currentBlockConsumedBytes, inputLenRemaining);
            byte[] singleBlock = new byte[64];
            byte[] singleBlockOut = new byte[64];
            System.arraycopy(input, inputOffset, singleBlock, this.currentBlockConsumedBytes, len);
            NativeCrypto.chacha20_encrypt_decrypt(singleBlock, 0, singleBlockOut, 0, 64, this.encodedKey, this.iv, this.blockCounter);
            System.arraycopy(singleBlockOut, this.currentBlockConsumedBytes, output, outputOffset, len);
            this.currentBlockConsumedBytes += len;
            if (this.currentBlockConsumedBytes < 64) {
                return len;
            }
            assert (this.currentBlockConsumedBytes == 64);
            this.currentBlockConsumedBytes = 0;
            inputOffset += len;
            outputOffset += len;
            inputLenRemaining -= len;
            ++this.blockCounter;
        }
        NativeCrypto.chacha20_encrypt_decrypt(input, inputOffset, output, outputOffset, inputLenRemaining, this.encodedKey, this.iv, this.blockCounter);
        this.currentBlockConsumedBytes = inputLenRemaining % 64;
        this.blockCounter += inputLenRemaining / 64;
        return inputLen;
    }

    @Override
    int doFinalInternal(byte[] output, int outputOffset, int maximumLen) throws IllegalBlockSizeException, BadPaddingException, ShortBufferException {
        this.reset();
        return 0;
    }

    private void reset() {
        this.blockCounter = 0;
        this.currentBlockConsumedBytes = 0;
    }

    @Override
    String getBaseCipherName() {
        return "ChaCha20";
    }

    @Override
    void checkSupportedKeySize(int keySize) throws InvalidKeyException {
        if (keySize != 32) {
            throw new InvalidKeyException("Unsupported key size: " + keySize + " bytes (must be 32)");
        }
    }

    @Override
    void checkSupportedMode(OpenSSLCipher.Mode mode) throws NoSuchAlgorithmException {
        if (mode != OpenSSLCipher.Mode.NONE) {
            throw new NoSuchAlgorithmException("Mode must be NONE");
        }
    }

    @Override
    void checkSupportedPadding(OpenSSLCipher.Padding padding) throws NoSuchPaddingException {
        if (padding != OpenSSLCipher.Padding.NOPADDING) {
            throw new NoSuchPaddingException("Must be NoPadding");
        }
    }

    @Override
    int getCipherBlockSize() {
        return 0;
    }

    @Override
    int getOutputSizeForFinal(int inputLen) {
        return inputLen;
    }

    @Override
    int getOutputSizeForUpdate(int inputLen) {
        return inputLen;
    }
}

