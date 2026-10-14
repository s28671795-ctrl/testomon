/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import org.conscrypt.ArrayUtils;
import org.conscrypt.GCMParameters;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLCipher;
import org.conscrypt.Platform;
import org.conscrypt.ShortBufferWithoutStackTraceException;

public abstract class OpenSSLAeadCipher
extends OpenSSLCipher {
    private static final boolean ENABLE_BYTEBUFFER_OPTIMIZATIONS = true;
    static final int DEFAULT_TAG_SIZE_BITS = 128;
    private static int lastGlobalMessageSize = 32;
    private byte[] previousKey;
    private byte[] previousIv;
    private boolean mustInitialize;
    byte[] buf;
    int bufCount;
    long evpAead;
    private byte[] aad;
    int tagLengthInBytes;

    public OpenSSLAeadCipher(OpenSSLCipher.Mode mode) {
        super(mode, OpenSSLCipher.Padding.NOPADDING);
    }

    private void checkInitialization() {
        if (this.mustInitialize) {
            throw new IllegalStateException("Cannot re-use same key and IV for multiple encryptions");
        }
    }

    private boolean arraysAreEqual(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        int diff = 0;
        for (int i = 0; i < a.length; ++i) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    private void expand(int i) {
        if (this.bufCount + i <= this.buf.length) {
            return;
        }
        byte[] newbuf = new byte[(this.bufCount + i) * 2];
        System.arraycopy(this.buf, 0, newbuf, 0, this.bufCount);
        this.buf = newbuf;
    }

    private void reset() {
        this.aad = null;
        int lastBufSize = lastGlobalMessageSize;
        if (this.buf == null) {
            this.buf = new byte[lastBufSize];
        } else if (this.bufCount > 0 && this.bufCount != lastBufSize) {
            lastGlobalMessageSize = this.bufCount;
            if (this.buf.length != this.bufCount) {
                this.buf = new byte[this.bufCount];
            }
        }
        this.bufCount = 0;
    }

    @Override
    void engineInitInternal(byte[] encodedKey, AlgorithmParameterSpec params, SecureRandom random) throws InvalidKeyException, InvalidAlgorithmParameterException {
        int tagLenBits;
        byte[] iv;
        if (params == null) {
            iv = null;
            tagLenBits = 128;
        } else {
            GCMParameters gcmParams = Platform.fromGCMParameterSpec(params);
            if (gcmParams != null) {
                iv = gcmParams.getIV();
                tagLenBits = gcmParams.getTLen();
            } else if (params instanceof IvParameterSpec) {
                IvParameterSpec ivParams = (IvParameterSpec)params;
                iv = ivParams.getIV();
                tagLenBits = 128;
            } else {
                iv = null;
                tagLenBits = 128;
            }
        }
        this.checkSupportedTagLength(tagLenBits);
        this.tagLengthInBytes = tagLenBits / 8;
        boolean encrypting = this.isEncrypting();
        this.evpAead = this.getEVP_AEAD(encodedKey.length);
        int expectedIvLength = NativeCrypto.EVP_AEAD_nonce_length(this.evpAead);
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
                throw new InvalidAlgorithmParameterException("Expected IV length of " + expectedIvLength + " but was " + iv.length);
            }
        }
        if (this.isEncrypting() && iv != null && !this.allowsNonceReuse()) {
            if (this.previousKey != null && this.previousIv != null && this.arraysAreEqual(this.previousKey, encodedKey) && this.arraysAreEqual(this.previousIv, iv)) {
                this.mustInitialize = true;
                throw new InvalidAlgorithmParameterException("When using AEAD key and IV must not be re-used");
            }
            this.previousKey = encodedKey;
            this.previousIv = iv;
        }
        this.mustInitialize = false;
        this.iv = iv;
        this.reset();
    }

    void checkSupportedTagLength(int tagLenBits) throws InvalidAlgorithmParameterException {
        if (tagLenBits % 8 != 0) {
            throw new InvalidAlgorithmParameterException("Tag length must be a multiple of 8; was " + tagLenBits);
        }
    }

    boolean allowsNonceReuse() {
        return false;
    }

    @Override
    protected int engineDoFinal(ByteBuffer input, ByteBuffer output) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        int bytesWritten;
        if (input == null || output == null) {
            throw new NullPointerException("Null ByteBuffer Error");
        }
        if (this.getOutputSizeForFinal(input.remaining()) > output.remaining()) {
            throw new ShortBufferWithoutStackTraceException("Insufficient Bytes for Output Buffer");
        }
        if (output.isReadOnly()) {
            throw new IllegalArgumentException("Cannot write to Read Only ByteBuffer");
        }
        if (this.bufCount != 0) {
            return super.engineDoFinal(input, output);
        }
        if (!input.isDirect()) {
            int incap = input.remaining();
            ByteBuffer inputClone = ByteBuffer.allocateDirect(incap);
            inputClone.mark();
            inputClone.put(input);
            inputClone.reset();
            input = inputClone;
        }
        if (!output.isDirect()) {
            ByteBuffer outputClone = ByteBuffer.allocateDirect(this.getOutputSizeForFinal(input.remaining()));
            bytesWritten = this.doFinalInternal(input, outputClone);
            output.put(outputClone);
            input.position(input.limit());
        } else {
            bytesWritten = this.doFinalInternal(input, output);
            output.position(output.position() + bytesWritten);
            input.position(input.limit());
        }
        return bytesWritten;
    }

    @Override
    protected int engineDoFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        if (output != null && this.getOutputSizeForFinal(inputLen) > output.length - outputOffset) {
            throw new ShortBufferWithoutStackTraceException("Insufficient output space");
        }
        return super.engineDoFinal(input, inputOffset, inputLen, output, outputOffset);
    }

    @Override
    int updateInternal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset, int maximumLen) throws ShortBufferException {
        this.checkInitialization();
        if (this.buf == null) {
            throw new IllegalStateException("Cipher not initialized");
        }
        ArrayUtils.checkOffsetAndCount(input.length, inputOffset, inputLen);
        if (inputLen > 0) {
            this.expand(inputLen);
            System.arraycopy(input, inputOffset, this.buf, this.bufCount, inputLen);
            this.bufCount += inputLen;
        }
        return 0;
    }

    private void throwAEADBadTagExceptionIfAvailable(String message, Throwable cause) throws BadPaddingException {
        Constructor<?> aeadBadTagConstructor;
        try {
            aeadBadTagConstructor = Class.forName("javax.crypto.AEADBadTagException").getConstructor(String.class);
        }
        catch (Exception ignored) {
            return;
        }
        BadPaddingException badTagException = null;
        try {
            badTagException = (BadPaddingException)aeadBadTagConstructor.newInstance(message);
            badTagException.initCause(cause);
        }
        catch (IllegalAccessException illegalAccessException) {
        }
        catch (InstantiationException instantiationException) {
        }
        catch (InvocationTargetException e2) {
            throw (BadPaddingException)new BadPaddingException().initCause(e2.getTargetException());
        }
        if (badTagException != null) {
            throw badTagException;
        }
    }

    int doFinalInternal(ByteBuffer input, ByteBuffer output) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        int bytesWritten;
        this.checkInitialization();
        try {
            bytesWritten = this.isEncrypting() ? NativeCrypto.EVP_AEAD_CTX_seal_buf(this.evpAead, this.encodedKey, this.tagLengthInBytes, output, this.iv, input, this.aad) : NativeCrypto.EVP_AEAD_CTX_open_buf(this.evpAead, this.encodedKey, this.tagLengthInBytes, output, this.iv, input, this.aad);
        }
        catch (BadPaddingException e) {
            this.throwAEADBadTagExceptionIfAvailable(e.getMessage(), e.getCause());
            throw e;
        }
        if (this.isEncrypting()) {
            this.mustInitialize = true;
        }
        return bytesWritten;
    }

    @Override
    int doFinalInternal(byte[] output, int outputOffset, int maximumLen) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        int bytesWritten;
        this.checkInitialization();
        try {
            bytesWritten = this.isEncrypting() ? NativeCrypto.EVP_AEAD_CTX_seal(this.evpAead, this.encodedKey, this.tagLengthInBytes, output, outputOffset, this.iv, this.buf, 0, this.bufCount, this.aad) : NativeCrypto.EVP_AEAD_CTX_open(this.evpAead, this.encodedKey, this.tagLengthInBytes, output, outputOffset, this.iv, this.buf, 0, this.bufCount, this.aad);
        }
        catch (BadPaddingException e) {
            this.throwAEADBadTagExceptionIfAvailable(e.getMessage(), e.getCause());
            throw e;
        }
        if (this.isEncrypting()) {
            this.mustInitialize = true;
        }
        this.reset();
        return bytesWritten;
    }

    @Override
    void checkSupportedPadding(OpenSSLCipher.Padding padding) throws NoSuchPaddingException {
        if (padding != OpenSSLCipher.Padding.NOPADDING) {
            throw new NoSuchPaddingException("Must be NoPadding for AEAD ciphers");
        }
    }

    @Override
    int getOutputSizeForUpdate(int inputLen) {
        return 0;
    }

    @Override
    int getOutputSizeForFinal(int inputLen) {
        return this.bufCount + inputLen + (this.isEncrypting() ? NativeCrypto.EVP_AEAD_max_overhead(this.evpAead) : 0);
    }

    @Override
    protected void engineUpdateAAD(byte[] input, int inputOffset, int inputLen) {
        this.checkInitialization();
        if (this.aad == null) {
            this.aad = Arrays.copyOfRange(input, inputOffset, inputOffset + inputLen);
        } else {
            int newSize = this.aad.length + inputLen;
            byte[] newaad = new byte[newSize];
            System.arraycopy(this.aad, 0, newaad, 0, this.aad.length);
            System.arraycopy(input, inputOffset, newaad, this.aad.length, inputLen);
            this.aad = newaad;
        }
    }

    @Override
    protected void engineUpdateAAD(ByteBuffer buf) {
        this.checkInitialization();
        if (this.aad == null) {
            this.aad = new byte[buf.remaining()];
            buf.get(this.aad);
        } else {
            int newSize = this.aad.length + buf.remaining();
            byte[] newaad = new byte[newSize];
            System.arraycopy(this.aad, 0, newaad, 0, this.aad.length);
            buf.get(newaad, this.aad.length, buf.remaining());
            this.aad = newaad;
        }
    }

    abstract long getEVP_AEAD(int var1) throws InvalidKeyException;
}

