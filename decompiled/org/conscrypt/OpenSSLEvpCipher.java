/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.OpenSSLCipher;
import org.conscrypt.ShortBufferWithoutStackTraceException;

public abstract class OpenSSLEvpCipher
extends OpenSSLCipher {
    private final NativeRef.EVP_CIPHER_CTX cipherCtx = new NativeRef.EVP_CIPHER_CTX(NativeCrypto.EVP_CIPHER_CTX_new());
    private boolean calledUpdate;
    private int modeBlockSize;

    public OpenSSLEvpCipher(OpenSSLCipher.Mode mode, OpenSSLCipher.Padding padding) {
        super(mode, padding);
    }

    @Override
    void engineInitInternal(byte[] encodedKey, AlgorithmParameterSpec params, SecureRandom random) throws InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] iv;
        if (params instanceof IvParameterSpec) {
            IvParameterSpec ivParams = (IvParameterSpec)params;
            iv = ivParams.getIV();
        } else {
            iv = null;
        }
        long cipherType = NativeCrypto.EVP_get_cipherbyname(this.getCipherName(encodedKey.length, this.mode));
        if (cipherType == 0L) {
            throw new InvalidAlgorithmParameterException("Cannot find name for key length = " + encodedKey.length * 8 + " and mode = " + (Object)((Object)this.mode));
        }
        boolean encrypting = this.isEncrypting();
        int expectedIvLength = NativeCrypto.EVP_CIPHER_iv_length(cipherType);
        if (iv == null && expectedIvLength != 0) {
            if (!encrypting) {
                throw new InvalidAlgorithmParameterException("IV must be specified in " + (Object)((Object)this.mode) + " mode");
            }
            iv = new byte[expectedIvLength];
            if (random != null) {
                random.nextBytes(iv);
            } else {
                NativeCrypto.RAND_bytes(iv);
            }
        } else {
            if (expectedIvLength == 0 && iv != null) {
                throw new InvalidAlgorithmParameterException("IV not used in " + (Object)((Object)this.mode) + " mode");
            }
            if (iv != null && iv.length != expectedIvLength) {
                throw new InvalidAlgorithmParameterException("expected IV length of " + expectedIvLength + " but was " + iv.length);
            }
        }
        this.iv = iv;
        if (this.supportsVariableSizeKey()) {
            NativeCrypto.EVP_CipherInit_ex(this.cipherCtx, cipherType, null, null, encrypting);
            NativeCrypto.EVP_CIPHER_CTX_set_key_length(this.cipherCtx, encodedKey.length);
            NativeCrypto.EVP_CipherInit_ex(this.cipherCtx, 0L, encodedKey, iv, this.isEncrypting());
        } else {
            NativeCrypto.EVP_CipherInit_ex(this.cipherCtx, cipherType, encodedKey, iv, encrypting);
        }
        NativeCrypto.EVP_CIPHER_CTX_set_padding(this.cipherCtx, this.getPadding() == OpenSSLCipher.Padding.PKCS5PADDING);
        this.modeBlockSize = NativeCrypto.EVP_CIPHER_CTX_block_size(this.cipherCtx);
        this.calledUpdate = false;
    }

    @Override
    int updateInternal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset, int maximumLen) throws ShortBufferException {
        int intialOutputOffset = outputOffset;
        int bytesLeft = output.length - outputOffset;
        if (bytesLeft < maximumLen) {
            throw new ShortBufferWithoutStackTraceException("output buffer too small during update: " + bytesLeft + " < " + maximumLen);
        }
        outputOffset += NativeCrypto.EVP_CipherUpdate(this.cipherCtx, output, outputOffset, input, inputOffset, inputLen);
        this.calledUpdate = true;
        return outputOffset - intialOutputOffset;
    }

    @Override
    int doFinalInternal(byte[] output, int outputOffset, int maximumLen) throws IllegalBlockSizeException, BadPaddingException, ShortBufferException {
        int writtenBytes;
        int initialOutputOffset = outputOffset;
        if (!this.isEncrypting() && !this.calledUpdate) {
            return 0;
        }
        int bytesLeft = output.length - outputOffset;
        if (bytesLeft >= maximumLen) {
            writtenBytes = NativeCrypto.EVP_CipherFinal_ex(this.cipherCtx, output, outputOffset);
        } else {
            byte[] lastBlock = new byte[maximumLen];
            writtenBytes = NativeCrypto.EVP_CipherFinal_ex(this.cipherCtx, lastBlock, 0);
            if (writtenBytes > bytesLeft) {
                throw new ShortBufferWithoutStackTraceException("buffer is too short: " + writtenBytes + " > " + bytesLeft);
            }
            if (writtenBytes > 0) {
                System.arraycopy(lastBlock, 0, output, outputOffset, writtenBytes);
            }
        }
        this.reset();
        return (outputOffset += writtenBytes) - initialOutputOffset;
    }

    @Override
    int getOutputSizeForFinal(int inputLen) {
        if (this.modeBlockSize == 1) {
            return inputLen;
        }
        int buffered = NativeCrypto.get_EVP_CIPHER_CTX_buf_len(this.cipherCtx);
        if (this.getPadding() == OpenSSLCipher.Padding.NOPADDING) {
            return buffered + inputLen;
        }
        boolean finalUsed = NativeCrypto.get_EVP_CIPHER_CTX_final_used(this.cipherCtx);
        int totalLen = inputLen + buffered + (finalUsed ? this.modeBlockSize : 0);
        return (totalLen += totalLen % this.modeBlockSize != 0 || this.isEncrypting() ? this.modeBlockSize : 0) - totalLen % this.modeBlockSize;
    }

    @Override
    int getOutputSizeForUpdate(int inputLen) {
        return this.getOutputSizeForFinal(inputLen);
    }

    abstract String getCipherName(int var1, OpenSSLCipher.Mode var2);

    private void reset() {
        NativeCrypto.EVP_CipherInit_ex(this.cipherCtx, 0L, this.encodedKey, this.iv, this.isEncrypting());
        this.calledUpdate = false;
    }
}

